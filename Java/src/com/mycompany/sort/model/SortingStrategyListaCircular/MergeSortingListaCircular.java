package com.mycompany.sort.SortingStrategyListaCircular;

import com.mycompany.sort.model.SortingStrategy.SortResult;

import java.util.Objects;

public class MergeSortingListaCircular<T extends Comparable<T>> implements SortingStrategyListaCircular {
    
    private long iterations;

    @Override
public SortResult sort(ListaEnlazadaSimpleCircular<T> lista) {
    Objects.requireNonNull(lista, "La lista a ordenar no puede ser null.");

    iterations = 0;
    double start = System.nanoTime();
    int n = lista.getTamanno();
    if (n <= 1) {
        return new SortResult(iterations, 0);
    }

    Nodo<T> cabeza = lista.getCabeza();
    Nodo<T> ultimo = cabeza;
    while (ultimo.getSiguiente() != cabeza) {
        ultimo = ultimo.getSiguiente();
    }
    ultimo.setSiguiente(null);

    Nodo<T> nuevaCabeza = mergeSortRecursivo(cabeza);

    Nodo<T> nuevaUltima = nuevaCabeza;
    while (nuevaUltima.getSiguiente() != null) {
        nuevaUltima = nuevaUltima.getSiguiente();
    }
    nuevaUltima.setSiguiente(nuevaCabeza); 

    lista.setCabeza(nuevaCabeza);
    double end = System.nanoTime() - start;
    double elapsedMillis = end / 1_000_000;

    return new SortResult(iterations, elapsedMillis);
}

private Nodo<T> mergeSortRecursivo(Nodo<T> cabeza) {
    if (cabeza == null || cabeza.getSiguiente() == null) {
        return cabeza;
    }

    Nodo<T> medio = encontrarMedio(cabeza);
    Nodo<T> segundaMitad = medio.getSiguiente();
    medio.setSiguiente(null); 

    Nodo<T> izquierda = mergeSortRecursivo(cabeza);
    Nodo<T> derecha = mergeSortRecursivo(segundaMitad);

    return fusionar(izquierda, derecha);
}

private Nodo<T> encontrarMedio(Nodo<T> cabeza) {
    Nodo<T> lento = cabeza;
    Nodo<T> rapido = cabeza.getSiguiente();

    while (rapido != null && rapido.getSiguiente() != null) {
        lento = lento.getSiguiente();
        rapido = rapido.getSiguiente().getSiguiente();
    }
    return lento;
}

private Nodo<T> fusionar(Nodo<T> izquierda, Nodo<T> derecha) {
    if (izquierda == null) return derecha;
    if (derecha == null) return izquierda;

    Nodo<T> resultado;

    if (izquierda.getDato().compareTo(derecha.getDato()) >= 0) {
        resultado = izquierda;
        resultado.setSiguiente(fusionar(izquierda.getSiguiente(), derecha));
    } else {
        resultado = derecha;
        resultado.setSiguiente(fusionar(izquierda, derecha.getSiguiente()));
    }

    return resultado;
}

    /**
     * Devuelve el nombre legible del algoritmo de ordenamiento.
     *
     * @return el nombre "Merge Sorting"
     */
    @Override
    public String getName() {
        return "Merge Sorting lista enlazada simple circular";
    }

}
