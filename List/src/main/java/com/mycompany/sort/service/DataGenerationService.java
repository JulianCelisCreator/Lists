package com.mycompany.sort.service;

import com.mycompany.sort.model.datahandler.DataGeneratorHandler;
import com.mycompany.sort.model.politico.Politico;

public class DataGenerationService {
    private final DataGeneratorHandler handlerChain;

    // Inyectamos la cadena de handlers a través del constructor
    public DataGenerationService(DataGeneratorHandler handlerChain) {
        this.handlerChain = handlerChain;
    }

    public Politico[] generateData(String type, int size) {
        return handlerChain.generateData(type, size);
    }
}