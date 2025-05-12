package com.mycompany.sort.model.SortingStrategy;

import com.mycompany.sort.model.ResultadoOrdenamiento;
import com.mycompany.sort.model.politico.ListaEnlazadaSimple;
import com.mycompany.sort.model.politico.Nodo;

import java.util.Objects;

/**
 * Implementación del algoritmo Selection Sort para listas enlazadas simples.
 */
public class SelectionSortingStrategy<T extends Comparable<T>> implements SortingStrategy<T> {

    @Override
    public ResultadoOrdenamiento sort(ListaEnlazadaSimple<T> lista) {
        Objects.requireNonNull(lista, "La lista a ordenar no puede ser null");

        long startTime = System.nanoTime();
        int iterations = 0;

        if (lista.getTamanno() <= 1) {
            return new ResultadoOrdenamiento(0, 0);
        }

        Nodo<T> actual = lista.getCabeza();

        while (actual != null) {
            Nodo<T> minimo = actual;
            Nodo<T> siguiente = actual.getSiguiente();

            while (siguiente != null) {
                iterations++; // Contamos cada comparación

                if (siguiente.getDato().compareTo(minimo.getDato()) < 0) {
                    minimo = siguiente;
                }
                siguiente = siguiente.getSiguiente();
            }

            // Intercambiar los datos si es necesario
            if (minimo != actual) {
                T temp = actual.getDato();
                actual.setDato(minimo.getDato());
                minimo.setDato(temp);
            }

            actual = actual.getSiguiente();
        }

        double elapsedMillis = (System.nanoTime() - startTime) / 1_000_000.0;
        return new ResultadoOrdenamiento(iterations, elapsedMillis);
    }

    @Override
    public String getName() {
        return "Selection Sort (Lista Enlazada)";
    }
}