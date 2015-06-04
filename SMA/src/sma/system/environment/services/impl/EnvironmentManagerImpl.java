package sma.system.environment.services.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import sma.common.pojo.EmptyGridBoxException;
import sma.common.pojo.NonEmptyGridBoxException;
import sma.common.pojo.Colors;
import sma.common.pojo.Position;
import sma.common.pojo.InvalidPositionException;
import sma.common.pojo.ServiceUnavailableException;
import sma.system.agents.pojo.interfaces.IAgentReadOnly;
import sma.system.environment.pojo.ColorBox;
import sma.system.environment.pojo.EnvironmentState;
import sma.system.environment.pojo.EnvironmentStateReadOnly;
import sma.system.environment.pojo.NotABoxException;
import sma.system.environment.pojo.interfaces.IEnvironmentOperations;
import sma.system.environment.services.interfaces.IEnvManagement;
import sma.system.environment.services.interfaces.IInteraction;
import sma.system.environment.services.interfaces.IPerception;
import components.environment.EnvironmentManager;
import components.environment.Nest;
import utils.PropertyFileReader;

public class EnvironmentManagerImpl extends EnvironmentManager {

    /**
     * Etat de l'environnement
     */
    private IEnvironmentOperations environment;

    /**
     * Configuration de l'environnement
     */
    private int[] configurationEnv;

    /**
     * Configuration des nids
     */
    private int configurationNest;

    /**
     * Configuration pour la génération aléatoire
     */
    private int[] configurationRandomTime;

    /**
     * Instances des nids
     */
    private Nest[] nestInstances;

    /*
     * Nombre de boîtes maximal dans l'environnement
     */
    private int maxNumberOfBoxes;
    
    /**
     * Indique si l'environnement est en cours d'exécution
     */
    private boolean environmentRunning;

    /**
     * Gestion du thread générateur
     */
    private ExecutorService executor;

    /**
     * Message d'erreur pour l'exception InvalidPositionException
     */
    private final String invalidPositionExceptionMessage = "La position %s est en dehors des limites de la grille";

    public EnvironmentManagerImpl(Nest redNest, Nest blueNest, Nest greenNest) {
        nestInstances = new Nest[3];
        nestInstances[0] = redNest;
        nestInstances[1] = blueNest;
        nestInstances[2] = greenNest;
        executor = Executors.newSingleThreadExecutor();
        environmentRunning = false;
    }

    @Override
    protected void start() {
        displayMessage("========== CREATION DE L'ENVIRONNEMENT ==========");

        // Configuration de l'environnement
        configurationEnv = new int[4];
        configurationRandomTime = new int[2];
        try {
            displayMessage("Configuration de l'environnement...");
            PropertyFileReader config = new PropertyFileReader("config/environmentConfig.properties");
            configurationEnv[0] = config.getPropertyAsInt("initNumberOfBoxes");
            configurationEnv[1] = config.getPropertyAsInt("gridColumns");
            configurationEnv[2] = config.getPropertyAsInt("gridLines");
            configurationNest = config.getPropertyAsInt("nestDistanceMinPercent");
            configurationRandomTime[0] = config.getPropertyAsInt("randomTimeMin");
            configurationRandomTime[1] = config.getPropertyAsInt("randomTimeMax");
            String maxNumberOfBoxesMethod = config.getPropertyAsString("maxNumberOfBoxesMethod");
            if("value".equals(maxNumberOfBoxesMethod)) {
                // Nombre de boîtes indépendant de la taille de la grille
                maxNumberOfBoxes = config.getPropertyAsInt("maxNumberOfBoxesValue");
            } else {
                // Nombre de boîtes déterminé en pourcentage de cases de la grille
                int maxNumberPercent = config.getPropertyAsInt("maxNumberOfBoxesPercent");
                int nbGridBoxes = environment.getGridHeight() * environment.getGridWidth();
                maxNumberOfBoxes = nbGridBoxes * maxNumberPercent / 100;
            }
        } catch (IOException | NumberFormatException ex) {
            displayError("Une erreur est survenue lors de la lecture du fichier de configuration de l'environnement. Utilisation de la configuration par défaut.", ex);
            // Valeurs par défaut en cas d'échec de la lecture du fichier de configuration
            configurationEnv[0] = 0;
            configurationEnv[1] = 50;
            configurationEnv[2] = 50;
            configurationNest = 50;
            configurationRandomTime[0] = 1;
            configurationRandomTime[1] = 15;
            maxNumberOfBoxes = environment.getGridHeight() * environment.getGridWidth() * 50 / 100;
        }
        environment = new EnvironmentState(configurationEnv[1], configurationEnv[2]);
        displayMessage("*** Environnement configuré");

        displayMessage("Placement des nids dans l'environnement...");
        putNestsIntoEnvironment();
        displayMessage("*** Nids placés dans l'environnement");

        displayMessage("Génération des boites initiales...");
        putBoxesIntoEnvironment();
        displayMessage("*** Boites initiales générées");

        displayMessage("========== L'ENVIRONNEMENT EST PRET ==========");
        super.start();
    }

