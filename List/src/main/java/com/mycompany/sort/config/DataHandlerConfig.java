package com.mycompany.sort.config;

import com.mycompany.sort.model.datahandler.DataGeneratorHandler;
import com.mycompany.sort.model.datahandler.RandomOrderHandler;
import com.mycompany.sort.model.datahandler.ReverseOrderHandler;
import com.mycompany.sort.model.datahandler.SortedOrderHandler;

public class DataHandlerConfig {
    public static DataGeneratorHandler buildHandlerChain() {
        DataGeneratorHandler sortedHandler = new SortedOrderHandler();
        DataGeneratorHandler reverseHandler = new ReverseOrderHandler();
        DataGeneratorHandler randomHandler = new RandomOrderHandler();

        sortedHandler.setNextHandler(reverseHandler);
        reverseHandler.setNextHandler(randomHandler);

        return sortedHandler;
    }
}