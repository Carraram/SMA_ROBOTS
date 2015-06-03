package sma.system.environment.pojo;

import java.util.List;
import java.util.Map;

import sma.common.pojo.Colors;
import sma.common.pojo.Position;

public class EnvironmentStateReadOnly {
    /**
     * Etat de l'environnement
     */
    private final EnvironmentState fullEnvironment;

    /**
     * Construit l'état de l'environnement en lecture seule
     * @param fullEnvironment Etat de l'environnement
     */
    public EnvironmentStateReadOnly(EnvironmentState fullEnvironment) {
        super();
        this.fullEnvironment = fullEnvironment;
    }
    
    /**
     * Renvoie le nombre courant de boîtes dans l'environnement
     * @return Nombre de boîtes
     */
    public int getNumberOfBoxes() {
        return fullEnvironment.getNumberOfBoxes();
    }
    
    /**
     * Renvoie le nombre courant d'éléments dans l'environnement
     * @return Nombre courant d'éléments
     */
    public int getNumberOfElementsInGrid() {
        return fullEnvironment.getNumberOfElements();
    }
    
    /**
     * Renvoie les nids (couleur) avec leur position dans la grille
     * @return Nids avec leur position
     */
    public Map<Colors, Position> getNestsWithPositions() {
        return fullEnvironment.getNests();
    }
    
    /**
     * Renvoie la largeur de la grille
     * @return Largeur de la grille
     */
    public int getGridWidth() {
        return fullEnvironment.getGridWidth();
    }
    
    /**
     * Renvoie la hauteur de la grille
     * @return Hauteur de la grille
     */
    public int getGridHeight() {
        return fullEnvironment.getGridHeight();
    }
    
    /**
     * Renvoie les positions des boîtes par couleur
     * @return Positions des boîtes par couleur
     */
    public Map<ColorBox, List<Position>> getAllBoxes() {
        return fullEnvironment.getBoxes();
    }
}
