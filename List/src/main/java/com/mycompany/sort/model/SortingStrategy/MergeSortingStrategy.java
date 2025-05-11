package com.mycompany.sort.model.SortingStrategy;

import com.mycompany.sort.model.ResultadoOrdenamiento;
import com.mycompany.sort.model.politico.ListaEnlazadaSimple;
import com.mycompany.sort.model.politico.Nodo;

import java.util.Objects;

public class MergeSortingStrategy<T extends Comparable<T>> implements SortingStrategy<T> {

    private int iterations;

    @Override
    public ResultadoOrdenamiento sort(ListaEnlazadaSimple<T> lista) {
        Objects.requireNonNull(lista, "La lista a ordenar no puede ser null");

        long startTime = System.nanoTime();
        iterations = 0;

        if (lista.getTamanno() <= 1) {
            return new ResultadoOrdenamiento(0, 0);
        }

        lista.setCabeza(mergeSortIterativo(lista.getCabeza()));

        double elapsedMillis = (System.nanoTime() - startTime) / 1_000_000.0;
        return new ResultadoOrdenamiento(iterations, elapsedMillis);
    }

    private Nodo<T> mergeSortIterativo(Nodo<T> cabeza) {
        if (cabeza == null || cabeza.getSiguiente() == null) {
            return cabeza;
        }

        // Dividir la lista en sublistas de tamaño creciente
        int tamano = obtenerTamano(cabeza);
        int tamanoSubLista = 1;

        Nodo<T> dummy = new Nodo<>(null);
        dummy.setSiguiente(cabeza);

        Nodo<T> izquierda, derecha, actual, cola;

        while (tamanoSubLista < tamano) {
            actual = dummy.getSiguiente();
            cola = dummy;

            while (actual != null) {
                izquierda = actual;
                derecha = dividir(izquierda, tamanoSubLista);
                actual = dividir(derecha, tamanoSubLista);
                cola = fusionar(izquierda, derecha, cola);
            }

            tamanoSubLista *= 2;
        }

        return dummy.getSiguiente();
    }

    private Nodo<T> dividir(Nodo<T> cabeza, int n) {
        for (int i = 1; cabeza != null && i < n; i++) {
            cabeza = cabeza.getSiguiente();
        }

        if (cabeza == null) {
            return null;
        }

        Nodo<T> siguiente = cabeza.getSiguiente();
        cabeza.setSiguiente(null);
        return siguiente;
    }

    private Nodo<T> fusionar(Nodo<T> izquierda, Nodo<T> derecha, Nodo<T> cola) {
        Nodo<T> actual = cola;

        while (izquierda != null && derecha != null) {
            iterations++;
            if (izquierda.getDato().compareTo(derecha.getDato()) <= 0) {
                actual.setSiguiente(izquierda);
                izquierda = izquierda.getSiguiente();
            } else {
                actual.setSiguiente(derecha);
                derecha = derecha.getSiguiente();
            }
            actual = actual.getSiguiente();
        }

        if (izquierda != null) {
            actual.setSiguiente(izquierda);
        } else {
            actual.setSiguiente(derecha);
        }

        // Avanzar hasta el final de la lista fusionada
        while (actual.getSiguiente() != null) {
            actual = actual.getSiguiente();
        }

        return actual;
    }

    private int obtenerTamano(Nodo<T> nodo) {
        int tamano = 0;
        while (nodo != null) {
            tamano++;
            nodo = nodo.getSiguiente();
        }
        return tamano;
    }

    @Override
    public String getName() {
        return "Merge Sort Iterativo";
    }
}