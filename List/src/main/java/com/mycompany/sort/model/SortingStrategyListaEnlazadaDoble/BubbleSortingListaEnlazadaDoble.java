package com.mycompany.sort.model.SortingStrategyListaEnlazadaDoble;

import com.mycompany.sort.model.ResultadoOrdenamiento;
import com.mycompany.sort.model.politico.ListaEnlazadaDoble;
import com.mycompany.sort.model.politico.NodoDoble;
import java.util.Objects;

public class BubbleSortingListaEnlazadaDoble<T extends Comparable<T>> implements SortingStrategyEnlazadaDoble<T> {

    public ResultadoOrdenamiento sort(ListaEnlazadaDoble<T> lista) {
        Objects.requireNonNull(lista, "La lista a ordenar no puede ser null");

        long iterations = 0;
        double startTime = System.nanoTime();

        if (lista.getTamanno() <= 1) {
            return new ResultadoOrdenamiento(iterations, 0);
        }

        boolean swapped;
        NodoDoble<T> end = null;

        do {
            swapped = false;
            NodoDoble<T> current = lista.getCabeza();

            while (current.getSiguiente() != end) {
                iterations++;
                NodoDoble<T> next = current.getSiguiente();

                if (current.getDato().compareTo(next.getDato()) > 0) {
                    swapData(current, next);
                    swapped = true;
                }
                current = next;
            }
            end = current; // reduce el rango del próximo ciclo
        } while (swapped);

        double elapsedMillis = (System.nanoTime() - startTime) / 1_000_000;
        return new ResultadoOrdenamiento(iterations, elapsedMillis);
    }


    private void swapData(NodoDoble<T> node1, NodoDoble<T> node2) {
        T temp = node1.getDato();
        node1.setDato(node2.getDato());
        node2.setDato(temp);
    }

    @Override
    public String getName() {
        return "Bubble Sort Lista Doblemente Enlazada";
    }
}