package com.mycompany.sort.controller;

import com.mycompany.sort.model.SortingStrategy.SortResult;
import com.mycompany.sort.model.SortingStrategy.SortingStrategy;
import com.mycompany.sort.model.datachain.DataGeneratorChain;
import com.mycompany.sort.model.politico.ListaEnlazadaSimple;
import com.mycompany.sort.model.politico.Politico;

import java.util.ArrayList;
import java.util.List;

public class SortingController {

    private final DataGeneratorChain generatorChain;
    private final SortingStrategy<Politico> sortingStrategy;
    private final List<SortResult> resultados;

    public SortingController(SortingStrategy<Politico> sortingStrategy) {
        this.generatorChain = new DataGeneratorChain();
        this.sortingStrategy = sortingStrategy;
        this.resultados = new ArrayList<>();
    }

    public void ejecutarSimulacion(int tamanioDatos) {
        resultados.clear(); // Limpiar resultados previos

        procesar("SORTED", tamanioDatos);
        procesar("INVERSE", tamanioDatos);
        procesar("RANDOM", tamanioDatos);
    }

    private void procesar(String tipo, int tamanio) {
        Politico[] datos = generatorChain.generateData(tipo, tamanio);

        ListaEnlazadaSimple<Politico> lista = new ListaEnlazadaSimple<>();
        for (Politico p : datos) {
            lista.insertarAlFinal(p);
        }

        long inicio = System.nanoTime();
        SortResult resultado = sortingStrategy.sort(lista);
        long fin = System.nanoTime();
        double tiempo = (fin - inicio) / 1_000_000.0;

        resultados.add(resultado.withContext(
                tipo, tamanio, sortingStrategy.getName()
        ));
    }

    public List<SortResult> getResultados() {
        return resultados;
    }
}
