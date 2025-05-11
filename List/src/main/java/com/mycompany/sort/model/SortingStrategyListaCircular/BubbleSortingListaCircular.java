package com.mycompany.sort.model.SortingStrategyListaCircular;

import com.mycompany.sort.model.ResultadoOrdenamiento;
import com.mycompany.sort.model.politico.ListaEnlazadaSimpleCircular;
import com.mycompany.sort.model.politico.Nodo;

import java.util.Objects;

public class BubbleSortingListaCircular<T extends Comparable<T>> implements SortingStrategyListaCircular<T> {

    /**
     * Ordena una lista enlazada simple circular usando Bubble Sort (orden descendente).
     *
     * @param lista la lista enlazada circular a ordenar
     * @return ResultadoOrdenamiento con el número de iteraciones y el tiempo en milisegundos
     */
    @Override
    public ResultadoOrdenamiento sort(ListaEnlazadaSimpleCircular<T> lista) {
        Objects.requireNonNull(lista, "La lista a ordenar no puede ser null.");

        long iterations = 0;
        double start = System.nanoTime();
        int n = lista.getTamanno();
        if (n <= 1) {
            return new ResultadoOrdenamiento(iterations, 0);
        }

        boolean intercambiado;
        Nodo<T> cabeza = lista.getCabeza();

        for (int i = 0; i < n - 1; i++) {
            Nodo<T> actual = cabeza;
            Nodo<T> siguiente = actual.getSiguiente();
            intercambiado = false;

            for (int j = 0; j < n - i - 1; j++) {
                iterations++;

                if (actual.getDato().compareTo(siguiente.getDato()) < 0) {
                    // Intercambio descendente
                    T temp = actual.getDato();
                    actual.setDato(siguiente.getDato());
                    siguiente.setDato(temp);
                    intercambiado = true;
                }

                actual = siguiente;
                siguiente = siguiente.getSiguiente();
            }

            if (!intercambiado) break; // Lista ya ordenada
        }

        lista.setCabeza(cabeza);
        double elapsedMillis = (System.nanoTime() - start) / 1_000_000.0;
        System.out.println("terminado bubble");
        return new ResultadoOrdenamiento(iterations, elapsedMillis);
    }

    @Override
    public String getName() {
        return "Bubble Sort Lista Enlazada Simple Circular";
    }
}
