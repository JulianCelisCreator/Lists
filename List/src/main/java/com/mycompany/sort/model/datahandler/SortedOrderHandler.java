package com.mycompany.sort.model.datahandler;

import com.mycompany.sort.model.politico.Politico;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Comparator;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Manejador que genera políticos ordenados por dinero (1,000 a 1,000,000)
 * con fechas aleatorias (20-80 años atrás)
 */
public class SortedOrderHandler extends DataGeneratorHandler {

    private static final int MIN_DINERO = 1000;
    private static final int MAX_DINERO = 1_000_000;
    private static final int MIN_ANIOS = 20;
    private static final int MAX_ANIOS = 80;

    @Override
    public Politico[] generateData(String type, int size) {
        if ("SORTED".equalsIgnoreCase(type)) {
            Politico[] data = new Politico[size];

            // Distribución lineal del dinero en el rango especificado
            for (int i = 0; i < size; i++) {
                int dinero = calcularDineroOrdenado(i, size);
                LocalDate fecha = generarFechaAleatoria();
                data[i] = new Politico(dinero, fecha);
            }

            Arrays.sort(data, Comparator.comparingInt(Politico::getDinero));
            return data;
        }
        return delegarSiNoEsTipoSolicitado(type, size);
    }

    private int calcularDineroOrdenado(int index, int total) {
        if (total <= 1) return MIN_DINERO;
        return MIN_DINERO + (index * (MAX_DINERO - MIN_DINERO)) / (total - 1);
    }

    private LocalDate generarFechaAleatoria() {
        int anosAtras = MIN_ANIOS + ThreadLocalRandom.current().nextInt(MAX_ANIOS - MIN_ANIOS + 1);
        return LocalDate.now().minusYears(anosAtras);
    }

    private Politico[] delegarSiNoEsTipoSolicitado(String type, int size) {
        if (nextHandler != null) {
            return nextHandler.generateData(type, size);
        }
        throw new IllegalArgumentException("Tipo no soportado: " + type);
    }
}