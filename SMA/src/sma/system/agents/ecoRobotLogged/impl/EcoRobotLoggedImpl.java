package sma.system.agents.ecoRobotLogged.impl;

import java.io.File;

import sma.common.pojo.Colors;
import sma.common.pojo.Position;
import sma.system.agents.ecoRobot.impl.EcoRobotImpl;
import sma.system.agents.ecoRobotLogged.interfaces.IAgentManagement;
import sma.system.agents.logging.impl.LoggingImplDirectory;
import sma.system.agents.logging.interfaces.ILog;
import components.agent.ecoRobot.EcoRobot;
import components.agent.ecoRobotLogged.EcoRobotLogged;
import components.agent.logging.Logging;
import components.environment.Environment;

public class EcoRobotLoggedImpl extends EcoRobotLogged {

	private final File logDir;

	public EcoRobotLoggedImpl(File logDir) {
		this.logDir = logDir;
	}

	@Override
	protected Logging make_l() {
		return new LoggingImplDirectory(logDir);
	}

	@Override
	protected EcoRobot make_b() {
		return new EcoRobotImpl();
	}

	@Override
	protected RobotLogged make_RobotLogged(String owner,
			final float maxEnergie, final Colors couleur,
			final Position positionInitiale) {
		return new RobotLogged() {
		};
	}

	@Override
	protected ILog make_elog() {
		Logging.Logger.Component eLogger = parts().l().create()
				.createStandaloneLogger("Eco_Robot");
		return eLogger.log();
	}

	@Override
	protected IAgentManagement make_agentManagementService() {
		// TODO Auto-generated method stub
		return new IAgentManagement() {

			@Override
			public void createRobot() {
				// TODO Auto-generated method stub
				components.agent.ecoRobotLogged.EcoRobotLogged.RobotLogged.Component newRobotLogged = newRobotLogged(
						"Test", 0f, Colors.BLUE, new Position(1, 1));
				newRobotLogged.execute().execute();
				System.out.println(newRobotLogged);
			}
		};
	}

}
