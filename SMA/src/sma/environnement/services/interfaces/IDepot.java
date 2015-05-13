package sma.environnement.services.interfaces;

import sma.agents.pojo.EtatRobot;
import sma.environnement.pojo.BoiteCouleur;

public interface IDepot {
    /**
     * Dépose une boite de couleur dans un nid
     * @param boite Boite à déposer
     * @param etatRobot Etat du robot qui dépose la boite
     * @return Energie reçue par le robot en fonction de sa couleur et de la couleur de la boite
     */
	float deposerBoite(BoiteCouleur boite, EtatRobot etatRobot);
}
