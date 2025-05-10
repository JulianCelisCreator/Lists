package com.mycompany.sort.model.SortingStrategy;

import com.mycompany.sort.model.politico.Politico;

import java.util.Comparator;

/**
 * Interfaz que define el contrato para estrategias de ordenamiento aplicadas a listas enlazadas simples de {@link Politico}.
 */
public interface SortingStrategy<T> {

    /**
     * Ordena la lista enlazada simple de políticos.
     *
     * @param data       lista enlazada simple de políticos a ordenar
     * @return un {@link SortResult} que contiene información sobre el rendimiento del algoritmo
     */
    SortResult sort(ListaEnlazadaSimple<T> lista);

    /**
     * Devuelve el nombre del algoritmo de ordenamiento.
     *
     * @return nombre legible del algoritmo
     */
    String getName();
}
