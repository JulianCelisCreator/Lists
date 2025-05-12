package com.mycompany.sort.model.SortingStrategyListaEnlazadaDoble;

import com.mycompany.sort.model.ResultadoOrdenamiento;
import com.mycompany.sort.model.politico.ListaEnlazadaDoble;
import com.mycompany.sort.model.politico.NodoDoble;

import java.util.Objects;

public class MergeSortingListaEnlazadaDoble<T extends Comparable<T>> implements SortingStrategyEnlazadaDoble<T> {

    private long iterations;

    @Override
    public ResultadoOrdenamiento sort(ListaEnlazadaDoble<T> lista) {
        Objects.requireNonNull(lista, "La lista a ordenar no puede ser null.");

        iterations = 0;
        double start = System.nanoTime();
        int n = lista.getTamanno();
        if (n <= 1) {
            return new ResultadoOrdenamiento(iterations, 0);
        }

        NodoDoble<T> nuevaCabeza = mergeSortRecursivo(lista.getCabeza());

        // Reestablecer los punteros anteriores
        NodoDoble<T> actual = nuevaCabeza;
        NodoDoble<T> anterior = null;
        while (actual != null) {
            actual.setAnterior(anterior);
            anterior = actual;
            actual = actual.getSiguiente();
        }

        lista.setCabeza(nuevaCabeza);
        double elapsedMillis = (System.nanoTime() - start) / 1_000_000;

        return new ResultadoOrdenamiento(iterations, elapsedMillis);
    }

    private NodoDoble<T> mergeSortRecursivo(NodoDoble<T> cabeza) {
        if (cabeza == null || cabeza.getSiguiente() == null) {
            return cabeza;
        }

        NodoDoble<T> medio = encontrarMedio(cabeza);
        NodoDoble<T> derecha = medio.getSiguiente();
        medio.setSiguiente(null);
        if (derecha != null) {
            derecha.setAnterior(null);
        }

        NodoDoble<T> izquierdaOrdenada = mergeSortRecursivo(cabeza);
        NodoDoble<T> derechaOrdenada = mergeSortRecursivo(derecha);

        return fusionar(izquierdaOrdenada, derechaOrdenada);
    }

    private NodoDoble<T> encontrarMedio(NodoDoble<T> cabeza) {
        NodoDoble<T> lento = cabeza;
        NodoDoble<T> rapido = cabeza.getSiguiente();

        while (rapido != null && rapido.getSiguiente() != null) {
            lento = lento.getSiguiente();
            rapido = rapido.getSiguiente().getSiguiente();
        }
        return lento;
    }

    private NodoDoble<T> fusionar(NodoDoble<T> izquierda, NodoDoble<T> derecha) {
        if (izquierda == null) return derecha;
        if (derecha == null) return izquierda;

        NodoDoble<T> cabeza = null;
        NodoDoble<T> actual = null;

        // ✅ ORDEN ASCENDENTE
        while (izquierda != null && derecha != null) {
            iterations++;
            if (izquierda.getDato().compareTo(derecha.getDato()) <= 0) {
                if (cabeza == null) {
                    cabeza = izquierda;
                    actual = cabeza;
                } else {
                    actual.setSiguiente(izquierda);
                    izquierda.setAnterior(actual); // Actualizar puntero anterior
                    actual = actual.getSiguiente();
                }
                izquierda = izquierda.getSiguiente();
            } else {
                if (cabeza == null) {
                    cabeza = derecha;
                    actual = cabeza;
                } else {
                    actual.setSiguiente(derecha);
                    derecha.setAnterior(actual); // Actualizar puntero anterior
                    actual = actual.getSiguiente();
                }
                derecha = derecha.getSiguiente();
            }
        }

        if (izquierda != null) {
            actual.setSiguiente(izquierda);
            izquierda.setAnterior(actual); // Actualizar puntero anterior
        } else if (derecha != null) {
            actual.setSiguiente(derecha);
            derecha.setAnterior(actual); // Actualizar puntero anterior
        }

        return cabeza;
    }

    @Override
    public String getName() {
        return "Merge Sorting lista enlazada doble";
    }
}
