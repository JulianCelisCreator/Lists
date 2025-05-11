package com.mycompany.sort.model.politico;

import java.util.Objects;

/**
 * Implementación de una lista doblemente enlazada genérica.
 * Cada nodo mantiene referencias al nodo anterior y al siguiente, permitiendo
 * recorrido bidireccional y operaciones eficientes (O(1)) de inserción/eliminación
 * en ambos extremos (cabeza y cola).
 *
 * @param <T> El tipo de elementos almacenados en la lista.
 */
public class ListaEnlazadaDoble<T> {
    
    /** Referencia al primer nodo de la lista (cabeza). {@code null} si la lista está vacía. */
    private NodoDoble<T> cabeza;
    /** Referencia al último nodo de la lista (cola). {@code null} si la lista está vacía. */
    private NodoDoble<T> cola;
    /** Número actual de elementos en la lista. */
    private int tamanno;

    /**
     * Construye una lista doblemente enlazada vacía.
     */
    public ListaEnlazadaDoble() {
        this.cabeza = null;
        this.cola = null;
        this.tamanno = 0;
    }

    /**
     * Devuelve el número de elementos en la lista.
     * @return El tamaño actual de la lista.
     */
    public int getTamanno() {
        return tamanno;
    }

    /**
 * Obtiene el primer nodo de la lista doblemente enlazada (cabeza).
 * Si la lista está vacía, devuelve {@code null}.
 *
 * @return El primer nodo de la lista, o {@code null} si la lista está vacía.
 */
public NodoDoble<T> getCabeza() {
    return this.cabeza;
}

/**
 * Establece la cabeza de la lista doblemente enlazada.
 * 
 * @param cabeza El nuevo nodo cabeza de la lista.
 */
public void setCabeza(NodoDoble<T> cabeza) {
    this.cabeza = cabeza;
}


    /**
     * Comprueba si la lista está vacía.
     * @return {@code true} si la lista no tiene elementos, {@code false} en caso contrario.
     */
    public boolean estaVacia() {
        return tamanno == 0;
    }


    /**
     * Inserta un elemento al principio de la lista (nueva cabeza).
     * Operación de tiempo constante O(1).
     * @param dato El dato a insertar.
     */
    public void insertarAlInicio(T dato) {
        NodoDoble<T> nuevoNodo = new NodoDoble<>(dato, null, this.cabeza); // Ant: null, Sig: cabeza antigua
        if (estaVacia()) {
            this.cabeza = nuevoNodo;
            this.cola = nuevoNodo;
        } else {
            this.cabeza.setAnterior(nuevoNodo);
            this.cabeza = nuevoNodo;
        }
        this.tamanno++;
    }

    /**
     * Inserta un elemento al final de la lista (nueva cola).
     * Operación de tiempo constante O(1).
     * @param dato El dato a insertar.
     */
    public void insertarAlFinal(T dato) {
        if (estaVacia()) {
            insertarAlInicio(dato); 
            return;
        }
        NodoDoble<T> nuevoNodo = new NodoDoble<>(dato, this.cola, null);
        this.cola.setSiguiente(nuevoNodo);
        this.cola = nuevoNodo;
        this.tamanno++;
    }

    /**
     * Elimina la primera ocurrencia del elemento especificado {@code dato} de la lista.
     * Utiliza {@code equals()} para la comparación. La búsqueda es O(n).
     *
     * @param dato El dato del elemento a eliminar.
     * @return {@code true} si el elemento fue encontrado y eliminado, {@code false} en caso contrario.
     */
    public boolean eliminar(T dato) {
        NodoDoble<T> nodoAEliminar = buscarNodo(dato);
        if (nodoAEliminar == null) {
            return false; 
        }
        eliminarNodo(nodoAEliminar);
        return true;
    }

    /**
     * Elimina el nodo especificado de la lista, actualizando correctamente
     * los enlaces de sus vecinos (si los tiene) y las referencias cabeza/cola
     * y el tamaño de la lista si es necesario.
     * Este método centraliza la lógica de eliminación de nodos.
     *
     * @param nodoAEliminar El nodo que se va a quitar de la lista (no debe ser null).
     */
    private void eliminarNodo(NodoDoble<T> nodoAEliminar) {

        NodoDoble<T> nodoAnterior = nodoAEliminar.getAnterior();
        NodoDoble<T> nodoSiguiente = nodoAEliminar.getSiguiente();

        if (nodoAnterior == null) {
            this.cabeza = nodoSiguiente;
        } else {
            nodoAnterior.setSiguiente(nodoSiguiente);
        }

        if (nodoSiguiente == null) {
            this.cola = nodoAnterior;
        } else {
            nodoSiguiente.setAnterior(nodoAnterior);
        }

        this.tamanno--;

        nodoAEliminar.setAnterior(null);
        nodoAEliminar.setSiguiente(null);
    }

    /**
     * Busca el primer nodo en la lista que contiene el {@code datoBusqueda}.
     * Utiliza {@code Objects.equals()} para manejar {@code null} de forma segura.
     *
     * @param datoBusqueda El dato a buscar.
     * @return El {@link NodoDoble} que contiene el dato, o {@code null} si no se encuentra.
     */
    private NodoDoble<T> buscarNodo(T datoBusqueda) {
        NodoDoble<T> actual = this.cabeza;
        while (actual != null) {
            if (Objects.equals(actual.getDato(), datoBusqueda)) {
                return actual;
            }
            actual = actual.getSiguiente();
        }
        return null; 
    }

}
