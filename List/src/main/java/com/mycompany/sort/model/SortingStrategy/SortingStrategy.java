package com.mycompany.sort.model.SortingStrategy;

import com.mycompany.sort.model.ResultadoOrdenamiento;
import com.mycompany.sort.model.politico.Politico;

import com.mycompany.sort.model.politico.ListaEnlazadaSimple;

/**
 * Interfaz que define el contrato para estrategias de ordenamiento aplicadas a listas enlazadas simples de {@link Politico}.
 */
public interface SortingStrategy<T> {

    /**
     * Ordena la lista enlazada simple de políticos.
     *
     * @param lista       lista enlazada simple de políticos a ordenar
     * @return un {@link ResultadoOrdenamiento} que contiene información sobre el rendimiento del algoritmo
     */
    ResultadoOrdenamiento sort(ListaEnlazadaSimple<T> lista);

    /**
     * Devuelve el nombre del algoritmo de ordenamiento.
     *
     * @return nombre legible del algoritmo
     */
    String getName();
}
