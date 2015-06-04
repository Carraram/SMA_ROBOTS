package sma.system.environment.pojo;

public class NotABoxException extends Exception {

    /**
     * Serial UID 
     */
    private static final long serialVersionUID = -589650172667636302L;

    /**
     * Crée une nouvelle instance de NotABoxException
     */
    public NotABoxException() {}
    
    /**
     * Crée une nouvelle instance de NotABoxException
     * @param message Message détaillant l'erreur
     */
    public NotABoxException(String message) {
        super(message);
    }
}
