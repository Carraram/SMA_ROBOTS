package sma.common.services.impl;

import sma.common.services.interfaces.IDisplay;
import components.common.Viewer;

/**
 * Implémentation des Zieux dans la console
 */
public class TerminalViewerImpl extends Viewer {

    @Override
    protected IDisplay make_displayService() {
        return new IDisplay() {

            @Override
            public void displayMessage(String message) {
                System.out.println(message);
            }

            @Override
            public void displayMessages(String[] messages) {
                for (String str : messages) {
                    System.out.println(str);
                }
            }
        };
    }

}
