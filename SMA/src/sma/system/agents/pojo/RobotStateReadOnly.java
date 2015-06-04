package sma.system.agents.pojo;

import sma.common.pojo.Colors;
import sma.common.pojo.Position;
import sma.system.agents.pojo.interfaces.IAgentOperations;
import sma.system.agents.pojo.interfaces.IAgentReadOnly;
import sma.system.environment.pojo.ColorBox;

public class RobotStateReadOnly implements IAgentReadOnly {

    private final IAgentOperations fullAgent;
    
    /**
     * Crée l'état d'un robot
     * @param fullAgent Agent dont on veut lire l'état
     */
    public RobotStateReadOnly(IAgentOperations fullAgent) {
        super();
        this.fullAgent = fullAgent;
    }
    
    @Override
    public ColorBox getColorBox() {
        return fullAgent.getColorBox();
    }

    @Override
    public Colors getRobotColor() {
        return fullAgent.getRobotColor();
    }

    @Override
    public float getMaxEnergy() {
        return fullAgent.getMaxEnergy();
    }

    @Override
    public float getCurrentEnergyLevel() {
        return fullAgent.getCurrentEnergyLevel();
    }

    @Override
    public float getCurrentSpeed() {
        return fullAgent.getCurrentSpeed();
    }

    @Override
    public Position getCurrentPosition() {
        return fullAgent.getCurrentPosition();
    }
}
