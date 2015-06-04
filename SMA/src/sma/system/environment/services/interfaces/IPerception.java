package sma.system.environment.services.interfaces;

import java.util.Map;

import sma.common.pojo.Colors;
import sma.common.pojo.Position;
import sma.common.pojo.exceptions.InvalidPositionException;
import sma.common.pojo.exceptions.ServiceUnavailableException;

/**
 * Services de perception des éléments de l'environnement
 */
public interface IPerception {
    /**
     * Renvoie la position des nids selon leur couleur
     * @return Map liant la couleur des nids à leur position
     * @throws ServiceUnavailableException Service indisponible
     */
    Map<Colors, Position> getNests() throws ServiceUnavailableException;
    
    /**
     * Renvoie les objets situés autour d'une position donnée
     * @param position Position centrale
     * @param offset Champ de vision autour de la position
     * @return Map reliant les positions entourant la position donnée aux éléments qu'elles contiennent
     * @throws InvalidPositionException La position donnée est en dehors des limites de la grille
     * @throws ServiceUnavailableException Service indisponible
     */
    Map<Position, Object> lookAround(Position position, int offset) throws InvalidPositionException, ServiceUnavailableException;
}
