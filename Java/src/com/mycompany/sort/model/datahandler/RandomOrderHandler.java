package com.mycompany.sort.model.datahandler;

import com.mycompany.sort.model.politico.Politico;

import java.util.Random;

/**
 * Manejador concreto de la cadena de generación de datos que se encarga de generar
 * un arreglo de objetos {@link Politico} con datos aleatorios.
 *
 * Si el tipo de dato solicitado no es "random", delega la responsabilidad al siguiente
 * manejador en la cadena.
 */
public class RandomOrderHandler extends DataGeneratorHandler {

    /**
     * Genera un arreglo de políticos con valores aleatorios si el tipo solicitado es "random".
     *
     * @param type Tipo de orden solicitado (ej. "random", "sorted", etc.)
     * @param n    Tamaño del arreglo a generar
     * @return     Un arreglo de objetos {@link Politico} con datos aleatorios o el resultado del siguiente handler
     */
    @Override
    public Politico[] generateData(String type, int n) {
        if (!type.equalsIgnoreCase("random") && nextHandler != null) {
            return nextHandler.generateData(type, n);
        }

        Politico[] politicos = new Politico[n];
        Random rand = new Random();
        for (int i = 0; i < n; i++) {
            // Genera políticos con dinero aleatorio (mínimo 100) y una fecha aleatoria válida
            politicos[i] = new Politico(rand.nextInt(990000) + 100, RandomDateGenerator.generateRandomDate());
        }
        return politicos;
    }
}
