package com.mycompany.sort.SortingStrategyListaEnlazadaDoble;

import com.mycompany.sort.model.SortingStrategy.SortResult;

import java.util.Objects;

public class MergeSortingListaEnlazadaDoble<T extends Comparable<T>> implements SortingStrategyEnlazadaDoble {
    
    private long iterations;

    @Override
public SortResult sort(ListaEnlazadaDoble<T> lista) {
    Objects.requireNonNull(lista, "La lista a ordenar no puede ser null.");

    iterations = 0;
    double start = System.nanoTime();
    int n = lista.getTamanno();
    if (n <= 1) {
        return new SortResult(iterations, 0);
    }

    NodoDoble<T> nuevaCabeza = mergeSortRecursivo(lista.getCabeza());

    // Reestablecer los punteros anteriores correctamente
    NodoDoble<T> actual = nuevaCabeza;
    NodoDoble<T> anterior = null;
    while (actual != null) {
        actual.setAnterior(anterior);
        anterior = actual;
        actual = actual.getSiguiente();
    }

    lista.setCabeza(nuevaCabeza);
    double elapsedMillis = (System.nanoTime() - start) / 1_000_000;

    return new SortResult(iterations, elapsedMillis);
}

private NodoDoble<T> mergeSortRecursivo(NodoDoble<T> cabeza) {
    if (cabeza == null || cabeza.getSiguiente() == null) {
        return cabeza;
    }

    NodoDoble<T> medio = encontrarMedio(cabeza);
    NodoDoble<T> derecha = medio.getSiguiente();
    medio.setSiguiente(null);
    if (derecha != null) {
        derecha.setAnterior(null);
    }

    NodoDoble<T> izquierdaOrdenada = mergeSortRecursivo(cabeza);
    NodoDoble<T> derechaOrdenada = mergeSortRecursivo(derecha);

    return fusionar(izquierdaOrdenada, derechaOrdenada);
}

private NodoDoble<T> encontrarMedio(NodoDoble<T> cabeza) {
    NodoDoble<T> lento = cabeza;
    NodoDoble<T> rapido = cabeza.getSiguiente();

    while (rapido != null && rapido.getSiguiente() != null) {
        lento = lento.getSiguiente();
        rapido = rapido.getSiguiente().getSiguiente();
    }
    return lento;
}

private NodoDoble<T> fusionar(NodoDoble<T> izquierda, NodoDoble<T> derecha) {
    if (izquierda == null) return derecha;
    if (derecha == null) return izquierda;

    NodoDoble<T> cabeza;

    if (izquierda.getDato().compareTo(derecha.getDato()) >= 0) {
        cabeza = izquierda;
        cabeza.setSiguiente(fusionar(izquierda.getSiguiente(), derecha));
        if (cabeza.getSiguiente() != null) {
            cabeza.getSiguiente().setAnterior(cabeza);
        }
    } else {
        cabeza = derecha;
        cabeza.setSiguiente(fusionar(izquierda, derecha.getSiguiente()));
        if (cabeza.getSiguiente() != null) {
            cabeza.getSiguiente().setAnterior(cabeza);
        }
    }

    return cabeza;
}

/**
     * Devuelve el nombre legible del algoritmo de ordenamiento.
     *
     * @return el nombre "Merge Sorting"
     */
    @Override
    public String getName() {
        return "Merge Sorting lista enlazada doble";
    }

}
