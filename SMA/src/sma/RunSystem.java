package sma;

import sma.system.services.impl.SystemImpl;
import components.system.SMASystem;

/**
 * Lancement de l'environnement
 */
public class RunSystem {
    
    public static void main(String[] args) {
        SMASystem.Component system = (new SystemImpl()).newComponent();
        System.out.println("Client : Lancement du système");
        system.userService().startSystem();
//        try {
//            Thread.sleep(10*1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        System.out.println("Client : Arrêt du système");
//        system.userService().stopSystem();
    }

}
