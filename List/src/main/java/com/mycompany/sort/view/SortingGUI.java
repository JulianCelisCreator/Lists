package com.mycompany.sort.view;

import com.mycompany.sort.controller.SortingController;
import com.mycompany.sort.model.ResultadoOrdenamiento;
import com.mycompany.sort.model.politico.Politico;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class SortingGUI extends JFrame {
    private static final String TITULO = "Simulación de Ordenamiento";
    private static final Dimension DIMENSION = new Dimension(800, 500);

    private final SortingController controller;
    private JTextField inputCantidad;
    private JTextArea areaResultados;

    public SortingGUI(SortingController controller) {
        this.controller = controller;
        configurarVentana();
        inicializarComponentes();
    }

    private void configurarVentana() {
        setTitle(TITULO);
        setSize(DIMENSION);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setMinimumSize(new Dimension(600, 400));
    }

    private void inicializarComponentes() {
        setLayout(new BorderLayout(10, 10));

        // Panel de entrada
        add(crearPanelEntrada(), BorderLayout.NORTH);

        // Panel central con pestañas
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Lista Simple", crearPanelLista("simple"));
        tabbedPane.addTab("Lista Doble", crearPanelLista("doble"));
        tabbedPane.addTab("Lista Circular", crearPanelLista("circular"));

        // Área de resultados
        areaResultados = new JTextArea();
        areaResultados.setEditable(false);
        areaResultados.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(areaResultados);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Resultados"));

        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, tabbedPane, scrollPane);
        splitPane.setResizeWeight(0.5);
        add(splitPane, BorderLayout.CENTER);

        // Panel de control inferior
        add(crearPanelControl(), BorderLayout.SOUTH);
    }

    private JPanel crearPanelEntrada() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panel.add(new JLabel("Cantidad de datos:"));

        inputCantidad = new JTextField(10);
        inputCantidad.setToolTipText("Ingrese la cantidad de elementos a ordenar");
        panel.add(inputCantidad);

        JButton btnIniciar = new JButton("Iniciar Simulación");
        btnIniciar.addActionListener(this::ejecutarSimulacion);
        panel.add(btnIniciar);

        return panel;
    }

    private JPanel crearPanelControl() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        JButton btnExportarCSV = new JButton("Exportar a CSV");
        btnExportarCSV.addActionListener(this::exportarCSV);
        panel.add(btnExportarCSV);

        JButton btnExportarPDF = new JButton("Exportar a PDF");
        btnExportarPDF.addActionListener(this::exportarPDF);
        panel.add(btnExportarPDF);

        return panel;
    }

    private JPanel crearPanelLista(String tipoLista) {
        JPanel panel = new JPanel(new BorderLayout());

        // Área de texto para mostrar los primeros 10 elementos
        JTextArea areaElementos = new JTextArea();
        areaElementos.setEditable(false);
        areaElementos.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(areaElementos);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Primeros 10 Elementos"));
        panel.add(scrollPane, BorderLayout.CENTER);

        // Panel de controles
        JPanel panelControles = new JPanel(new GridLayout(2, 2, 10, 10));

        // Botón para mostrar elementos
        JButton btnMostrar = new JButton("Mostrar 10 Primeros");
        btnMostrar.addActionListener(e -> {
            String elementos = controller.getPrimeros10Elementos(tipoLista);
            areaElementos.setText(elementos);
        });
        panelControles.add(btnMostrar);

        // Botón para insertar nuevo elemento
        JButton btnInsertarInicio = new JButton("Insertar al Inicio");
        btnInsertarInicio.addActionListener(e -> {
            Object[] entrada = mostrarDialogoEntradaPolitico();
            if (entrada != null) {
                controller.insertarPoliticoAlInicio(tipoLista, (int) entrada[0], (LocalDate) entrada[1]);
                areaElementos.setText(controller.getPrimeros10Elementos(tipoLista));
            }
        });
        panelControles.add(btnInsertarInicio);

        // Insertar al final
        JButton btnInsertarFinal = new JButton("Insertar al Final");
        btnInsertarFinal.addActionListener(e -> {
            Object[] entrada = mostrarDialogoEntradaPolitico();
            if (entrada != null) {
                controller.insertarPolitico(tipoLista, (int) entrada[0], (LocalDate) entrada[1]);
                areaElementos.setText(controller.getPrimeros10Elementos(tipoLista));
            }
        });
        panelControles.add(btnInsertarFinal);

        // Eliminar por datos
        JButton btnEliminar = new JButton("Eliminar");
        btnEliminar.addActionListener(e -> {
            Object[] entrada = mostrarDialogoEntradaPolitico();
            if (entrada != null) {
                controller.eliminarPolitico(tipoLista, (int) entrada[0], (LocalDate) entrada[1]);
                areaElementos.setText(controller.getPrimeros10Elementos(tipoLista));
            }
        });
        panelControles.add(btnEliminar);

        // Buscar (visualización simple)
        JButton btnBuscar = new JButton("Buscar");
        btnBuscar.addActionListener(e -> {
            Object[] entrada = mostrarDialogoEntradaPolitico();
            if (entrada != null) {
                String resultado = controller.buscarPolitico(tipoLista, (int) entrada[0], (LocalDate) entrada[1])
                        ? "Encontrado"
                        : "No encontrado";
                JOptionPane.showMessageDialog(this, resultado, "Resultado de Búsqueda",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        });
        panelControles.add(btnBuscar);

        panel.add(panelControles, BorderLayout.SOUTH);
        return panel;
    }

    private Object[] mostrarDialogoEntradaPolitico() {
        JSpinner dineroSpinner = new JSpinner(new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 100));
        SpinnerDateModel dateModel = new SpinnerDateModel();
        JSpinner fechaSpinner = new JSpinner(dateModel);
        fechaSpinner.setEditor(new JSpinner.DateEditor(fechaSpinner, "dd/MM/yyyy"));

        JPanel panel = new JPanel(new GridLayout(2, 2));
        panel.add(new JLabel("Dinero ($):"));
        panel.add(dineroSpinner);
        panel.add(new JLabel("Fecha:"));
        panel.add(fechaSpinner);

        int result = JOptionPane.showConfirmDialog(
                this,
                panel,
                "Crear Nuevo Político",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            try {
                int dinero = (Integer) dineroSpinner.getValue();
                LocalDate fecha = ((java.util.Date) fechaSpinner.getValue()).toInstant()
                        .atZone(java.time.ZoneId.systemDefault())
                        .toLocalDate();
                return new Object[] { dinero, fecha };
            } catch (Exception e) {
                JOptionPane.showMessageDialog(
                        this,
                        "Error en los datos: " + e.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
        return null;
    }

    private void ejecutarSimulacion(ActionEvent e) {
        try {
            int cantidad = validarEntrada();
            limpiarResultados();
            ejecutarYMostrarResultados(cantidad);
        } catch (NumberFormatException ex) {
            mostrarError("Ingrese un número entero válido mayor que 0.");
        }
    }

    private int validarEntrada() throws NumberFormatException {
        String texto = inputCantidad.getText().trim();
        int cantidad = Integer.parseInt(texto);
        if (cantidad <= 0)
            throw new NumberFormatException();
        return cantidad;
    }

    private void limpiarResultados() {
        areaResultados.setText("");
    }

    private void ejecutarYMostrarResultados(int cantidad) {
        controller.ejecutarSimulacionCompleta(cantidad);
        controller.cargarDatosParaManipulacion(controller.crearArregloOrdenado(cantidad));
        mostrarResultados();
    }

    private void mostrarResultados() {
        areaResultados.append(String.format("%-12s %-35s %-12s %-12s%n",
                "TIPO", "ESTRATEGIA", "TIEMPO (ms)", "ITERACIONES"));
        areaResultados.append("--------------------------------------------------------------------------\n");

        List<ResultadoOrdenamiento> resultados = controller.getResultados();
        for (ResultadoOrdenamiento r : resultados) {
            areaResultados.append(String.format("%-12s %-35s %-12.3f %-12d%n",
                    r.getType(),
                    r.getStrategy(),
                    r.getTimeElapsedMillis(),
                    r.getIterations()));
        }
    }

    private void exportarCSV(ActionEvent e) {
        try {
            String ruta = System.getProperty("user.home") + "/Desktop/ResultadosOrdenamiento_"
                    + System.currentTimeMillis() + ".csv";
            controller.exportarCSV(ruta);
            JOptionPane.showMessageDialog(this, "CSV guardado en:\n" + ruta, "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException ex) {
            mostrarError("Error al guardar CSV: " + ex.getMessage());
        }
    }

    private void exportarPDF(ActionEvent e) {
        try {
            String ruta = System.getProperty("user.home") + "/Desktop/ResultadosOrdenamiento_"
                    + System.currentTimeMillis() + ".pdf";
            controller.exportarPDF(ruta);
            JOptionPane.showMessageDialog(this, "PDF guardado en:\n" + ruta, "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException ex) {
            mostrarError("Error al guardar PDF: " + ex.getMessage());
        }
    }

    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }
}