package com.mycompany.sort.model.matrix;

import com.mycompany.sort.model.SortingStrategy.SortResult;
import com.mycompany.sort.model.SortingStrategy.SortingStrategy;
import com.mycompany.sort.model.politico.Politico;
import com.mycompany.sort.model.politico.PoliticoComparator;

import java.util.Arrays;

/**
 * Clase encargada de organizar un arreglo de objetos {@link Politico}
 * en forma de matriz y aplicar ordenamiento por filas según la edad.
 *
 * Esta clase colabora en la simulación de estrategias de ordenamiento
 * sobre estructuras más complejas como matrices.
 */
public class MatrixOrganizer {

    /**
     * Organiza un arreglo de políticos en una matriz y ordena cada fila
     * utilizando la estrategia proporcionada, comparando por edad.
     *
     * @param data      Arreglo original de políticos (ordenado por dinero)
     * @param rows      Número de filas deseado en la matriz
     * @param cols      Número de columnas deseado en la matriz
     * @param strategy  Estrategia de ordenamiento a aplicar en cada fila
     * @return          Resultado combinado del ordenamiento de todas las filas
     */
    public SortResult organizeMatrix(Politico[] data, int rows, int cols, SortingStrategy strategy) {
        // 1. Copiar el array para no modificar el original (ya está ordenado por dinero)
        Politico[] copy = Arrays.copyOf(data, data.length);

        // 2. Crear matriz
        Politico[][] matrix = createMatrix(copy, rows, cols);

        // 3. Ordenar filas por edad y acumular métricas
        long totalIterations = 0;
        long totalTime = 0;

        for (Politico[] fila : matrix) {
            SortResult rowResult = strategy.sort(fila, PoliticoComparator.porEdad());
            totalIterations += rowResult.getIterations();
            totalTime += rowResult.getTimeElapsedMillis();
        }

        return new SortResult("MATRIX", data.length, strategy.getName(), totalIterations, totalTime);
    }

    /**
     * Convierte un arreglo unidimensional de políticos en una matriz bidimensional
     * rellenando con el último elemento si faltan datos para completar la matriz.
     *
     * @param data  Arreglo de políticos a convertir
     * @param rows  Número de filas
     * @param cols  Número de columnas
     * @return      Matriz bidimensional de políticos
     */
    public Politico[][] createMatrix(Politico[] data, int rows, int cols) {
        Politico[][] matrix = new Politico[rows][cols];
        int index = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                matrix[i][j] = (index < data.length)
                        ? data[index++]
                        : data[data.length - 1]; // Relleno si falta
            }
        }
        return matrix;
    }
}
