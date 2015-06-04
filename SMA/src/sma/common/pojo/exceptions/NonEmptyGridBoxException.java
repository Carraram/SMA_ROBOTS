package sma.common.pojo.exceptions;

public class NonEmptyGridBoxException extends Exception {
    /**
     * Serial UID
     */
    private static final long serialVersionUID = 3624495252202657238L;

    /**
     * Crée une nouvelle instance de NonEmptyGridBoxException
     */
    public NonEmptyGridBoxException() {}
    
    /**
     * Crée une nouvelle instance de NonEmptyGridBoxException
     * @param message Message détaillant l'erreur
     */
    public NonEmptyGridBoxException(String message) {
        super(message);
    }
}
