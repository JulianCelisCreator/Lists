package com.mycompany.sort.model.politico;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Representa un político con dos atributos: dinero y fecha de nacimiento (o cualquier fecha relevante).
 * Esta clase es inmutable: sus atributos son finales y no posee setters.
 * Se utiliza como entidad base para aplicar diferentes estrategias de ordenamiento.
 */
public class Politico implements Comparable<Politico> {
    private final int dinero; // Cantidad de dinero asociada al político
    private final LocalDate fecha; // Fecha asociada al político (puede ser fecha de nacimiento, ingreso, etc.)

    /**
     * Crea una nueva instancia de {@code Politico} con los valores especificados.
     *
     * @param dinero  Cantidad de dinero asociada al político
     * @param fecha   Fecha asociada al político (puede representar nacimiento, ingreso, etc.)
     */
    public Politico(int dinero, LocalDate fecha) {
        this.dinero = dinero;  // Asigna el valor del dinero
        this.fecha = fecha;    // Asigna la fecha asociada
    }

    /**
     * Obtiene la cantidad de dinero del político.
     *
     * @return dinero del político
     */
    public int getDinero() {
        return dinero;  // Devuelve el valor del dinero
    }

    /**
     * Obtiene la fecha asociada al político.
     *
     * @return fecha del político
     */
    public LocalDate getFecha() {
        return fecha;  // Devuelve la fecha asociada
    }

    /**
     * Devuelve una representación en cadena del político, mostrando el dinero y la fecha.
     *
     * @return cadena con formato "$dinero - fecha"
     */
    @Override
    public String toString() {
        // Devuelve la representación en formato "$dinero - fecha"
        return String.format("$%,d - %s", dinero, fecha.toString());
    }

    /**
     * Compara este Politico con otro basándose en la cantidad de dinero (orden de mayor a menor dinero).
     * Esta es la implementación del contrato {@link Comparable}<{@link Politico}>.
     *
     * @param other El otro Politico a comparar (no debe ser null).
     * @return Un entero negativo, cero o positivo si este Politico tiene menos,
     *         la misma cantidad o más dinero que {@code other}.
     * @throws NullPointerException si {@code other} es null.
     */
    @Override
    public int compareTo(Politico other) {
        // Se asegura de que el objeto "other" no sea null
        Objects.requireNonNull(other, "No se puede comparar con un Politico null.");

        // Compara los valores de dinero entre este Politico y el otro
        return Integer.compare(this.dinero, other.dinero); // Devuelve -1, 0 o 1
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Politico politico = (Politico) o;
        return dinero == politico.dinero &&
                Objects.equals(fecha, politico.fecha);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dinero, fecha);
    }
}