    @Override
    protected IInteraction make_interactionService() {
        return new IInteraction() {

            @Override
            public float dropColorBox(IAgentReadOnly robotState) throws ServiceUnavailableException {
                checkAvailability();
                float energieRecue = 0.F;
                switch (robotState.getColorBox()) {
                case BLUE:
                    energieRecue = EnvironmentManagerImpl.this.requires().dropBlueService().dropColorBox(robotState);
                    break;
                case RED:
                    energieRecue = EnvironmentManagerImpl.this.requires().dropRedService().dropColorBox(robotState);
                    break;
                case GREEN:
                    energieRecue = EnvironmentManagerImpl.this.requires().dropGreenService().dropColorBox(robotState);
                    break;
                }
                return energieRecue;
            }

            @Override
            public void move(Position initPosition, Position newPosition) throws NonEmptyGridBoxException, InvalidPositionException, ServiceUnavailableException {
                checkAvailability();
                if (environment.isValidShifting(initPosition, newPosition)) {
                    environment.moveRobot(initPosition, newPosition);
                }
            }

            @Override
            public ColorBox takeColorBox(Position boxPosition) throws EmptyGridBoxException, NotABoxException, ServiceUnavailableException {
                checkAvailability();
                return environment.removeBoxAtPosition(boxPosition);
            }
        };
    }

