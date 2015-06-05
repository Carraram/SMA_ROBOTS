package sma.system.control.services.impl;

import javax.swing.JFrame;

import sma.system.control.gui.window.MainWindow;
import sma.system.control.services.interfaces.IUserOperations;
import components.control.Visualisation;

public class VisualisationImpl extends Visualisation {
	private MainWindow window;
	
	@Override
	protected IUserOperations make_userServiceDisplay() {
		// TODO Auto-generated method stub
		return new IUserOperations() {
			
			@Override
			public void stopSystem() {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void startSystem() {
				int width = requires().envDisplayService().getEnvironmentState().getGridWidth();
				int height = requires().envDisplayService().getEnvironmentState().getGridHeight();
				window = new MainWindow(width, height);
				window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			}
			
			@Override
			public void saveSystemState() {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void loadSystemState() {
				// TODO Auto-generated method stub
				
			}
		};
	}

}
