package sma.common.pojo.exceptions;

public class PersistedObjectNotFoundException extends Exception {

    /**
     * Serial UID
     */
    private static final long serialVersionUID = -3866136474097667201L;
    
    /**
     * Crée une nouvelle instance de PersistedObjectNotFoundException
     */
    public PersistedObjectNotFoundException() {}
    
    /**
     * Crée une nouvelle instance de PersistedObjectNotFoundException
     * @param message Message détaillant l'erreur
     */
    public PersistedObjectNotFoundException(String message) {
        super(message);
    }

}
