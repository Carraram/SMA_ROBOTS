package sma.environment.services.interfaces;

import sma.common.pojo.NonEmptyGridBoxException;
import sma.common.pojo.Position;
import sma.common.pojo.InvalidPositionException;

public interface IInteraction extends IStore {
    /**
     * Déplacement d'une case vers une de ses voisines
     * @param initPosition Position de départ
     * @param newPosition Position d'arrivée
     * @throws NonEmptyGridBoxException Case d'arrivée déjà occupée
     * @throws InvalidPositionException Position d'arrivée en dehors de la grille
     */
    void move(Position initPosition, Position newPosition) throws NonEmptyGridBoxException, InvalidPositionException;
}
