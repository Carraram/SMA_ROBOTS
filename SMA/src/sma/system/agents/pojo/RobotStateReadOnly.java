package sma.system.agents.pojo;

import sma.common.pojo.Colors;
import sma.common.pojo.Position;
import sma.system.environment.pojo.ColorBox;

public class RobotStateReadOnly {

    private final float maxEnergy;
    private final Colors color;
    private float energyLevel;
    private float speed;
    private Position position;
    private ColorBox colorBox;
    
    /**
     * Crée l'état d'un robot
     * @param maxEnergy Energie maximale du robot
     * @param energyLevel Energie courante du robot
     * @param color Couleur du robot
     * @param initPosition Position initiale du robot
     */
    public RobotStateReadOnly(float maxEnergy, float energyLevel, Colors color, Position initPosition, ColorBox colorBox) {
        super();
        this.maxEnergy = maxEnergy;
        this.color = color;
        this.position = initPosition;
        this.colorBox = colorBox;
        this.energyLevel = energyLevel;
    }
    
    public ColorBox getColorBox() {
        return colorBox;
    }
    
    public Colors getRobotColor() {
        return color;
    }
    
    public float getMaxEnergy() {
        return maxEnergy;
    }
    
    public float getCurrentEnergyLevel() {
        return energyLevel;
    }
    
    public float getCurrentSpeed() {
        return speed;
    }
    
    public Position getCurrentPosition() {
        return position;
    }
}
