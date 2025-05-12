package com.mycompany.sort.model.datahandler;

import com.mycompany.sort.model.politico.Politico;
import java.time.LocalDate;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Manejador que genera políticos con valores aleatorios controlados:
 * - Dinero entre 1,000 y 1,000,000
 * - Fechas entre 20 y 80 años atrás
 */
public class RandomOrderHandler extends DataGeneratorHandler {

    private static final int MIN_DINERO = 1000;
    private static final int MAX_DINERO = 1_000_000;
    private static final int MIN_ANIOS = 20;
    private static final int MAX_ANIOS = 80;

    @Override
    public Politico[] generateData(String type, int size) {
        if (!"RANDOM".equalsIgnoreCase(type)) {
            return delegarSiNoEsTipoSolicitado(type, size);
        }

        Politico[] data = new Politico[size];
        for (int i = 0; i < size; i++) {
            data[i] = new Politico(
                    generarDineroAleatorio(),
                    generarFechaAleatoria()
            );
        }
        return data;
    }

    private int generarDineroAleatorio() {
        return ThreadLocalRandom.current().nextInt(MIN_DINERO, MAX_DINERO + 1);
    }

    private LocalDate generarFechaAleatoria() {
        int anosAtras = ThreadLocalRandom.current().nextInt(MIN_ANIOS, MAX_ANIOS + 1);
        int mes = ThreadLocalRandom.current().nextInt(1, 13);
        int dia = ThreadLocalRandom.current().nextInt(1, 29); // Evita problemas con días inválidos
        return LocalDate.now()
                .minusYears(anosAtras)
                .withMonth(mes)
                .withDayOfMonth(dia);
    }

    private Politico[] delegarSiNoEsTipoSolicitado(String type, int size) {
        if (nextHandler != null) {
            return nextHandler.generateData(type, size);
        }
        throw new IllegalArgumentException("Tipo no soportado: " + type);
    }
}