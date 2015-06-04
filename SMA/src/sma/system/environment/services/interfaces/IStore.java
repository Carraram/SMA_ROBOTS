package sma.system.environment.services.interfaces;

import sma.common.pojo.exceptions.ServiceUnavailableException;
import sma.system.agents.pojo.interfaces.IAgentReadOnly;

public interface IStore {
    /**
     * Dépose une boite de couleur dans un nid
     * @param robotState Etat du robot qui dépose la boite
     * @return Energie reçue par le robot en fonction de sa couleur et de la couleur de la boite qu'il transporte
     * @throws ServiceUnavailableException Service indisponible
     */
	float dropColorBox(IAgentReadOnly robotState) throws ServiceUnavailableException;
}
