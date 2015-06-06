package sma.system.agents.ecoRobotLogged.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import sma.common.pojo.Colors;
import sma.common.pojo.Position;
import sma.system.agents.ecoRobot.impl.EcoRobotImpl;
import sma.system.agents.ecoRobotLogged.interfaces.IAgentManagement;
import sma.system.agents.logging.impl.LoggingImplDirectory;
import sma.system.agents.logging.interfaces.ILog;
import sma.system.agents.pojo.interfaces.IAgentReadOnly;
import components.agent.ecoRobot.EcoRobot;
import components.agent.ecoRobotLogged.EcoRobotLogged;
import components.agent.logging.Logging;
import components.environment.Environment;
import components.agent.ecoRobotLogged.EcoRobotLogged.RobotLogged.Component;

public class EcoRobotLoggedImpl extends EcoRobotLogged {

	private final File logDir;
	private List<RobotLogged.Component> listRobots = new ArrayList<RobotLogged.Component>();

	private ExecutorService executor = Executors.newCachedThreadPool();

	public EcoRobotLoggedImpl(File logDir) {
		this.logDir = logDir;
	}

	@Override
	protected Logging make_logging() {
		return new LoggingImplDirectory(logDir);
	}

	@Override
	protected EcoRobot make_ecoRobot() {
		return new EcoRobotImpl();
	}

	@Override
	protected RobotLogged make_RobotLogged(String owner, final float maxEnergie, final Colors couleur, final Position positionInitiale) {
		return new RobotLogged() {
		};
	}

	@Override
	protected ILog make_elog() {
		Logging.Logger.Component eLogger = parts().logging().create().createStandaloneLogger("Eco_Robot");
		return eLogger.log();
	}

	@Override
	protected IAgentManagement make_agentManagementService() {
		return new IAgentManagement() {

			@Override
			public void createRobot() {
				listRobots.add(newRobotLogged("Test1", 40f, Colors.BLUE, new Position(1, 1)));
				listRobots.add(newRobotLogged("Test2", 30f, Colors.RED, new Position(1, 2)));

				for (final RobotLogged.Component robot : listRobots) {
					System.out.println(robot.status().getRobotState());
					executor.execute(new Runnable() {
						@Override
						public void run() {
							robot.execute().execute();
							synchronized (listRobots) {
								listRobots.remove(robot);
							}
						}
					});
				}
			}

			@Override
			public Map<Position, Colors> getRobotsPositions() {
				Map<Position, Colors> robotsPositions = new HashMap<Position, Colors>();
				for (RobotLogged.Component robot : listRobots) {
					robotsPositions.put(robot.status().getRobotState().getCurrentPosition(), robot.status().getRobotState().getRobotColor());
				}
				return robotsPositions;
			}

			@Override
			public List<IAgentReadOnly> getRobotsStatuses() {
				List<IAgentReadOnly> robotsStatuses = new ArrayList<>();
				for (RobotLogged.Component robot : listRobots) {
					robotsStatuses.add(robot.status().getRobotState());
				}
				return robotsStatuses;
			}
		};
	}

}
