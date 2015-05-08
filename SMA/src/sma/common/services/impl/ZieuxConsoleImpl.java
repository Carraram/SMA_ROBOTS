package sma.common.services.impl;

import sma.common.services.interfaces.IAffichage;
import compCommon.Zieux;

/**
 * Implémentation des Zieux dans la console
 */
public class ZieuxConsoleImpl extends Zieux {

    @Override
    protected IAffichage make_affichageService() {
        return new IAffichage() {

            @Override
            public void afficherMessage(String message) {
                System.out.println(message);
            }

            @Override
            public void afficherMessages(String[] messages) {
                for (String str : messages) {
                    System.out.println(str);
                }
            }
        };
    }

}
