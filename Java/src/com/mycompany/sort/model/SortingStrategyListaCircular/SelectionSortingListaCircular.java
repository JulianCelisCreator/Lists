package com.mycompany.sort.SortingStrategyListaCircular;

import com.mycompany.sort.model.SortingStrategy.SortResult;

import java.util.Objects;

public class SelectionSortingListaCircular<T extends Comparable<T>> implements SortingStrategyListaCircular {
    
    @Override
public SortResult Sort(ListaEnlazadaSimpleCircular<T> lista) {
    Objects.requireNonNull(lista, "La lista a ordenar no puede ser null.");

    long iterations = 0;
    double start = System.nanoTime();
    int n = lista.getTamanno();

    if (n <= 1) {
        return new SortResult(iterations, 0);
    }

    Nodo<T> cabeza = lista.getCabeza();

    // Romper temporalmente la circularidad
    Nodo<T> cola = cabeza;
    while (cola.getSiguiente() != cabeza) {
        cola = cola.getSiguiente();
    }
    cola.setSiguiente(null); // Romper la circularidad

    Nodo<T> actual = cabeza;

    while (actual != null) {
        Nodo<T> maximo = actual;
        Nodo<T> siguiente = actual.getSiguiente();

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

    // Restaurar circularidad
    Nodo<T> nuevaCola = cabeza;
    while (nuevaCola.getSiguiente() != null) {
        nuevaCola = nuevaCola.getSiguiente();
    }
    nuevaCola.setSiguiente(cabeza); // Restaurar enlace circular

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
