package sma.system.environment.services.impl;

import sma.system.agents.pojo.interfaces.IAgentReadOnly;
import sma.system.environment.services.interfaces.IStore;
import components.environment.Nest;

public class NestImpl extends Nest {
	@Override
	protected IStore make_dropService() {
		return new IStore() {
			
			@Override
			public float dropColorBox(IAgentReadOnly robotState) {
				if (robotState.getColorBox().name().equals(robotState.getRobotColor().name())) {
					return 2 * robotState.getMaxEnergy() / 3;
				} else {
					return robotState.getMaxEnergy() / 3;
				}
			}
		};
	}

}
