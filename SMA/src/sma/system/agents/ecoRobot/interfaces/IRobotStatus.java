package sma.system.agents.ecoRobot.interfaces;

import sma.system.agents.pojo.RobotState;

public interface IRobotStatus {

	public int getBalance();
	
	public String getOwner();
	
	public RobotState getRobotState();
	
}
