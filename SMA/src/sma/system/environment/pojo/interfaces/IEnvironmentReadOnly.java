package sma.system.environment.pojo.interfaces;

import java.util.List;
import java.util.Map;

import sma.common.pojo.Colors;
import sma.common.pojo.Position;
import sma.system.environment.pojo.ColorBox;

public interface IEnvironmentReadOnly {
    /**
     * Renvoie le nombre courant de boîtes dans l'environnement
     * @return Nombre de boîtes
     */
    int getNumberOfBoxes();
    
    /**
     * Renvoie le nombre courant d'éléments dans l'environnement
     * @return Nombre courant d'éléments
     */
    int getNumberOfElements();
    
    /**
     * Renvoie les nids (couleur) avec leur position dans la grille
     * @return Nids avec leur position
     */
    Map<Colors, Position> getNestsWithPositions();
    
    /**
     * Renvoie la largeur de la grille
     * @return Largeur de la grille
     */
    int getGridWidth();
    
    /**
     * Renvoie la hauteur de la grille
     * @return Hauteur de la grille
     */
    int getGridHeight();
    
    /**
     * Renvoie les positions des boîtes par couleur
     * @return Positions des boîtes par couleur
     */
    Map<ColorBox, List<Position>> getAllBoxes();
}
