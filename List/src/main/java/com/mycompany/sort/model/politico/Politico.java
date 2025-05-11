package com.mycompany.sort.model.politico;

import java.time.LocalDate;

import java.util.Objects;

/**
 * Representa un político con dos atributos: dinero y fecha de nacimiento (o cualquier fecha relevante).
 *
 * Esta clase es inmutable: sus atributos son finales y no posee setters.
 * Se utiliza como entidad base para aplicar diferentes estrategias de ordenamiento.
 */
public class Politico implements Comparable<Politico>{
    private final int dinero;
    private final LocalDate fecha;

    /**
     * Crea una nueva instancia de {@code Politico} con los valores especificados.
     *
     * @param dinero  Cantidad de dinero asociada al político
     * @param fecha   Fecha asociada (puede representar nacimiento, ingreso, etc.)
     */
    public Politico(int dinero, LocalDate fecha) {
        this.dinero = dinero;
        this.fecha = fecha;
    }

    /**
     * Obtiene la cantidad de dinero del político.
     *
     * @return dinero del político
     */
    public int getDinero() {
        return dinero;
    }

    /**
     * Obtiene la fecha asociada al político.
     *
     * @return fecha del político
     */
    public LocalDate getFecha() {
        return fecha;
    }

    /**
     * Devuelve una representación en cadena del político, mostrando el dinero y la fecha.
     *
     * @return cadena con formato "$dinero - fecha"
     */
    @Override
    public String toString() {
        return String.format("$%,d - %s", dinero, fecha.toString());
    }

    /**
     * Compara este Papa con otro basándose en la fecha de inicio del papado (orden cronológico).
     * Esta es la implementación del contrato {@link Comparable}<{@link Papa}>.
     *
     * @param other El otro Papa a comparar (no debe ser null).
     * @return Un entero negativo, cero o positivo si este Papa inició antes,
     *         en la misma fecha, o después que {@code other}.
     * @throws NullPointerException si {@code other} es null.
     */

    @Override
    public int compareTo(Politico other) {
    Objects.requireNonNull(other, "No se puede comparar con un Politico null.");
    return Integer.compare(this.dinero, other.dinero);
}

}
