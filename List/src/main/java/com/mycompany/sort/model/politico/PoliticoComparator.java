package com.mycompany.sort.model.politico;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;

/**
 * Clase utilitaria que proporciona comparadores estáticos para ordenar objetos {@link Politico}
 * según diferentes criterios como dinero o edad.
 */
public class PoliticoComparator {

    /**
     * Devuelve un comparador que ordena políticos por su cantidad de dinero de forma ascendente.
     *
     * @return comparador basado en el dinero
     */
    public static Comparator<Politico> porDinero() {
        return Comparator.comparingInt(Politico::getDinero);
    }

    /**
     * Devuelve un comparador que ordena políticos por su edad de forma ascendente.
     * La edad se calcula como la diferencia en años entre la fecha asociada al político y la fecha actual.
     *
     * @return comparador basado en la edad (calculada desde la fecha hasta hoy)
     */
    public static Comparator<Politico> porEdad() {
        return Comparator.comparing(p -> {
            long years = ChronoUnit.YEARS.between(p.getFecha(), LocalDate.now());
            return (int) years;
        });
    }
}
