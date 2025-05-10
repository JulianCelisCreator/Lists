package com.mycompany.sort.SortingStrategyListaEnlazadaDoble;

import com.mycompany.sort.model.SortingStrategy.SortResult;

import java.util.Objects;

public class QuickSortingListaEnlazadaDoble<T extends Comparable<T>> implements SortingStrategyEnlazadaDoble {
    
    private long iterations;

    @Override
public SortResult sort(ListaEnlazadaDoble<T> lista) {
    Objects.requireNonNull(lista, "La lista a ordenar no puede ser null.");

    iterations = 0;
    double start = System.nanoTime();
    int n = lista.getTamanno();

    if (n <= 1) {
        return new SortResult(iterations, 0);
    }

    NodoDoble<T> cabeza = lista.getCabeza();
    NodoDoble<T> cola = encontrarCola(cabeza); 
    quickSortRecursivo(cabeza, cola);

    lista.setCabeza(cabeza);
    double elapsedMillis = (System.nanoTime() - start) / 1_000_000;
    return new SortResult(iterations, elapsedMillis);
}

private NodoDoble<T> encontrarCola(NodoDoble<T> nodo) {
    if (nodo == null) return null;
    while (nodo.getSiguiente() != null) {
        nodo = nodo.getSiguiente();
    }
    return nodo;
}

private void quickSortRecursivo(NodoDoble<T> cabezaSubLista, NodoDoble<T> colaSubLista) {
    if (cabezaSubLista == null || colaSubLista == null || cabezaSubLista == colaSubLista || cabezaSubLista == colaSubLista.getSiguiente()) {
        return;
    }

    NodoDoble<T>[] resultadoParticion = particionar(cabezaSubLista, colaSubLista);
    NodoDoble<T> nodoPivoteFinal = resultadoParticion[0];
    NodoDoble<T> nodoAntesPivote = resultadoParticion[1];

    if (nodoAntesPivote != null && nodoPivoteFinal != cabezaSubLista) {
        quickSortRecursivo(cabezaSubLista, nodoAntesPivote);
    }

    if (nodoPivoteFinal != null && nodoPivoteFinal != colaSubLista) {
        quickSortRecursivo(nodoPivoteFinal.getSiguiente(), colaSubLista);
    }
}

private NodoDoble<T>[] particionar(NodoDoble<T> cabeza, NodoDoble<T> cola) {
    T valorPivote = cola.getDato();

    NodoDoble<T> i = null;
    NodoDoble<T> actual = cabeza;

    while (actual != cola) {
        if (actual.getDato().compareTo(valorPivote) > 0) {
            i = (i == null) ? cabeza : i.getSiguiente();
            T temp = actual.getDato();
            actual.setDato(i.getDato());
            i.setDato(temp);
        }
        actual = actual.getSiguiente();
    }

    i = (i == null) ? cabeza : i.getSiguiente();
    T temp = cola.getDato();
    cola.setDato(i.getDato());
    i.setDato(temp);

    NodoDoble<T> nodoAntesPivote = i.getAnterior();

    @SuppressWarnings("unchecked")
    NodoDoble<T>[] resultado = (NodoDoble<T>[]) new NodoDoble<?>[2];
    resultado[0] = i;
    resultado[1] = nodoAntesPivote;
    return resultado;
}

    /**
     * Retorna el nombre legible del algoritmo.
     *
     * @return el nombre "Quick Sort"
     */
    @Override
    public String getName() {
        return "Quick Sort lista enlazada doble";
    }

}
