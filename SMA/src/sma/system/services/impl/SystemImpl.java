package sma.system.services.impl;

import java.io.File;

import sma.system.agents.ecoRobotLogged.impl.EcoRobotLoggedImpl;
import sma.system.control.services.impl.UserAccessImpl;
import sma.system.environment.services.impl.EnvironmentImpl;
import components.agent.ecoRobotLogged.EcoRobotLogged;
import components.control.UserAccess;
import components.environment.Environment;
import components.system.SMASystem;

/**
 * Implémentation du système SMA
 */
public class SystemImpl extends SMASystem {

    protected Environment make_environment() {
        return new EnvironmentImpl();
    }

    protected UserAccess make_manager() {
        // TODO Auto-generated method stub
        return new UserAccessImpl();
    }

	protected EcoRobotLogged make_agents() {
		// TODO Auto-generated method stub
		return new EcoRobotLoggedImpl(new File(System.getProperty("java.io.tmpdir")));
	}

}
