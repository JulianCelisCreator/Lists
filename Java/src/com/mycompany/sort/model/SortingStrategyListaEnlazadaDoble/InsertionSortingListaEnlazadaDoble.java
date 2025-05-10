package com.mycompany.sort.model.SortingStrategyListaEnlazadaDoble;

import com.mycompany.sort.model.SortingStrategy.SortResult;

import java.util.Objects;

public class InsertionSortingListaEnlazadaDoble<T extends Comparable<T>> implements SortingStrategyEnlazadaDoble {
 
    @Override
public SortResult sort(ListaEnlazadaDoble<T> lista) {
    Objects.requireNonNull(lista, "La lista a ordenar no puede ser null.");

    long iterations = 0;
    double start = System.nanoTime();
    int n = lista.getTamanno();
    if (n <= 1) {
        return new SortResult(iterations, 0);
    }

    NodoDoble<T> cabezaOriginal = lista.getCabeza();
    NodoDoble<T> nodoActual = cabezaOriginal.getSiguiente();
    cabezaOriginal.setAnterior(null);
    cabezaOriginal.setSiguiente(null);

    while (nodoActual != null) {
        NodoDoble<T> siguiente = nodoActual.getSiguiente();
        nodoActual.setAnterior(null);
        nodoActual.setSiguiente(null);

        insertarEnOrden(nodoActual, lista);

        nodoActual = siguiente;
        iterations++;
    }

    double elapsedMillis = (System.nanoTime() - start) / 1_000_000;
    return new SortResult(iterations, elapsedMillis);
}

private void insertarEnOrden(NodoDoble<T> nodoAInsertar, ListaEnlazadaDoble<T> lista) {
    NodoDoble<T> cabeza = lista.getCabeza();

    if (cabeza == null || cabeza.getDato().compareTo(nodoAInsertar.getDato()) <= 0) {
        // Insertar al inicio
        nodoAInsertar.setSiguiente(cabeza);
        if (cabeza != null) {
            cabeza.setAnterior(nodoAInsertar);
        }
        lista.setCabeza(nodoAInsertar);
        return;
    }

    NodoDoble<T> actual = cabeza;

    while (actual.getSiguiente() != null && 
           actual.getSiguiente().getDato().compareTo(nodoAInsertar.getDato()) > 0) {
        actual = actual.getSiguiente();
    }

    NodoDoble<T> siguiente = actual.getSiguiente();
    actual.setSiguiente(nodoAInsertar);
    nodoAInsertar.setAnterior(actual);

    if (siguiente != null) {
        nodoAInsertar.setSiguiente(siguiente);
        siguiente.setAnterior(nodoAInsertar);
    }
}

    /**
     * Devuelve el nombre legible del algoritmo de ordenamiento.
     *
     * @return el nombre "Insertion Sort"
     */
    @Override
    public String getName() {
        return "Insertion Sort lista enlazada doble";
    }
    
}
