package sma.system.agents.ecoRobotLogged.interfaces;

import java.util.List;
import java.util.Map;

import sma.common.pojo.Colors;
import sma.common.pojo.Position;

public interface IAgentManagement {

	void createRobot();
	
	void getRobots();
	
	Map<Position, Colors> getRobotsPositions();
	
//	Map<Colors, List<Position>> getRobotsPositions();

}
