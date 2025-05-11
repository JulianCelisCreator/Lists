package com.mycompany.sort.model.datachain;

import com.mycompany.sort.model.datahandler.*;
import com.mycompany.sort.model.politico.Politico;

/**
 * Clase encargada de gestionar la cadena de responsabilidad para la generación
 * de datos de tipo {@link Politico} en distintos órdenes: ordenado, inverso y aleatorio.
 *
 * Utiliza una serie de {@link DataGeneratorHandler} conectados entre sí,
 * siguiendo el patrón de diseño "Chain of Responsibility".
 */
public class DataGeneratorChain {
    private DataGeneratorHandler chain;

    /**
     * Constructor que inicializa la cadena de generación de datos en el siguiente orden:
     * Ordenado → Inverso → Aleatorio.
     */
    public DataGeneratorChain() {
        // Configurar la cadena correctamente: Sorted -> Reverse -> Random
        DataGeneratorHandler sorted = new SortedOrderHandler();
        DataGeneratorHandler reverse = new ReverseOrderHandler();
        DataGeneratorHandler random = new RandomOrderHandler();

        sorted.setNextHandler(reverse);
        reverse.setNextHandler(random);

        this.chain = sorted; // La cadena empieza con "sorted"
    }

    /**
     * Genera un arreglo de objetos {@link Politico} con el orden especificado.
     *
     * @param type Tipo de orden a generar: "SORTED", "INVERSE" o "RANDOM".
     * @param size Tamaño del arreglo a generar.
     * @return Arreglo de {@link Politico} en el orden solicitado.
     */
    public Politico[] generateData(String type, int size) {
        return chain.generateData(type, size);
    }
}
