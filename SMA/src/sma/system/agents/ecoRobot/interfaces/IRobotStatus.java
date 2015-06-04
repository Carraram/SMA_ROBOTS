package sma.system.agents.ecoRobot.interfaces;

import sma.system.agents.pojo.interfaces.IAgentOperations;

public interface IRobotStatus {

	public int getBalance();
	
	public String getOwner();
	
	public IAgentOperations getRobotState();
	
}
