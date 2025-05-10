package com.mycompany.sort.SortingStrategyListaCircular;

import com.mycompany.sort.model.SortingStrategy.SortResult;

public interface SortingStrategyListaCircular<T> {
    
    SortResult sort(ListaEnlazadaSimpleCircular<T> lista);

    String getName();


}
