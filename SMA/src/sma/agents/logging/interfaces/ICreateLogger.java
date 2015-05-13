package sma.agents.logging.interfaces;

import agent.logging.Logging;

public interface ICreateLogger {

	public Logging.Logger.Component createStandaloneLogger(String name);

}
