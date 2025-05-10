package com.mycompany.sort.model.datahandler;

import java.time.LocalDate;
import java.util.Random;

/**
 * Clase utilitaria encargada de generar fechas aleatorias.
 *
 * La fecha generada estará comprendida entre hace 100 años y hace 20 años desde la fecha actual,
 * con un día y mes válidos (mes entre 1-12, día entre 1-28 para evitar fechas inválidas).
 */
public class RandomDateGenerator {

    /**
     * Genera una fecha aleatoria válida entre 20 y 100 años en el pasado desde la fecha actual.
     *
     * @return una fecha aleatoria como {@link LocalDate}.
     */
    public static LocalDate generateRandomDate() {
        Random rand = new Random();

        // Año actual
        int currentYear = LocalDate.now().getYear();

        // Rango: desde hace 100 años hasta hace 20 años
        int minYear = currentYear - 100;
        int maxYear = currentYear - 20;

        // Generar año aleatorio dentro del rango
        int randomYear = rand.nextInt(maxYear - minYear) + minYear;

        // Generar mes entre 1 y 12
        int randomMonth = rand.nextInt(12) + 1;

        // Generar día entre 1 y 28 para evitar fechas inválidas (como 30/Feb)
        int randomDay = rand.nextInt(28) + 1;

        return LocalDate.of(randomYear, randomMonth, randomDay);
    }
}
