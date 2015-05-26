package sma.control.services.interfaces;

/**
 * Service des actions sur le système proposées à l'utilisateur
 */
public interface IUserOperations {
    /**
     * Sauvegarde les éléments du système et leur état courant
     */
    void saveSystemState();
    
    /**
     * Charge l'état du système s'il existe
     */
    void loadSystemState();
    
    /**
     * Démarre le système
     */
    void startSystem();
    
    /**
     * Arrête le système
     */
    void stopSystem();
}
