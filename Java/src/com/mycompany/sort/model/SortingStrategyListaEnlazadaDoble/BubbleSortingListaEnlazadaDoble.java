package com.mycompany.sort.model.SortingStrategyListaEnlazadaDoble;

import com.mycompany.sort.model.SortingStrategy.SortResult;
import com.mycompany.sort.model.politico.ListaEnlazadaDoble;
import com.mycompany.sort.model.politico.NodoDoble;

import java.util.Objects;

/**
 * Implementación del algoritmo de ordenamiento Bubble Sort.
 * Este algoritmo compara pares adyacentes y los intercambia si están en el orden incorrecto,
 * repitiendo el proceso hasta que la lista esté ordenada.
 */

public class BubbleSortingListaEnlazadaDoble<T extends Comparable<T>> implements SortingStrategyEnlazadaDoble<T> {
    
/**
     * Ordena una lista enlazada doble de objetos {@link Politico} usando el algoritmo Bubble Sort
     *
     * @param lista         la lista enlazada doble de políticos a ordenar
     * @return objeto {@link SortResult} con estadísticas del proceso (iteraciones y tiempo)
     */

    @Override
public SortResult sort(ListaEnlazadaDoble<T> lista) {
    Objects.requireNonNull(lista, "La lista a ordenar no puede ser null.");

    long iterations = 0;
    double start = System.nanoTime();

    int n = lista.getTamanno();
    if (n <= 1) {
        return new SortResult(iterations, 0);
    }

    NodoDoble<T> actual = lista.getCabeza();
    NodoDoble<T> siguiente;
    boolean intercambiado;

    for (int i = 0; i < n - 1; i++) {
        actual = lista.getCabeza();
        intercambiado = false;

        for (int j = 0; j < n - i - 1; j++) {
            iterations++;
            siguiente = actual.getSiguiente();

            if (actual.getDato().compareTo(siguiente.getDato()) > 0) { 
                T temp = actual.getDato();
                actual.setDato(siguiente.getDato());
                siguiente.setDato(temp);
                intercambiado = true;
            }

            actual = siguiente;
        }

        if (!intercambiado) {
            break;
        }
    }

    double elapsedMillis = (System.nanoTime() - start) / 1_000_000;
    return new SortResult(iterations, elapsedMillis);
}

    /**
     * Devuelve el nombre legible del algoritmo de ordenamiento.
     *
     * @return nombre del algoritmo ("Bubble Sort")
     */
    @Override
    public String getName() {
        return "Bubble Sort Lista Enlazada doble";
    }

}
