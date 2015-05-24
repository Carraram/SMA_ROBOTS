package sma;

import components.control.UserAccess;
import components.environment.Environment;
import sma.control.services.impl.UserAccessImpl;
import sma.environment.services.impl.EnvironmentImpl;

/**
 * Lancement de l'environnement
 */
public class RunEnvironnement {

    public static void main(String[] args) {
        Environment.Component environnement = (new EnvironmentImpl()).newComponent();
        environnement.visualisationService().viewState();
        UserAccess.Component userAccess = (new UserAccessImpl()).newComponent();
        userAccess.userService().saveSystemState();
        userAccess.userService().loadSystemState();
    }

}
