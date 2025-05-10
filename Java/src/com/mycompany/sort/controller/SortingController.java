package com.mycompany.sort.controller;

import com.mycompany.sort.controller.accumulator.AccumulatorKey;
import com.mycompany.sort.controller.accumulator.AccumulatorValue;
import com.mycompany.sort.model.SortingStrategy.*;
import com.mycompany.sort.model.datachain.DataGeneratorChain;
import com.mycompany.sort.model.matrix.MatrixOrganizer;
import com.mycompany.sort.model.politico.Politico;
import com.mycompany.sort.model.politico.PoliticoComparator;
import com.mycompany.sort.model.SortingStrategy.SortResult;
import com.mycompany.sort.model.SortingStrategyListaCircular.*;
import com.mycompany.sort.model.SortingStrategyListaEnlazadaDoble.*;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

/**
 * Controlador principal encargado de orquestar el análisis de distintas
 * estrategias de ordenamiento sobre conjuntos de datos generados dinámicamente.
 *
 * Gestiona la generación de datos, la ejecución de algoritmos de ordenamiento
 * en estructuras tipo array y matriz, y acumula resultados estadísticos.
 */
public class SortingController {
    private final DataGeneratorChain dataGenerator;
    private final List<SortingStrategy> strategies;
    private final List<SortingStrategyListaCircular> strategiesListaCircular;
    private final List<SortingStrategyEnlazadaDoble> strategiesListaEnlazadaDoble;
    private final List<SortResult> results;
    private final MatrixOrganizer matrixOrganizer;
    private final Map<AccumulatorKey, AccumulatorValue> accumulator;

    /**
     * Constructor por defecto que inicializa las estrategias, el generador de datos,
     * el organizador de matrices y el mapa de acumuladores.
     */
    public SortingController() {
        this.dataGenerator = new DataGeneratorChain();
        this.strategies = List.of(
                new BubbleSortingStrategy(),
                new SelectionSortingStrategy(),
                new InsertionSortingStrategy(),
                new MergeSortingStrategy(),
                new QuickSortingStrategy()
        );
        this.strategiesListaCircular = List.of(
            new BubbleSortingListaCircular(),
            new InsertionSortingListaCircular(),
            new MergeSortingListaCircular(),
            new QuickSortingListaCircular(),
            new SelectionSortingListaCircular()
        );
        this.strategiesListaEnlazadaDoble = List.of(
            new BubbleSortingListaEnlazadaDoble(),
            new InsertionSortingListaEnlazadaDoble(),
            new MergeSortingListaEnlazadaDoble(),
            new QuickSortingListaEnlazadaDoble(),
            new SelectionSortingListaEnlazadaDoble()
        );
        this.results = new ArrayList<>();
        this.matrixOrganizer = new MatrixOrganizer();
        this.accumulator = new HashMap<>();
        setupShutdownHook();
    }

