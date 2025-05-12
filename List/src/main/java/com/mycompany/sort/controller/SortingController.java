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
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

/**
 * Controlador que gestiona la ejecución de diferentes algoritmos de ordenamiento
 * sobre distintas estructuras de datos (lista simple, doble y circular).
 * Permite realizar simulaciones completas y exportar los resultados.
 */
public class SortingController {
    private ListaEnlazadaSimple<Politico> listaSimpleActual;
    private ListaEnlazadaDoble<Politico> listaDobleActual;
    private ListaEnlazadaSimpleCircular<Politico> listaCircularActual;
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
        this.listaSimpleActual = new ListaEnlazadaSimple<>();
        this.listaDobleActual = new ListaEnlazadaDoble<>();
        this.listaCircularActual = new ListaEnlazadaSimpleCircular<>();

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
    //--------------- Mostrar Elementos ------------------------
    /**
     * Obtiene los primeros 10 elementos de la lista simple como cadena
     */
    public String getPrimeros10ListaSimple() {
        return obtenerPrimerosNElementos(listaSimpleActual, 10);
    }

    /**
     * Obtiene los primeros 10 elementos de la lista doble como cadena
     */
    public String getPrimeros10ListaDoble() {
        return obtenerPrimerosNElementos(listaDobleActual, 10);
    }

    /**
     * Obtiene los primeros 10 elementos de la lista circular como cadena
     */
    public String getPrimeros10ListaCircular() {
        return obtenerPrimerosNElementos(listaCircularActual, 10);
    }

    /**
     * Método genérico para obtener los primeros N elementos de cualquier lista
     */
    private String obtenerPrimerosNElementos(ListaEnlazadaSimple<Politico> lista, int n) {
        StringBuilder sb = new StringBuilder();
        Nodo<Politico> actual = lista.getCabeza();
        int count = 0;

        while (actual != null && count < n) {
            sb.append(actual.getDato().toString()).append("\n");
            actual = actual.getSiguiente();
            count++;
        }
        return sb.toString();
    }

    private String obtenerPrimerosNElementos(ListaEnlazadaDoble<Politico> lista, int n) {
        StringBuilder sb = new StringBuilder();
        NodoDoble<Politico> actual = lista.getCabeza();
        int count = 0;

        while (actual != null && count < n) {
            sb.append(actual.getDato().toString()).append("\n");
            actual = actual.getSiguiente();
            count++;
        }
        return sb.toString();
    }

    private String obtenerPrimerosNElementos(ListaEnlazadaSimpleCircular<Politico> lista, int n) {
        StringBuilder sb = new StringBuilder();
        if (lista.estaVacia())
            return "";

        Nodo<Politico> actual = lista.getCabeza();
        int count = 0;

        do {
            sb.append(actual.getDato().toString()).append("\n");
            actual = actual.getSiguiente();
            count++;
        } while (actual != lista.getCabeza() && count < n);

        return sb.toString();
    }

