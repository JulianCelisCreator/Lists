package com.mycompany.sort.model.SortingStrategyListaCircular;

import com.mycompany.sort.model.ResultadoOrdenamiento;
import com.mycompany.sort.model.politico.ListaEnlazadaSimpleCircular;
import com.mycompany.sort.model.politico.Nodo;

import java.util.Objects;

public class QuickSortingListaCircular<T extends Comparable<T>> implements SortingStrategyListaCircular<T> {

    @Override
    public ResultadoOrdenamiento sort(ListaEnlazadaSimpleCircular<T> listaOriginal) {
        Objects.requireNonNull(listaOriginal, "La lista a ordenar no puede ser null.");

        ListaEnlazadaSimpleCircular<T> lista = copiarLista(listaOriginal);

        long iterations = 0;
        double start = System.nanoTime();

        if (lista.estaVacia() || lista.getTamanno() == 1) {
            return new ResultadoOrdenamiento(iterations, 0);
        }

        Nodo<T> cabeza = lista.getCabeza();
        Nodo<T> ultimo = lista.getUltimo();

        if (cabeza == null || ultimo == null) {
            return new ResultadoOrdenamiento(iterations, 0);
        }

        // Romper circularidad
        ultimo.setSiguiente(null);

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
        return new ResultadoOrdenamiento(iterations, elapsedMillis);
    }

    private static class QuickSortResult<T> {
        Nodo<T> cabeza;
        long iterations;

        QuickSortResult(Nodo<T> cabeza, long iterations) {
            this.cabeza = cabeza;
            this.iterations = iterations;
        }
    }

    private QuickSortResult<T> quickSort(Nodo<T> head, Nodo<T> end) {
        if (head == null || head == end || head.getSiguiente() == end) {
            return new QuickSortResult<>(head, 0);
        }

        // Select middle node as pivot and swap with head to avoid worst-case
        Nodo<T> middle = findMiddle(head, end);
        swap(head, middle);

        long iterations = 0;
        Nodo<T> pivot = head;
        Nodo<T> i = head;
        Nodo<T> j = head.getSiguiente();

        while (j != end) {
            iterations++;
            if (j.getDato().compareTo(pivot.getDato()) < 0) {
                i = i.getSiguiente();
                swap(i, j);
            }
            j = j.getSiguiente();
        }

        swap(head, i); // Place pivot in correct position

        // Recursively sort left and right partitions
        QuickSortResult<T> left = quickSort(head, i);
        QuickSortResult<T> right = quickSort(i.getSiguiente(), end);

        iterations += left.iterations + right.iterations;

        return new QuickSortResult<>(left.cabeza, iterations);
    }

    private Nodo<T> findMiddle(Nodo<T> head, Nodo<T> end) {
        if (head == null) return null;
        Nodo<T> slow = head;
        Nodo<T> fast = head.getSiguiente();
        while (fast != end && fast.getSiguiente() != end) {
            slow = slow.getSiguiente();
            fast = fast.getSiguiente().getSiguiente();
        }
        return slow;
    }

    private void swap(Nodo<T> a, Nodo<T> b) {
        if (a == b) return;
        T temp = a.getDato();
        a.setDato(b.getDato());
        b.setDato(temp);
    }

    private ListaEnlazadaSimpleCircular<T> copiarLista(ListaEnlazadaSimpleCircular<T> original) {
        ListaEnlazadaSimpleCircular<T> copia = new ListaEnlazadaSimpleCircular<>();
        if (original.estaVacia()) return copia;

        Nodo<T> actual = original.getCabeza();
        for (int i = 0; i < original.getTamanno(); i++) {
            copia.insertarAlFinal(actual.getDato());
            actual = actual.getSiguiente();
        }
        return copia;
    }

    @Override
    public String getName() {
        return "Quick Sort lista enlazada simple circular";
    }
}