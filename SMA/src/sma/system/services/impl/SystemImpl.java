package sma.system.services.impl;

import sma.system.control.services.impl.UserAccessImpl;
import sma.system.environment.services.impl.EnvironmentImpl;
import components.control.UserAccess;
import components.environment.Environment;
import components.system.SMASystem;

/**
 * Implémentation du système SMA
 */
public class SystemImpl extends SMASystem {

    @Override
    protected Environment make_environment() {
        return new EnvironmentImpl();
    }

    @Override
    protected UserAccess make_manager() {
        // TODO Auto-generated method stub
        return new UserAccessImpl();
    }

}
