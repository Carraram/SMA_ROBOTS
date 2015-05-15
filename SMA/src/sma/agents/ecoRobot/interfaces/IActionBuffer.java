package sma.agents.ecoRobot.interfaces;

import java.util.LinkedList;
import java.util.List;

public interface IActionBuffer {

	public void push(String[] element);

	public String[] pop();
}
