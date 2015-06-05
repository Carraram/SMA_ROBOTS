package sma.system.agents.ecoRobot.impl;

import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import sma.common.pojo.Colors;
import sma.common.pojo.InvalidPositionException;
import sma.common.pojo.Position;
import sma.common.pojo.exceptions.ServiceUnavailableException;
import sma.system.agents.ecoRobot.interfaces.IActionBuffer;
import sma.system.agents.ecoRobot.interfaces.IActuators;
import sma.system.agents.ecoRobot.interfaces.IExecute;
import sma.system.agents.ecoRobot.interfaces.IKnowledge;
import sma.system.agents.ecoRobot.interfaces.IRobotOperations;
import sma.system.agents.ecoRobot.interfaces.IRobotStatus;
import sma.system.agents.ecoRobotLogged.interfaces.IAgentManagement;
import sma.system.agents.pojo.RobotState;
import sma.system.environment.services.interfaces.IInteraction;
import sma.system.agents.pojo.interfaces.IAgentOperations;
import sma.system.environment.services.interfaces.IPerception;
import components.agent.ecoRobot.Action;
import components.agent.ecoRobot.Decision;
import components.agent.ecoRobot.EcoRobot;
import components.agent.ecoRobot.Perception;
import components.agent.ecoRobot.ReusableJoiningComp;
import components.agent.ecoRobot.UniversalProvider;
import components.agent.ecoRobot.ReusableJoiningComp.JoiningEntity;
import components.environment.Environment;

public class EcoRobotImpl extends EcoRobot {

	@Override
	protected Robot make_Robot(final float maxEnergy, final Colors robotColor,
			final Position initPosition) {

		System.out.println("Robot created");
		return new Robot() {

			private float _maxEnergy = maxEnergy;
			private Colors _robotColor = robotColor;
			private Position _initPosition = initPosition;
			private int _offset = 5;

			private IActionBuffer actionBuffer = new IActionBuffer() {
				private LinkedList<String[]> actionBuffer = new LinkedList<String[]>();

				@Override
				public void push(String[] element) {
					this.actionBuffer.push(element);
				}

				@Override
				public String[] pop() {
					return this.actionBuffer.removeFirst();
				}

			};

			private final IAgentOperations robotState = new RobotState(
					maxEnergy, robotColor, initPosition);

			private final AtomicInteger balance = new AtomicInteger();

			@Override
			protected void start() {
				eco_requires().elog().addLine(
						"Added a new Robot with :" + maxEnergy + ","
								+ robotColor + "," + initPosition + ",");
			}

			@Override
			protected IRobotOperations make_operations() {
				return new IRobotOperations() {

					@Override
					public void withdraw(int value) {
						provides().operations().deposit(-value);
						requires().log().addLine(
								value + " were withdrawn, current balance: "
										+ provides().status().getBalance());
					}

					@Override
					public void deposit(int value) {
						balance.addAndGet(value);
						requires().log().addLine(
								value + " were deposited, current balance: "
										+ provides().status().getBalance());
					}
				};
			}

			@Override
			protected IRobotStatus make_status() {
				return new IRobotStatus() {
					@Override
					public int getBalance() {
						return balance.get();
					}

					@Override
					public String getOwner() {
						return null;
					}

					@Override
					public IAgentOperations getRobotState() {
						return robotState;
					}
				};
			}

			@Override
			protected Perception make_perception() {
				return new Perception() {

					@Override
					protected IExecute make_perceive() {
						return new IExecute() {

							@Override
							public void execute() {
								try {
									Map<Colors, Position> nests = requires()
											.envPerception().getNests();
									Map<Position, Object> lookAround = requires()
											.envPerception().lookAround(
													_initPosition, _offset);
									System.out.println(lookAround);
								} catch (ServiceUnavailableException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} catch (sma.common.pojo.exceptions.InvalidPositionException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						};
					}

				};
			}

			@Override
			protected Decision make_decision() {
				return new Decision() {

					@Override
					protected IExecute make_decide() {
						return new IExecute() {

							@Override
							public void execute() {
								requires().perception().execute();
								// décider et setter un enum d'action sur lequel
								// action se basera ?
								requires().action().execute();
							}
						};
					}

				};
			}

			@Override
			protected Action make_action() {
				return new Action() {

					@Override
					protected IExecute make_act() {
						return new IExecute() {

							@Override
							public void execute() {
								// Obtenir la décision dans une variable ...
								// Action
							}
						};
					}

				};
			}

			@Override
			protected IExecute make_execute() {
				return new IExecute() {
					@Override
					public void execute() {
						System.out.println("Exécution du robot");
						// parts().perception().perceive().execute();
						parts().decision().decide().execute();
						// parts().action().act().execute();
					}
				};
			}
		};
	}

	@Override
	protected ReusableJoiningComp make_rjc() {
		// TODO Auto-generated method stub
		return new ReusableJoiningComp() {

			@Override
			protected JoiningEntity make_JoiningEntity() {
				// TODO Auto-generated method stub
				return new JoiningEntity() {

					@Override
					protected IPerception make_joinEnvPerception() {
						// TODO Auto-generated method stub
						return eco_requires().universalEnvPerception();
					}

					@Override
					protected IInteraction make_joinEnvInteraction() {
						// TODO Auto-generated method stub
						return eco_requires().universalEnvInteraction();
					}
				};
			}
		};
	}

}
