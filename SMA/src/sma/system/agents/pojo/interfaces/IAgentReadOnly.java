package sma.system.agents.pojo.interfaces;

import sma.common.pojo.Colors;
import sma.common.pojo.Position;
import sma.system.environment.pojo.ColorBox;

/**
 * Opérations de l'agent en lecture seule
 */
public interface IAgentReadOnly {
    /**
     * Renvoie la boite de couleur transportée par le robot
     * @return Boite de couleur
     */
    public ColorBox getColorBox();
    
    /**
     * Renvoie la couleur du robot
     * @return Couleur du robot
     */
    public Colors getRobotColor();
    
    /**
     * Renvoie le niveau maximal d'énergie
     * @return Niveau maximal d'énergie
     */
    public float getMaxEnergy();
    
    /**
     * Renvoie le niveau actuel d'énergie
     * @return Niveau actuel d'énergie
     */
    public float getCurrentEnergyLevel();
    
    /**
     * Renvoie la vitesse actuelle de déplacement
     * @return Vitesse actuelle de déplacement
     */
    public float getCurrentSpeed();
    
    /**
     * Renvoie la position actuelle
     * @return Position actuelle
     */
    public Position getCurrentPosition();
}
