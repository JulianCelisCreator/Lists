package com.mycompany.sort.model.SortingStrategyListaEnlazadaDoble;

import com.mycompany.sort.model.ResultadoOrdenamiento;

import com.mycompany.sort.model.politico.ListaEnlazadaDoble;

public interface SortingStrategyEnlazadaDoble<T> {
    
    ResultadoOrdenamiento sort(ListaEnlazadaDoble<T> lista);

    String getName();

}
