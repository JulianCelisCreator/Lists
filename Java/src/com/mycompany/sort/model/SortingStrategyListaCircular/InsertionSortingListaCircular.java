package com.mycompany.sort.model.SortingStrategyListaCircular;

import com.mycompany.sort.model.SortingStrategy.SortResult;

import com.mycompany.sort.model.politico.ListaEnlazadaSimpleCircular;
import com.mycompany.sort.model.politico.Nodo;

import java.util.Objects;

public class InsertionSortingListaCircular<T extends Comparable<T>> implements SortingStrategyListaCircular<T> {
    
    /**
     * Ordena una lista enlazada simple circular de objetos {@link Politico} usando el algoritmo Insertion Sort
     * y un comparador definido por el usuario.
     *
     * @param lista         lista enlazada simple circular de políticos a ordenar
     * @param comparator  el comparador que define el criterio de ordenamiento
     * @return objeto {@link SortResult} que contiene el número de iteraciones y el tiempo de ejecución
     */

    @Override
public SortResult sort(ListaEnlazadaSimpleCircular<T> lista) {
    Objects.requireNonNull(lista, "La lista a ordenar no puede ser null.");

    long iterations = 0;
    double start = System.nanoTime();
    int n = lista.getTamanno();
    if (n <= 1) {
        return new SortResult(iterations, 0);
    }

    Nodo<T> cabeza = lista.getCabeza();

    Nodo<T> actual = cabeza.getSiguiente();
    for (int i = 1; i < n; i++) {
        T datoActual = actual.getDato();
        Nodo<T> anterior = cabeza;
        Nodo<T> recorrido = cabeza;

        for (int j = 0; j < i; j++) {
            iterations++;
            if (datoActual.compareTo(recorrido.getDato()) > 0) {
                Nodo<T> mover = actual;
                for (int k = i; k > j; k--) {
                    Nodo<T> previo = cabeza;
                    for (int l = 1; l < k; l++) {
                        previo = previo.getSiguiente();
                    }
                    try {
                        mover.setDato(previo.getDato());
                        mover = previo;
                    } catch (Exception e) {
                        throw new UnsupportedOperationException(
                            "El método Insertion Sort requiere un método setDato(T) en Nodo.", e);
                    }
                }

                recorrido.setDato(datoActual);
                break;
            }
            anterior = recorrido;
            recorrido = recorrido.getSiguiente();
        }

        actual = actual.getSiguiente();
    }

    lista.setCabeza(cabeza);
    double elapsedMillis = (System.nanoTime() - start) / 1_000_000;
    return new SortResult(iterations, elapsedMillis);
}


    /**
     * Devuelve el nombre legible del algoritmo de ordenamiento.
     *
     * @return el nombre "Insertion Sort"
     */
    @Override
    public String getName() {
        return "Insertion Sort lista enlazada simple circular";
    }

}
