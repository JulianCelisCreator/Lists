package com.mycompany.sort.model.SortingStrategyListaEnlazadaDoble;

import com.mycompany.sort.model.SortingStrategy.SortResult;

import com.mycompany.sort.model.politico.ListaEnlazadaDoble;

public interface SortingStrategyEnlazadaDoble<T> {
    
    SortResult sort(ListaEnlazadaDoble<T> lista);

    String getName();

}
