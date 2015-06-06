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
import components.agent.ecoRobot.EcoRobot;
import components.agent.ecoRobotLogged.EcoRobotLogged;
import components.agent.logging.Logging;
import components.environment.Environment;
import components.agent.ecoRobotLogged.EcoRobotLogged.RobotLogged.Component;

public class EcoRobotLoggedImpl extends EcoRobotLogged {

	private final File logDir;
	private List<RobotLogged.Component> listRobots =
			new ArrayList<RobotLogged.Component>();
	
    private ExecutorService executor = Executors.newCachedThreadPool();

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
				listRobots.add(newRobotLogged(
						"Test1", 40f, Colors.BLUE, new Position(1, 1)));
				listRobots.add(newRobotLogged(
						"Test2", 30f, Colors.RED, new Position(1, 2)));
				
				for (final RobotLogged.Component robot : listRobots){
					System.out.println(robot.status().getRobotState());
//					robot.execute().execute();
                    executor.execute(new Runnable() {
                        @Override
                        public void run() {
                        	robot.execute().execute();
                        }
                    });
				}
			}

			@Override
			public void getRobots() {
				// TODO Auto-generated method stub
				
			}

			@Override
			public Map<Position, Colors> getRobotsPositions() {
				// TODO Auto-generated method stub
				Map<Position, Colors> robotsPositions = new HashMap<Position, Colors>();
				for (RobotLogged.Component robot : listRobots){
					robotsPositions.put(robot.status().getRobotState().getCurrentPosition(), robot.status().getRobotState().getRobotColor());
				}
				return robotsPositions;
			}
		};
	}

}
