package com.mycompany.sort.model.SortingStrategy;

import com.mycompany.sort.model.politico.Politico;
import com.mycompany.sort.model.politico.Nodo;
import com.mycompany.sort.model.politico.ListaEnlazadaSimple;

import java.util.Objects;

/**
 * Implementación del algoritmo Quick Sort para ordenar arreglos de {@link Politico}.
 * Utiliza una pila para evitar recursión y selecciona el pivote usando la técnica de mediana de tres.
 */
public class QuickSortingStrategy<T extends Comparable<T>> implements SortingStrategy<T> {

    private long iterations;

    /**
     * Ordena una lista enlazada simple de objetos {@link Politico} utilizando Quick Sort de forma iterativa.
     *
     * @param lista        la lista enlazada simple de políticos a ordenar
     * @return un objeto {@link SortResult} con estadísticas de rendimiento
     */
    @Override
    public SortResult sort(ListaEnlazadaSimple<T> lista) {
        Objects.requireNonNull(lista, "La lista a ordenar no puede ser null.");

        iterations = 0;
        double start = System.nanoTime();
        int n = lista.getTamanno();

        if (n <= 1) {
            return new SortResult(iterations , 0);
        }

        Nodo<T> cabeza = lista.getCabeza();
        Nodo<T> cola = encontrarCola(cabeza); 
        quickSortRecursivo(cabeza, cola);

        lista.setCabeza(cabeza);
        double elapsedMillis = (System.nanoTime() - start) / 1_000_000;
        return new SortResult(iterations, elapsedMillis);
    }

    /**
 * Encuentra el nodo final (cola) de una lista enlazada.
 * Recorre la lista desde el nodo proporcionado hasta el último nodo.
 *
 * @param nodo El nodo desde el cual comenzar la búsqueda de la cola.
 * @return El nodo que representa la cola de la lista, o {@code null} si la lista está vacía.
 */

    private Nodo<T> encontrarCola(Nodo<T> nodo) {
        if (nodo == null) {
            return null;
        }
        while (nodo.getSiguiente() != null) {
            nodo = nodo.getSiguiente();
        }
        return nodo;
    }

    /**
 * Realiza el algoritmo de ordenamiento QuickSort de manera recursiva en una sublista de la lista enlazada.
 * Realiza particiones sobre la lista hasta que cada sublista quede ordenada.
 *
 * @param cabezaSubLista El nodo cabeza de la sublista que se va a ordenar.
 * @param colaSubLista El nodo cola de la sublista que se va a ordenar.
 */

    private void quickSortRecursivo(Nodo<T> cabezaSubLista, Nodo<T> colaSubLista) {

        if (cabezaSubLista == null || colaSubLista == null || cabezaSubLista == colaSubLista || cabezaSubLista == colaSubLista.getSiguiente()) {
            return;
        }

        Nodo<T>[] resultadoParticion = particionar(cabezaSubLista, colaSubLista);
        Nodo<T> nodoPivoteFinal = resultadoParticion[0];
        Nodo<T> nodoAntesPivote = resultadoParticion[1];

        if (nodoAntesPivote != null && nodoPivoteFinal != cabezaSubLista) {
           quickSortRecursivo(cabezaSubLista, nodoAntesPivote);
        } else if (nodoAntesPivote == null && nodoPivoteFinal != cabezaSubLista){
        }

         if (nodoPivoteFinal != null && nodoPivoteFinal != colaSubLista) { 
             quickSortRecursivo(nodoPivoteFinal.getSiguiente(), colaSubLista);
         }
    }

    /**
 * Particiona la sublista de la lista enlazada en torno a un pivote.
 * Los elementos mayores al pivote se colocan a la izquierda y los menores a la derecha.
 *
 * @param cabeza El nodo cabeza de la sublista que se va a particionar.
 * @param cola El nodo cola de la sublista que se va a particionar.
 * @return Un arreglo de nodos donde el primer elemento es el nodo pivote final y el segundo elemento es el nodo antes del pivote.
 */

    private Nodo<T>[] particionar(Nodo<T> cabeza, Nodo<T> cola) {
        T valorPivote = cola.getDato();

        Nodo<T> i = null;
        Nodo<T> actual = cabeza;

        while (actual != cola) {
            if (actual.getDato().compareTo(valorPivote) > 0) {
                i = (i == null) ? cabeza : i.getSiguiente(); 
                T temp = actual.getDato();
                try {
                     actual.setDato(i.getDato());
                     i.setDato(temp);
                } catch (Exception e) {
                     throw new UnsupportedOperationException(
                         "La partición de QuickSort requiere un método setDato(T) en la clase Nodo.", e);
                }
            }
            actual = actual.getSiguiente(); 
        }

        i = (i == null) ? cabeza : i.getSiguiente(); 
        T temp = cola.getDato();
         try {
            cola.setDato(i.getDato());
            i.setDato(temp);
         } catch (Exception e) {
            throw new UnsupportedOperationException(
                "La partición de QuickSort requiere un método setDato(T) en la clase Nodo.", e);
         }


        Nodo<T> nodoAntesPivote = null;
        if (i != cabeza) {
            Nodo<T> buscador = cabeza;
            while (buscador != null && buscador.getSiguiente() != i) {
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
        return "Quick Sort lista enlazada simple";
    }
}
