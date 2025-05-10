package com.mycompany.sort.model.SortingStrategyListaEnlazadaDoble;

import com.mycompany.sort.model.SortingStrategy.SortResult;
import com.mycompany.sort.model.politico.ListaEnlazadaDoble;
import com.mycompany.sort.model.politico.NodoDoble;

import java.util.Objects;

public class InsertionSortingListaEnlazadaDoble<T extends Comparable<T>> implements SortingStrategyEnlazadaDoble<T> {
 
    @Override
public SortResult sort(ListaEnlazadaDoble<T> lista) {
    Objects.requireNonNull(lista, "La lista a ordenar no puede ser null.");

    long iterations = 0;
    double start = System.nanoTime();

    int n = lista.getTamanno();
    if (n <= 1) {
        return new SortResult(iterations, 0);
    }

    NodoDoble<T> actual = lista.getCabeza().getSiguiente(); // Empezamos desde el segundo nodo
    while (actual != null) {
        NodoDoble<T> siguiente = actual.getSiguiente();
        NodoDoble<T> anterior = actual.getAnterior();

        while (anterior != null && anterior.getDato().compareTo(actual.getDato()) > 0) {  // ASCENDENTE
            // Intercambiar los datos de los nodos
            T temp = anterior.getDato();
            anterior.setDato(actual.getDato());
            actual.setDato(temp);

            // Mover hacia atrás en la lista
            actual = anterior;
            anterior = actual.getAnterior();

            iterations++;
        }

        actual = siguiente;
    }

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
        return "Insertion Sort lista enlazada doble";
    }
    
}
