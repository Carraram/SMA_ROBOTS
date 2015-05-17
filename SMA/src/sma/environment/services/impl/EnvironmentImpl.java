package sma.environment.services.impl;

import sma.common.services.impl.RandomGeneratorImpl;
import sma.common.services.impl.TerminalViewerImpl;
import components.common.Generator;
import components.common.Viewer;
import components.environment.EnvironmentManager;
import components.environment.Environment;
import components.environment.Nest;

public class EnvironmentImpl extends Environment {

    /**
     * Instance du nid rouge
     */
    private Nest redNest;
    
    /**
     * Instance du nid bleu
     */
    private Nest blueNest;
    
    /**
     * Instance du nid vert
     */
    private Nest greenNest;
    
	@Override
	protected Viewer make_viewer() {
		return new TerminalViewerImpl();
	}

	@Override
	protected EnvironmentManager make_environmentManager() {
		return new EnvironmentManagerImpl(redNest, blueNest, greenNest);
	}

    @Override
    protected Nest make_redNest() {
        redNest = new NestImpl();
        return redNest;
    }

    @Override
    protected Nest make_blueNest() {
        blueNest = new NestImpl();
        return blueNest;
    }

    @Override
    protected Nest make_greenNest() {
        greenNest = new NestImpl();
        return greenNest;
    }

    @Override
    protected Generator make_randomGenerator() {
        return new RandomGeneratorImpl();
    }
}
