package sma.environment.services.impl;

import java.io.IOException;
import java.util.Map;

import sma.agents.pojo.RobotState;
import sma.common.pojo.NonEmptyGridBoxException;
import sma.common.pojo.Colors;
import sma.common.pojo.Position;
import sma.common.pojo.InvalidPositionException;
import sma.environment.pojo.ColorBox;
import sma.environment.pojo.EnvironmentState;
import sma.environment.services.interfaces.IEnvironmentViewing;
import sma.environment.services.interfaces.IInteraction;
import sma.environment.services.interfaces.IPerception;
import components.environment.EnvironmentManager;
import components.environment.Nest;
import utils.PropertyFileReader;

public class EnvironmentManagerImpl extends EnvironmentManager {

    /**
     * Etat de l'environnement
     */
    private EnvironmentState environment;
    
    public EnvironmentManagerImpl(Nest redNest, Nest blueNest, Nest greenNest) {
        System.out.println("\r\n***** La Genèse - Bible des SMA V0.1 *****\r\n");
        System.out.println("Au commencement, le Super Composant Anonyme créa l'environnement...");
        // Configuration de l'environnement
        int[] configurationEnv = new int[3];
        try {
            PropertyFileReader config = new PropertyFileReader("config/environmentConfig.properties");
            configurationEnv[0] = config.getPropertyAsInt("initNumberOfBoxes");
            configurationEnv[1] = config.getPropertyAsInt("gridColumns");
            configurationEnv[2] = config.getPropertyAsInt("gridLines");
        } catch (IOException | NumberFormatException ex) {
            System.err.println("Une erreur est survenue lors de la lecture du fichier de configuration de l'environnement");
            System.err.println("Utilisation de la configuration par défaut");
            ex.printStackTrace();
            // Valeurs par défaut en cas d'échec de la lecture du fichier de configuration
            configurationEnv[0] = 0;
            configurationEnv[1] = 50;
            configurationEnv[2] = 50;
        }
        environment = new EnvironmentState(configurationEnv[0], configurationEnv[1], configurationEnv[2]);
        
        // TODO Créer les nids à équidistance en fonction de la taille de la grille
        try {
            System.out.println("Les trois jours qui suivirent, le Super Composant Anonyme créa les nids...");
            environment.createNest(redNest, Colors.RED, new Position(0,0));
            environment.createNest(blueNest, Colors.BLUE, new Position(1,1));
            environment.createNest(greenNest, Colors.GREEN, new Position(2,2));
        } catch (NonEmptyGridBoxException | InvalidPositionException e) {
            e.printStackTrace();
        }
        
        System.out.println("Le Cinquième jour, le Super Composant Anonyme créa les boites...");
        // TODO Placer les boites de départ
        
        System.out.println("Le Sixième jour, le Super Composant Anonyme créa le générateur de boites...");
        // TODO Lancer le générateur de boites
        System.out.println("Le Septième Jour, le Super Composant Anonyme se reposa...");
        System.out.println("\r\n***** Et c'est ainsi que fut créé l'environnement *****\r\n");
    }
    
	@Override
	protected IInteraction make_interactionService() {
		return new IInteraction() {
			
			@Override
			public float dropBox(ColorBox box, RobotState robotState) {
			    float energieRecue = 0.F;
			    switch (box) {
			    case BLUE:
			        energieRecue = EnvironmentManagerImpl.this.requires().dropBlueService().dropBox(box, robotState);
			        break;
			    case RED:
			        energieRecue = EnvironmentManagerImpl.this.requires().dropRedService().dropBox(box, robotState);
			        break;
			    case GREEN:
			        energieRecue = EnvironmentManagerImpl.this.requires().dropGreenService().dropBox(box, robotState);
			        break;
			    }
			    
			    robotState.increaseEnergy(energieRecue);
				System.out.println("Le robot a recu " + energieRecue + "NE.");
				return energieRecue;
			}

            @Override
            public void move(Position initPosition, Position newPosition) throws NonEmptyGridBoxException, InvalidPositionException {
                if (environment.isValidShifting(initPosition, newPosition)) {
                    environment.moveRobot(initPosition, newPosition);
                }
                
            }
		};
	}

    @Override
    protected IPerception make_perceptionService() {
        return new IPerception() {
            
            @Override
            public Map<Colors, Position> getNests() {
                return environment.getNests();
            }
        };
    }

    @Override
    protected IEnvironmentViewing make_viewingService() {
        return new IEnvironmentViewing() {
            
            @Override
            public void viewState() {
                Map<Colors, Position> nids = environment.getNests();
                String etatNids = "";
                etatNids += "Nid rouge : " + nids.get(Colors.RED);
                etatNids += "   Nid vert : " + nids.get(Colors.GREEN);
                etatNids += "   Nid bleu : " + nids.get(Colors.BLUE);
                String etatBoites = "Nombre de boites dans l'environnement : " + environment.getNumberOfBoxes();
                String couleurMer = "C'est la mer noire";
                EnvironmentManagerImpl.this.requires().displayService().displayMessages(new String[] {etatNids, etatBoites, couleurMer});
            }
        };
    }

}
