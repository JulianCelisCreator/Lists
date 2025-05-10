package com.mycompany.sort.SortingStrategyListaCircular;

import com.mycompany.sort.model.SortingStrategy.SortResult;

import java.util.Objects;

public class QuickSortingListaCircular<T extends Comparable<T>> implements SortingStrategyListaCircular{
    
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

    Nodo<T> cola = cabeza;
    while (cola.getSiguiente() != cabeza) {
        cola = cola.getSiguiente();
    }
    cola.setSiguiente(null);

    quickSortRecursivo(cabeza, cola);

    Nodo<T> nuevaCola = cabeza;
    while (nuevaCola.getSiguiente() != null) {
        nuevaCola = nuevaCola.getSiguiente();
    }
    nuevaCola.setSiguiente(cabeza); 

    lista.setCabeza(cabeza);
    double elapsedMillis = (System.nanoTime() - start) / 1_000_000;
    return new SortResult(iterations, elapsedMillis);
}

private void quickSortRecursivo(Nodo<T> cabezaSubLista, Nodo<T> colaSubLista) {
    if (cabezaSubLista == null || colaSubLista == null || cabezaSubLista == colaSubLista || cabezaSubLista == colaSubLista.getSiguiente()) {
        return;
    }

    Nodo<T>[] resultadoParticion = particionar(cabezaSubLista, colaSubLista);
    Nodo<T> pivote = resultadoParticion[0];
    Nodo<T> nodoAntesPivote = resultadoParticion[1];

    if (nodoAntesPivote != null && pivote != cabezaSubLista) {
        quickSortRecursivo(cabezaSubLista, nodoAntesPivote);
    }

    if (pivote != null && pivote != colaSubLista) {
        quickSortRecursivo(pivote.getSiguiente(), colaSubLista);
    }
}

private Nodo<T>[] particionar(Nodo<T> cabeza, Nodo<T> cola) {
    T valorPivote = cola.getDato();

    Nodo<T> i = null;
    Nodo<T> actual = cabeza;

    while (actual != cola) {
        if (actual.getDato().compareTo(valorPivote) < 0) { 
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

    Nodo<T> nodoAntesPivote = null;
    if (i != cabeza) {
        Nodo<T> buscador = cabeza;
        while (buscador.getSiguiente() != i) {
            buscador = buscador.getSiguiente();
        }
        nodoAntesPivote = buscador;
    }

    @SuppressWarnings("unchecked")
    Nodo<T>[] resultado = (Nodo<T>[]) new Nodo<?>[2];
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
        return "Quick Sort lista enlazada simple circular";
    }

}
