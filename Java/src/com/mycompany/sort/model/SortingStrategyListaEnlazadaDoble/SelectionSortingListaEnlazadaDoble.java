package com.mycompany.sort.SortingStrategyListaEnlazadaDoble;

import com.mycompany.sort.model.SortingStrategy.SortResult;

import java.util.Objects;

public class SelectionSortingListaEnlazadaDoble<T extends Comparable<T>> implements SortingStrategyEnlazadaDoble {

    @Override
public SortResult sort(ListaEnlazadaDoble<T> lista) {
    Objects.requireNonNull(lista, "La lista a ordenar no puede ser null.");

    long iterations = 0;
    double start = System.nanoTime();
    int n = lista.getTamanno();
    if (n <= 1) {
        return new SortResult(iterations, 0);
    }

    NodoDoble<T> actual = lista.getCabeza();

    while (actual != null) {
        NodoDoble<T> maximo = actual;
        NodoDoble<T> siguiente = actual.getSiguiente();

        while (siguiente != null) {
            iterations++;
            if (siguiente.getDato().compareTo(maximo.getDato()) > 0) {
                maximo = siguiente;
            }
            siguiente = siguiente.getSiguiente();
        }

        if (maximo != actual) {
            T temp = actual.getDato();
            actual.setDato(maximo.getDato());
            maximo.setDato(temp);
        }

        actual = actual.getSiguiente();
    }

    lista.setCabeza(lista.getCabeza());
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
        return "Selection sort lista enlazada doble";
    }

}
