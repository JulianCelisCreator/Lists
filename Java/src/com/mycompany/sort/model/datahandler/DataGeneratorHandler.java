package com.mycompany.sort.model.datahandler;

import com.mycompany.sort.model.politico.Politico;

/**
 * Clase base abstracta para los generadores de datos de tipo {@link Politico}.
 *
 * Implementa el patrón de diseño Chain of Responsibility, permitiendo encadenar
 * múltiples generadores de datos que manejan distintos tipos de ordenamiento
 * (por ejemplo: ordenado, inverso, aleatorio).
 */
public abstract class DataGeneratorHandler {
    /**
     * Referencia al siguiente manejador en la cadena.
     */
    protected DataGeneratorHandler nextHandler;

    /**
     * Establece el siguiente manejador en la cadena.
     *
     * @param nextHandler el siguiente {@link DataGeneratorHandler} en la cadena.
     */
    public void setNextHandler(DataGeneratorHandler nextHandler) {
        this.nextHandler = nextHandler;
    }

    /**
     * Método abstracto que cada subclase debe implementar para generar datos
     * del tipo especificado.
     *
     * @param type el tipo de ordenamiento deseado ("SORTED", "INVERSE", "RANDOM", etc.).
     * @param size el tamaño del arreglo a generar.
     * @return un arreglo de objetos {@link Politico} en el orden especificado.
     */
    public abstract Politico[] generateData(String type, int size);
}
