package com.mycompany.sort.model.SortingStrategyListaCircular;

import com.mycompany.sort.model.ResultadoOrdenamiento;

import com.mycompany.sort.model.politico.ListaEnlazadaSimpleCircular;

public interface SortingStrategyListaCircular<T> {
    
    ResultadoOrdenamiento sort(ListaEnlazadaSimpleCircular<T> lista);

    String getName();


}
