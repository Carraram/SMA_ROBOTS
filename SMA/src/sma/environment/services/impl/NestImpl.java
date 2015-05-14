package sma.environment.services.impl;

import sma.agents.pojo.RobotState;
import sma.environment.pojo.ColorBox;
import sma.environment.services.interfaces.IStore;
import components.environment.Nest;

public class NestImpl extends Nest {
	@Override
	protected IStore make_dropService() {
		return new IStore() {
			
			@Override
			public float dropBox(ColorBox box, RobotState robotState) {
				if (box.name().equals(robotState.getRobotColor().name())) {
					return 2 * robotState.getMaxEnergy() / 3;
				} else {
					return robotState.getMaxEnergy() / 3;
				}
			}
		};
	}

}
