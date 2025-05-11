package com.mycompany.sort.model.SortingStrategyListaEnlazadaDoble;

import com.mycompany.sort.model.SortingStrategy.SortResult;
import com.mycompany.sort.model.politico.ListaEnlazadaDoble;
import com.mycompany.sort.model.politico.NodoDoble;

import java.util.Objects;

/**
 * Implementación del algoritmo Merge Sort para ordenar listas enlazadas dobles de {@link Politico}.
 * Este algoritmo utiliza el enfoque de divide y vencerás para ordenar eficientemente los datos.
 */

public class MergeSortingListaEnlazadaDoble<T extends Comparable<T>> implements SortingStrategyEnlazadaDoble<T> {
    
    private long iterations;

    /**
 * Ordena una lista doblemente enlazada utilizando el algoritmo Merge Sort de forma recursiva.
 *
 * @param lista La lista doblemente enlazada a ordenar.
 * @return Un objeto {@link SortResult} que contiene el número de iteraciones y el tiempo transcurrido en milisegundos.
 * @throws NullPointerException Si la lista proporcionada es null.
 */
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

/**
 * Implementa recursivamente el algoritmo Merge Sort sobre una lista doblemente enlazada.
 * Divide la lista en dos mitades, ordena cada mitad y las fusiona de manera ordenada.
 *
 * @param cabeza El nodo cabeza de la sublista a ordenar.
 * @return El nodo cabeza de la sublista ordenada.
 */

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

/**
 * Encuentra el nodo del medio de una lista doblemente enlazada.
 * Utiliza el algoritmo de dos punteros (lento y rápido) para lograrlo.
 *
 * @param cabeza El nodo inicial de la lista.
 * @return El nodo medio de la lista.
 */

private NodoDoble<T> encontrarMedio(NodoDoble<T> cabeza) {
    NodoDoble<T> lento = cabeza;
    NodoDoble<T> rapido = cabeza.getSiguiente();

    while (rapido != null && rapido.getSiguiente() != null) {
        lento = lento.getSiguiente();
        rapido = rapido.getSiguiente().getSiguiente();
    }
    return lento;
}

/**
 * Fusiona dos listas doblemente enlazadas ordenadas en una sola lista también ordenada.
 * Preserva los punteros tanto hacia adelante como hacia atrás entre los nodos.
 *
 * @param izquierda La cabeza de la primera lista ordenada.
 * @param derecha La cabeza de la segunda lista ordenada.
 * @return La cabeza de la lista resultante ordenada.
 */

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
