package com.mycompany.sort.model.SortingStrategy;

import java.util.Comparator;

import com.mycompany.sort.model.politico.Politico;

import java.util.Objects;

/**
 * Implementación del algoritmo Merge Sort para ordenar arreglos de {@link Politico}.
 * Este algoritmo utiliza el enfoque de divide y vencerás para ordenar eficientemente los datos.
 */
public class MergeSortingStrategy<T extends Comparable<T>> implements SortingStrategy {

    private long iterations;

    /**
     * Ordena un arreglo de objetos {@link Politico} usando Merge Sort
     * y un comparador definido por el usuario.
     *
     * @param arr         el arreglo de políticos a ordenar
     * @param comparator  el comparador que define el criterio de ordenamiento
     * @return objeto {@link SortResult} con métricas de rendimiento
     */
    @Override
    public SortResult sort(Politico[] arr, Comparator<Politico> comparator) {
        iterations = 0;
        double start = System.nanoTime();

        mergeSort(arr, 0, arr.length - 1, comparator);

        double end = System.nanoTime() - start;
        double elapsedMillis = end / 1_000_000;

        return new SortResult(iterations, elapsedMillis);
    }

    /**
     * Método recursivo para dividir el arreglo y aplicar Merge Sort.
     */
    private void mergeSort(Politico[] arr, int left, int right, Comparator<Politico> comparator) {
        if (left < right) {
            int mid = (left + right) / 2;

            mergeSort(arr, left, mid, comparator);
            mergeSort(arr, mid + 1, right, comparator);

            merge(arr, left, mid, right, comparator);
        }
    }

    /**
     * Fusión de dos subarreglos ordenados de {@link Politico} en uno solo ordenado.
     */
    private void merge(Politico[] arr, int left, int mid, int right, Comparator<Politico> comparator) {
        int n1 = mid - left + 1;
        int n2 = right - mid;

        Politico[] leftArr = new Politico[n1];
        Politico[] rightArr = new Politico[n2];

        System.arraycopy(arr, left, leftArr, 0, n1);
        System.arraycopy(arr, mid + 1, rightArr, 0, n2);

        int i = 0, j = 0, k = left;

        while (i < n1 && j < n2) {
            iterations++;
            if (comparator.compare(leftArr[i], rightArr[j]) <= 0) {
                arr[k++] = leftArr[i++];
            } else {
                arr[k++] = rightArr[j++];
            }
        }

        while (i < n1) {
            arr[k++] = leftArr[i++];
        }

        while (j < n2) {
            arr[k++] = rightArr[j++];
        }
    }

    @Override
    public SortResult sort(ListaEnlazadaSimple<T> lista) {
        Objects.requireNonNull(lista, "La lista a ordenar no puede ser null.");

        iterations = 0;
        double start = System.nanoTime();
        int n = lista.getTamanno();
        if (n <= 1) {
            return new SortResult(iterations, 0); 
        }

        Nodo<T> nuevaCabeza = mergeSortRecursivo(lista.getCabeza());

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
        Nodo<T> segundaMitadCabeza = medio.getSiguiente();
        medio.setSiguiente(null);

        Nodo<T> izquierdaOrdenada = mergeSortRecursivo(cabeza);
        Nodo<T> derechaOrdenada = mergeSortRecursivo(segundaMitadCabeza);

        return fusionar(izquierdaOrdenada, derechaOrdenada);
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
        if (izquierda == null) {
            return derecha;
        }
        if (derecha == null) {
            return izquierda;
        }

        Nodo<T> cabezaResultado;

        if (izquierda.getDato().compareTo(derecha.getDato()) >= 0) {
            cabezaResultado = izquierda;
            cabezaResultado.setSiguiente(fusionar(izquierda.getSiguiente(), derecha));
        } else {
            cabezaResultado = derecha;
            cabezaResultado.setSiguiente(fusionar(izquierda, derecha.getSiguiente()));
        }
        return cabezaResultado;
    }

    /**
     * Devuelve el nombre legible del algoritmo de ordenamiento.
     *
     * @return el nombre "Merge Sorting"
     */
    @Override
    public String getName() {
        return "Merge Sorting lista enlazada simple";
    }
}
