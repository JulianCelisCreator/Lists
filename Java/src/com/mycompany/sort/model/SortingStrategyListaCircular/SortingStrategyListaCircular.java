package com.mycompany.sort.model.SortingStrategyListaCircular;

import com.mycompany.sort.model.SortingStrategy.SortResult;

import com.mycompany.sort.model.politico.ListaEnlazadaSimpleCircular;

public interface SortingStrategyListaCircular<T> {
    
    SortResult sort(ListaEnlazadaSimpleCircular<T> lista);

    String getName();


}
