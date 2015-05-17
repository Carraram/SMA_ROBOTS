package sma.common.services.interfaces;

import sma.common.pojo.Position;

public interface IGeneration {
    /**
     * Génération d'une position comprise dans un intervalle
     * @param minX Valeur minimale générée (coordonnée X)
     * @param maxX Valeur maximale générée (coordonnée X)
     * @param minY Valeur minimale générée (coordonnée Y)
     * @param maxY Valeur maximale générée (coordonnée Y)
     * @return Position générée
     */
    Position generatePosition(int minX, int maxX, int minY, int maxY);
}
