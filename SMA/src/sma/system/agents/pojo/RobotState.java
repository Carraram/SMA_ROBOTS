package sma.system.agents.pojo;

import sma.common.pojo.Colors;
import sma.common.pojo.Position;
import sma.system.agents.pojo.interfaces.IAgentOperations;
import sma.system.environment.pojo.ColorBox;
/**
 * Etat d'un robot
 */
public class RobotState implements IAgentOperations {
	private final float maxEnergy;
	private final Colors color;
	private float energyLevel;
	private float speed;
	private Position position;
	private ColorBox colorBox;
	
	/**
	 * Crée l'état d'un robot
	 * @param maxEnergy Energie maximale du robot
	 * @param color Couleur du robot
	 * @param initPosition Position initiale du robot
	 */
	public RobotState(float maxEnergy, Colors color, Position initPosition) {
		super();
		this.maxEnergy = maxEnergy;
		this.color = color;
		this.position = initPosition;
		energyLevel = this.maxEnergy;
		this.colorBox = null;
		
		updateSpeed();
	}
	
	@Override
	public void increaseEnergy(float energy) {
		float totalEnergy = energyLevel += energy;
		if (totalEnergy > maxEnergy) {
			energyLevel = maxEnergy;
		} else {
			energyLevel = totalEnergy;
		}
		updateSpeed();
	}
	
	@Override
	public void updateSpeed() {
		float oneThird = maxEnergy / 3;
		float twoThirds = 2 * maxEnergy / 3;
		if (energyLevel > twoThirds) {
			speed = 1.F;
		}
		if (energyLevel <= twoThirds && energyLevel > oneThird) {
			speed = 0.5F;
		}
		if (oneThird >= energyLevel && energyLevel > 0) {
			speed = 0.3F;
		}
		if (energyLevel == 0) {
			speed = 0;
		}
	}
	
	@Override
	public void updatePosition(Position newPosition) {
	    position = newPosition;
	}
	
	@Override
	public Colors getRobotColor() {
		return color;
	}
	
	@Override
	public float getMaxEnergy() {
		return maxEnergy;
	}
	
	@Override
	public float getCurrentEnergyLevel() {
		return energyLevel;
	}
	
	@Override
	public float getCurrentSpeed() {
		return speed;
	}
	
	@Override
	public Position getCurrentPosition() {
	    return position;
	}

    @Override
    public ColorBox getColorBox() {
        return colorBox;
    }

	@Override
	public void setColorBox(ColorBox newColorBox) {
		colorBox = newColorBox;
	}
}
