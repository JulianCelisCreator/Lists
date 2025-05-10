package com.mycompany.sort.controller.accumulator;

/**
 * Representa una clave única para identificar resultados acumulados
 * de una estrategia de ordenamiento, en función del caso de datos,
 * el tipo de estructura usada y el nombre de la estrategia.
 *
 * Este record se utiliza como clave en estructuras de datos como {@code Map}
 * para agrupar y acceder eficientemente a los valores acumulados por combinación específica.
 *
 * @param dataCase     el tipo de caso de datos (por ejemplo: "SORTED", "INVERSE", "RANDOM")
 * @param dataType     el tipo de estructura usada (por ejemplo: "ARRAY", "MATRIX")
 * @param strategyName el nombre de la estrategia de ordenamiento (por ejemplo: "QuickSort", "MergeSort")
 */
public record AccumulatorKey(String dataCase, String dataType, String strategyName) { }
