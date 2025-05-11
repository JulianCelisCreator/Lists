package com.mycompany.sort.model.SortingStrategyListaCircular;

import com.mycompany.sort.model.SortingStrategy.SortResult;
import com.mycompany.sort.model.politico.ListaEnlazadaSimpleCircular;
import com.mycompany.sort.model.politico.Nodo;

import java.util.Objects;

/**
 * Implementación del algoritmo Merge Sort para ordenar listas enlazadas simples ciruclares de {@link Politico}.
 * Este algoritmo utiliza el enfoque de divide y vencerás para ordenar eficientemente los datos.
 */

public class MergeSortingListaCircular<T extends Comparable<T>> implements SortingStrategyListaCircular<T> {
    
    private long iterations;

    /**
     * Ordena una lista enlazada simple circular de objetos {@link Politico} usando Merge Sort
     *
     * @param lista         la lista enlazada simple circular de políticos a ordenar
     * @return objeto {@link SortResult} con métricas de rendimiento
     */
@Override
public SortResult sort(ListaEnlazadaSimpleCircular<T> lista) {
    Objects.requireNonNull(lista, "La lista a ordenar no puede ser null.");
    this.iterations = 0;
    double start = System.nanoTime();

    int n = lista.getTamanno();
    if (n <= 1) {
        return new SortResult(iterations, 0);
    }

    Nodo<T> cabeza = lista.getCabeza();
    lista.getUltimo().setSiguiente(null);

    Nodo<T> nuevoCabeza = mergeSort(cabeza);

    Nodo<T> nuevoUltimo = nuevoCabeza;
    while (nuevoUltimo.getSiguiente() != null) {
        nuevoUltimo = nuevoUltimo.getSiguiente();
    }
    nuevoUltimo.setSiguiente(nuevoCabeza);
    lista.setCabeza(nuevoCabeza);

    double elapsedMillis = (System.nanoTime() - start) / 1_000_000;
    return new SortResult(iterations, elapsedMillis);
}

/**
 * Ordena una lista enlazada simple circular utilizando el algoritmo de Merge Sort.
 * Este método divide recursivamente la lista en mitades, las ordena y luego
 * las fusiona en una lista ordenada.
 *
 * @param cabeza El nodo cabeza de la lista a ordenar.
 * @return El nodo cabeza de la lista ya ordenada.
 */

private Nodo<T> mergeSort(Nodo<T> cabeza) {
    if (cabeza == null || cabeza.getSiguiente() == null) {
        return cabeza;
    }

    Nodo<T> medio = getMiddle(cabeza);
    Nodo<T> siguienteDeMedio = medio.getSiguiente();
    medio.setSiguiente(null);

    Nodo<T> izquierda = mergeSort(cabeza);
    Nodo<T> derecha = mergeSort(siguienteDeMedio);

    return merge(izquierda, derecha);
}

/**
 * Fusiona dos listas enlazadas ordenadas en una sola lista ordenada.
 * El proceso es estable y se realiza comparando los valores de los nodos de ambas listas.
 *
 * @param izquierda La cabeza de la primera sublista ordenada.
 * @param derecha La cabeza de la segunda sublista ordenada.
 * @return El nodo cabeza de la lista resultante fusionada y ordenada.
 */

private Nodo<T> merge(Nodo<T> izquierda, Nodo<T> derecha) {
    Nodo<T> resultado = null;
    Nodo<T> actual = null;

    while (izquierda != null && derecha != null) {
        this.iterations++;
        Nodo<T> temp;
        if (izquierda.getDato().compareTo(derecha.getDato()) <= 0) {
            temp = izquierda;
            izquierda = izquierda.getSiguiente();
        } else {
            temp = derecha;
            derecha = derecha.getSiguiente();
        }

        temp.setSiguiente(null);
        if (resultado == null) {
            resultado = temp;
            actual = resultado;
        } else {
            actual.setSiguiente(temp);
            actual = actual.getSiguiente();
        }
    }

    if (izquierda != null) {
        actual.setSiguiente(izquierda);
    } else if (derecha != null) {
        actual.setSiguiente(derecha);
    }

    return resultado;
}

/**
 * Encuentra y devuelve el nodo del medio de una lista enlazada simple.
 * Utiliza el algoritmo de los punteros lento y rápido (tortuga y liebre).
 *
 * @param cabeza El nodo cabeza de la lista enlazada.
 * @return El nodo del medio de la lista. Si la lista tiene un número par de nodos,
 *         devuelve el nodo justo antes de la mitad exacta.
 */

private Nodo<T> getMiddle(Nodo<T> cabeza) {
    if (cabeza == null) return null;

    Nodo<T> lenta = cabeza;
    Nodo<T> rapida = cabeza.getSiguiente();

    while (rapida != null && rapida.getSiguiente() != null) {
        lenta = lenta.getSiguiente();
        rapida = rapida.getSiguiente().getSiguiente();
    }

    return lenta;
}

    /**
     * Devuelve el nombre legible del algoritmo de ordenamiento.
     *
     * @return el nombre "Merge Sorting"
     */
    @Override
    public String getName() {
        return "Merge Sorting lista enlazada simple circular";
    }

}
