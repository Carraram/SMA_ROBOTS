package sma.system.control.services.impl;

import java.util.HashMap;

import sma.common.services.impl.XMLPersistenceImpl;
import sma.system.agents.pojo.RobotState;
import sma.system.environment.pojo.EnvironmentState;
import components.control.Persistence;
import components.control.SystemManager;
import components.control.UserAccess;

/**
 * Implémentation de la classe d'accès au système
 */
public class UserAccessImpl extends UserAccess {

    @Override
    protected Persistence make_persistence() {
        HashMap<Class<?>, String> alias = new HashMap<Class<?>, String>();
        alias.put(EnvironmentState.class, "environment");
        alias.put(RobotState.class, "agent");
        // TODO Trouver une alternative pour que chaque agent ait un alias différent
        return new XMLPersistenceImpl(alias);
    }

    @Override
    protected SystemManager make_systemManager() {
        return new SystemManagerImpl();
    }

}
