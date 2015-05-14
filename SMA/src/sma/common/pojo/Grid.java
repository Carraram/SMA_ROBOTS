package sma.common.pojo;

import java.util.HashMap;
import java.util.Map;

public class Grid {
    private Map<Position, Object> gridElements;
    
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
            throw new NonEmptyGridBoxException("La case en position " + elementPosition + " ne peut pas contenir un autre élément.");
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
}
