package sma.system.environment.pojo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import components.environment.Nest;
import sma.common.pojo.Colors;
import sma.common.pojo.Grid;
import sma.common.pojo.Position;
import sma.common.pojo.exceptions.EmptyGridBoxException;
import sma.common.pojo.exceptions.InvalidPositionException;
import sma.common.pojo.exceptions.NonEmptyGridBoxException;
import sma.common.pojo.interfaces.IGridOperations;
import sma.system.environment.pojo.exceptions.NotABoxException;
import sma.system.environment.pojo.interfaces.IEnvironmentOperations;

/**
 * Etat de l'environnement
 */
public class EnvironmentState implements IEnvironmentOperations {
    /**
     * Grille représentant l'environnement
     */
	private IGridOperations grid;
	
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
     * Message d'erreur pour l'exception EmptyGridBoxException
     */
    private final String emptyGridBoxExceptionMessage = "La position %s est vide";
	
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
	
	@Override
	public void moveRobot(Position start, Position finish) throws NonEmptyGridBoxException {
	    grid.addElement(grid.getElement(start), finish);
	    grid.removeElement(start);
	}
	
	@Override
	public void createNest(Nest newNest, Colors nestColor, Position nestPosition) throws NonEmptyGridBoxException, InvalidPositionException {
	    if (!isValidPosition(nestPosition)) {
	        throw new InvalidPositionException(String.format(invalidPositionExceptionMessage, nestPosition.toString()));
	    }
	    nests.put(nestColor, nestPosition);
	    grid.addElement(newNest, nestPosition);
	    nbObjects++;
	}
	
	@Override
	public void putBox(ColorBox box, Position boxPosition) throws NonEmptyGridBoxException, InvalidPositionException {
	    if (!isValidPosition(boxPosition)) {
	        throw new InvalidPositionException(String.format(invalidPositionExceptionMessage, boxPosition.toString()));
	    }
	    grid.addElement(box, boxPosition);
	    nbBoxes++;
	    nbObjects++;
	}
	
	@Override
	public int getNumberOfBoxes() {
	    return nbBoxes;
	}
	
	@Override
	public boolean isValidShifting(Position start, Position finish) throws InvalidPositionException, NonEmptyGridBoxException {
	    if (!isValidPosition(finish)) {
	        throw new InvalidPositionException(String.format(invalidPositionExceptionMessage, finish.toString()));
	    }
	    if (!grid.isEmptyGridBox(finish)) {
	        throw new NonEmptyGridBoxException(String.format(nonEmptyGridBoxExceptionMessage, finish.toString()));
	    }
	    // Vérification de la distance entre les deux positions
	    int xStart = start.getCoordX();
	    int yStart = start.getCoordY();
	    int xFinish = finish.getCoordX();
	    int yFinish = finish.getCoordY();
	    boolean xOk = (xStart - 1 <= xFinish) && (xFinish <= xStart + 1);
	    boolean yOk = (yStart - 1 <= yFinish) && (yFinish <= yStart + 1);
	    if(!(xOk && yOk)) {
	        throw new InvalidPositionException("Les positions de départ et d'arrivée doivent être adjacentes");
	    }
	    return true;
	}
	
	@Override
	public Map<Position, Object> getElementsForPositions(List<Position> positions) {
	    return grid.getElementsForPositions(positions);
	}
	
	@Override
	public boolean isValidPosition(Position position) {
	    boolean coordXValide = position.getCoordX() >= 0 && position.getCoordX() <= gridHeight;
	    boolean coordYValide = position.getCoordY() >= 0 && position.getCoordY() <= gridWidth;
	    return coordXValide && coordYValide;
	}
	
	@Override
	public int getGridWidth() {
	    return gridWidth;
	}
	
	@Override
	public int getGridHeight() {
	   return gridHeight; 
	}
	
	@Override
	public int getNumberOfElements() {
	    return nbObjects;
	}
	
	@Override
	public Map<ColorBox, List<Position>> getAllBoxes () {
	    Map<Position, Object> boites = grid.getObjectsByType(ColorBox.class);
	    List<Position> greenBoxes = new ArrayList<Position>();
	    List<Position> redBoxes = new ArrayList<Position>();
	    List<Position> blueBoxes = new ArrayList<Position>();
	    for (Entry<Position, Object> entry : boites.entrySet()) {
	        ColorBox currentBox = (ColorBox) entry.getValue();
	        switch (currentBox) {
	        case BLUE:
	            blueBoxes.add(entry.getKey());
	            break;
	        case RED:
	            redBoxes.add(entry.getKey());
	            break;
	        case GREEN:
	            greenBoxes.add(entry.getKey());
	        }
	    }
	    Map<ColorBox, List<Position>> allBoxes = new HashMap<ColorBox, List<Position>>();
	    allBoxes.put(ColorBox.BLUE, blueBoxes);
	    allBoxes.put(ColorBox.RED, redBoxes);
	    allBoxes.put(ColorBox.GREEN, greenBoxes);
	    return allBoxes;
	}
	
	@Override
	public ColorBox removeBoxAtPosition(Position position) throws EmptyGridBoxException, NotABoxException {
	    Object box = grid.removeElement(position);
	    if (box == null) {
	        throw new EmptyGridBoxException(String.format(emptyGridBoxExceptionMessage, position.toString()));
	    }
	    if (!(box instanceof ColorBox)) {
	        throw new NotABoxException();
	    }
	    return (ColorBox) box;
	}

    @Override
    public Map<Colors, Position> getNestsWithPositions() {
        return nests;
    }
}
