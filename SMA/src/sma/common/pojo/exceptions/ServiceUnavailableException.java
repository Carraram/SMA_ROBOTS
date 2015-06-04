package sma.common.pojo.exceptions;

public class ServiceUnavailableException extends Exception {

    /**
     * Serial UID
     */
    private static final long serialVersionUID = 7364719188950006026L;

    /**
     * Crée une nouvelle instance de ServiceUnavailableException
     */
    public ServiceUnavailableException() {}
    
    /**
     * Crée une nouvelle instance de ServiceUnavailableException
     * @param message Message détaillant l'erreur
     */
    public ServiceUnavailableException(String message) {
        super(message);
    }
}
