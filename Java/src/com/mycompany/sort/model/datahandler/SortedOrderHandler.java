package com.mycompany.sort.model.datahandler;

import com.mycompany.sort.model.politico.Politico;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Comparator;

/**
 * Manejador concreto de la cadena de generación de datos que genera un arreglo
 * de objetos {@link Politico} ordenados ascendentemente por dinero.
 *
 * Forma parte del patrón Chain of Responsibility para generar datos de prueba
 * con diferentes características.
 */
public class SortedOrderHandler extends DataGeneratorHandler {

    /**
     * Genera un arreglo de políticos ordenados de forma ascendente por la cantidad de dinero.
     *
     * Si el tipo solicitado no es "SORTED", delega la generación al siguiente manejador
     * en la cadena.
     *
     * @param type Tipo de orden solicitado (por ejemplo, "SORTED", "INVERSE", "RANDOM")
     * @param size Tamaño del arreglo a generar
     * @return     Un arreglo de políticos ordenados o delegación al siguiente handler
     * @throws IllegalArgumentException si el tipo no es reconocido y no hay siguiente handler
     */
    @Override
    public Politico[] generateData(String type, int size) {
        if ("SORTED".equalsIgnoreCase(type)) {
            Politico[] data = new Politico[size];
            for (int i = 0; i < size; i++) {
                data[i] = new Politico(i * 1000, LocalDate.now().minusYears(i % 50));
            }
            Arrays.sort(data, Comparator.comparingInt(Politico::getDinero));
            return data;
        } else if (nextHandler != null) {
            return nextHandler.generateData(type, size);
        } else {
            throw new IllegalArgumentException("Tipo no soportado: " + type);
        }
    }
}
