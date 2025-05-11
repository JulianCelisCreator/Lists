package com.mycompany.sort.model.SortingStrategy;

import com.mycompany.sort.model.ResultadoOrdenamiento;
import com.mycompany.sort.model.politico.Politico;

import com.mycompany.sort.model.politico.ListaEnlazadaSimple;

import com.mycompany.sort.model.politico.Nodo;

import java.util.Objects;

/**
 * Implementación del algoritmo de ordenamiento Bubble Sort.
 * Este algoritmo compara pares adyacentes y los intercambia si están en el orden incorrecto,
 * repitiendo el proceso hasta que la lista esté ordenada.
 */
public class BubbleSortingStrategy<T extends Comparable<T>> implements SortingStrategy<T> {

    /**
     * Ordena una lista enlazada simple de objetos {@link Politico} usando el algoritmo Bubble Sort
     *
     * @param lista         la lista enlazada simple de políticos a ordenar
     * @return objeto {@link ResultadoOrdenamiento} con estadísticas del proceso (iteraciones y tiempo)
     */

    @Override
    public ResultadoOrdenamiento sort(ListaEnlazadaSimple<T> lista) {
        Objects.requireNonNull(lista, "La lista a ordenar no puede ser null");

        long iterations = 0;
        double start = System.nanoTime();
        int n = lista.getTamanno();

        if (n <= 1) {
            return new ResultadoOrdenamiento(iterations, 0);
        }

        boolean intercambiado;
        do {
            intercambiado = false;
            Nodo<T> actual = lista.getCabeza();
            Nodo<T> anterior = null;
            Nodo<T> siguiente = (actual != null) ? actual.getSiguiente() : null;

            while (actual != null && siguiente != null) {
                iterations++; // Contamos cada comparación

                // Cambiado a > 0 para orden ascendente
                if (actual.getDato().compareTo(siguiente.getDato()) > 0) {
                    // Intercambiar datos
                    T temp = actual.getDato();
                    actual.setDato(siguiente.getDato());
                    siguiente.setDato(temp);
                    intercambiado = true;
                }

                anterior = actual;
                actual = siguiente;
                siguiente = siguiente.getSiguiente();
            }
        } while (intercambiado);

        double elapsedMillis = (System.nanoTime() - start) / 1_000_000;
        return new ResultadoOrdenamiento(iterations, elapsedMillis);
    }

    /**
     * Devuelve el nombre legible del algoritmo de ordenamiento.
     *
     * @return nombre del algoritmo ("Bubble Sort Lista Enlazada Simple")
     */
    @Override
    public String getName() {
        return "Bubble Sort Lista Enlazada Simple";
    }
}