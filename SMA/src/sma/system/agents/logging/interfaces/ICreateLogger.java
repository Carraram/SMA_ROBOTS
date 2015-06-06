package sma.system.agents.logging.interfaces;

import components.agent.logging.Logging;

public interface ICreateLogger {

	/**
	 * Création d'un Standalone Logger
	 * @param name nom du logger
	 * @return le logger
	 */
	public Logging.Logger.Component createStandaloneLogger(String name);

}
