package com.mycompany.sort.model.SortingStrategyListaCircular;

import com.mycompany.sort.model.SortingStrategy.SortResult;
import com.mycompany.sort.model.politico.ListaEnlazadaSimpleCircular;
import com.mycompany.sort.model.politico.Nodo;

import java.util.Objects;

/**
 * Implementación del algoritmo Quick Sort para ordenar lista simple circular de {@link Politico}.
 * Utiliza una pila para evitar recursión y selecciona el pivote usando la técnica de mediana de tres.
 */

public class QuickSortingListaCircular<T extends Comparable<T>> implements SortingStrategyListaCircular<T>{
    
    private long iterations;

    /**
     * Ordena una lista enlazada simple circular de objetos {@link Politico} utilizando Quick Sort de forma iterativa.
     *
     * @param lista        la lista enlazada simple circular de políticos a ordenar
     * @return un objeto {@link SortResult} con estadísticas de rendimiento
     */
    @Override
public SortResult sort(ListaEnlazadaSimpleCircular<T> lista) {
    Objects.requireNonNull(lista, "La lista a ordenar no puede ser null.");
    this.iterations = 0;
    double start = System.nanoTime();

    if (lista.getTamanno() <= 1) {
        return new SortResult(iterations, 0);
    }

    Nodo<T> cabeza = lista.getCabeza();
    lista.getUltimo().setSiguiente(null); 

    Nodo<T> nuevaCabeza = quickSort(cabeza, null);

    Nodo<T> nuevoUltimo = nuevaCabeza;
    while (nuevoUltimo.getSiguiente() != null) {
        nuevoUltimo = nuevoUltimo.getSiguiente();
    }
    nuevoUltimo.setSiguiente(nuevaCabeza);
    lista.setCabeza(nuevaCabeza);

    double elapsedMillis = (System.nanoTime() - start) / 1_000_000;
    return new SortResult(iterations, elapsedMillis);
}

/**
 * Ordena recursivamente una sublista de nodos enlazados usando el algoritmo de Quick Sort.
 * La partición se realiza en torno al nodo inicial como pivote, reordenando nodos
 * menores al pivote a la izquierda y mayores a la derecha.
 *
 * @param inicio El nodo inicial (inclusive) de la sublista a ordenar.
 * @param fin El nodo final (exclusivo) de la sublista a ordenar. Puede ser null para indicar el fin de la lista.
 * @return El nodo cabeza de la sublista ordenada. El nodo original puede o no mantenerse como cabeza después del ordenamiento.
 */

private Nodo<T> quickSort(Nodo<T> inicio, Nodo<T> fin) {
    if (inicio == fin || inicio == null || inicio.getSiguiente() == fin) {
        return inicio;
    }

    Nodo<T> pivot = inicio;
    Nodo<T> i = inicio;
    Nodo<T> j = inicio.getSiguiente();

    while (j != fin) {
        this.iterations++;
        if (j.getDato().compareTo(pivot.getDato()) < 0) { // ASCENDENTE
            i = i.getSiguiente();
            swap(i, j);
        }
        j = j.getSiguiente();
    }

    swap(i, pivot);

    Nodo<T> cabezaIzquierda = quickSort(inicio, i);
    Nodo<T> cabezaDerecha = quickSort(i.getSiguiente(), fin);

    return cabezaIzquierda;
}

/**
 * Intercambia los valores almacenados en dos nodos de una lista enlazada.
 *
 * @param a El primer nodo.
 * @param b El segundo nodo.
 */

private void swap(Nodo<T> a, Nodo<T> b) {
    T temp = a.getDato();
    a.setDato(b.getDato());
    b.setDato(temp);
}

    /**
     * Retorna el nombre legible del algoritmo.
     *
     * @return el nombre "Quick Sort"
     */
    @Override
    public String getName() {
        return "Quick Sort lista enlazada simple circular";
    }

}
