package com.mycompany.sort.model.SortingStrategyListaEnlazadaDoble;

import com.mycompany.sort.model.ResultadoOrdenamiento;
import com.mycompany.sort.model.politico.ListaEnlazadaDoble;
import com.mycompany.sort.model.politico.NodoDoble;

import java.util.Objects;

public class InsertionSortingListaEnlazadaDoble<T extends Comparable<T>> implements SortingStrategyEnlazadaDoble<T> {

    @Override
    public ResultadoOrdenamiento sort(ListaEnlazadaDoble<T> lista) {
        Objects.requireNonNull(lista, "La lista a ordenar no puede ser null.");

        long iterations = 0;
        double start = System.nanoTime();

        if (lista.getTamanno() <= 1) {
            return new ResultadoOrdenamiento(iterations, 0);
        }

        NodoDoble<T> actual = lista.getCabeza().getSiguiente(); // Segundo nodo

        while (actual != null) {
            T key = actual.getDato();
            NodoDoble<T> mover = actual.getAnterior();

            // Recorremos hacia atrás buscando dónde insertar el dato
            while (mover != null && mover.getDato().compareTo(key) > 0) {
                mover.getSiguiente().setDato(mover.getDato());  // Mueve el dato del nodo hacia adelante
                mover = mover.getAnterior();                    // Mueve hacia el nodo anterior
                iterations++;
            }

            // Insertar el key en la posición correcta
            if (mover == null) {
                // Si se insertó al principio, actualizar la cabeza
                lista.getCabeza().setDato(key);
            } else {
                mover.getSiguiente().setDato(key); // Insertar el dato en el lugar correcto
            }

            actual = actual.getSiguiente();
        }

        double elapsedMillis = (System.nanoTime() - start) / 1_000_000;
        return new ResultadoOrdenamiento(iterations, elapsedMillis);
    }

    /**
     * Devuelve el nombre legible del algoritmo de ordenamiento.
     *
     * @return el nombre "Insertion Sort"
     */
    @Override
    public String getName() {
        return "Insertion Sort lista enlazada doble";
    }
}
