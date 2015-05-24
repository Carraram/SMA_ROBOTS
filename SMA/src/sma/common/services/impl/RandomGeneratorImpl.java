package sma.common.services.impl;

import java.util.Random;

import sma.common.pojo.Position;
import sma.common.services.interfaces.IGeneration;
import components.common.Generator;

/**
 * Générateur aléatoire
 */
public class RandomGeneratorImpl extends Generator {
    
    private Random random = new Random();

    @Override
    protected IGeneration make_generationService() {
        return new IGeneration() {
            
            @Override
            public Position generatePosition(int minX, int maxX, int minY, int maxY,
                    boolean minInclusive, boolean maxInclusive) {
                /*
                 * Comportement par défaut de nextInt() :
                 * Génération d'une valeur dans l'intervalle [0 ; max[
                 */
                if (!minInclusive) {
                    minX++;
                    minY++;
                }
                if (maxInclusive) {
                    maxX++;
                    maxY++;
                }
                int posX = minX + random.nextInt(maxX - minX);
                int posY = minY + random.nextInt(maxY - minY);
                return new Position(posX, posY);
            }

            @Override
            public int generateIntegerWithinRange(int min, int max,
                    boolean minInclusive, boolean maxInclusive) {
                /*
                 * Comportement par défaut de nextInt() :
                 * Génération d'une valeur dans l'intervalle [0 ; max[
                 */
                min = minInclusive ? min : min + 1;
                max = maxInclusive ? max + 1 : max;
                return min + random.nextInt(max - min);
            }
        };
    }

}
