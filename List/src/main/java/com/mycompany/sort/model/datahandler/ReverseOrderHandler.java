package com.mycompany.sort.model.datahandler;

import com.mycompany.sort.model.politico.Politico;
import java.time.LocalDate;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Manejador concreto de la cadena de generación de datos que genera un arreglo
 * de objetos {@link Politico} ordenado de forma inversa.
 * Si el tipo de dato solicitado no es "INVERSE", delega la generación al siguiente
 * manejador en la cadena.
 */
public class ReverseOrderHandler extends DataGeneratorHandler {

    private static final int MIN_DINERO = 1000;
    private static final int MAX_DINERO = 1_000_000;
    private static final int MIN_ANIOS = 20;
    private static final int MAX_ANIOS = 80;

    @Override
    public Politico[] generateData(String type, int size) {
        if ("INVERSE".equalsIgnoreCase(type)) {
            Politico[] data = new Politico[size];

            // Generación directa en orden descendente
            for (int i = 0; i < size; i++) {
                int dinero = calcularDineroDescendente(i, size);
                LocalDate fecha = generarFechaAleatoria();
                data[i] = new Politico(dinero, fecha);
            }
            return data;
        }
        return delegarSiNoEsTipoSolicitado(type, size);
    }

    /** Calcula dinero en orden descendente (mayor a menor) */
    private int calcularDineroDescendente(int index, int total) {
        if (total <= 1) return MAX_DINERO;
        return MAX_DINERO - (index * (MAX_DINERO - MIN_DINERO) / (total - 1));
    }

    /** Genera fechas aleatorias entre 20-80 años atrás */
    private LocalDate generarFechaAleatoria() {
        int anosAtras = ThreadLocalRandom.current().nextInt(MIN_ANIOS, MAX_ANIOS + 1);
        return LocalDate.now().minusYears(anosAtras);
    }

    /** Delegación al siguiente manejador en la cadena */
    private Politico[] delegarSiNoEsTipoSolicitado(String type, int size) {
        if (nextHandler != null) {
            return nextHandler.generateData(type, size);
        }
        throw new IllegalArgumentException("Tipo no soportado: " + type);
    }
}
