package com.mycompany.sort.model.SortingStrategyListaCircular;

import com.mycompany.sort.model.ResultadoOrdenamiento;
import com.mycompany.sort.model.politico.ListaEnlazadaSimpleCircular;
import com.mycompany.sort.model.politico.Nodo;

import java.util.Objects;

public class SelectionSortingListaCircular<T extends Comparable<T>> implements SortingStrategyListaCircular<T> {

    @Override
    public ResultadoOrdenamiento sort(ListaEnlazadaSimpleCircular<T> lista) {
        Objects.requireNonNull(lista, "La lista a ordenar no puede ser null.");

        long iterations = 0;
        double start = System.nanoTime();

        int n = lista.getTamanno();
        if (n <= 1) {
            return new ResultadoOrdenamiento(iterations, 0);
        }

        Nodo<T> actual = lista.getCabeza();

        for (int i = 0; i < n - 1; i++) {
            Nodo<T> minimo = actual;
            Nodo<T> siguiente = actual.getSiguiente();

            for (int j = i + 1; j < n; j++) {
                iterations++;
                if (siguiente.getDato().compareTo(minimo.getDato()) < 0) { // ASCENDENTE
                    minimo = siguiente;
                }
                siguiente = siguiente.getSiguiente();
            }

            if (minimo != actual) {
                T temp = actual.getDato();
                actual.setDato(minimo.getDato());
                minimo.setDato(temp);
            }

            actual = actual.getSiguiente();
        }

        double elapsedMillis = (System.nanoTime() - start) / 1_000_000;
        System.out.println("terminado selection");
        return new ResultadoOrdenamiento(iterations, elapsedMillis);
    }

    @Override
    public String getName() {
        return "Selection sort lista enlazada simple circular";
    }
}
