package sma.common.pojo;

public class NonEmptyGridBoxException extends Exception {
    /**
     * Serial UID
     */
    private static final long serialVersionUID = 3624495252202657238L;

    /**
     * Crée une nouvelle insdtance de CaseNonVideException
     */
    public NonEmptyGridBoxException() {}
    
    /**
     * Crée une nouvelle insdtance de CaseNonVideException
     * @param message Message détaillant l'erreur
     */
    public NonEmptyGridBoxException(String message) {
        super(message);
    }
}
