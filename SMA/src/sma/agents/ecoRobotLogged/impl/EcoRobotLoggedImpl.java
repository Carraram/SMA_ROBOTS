package sma.agents.ecoRobotLogged.impl;

import java.io.File;

import sma.agents.ecoRobot.impl.EcoRobotImpl;
import sma.agents.logging.impl.LoggingImplDirectory;
import sma.agents.logging.interfaces.ILog;
import sma.common.pojo.Couleur;
import sma.common.pojo.Position;
import agent.ecoRobot.EcoRobot;
import agent.ecoRobotLogged.EcoRobotLogged;
import agent.logging.Logging;

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
	protected RobotLogged make_RobotLogged(String owner, final float maxEnergie, final Couleur couleur, final Position positionInitiale) {
		return new RobotLogged() {};
	}
	
	@Override
	protected ILog make_elog() {
		Logging.Logger.Component eLogger = parts().l().create().createStandaloneLogger("BANK");
		return eLogger.log();
	}
	
//	@Override
//	protected IBankManage make_manage() {
//		return new IBankManage() {
//			@Override
//			public void instantiateRobot(String owner) {
//				newRobotLogged(owner);
//			}
//		};
//	}

//	@Override
//	protected BankGUI make_gui() {
//		return new BankGUIImpl();
//	}

}
