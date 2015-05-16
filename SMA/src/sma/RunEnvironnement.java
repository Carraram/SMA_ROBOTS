package sma;

import components.environment.Environment;
import sma.environment.services.impl.EnvironmentImpl;

/**
 * Lancement de l'environnement
 */
public class RunEnvironnement {

    public static void main(String[] args) {
        Environment.Component environnement = (new EnvironmentImpl()).newComponent();
        environnement.visualisationService().viewState();
    }

}
