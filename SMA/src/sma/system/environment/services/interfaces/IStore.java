package sma.system.environment.services.interfaces;

import sma.system.agents.pojo.RobotStateReadOnly;

public interface IStore {
    /**
     * Dépose une boite de couleur dans un nid
     * @param robotState Etat du robot qui dépose la boite
     * @return Energie reçue par le robot en fonction de sa couleur et de la couleur de la boite qu'il transporte
     */
	float dropColorBox(RobotStateReadOnly robotState);
}
