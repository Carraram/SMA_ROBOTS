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
            public void saveSystemState() {
//                EnvironmentState test = new EnvironmentState(15, 20);
//                try {
//                    test.putBox(ColorBox.RED, new Position(5,5));
//                } catch (NonEmptyGridBoxException | InvalidPositionException e) {
//                    // TODO Auto-generated catch block
//                    e.printStackTrace();
//                }
//                requires().persistenceService().saveObject(test, "test");
//                
            }

            @Override
            public void loadSystemState() {
//                EnvironmentState object;
//                try {
//                    object = (EnvironmentState) requires().persistenceService().loadObject("test");
//                    System.out.println(object.getGridHeight());
//                } catch (PersistedObjectNotFoundException e) {
//                    // TODO Auto-generated catch block
//                    e.printStackTrace();
//                }
            }

            @Override
            public void startSystem() {
            	requires().envDisplayService().startEnvironmentExecution();
                int width = requires().envDisplayService().getEnvironmentState().getGridWidth();
				int height = requires().envDisplayService().getEnvironmentState().getGridHeight();
				window = new MainWindow(width, height);
				window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                // TODO Start agents
            }

            @Override
            public void stopSystem() {
                // TODO Auto-generated method stub
            	requires().envDisplayService().stopEnvironmentExecution();
            }

			@Override
			public void createRobot() {
				// TODO Auto-generated method stub
				requires().agentDisplayService().createRobot();
			}
		};
	}

}
