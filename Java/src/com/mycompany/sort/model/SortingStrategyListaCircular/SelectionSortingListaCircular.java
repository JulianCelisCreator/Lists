package com.mycompany.sort.model.SortingStrategyListaCircular;

import com.mycompany.sort.model.SortingStrategy.SortResult;
import com.mycompany.sort.model.politico.ListaEnlazadaSimpleCircular;
import com.mycompany.sort.model.politico.Nodo;

import java.util.Objects;

/**
 * Estrategia de ordenamiento que implementa el algoritmo Selection Sort.
 * Ordena una lista Enlazada simple de objetos {@link Politico}
 */

public class SelectionSortingListaCircular<T extends Comparable<T>> implements SortingStrategyListaCircular<T> {
    
    /**
     * Ordena la lista enlazada simple de politicos utilizando Selection Sort.
     *
     * @param lista        la lista enlazada simple de políticos a ordenar
     * @return un objeto {@link SortResult} que contiene las estadísticas del ordenamiento
     */

    @Override
public SortResult sort(ListaEnlazadaSimpleCircular<T> lista) {
    Objects.requireNonNull(lista, "La lista a ordenar no puede ser null.");

    long iterations = 0;
    double start = System.nanoTime();

    int n = lista.getTamanno();
    if (n <= 1) {
        return new SortResult(iterations, 0);
    }

    Nodo<T> cabeza = lista.getCabeza();
    Nodo<T> actual = cabeza;

    for (int i = 0; i < n - 1; i++) {
        Nodo<T> minimo = actual;
        Nodo<T> siguiente = actual.getSiguiente();

        for (int j = i + 1; j < n; j++) {
            iterations++;
            if (siguiente.getDato().compareTo(minimo.getDato()) > 0) { 
                minimo = siguiente;
            }
            siguiente = siguiente.getSiguiente();
        }

        if (minimo != actual) {
            T temp = actual.getDato();
            actual.setDato(minimo.getDato());
            minimo.setDato(temp);
        }

        actual = actual.getSiguiente();
    }

    lista.setCabeza(cabeza);

    double elapsedMillis = (System.nanoTime() - start) / 1_000_000;
    return new SortResult(iterations, elapsedMillis);
}


/**
     * Retorna el nombre legible del algoritmo.
     *
     * @return "Selection sort"
     */
    @Override
    public String getName() {
        return "Selection sort lista enlazada simple circular";
    }

}
