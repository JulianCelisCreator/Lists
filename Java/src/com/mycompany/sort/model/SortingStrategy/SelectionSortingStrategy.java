package com.mycompany.sort.model.SortingStrategy;

import com.mycompany.sort.model.politico.Politico;

import com.mycompany.sort.model.politico.ListaEnlazadaSimple;
import com.mycompany.sort.model.politico.Nodo;

import java.util.Comparator;

import java.util.Objects;

/**
 * Estrategia de ordenamiento que implementa el algoritmo Selection Sort.
 * Ordena un arreglo de objetos {@link Politico} según el criterio proporcionado por un {@link Comparator}.
 */
public class SelectionSortingStrategy<T extends Comparable<T>> implements SortingStrategy<T> {

    /**
     * Ordena la lista enlazada simple de politicos utilizando Selection Sort.
     *
     * @param lista        la lista enlazada simple de políticos a ordenar
     * @return un objeto {@link SortResult} que contiene las estadísticas del ordenamiento
     */
    @Override
    public SortResult sort(ListaEnlazadaSimple<T> lista) {
        Objects.requireNonNull(lista, "La lista a ordenar no puede ser null.");
    
        long iterations = 0;
        double start = System.nanoTime();
        int n = lista.getTamanno();
        if (n <= 1) {
            return new SortResult(iterations, 0); 
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
