package com.mycompany.sort.model.politico;

import java.util.NoSuchElementException;
import java.util.Objects;

public class ListaEnlazadaSimple<T> {
    
    /** Referencia al primer nodo de la lista (cabeza). {@code null} si la lista está vacía. */
    private Nodo<T> cabeza;
    /** Referencia al último nodo de la lista (cola). {@code null} si la lista está vacía. Optimiza inserción al final. */
    private Nodo<T> cola;
    /** Número actual de elementos en la lista. */
    private int tamanno;

    /**
     * Construye una lista enlazada simple vacía.
     * La cabeza, la cola y el tamaño se inicializan adecuadamente.
     */
    public ListaEnlazadaSimple() {
        this.cabeza = null;
        this.cola = null;
        this.tamanno = 0;
    }

    /**
     * Comprueba si la lista no contiene elementos.
     * @return {@code true} si el tamaño es 0, {@code false} en caso contrario.
     */
    public boolean estaVacia() {
        return this.tamanno == 0;
    }

    /**
     * Devuelve el número de elementos actualmente en la lista.
     * @return El tamaño (entero no negativo).
     */
    public int getTamanno() {
        return this.tamanno;
    }

    /**
     * Obtiene el nodo cabeza de la lista.
     * Utilizado internamente por algunas estrategias de ordenación.
     * ¡Precaución! Modificar el nodo devuelto externamente puede romper la lista.
     * @return El primer {@link Nodo}, o {@code null} si la lista está vacía.
     */
    public Nodo<T> getCabeza() {
        return this.cabeza;
    }

    public void setCabeza(Nodo<T> cabeza) {
        this.cabeza = cabeza;
        if (this.cabeza == null) {
            this.cola = null;
        } else {
            Nodo<T> actual = this.cabeza;
            while (actual.getSiguiente() != null) {
                actual = actual.getSiguiente();
            }
            this.cola = actual; 
        }
    }

    /**
     * Inserta un elemento al principio de la lista (nueva cabeza).
     * Operación de tiempo constante O(1).
     * @param dato El dato a insertar.
     */
    public void insertarAlInicio(T dato) {
        Nodo<T> nuevoNodo = new Nodo<>(dato, this.cabeza);
        this.cabeza = nuevoNodo;
        if (this.cola == null) {
            this.cola = this.cabeza;
        }
        this.tamanno++;
    }

    /**
     * Inserta un elemento al final de la lista (nueva cola).
     * Operación de tiempo constante O(1) gracias a la referencia {@code cola}.
     * @param dato El dato a insertar.
     */
    public void insertarAlFinal(T dato) {
        Nodo<T> nuevoNodo = new Nodo<>(dato);
        if (estaVacia()) {
            this.cabeza = nuevoNodo;
            this.cola = nuevoNodo;
        } else {
            this.cola.setSiguiente(nuevoNodo); 
            this.cola = nuevoNodo;           
        }
        this.tamanno++;
    }

    /**
     * Elimina y devuelve el elemento que se encuentra al principio de la lista (la cabeza).
     * Operación de tiempo constante O(1).
     *
     * @return El dato del elemento eliminado.
     * @throws NoSuchElementException si la lista está vacía.
     */
    public T eliminarAlInicio() {
        if (estaVacia()) {
            throw new NoSuchElementException("La lista está vacía, no se puede eliminar al inicio.");
        }
        T datoEliminado = this.cabeza.getDato();
        this.cabeza = this.cabeza.getSiguiente();
        this.tamanno--;
        if (estaVacia()) {
            this.cola = null; 
        }
        return datoEliminado;
    }

    /**
     * Elimina la primera ocurrencia del elemento especificado {@code dato} de la lista.
     * Utiliza {@code equals()} para la comparación. La búsqueda es O(n).
     *
     * @param dato El dato del elemento a eliminar.
     * @return {@code true} si el elemento fue encontrado y eliminado, {@code false} en caso contrario.
     */
    /**
 * Elimina la primera ocurrencia del elemento que tiene el mismo dinero que el especificado.
 *
 * @param dato El dato del elemento a eliminar (solo se compara el dinero).
 * @return {@code true} si el elemento fue encontrado y eliminado, {@code false} en caso contrario.
 */
    public boolean eliminar(T dato) {
        if (estaVacia()) {
            return false;
        }

        if (this.cabeza.getDato().equals(dato)) {
            eliminarAlInicio();
            return true;
        }

        Nodo<T> nodoAnterior = buscarNodoAnterior(dato);
        if (nodoAnterior == null) {
            return false;
        }

        Nodo<T> nodoAEliminar = nodoAnterior.getSiguiente();
        nodoAnterior.setSiguiente(nodoAEliminar.getSiguiente());

        if (nodoAEliminar == this.cola) {
            this.cola = nodoAnterior;
        }

        this.tamanno--;
        return true;
    }

    public Nodo<T> buscarNodoAnterior(T datoBusqueda) {
        if (estaVacia() || this.tamanno == 1) {
            return null;
        }

        Nodo<T> actual = this.cabeza;
        while (actual.getSiguiente() != null) {
            if (actual.getSiguiente().getDato().equals(datoBusqueda)) {
                return actual;
            }
            actual = actual.getSiguiente();
        }
        return null;
    }
}
