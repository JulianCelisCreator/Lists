package com.mycompany.sort.controller.accumulator;

/**
 * Clase encargada de acumular y calcular estadísticas de rendimiento de estrategias de ordenamiento,
 * como el número total de iteraciones y el tiempo total de ejecución.
 *
 * Se utiliza para obtener promedios a partir de múltiples ejecuciones de un mismo algoritmo
 * en diferentes tamaños o casos de datos.
 */
public class AccumulatorValue {
    private long totalIterations = 0;
    private double totalTime = 0.0;  // Cambiado a double
    private int count = 0;

    public void add(long iterations, double time) {  // time ahora es double
        totalIterations += iterations;
        totalTime += time;
        count++;
    }

    public void merge(AccumulatorValue other) {
        this.totalIterations += other.totalIterations;
        this.totalTime += other.totalTime;
        this.count += other.count;
    }

    /**
     * Devuelve el promedio de iteraciones registradas.
     *
     * @return el número promedio de iteraciones por ejecución.
     */
    public double getAverageIterations() {
        return count == 0 ? 0 : (double) totalIterations / count;
    }

    /**
     * Devuelve el tiempo promedio de ejecución registrado.
     *
     * @return el tiempo promedio por ejecución, en milisegundos.
     */
    public double getAverageTime() {
        return count == 0 ? 0 : (double) totalTime / count;
    }
}
