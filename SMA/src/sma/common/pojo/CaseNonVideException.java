package sma.common.pojo;

public class CaseNonVideException extends Exception {
    /**
     * Serial UID
     */
    private static final long serialVersionUID = 3624495252202657238L;

    /**
     * Crée une nouvelle insdtance de CaseNonVideException
     */
    public CaseNonVideException() {}
    
    /**
     * Crée une nouvelle insdtance de CaseNonVideException
     * @param message Message détaillant l'erreur
     */
    public CaseNonVideException(String message) {
        super(message);
    }
}
