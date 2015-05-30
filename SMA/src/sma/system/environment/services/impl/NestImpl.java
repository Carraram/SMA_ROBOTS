package sma.system.environment.services.impl;

import sma.system.agents.pojo.RobotStateReadOnly;
import sma.system.environment.services.interfaces.IStore;
import components.environment.Nest;

public class NestImpl extends Nest {
	@Override
	protected IStore make_dropService() {
		return new IStore() {
			
			@Override
			public float dropColorBox(RobotStateReadOnly robotState) {
				if (robotState.getColorBox().name().equals(robotState.getRobotColor().name())) {
					return 2 * robotState.getMaxEnergy() / 3;
				} else {
					return robotState.getMaxEnergy() / 3;
				}
			}
		};
	}

}
