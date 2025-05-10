package com.mycompany.sort;

import com.mycompany.sort.controller.SortingController;
import com.mycompany.sort.view.SortingGUI;

import javax.swing.SwingUtilities;

/**
 * Clase principal del programa. Inicia la aplicación lanzando la interfaz gráfica.
 */
public class Main {
    /**
     * Punto de entrada del programa. Usa SwingUtilities para asegurar
     * que la GUI se construya y manipule en el hilo de eventos de Swing (EDT).
     *
     * @param args Argumentos de línea de comandos (no utilizados).
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SortingController controller = new SortingController(); // Lógica del análisis
            SortingGUI gui = new SortingGUI(controller);            // Interfaz visual
            gui.setVisible(true);
        });
    }
}
