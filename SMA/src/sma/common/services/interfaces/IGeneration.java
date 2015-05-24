package sma.common.services.interfaces;

import sma.common.pojo.Position;

public interface IGeneration {
    /**
     * Génération d'une position comprise dans un intervalle
     * @param minX Valeur minimale générée (coordonnée X)
     * @param maxX Valeur maximale générée (coordonnée X)
     * @param minY Valeur minimale générée (coordonnée Y)
     * @param maxY Valeur maximale générée (coordonnée Y)
     * @param minInclusive Position (minX, minY) incluse dans l'intervalle
     * @param maxInclusive Position (maxX, maxY) incluse dans l'intervalle
     * @return Position générée
     */
    Position generatePosition(int minX, int maxX, int minY, int maxY, boolean minInclusive, boolean maxInclusive);
    
    /**
     * Génération d'un entier compris dans un intervalle de valeurs
     * @param min Valeur minimale générée
     * @param max Valeur maximale générée
     * @param minInclusive Valeur min incluse dans l'intervalle
     * @param maxInclusive Valeur max incluse dans l'intervalle
     * @return Valeur générée
     */
    int generateIntegerWithinRange(int min, int max, boolean minInclusive, boolean maxInclusive);
}
