package sma.common.pojo.interfaces;

import java.util.List;
import java.util.Map;

import sma.common.pojo.Position;
import sma.common.pojo.exceptions.NonEmptyGridBoxException;

public interface IGridOperations {
    /**
     * Ajoute un élément dans la grille
     * @param element Element à ajouter
     * @param elementPosition Position dans la grille
     * @throws NonEmptyGridBoxException Ajout d'un élément sur une case déjà occupée
     */
    public void addElement(Object element, Position elementPosition) throws NonEmptyGridBoxException;
    
    /**
     * Retire un élément de la grille et le renvoie
     * @param elementPosition Position de l'élément dans la grille
     * @return Element retiré
     */
    public Object removeElement(Position elementPosition);
    
    /**
     * Indique si la case située à la position indiquée est vide
     * @param position Position de la case
     * @return TRUE si la case est vide, FALSE sinon
     */
    public boolean isEmptyGridBox(Position position);
    
    /**
     * Renvoie l'élément situé à la position indiquée
     * @param position Position dans la grille
     * @return L'élément s'il existe, NULL sinon
     */
    public Object getElement(Position position);
    
    /**
     * Renvoie les éléments situés aux positions données
     * @param positions Liste des positions
     * @return Map liant les positions à l'objet qu'elles contiennent
     */
    public Map<Position, Object> getElementsForPositions(List<Position> positions);
    
    /**
     * Renvoie les objets d'une classe donnée avec leur position
     * @param type Classe de l'objet
     * @return Objets de la classe recherchée, identifiés par leur position
     */
    public Map<Position, Object> getObjectsByType(Class<?> type);
}
