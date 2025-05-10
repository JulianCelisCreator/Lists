package com.mycompany.sort.model.SortingStrategy;


import com.mycompany.sort.model.politico.Politico;
import com.mycompany.sort.model.politico.Nodo;
import com.mycompany.sort.model.politico.ListaEnlazadaSimple;

import java.util.Objects;

/**
 * Implementación del algoritmo de ordenamiento Insertion Sort.
 * Este algoritmo construye el arreglo ordenado de izquierda a derecha,
 * insertando cada nuevo elemento en la posición correcta respecto a los anteriores.
 */
public class InsertionSortingStrategy<T extends Comparable<T>> implements SortingStrategy<T> {

    private Nodo<T> cabezaOrdenada;

    /**
     * Ordena una lista enlazada simple de objetos {@link Politico} usando el algoritmo Insertion Sort
     * y un comparador definido por el usuario.
     *
     * @param lista         lista enlazada simple de políticos a ordenar
     * @param comparator  el comparador que define el criterio de ordenamiento
     * @return objeto {@link SortResult} que contiene el número de iteraciones y el tiempo de ejecución
     */
    @Override
    public SortResult sort(ListaEnlazadaSimple<T> lista) {
        Objects.requireNonNull(lista, "La lista a ordenar no puede ser null.");

        long iterations = 0;
        double start = System.nanoTime();
        int n = lista.getTamanno();
        if (n <= 1) {
            return new SortResult(iterations, 0); 
        }

        Nodo<T> actualOriginal = lista.getCabeza(); 
        this.cabezaOrdenada = null; 

        while (actualOriginal != null) {
            Nodo<T> siguienteOriginal = actualOriginal.getSiguiente();

            insertarEnOrden(actualOriginal);

            actualOriginal = siguienteOriginal;
        }

        lista.setCabeza(this.cabezaOrdenada); 
        double end = System.nanoTime() - start;
        double elapsedMillis = end / 1_000_000;
        return new SortResult(iterations, elapsedMillis);
    }

    /**
     * Método auxiliar privado para insertar un {@code nodoAInsertar} en la lista
     * ordenada referenciada por {@code cabezaOrdenada}, manteniendo el orden.
     *
     * @param nodoAInsertar El {@link Nodo} que se va a insertar.
     */
    private void insertarEnOrden(Nodo<T> nodoAInsertar) {
        if (this.cabezaOrdenada == null ||
            this.cabezaOrdenada.getDato().compareTo(nodoAInsertar.getDato()) <= 0)
        {
            nodoAInsertar.setSiguiente(this.cabezaOrdenada);
            this.cabezaOrdenada = nodoAInsertar;
        } else {
            Nodo<T> actualOrdenado = this.cabezaOrdenada;
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
        return "Insertion Sort lista enlazada simple";
    }
}
