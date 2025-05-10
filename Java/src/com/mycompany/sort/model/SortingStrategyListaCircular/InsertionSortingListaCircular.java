package com.mycompany.sort.SortingStrategyListaCircular;

import com.mycompany.sort.model.SortingStrategy.SortResult;

import java.util.Objects;

public class InsertionSortingListaCircular<T extends Comparable<T>> implements SortingStrategyListaCircular {
    
    @Override
    public SortResult sort(ListaEnlazadaSimpleCircular<T> lista) {
        Objects.requireNonNull(lista, "La lista a ordenar no puede ser null.");
    
        long iterations = 0;
        double start = System.nanoTime();
        int n = lista.getTamanno();
        if (n <= 1) {
            return new SortResult(iterations, 0);
        }
    
        Nodo<T> actualOriginal = lista.getCabeza();
        this.cabezaOrdenada = null;
    
        for (int i = 0; i < n; i++) {
            Nodo<T> siguienteOriginal = actualOriginal.getSiguiente();
    
            actualOriginal.setSiguiente(null);
            insertarEnOrden(actualOriginal);
    
            actualOriginal = siguienteOriginal;
            iterations++;
        }
    
        if (cabezaOrdenada != null) {
            Nodo<T> ultimo = cabezaOrdenada;
            while (ultimo.getSiguiente() != null && ultimo.getSiguiente() != cabezaOrdenada) {
                ultimo = ultimo.getSiguiente();
            }
            ultimo.setSiguiente(cabezaOrdenada);
        }
    
        lista.setCabeza(cabezaOrdenada);
    
        double elapsedMillis = (System.nanoTime() - start) / 1_000_000;
        return new SortResult(iterations, elapsedMillis);
    }
    
    /**
     * Inserta un nodo en orden descendente en una lista circular parcial.
     */
    private void insertarEnOrden(Nodo<T> nodoAInsertar) {
        if (cabezaOrdenada == null ||
            cabezaOrdenada.getDato().compareTo(nodoAInsertar.getDato()) <= 0)
        {
            nodoAInsertar.setSiguiente(cabezaOrdenada);
            cabezaOrdenada = nodoAInsertar;
        } else {
            Nodo<T> actualOrdenado = cabezaOrdenada;
            while (actualOrdenado.getSiguiente() != null &&
                   actualOrdenado.getSiguiente().getDato().compareTo(nodoAInsertar.getDato()) > 0)
            {
                actualOrdenado = actualOrdenado.getSiguiente();
            }
            nodoAInsertar.setSiguiente(actualOrdenado.getSiguiente());
            actualOrdenado.setSiguiente(nodoAInsertar);
        }
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
