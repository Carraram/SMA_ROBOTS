package sma.agents.ecoRobot.interfaces;

import sma.agents.pojo.EtatRobot;

public interface IRobotStatus {

	public int getBalance();
	
	public String getOwner();
	
	public EtatRobot getEtatRobot();
	
}
