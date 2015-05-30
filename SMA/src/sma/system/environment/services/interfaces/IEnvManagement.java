package sma.system.environment.services.interfaces;

import sma.system.environment.pojo.EnvironmentStateReadOnly;

/**
 * Services de management de l'environnement
 */
public interface IEnvManagement {
    /**
     * Obtenir l'état courant de l'environnement
     * @return Etat de l'environnement en lecture seule
     */
    EnvironmentStateReadOnly getEnvironmentState();
    
    /**
     * Arrête l'exécution de l'environnement
     */
    void stopEnvironmentExecution();
    
    /**
     * Lance l'exécution de l'environnement
     */
    void startEnvironmentExecution();
}
