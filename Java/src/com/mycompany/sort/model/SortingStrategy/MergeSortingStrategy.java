package com.mycompany.sort.model.SortingStrategy;

import com.mycompany.sort.model.politico.Politico;
import com.mycompany.sort.model.politico.Nodo;
import com.mycompany.sort.model.politico.ListaEnlazadaSimple;

import java.util.Objects;

/**
 * Implementación del algoritmo Merge Sort para ordenar arreglos de {@link Politico}.
 * Este algoritmo utiliza el enfoque de divide y vencerás para ordenar eficientemente los datos.
 */
public class MergeSortingStrategy<T extends Comparable<T>> implements SortingStrategy<T> {

    private long iterations;

    /**
     * Ordena una lista enlazada simple de objetos {@link Politico} usando Merge Sort
     *
     * @param lista         la lista enlazada simple de políticos a ordenar
     * @return objeto {@link SortResult} con métricas de rendimiento
     */
    @Override
    public SortResult sort(ListaEnlazadaSimple<T> lista) {
        Objects.requireNonNull(lista, "La lista a ordenar no puede ser null.");

        iterations = 0;
        double start = System.nanoTime();
        int n = lista.getTamanno();
        if (n <= 1) {
            return new SortResult(iterations, 0); 
        }

        Nodo<T> nuevaCabeza = mergeSortRecursivo(lista.getCabeza());

        lista.setCabeza(nuevaCabeza);
        double end = System.nanoTime() - start;
        double elapsedMillis = end / 1_000_000;

        return new SortResult(iterations, elapsedMillis);
    }

    /**
 * Realiza el algoritmo de ordenamiento Merge Sort de manera recursiva sobre una lista enlazada.
 * Divide la lista en dos mitades y las ordena de forma recursiva. Luego fusiona las dos mitades ordenadas.
 *
 * @param cabeza El nodo cabeza de la lista a ordenar.
 * @return El nodo cabeza de la lista ordenada.
 */

    private Nodo<T> mergeSortRecursivo(Nodo<T> cabeza) {
        if (cabeza == null || cabeza.getSiguiente() == null) {
            return cabeza;
        }

        Nodo<T> medio = encontrarMedio(cabeza);
        Nodo<T> segundaMitadCabeza = medio.getSiguiente();
        medio.setSiguiente(null);

        Nodo<T> izquierdaOrdenada = mergeSortRecursivo(cabeza);
        Nodo<T> derechaOrdenada = mergeSortRecursivo(segundaMitadCabeza);

        return fusionar(izquierdaOrdenada, derechaOrdenada);
    }

    /**
 * Encuentra el nodo del medio de la lista enlazada.
 * Utiliza el enfoque de dos punteros, uno lento que avanza de uno en uno y otro rápido que avanza de dos en dos.
 * Cuando el puntero rápido llega al final, el puntero lento estará en el medio.
 *
 * @param cabeza El nodo cabeza de la lista en la que se busca el medio.
 * @return El nodo que se encuentra en el medio de la lista.
 */

    private Nodo<T> encontrarMedio(Nodo<T> cabeza) {
        Nodo<T> lento = cabeza;
        Nodo<T> rapido = cabeza.getSiguiente();

        while (rapido != null && rapido.getSiguiente() != null) {
            lento = lento.getSiguiente();
            rapido = rapido.getSiguiente().getSiguiente();
        }
        return lento;
    }

    /**
 * Fusiona dos sublistas ordenadas en una sola lista ordenada.
 * Compara los elementos de ambas listas y los combina en el orden adecuado.
 *
 * @param izquierda La primera lista ordenada.
 * @param derecha La segunda lista ordenada.
 * @return El nodo cabeza de la lista fusionada y ordenada.
 */

    private Nodo<T> fusionar(Nodo<T> izquierda, Nodo<T> derecha) {
        if (izquierda == null) {
            return derecha;
        }
        if (derecha == null) {
            return izquierda;
        }

        Nodo<T> cabezaResultado;

        if (izquierda.getDato().compareTo(derecha.getDato()) >= 0) {
            cabezaResultado = izquierda;
            cabezaResultado.setSiguiente(fusionar(izquierda.getSiguiente(), derecha));
        } else {
            cabezaResultado = derecha;
            cabezaResultado.setSiguiente(fusionar(izquierda, derecha.getSiguiente()));
        }
        return cabezaResultado;
    }

    /**
     * Devuelve el nombre legible del algoritmo de ordenamiento.
     *
     * @return el nombre "Merge Sorting"
     */
    @Override
    public String getName() {
        return "Merge Sorting lista enlazada simple";
    }
}