    //-------- Listas Manipulables -----------------------------
    /**
     * Crea un arreglo ordenado de políticos para pruebas.
     * 
     * @param tamanio Cantidad de políticos a generar
     * @return Arreglo de políticos ordenados
     */
    public Politico[] crearArregloOrdenado(int tamanio) {
        return dataGenerator.generateData("SORTED", tamanio);
    }
    public String getPrimeros10Elementos(String tipoLista) {
    switch (tipoLista) {
        case "simple":
            return getPrimeros10ListaSimple();
        case "doble":
            return getPrimeros10ListaDoble();
        case "circular":
            return getPrimeros10ListaCircular();
        default:
            return "";
    }
}

public void insertarPolitico(String tipoLista, int dinero, LocalDate fecha) {
    Politico politico = new Politico(dinero, fecha);
    switch (tipoLista) {
        case "simple":
            insertarAlFinalListaSimple(politico);
            break;
        case "doble":
            insertarAlFinalListaDoble(politico);
            break;
        case "circular":
            insertarAlFinalListaCircular(politico);
            break;
    }
}

public void insertarPoliticoAlInicio(String tipoLista, int dinero, LocalDate fecha) {
    Politico politico = new Politico(dinero, fecha);
    switch (tipoLista) {
        case "simple":
            listaSimpleActual.insertarAlInicio(politico);
            break;
        case "doble":
            listaDobleActual.insertarAlInicio(politico);
            break;
        case "circular":
            listaCircularActual.insertarAlInicio(politico);
            break;
    }
}

public void eliminarPolitico(String tipoLista, int dinero, LocalDate fecha) {
    Politico politico = new Politico(dinero, fecha);
    switch (tipoLista) {
        case "simple":
            listaSimpleActual.eliminar(politico);
            break;
        case "doble":
            listaDobleActual.eliminar(politico);
            break;
        case "circular":
            listaCircularActual.eliminar(politico);
            break;
    }
}

public boolean buscarPolitico(String tipoLista, int dinero, LocalDate fecha) {
    Politico politico = new Politico(dinero, fecha);
    switch (tipoLista) {
        case "simple":
            return contiene(listaSimpleActual, politico);
        case "doble":
            return contiene(listaDobleActual, politico);
        case "circular":
            return contiene(listaCircularActual, politico);
        default:
            return false;
    }
}

private boolean contiene(ListaEnlazadaSimple<Politico> lista, Politico objetivo) {
    if (lista.estaVacia()) {
        return false;
    }
    
    // Caso especial para la cabeza
    if (lista.getCabeza().getDato().equals(objetivo)) {
        return true;
    }
    
    // Usar buscarNodoAnterior para el resto de nodos
    Nodo<Politico> nodoAnterior = lista.buscarNodoAnterior(objetivo);
    return nodoAnterior != null;
}

private boolean contiene(ListaEnlazadaDoble<Politico> lista, Politico objetivo) {
    NodoDoble<Politico> actual = lista.getCabeza();
    while (actual != null) {
        if (actual.getDato().equals(objetivo))
            return true;
        actual = actual.getSiguiente();
    }
    return false;
}

private boolean contiene(ListaEnlazadaSimpleCircular<Politico> lista, Politico objetivo) {
    if (lista.estaVacia())
        return false;

    Nodo<Politico> actual = lista.getCabeza();
    int count = 0;
    do {
        if (actual.getDato().equals(objetivo))
            return true;
        actual = actual.getSiguiente();
        count++;
    } while (actual != lista.getCabeza() && count < lista.getTamanno());

    return false;
}

    /**
     * Carga datos en las listas para manipulación.
     * 
     * @param datos Arreglo de políticos a cargar
     */
    public void cargarDatosParaManipulacion(Politico[] datos) {
        this.listaSimpleActual = convertirArrayAListaSimple(datos);
        this.listaDobleActual = convertirArrayAListaDoble(datos);
        this.listaCircularActual = convertirArrayAListaCircular(datos);
    }

    // Métodos para manipulación de lista simple

    public void insertarAlInicioListaSimple(Politico politico) {
        listaSimpleActual.insertarAlInicio(politico);
    }

    public void insertarAlFinalListaSimple(Politico politico) {
        listaSimpleActual.insertarAlFinal(politico);
    }

    public boolean eliminarDeListaSimple(Politico politico) {
        return listaSimpleActual.eliminar(politico);
    }

    public Politico eliminarPrimeroListaSimple() {
        return listaSimpleActual.eliminarAlInicio();
    }

    public ListaEnlazadaSimple<Politico> getListaSimpleActual() {
        return listaSimpleActual;
    }

    // Métodos para manipulación de lista doble

    public void insertarAlInicioListaDoble(Politico politico) {
        listaDobleActual.insertarAlInicio(politico);
    }

    public void insertarAlFinalListaDoble(Politico politico) {
        listaDobleActual.insertarAlFinal(politico);
    }

    public boolean eliminarDeListaDoble(Politico politico) {
        return listaDobleActual.eliminar(politico);
    }

    public ListaEnlazadaDoble<Politico> getListaDobleActual() {
        return listaDobleActual;
    }

    // Métodos para manipulación de lista circular

    public void insertarAlInicioListaCircular(Politico politico) {
        listaCircularActual.insertarAlInicio(politico);
    }

    public void insertarAlFinalListaCircular(Politico politico) {
        listaCircularActual.insertarAlFinal(politico);
    }

    public boolean eliminarDeListaCircular(Politico politico) {
        return listaCircularActual.eliminar(politico);
    }

    public Politico eliminarPrimeroListaCircular() {
        return listaCircularActual.eliminarAlInicio();
    }

