package sma.system.environment.services.interfaces;

import sma.common.pojo.EmptyGridBoxException;
import sma.common.pojo.NonEmptyGridBoxException;
import sma.common.pojo.Position;
import sma.common.pojo.InvalidPositionException;
import sma.common.pojo.ServiceUnavailableException;
import sma.system.environment.pojo.ColorBox;
import sma.system.environment.pojo.NotABoxException;

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
     * @throws ServiceUnavailableException Environnement arrêté
     */
    void move(Position initPosition, Position newPosition) throws NonEmptyGridBoxException, InvalidPositionException, ServiceUnavailableException;
    
    /**
     * Récupère une boite de couleur de la grille
     * @param boxPosition Position de la boîte à ramasser
     * @return Boite de couleur
     * @throws EmptyGridBoxException La case est vide
     * @throws NotABoxException L'élément n'est pas une boîte
     * @throws ServiceUnavailableException Environnement arrêté
     */
    ColorBox takeColorBox(Position boxPosition) throws EmptyGridBoxException, NotABoxException, ServiceUnavailableException;
}
