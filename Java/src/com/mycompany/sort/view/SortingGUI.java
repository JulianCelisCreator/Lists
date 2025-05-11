package com.mycompany.sort.view;

import com.mycompany.sort.controller.SortingController;
import com.mycompany.sort.model.SortingStrategy.BubbleSortingStrategy;
import com.mycompany.sort.model.SortingStrategy.SortResult;
import com.mycompany.sort.model.politico.Politico;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class SortingGUI extends JFrame {

    private final SortingController controller;
    private JTextField inputCantidad;
    private JTextArea areaResultados;

    public SortingGUI() {
        this.controller = new SortingController(new BubbleSortingStrategy<>());

        setTitle("Simulación de Ordenamiento");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        inicializarComponentes();
    }

    private void inicializarComponentes() {
        JPanel panelPrincipal = new JPanel(new BorderLayout());

        // Parte superior: entrada de datos y botón
        JPanel panelSuperior = new JPanel();
        panelSuperior.add(new JLabel("Cantidad de datos:"));

        inputCantidad = new JTextField(10);
        panelSuperior.add(inputCantidad);

        JButton btnIniciar = new JButton("Iniciar");
        btnIniciar.addActionListener(this::accionIniciar);
        panelSuperior.add(btnIniciar);

        panelPrincipal.add(panelSuperior, BorderLayout.NORTH);

        // Parte central: resultados
        areaResultados = new JTextArea();
        areaResultados.setEditable(false);
        JScrollPane scroll = new JScrollPane(areaResultados);
        panelPrincipal.add(scroll, BorderLayout.CENTER);

        add(panelPrincipal);
    }

    private void accionIniciar(ActionEvent e) {
        String texto = inputCantidad.getText().trim();
        areaResultados.setText(""); // Limpiar

        try {
            int cantidad = Integer.parseInt(texto);
            if (cantidad <= 0) {
                throw new NumberFormatException();
            }

            controller.ejecutarSimulacion(cantidad);

            areaResultados.append(String.format("%-10s %-30s %-15s %-15s%n",
                    "Tipo", "Estrategia", "Tiempo (ms)", "Iteraciones"));

            for (SortResult r : controller.getResultados()) {
                areaResultados.append(String.format("%-10s %-30s %-15.3f %-15d%n",
                        r.getType(), r.getStrategy(), r.getTimeElapsedMillis(), r.getIterations()));
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Ingrese un número entero válido mayor que 0.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
