package sma.environment.pojo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import components.environment.Nest;
import sma.common.pojo.NonEmptyGridBoxException;
import sma.common.pojo.Colors;
import sma.common.pojo.Grid;
import sma.common.pojo.Position;
import sma.common.pojo.InvalidPositionException;

/**
 * Etat de l'environnement
 */
public class EnvironmentState {
    /**
     * Grille représentant l'environnement
     */
	private Grid grid;
	
	/**
	 * Nids situés dans l'environnement
	 */
	private Map<Colors, Position> nests;
	
	/**
	 * Nombre de colonnes de la grille
	 */
	private final int gridWidth;
	
	/**
	 * Nombre de lignes de la grille
	 */
	private final int gridHeight;
	
	/**
	 * Nombre de boites dans le système
	 */
	private int nbBoxes;
	
	/**
	 * Nombre d'objets dans l'environnement
	 */
	private int nbObjects;
	
	/**
	 * Message d'erreur pour l'exception InvalidPositionException
	 */
	private final String invalidPositionExceptionMessage = "La position %s est en dehors des limites de la grille";
	
	/**
	 * Message d'erreur pour l'exception NonEmptyGridBoxException
	 */
	private final String nonEmptyGridBoxExceptionMessage = "La position %s n'est pas libre";
	
	/**
	 * Cree l'etat de l'environnement
	 * @param gridWidth Nombre de colonnes de la grille
	 * @param gridHeight Nombre de lignes de la grille
	 */
	public EnvironmentState(int gridWidth, int gridHeight) {
	    nests = new HashMap<Colors, Position>();
	    grid = new Grid();
		nbObjects = 0;
		this.gridWidth = gridWidth;
		this.gridHeight = gridHeight;
	}
	
	/**
	 * Déplace le robot de sa position de départ jusqu'à sa position d'arrivée
	 * @param start Position de départ
	 * @param finish Position d'arrivée
	 * @throws NonEmptyGridBoxException  Position d'arrivée déjà occupée
	 */
	public void moveRobot(Position start, Position finish) throws NonEmptyGridBoxException {
	    // TODO Gérer le fait qu'un autre robot puisse se déplacer sur la case avant notre robot
	    // TODO Modifier le type de la variable robot (pour perception de l'env par les autres robots)
	    grid.addElement(grid.getElement(start), finish);
	    grid.removeElement(start);
	}
	
	/**
	 * Crée un nid dans la grille
	 * @param newNest Instance du nid
	 * @param nestColor Couleur du nid à créer
	 * @param nestPosition Position du nid à créer
	 * @throws NonEmptyGridBoxException Position déjà occupée
	 * @throws InvalidPositionException Position en dehors de la grille
	 */
	public void createNest(Nest newNest, Colors nestColor, Position nestPosition) throws NonEmptyGridBoxException, InvalidPositionException {
	    if (!isValidPosition(nestPosition)) {
	        throw new InvalidPositionException(String.format(invalidPositionExceptionMessage, nestPosition.toString()));
	    }
	    nests.put(nestColor, nestPosition);
	    grid.addElement(newNest, nestPosition);
	    nbObjects++;
	}
	
	public void putBox(ColorBox box, Position boxPosition) throws NonEmptyGridBoxException, InvalidPositionException {
	    if (!isValidPosition(boxPosition)) {
	        throw new InvalidPositionException(String.format(invalidPositionExceptionMessage, boxPosition.toString()));
	    }
	    grid.addElement(box, boxPosition);
	    nbBoxes++;
	    nbObjects++;
	}
	
	/**
	 * Renvoie la position des nids avec leur couleur
	 * @return Map associant la position des nids à leur couleur
	 * @throws InvalidPositionException Position d'arrivée invalide
	 * @throws NonEmptyGridBoxException Position d'arrivée déjà occupée
	 */
	public Map<Colors, Position> getNests() {
        return nests;
    }
	
	/**
	 * Renvoie le nombre de boîtes actuellement dans le système
	 * @return Nombre de boites dans le système
	 */
	public int getNumberOfBoxes() {
	    return nbBoxes;
	}
	
	/**
	 * Vérifie si le déplacement est valide par rapport à la position initiale
	 * @param start Position de départ
	 * @param finish Position d'arrivée
	 * @return TRUE si le déplacement est valide, FALSE sinon
	 * @throws InvalidPositionException Position d'arrivée en dehors de la grille
	 * @throws NonEmptyGridBoxException Position d'arrivée déjà occupée
	 */
	public boolean isValidShifting(Position start, Position finish) throws InvalidPositionException, NonEmptyGridBoxException {
	    if (!isValidPosition(finish)) {
	        throw new InvalidPositionException(String.format(invalidPositionExceptionMessage, finish.toString()));
	    }
	    if (!grid.isEmptyGridBox(finish)) {
	        throw new NonEmptyGridBoxException(String.format(nonEmptyGridBoxExceptionMessage, finish.toString()));
	    }
	    // TODO Vérifier si déplacement d'une seule case
	    return true;
	}
	
	/**
     * Renvoie les éléments situés aux positions données
     * @param positions Liste des positions
     * @return Map liant les positions à l'objet qu'elles contiennent
     */
	public Map<Position, Object> getElementsForPositions(List<Position> positions) {
	    return grid.getElementsForPositions(positions);
	}
	
	/**
	 * Indique si une position est valide dans la grille
	 * @param position Position à vérifier
	 * @return TRUE si la Position est contenue dans les limites de la grille, FALSE sinon
	 */
	public boolean isValidPosition(Position position) {
	    boolean coordXValide = position.getCoordX() >= 0 && position.getCoordX() <= gridHeight;
	    boolean coordYValide = position.getCoordY() >= 0 && position.getCoordY() <= gridWidth;
	    return coordXValide && coordYValide;
	}
	
	/**
	 * Renvoie la largeur de la grille (nombre de colonnes)
	 * @return Largeur de la grille
	 */
	public int getGridWidth() {
	    return gridWidth;
	}
	
	/**
	 * Renvoie la hauteur de la grille (nombre de lignes)
	 * @return Hauteur de la grille
	 */
	public int getGridHeight() {
	   return gridHeight; 
	}
	
	/**
	 * Renvoie le nombre d'éléments dans la grille
	 * @return Nombre d'éléments dans la grille
	 */
	public int getNumberOfElements() {
	    return nbObjects;
	}
}
