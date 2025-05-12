package com.mycompany.sort.model.SortingStrategyListaEnlazadaDoble;

import com.mycompany.sort.model.ResultadoOrdenamiento;
import com.mycompany.sort.model.politico.ListaEnlazadaDoble;
import com.mycompany.sort.model.politico.NodoDoble;

import java.util.Objects;

public class SelectionSortingListaEnlazadaDoble<T extends Comparable<T>> implements SortingStrategyEnlazadaDoble<T> {

    @Override
    public ResultadoOrdenamiento sort(ListaEnlazadaDoble<T> lista) {
        Objects.requireNonNull(lista, "La lista a ordenar no puede ser null.");

        long iterations = 0;
        double start = System.nanoTime();

        int n = lista.getTamanno();
        if (n <= 1) {
            return new ResultadoOrdenamiento(iterations, 0);
        }

        NodoDoble<T> actual = lista.getCabeza();

        while (actual != null) {
            NodoDoble<T> maximo = actual;
            NodoDoble<T> siguiente = actual.getSiguiente();

            while (siguiente != null) {
                iterations++;
                if (siguiente.getDato().compareTo(maximo.getDato()) > 0) {
                    maximo = siguiente;
                }
                siguiente = siguiente.getSiguiente();
            }

            if (maximo != actual) {
                intercambiarDatos(actual, maximo);
            }

            actual = actual.getSiguiente();
        }

        double elapsedMillis = (System.nanoTime() - start) / 1_000_000.0;
        return new ResultadoOrdenamiento(iterations, elapsedMillis);
    }

    private void intercambiarDatos(NodoDoble<T> a, NodoDoble<T> b) {
        T temp = a.getDato();
        a.setDato(b.getDato());
        b.setDato(temp);
    }

    @Override
    public String getName() {
        return "Selection sort lista enlazada doble";
    }
}
