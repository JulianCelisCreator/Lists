package com.mycompany.sort.model.SortingStrategy;

import com.mycompany.sort.model.politico.Politico;

import java.util.Comparator;
import java.util.Stack;

import java.util.Objects;

/**
 * Implementación del algoritmo Quick Sort para ordenar arreglos de {@link Politico}.
 * Utiliza una pila para evitar recursión y selecciona el pivote usando la técnica de mediana de tres.
 */
public class QuickSortingStrategy<T extends Comparable<T>> implements SortingStrategy {

    private long iterations;

    /**
     * Ordena un arreglo de objetos {@link Politico} utilizando Quick Sort de forma iterativa.
     *
     * @param arr        el arreglo de políticos a ordenar
     * @param comparator el comparador que define el criterio de ordenamiento
     * @return un objeto {@link SortResult} con estadísticas de rendimiento
     */
    @Override
    public SortResult sort(Politico[] arr, Comparator<Politico> comparator) {
        iterations = 0;
        double start = System.nanoTime();

        Stack<Integer> stack = new Stack<>();
        stack.push(0);
        stack.push(arr.length - 1);

        while (!stack.isEmpty()) {
            int high = stack.pop();
            int low = stack.pop();

            if (low < high) {
                int pivotIndex = partition(arr, low, high, comparator);

                // Empujar los subarreglos restantes a la pila
                stack.push(low);
                stack.push(pivotIndex - 1);
                stack.push(pivotIndex + 1);
                stack.push(high);
            }
        }

        double elapsedMillis = (System.nanoTime() - start) / 1_000_000;
        return new SortResult(iterations, elapsedMillis);
    }

    /**
     * Particiona el arreglo alrededor de un pivote seleccionado mediante mediana de tres.
     *
     * @param arr        el arreglo a particionar
     * @param low        índice inferior del subarreglo
     * @param high       índice superior del subarreglo
     * @param comparator el comparador a usar
     * @return índice final del pivote después de la partición
     */
    private int partition(Politico[] arr, int low, int high, Comparator<Politico> comparator) {
        int mid = low + (high - low) / 2;

        // Selección del pivote con mediana de tres
        if (comparator.compare(arr[low], arr[mid]) > 0) swap(arr, low, mid);
        if (comparator.compare(arr[low], arr[high]) > 0) swap(arr, low, high);
        if (comparator.compare(arr[mid], arr[high]) > 0) swap(arr, mid, high);

        swap(arr, mid, high);
        Politico pivot = arr[high];

        int i = low - 1;
        for (int j = low; j < high; j++) {
            iterations++;
            if (comparator.compare(arr[j], pivot) <= 0) {
                i++;
                swap(arr, i, j);
            }
        }

        swap(arr, i + 1, high);
        return i + 1;
    }

    /**
     * Intercambia dos elementos en el arreglo.
     *
     * @param arr el arreglo de políticos
     * @param i   índice del primer elemento
     * @param j   índice del segundo elemento
     */
    private void swap(Politico[] arr, int i, int j) {
        Politico temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    @Override
    public SortResult sort(ListaEnlazadaSimple<T> lista) {
        Objects.requireNonNull(lista, "La lista a ordenar no puede ser null.");

        iterations = 0;
        double start = System.nanoTime();
        int n = lista.getTamanno();

        if (n <= 1) {
            return;
        }

        Nodo<T> cabeza = lista.getCabeza();
        Nodo<T> cola = encontrarCola(cabeza); 
        quickSortRecursivo(cabeza, cola);

        lista.setCabeza(cabeza);
        double elapsedMillis = (System.nanoTime() - start) / 1_000_000;
        return new SortResult(iterations, elapsedMillis);
    }

    private Nodo<T> encontrarCola(Nodo<T> nodo) {
        if (nodo == null) {
            return null;
        }
        while (nodo.getSiguiente() != null) {
            nodo = nodo.getSiguiente();
        }
        return nodo;
    }

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
