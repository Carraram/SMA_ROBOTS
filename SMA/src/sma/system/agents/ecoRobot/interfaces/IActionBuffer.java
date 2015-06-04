package sma.system.agents.ecoRobot.interfaces;

public interface IActionBuffer {

	public void push(String[] element);

	public String[] pop();
}
