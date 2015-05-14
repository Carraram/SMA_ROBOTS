package sma.environment.pojo;

import java.util.HashMap;
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
	 * Cree l'etat de l'environnement
	 * @param nombreBoitesInitial Nombre de boites placees initialement dans la grille
	 * @param gridWidth Nombre de colonnes de la grille
	 * @param gridHeight Nombre de lignes de la grille
	 */
	public EnvironmentState(int initNbBoxes, int gridWidth, int gridHeight) {
	    nests = new HashMap<Colors, Position>();
	    grid = new Grid();
		nbBoxes = initNbBoxes;
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
	        throw new InvalidPositionException("La position " + nestPosition + " est en dehors de la grille");
	    }
	    nests.put(nestColor, nestPosition);
	    grid.addElement(newNest, nestPosition);
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
	        throw new InvalidPositionException("La position " + finish + " est en dehors des limites de la grille");
	    }
	    if (!grid.isEmptyGridBox(finish)) {
	        throw new NonEmptyGridBoxException("La position " + finish + " n'est pas libre");
	    }
	    // TODO Vérifier si déplacement d'une seule case
	    return true;
	}
	
	/**
	 * Indique si une position est valide dans la grille
	 * @param position Position à vérifier
	 * @return TRUE si la Position est contenue dans les limites de la grille, FALSE sinon
	 */
	private boolean isValidPosition(Position position) {
	    boolean coordXValide = position.getCoordX() >= 0 && position.getCoordX() <= gridHeight;
	    boolean coordYValide = position.getCoordY() >= 0 && position.getCoordY() <= gridWidth;
	    return coordXValide && coordYValide;
	}
}
