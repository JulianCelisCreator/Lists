package com.mycompany.sort.model.SortingStrategyListaCircular;

import com.mycompany.sort.model.ResultadoOrdenamiento;
import com.mycompany.sort.model.politico.ListaEnlazadaSimpleCircular;
import com.mycompany.sort.model.politico.Nodo;

import java.util.Objects;

public class InsertionSortingListaCircular<T extends Comparable<T>> implements SortingStrategyListaCircular<T> {

    /**
     * Ordena una lista enlazada simple circular de objetos utilizando el algoritmo Insertion Sort.
     *
     * @param lista la lista a ordenar.
     * @return un objeto {@link ResultadoOrdenamiento} con estadísticas del proceso.
     */
    @Override
    public ResultadoOrdenamiento sort(ListaEnlazadaSimpleCircular<T> lista) {
        Objects.requireNonNull(lista, "La lista a ordenar no puede ser null.");

        long iterations = 0;
        double start = System.nanoTime();

        // Si la lista tiene 0 o 1 elemento, ya está ordenada
        if (lista.getTamanno() <= 1) {
            return new ResultadoOrdenamiento(iterations, 0);
        }

        // Comienza el algoritmo Insertion Sort
        Nodo<T> actual = lista.getCabeza().getSiguiente(); // Segundo nodo
        Nodo<T> previo = lista.getCabeza();
        int processed = 1; // Cuántos nodos se han procesado

        while (processed < lista.getTamanno()) {
            T key = actual.getDato();
            Nodo<T> mover = lista.getCabeza();
            Nodo<T> insertarAntes = null;

            // Buscar la posición adecuada para insertar el 'key'
            while (mover != actual && mover.getDato().compareTo(key) <= 0) {
                insertarAntes = mover;
                mover = mover.getSiguiente();
                iterations++;
            }

            if (insertarAntes == null) {
                // Insertar al principio de la lista
                lista.getCabeza().setDato(key);
            } else {
                // Desplazar los elementos para insertar el 'key' en la posición correcta
                Nodo<T> desplazador = insertarAntes.getSiguiente();
                while (desplazador != actual) {
                    Nodo<T> siguiente = desplazador.getSiguiente();
                    desplazador.setDato(siguiente.getDato());
                    desplazador = siguiente;
                }
                insertarAntes.getSiguiente().setDato(key);
            }

            // Mover al siguiente nodo
            actual = actual.getSiguiente();
            processed++;
        }

        double elapsedMillis = (System.nanoTime() - start) / 1_000_000.0;
        System.out.printf("Terminado insertion\n");
        return new ResultadoOrdenamiento(iterations, elapsedMillis);
    }

    /**
     * Devuelve el nombre legible del algoritmo.
     *
     * @return el nombre "Insertion Sort Lista Enlazada Simple Circular".
     */
    @Override
    public String getName() {
        return "Insertion Sort Lista Enlazada Simple Circular";
    }
}
