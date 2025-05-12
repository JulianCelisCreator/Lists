package com.mycompany.sort;

import com.mycompany.sort.controller.SortingController;
import com.mycompany.sort.view.SortingGUI;
import javax.swing.SwingUtilities;

/**
 * Clase principal del programa. Inicia la aplicación lanzando la interfaz gráfica.
 */
public class Main {
    // Método main para ejecutar la GUI
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SortingController controller = new SortingController();
            new SortingGUI(controller).setVisible(true);
        });
    }
}