package com.mycompany.sort.model.SortingStrategy;

/**
 * Clase que encapsula el resultado de la ejecución de una estrategia de ordenamiento.
 * Puede representar métricas crudas o con contexto (tipo de dato, tamaño, nombre del algoritmo).
 */
public class SortResult {
    private final String type;               // "ARRAY" o "MATRIX"
    private final int size;                  // Tamaño de la estructura de datos ordenada
    private final String strategy;           // Nombre de la estrategia de ordenamiento
    private final long iterations;           // Número total de iteraciones/comparaciones
    private final double timeElapsedMillis;    // Tiempo total transcurrido en milisegundos

    /**
     * Constructor completo. Se usa cuando se quiere asociar contexto al resultado.
     *
     * @param type              Tipo de estructura ("ARRAY" o "MATRIX")
     * @param size              Cantidad de elementos ordenados
     * @param strategy          Nombre del algoritmo de ordenamiento
     * @param iterations        Número de iteraciones realizadas
     * @param timeElapsedMillis Tiempo total en milisegundos
     */
    public SortResult(String type, int size, String strategy, long iterations, double timeElapsedMillis) {
        if (iterations < 0 || timeElapsedMillis < 0) {
            throw new IllegalArgumentException("Iteraciones o tiempo no pueden ser negativos");
        }
        this.type = type;
        this.size = size;
        this.strategy = strategy;
        this.iterations = iterations;
        this.timeElapsedMillis = timeElapsedMillis;
    }

    /**
     * Constructor simplificado. Se usa internamente en cada algoritmo antes de añadir contexto.
     *
     * @param iterations        Número de iteraciones realizadas
     * @param timeElapsedMillis Tiempo total en milisegundos
     */
    public SortResult(long iterations, double timeElapsedMillis) {
        this(null, -1, null, iterations, timeElapsedMillis);
    }

    /**
     * Añade información contextual al resultado generado por una estrategia.
     *
     * @param type     Tipo de estructura ("ARRAY", "MATRIX")
     * @param size     Tamaño de los datos ordenados
     * @param strategy Nombre del algoritmo
     * @return Un nuevo objeto SortResult con contexto
     */
    public SortResult withContext(String type, int size, String strategy) {
        return new SortResult(type, size, strategy, this.iterations, this.timeElapsedMillis);
    }

    // ======== Getters ========

    public String getType() { return type; }

    public int getSize() { return size; }

    public String getStrategy() { return strategy; }

    public long getIterations() { return iterations; }

    public double getTimeElapsedMillis() { return timeElapsedMillis; }

    /**
     * @return Tiempo en segundos con decimales
     */
    public double getTimeElapsedSeconds() {
        return timeElapsedMillis / 1_000.0;
    }

    /**
     * @return Tiempo en minutos con decimales
     */
    public double getTimeElapsedMinutes() {
        return timeElapsedMillis / 60_000.0;
    }

    /**
     * Devuelve el tiempo formateado como "mm:ss.mmm".
     *
     * @return Cadena legible de tiempo
     */
    public String getFormattedTime() {
        double totalMillis = timeElapsedMillis;
        long minutes = (long) (totalMillis / 60_000);
        long seconds = (long) ((totalMillis % 60_000) / 1_000);
        double millis = totalMillis % 1_000;
        return String.format("%02d:%02d.%03.0f", minutes, seconds, millis);
    }

    /**
     * Representación legible del resultado de ordenamiento.
     *
     * @return Cadena con resumen de los resultados
     */
    @Override
    public String toString() {
        return String.format(
                "[%s] %s | Tamaño: %,d | Iteraciones: %,d | Tiempo: %s",
                type != null ? type : "RAW",
                strategy != null ? strategy : "N/A",
                size,
                iterations,
                getFormattedTime()
        );
    }
}
