package com.mycompany.sort.view;

import com.mycompany.sort.controller.SortingController;
import com.mycompany.sort.model.ResultadoOrdenamiento;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
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

        // Área de resultados
        areaResultados = new JTextArea();
        areaResultados.setEditable(false);
        areaResultados.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(areaResultados);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Resultados"));
        add(scrollPane, BorderLayout.CENTER);

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
        if (cantidad <= 0) throw new NumberFormatException();
        return cantidad;
    }

    private void limpiarResultados() {
        areaResultados.setText("");
    }

    private void ejecutarYMostrarResultados(int cantidad) {
        controller.ejecutarSimulacionCompleta(cantidad);
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
            String ruta = System.getProperty("user.home") + "/Desktop/ResultadosOrdenamiento_" + System.currentTimeMillis() + ".csv";
            controller.exportarCSV(ruta);
            JOptionPane.showMessageDialog(this, "CSV guardado en:\n" + ruta, "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException ex) {
            mostrarError("Error al guardar CSV: " + ex.getMessage());
        }
    }

    private void exportarPDF(ActionEvent e) {
        try {
            String ruta = System.getProperty("user.home") + "/Desktop/ResultadosOrdenamiento_" + System.currentTimeMillis() + ".pdf";
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
