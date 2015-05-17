package sma.environment.services.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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
    private int[] configurationEnv;
    private Nest[] nestInstances;
    
    public EnvironmentManagerImpl(Nest redNest, Nest blueNest, Nest greenNest) {
        System.out.println("========== CREATION DE L'ENVIRONNEMENT ==========");
        
        nestInstances = new Nest[3];
        nestInstances[0] = redNest;
        nestInstances[1] = blueNest;
        nestInstances[2] = greenNest;
        
        // Configuration de l'environnement
        configurationEnv = new int[4];
        try {
            System.out.println("Configuration de l'environnement...");
            PropertyFileReader config = new PropertyFileReader("config/environmentConfig.properties");
            configurationEnv[0] = config.getPropertyAsInt("initNumberOfBoxes");
            configurationEnv[1] = config.getPropertyAsInt("gridColumns");
            configurationEnv[2] = config.getPropertyAsInt("gridLines");
            configurationEnv[3] = config.getPropertyAsInt("nestDistanceMinPercent");
        } catch (IOException | NumberFormatException ex) {
            System.err.println("Une erreur est survenue lors de la lecture du fichier de configuration de l'environnement");
            System.err.println("Utilisation de la configuration par défaut");
            ex.printStackTrace();
            // Valeurs par défaut en cas d'échec de la lecture du fichier de configuration
            configurationEnv[0] = 0;
            configurationEnv[1] = 50;
            configurationEnv[2] = 50;
            configurationEnv[3] = 50;
        }
        environment = new EnvironmentState(configurationEnv[0], configurationEnv[1], configurationEnv[2]);
        System.out.println("*** Environnement configuré");
    }
    
    @Override
    protected void start() {
        try {
            System.out.println("Placement des nids dans l'environnement...");
            int gridHeight = environment.getGridHeight();
            int gridWidth = environment.getGridWidth();
            int gridMinLength = (gridHeight > gridWidth) ? gridWidth : gridHeight;
            int sideMinLength = gridMinLength * configurationEnv[3] / 100;
            Position initNestPosition = EnvironmentManagerImpl.this.requires().generationService().generatePosition(0, gridWidth - sideMinLength, 0, gridHeight - sideMinLength); 
            
            /**
             * TailleAretes = largeurGrille - posX pour avoir la plus grande
             * distance possible entre les nids.
             * Si la hauteur de la grille est insuffisante par rapport à cette
             * taille, alors TailleAretes = hauteurGrille - posY
             */
            int sideLength = gridWidth - initNestPosition.getCoordX();
            if (sideLength + initNestPosition.getCoordY() > gridHeight) {
                sideLength = gridHeight - initNestPosition.getCoordY();
            }
            Position[] nestCoordinates = computeEquilateralTriangleCoordinates(initNestPosition, sideLength);
            environment.createNest(nestInstances[0], Colors.RED, nestCoordinates[0]);
            environment.createNest(nestInstances[1], Colors.BLUE, nestCoordinates[1]);
            environment.createNest(nestInstances[2], Colors.GREEN, nestCoordinates[2]);
        } catch (NonEmptyGridBoxException | InvalidPositionException e) {
            e.printStackTrace();
        }
        System.out.println("*** Nids placés dans l'environnement");
        
        // TODO Placer les boites de départ
        System.out.println("Génération des boites initiales...");
        System.out.println("*** Boites initiales générées");
        
        // TODO Lancer le générateur de boites
        System.out.println("Lancement du générateur de boites...");
        System.out.println("*** Générateur de boites lancé");
        
        System.out.println("========== L'ENVIRONNEMENT EST PRET ==========");
        super.start();
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

            @Override
            public Map<Position, Object> lookAround(Position position,
                    int offset) throws InvalidPositionException {
                if (!environment.isValidPosition(position)) {
                    throw new InvalidPositionException("La position " + position + " n'est pas une position valide");
                }
                List<Position> positionsAround = new ArrayList<Position>();
                int width = environment.getGridWidth();
                int height = environment.getGridHeight();
                int posX = position.getCoordX();
                int posY = position.getCoordY();
                int xMin = (posX - offset) < 0 ? 0 : (posX - offset);
                int yMin = (posY - offset) < 0 ? 0 : (posY - offset);
                int xMax = (posX + offset) > width ? width : (posX + offset);
                int yMax = (posY + offset) > height ? height : (posY + offset);
                for (int i = xMin; i <= xMax; i++) {
                    for (int j = yMin; j <= yMax; j++) {
                        if (!(i == posX && j == posY)) {
                            positionsAround.add(new Position(i, j));
                        }
                    }
                }
                positionsAround.remove(position);
                return environment.getElementsForPositions(positionsAround);
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
                EnvironmentManagerImpl.this.requires().displayService().displayMessages(new String[] {etatNids, etatBoites});
            }
        };
    }
    
    /**
     * Calcule les coordonnées des sommets d'un triangle équilatéral
     * @param leftBottom Sommet de départ pour le calcul
     * @param sideLength Longueur d'un côté du triangle
     * @return Coordonnées des sommets du triangle
     */
    private Position[] computeEquilateralTriangleCoordinates(Position leftBottom, int sideLength) {
        Position[] coordinates = new Position[3];
        int xStart = leftBottom.getCoordX();
        int yStart = leftBottom.getCoordY();
        double sin60MultBySide = Math.round((Math.sqrt(3)/2d) * sideLength);
        Position rightBottom = new Position(xStart + sideLength, yStart);
        Position top = new Position(xStart + sideLength/2, yStart + (int) sin60MultBySide);
        coordinates[0] = leftBottom;
        coordinates[1] = rightBottom;
        coordinates[2] = top;
        return coordinates;
    }

}
