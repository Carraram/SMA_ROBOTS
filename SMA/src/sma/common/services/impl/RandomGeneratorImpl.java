package sma.common.services.impl;

import java.util.Random;

import sma.common.pojo.Position;
import sma.common.services.interfaces.IGeneration;
import components.common.Generator;

/**
 * Générateur aléatoire
 */
public class RandomGeneratorImpl extends Generator {

    @Override
    protected IGeneration make_generationService() {
        return new IGeneration() {
            
            @Override
            public Position generatePosition(int minX, int maxX, int minY, int maxY) {
                // TODO Faire un random amélioré
                Random random = new Random();
                // Intervalle [minX ; maxX] avec minX et maxX inclus
                int posX = minX + random.nextInt(maxX - minX + 1);
                // Intervalle [minY ; maxY] avec minY et maxY inclus
                int posY = minY + random.nextInt(maxY - minY + 1);
                return new Position(posX, posY);
            }
        };
    }

}
