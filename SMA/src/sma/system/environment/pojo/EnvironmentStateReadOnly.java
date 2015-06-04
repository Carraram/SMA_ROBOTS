package sma.system.environment.pojo;

import java.util.List;
import java.util.Map;

import sma.common.pojo.Colors;
import sma.common.pojo.Position;
import sma.system.environment.pojo.interfaces.IEnvironmentOperations;
import sma.system.environment.pojo.interfaces.IEnvironmentReadOnly;

public class EnvironmentStateReadOnly implements IEnvironmentReadOnly {
    /**
     * Etat de l'environnement
     */
    private final IEnvironmentOperations fullEnvironment;

    /**
     * Construit l'état de l'environnement en lecture seule
     * @param fullEnvironment Etat de l'environnement
     */
    public EnvironmentStateReadOnly(IEnvironmentOperations fullEnvironment) {
        super();
        this.fullEnvironment = fullEnvironment;
    }
    
    @Override
    public int getNumberOfBoxes() {
        return fullEnvironment.getNumberOfBoxes();
    }
    
    @Override
    public Map<Colors, Position> getNestsWithPositions() {
        return fullEnvironment.getNestsWithPositions();
    }
    
    @Override
    public int getGridWidth() {
        return fullEnvironment.getGridWidth();
    }
    
    @Override
    public int getGridHeight() {
        return fullEnvironment.getGridHeight();
    }
    
    @Override
    public Map<ColorBox, List<Position>> getAllBoxes() {
        return fullEnvironment.getAllBoxes();
    }

    @Override
    public int getNumberOfElements() {
        return fullEnvironment.getNumberOfElements();
    }
}
