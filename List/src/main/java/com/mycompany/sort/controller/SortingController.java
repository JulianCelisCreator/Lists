package com.mycompany.sort.controller;

import com.mycompany.sort.config.DataHandlerConfig;
import com.mycompany.sort.model.ResultadoOrdenamiento;
import com.mycompany.sort.model.SortingStrategy.*;
import com.mycompany.sort.model.SortingStrategyListaEnlazadaDoble.*;
import com.mycompany.sort.model.SortingStrategyListaCircular.*;
import com.mycompany.sort.model.datahandler.DataGeneratorHandler;
import com.mycompany.sort.model.politico.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * Controlador que gestiona la ejecución de diferentes algoritmos de ordenamiento
 * sobre distintas estructuras de datos (lista simple, doble y circular).
 * Permite realizar simulaciones completas y exportar los resultados.
 */
public class SortingController {
    /**
     * Generador de datos para las pruebas de ordenamiento.
     */
    private final DataGeneratorHandler dataGenerator;

    /**
     * Lista que almacena los resultados de las operaciones de ordenamiento.
     */
    private final List<ResultadoOrdenamiento> resultados;

    /**
     * Lista de estrategias de ordenamiento para listas enlazadas simples.
     */
    private final List<SortingStrategy<Politico>> estrategiasSimples;

    /**
     * Lista de estrategias de ordenamiento para listas doblemente enlazadas.
     */
    private final List<SortingStrategyEnlazadaDoble<Politico>> estrategiasDobles;

    /**
     * Lista de estrategias de ordenamiento para listas circulares.
     */
    private final List<SortingStrategyListaCircular<Politico>> estrategiasCirculares;

    /**
     * Constructor que inicializa el controlador y configura las estrategias de ordenamiento.
     */
    public SortingController() {
        this.dataGenerator = DataHandlerConfig.buildHandlerChain();
        this.resultados = new ArrayList<>();
        this.estrategiasSimples = new ArrayList<>();
        this.estrategiasDobles = new ArrayList<>();
        this.estrategiasCirculares = new ArrayList<>();

        inicializarEstrategias();
    }

    /**
     * Inicializa todas las estrategias de ordenamiento disponibles para cada tipo de lista.
     */
    private void inicializarEstrategias() {
        // Estrategias para lista simple
        estrategiasSimples.add(new BubbleSortingStrategy<>());
        estrategiasSimples.add(new SelectionSortingStrategy<>());
        estrategiasSimples.add(new InsertionSortingStrategy<>());
        estrategiasSimples.add(new MergeSortingStrategy<>());
        estrategiasSimples.add(new QuickSortingStrategy<>());

        // Estrategias para lista doble
        estrategiasDobles.add(new BubbleSortingListaEnlazadaDoble<>());
        estrategiasDobles.add(new SelectionSortingListaEnlazadaDoble<>());
        estrategiasDobles.add(new InsertionSortingListaEnlazadaDoble<>());
        estrategiasDobles.add(new MergeSortingListaEnlazadaDoble<>());
        estrategiasDobles.add(new QuickSortingListaEnlazadaDoble<>());

        // Estrategias para lista circular
        estrategiasCirculares.add(new BubbleSortingListaCircular<>());
        estrategiasCirculares.add(new SelectionSortingListaCircular<>());
        estrategiasCirculares.add(new InsertionSortingListaCircular<>());
        estrategiasCirculares.add(new MergeSortingListaCircular<>());
        estrategiasCirculares.add(new QuickSortingListaCircular<>());
    }

    /**
     * Ejecuta una simulación completa de ordenamiento con diferentes tipos de datos y estructuras.
     * @param tamanioDatos Tamaño del conjunto de datos a ordenar
     */
    public void ejecutarSimulacionCompleta(int tamanioDatos) {
        resultados.clear();  // Asegurarse de que los resultados se limpien antes de la ejecución

        String[] tiposDatos = {"SORTED", "INVERSE", "RANDOM"};

        for (String tipo : tiposDatos) {
            System.out.println("Comenzando simulación para tipo de datos: " + tipo);

            Politico[] datos = dataGenerator.generateData(tipo, tamanioDatos);

            // Procesar lista simple
            System.out.println("Procesando lista simple...");
            procesarListaSimple(datos, tipo, tamanioDatos);
            System.out.println("terminado simples");

            // Procesar lista doble
            System.out.println("Procesando lista doble...");
            procesarListaDoble(datos, tipo, tamanioDatos);
            System.out.println("terminado dobles");

            // Procesar lista circular
            System.out.println("Procesando lista circular...");
            procesarListaCircular(datos, tipo, tamanioDatos);
            System.out.println("terminado circular");
        }

        System.out.println("Simulación completa terminada.");
    }

