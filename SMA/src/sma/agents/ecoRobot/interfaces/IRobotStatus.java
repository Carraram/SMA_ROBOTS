package sma.agents.ecoRobot.interfaces;

import sma.agents.pojo.RobotState;

public interface IRobotStatus {

	public int getBalance();
	
	public String getOwner();
	
	public RobotState getRobotState();
	
}
