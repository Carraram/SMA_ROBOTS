package sma.common.pojo;

public class PositionInvalideException extends Exception {
    /**
     * Serial UID
     */
    private static final long serialVersionUID = 236721522426589695L;

    /**
     * Crée une nouvelle instance de PositionInvalideException
     */
    public PositionInvalideException() {}
    
    /**
     * Crée une nouvelle instance de PositionInvalideException
     * @param message Message détaillant l'erreur
     */
    public PositionInvalideException(String message) {
        super(message);
    }
}