    /**
     * Procesa el ordenamiento usando las estrategias para lista simple.
     * @param datos Array de políticos a ordenar
     * @param tipo Tipo de ordenamiento inicial de los datos
     * @param tamanio Tamaño del conjunto de datos
     */
    private void procesarListaSimple(Politico[] datos, String tipo, int tamanio) {
        ListaEnlazadaSimple<Politico> listaOriginal = convertirArrayAListaSimple(datos);

        for (SortingStrategy<Politico> estrategia : estrategiasSimples) {
            ListaEnlazadaSimple<Politico> listaParaOrdenar = copiarListaSimple(listaOriginal);

            ResultadoOrdenamiento resultado = estrategia.sort(listaParaOrdenar);
            resultados.add(resultado.withContext(
                    tipo,
                    tamanio,
                    "Simple - " + estrategia.getClass().getSimpleName()
            ));
        }

    }

    /**
     * Procesa el ordenamiento usando las estrategias para lista doble.
     * @param datos Array de políticos a ordenar
     * @param tipo Tipo de ordenamiento inicial de los datos
     * @param tamanio Tamaño del conjunto de datos
     */
    private void procesarListaDoble(Politico[] datos, String tipo, int tamanio) {
        ListaEnlazadaDoble<Politico> listaOriginal = convertirArrayAListaDoble(datos);

        for (SortingStrategyEnlazadaDoble<Politico> estrategia : estrategiasDobles) {
            ListaEnlazadaDoble<Politico> listaParaOrdenar = copiarListaDoble(listaOriginal);

            ResultadoOrdenamiento resultado = estrategia.sort(listaParaOrdenar);
            resultados.add(resultado.withContext(
                    tipo,
                    tamanio,
                    "Doble - " + estrategia.getClass().getSimpleName()
            ));
        }
    }

    /**
     * Procesa el ordenamiento usando las estrategias para lista circular.
     * @param datos Array de políticos a ordenar
     * @param tipo Tipo de ordenamiento inicial de los datos
     * @param tamanio Tamaño del conjunto de datos
     */
    private void procesarListaCircular(Politico[] datos, String tipo, int tamanio) {
        ListaEnlazadaSimpleCircular<Politico> listaOriginal = convertirArrayAListaCircular(datos);

        for (SortingStrategyListaCircular<Politico> estrategia : estrategiasCirculares) {
            System.out.println("Ejecutando estrategia: " + estrategia.getClass().getSimpleName());

            ListaEnlazadaSimpleCircular<Politico> listaParaOrdenar = copiarListaCircular(listaOriginal);

            // Aquí se muestra el tiempo de ejecución de cada estrategia
            try {
                ResultadoOrdenamiento resultado = estrategia.sort(listaParaOrdenar);
                resultados.add(resultado.withContext(
                        tipo,
                        tamanio,
                        "Circular - " + estrategia.getClass().getSimpleName()
                ));
                System.out.println("Estrategia " + estrategia.getClass().getSimpleName() + " terminada");
            } catch (Exception e) {
                System.err.println("Error en la ejecución de la estrategia " + estrategia.getClass().getSimpleName() + ": " + e.getMessage());
            }
        }
        System.out.println("Terminada la simulación de listas circulares para tipo de datos: " + tipo);
    }


    /**
     * Convierte un array de políticos a una lista enlazada simple.
     * @param datos Array de políticos a convertir
     * @return Lista enlazada simple con los datos convertidos
     */
    private ListaEnlazadaSimple<Politico> convertirArrayAListaSimple(Politico[] datos) {
        ListaEnlazadaSimple<Politico> lista = new ListaEnlazadaSimple<>();
        for (Politico p : datos) {
            lista.insertarAlFinal(p);
        }
        return lista;
    }

    private ListaEnlazadaSimple<Politico> copiarListaSimple(ListaEnlazadaSimple<Politico> original) {
        ListaEnlazadaSimple<Politico> copia = new ListaEnlazadaSimple<>();
        Nodo<Politico> actual = original.getCabeza();
        while (actual != null) {
            copia.insertarAlFinal(actual.getDato());
            actual = actual.getSiguiente();
        }
        return copia;
    }

    private ListaEnlazadaDoble<Politico> convertirArrayAListaDoble(Politico[] datos) {
        ListaEnlazadaDoble<Politico> lista = new ListaEnlazadaDoble<>();
        for (Politico p : datos) {
            lista.insertarAlFinal(p);
        }
        return lista;
    }

    private ListaEnlazadaDoble<Politico> copiarListaDoble(ListaEnlazadaDoble<Politico> original) {
        ListaEnlazadaDoble<Politico> copia = new ListaEnlazadaDoble<>();
        NodoDoble<Politico> actual = original.getCabeza();
        while (actual != null) {
            copia.insertarAlFinal(actual.getDato());
            actual = actual.getSiguiente();
        }
        return copia;
    }

    private ListaEnlazadaSimpleCircular<Politico> convertirArrayAListaCircular(Politico[] datos) {
        ListaEnlazadaSimpleCircular<Politico> lista = new ListaEnlazadaSimpleCircular<>();
        for (Politico p : datos) {
            lista.insertarAlFinal(p);
        }
        return lista;
    }

