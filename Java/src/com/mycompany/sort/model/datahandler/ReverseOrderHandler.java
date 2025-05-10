package com.mycompany.sort.model.datahandler;

import com.mycompany.sort.model.politico.Politico;

import java.util.Arrays;
import java.util.Collections;

/**
 * Manejador concreto de la cadena de generación de datos que genera un arreglo
 * de objetos {@link Politico} ordenado de forma inversa.
 *
 * Si el tipo de dato solicitado no es "INVERSE", delega la generación al siguiente
 * manejador en la cadena.
 */
public class ReverseOrderHandler extends DataGeneratorHandler {

    /**
     * Genera un arreglo de políticos con orden descendente si el tipo solicitado es "INVERSE".
     * Internamente utiliza el {@link SortedOrderHandler} para generar el orden ascendente
     * y luego invierte el arreglo.
     *
     * @param type Tipo de orden solicitado (ej. "INVERSE", "SORTED", "RANDOM")
     * @param size Tamaño del arreglo a generar
     * @return     Un arreglo de políticos ordenados de forma inversa o delegación al siguiente handler
     */
    @Override
    public Politico[] generateData(String type, int size) {
        if ("INVERSE".equalsIgnoreCase(type)) {
            // Generar en orden ascendente y luego invertir
            Politico[] data = new SortedOrderHandler().generateData("SORTED", size);
            Collections.reverse(Arrays.asList(data));
            return data;
        }

        // Delegar al siguiente manejador
        if (nextHandler != null) {
            return nextHandler.generateData(type, size);
        }

        // Tipo no reconocido
        throw new IllegalArgumentException("Tipo no soportado: " + type);
    }
}
