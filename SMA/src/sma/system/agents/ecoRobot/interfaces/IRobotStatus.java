package sma.system.agents.ecoRobot.interfaces;

import sma.system.agents.pojo.interfaces.IAgentOperations;

/**
 * Interface d'état du robot
 */
public interface IRobotStatus {

	/**
	 * Retourne l'état du robot
	 * @return L'état du robot
	 */
	public IAgentOperations getRobotState();
	
}
