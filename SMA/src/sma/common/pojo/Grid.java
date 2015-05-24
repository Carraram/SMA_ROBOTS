package sma.common.pojo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Grid {
    /**
     * Ensemble des éléments identifiés par leur position
     */
    private Map<Position, Object> gridElements;
    
    /**
     * Message d'erreur pour l'exception NonEmptyGridBoxException
     */
    private final String nonEmptyGridBoxExceptionMessage = "La case en position %s ne peut pas contenir un autre élément.";
    
    /**
     * Crée une grille vide
     */
    public Grid() {
        gridElements = new HashMap<Position, Object>();
    }
    
    /**
     * Ajoute un élément dans la grille
     * @param element Element à ajouter
     * @param elementPosition Position dans la grille
     * @throws NonEmptyGridBoxException Ajout d'un élément sur une case déjà occupée
     */
    public void addElement(Object element, Position elementPosition) throws NonEmptyGridBoxException {
        if (!isEmptyGridBox(elementPosition)) {
            throw new NonEmptyGridBoxException(String.format(nonEmptyGridBoxExceptionMessage, elementPosition.toString()));
        }
        gridElements.put(elementPosition, element);
    }
    
    /**
     * Retire un élément de la grille et le renvoie
     * @param elementPosition Position de l'élément dans la grille
     * @return Element retiré
     */
    public Object removeElement(Position elementPosition) {
        return gridElements.remove(elementPosition);
    }
    
    /**
     * Indique si la case située à la position indiquée est vide
     * @param position Position de la case
     * @return TRUE si la case est vide, FALSE sinon
     */
    public boolean isEmptyGridBox(Position position) {
        return gridElements.get(position) == null;
    }
    
    /**
     * Renvoie l'élément situé à la position indiquée
     * @param position Position dans la grille
     * @return L'élément s'il existe, NULL sinon
     */
    public Object getElement(Position position) {
        return gridElements.get(position);
    }
    
    /**
     * Renvoie les éléments situés aux positions données
     * @param positions Liste des positions
     * @return Map liant les positions à l'objet qu'elles contiennent
     */
    public Map<Position, Object> getElementsForPositions(List<Position> positions) {
        Map<Position, Object> elements = new HashMap<Position, Object>();
        for (Position current : positions) {
            Object objectAtPosition = getElement(current);
            if (objectAtPosition != null) {
                elements.put(current, objectAtPosition);
            }
        }
        return elements;
    }
}
