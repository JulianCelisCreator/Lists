package com.mycompany.sort.model.SortingStrategy;

import com.mycompany.sort.model.ResultadoOrdenamiento;
import com.mycompany.sort.model.politico.ListaEnlazadaSimple;
import com.mycompany.sort.model.politico.Nodo;
import java.util.Objects;
import java.util.Random;

public class QuickSortingStrategy<T extends Comparable<T>> implements SortingStrategy<T> {

    private int iterations;

    @Override
    public ResultadoOrdenamiento sort(ListaEnlazadaSimple<T> lista) {
        Objects.requireNonNull(lista, "La lista a ordenar no puede ser null");

        long startTime = System.nanoTime();
        iterations = 0;

        int tamaño = lista.getTamanno();
        if (tamaño <= 1) {
            return new ResultadoOrdenamiento(0, 0); // Lista vacía o de 1 elemento
        }

        try {
            Nodo<T> cabeza = lista.getCabeza();
            Nodo<T> cola = obtenerCola(cabeza);
            quickSort(cabeza, cola);
        } catch (Exception e) {
            // En caso de error, devolver valores seguros
            return new ResultadoOrdenamiento(0, 0);
        }

        double elapsedMillis = (System.nanoTime() - startTime) / 1_000_000.0;
        // Asegurar que los valores no sean negativos
        elapsedMillis = Math.max(0, elapsedMillis);
        iterations = Math.max(0, iterations);

        return new ResultadoOrdenamiento(iterations, elapsedMillis);
    }

    private void quickSort(Nodo<T> inicio, Nodo<T> fin) {
        if (inicio == null || fin == null || inicio == fin) {
            return;
        }

        Nodo<T>[] particion = particionar(inicio, fin);
        Nodo<T> pivote = particion[0];
        Nodo<T> anteriorPivote = particion[1];

        if (anteriorPivote != null && inicio != pivote) {
            quickSort(inicio, anteriorPivote);
        }

        if (pivote != null && pivote != fin && pivote.getSiguiente() != null) {
            quickSort(pivote.getSiguiente(), fin);
        }
    }

    private Nodo<T>[] particionar(Nodo<T> inicio, Nodo<T> fin) {
        Nodo<T> pivoteRandom = seleccionarPivoteAleatorio(inicio, fin);
        T tempPivote = fin.getDato();
        fin.setDato(pivoteRandom.getDato());
        pivoteRandom.setDato(tempPivote);

        T valorPivote = fin.getDato();
        Nodo<T> i = null;
        Nodo<T> j = inicio;

        while (j != fin) {
            iterations = Math.max(iterations + 1, 0); // Evitar desbordamiento

            if (j.getDato().compareTo(valorPivote) <= 0) {
                i = (i == null) ? inicio : i.getSiguiente();
                T temp = i.getDato();
                i.setDato(j.getDato());
                j.setDato(temp);
            }
            j = j.getSiguiente();
        }

        i = (i == null) ? inicio : i.getSiguiente();
        T temp = fin.getDato();
        fin.setDato(i.getDato());
        i.setDato(temp);

        Nodo<T> anteriorPivote = null;
        if (i != inicio) {
            anteriorPivote = inicio;
            while (anteriorPivote.getSiguiente() != i) {
                anteriorPivote = anteriorPivote.getSiguiente();
            }
        }

        @SuppressWarnings("unchecked")
        Nodo<T>[] resultado = (Nodo<T>[]) new Nodo<?>[2];
        resultado[0] = i;
        resultado[1] = anteriorPivote;
        return resultado;
    }

    private Nodo<T> seleccionarPivoteAleatorio(Nodo<T> inicio, Nodo<T> fin) {
        int length = 0;
        Nodo<T> current = inicio;
        while (current != fin) {
            length++;
            current = current.getSiguiente();
        }
        length++;

        Random rand = new Random();
        int randomIndex = rand.nextInt(length);
        current = inicio;
        for (int i = 0; i < randomIndex; i++) {
            current = current.getSiguiente();
        }
        return current;
    }

    private Nodo<T> obtenerCola(Nodo<T> cabeza) {
        if (cabeza == null) return null;
        while (cabeza.getSiguiente() != null) {
            cabeza = cabeza.getSiguiente();
        }
        return cabeza;
    }

    @Override
    public String getName() {
        return "QuickSort (Lista Enlazada)";
    }
}