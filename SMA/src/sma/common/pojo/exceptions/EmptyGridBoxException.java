package sma.common.pojo.exceptions;

public class EmptyGridBoxException extends Exception {

    /**
     * Serial UID
     */
    private static final long serialVersionUID = 389980092374684707L;
    
    /**
     * Crée une nouvelle instance de EmptyGridBoxException
     */
    public EmptyGridBoxException() {}
    
    /**
     * Crée une nouvelle instance de EmptyGridBoxException
     * @param message Message détaillant l'erreur
     */
    public EmptyGridBoxException(String message) {
        super(message);
    }

}
