package com.mycompany.sort.model.SortingStrategyListaCircular;

import com.mycompany.sort.model.SortingStrategy.SortResult;

import com.mycompany.sort.model.politico.ListaEnlazadaSimpleCircular;

/**
 * Interfaz que define el contrato para estrategias de ordenamiento aplicadas a listas enlazadas simples circulares de {@link Politico}.
 */

public interface SortingStrategyListaCircular<T> {
    
    /**
     * Ordena la lista enlazada simple circular de políticos.
     *
     * @param lista       lista enlazada simple circular de políticos a ordenar
     * @return un {@link SortResult} que contiene información sobre el rendimiento del algoritmo
     */

    SortResult sort(ListaEnlazadaSimpleCircular<T> lista);

    /**
     * Devuelve el nombre del algoritmo de ordenamiento.
     *
     * @return nombre legible del algoritmo
     */

    String getName();


}
