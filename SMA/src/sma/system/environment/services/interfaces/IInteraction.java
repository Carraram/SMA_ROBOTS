package sma.system.environment.services.interfaces;

import sma.common.pojo.NonEmptyGridBoxException;
import sma.common.pojo.Position;
import sma.common.pojo.InvalidPositionException;
import sma.system.environment.pojo.ColorBox;

/**
 * Services d'interaction et de modification de l'environnement
 */
public interface IInteraction extends IStore {
    /**
     * Déplacement d'une case vers une de ses voisines
     * @param initPosition Position de départ
     * @param newPosition Position d'arrivée
     * @throws NonEmptyGridBoxException Case d'arrivée déjà occupée
     * @throws InvalidPositionException Position d'arrivée en dehors de la grille
     */
    void move(Position initPosition, Position newPosition) throws NonEmptyGridBoxException, InvalidPositionException;
    
    /**
     * Récupère une boite de couleur de la grille
     * @param boxPosition Position de la boîte à ramasser
     * @return Boite de couleur
     */
    ColorBox takeColorBox(Position boxPosition);
}
