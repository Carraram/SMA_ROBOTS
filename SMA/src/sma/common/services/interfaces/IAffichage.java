package sma.common.services.interfaces;

public interface IAffichage {
    /**
     * Affiche un message simple
     * @param message Message à afficher
     */
	void afficherMessage(String message);
	
	/*
	 * Affiche un ensemble de messages
	 */
	void afficherMessages(String [] messages);
}
