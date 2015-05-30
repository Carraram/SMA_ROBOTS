package sma.system.agents.logging.interfaces;

import components.agent.logging.Logging;

public interface ICreateLogger {

	public Logging.Logger.Component createStandaloneLogger(String name);

}
