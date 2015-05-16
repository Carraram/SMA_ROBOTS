package sma.environment.services.interfaces;

import java.util.Map;

import sma.common.pojo.Colors;
import sma.common.pojo.InvalidPositionException;
import sma.common.pojo.Position;

/**
 * Services de perception des éléments de l'environnement
 */
public interface IPerception {
    /**
     * Renvoie la position des nids selon leur couleur
     * @return Map liant la couleur des nids à leur position
     */
    Map<Colors, Position> getNests();
    
    /**
     * Renvoie les objets situés autour d'une position donnée
     * @param position Position centrale
     * @param offset Champ de vision autour de la position
     * @return Map reliant les positions entourant la position donnée aux éléments qu'elles contiennent
     * @throws InvalidPositionException La position donnée est en dehors des limites de la grille
     */
    Map<Position, Object> lookAround(Position position, int offset) throws InvalidPositionException;
}
