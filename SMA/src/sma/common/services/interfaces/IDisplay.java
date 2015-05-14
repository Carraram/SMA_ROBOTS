package sma.common.services.interfaces;

public interface IDisplay {
    /**
     * Affiche un message simple
     * @param message Message à afficher
     */
	void displayMessage(String message);
	
	/*
	 * Affiche un ensemble de messages
	 */
	void displayMessages(String [] messages);
}
