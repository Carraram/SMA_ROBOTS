package sma.control.services.impl;

import java.util.HashMap;

import sma.common.services.impl.XMLPersistenceImpl;
import sma.environment.pojo.EnvironmentState;
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
        return new XMLPersistenceImpl(alias);
    }

    @Override
    protected SystemManager make_systemManager() {
        return new SystemManagerImpl();
    }

}