    /**
     * Establece un hook para mostrar resultados acumulados cuando se cierra la aplicación.
     */
    private void setupShutdownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread(this::displayAccumulatedResults));
    }

    /**
     * Ejecuta el análisis iterativo de las estrategias de ordenamiento con distintos tamaños
     * de entrada y tipos de caso (ordenado, inverso, aleatorio), incrementando el tamaño
     * según el factor de crecimiento especificado.
     *
     * @param initialSize  Tamaño inicial del conjunto de datos.
     * @param growthFactor Factor de crecimiento entre cada iteración.
     */
    public void runAnalysis(int initialSize, double growthFactor) {
        List<String> dataCases = List.of("SORTED", "INVERSE", "RANDOM");
        int currentSize = initialSize;

        try {
            while (true) {
                System.out.println("\n===== TAMAÑO ACTUAL: " + currentSize + " =====");

                for (String dataCase : dataCases) {
                    System.out.println("\n=== CASO: " + dataCase + " ===");
                    Politico[] data = dataGenerator.generateData(dataCase, currentSize);

                    analyzeArrayResults(data, currentSize, dataCase);
                    analyzeMatrixResults(data, currentSize, dataCase);
                }

                currentSize = (int) (currentSize * growthFactor);
            }
        } catch (OutOfMemoryError e) {
            System.out.println("Memoria llena en tamaño: " + currentSize);
            displayAccumulatedResults();
        }
    }

    /**
     * Ejecuta el análisis de ordenamiento sobre arreglos simples (arrays).
     */
    private void analyzeArrayResults(Politico[] data, int size, String dataCase) {
        System.out.println("\n[RESULTADOS DE ARRAYS]");
        for (SortingStrategy strategy : strategies) {
            try {
                Politico[] copy = Arrays.copyOf(data, data.length);
                SortResult result = strategy.sort(copy, PoliticoComparator.porDinero())
                        .withContext("ARRAY", size, strategy.getName());

                results.add(result);
                updateAccumulator(dataCase, "ARRAY", strategy.getName(), result);
                printResult("[Array]", strategy.getName(), result);

            } catch (OutOfMemoryError e) {
                System.out.println("Memoria insuficiente para array en: " + strategy.getName());
            }
        }
    }

    /**
     * Ejecuta el análisis de ordenamiento sobre estructuras de tipo matriz.
     */
    private void analyzeMatrixResults(Politico[] data, int size, String dataCase) {
        System.out.println("\n[RESULTADOS DE MATRICES]");
        for (SortingStrategy strategy : strategies) {
            try {
                Politico[] sortedByMoney = Arrays.copyOf(data, data.length);
                SortResult moneySortResult = strategy.sort(sortedByMoney, PoliticoComparator.porDinero());

                int rows = calculateRows(size);
                int cols = calculateColumns(size, rows);

                SortResult matrixResult = matrixOrganizer.organizeMatrix(sortedByMoney, rows, cols, strategy);

                SortResult combinedResult = new SortResult(
                        "MATRIX",
                        size,
                        strategy.getName(),
                        moneySortResult.getIterations() + matrixResult.getIterations(),
                        moneySortResult.getTimeElapsedMillis() + matrixResult.getTimeElapsedMillis()
                );

                results.add(combinedResult);
                updateAccumulator(dataCase, "MATRIX", strategy.getName(), combinedResult);
                printResult("[Matriz]", strategy.getName(), combinedResult);

            } catch (OutOfMemoryError e) {
                System.out.println("Memoria insuficiente para matriz en: " + strategy.getName());
            }
        }
    }

    /**
     * Calcula el número de filas para una matriz cuadrada o casi cuadrada.
     */
    private int calculateRows(int size) {
        return Math.max(1, (int) Math.ceil(Math.sqrt(size)));
    }

    /**
     * Calcula el número de columnas para una matriz, basado en el tamaño y filas.
     */
    private int calculateColumns(int size, int rows) {
        return (int) Math.ceil((double) size / rows);
    }

    /**
     * Actualiza el acumulador global con los datos de un resultado nuevo.
     *
     * @param dataCase     Tipo de caso de prueba.
     * @param dataType     Tipo de estructura de datos ("ARRAY" o "MATRIX").
     * @param strategyName Nombre de la estrategia usada.
     * @param result       Resultado de ordenamiento.
     */
    private void updateAccumulator(String dataCase, String dataType, String strategyName, SortResult result) {
        AccumulatorKey key = new AccumulatorKey(dataCase, dataType, strategyName);
        accumulator.compute(key, (k, v) -> {
            if (v == null) {
                v = new AccumulatorValue();
            }
            v.add(result.getIterations(), result.getTimeElapsedMillis()); // Pasa double
            return v;
        });
    }

    /**
     * Imprime los resultados acumulados promedio al terminar la ejecución.
     */
    private void displayAccumulatedResults() {
        System.out.println("\n=== Resultados Acumulados por Caso, Tipo y Estrategia ===");
        for (Map.Entry<AccumulatorKey, AccumulatorValue> entry : accumulator.entrySet()) {
            AccumulatorKey key = entry.getKey();
            AccumulatorValue value = entry.getValue();
            System.out.printf(
                    "Caso: %-7s Tipo: %-6s Estrategia: %-18s | Iteraciones Promedio: %10.2f | Tiempo Promedio: %10.2f ms%n",
                    key.dataCase(),
                    key.dataType(),
                    key.strategyName(),
                    value.getAverageIterations(),
                    value.getAverageTime()
            );
        }
    }

    /**
     * Devuelve los resultados acumulados filtrados por tipo de estructura.
     *
     * @param dataType tipo de estructura ("ARRAY" o "MATRIX").
     * @return Mapa anidado [Caso -> Estrategia -> Resultado acumulado].
     */
    public Map<String, Map<String, AccumulatorValue>> getAccumulatedResults(String dataType) {
        Map<String, Map<String, AccumulatorValue>> results = new HashMap<>();

        for (Map.Entry<AccumulatorKey, AccumulatorValue> entry : accumulator.entrySet()) {
            AccumulatorKey key = entry.getKey();
            if (key.dataType().equals(dataType)) {
                results.computeIfAbsent(key.dataCase(), k -> new HashMap<>())
                        .put(key.strategyName(), entry.getValue());
            }
        }
        return results;
    }

    /**
     * Retorna la lista de estrategias de ordenamiento disponibles.
     */
    public List<SortingStrategy> getStrategies() {
        return strategies;
    }

    /**
     * Imprime por consola un resultado individual en formato legible.
     *
     * @param type         Tipo de estructura ("Array" o "Matriz").
     * @param strategyName Nombre de la estrategia.
     * @param result       Resultado detallado.
     */
    private void printResult(String type, String strategyName, SortResult result) {
        System.out.printf(
                "%-8s %-15s | Tamaño: %,6d | Iteraciones: %,9d | Tiempo: %s%n",
                type,
                strategyName,
                result.getSize(),
                result.getIterations(),
                result.getFormattedTime()
        );
    }
}
