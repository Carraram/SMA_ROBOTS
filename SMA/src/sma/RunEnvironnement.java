package sma;

import components.environment.Environment;
import sma.agents.pojo.RobotState;
import sma.common.pojo.NonEmptyGridBoxException;
import sma.common.pojo.Colors;
import sma.common.pojo.Position;
import sma.common.pojo.InvalidPositionException;
import sma.environment.pojo.ColorBox;
import sma.environment.services.impl.EnvironmentImpl;

/**
 * Lancement de l'environnement
 */
public class RunEnvironnement {

    public static void main(String[] args) {
        System.out.println("Père Castor, raconte nous une histoire !\r\n");
        Environment.Component environnement = (new EnvironmentImpl()).newComponent();
        System.out.println("\r\n** Au départ, l'environnement était bien peu peuplé **\r\n");
        environnement.visualisationService().viewState();
        System.out.println("\r\n** Puis les robots arrivèrent, ils vinrent de toutes les directions **\r\n");

        try {
            RobotState robocop = new RobotState(10.F, Colors.BLUE, new Position(0,0));
            environnement.interactionService().move(robocop.getCurrentPosition(), new Position(0,1));
            System.out.println("/!\\ Robocop avance vers la droite /!\\");
            RobotState terminator = new RobotState(20.F, Colors.RED, new Position(7,3));
            environnement.interactionService().move(terminator.getCurrentPosition(), new Position(8,3));
            System.out.println("/!\\ Terminator se dirige vers le bas /!\\");
            RobotState goldorak = new RobotState(5.F, Colors.GREEN, new Position(10,10));
            environnement.interactionService().move(goldorak.getCurrentPosition(), new Position(10,9));
            System.out.println("/!\\ Goldorak arrive en renfort vers la gauche /!\\");
            RobotState optimusPrime = new RobotState(5.F, Colors.BLUE, new Position(20,20));
            environnement.interactionService().move(optimusPrime.getCurrentPosition(), new Position(19,20));
            System.out.println("/!\\ Optimus Prime se dirige vers le nord /!\\");
            
            System.out.println("\r\n** Ils gagnèrent de plus en plus d'énergie... **\r\n");
            environnement.interactionService().dropBox(ColorBox.BLUE, robocop);
            environnement.interactionService().dropBox(ColorBox.GREEN, terminator);
            environnement.interactionService().dropBox(ColorBox.GREEN, goldorak);
            environnement.interactionService().dropBox(ColorBox.RED, optimusPrime);
            
            System.out.println("\r\n***** Et ce fut l'apocalypse. *****\r\n");
            
        } catch (NonEmptyGridBoxException | InvalidPositionException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
