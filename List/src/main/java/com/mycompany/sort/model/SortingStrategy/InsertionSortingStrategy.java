package com.mycompany.sort.model.SortingStrategy;

import com.mycompany.sort.model.ResultadoOrdenamiento;
import com.mycompany.sort.model.politico.ListaEnlazadaSimple;
import com.mycompany.sort.model.politico.Nodo;
import java.util.Objects;

/**
 * Implementación del algoritmo de ordenamiento Insertion Sort para listas enlazadas simples.
 */
public class InsertionSortingStrategy<T extends Comparable<T>> implements SortingStrategy<T> {

    /**
     * Ordena una lista enlazada simple usando el algoritmo Insertion Sort.
     *
     * @param lista La lista enlazada simple a ordenar
     * @return Objeto ResultadoOrdenamiento con iteraciones y tiempo de ejecución
     */
    @Override
    public ResultadoOrdenamiento sort(ListaEnlazadaSimple<T> lista) {
        Objects.requireNonNull(lista, "La lista a ordenar no puede ser null");

        long startTime = System.nanoTime();
        int iterations = 0;

        int tamaño = lista.getTamanno();
        if (tamaño <= 1) {
            return new ResultadoOrdenamiento(0, 0);
        }

        Nodo<T> cabezaOrdenada = null;
        Nodo<T> actual = lista.getCabeza();

        while (actual != null) {
            Nodo<T> siguiente = actual.getSiguiente();
            iterations = incrementIterationSafely(iterations);

            // Caso 1: lista ordenada vacía o elemento debe ir al inicio
            if (cabezaOrdenada == null || cabezaOrdenada.getDato().compareTo(actual.getDato()) >= 0) {
                actual.setSiguiente(cabezaOrdenada);
                cabezaOrdenada = actual;
            }
            // Caso 2: insertar en medio o al final
            else {
                Nodo<T> temp = cabezaOrdenada;
                while (temp.getSiguiente() != null &&
                        temp.getSiguiente().getDato().compareTo(actual.getDato()) < 0) {
                    temp = temp.getSiguiente();
                    iterations = incrementIterationSafely(iterations);
                }
                actual.setSiguiente(temp.getSiguiente());
                temp.setSiguiente(actual);
            }

            actual = siguiente;
        }

        lista.setCabeza(cabezaOrdenada);

        double elapsedMillis = calculateElapsedTime(startTime);
        return new ResultadoOrdenamiento(iterations, elapsedMillis);
    }

    /**
     * Calcula el tiempo transcurrido asegurando que no sea negativo
     */
    private double calculateElapsedTime(long startTime) {
        double elapsedMillis = (System.nanoTime() - startTime) / 1_000_000.0;
        return Math.max(0, elapsedMillis);  // Asegura que el tiempo no sea negativo
    }

    /**
     * Incrementa el contador de iteraciones evitando desbordamiento
     */
    private int incrementIterationSafely(int currentIterations) {
        if (currentIterations == Integer.MAX_VALUE) {
            return currentIterations;  // Evita overflow
        }
        return currentIterations + 1;
    }

    @Override
    public String getName() {
        return "Insertion Sort";
    }
}