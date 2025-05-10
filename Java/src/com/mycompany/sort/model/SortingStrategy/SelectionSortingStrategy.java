package com.mycompany.sort.model.SortingStrategy;

import com.mycompany.sort.model.politico.Politico;

import java.util.Comparator;

import java.util.Objects;

/**
 * Estrategia de ordenamiento que implementa el algoritmo Selection Sort.
 * Ordena un arreglo de objetos {@link Politico} según el criterio proporcionado por un {@link Comparator}.
 */
public class SelectionSortingStrategy<T extends Comparable<T>> implements SortingStrategy {

    /**
     * Ordena el arreglo utilizando Selection Sort.
     *
     * @param arr        el arreglo de políticos a ordenar
     * @param comparator el comparador que define el criterio de ordenamiento
     * @return un objeto {@link SortResult} que contiene las estadísticas del ordenamiento
     */
    @Override
    public SortResult sort(Politico[] arr, Comparator<Politico> comparator) {
        long iterations = 0;
        double start = System.nanoTime();
        int n = arr.length;

        for (int i = 0; i < n - 1; i++) {
            int minIndex = i;

            for (int j = i + 1; j < n; j++) {
                iterations++;
                if (comparator.compare(arr[j], arr[minIndex]) < 0) {
                    minIndex = j;
                }
            }

            if (minIndex != i) {
                Politico temp = arr[i];
                arr[i] = arr[minIndex];
                arr[minIndex] = temp;
            }
        }

        double elapsedMillis = (System.nanoTime() - start) / 1_000_000;
        return new SortResult(iterations, elapsedMillis);
    }

    @Override
    public SortResult Sort(ListaEnlazadaSimple<T> lista) {
        Objects.requireNonNull(lista, "La lista a ordenar no puede ser null.");
    
        long iterations = 0;
        double start = System.nanoTime();
        int n = lista.getTamanno();
        if (n <= 1) {
            return; 
        }
    
        Nodo<T> actual = lista.getCabeza();
    
        while (actual != null) {
            Nodo<T> maximo = actual;
            Nodo<T> siguiente = actual.getSiguiente();
    
            while (siguiente != null) {
                if (siguiente.getDato().compareTo(maximo.getDato()) > 0) {
                    maximo = siguiente;
                }
                siguiente = siguiente.getSiguiente();
            }
    
            if (maximo != actual) {
                T temp = actual.getDato();
                actual.setDato(maximo.getDato());
                maximo.setDato(temp);
            }
    
            actual = actual.getSiguiente();
        }
    
        lista.setCabeza(lista.getCabeza());
        double elapsedMillis = (System.nanoTime() - start) / 1_000_000;
        return new SortResult(iterations, elapsedMillis);
    }

    /**
     * Retorna el nombre legible del algoritmo.
     *
     * @return "Selection sort"
     */
    @Override
    public String getName() {
        return "Selection sort lista enlazada simple";
    }
}
