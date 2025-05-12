package com.mycompany.sort.model.politico;

import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * Implementación de una lista enlazada simple y circular genérica.
 * En esta estructura, el último nodo apunta de vuelta al primer nodo (cabeza).
 * Se utiliza una única referencia externa, {@code ultimo}, que apunta al último nodo,
 * lo que permite acceso O(1) a la cabeza ({@code ultimo.getSiguiente()}) y
 * operaciones O(1) de inserción al inicio y al final.
 * Utiliza la clase {@link Nodo}.
 *
 * @param <T> El tipo de elementos almacenados en la lista.
 */

public class ListaEnlazadaSimpleCircular<T> {
    
    /**
     * Referencia al último nodo de la lista circular.
     * Si la lista está vacía, es {@code null}.
     * Si la lista tiene un solo elemento, {@code ultimo} apunta a ese elemento,
     * y ese elemento apunta a sí mismo.
     * En una lista con más elementos, {@code ultimo.getSiguiente()} devuelve la cabeza.
     */
    private Nodo<T> ultimo;
    /** Número actual de elementos en la lista. */
    private int tamanno;

    /**
     * Construye una lista enlazada simple circular vacía.
     */
    public ListaEnlazadaSimpleCircular() {
        this.ultimo = null;
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
     * Comprueba si la lista está vacía.
     * @return {@code true} si la lista no tiene elementos, {@code false} en caso contrario.
     */
    public boolean estaVacia() {
        return tamanno == 0;
    }

    /**
     * Obtiene el nodo cabeza de la lista circular (el primer elemento).
     * El acceso es O(1) a través de la referencia {@code ultimo}.
     * @return El {@link Nodo} cabeza, o {@code null} si la lista está vacía.
     */
    public Nodo<T> getCabeza() {
        return estaVacia() ? null : this.ultimo.getSiguiente();
    }

    /**
 * Devuelve el último nodo de la lista circular.
 * En una lista vacía, devuelve {@code null}.
 * En una lista con elementos, este nodo apunta al nodo cabeza mediante {@code getSiguiente()}.
 *
 * @return El nodo último de la lista, o {@code null} si está vacía.
 */
public Nodo<T> getUltimo() {
    return this.ultimo;
}

    /**
 * Establece un nuevo nodo como la cabeza (primer nodo) de la lista circular.
 * <p>
 * Si la lista está vacía, el nodo se convierte en el único elemento,
 * apuntándose a sí mismo y asignándose como {@code ultimo}.
 * Si la lista no está vacía, el nuevo nodo se inserta justo después del nodo
 * {@code ultimo}, y apunta al nodo que antes era la cabeza, convirtiéndose así
 * en el nuevo nodo cabeza de la lista.
 * </p>
 *
 * @param nuevoCabeza El nodo que se desea establecer como cabeza.
 * @throws IllegalArgumentException si {@code nuevoCabeza} es {@code null}.
 */
public void setCabeza(Nodo<T> nuevoCabeza) {
    if (nuevoCabeza == null) {
        throw new IllegalArgumentException("El nuevo nodo cabeza no puede ser null.");
    }

    if (estaVacia()) {
        nuevoCabeza.setSiguiente(nuevoCabeza);
        this.ultimo = nuevoCabeza;
        this.tamanno = 1;
    } else {
        nuevoCabeza.setSiguiente(this.ultimo.getSiguiente()); 
        this.ultimo.setSiguiente(nuevoCabeza);
        this.tamanno++;
    }
}    
    
    /**
     * Inserta un elemento al principio de la lista (se convierte en la nueva cabeza).
     * Operación de tiempo constante O(1).
     * @param dato El dato a insertar.
     */
    public void insertarAlInicio(T dato) {
        Nodo<T> nuevoNodo = new Nodo<>(dato);
        if (estaVacia()) {
            this.ultimo = nuevoNodo;
            this.ultimo.setSiguiente(this.ultimo);
        } else {
            nuevoNodo.setSiguiente(this.ultimo.getSiguiente());
            this.ultimo.setSiguiente(nuevoNodo);
        }
        this.tamanno++;
    }

    /**
     * Inserta un elemento al final de la lista (se convierte en el nuevo {@code ultimo}).
     * Operación de tiempo constante O(1).
     * @param dato El dato a insertar.
     */
    public void insertarAlFinal(T dato) {
        insertarAlInicio(dato);
        if (this.tamanno > 1) { 
            this.ultimo = this.ultimo.getSiguiente();
        }
    }

    /**
     * Elimina y devuelve el elemento al principio de la lista (la cabeza).
     * Operación de tiempo constante O(1).
     *
     * @return El dato del elemento eliminado.
     * @throws NoSuchElementException si la lista está vacía.
     */
    public T eliminarAlInicio() {
        if (estaVacia()) {
            throw new NoSuchElementException("No se puede eliminar de una lista circular vacía.");
        }

        Nodo<T> cabeza = getCabeza(); 
        T datoEliminado = cabeza.getDato();

        if (this.ultimo == cabeza) { 
            this.ultimo = null;
        } else {
            this.ultimo.setSiguiente(cabeza.getSiguiente());
        }
        this.tamanno--;
        return datoEliminado;
    }

    /**
     * Elimina la primera ocurrencia del elemento especificado {@code dato} de la lista.
     * Utiliza {@code equals()} para la comparación. La búsqueda es O(n).
     *
     * @param dato El dato del elemento a eliminar.
     * @return {@code true} si el elemento fue encontrado y eliminado, {@code false} en caso contrario.
     */
    public boolean eliminar(T dato) {
        if (estaVacia()) {
            return false;
        }

        Nodo<T> cabeza = getCabeza();
        if (Objects.equals(cabeza.getDato(), dato)) {
            eliminarAlInicio();
            return true;
        }

        Nodo<T> nodoAnterior = buscarNodoAnterior(dato);
        if (nodoAnterior == null) {
            return false;
        }

        Nodo<T> nodoAEliminar = nodoAnterior.getSiguiente();
        nodoAnterior.setSiguiente(nodoAEliminar.getSiguiente());

        if (nodoAEliminar == this.ultimo) {
            this.ultimo = nodoAnterior;
        }
        this.tamanno--;
        return true;
    }

    /**
     * Busca el nodo que precede inmediatamente al primer nodo que contiene {@code datoBusqueda}.
     * Utiliza {@code Objects.equals()} para manejar {@code null}.
     * Recorre la lista circular una vez.
     *
     * @param datoBusqueda El dato contenido en el nodo objetivo (el nodo *después* del que buscamos).
     * @return El nodo predecesor, o {@code null} si {@code datoBusqueda} no se encuentra o está en la cabeza (no tiene predecesor único en este contexto).
     */
    private Nodo<T> buscarNodoAnterior(T datoBusqueda) {
        if (estaVacia() || this.tamanno == 1) {
            return null;
        }

        Nodo<T> actual = this.ultimo; 
        for (int i = 0; i < this.tamanno; i++) {
            if (Objects.equals(actual.getSiguiente().getDato(), datoBusqueda)) {

                 if (actual.getSiguiente() == getCabeza() && Objects.equals(getCabeza().getDato(), datoBusqueda)) {
                    
                 }
                 return actual;
            }
            actual = actual.getSiguiente(); 
        }
        return null; 
    }

}
