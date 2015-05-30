package sma.system.agents.pojo;

import sma.common.pojo.Colors;
import sma.common.pojo.Position;
/**
 * Etat d'un robot
 */
public class RobotState {
	private final float maxEnergy;
	private final Colors color;
	private float energyLevel;
	private float speed;
	private Position position;
	
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
		updateSpeed();
	}
	
	/**
	 * Augmente l'énergie et met la vitesse à jour
	 * @param energy Energie reçue
	 */
	public void increaseEnergy(float energy) {
		float totalEnergy = energyLevel += energy;
		if (totalEnergy > maxEnergy) {
			energyLevel = maxEnergy;
		} else {
			energyLevel = totalEnergy;
		}
		updateSpeed();
	}
	
	/**
	 * Met à jour la vitesse du robot en fonction de son niveau d'énergie
	 */
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
	
	public void updatePosition(Position newPosition) {
	    position = newPosition;
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
