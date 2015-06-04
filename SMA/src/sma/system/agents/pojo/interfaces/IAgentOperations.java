package sma.system.agents.pojo.interfaces;

import sma.common.pojo.Position;

public interface IAgentOperations extends IAgentReadOnly {
    /**
     * Augmente l'énergie et met la vitesse à jour
     * @param energy Energie reçue
     */
    void increaseEnergy(float energy);
    
    /**
     * Met à jour la vitesse du robot en fonction de son niveau d'énergie
     */
    void updateSpeed();
    
    /**
     * Met à jour la position du robot
     * @param newPosition Nouvelle position
     */
    void updatePosition(Position newPosition);
}
