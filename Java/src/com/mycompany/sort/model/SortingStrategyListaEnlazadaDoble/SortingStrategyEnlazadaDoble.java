package com.mycompany.sort.model.SortingStrategyListaEnlazadaDoble;

import com.mycompany.sort.model.SortingStrategy.SortResult;

import com.mycompany.sort.model.politico.ListaEnlazadaDoble;

/**
 * Interfaz que define el contrato para estrategias de ordenamiento aplicadas a listas enlazadas dobles de {@link Politico}.
 */

public interface SortingStrategyEnlazadaDoble<T> {
    
    /**
     * Ordena la lista enlazada doble de políticos.
     *
     * @param lista       lista enlazada doble de políticos a ordenar
     * @return un {@link SortResult} que contiene información sobre el rendimiento del algoritmo
     */

    SortResult sort(ListaEnlazadaDoble<T> lista);

    /**
     * Devuelve el nombre del algoritmo de ordenamiento.
     *
     * @return nombre legible del algoritmo
     */

    String getName();

}
