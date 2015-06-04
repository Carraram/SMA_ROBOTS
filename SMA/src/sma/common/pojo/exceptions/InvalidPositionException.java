package sma.common.pojo.exceptions;

public class InvalidPositionException extends Exception {
    /**
     * Serial UID
     */
    private static final long serialVersionUID = 236721522426589695L;

    /**
     * Crée une nouvelle instance de InvalidPositionException
     */
    public InvalidPositionException() {}
    
    /**
     * Crée une nouvelle instance de InvalidPositionException
     * @param message Message détaillant l'erreur
     */
    public InvalidPositionException(String message) {
        super(message);
    }
}
