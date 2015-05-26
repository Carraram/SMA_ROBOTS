package sma.system.services.impl;

import sma.environment.services.impl.EnvironmentImpl;
import sma.control.services.impl.UserAccessImpl;
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
