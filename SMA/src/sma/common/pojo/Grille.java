package sma.common.pojo;

import java.util.HashMap;
import java.util.Map;

public class Grille {
    private Map<Position, Object> elementsGrille;
    
    /**
     * Cree une grille vide
     */
    public Grille() {
        elementsGrille = new HashMap<Position, Object>();
    }
    
    /**
     * Ajoute un élément dans la grille
     * @param element Element à ajouter
     * @param positionElement Position dans la grille
     * @throws CaseNonVideException Ajout d'un élément sur une case déjà occupée
     */
    public void ajouterElement(Object element, Position positionElement) throws CaseNonVideException {
        if (!estCaseVide(positionElement)) {
            throw new CaseNonVideException("La case en position " + positionElement + " ne peut pas contenir un autre élément.");
        }
        elementsGrille.put(positionElement, element);
    }
    
    /**
     * Retire un élément de la grille et le renvoie
     * @param positionElement Position de l'élément dans la grille
     * @return Element retiré
     */
    public Object retirerElement(Position positionElement) {
        return elementsGrille.remove(positionElement);
    }
    
    public boolean estCaseVide(Position position) {
        return elementsGrille.get(position) == null;
    }
    
    public Object getElement(Position position) {
        return elementsGrille.get(position);
    }
}
