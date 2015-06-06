package sma.system.agents.ecoRobotLogged.interfaces;

import java.util.List;
import java.util.Map;

import sma.common.pojo.Colors;
import sma.common.pojo.Position;
import sma.system.agents.pojo.interfaces.IAgentReadOnly;

public interface IAgentManagement {

	/**
	 * Création des robots
	 */
	void createRobot();
	
	/**
	 * Retourne une Map Position Couleur de tous les robots
	 * @return Map Position Couleur de tous les robots
	 */
	Map<Position, Colors> getRobotsPositions();
	
	/**
	 * Retourne une liste de tous les états des robots
	 * @return liste de tous les états des robots
	 */
	List<IAgentReadOnly> getRobotsStatuses();
	
}
