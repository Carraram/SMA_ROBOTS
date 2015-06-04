package sma.common.pojo;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import sma.common.pojo.exceptions.NonEmptyGridBoxException;
import sma.common.pojo.interfaces.IGridOperations;

public class Grid implements IGridOperations {
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
        // Synchronisation des accès à la grille
        gridElements = Collections.synchronizedMap(gridElements);
    }
    
    @Override
    public void addElement(Object element, Position elementPosition) throws NonEmptyGridBoxException {
        if (!isEmptyGridBox(elementPosition)) {
            throw new NonEmptyGridBoxException(String.format(nonEmptyGridBoxExceptionMessage, elementPosition.toString()));
        }
        gridElements.put(elementPosition, element);
    }
    
    @Override
    public Object removeElement(Position elementPosition) {
        return gridElements.remove(elementPosition);
    }
    
    @Override
    public boolean isEmptyGridBox(Position position) {
        return gridElements.get(position) == null;
    }
    
    @Override
    public Object getElement(Position position) {
        return gridElements.get(position);
    }
    
    @Override
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
    
    @Override
    public Map<Position, Object> getObjectsByType(Class<?> type) {
        Map<Position, Object> objects = new HashMap<Position, Object>();
        for(Entry<Position, Object> entry: gridElements.entrySet()){
            if(type.isInstance(entry.getValue())) {
                objects.put(entry.getKey(), entry.getValue());
            }
        }
        return objects;
    }
}
