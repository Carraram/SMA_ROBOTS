package sma;

import compEnvironnement.Environnement;
import sma.agents.pojo.EtatRobot;
import sma.common.pojo.CaseNonVideException;
import sma.common.pojo.Couleur;
import sma.common.pojo.Position;
import sma.common.pojo.PositionInvalideException;
import sma.environnement.pojo.BoiteCouleur;
import sma.environnement.services.impl.EnvironnementImpl;

public class RunEnvironnement {

    public static void main(String[] args) {
        System.out.println("Père Castor, raconte nous une histoire !\r\n");
        Environnement.Component environnement = (new EnvironnementImpl()).newComponent();
        System.out.println("\r\n** Au départ, l'environnement était bien peu peuplé **\r\n");
        environnement.visualisationService().afficherEtat();
        System.out.println("\r\n** Puis les robots arrivèrent, ils vinrent de toutes les directions **\r\n");

        try {
            EtatRobot robocop = new EtatRobot(10.F, Couleur.BLEU, new Position(0,0));
            environnement.interactionService().seDeplacer(robocop.getPositionCourante(), new Position(0,1));
            System.out.println("/!\\ Robocop avance vers la droite /!\\");
            EtatRobot terminator = new EtatRobot(20.F, Couleur.ROUGE, new Position(7,3));
            environnement.interactionService().seDeplacer(terminator.getPositionCourante(), new Position(8,3));
            System.out.println("/!\\ Terminator se dirige vers le bas /!\\");
            EtatRobot goldorak = new EtatRobot(5.F, Couleur.VERT, new Position(10,10));
            environnement.interactionService().seDeplacer(goldorak.getPositionCourante(), new Position(10,9));
            System.out.println("/!\\ Goldorak arrive en renfort vers la gauche /!\\");
            EtatRobot optimusPrime = new EtatRobot(5.F, Couleur.BLEU, new Position(20,20));
            environnement.interactionService().seDeplacer(optimusPrime.getPositionCourante(), new Position(19,20));
            System.out.println("/!\\ Optimus Prime se dirige vers le nord /!\\");
            
            System.out.println("\r\n** Ils gagnèrent de plus en plus d'énergie... **\r\n");
            environnement.interactionService().deposerBoite(BoiteCouleur.BLEU, robocop);
            environnement.interactionService().deposerBoite(BoiteCouleur.VERT, terminator);
            environnement.interactionService().deposerBoite(BoiteCouleur.VERT, goldorak);
            environnement.interactionService().deposerBoite(BoiteCouleur.ROUGE, optimusPrime);
            
            System.out.println("\r\n***** Et ce fut l'apocalypse. *****\r\n");
            
        } catch (CaseNonVideException | PositionInvalideException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
