package sma.system.control.services.impl;

import sma.system.control.services.interfaces.IUserOperations;
import components.control.SystemManager;
import components.control.Visualisation;

/**
 * Implémentation du SystemManager
 */
public class SystemManagerImpl extends SystemManager {

	@Override
	protected Visualisation make_display() {
		// TODO Auto-generated method stub
		return new VisualisationImpl();
	}

}