    private ListaEnlazadaSimpleCircular<Politico> copiarListaCircular(ListaEnlazadaSimpleCircular<Politico> original) {
        ListaEnlazadaSimpleCircular<Politico> copia = new ListaEnlazadaSimpleCircular<>();
        if (original.estaVacia()) return copia;

        Nodo<Politico> actual = original.getCabeza();
        for (int i = 0; i < original.getTamanno(); i++) {
            copia.insertarAlFinal(actual.getDato());
            actual = actual.getSiguiente();
        }
        return copia;
    }

    /**
     * Obtiene una copia de la lista de resultados de ordenamiento.
     * @return Lista con los resultados de las operaciones de ordenamiento
     */
    public List<ResultadoOrdenamiento> getResultados() {
        return new ArrayList<>(resultados);
    }


    // ----------------------------- Impresión -------------------------------

    /**
     * Exporta los resultados de ordenamiento a un archivo CSV.
     * @param rutaCompleta Ruta completa del archivo CSV a generar
     * @throws IOException Si ocurre un error durante la escritura del archivo
     */
    public void exportarCSV(String rutaCompleta) throws IOException {
        try (FileWriter fw = new FileWriter(rutaCompleta)) {
            // Cabecera
            fw.write("TIPO,ESTRATEGIA,TIEMPO_MS,ITERACIONES\n");
            // Filas
            for (ResultadoOrdenamiento r : resultados) {
                fw.write(String.format("%s,%s,%.3f,%d\n",
                        r.getType(),
                        r.getStrategy(),
                        r.getTimeElapsedMillis(),
                        r.getIterations()));
            }
        }
    }

    /**
     * Exporta los resultados de ordenamiento a un archivo PDF.
     * @param rutaCompleta Ruta completa del archivo PDF a generar
     * @throws IOException Si ocurre un error durante la escritura del archivo
     */
    public void exportarPDF(String rutaCompleta) throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append("%PDF-1.4\n");
        // Objeto 1: Catálogo
        int obj1 = sb.length();
        sb.append("1 0 obj\n<< /Type /Catalog /Pages 2 0 R >>\nendobj\n");
        // Objeto 2: Páginas
        int obj2 = sb.length();
        sb.append("2 0 obj\n<< /Type /Pages /Kids [3 0 R] /Count 1 >>\nendobj\n");
        // Objeto 3: Página
        int obj3 = sb.length();
        sb.append("3 0 obj\n")
                .append("<< /Type /Page /Parent 2 0 R /MediaBox [0 0 612 792] ")
                .append("/Contents 4 0 R /Resources << /Font << /F1 5 0 R >> >> >>\n")
                .append("endobj\n");
        // Objeto 4: Contenido
        StringBuilder contenido = new StringBuilder();
        contenido.append("BT /F1 12 Tf 40 750 Td (Reporte de Ordenamiento) Tj ET\n");
        float y = 730;
        contenido.append("BT /F1 10 Tf 40 ").append(y).append(" Td (TIPO     ESTRATEGIA                   TIEMPO_MS   ITERACIONES) Tj ET\n");
        y -= 15;
        for (ResultadoOrdenamiento r : resultados) {
            String line = String.format("%s %s %.3f %d",
                    r.getType(),
                    r.getStrategy().length() > 25 ? r.getStrategy().substring(0,25) : r.getStrategy(),
                    r.getTimeElapsedMillis(),
                    r.getIterations());
            contenido.append("BT /F1 10 Tf 40 ").append(y).append(" Td (")
                    .append(line.replace("(", "\\(").replace(")", "\\)"))
                    .append(") Tj ET\n");
            y -= 15;
        }
        String stream = contenido.toString();
        int obj4 = sb.length();
        sb.append("4 0 obj\n<< /Length ").append(stream.length()).append(" >>\nstream\n")
                .append(stream)
                .append("endstream\nendobj\n");
        // Objeto 5: Fuente
        int obj5 = sb.length();
        sb.append("5 0 obj\n<< /Type /Font /Subtype /Type1 /BaseFont /Helvetica >>\nendobj\n");
        // xref
        int xref = sb.length();
        sb.append("xref\n0 6\n")
                .append("0000000000 65535 f \n")
                .append(String.format("%010d 00000 n \n", obj1))
                .append(String.format("%010d 00000 n \n", obj2))
                .append(String.format("%010d 00000 n \n", obj3))
                .append(String.format("%010d 00000 n \n", obj4))
                .append(String.format("%010d 00000 n \n", obj5));
        // trailer
        sb.append("trailer\n<< /Size 6 /Root 1 0 R >>\n")
                .append("startxref\n").append(xref).append("\n%%EOF");
        // Escribir
        try (FileOutputStream fos = new FileOutputStream(new File(rutaCompleta))) {
            fos.write(sb.toString().getBytes(StandardCharsets.US_ASCII));
        }
    }
}