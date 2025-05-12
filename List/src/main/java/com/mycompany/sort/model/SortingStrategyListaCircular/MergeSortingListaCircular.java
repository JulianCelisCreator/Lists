package com.mycompany.sort.model.SortingStrategyListaCircular;

import com.mycompany.sort.model.ResultadoOrdenamiento;
import com.mycompany.sort.model.politico.ListaEnlazadaSimpleCircular;
import com.mycompany.sort.model.politico.Nodo;

import java.util.Objects;

public class MergeSortingListaCircular<T extends Comparable<T>> implements SortingStrategyListaCircular<T> {

    @Override
    public ResultadoOrdenamiento sort(ListaEnlazadaSimpleCircular<T> lista) {
        Objects.requireNonNull(lista, "La lista a ordenar no puede ser null.");
        long iterations = 0;
        double start = System.nanoTime();

        int n = lista.getTamanno();
        if (n <= 1) {
            return new ResultadoOrdenamiento(iterations, 0);
        }

        Nodo<T> cabeza = lista.getCabeza();
        lista.getUltimo().setSiguiente(null); // Romper circularidad temporalmente

        // Llamar al mergeSort y obtener la nueva cabeza ordenada
        SortWrapper<T> resultado = mergeSort(cabeza);
        iterations = resultado.iterations;

        // Reconstruir la lista circular
        Nodo<T> nuevoUltimo = resultado.nodo;
        while (nuevoUltimo.getSiguiente() != null) {
            nuevoUltimo = nuevoUltimo.getSiguiente();
        }
        nuevoUltimo.setSiguiente(resultado.nodo); // Hacer circular otra vez
        lista.setCabeza(resultado.nodo);

        double elapsedMillis = (System.nanoTime() - start) / 1_000_000;
        System.out.println("terminado merge");
        return new ResultadoOrdenamiento(iterations, elapsedMillis);
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
        medio.setSiguiente(null); // Romper la lista temporalmente

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
            if (izquierda.getDato().compareTo(derecha.getDato()) <= 0) {
                if (resultado == null) {
                    resultado = izquierda;
                    actual = resultado;
                } else {
                    actual.setSiguiente(izquierda);
                    actual = actual.getSiguiente();
                }
                izquierda = izquierda.getSiguiente();
            } else {
                if (resultado == null) {
                    resultado = derecha;
                    actual = resultado;
                } else {
                    actual.setSiguiente(derecha);
                    actual = actual.getSiguiente();
                }
                derecha = derecha.getSiguiente();
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

    @Override
    public String getName() {
        return "Merge Sorting lista enlazada simple circular";
    }
}
