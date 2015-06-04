package sma.system.environment.pojo.interfaces;

import java.util.List;
import java.util.Map;

import sma.common.pojo.Colors;
import sma.common.pojo.Position;
import sma.common.pojo.exceptions.EmptyGridBoxException;
import sma.common.pojo.exceptions.InvalidPositionException;
import sma.common.pojo.exceptions.NonEmptyGridBoxException;
import sma.system.environment.pojo.ColorBox;
import sma.system.environment.pojo.exceptions.NotABoxException;
import components.environment.Nest;

public interface IEnvironmentOperations extends IEnvironmentReadOnly {
    /**
     * Déplace le robot de sa position de départ jusqu'à sa position d'arrivée
     * @param start Position de départ
     * @param finish Position d'arrivée
     * @throws NonEmptyGridBoxException  Position d'arrivée déjà occupée
     */
    void moveRobot(Position start, Position finish) throws NonEmptyGridBoxException;
    
    /**
     * Crée un nid dans la grille
     * @param newNest Instance du nid
     * @param nestColor Couleur du nid à créer
     * @param nestPosition Position du nid à créer
     * @throws NonEmptyGridBoxException Position déjà occupée
     * @throws InvalidPositionException Position en dehors de la grille
     */
    void createNest(Nest newNest, Colors nestColor, Position nestPosition) throws NonEmptyGridBoxException, InvalidPositionException;
    
    void putBox(ColorBox box, Position boxPosition) throws NonEmptyGridBoxException, InvalidPositionException;
    
    /**
     * Renvoie le nombre de boîtes actuellement dans le système
     * @return Nombre de boites dans le système
     */
    int getNumberOfBoxes();
    
    /**
     * Vérifie si le déplacement est valide par rapport à la position initiale
     * @param start Position de départ
     * @param finish Position d'arrivée
     * @return TRUE si le déplacement est valide, FALSE sinon
     * @throws InvalidPositionException Position d'arrivée en dehors de la grille
     * @throws NonEmptyGridBoxException Position d'arrivée déjà occupée
     */
    boolean isValidShifting(Position start, Position finish) throws InvalidPositionException, NonEmptyGridBoxException;
    
    /**
     * Renvoie les éléments situés aux positions données
     * @param positions Liste des positions
     * @return Map liant les positions à l'objet qu'elles contiennent
     */
    Map<Position, Object> getElementsForPositions(List<Position> positions);
    
    /**
     * Indique si une position est valide dans la grille
     * @param position Position à vérifier
     * @return TRUE si la Position est contenue dans les limites de la grille, FALSE sinon
     */
    boolean isValidPosition(Position position);
    
    /**
     * Retire une boîte de la grille et renvoie celle-ci
     * @param position Position de la boite
     * @return Boite retirée de la grille
     * @throws EmptyGridBoxException Case vide
     * @throws NotABoxException L'élément n'est pas une boîte
     */
    ColorBox removeBoxAtPosition(Position position) throws EmptyGridBoxException, NotABoxException;
}