    public ListaEnlazadaSimpleCircular<Politico> getListaCircularActual() {
        return listaCircularActual;
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
        String rutaAdaptada = adaptarRuta(rutaCompleta);
        File archivo = new File(rutaAdaptada);

        File directorioPadre = archivo.getParentFile();
        if (directorioPadre != null && !directorioPadre.exists()) {
            if (!directorioPadre.mkdirs()) {
                throw new IOException("Error creando directorios: " + directorioPadre.getAbsolutePath());
            }
        }

        try (FileWriter fw = new FileWriter(archivo)) {
            fw.write("TIPO,ESTRATEGIA,TIEMPO_MS,ITERACIONES\n");
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
        String rutaAdaptada = adaptarRuta(rutaCompleta);
        File archivo = new File(rutaAdaptada);

        // Verificar y crear directorios padres si no existen
        File directorioPadre = archivo.getParentFile();
        if (directorioPadre != null && !directorioPadre.exists()) {
            if (!directorioPadre.mkdirs()) {
                throw new IOException("Error creando directorios: " + directorioPadre.getAbsolutePath());
            }
        }

        try (FileOutputStream fos = new FileOutputStream(archivo)) {
            StringBuilder pdfContent = new StringBuilder();

            // Encabezado PDF
            pdfContent.append("%PDF-1.4\n");

            // Objetos PDF
            List<Integer> objectPositions = new ArrayList<>();

            // Objeto 1: Catálogo
            objectPositions.add(pdfContent.length());
            pdfContent.append("1 0 obj\n<< /Type /Catalog /Pages 2 0 R >>\nendobj\n");

            // Objeto 2: Páginas
            objectPositions.add(pdfContent.length());
            pdfContent.append("2 0 obj\n<< /Type /Pages /Kids [3 0 R] /Count 1 >>\nendobj\n");

            // Objeto 3: Página
            objectPositions.add(pdfContent.length());
            pdfContent.append("3 0 obj\n<< /Type /Page /Parent 2 0 R /MediaBox [0 0 612 792] ")
                    .append("/Contents 4 0 R /Resources << /Font << /F1 5 0 R >> >> >>\nendobj\n");

            // Preparar contenido
            StringBuilder content = new StringBuilder();
            content.append("BT /F1 12 Tf 40 750 Td (Reporte de Ordenamiento) Tj ET\n");
            float y = 730;
            content.append("BT /F1 10 Tf 40 ").append(y).append(" Td (TIPO     ESTRATEGIA                   TIEMPO_MS   ITERACIONES) Tj ET\n");

            y -= 15;
            for (ResultadoOrdenamiento r : resultados) {
                String line = String.format("%s %s %.3f %d",
                        r.getType(),
                        r.getStrategy().length() > 25 ? r.getStrategy().substring(0, 25) : r.getStrategy(),
                        r.getTimeElapsedMillis(),
                        r.getIterations());

                // Escapar caracteres especiales en texto PDF
                line = escapePDFString(line);
                content.append("BT /F1 10 Tf 40 ").append(y).append(" Td (").append(line).append(") Tj ET\n");
                y -= 15;
            }

            // Objeto 4: Contenido
            objectPositions.add(pdfContent.length());
            String streamContent = content.toString();
            pdfContent.append("4 0 obj\n<< /Length ").append(streamContent.length()).append(" >>\nstream\n")
                    .append(streamContent)
                    .append("\nendstream\nendobj\n");

            // Objeto 5: Fuente
            objectPositions.add(pdfContent.length());
            pdfContent.append("5 0 obj\n<< /Type /Font /Subtype /Type1 /BaseFont /Helvetica >>\nendobj\n");

            // Tabla xref
            int xrefPosition = pdfContent.length();
            pdfContent.append("xref\n0 6\n0000000000 65535 f \n");

            for (int pos : objectPositions) {
                pdfContent.append(String.format("%010d 00000 n \n", pos));
            }

            // Trailer
            pdfContent.append("trailer\n<< /Size 6 /Root 1 0 R >>\n")
                    .append("startxref\n").append(xrefPosition).append("\n%%EOF");

            // Escribir el contenido al archivo
            fos.write(pdfContent.toString().getBytes(StandardCharsets.US_ASCII));
        }
    }

    private String escapePDFString(String text) {
        // Escapar caracteres especiales en strings PDF
        return text.replace("\\", "\\\\")
                .replace("(", "\\(")
                .replace(")", "\\)")
                .replace("\r", "\\r")
                .replace("\n", "\\n");
    }

    private String adaptarRuta(String ruta) {
        // Reemplazar cualquier marcador de usuario con el directorio home real
        ruta = ruta.replace("%USER%", System.getProperty("user.home"));

        // Normalizar separadores de ruta
        return Paths.get(ruta).normalize().toString();
    }
}