package com.mycompany.sort.model.SortingStrategyListaEnlazadaDoble;

import com.mycompany.sort.model.ResultadoOrdenamiento;
import com.mycompany.sort.model.politico.ListaEnlazadaDoble;
import com.mycompany.sort.model.politico.NodoDoble;

import java.util.Objects;
import java.util.Random;

public class QuickSortingListaEnlazadaDoble<T extends Comparable<T>> implements SortingStrategyEnlazadaDoble<T> {

    private long iterations;

    @Override
    public ResultadoOrdenamiento sort(ListaEnlazadaDoble<T> lista) {
        Objects.requireNonNull(lista, "La lista a ordenar no puede ser null.");

        iterations = 0;
        double start = System.nanoTime();

        int n = lista.getTamanno();
        if (n <= 1) {
            return new ResultadoOrdenamiento(iterations, 0);
        }

        NodoDoble<T> cabeza = lista.getCabeza();
        NodoDoble<T> cola = encontrarCola(cabeza);

        quickSortRecursivo(cabeza, cola);

        lista.setCabeza(cabeza);
        double elapsedMillis = (System.nanoTime() - start) / 1_000_000.0;
        return new ResultadoOrdenamiento(iterations, elapsedMillis);
    }

    private NodoDoble<T> encontrarCola(NodoDoble<T> nodo) {
        while (nodo != null && nodo.getSiguiente() != null) {
            nodo = nodo.getSiguiente();
        }
        return nodo;
    }

    private void quickSortRecursivo(NodoDoble<T> inicio, NodoDoble<T> fin) {
        if (inicio == null || fin == null || inicio == fin || inicio == fin.getSiguiente()) {
            return;
        }

        NodoDoble<T>[] resultadoParticion = particionar(inicio, fin);
        NodoDoble<T> pivoteFinal = resultadoParticion[0];
        NodoDoble<T> antesDelPivote = resultadoParticion[1];

        if (antesDelPivote != null) {
            quickSortRecursivo(inicio, antesDelPivote);
        }

        if (pivoteFinal != null && pivoteFinal.getSiguiente() != null) {
            quickSortRecursivo(pivoteFinal.getSiguiente(), fin);
        }
    }

    private NodoDoble<T>[] particionar(NodoDoble<T> inicio, NodoDoble<T> fin) {
        NodoDoble<T> pivoteRandom = elegirPivoteAleatorio(inicio, fin);
        intercambiarDatos(pivoteRandom, fin);

        T valorPivote = fin.getDato();
        NodoDoble<T> i = null;
        NodoDoble<T> j = inicio;

        while (j != fin) {
            iterations++;
            if (j.getDato().compareTo(valorPivote) <= 0) {
                i = (i == null) ? inicio : i.getSiguiente();
                intercambiarDatos(i, j);
            }
            j = j.getSiguiente();
        }

        i = (i == null) ? inicio : i.getSiguiente();
        intercambiarDatos(i, fin);

        NodoDoble<T>[] resultado = (NodoDoble<T>[]) new NodoDoble<?>[2];
        resultado[0] = i;                   // pivote final
        resultado[1] = i.getAnterior();     // nodo antes del pivote
        return resultado;
    }

    private NodoDoble<T> elegirPivoteAleatorio(NodoDoble<T> inicio, NodoDoble<T> fin) {
        int longitud = 0;
        NodoDoble<T> actual = inicio;
        while (actual != fin) {
            longitud++;
            actual = actual.getSiguiente();
        }
        longitud++; // incluir el nodo 'fin'

        int indiceAleatorio = new Random().nextInt(longitud);
        actual = inicio;
        for (int i = 0; i < indiceAleatorio; i++) {
            actual = actual.getSiguiente();
        }
        return actual;
    }

    private void intercambiarDatos(NodoDoble<T> a, NodoDoble<T> b) {
        T temp = a.getDato();
        a.setDato(b.getDato());
        b.setDato(temp);
    }

    @Override
    public String getName() {
        return "Quick Sort lista enlazada doble";
    }
}
