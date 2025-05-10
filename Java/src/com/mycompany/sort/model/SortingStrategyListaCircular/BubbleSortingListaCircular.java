package com.mycompany.sort.SortingStrategyListaCircular;

import com.mycompany.sort.model.SortingStrategy.SortResult;

import java.util.Objects;

public class BubbleSortingListaCircular<T extends Comparable<T>> implements SortingStrategyListaCircular {
 

    @Override
public SortResult sort(ListaEnlazadaSimpleCircular<T> lista) {
    Objects.requireNonNull(lista, "La lista a ordenar no puede ser null.");

    long iterations = 0;
    double start = System.nanoTime();
    int n = lista.getTamanno();
    if (n <= 1) {
        return new SortResult(iterations, 0);
    }

    boolean intercambiado;
    Nodo<T> cabezaActual = lista.getCabeza();

    for (int i = 0; i < n - 1; i++) {
        Nodo<T> actual = cabezaActual;
        Nodo<T> siguiente = actual.getSiguiente();
        intercambiado = false;

        for (int j = 0; j < n - i - 1; j++) {
            iterations++;

            if (actual.getDato().compareTo(siguiente.getDato()) < 0) { 
                T temp = actual.getDato();
                try {
                    actual.setDato(siguiente.getDato());
                    siguiente.setDato(temp);
                } catch (Exception e) {
                    throw new UnsupportedOperationException(
                        "La ordenación por Burbuja requiere un método setDato(T) en la clase Nodo.", e);
                }
                intercambiado = true;
            }

            actual = siguiente;
            siguiente = siguiente.getSiguiente();
        }

        if (!intercambiado) {
            break;
        }
    }

    lista.setCabeza(cabezaActual);
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
        return "Bubble Sort Lista Enlazada Simple circular";
    }

}
