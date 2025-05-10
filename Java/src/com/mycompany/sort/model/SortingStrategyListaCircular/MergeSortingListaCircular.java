package com.mycompany.sort.model.SortingStrategyListaCircular;

import com.mycompany.sort.model.SortingStrategy.SortResult;
import com.mycompany.sort.model.politico.ListaEnlazadaSimpleCircular;
import com.mycompany.sort.model.politico.Nodo;

import java.util.Objects;

public class MergeSortingListaCircular<T extends Comparable<T>> implements SortingStrategyListaCircular<T> {
    
    /**
     * Ordena una lista enlazada simple circular de objetos {@link Politico} usando Merge Sort
     *
     * @param lista         la lista enlazada simple circular de políticos a ordenar
     * @return objeto {@link SortResult} con métricas de rendimiento
     */

    @Override
public SortResult sort(ListaEnlazadaSimpleCircular<T> lista) {
    Objects.requireNonNull(lista, "La lista a ordenar no puede ser null.");
    long iterations = 0;
    double start = System.nanoTime();

    int n = lista.getTamanno();
    if (n <= 1) {
        return new SortResult(iterations, 0);
    }

    // Paso 1: Convertir la lista circular en lista lineal para facilitar el merge
    Nodo<T> cabeza = lista.getCabeza();
    lista.getUltimo().setSiguiente(null); // Romper circularidad temporalmente

    // Paso 2: Llamar a mergeSort y obtener la nueva cabeza ordenada
    SortWrapper<T> resultado = mergeSort(cabeza);
    iterations = resultado.iterations;

    // Paso 3: Reconstruir la lista circular
    Nodo<T> nuevoUltimo = resultado.nodo;
    while (nuevoUltimo.getSiguiente() != null) {
        nuevoUltimo = nuevoUltimo.getSiguiente();
    }
    nuevoUltimo.setSiguiente(resultado.nodo); // Hacer circular otra vez
    lista.setCabeza(resultado.nodo);
    
    double elapsedMillis = (System.nanoTime() - start) / 1_000_000;
    return new SortResult(iterations, elapsedMillis);
}

private static class SortWrapper<T> {
    Nodo<T> nodo;
    long iterations;

    SortWrapper(Nodo<T> nodo, long iterations) {
        this.nodo = nodo;
        this.iterations = iterations;
    }
}


private SortWrapper<T> mergeSort(Nodo<T> cabeza) {
    if (cabeza == null || cabeza.getSiguiente() == null) {
        return new SortWrapper<>(cabeza, 0);
    }

    // Dividir la lista en dos mitades
    Nodo<T> medio = getMiddle(cabeza);
    Nodo<T> siguienteDeMedio = medio.getSiguiente();
    medio.setSiguiente(null); // Romper

    SortWrapper<T> izquierda = mergeSort(cabeza);
    SortWrapper<T> derecha = mergeSort(siguienteDeMedio);
    
    long totalIterations = izquierda.iterations + derecha.iterations;

    // Combinar listas ordenadas
    SortWrapper<T> combinada = merge(izquierda.nodo, derecha.nodo);
    totalIterations += combinada.iterations;

    return new SortWrapper<>(combinada.nodo, totalIterations);
}

private SortWrapper<T> merge(Nodo<T> izquierda, Nodo<T> derecha) {
    Nodo<T> resultado = null;
    Nodo<T> actual = null;
    long iterations = 0;

    while (izquierda != null && derecha != null) {
        iterations++;
        Nodo<T> temp;
        if (izquierda.getDato().compareTo(derecha.getDato()) <= 0) { // ASCENDENTE
            temp = izquierda;
            izquierda = izquierda.getSiguiente();
        } else {
            temp = derecha;
            derecha = derecha.getSiguiente();
        }

        temp.setSiguiente(null);
        if (resultado == null) {
            resultado = temp;
            actual = resultado;
        } else {
            actual.setSiguiente(temp);
            actual = actual.getSiguiente();
        }
    }

    if (izquierda != null) {
        actual.setSiguiente(izquierda);
    } else if (derecha != null) {
        actual.setSiguiente(derecha);
    }

    return new SortWrapper<>(resultado, iterations);
}

private Nodo<T> getMiddle(Nodo<T> cabeza) {
    if (cabeza == null) return null;

    Nodo<T> lenta = cabeza;
    Nodo<T> rapida = cabeza.getSiguiente();

    while (rapida != null && rapida.getSiguiente() != null) {
        lenta = lenta.getSiguiente();
        rapida = rapida.getSiguiente().getSiguiente();
    }

    return lenta;
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
