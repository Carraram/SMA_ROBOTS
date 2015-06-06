package sma.system.control.services.impl;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

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
				requires().agentDisplayService().createRobot();
                int width = requires().envDisplayService().getEnvironmentState().getGridWidth();
				int height = requires().envDisplayService().getEnvironmentState().getGridHeight();
				window = new MainWindow(width, height);
				window.setNests(requires().envDisplayService().getEnvironmentState().getNestsWithPositions());
				window.setBoxs(requires().envDisplayService().getEnvironmentState().getAllBoxes());
				window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				window.addWindowListener(new WindowAdapter() {
			        @Override
			        public void windowClosing(WindowEvent event) {
			        	System.out.println("stop System");
//			        	stopSystem();
			        	System.out.println("exit");
			        	window.dispose();
			            System.exit(0);
			        }
			    });
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