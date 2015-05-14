package sma.environment.services.interfaces;

import sma.agents.pojo.RobotState;
import sma.environment.pojo.ColorBox;

public interface IStore {
    /**
     * Dépose une boite de couleur dans un nid
     * @param box Boite à déposer
     * @param robotState Etat du robot qui dépose la boite
     * @return Energie reçue par le robot en fonction de sa couleur et de la couleur de la boite
     */
	float dropBox(ColorBox box, RobotState robotState);
}
