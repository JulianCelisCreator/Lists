package com.mycompany.sort.model.politico;

import java.util.Objects;

/**
 * Representa un nodo genérico para ser utilizado en listas enlazadas simples
 * (incluyendo {@link ListaEnlazadaSimple} y {@link ListaEnlazadaSimpleCircular}).
 * Almacena un dato de tipo {@code T} y una referencia al siguiente nodo en la secuencia.
 *
 * @param <T> El tipo del dato almacenado en el nodo.
 */

public class Nodo<T> {
    
    /** El dato o elemento almacenado en este nodo. */
    private T dato;
    /** Referencia al siguiente nodo en la lista, o {@code null} si este es el último. */
    private Nodo<T> siguiente;

    /**
     * Construye un nodo con el dato especificado y sin nodo siguiente ({@code null}).
     *
     * @param dato El dato a almacenar en este nodo.
     */
    public Nodo(T dato) {
        this(dato, null); // Delega al constructor más completo
    }

    /**
     * Construye un nodo con el dato y la referencia al siguiente nodo especificados.
     *
     * @param dato El dato a almacenar.
     * @param siguiente El nodo que seguirá a este en la lista.
     */
    public Nodo(T dato, Nodo<T> siguiente) {
        this.dato = dato;
        this.siguiente = siguiente;
    }

    /**
     * Obtiene el dato almacenado en este nodo.
     * @return El dato de tipo {@code T}.
     */
    public T getDato() {
        return dato;
    }

    /**
     * Obtiene la referencia al siguiente nodo en la lista.
     * @return El {@link Nodo} siguiente, o {@code null} si no hay siguiente.
     */
    public Nodo<T> getSiguiente() {
        return siguiente;
    }

    /**
     * Establece o actualiza el dato almacenado en este nodo.
     * Útil para operaciones como la ordenación por intercambio de datos.
     *
     * @param dato El nuevo dato a almacenar.
     */
    public void setDato(T dato) {
        this.dato = dato;
    }

    /**
     * Establece o actualiza la referencia al siguiente nodo.
     * Permite modificar la estructura de la lista enlazada.
     *
     * @param siguiente El nodo que será el nuevo siguiente.
     */
    public void setSiguiente(Nodo<T> siguiente) {
        this.siguiente = siguiente;
    }

    /**
     * Devuelve una representación textual del dato contenido en el nodo.
     * Si el dato es {@code null}, devuelve la cadena "null".
     *
     * @return La representación en cadena del dato.
     */
    @Override
    public String toString() {
        return Objects.toString(dato, "null"); // Forma segura de manejar null
    }

}