    @Override
    protected IPerception make_perceptionService() {
        return new IPerception() {

            @Override
            public Map<Colors, Position> getNests() throws ServiceUnavailableException {
                checkAvailability();
                return environment.getNestsWithPositions();
            }

            @Override
            public Map<Position, Object> lookAround(Position position,
                    int offset) throws InvalidPositionException, ServiceUnavailableException {
                checkAvailability();
                if (!environment.isValidPosition(position)) {
                    throw new InvalidPositionException(String.format(invalidPositionExceptionMessage, position.toString()));
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
    protected IEnvManagement make_managementService() {
        return new IEnvManagement() {

            @Override
            public void stopEnvironmentExecution() {
                try {
                    checkAvailability();
                    displayMessage("========== ARRET DE L'ENVIRONNEMENT ==========");
                    executor.shutdownNow();
                    // On attend que tous les threads s'arrêtent
                    // On relance la demande d'arrêt toutes les 10s si certains threads ne sont pas terminés
                    while (!executor.isTerminated()) {
                        try {
                            executor.awaitTermination(10, TimeUnit.SECONDS);
                        } catch (InterruptedException e) {
                            displayError("Environment : generator didn't stop, shutdown now", null);
                            executor.shutdownNow();
                        }
                    }
                    environmentRunning = false;
                    displayMessage("*** Générateur arrêté");
                } catch (ServiceUnavailableException e1) {
                    displayMessage("L'environnement est déjà arrêté");
                    // Ne rien faire
                }
            }

            @Override
            public void startEnvironmentExecution() {
                try {
                    checkAvailability();
                    displayMessage("L'environnement est déjà en cours d'exécution");
                    // Ne rien faire
                } catch (ServiceUnavailableException e) {
                    displayMessage("========== LANCEMENT DE L'ENVIRONNEMENT ==========");
                    executor.execute(new Runnable() {
                        @Override
                        public void run() {
                            runRandomBoxGenerator();
                        }
                    });
                    environmentRunning = true;
                    displayMessage("*** Générateur lancé");
                }
            }

            @Override
            public EnvironmentStateReadOnly getEnvironmentState() {
                return new EnvironmentStateReadOnly(environment);
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

    /**
     * Placement des nids dans l'environnement
     */
    private void putNestsIntoEnvironment() {
        try {
            int gridHeight = environment.getGridHeight();
            int gridWidth = environment.getGridWidth();
            int gridMinLength = (gridHeight > gridWidth) ? gridWidth : gridHeight;
            float nestDistancePercent = (float) configurationNest / 100;
            float sideLengthAsFloat = gridMinLength * nestDistancePercent;
            int sideMinLength = (int) sideLengthAsFloat;
            Position initNestPosition = generateRandomPosition(0, gridWidth - sideMinLength, 0, gridHeight - sideMinLength);

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
            displayError("Une erreur est survenue, l'environnement ne peut pas être créé.", e);
            System.exit(0);
        }
    }

    /**
     * Placement des boîtes initiales dans l'environnement
     */
    private void putBoxesIntoEnvironment() {
        int gridHeight = environment.getGridHeight();
        int gridWidth = environment.getGridWidth();
        int nbBoxes = configurationEnv[0];
        int nbBoxesPlaced = 0;
        while (nbBoxesPlaced != nbBoxes) {
            Position boxPosition = generateRandomPosition(0, gridWidth, 0, gridHeight);
            try {
                environment.putBox(generateRandomBox(), boxPosition);
                nbBoxesPlaced++;
            } catch (NonEmptyGridBoxException | InvalidPositionException e) {
                // Rien à faire
            }
        }
    }

    /**
     * Lancement d'un générateur aléatoire de boites
     */
    private void runRandomBoxGenerator(){
        while(true) {
            try {
              if (environment.getNumberOfBoxes() < maxNumberOfBoxes) {
                  Position randomPosition = generateRandomPosition(0, environment.getGridWidth(), 0, environment.getGridHeight());
                  int boxNumber = generateRandomInteger(0, 2);
                  ColorBox newBox = ColorBox.values()[boxNumber];
                  displayMessage("Insertion de la boîte " + newBox + " en position " + randomPosition);
                  environment.putBox(newBox, randomPosition);
                  int nextBoxTime = generateRandomInteger(configurationRandomTime[0], configurationRandomTime[1]);
                  Thread.sleep(nextBoxTime * 1000);
              }
              } catch (NonEmptyGridBoxException | InvalidPositionException e) {
                  // Ne rien faire
                  displayError("Placement de la boîte impossible : case non vide", null);
              } catch (InterruptedException e) {
                  // Demande d'arrêt de l'environnement
                break;
            }
        }
    }

    /**
     * Génère une position aléatoire comprise dans un intervalle (bornes incluses)
     * @param minX Valeur mininimale de la coordonnée X
     * @param maxX Valeur maximale de la coordonnée X
     * @param minY Valeur minimale de la coordonnée Y
     * @param maxY Valeur maximale de la coordonnée Y
     * @return Position générée aléatoirement
     */
    private Position generateRandomPosition(int minX, int maxX, int minY, int maxY) {
        return EnvironmentManagerImpl.this.requires().generationService().generatePosition(minX, maxY, minY, maxY, true, true);
    }

    /**
     * Génère un entier aléatoire inclus dans un intervalle (bornes incluses)
     * @param min Valeur minimale
     * @param max Valeur maximale
     * @return Entier généré
     */
    private int generateRandomInteger(int min, int max) {
        return EnvironmentManagerImpl.this.requires().generationService().generateIntegerWithinRange(min, max, true, true);
    }

    /**
     * Génère une boîte de couleur aléatoire
     * @return Boite de couleur
     */
    private ColorBox generateRandomBox() {
        return ColorBox.values()[generateRandomInteger(0, 2)];
    }

    /**
     * Affiche un message basique
     * @param message Message
     */
    private void displayMessage(String message) {
        EnvironmentManagerImpl.this.requires().displayService().displayMessage(message);
    }

    /**
     * Affiche un message d'erreur et/ou l'exception
     * @param errorMessage Message d'erreur
     * @param ex Exception
     */
    private void displayError(String errorMessage, Exception ex) {
        if (errorMessage != null && !errorMessage.isEmpty()) {
            EnvironmentManagerImpl.this.requires().displayService().displayErrorMessage(errorMessage);
        }
        if (ex != null) {
            ex.printStackTrace();
        }
    }
    
    /**
     * Vérifie si l'environnement est en cours d'exécution
     * @throws ServiceUnavailableException Environnement arrêté
     */
    private void checkAvailability() throws ServiceUnavailableException {
        if (!environmentRunning) {
            throw new ServiceUnavailableException("L'environnement est arrêté");
        }
    }
}
