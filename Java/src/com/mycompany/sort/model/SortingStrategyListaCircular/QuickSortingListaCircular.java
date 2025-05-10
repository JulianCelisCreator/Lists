package com.mycompany.sort.model.SortingStrategyListaCircular;

import com.mycompany.sort.model.SortingStrategy.SortResult;
import com.mycompany.sort.model.politico.ListaEnlazadaSimpleCircular;
import com.mycompany.sort.model.politico.Nodo;

import java.util.Objects;

public class QuickSortingListaCircular<T extends Comparable<T>> implements SortingStrategyListaCircular<T>{
    
    @Override
public SortResult sort(ListaEnlazadaSimpleCircular<T> lista) {
    Objects.requireNonNull(lista, "La lista a ordenar no puede ser null.");
    long iterations = 0;
    double start = System.nanoTime();

    if (lista.getTamanno() <= 1) {
        return new SortResult(iterations, 0);
    }

    Nodo<T> cabeza = lista.getCabeza();
    lista.getUltimo().setSiguiente(null); // Rompe circularidad temporal

    // Aplicar quicksort y obtener la nueva cabeza
    QuickSortResult<T> resultado = quickSort(cabeza, null);
    iterations = resultado.iterations;

    // Restaurar circularidad
    Nodo<T> nuevoUltimo = resultado.cabeza;
    while (nuevoUltimo.getSiguiente() != null) {
        nuevoUltimo = nuevoUltimo.getSiguiente();
    }
    nuevoUltimo.setSiguiente(resultado.cabeza);
    lista.setCabeza(resultado.cabeza);

    double elapsedMillis = (System.nanoTime() - start) / 1_000_000;
    return new SortResult(iterations, elapsedMillis);
}

private static class QuickSortResult<T> {
    Nodo<T> cabeza;
    long iterations;

    QuickSortResult(Nodo<T> cabeza, long iterations) {
        this.cabeza = cabeza;
        this.iterations = iterations;
    }
}

private QuickSortResult<T> quickSort(Nodo<T> inicio, Nodo<T> fin) {
    if (inicio == fin || inicio == null || inicio.getSiguiente() == fin) {
        return new QuickSortResult<>(inicio, 0);
    }

    long iterations = 0;
    Nodo<T> pivot = inicio;
    Nodo<T> i = inicio;
    Nodo<T> j = inicio.getSiguiente();

    while (j != fin) {
        iterations++;
        if (j.getDato().compareTo(pivot.getDato()) < 0) { // ASCENDENTE
            i = i.getSiguiente();
            swap(i, j);
        }
        j = j.getSiguiente();
    }

    swap(i, pivot);

    QuickSortResult<T> left = quickSort(inicio, i);
    QuickSortResult<T> right = quickSort(i.getSiguiente(), fin);

    iterations += left.iterations + right.iterations;

    return new QuickSortResult<>(left.cabeza, iterations);
}

private void swap(Nodo<T> a, Nodo<T> b) {
    T temp = a.getDato();
    a.setDato(b.getDato());
    b.setDato(temp);
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
