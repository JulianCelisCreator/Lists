package com.mycompany.sort.model.SortingStrategyListaEnlazadaDoble;

import com.mycompany.sort.model.SortingStrategy.SortResult;
import com.mycompany.sort.model.politico.ListaEnlazadaDoble;
import com.mycompany.sort.model.politico.NodoDoble;

import java.util.Objects;

/**
 * Implementación del algoritmo Quick Sort para ordenar listas enlazadas simples de {@link Politico}.
 * Utiliza una pila para evitar recursión y selecciona el pivote usando la técnica de mediana de tres.
 */

public class QuickSortingListaEnlazadaDoble<T extends Comparable<T>> implements SortingStrategyEnlazadaDoble<T> {
    
    private long iterations;

/**
 * Ordena una lista doblemente enlazada utilizando el algoritmo QuickSort.
 * La lista se ordena en orden descendente, modificando los nodos en su lugar.
 *
 * @param lista Lista doblemente enlazada a ordenar.
 * @return Un objeto {@link SortResult} con el número de iteraciones y tiempo de ejecución en milisegundos.
 * @throws NullPointerException si la lista es null.
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

    NodoDoble<T> cabeza = lista.getCabeza();
    NodoDoble<T> cola = encontrarCola(cabeza); 
    quickSortRecursivo(cabeza, cola);

    lista.setCabeza(cabeza);
    double elapsedMillis = (System.nanoTime() - start) / 1_000_000;
    return new SortResult(iterations, elapsedMillis);
}

/**
 * Encuentra el último nodo (cola) de una lista doblemente enlazada.
 *
 * @param nodo Nodo inicial de la lista.
 * @return El último nodo de la lista, o null si la lista está vacía.
 */

private NodoDoble<T> encontrarCola(NodoDoble<T> nodo) {
    if (nodo == null) return null;
    while (nodo.getSiguiente() != null) {
        nodo = nodo.getSiguiente();
    }
    return nodo;
}

/**
 * Aplica recursivamente el algoritmo QuickSort sobre una sublista de nodos dobles.
 *
 * @param cabezaSubLista Nodo inicial de la sublista.
 * @param colaSubLista Nodo final de la sublista.
 */

private void quickSortRecursivo(NodoDoble<T> cabezaSubLista, NodoDoble<T> colaSubLista) {
    if (cabezaSubLista == null || colaSubLista == null || cabezaSubLista == colaSubLista || cabezaSubLista == colaSubLista.getSiguiente()) {
        return;
    }

    NodoDoble<T>[] resultadoParticion = particionar(cabezaSubLista, colaSubLista);
    NodoDoble<T> nodoPivoteFinal = resultadoParticion[0];
    NodoDoble<T> nodoAntesPivote = resultadoParticion[1];

    if (nodoAntesPivote != null && nodoPivoteFinal != cabezaSubLista) {
        quickSortRecursivo(cabezaSubLista, nodoAntesPivote);
    }

    if (nodoPivoteFinal != null && nodoPivoteFinal != colaSubLista) {
        quickSortRecursivo(nodoPivoteFinal.getSiguiente(), colaSubLista);
    }
}

/**
 * Realiza la partición de una sublista para QuickSort usando el último nodo como pivote.
 * Coloca los elementos mayores al pivote antes de él, y los menores después (orden descendente).
 *
 * @param cabeza Nodo inicial de la sublista.
 * @param cola Nodo final de la sublista (pivote).
 * @return Un arreglo con dos nodos:
 *         [0] Nodo pivote luego de su ubicación final.
 *         [1] Nodo anterior al pivote (para dividir la sublista izquierda).
 */

private NodoDoble<T>[] particionar(NodoDoble<T> cabeza, NodoDoble<T> cola) {
    T valorPivote = cola.getDato();

    NodoDoble<T> i = null;
    NodoDoble<T> actual = cabeza;

    while (actual != cola) {
        if (actual.getDato().compareTo(valorPivote) > 0) {
            i = (i == null) ? cabeza : i.getSiguiente();
            T temp = actual.getDato();
            actual.setDato(i.getDato());
            i.setDato(temp);
        }
        actual = actual.getSiguiente();
    }

    i = (i == null) ? cabeza : i.getSiguiente();
    T temp = cola.getDato();
    cola.setDato(i.getDato());
    i.setDato(temp);

    NodoDoble<T> nodoAntesPivote = i.getAnterior();

    @SuppressWarnings("unchecked")
    NodoDoble<T>[] resultado = (NodoDoble<T>[]) new NodoDoble<?>[2];
    resultado[0] = i;
    resultado[1] = nodoAntesPivote;
    return resultado;
}

    /**
     * Retorna el nombre legible del algoritmo.
     *
     * @return el nombre "Quick Sort"
     */
    @Override
    public String getName() {
        return "Quick Sort lista enlazada doble";
    }

}
