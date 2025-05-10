package com.mycompany.sort.view;

import com.mycompany.sort.controller.SortingController;
import com.mycompany.sort.controller.accumulator.AccumulatorValue;
import com.mycompany.sort.model.SortingStrategy.SortingStrategy;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Interfaz gráfica que permite visualizar y comparar el rendimiento de distintas
 * estrategias de ordenamiento sobre arreglos y matrices de objetos tipo Politico.
 */
public class SortingGUI extends JFrame {
    private final SortingController controller;
    private final JTabbedPane tabbedPane;
    private final DefaultTableModel arrayTableModel;
    private final DefaultTableModel matrixTableModel;

    /**
     * Constructor principal. Inicializa todos los componentes gráficos.
     *
     * @param controller Controlador que maneja la lógica de análisis.
     */
    public SortingGUI(SortingController controller) {
        super("Analizador de Estrategias de Ordenamiento");
        this.controller = controller;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Panel superior con controles
        JPanel controlPanel = new JPanel();
        JTextField initialSizeField = new JTextField("1000", 10);
        JTextField growthFactorField = new JTextField("1.5", 10);
        JButton startButton = new JButton("Iniciar");
        JButton stopButton = new JButton("Detener");
        JButton exportButton = new JButton("Exportar"); // Corregir nombre
        controlPanel.add(exportButton); // Agregar al panel
        controlPanel.add(new JLabel("Tamaño inicial:"));
        controlPanel.add(initialSizeField);
        controlPanel.add(new JLabel("Factor crecimiento:"));
        controlPanel.add(growthFactorField);
        controlPanel.add(startButton);
        controlPanel.add(stopButton);
        add(controlPanel, BorderLayout.NORTH);

        // Tablas y pestañas
        tabbedPane = new JTabbedPane();
        arrayTableModel = createTableModel();
        matrixTableModel = createTableModel();

        tabbedPane.addTab("Arreglos", new JScrollPane(new JTable(arrayTableModel)));
        tabbedPane.addTab("Matrices", new JScrollPane(new JTable(matrixTableModel)));
        add(tabbedPane, BorderLayout.CENTER);

        exportButton.addActionListener(e -> {
            Component selectedTab = tabbedPane.getSelectedComponent();
            if (selectedTab instanceof JScrollPane) {
                JViewport viewport = ((JScrollPane) selectedTab).getViewport();
                if (viewport.getView() instanceof JTable) {
                    exportToHTML((JTable) viewport.getView());
                }
            }
        });
        // Listeners de botones
        startButton.addActionListener(e -> {
            try {
                int initialSize = Integer.parseInt(initialSizeField.getText());
                double growthFactor = Double.parseDouble(growthFactorField.getText());

                new Thread(() -> {
                    controller.runAnalysis(initialSize, growthFactor);
                    SwingUtilities.invokeLater(this::updateTables);
                }).start();

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Valores inválidos", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        stopButton.addActionListener(e -> updateTables());
    }

    /**
     * Crea un modelo de tabla con columnas basadas en las estrategias disponibles.
     *
     * @return Modelo de tabla con columnas configuradas.
     */
    private DefaultTableModel createTableModel() {
        List<SortingStrategy> strategies = controller.getStrategies();
        String[] columns = new String[1 + strategies.size() * 2];
        columns[0] = "Caso";

        int index = 1;
        for (SortingStrategy strategy : strategies) {
            columns[index++] = strategy.getName() + " (Iter)";
            columns[index++] = strategy.getName() + " (ms)";
        }

        return new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
    }

    /**
     * Actualiza ambas tablas (arreglos y matrices) con los datos acumulados del análisis.
     */
    private void updateTables() {
        updateTableModel(arrayTableModel, controller.getAccumulatedResults("ARRAY"));
        updateTableModel(matrixTableModel, controller.getAccumulatedResults("MATRIX"));
    }

    /**
     * Actualiza el modelo de tabla con los resultados promedios por tipo de caso (RANDOM, SORTED, INVERSE).
     *
     * @param model Modelo de tabla a actualizar.
     * @param data  Datos acumulados organizados por tipo de caso y estrategia.
     */
    private void updateTableModel(DefaultTableModel model, Map<String, Map<String, AccumulatorValue>> data) {
        model.setRowCount(0);
        List<String> cases = List.of("SORTED", "INVERSE", "RANDOM");
        List<SortingStrategy> strategies = controller.getStrategies();

        for (String dataCase : cases) {
            Object[] row = new Object[model.getColumnCount()];
            row[0] = dataCase;

            Map<String, AccumulatorValue> strategyData = data.get(dataCase);
            if (strategyData == null) continue;

            int colIndex = 1;
            for (SortingStrategy strategy : strategies) {
                AccumulatorValue value = strategyData.get(strategy.getName());
                if (value != null) {
                    row[colIndex++] = String.format("%.2f", value.getAverageIterations());
                    row[colIndex++] = String.format("%.2f", value.getAverageTime());
                } else {
                    colIndex += 2;
                }
            }
            model.addRow(row);
        }
    }

    private void exportToHTML(JTable table) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("Archivos HTML", "html"));

        if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            if (!file.getName().toLowerCase().endsWith(".html")) {
                file = new File(file.getParentFile(), file.getName() + ".html");
            }

            try (FileWriter writer = new FileWriter(file)) {
                writer.write("<html><body>\n");
                writer.write("<h1>Reporte de Ordenamiento</h1>\n");
                writer.write("<table border='1'>\n");

                // Encabezados
                writer.write("<tr>");
                for (int i = 0; i < table.getColumnCount(); i++) {
                    writer.write("<th>" + table.getColumnName(i) + "</th>");
                }
                writer.write("</tr>\n");

                // Datos
                for (int row = 0; row < table.getRowCount(); row++) {
                    writer.write("<tr>");
                    for (int col = 0; col < table.getColumnCount(); col++) {
                        writer.write("<td>" + table.getValueAt(row, col) + "</td>");
                    }
                    writer.write("</tr>\n");
                }

                writer.write("</table>\n</body></html>");
                JOptionPane.showMessageDialog(this, "Datos exportados exitosamente!");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error al exportar: " + ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Punto de entrada del programa. Lanza la interfaz gráfica.
     *
     * @param args Argumentos del sistema (no utilizados).
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SortingController controller = new SortingController();
            SortingGUI gui = new SortingGUI(controller);
            gui.setVisible(true);
        });
    }
}
