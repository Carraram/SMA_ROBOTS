package sma.environnement.services.impl;

import sma.agents.pojo.EtatRobot;
import sma.environnement.pojo.BoiteCouleur;
import sma.environnement.services.interfaces.IDepot;
import compEnvironnement.Nid;

public class NidImpl extends Nid {
	@Override
	protected IDepot make_dropService() {
		return new IDepot() {
			
			@Override
			public float deposerBoite(BoiteCouleur boite, EtatRobot etatRobot) {
				if (boite.name().equals(etatRobot.getCouleurRobot().name())) {
					return 2 * etatRobot.getEnergieMaximale() / 3;
				} else {
					return etatRobot.getEnergieMaximale() / 3;
				}
			}
		};
	}

}
