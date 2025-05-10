package com.mycompany.sort.SortingStrategyListaEnlazadaDoble;

import com.mycompany.sort.model.SortingStrategy.SortResult;

import java.util.Objects;

public class BubbleSortingListaEnlazadaDoble<T extends Comparable<T>> implements SortingStrategyEnlazadaDoble {
    
    @Override
public SortResult sort(ListaEnlazadaDoble<T> lista) {
    Objects.requireNonNull(lista, "La lista a ordenar no puede ser null.");

    long iterations = 0;
    double start = System.nanoTime();
    int n = lista.getTamanno();

    if (n <= 1) {
        return new SortResult(iterations, 0);
    }

    boolean intercambiado;

    for (int i = 0; i < n - 1; i++) {
        NodoDoble<T> actual = lista.getCabeza();
        intercambiado = false;

        while (actual != null && actual.getSiguiente() != null) {
            iterations++;
            if (actual.getDato().compareTo(actual.getSiguiente().getDato()) < 0) {
                // Intercambiar los datos
                T temp = actual.getDato();
                actual.setDato(actual.getSiguiente().getDato());
                actual.getSiguiente().setDato(temp);
                intercambiado = true;
            }
            actual = actual.getSiguiente();
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
