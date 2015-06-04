package sma.system.agents.ecoRobot.impl;

import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicInteger;

import sma.common.pojo.Colors;
import sma.common.pojo.Position;
import sma.system.agents.ecoRobot.interfaces.IActionBuffer;
import sma.system.agents.ecoRobot.interfaces.IActuators;
import sma.system.agents.ecoRobot.interfaces.IExecute;
import sma.system.agents.ecoRobot.interfaces.IKnowledge;
import sma.system.agents.ecoRobot.interfaces.IRobotOperations;
import sma.system.agents.ecoRobot.interfaces.IRobotStatus;
import sma.system.agents.pojo.RobotState;
import sma.system.agents.pojo.interfaces.IAgentOperations;
import sma.system.environment.services.interfaces.IPerception;
import components.agent.ecoRobot.Action;
import components.agent.ecoRobot.Decision;
import components.agent.ecoRobot.EcoRobot;
import components.agent.ecoRobot.Perception;
import components.environment.Environment;

public class EcoRobotImpl extends EcoRobot {

	@Override
	protected Robot make_Robot(final float maxEnergy, final Colors robotColor,
			final Position initPosition) {
		return new Robot() {

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

			private final IAgentOperations robotState = new RobotState(maxEnergy,
					robotColor, initPosition);

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
								// Ajouter des informations au knowledge
								Object infos = requires().sensors().getNests();
								// obtenir la perception autour du robot
								requires().knowledge().setInfos(infos);
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
								Object infos = requires().knowledge()
										.getInfos();
								// Decider en fonction des informations
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
								String[] action = provides().actionBuffer()
										.pop();
								// Exécuter l'action avec les Actuators
							}
						};
					}

					@Override
					protected IActionBuffer make_actionBuffer() {
						return actionBuffer;
					}

				};
			}

			@Override
			protected IExecute make_execute() {
				return new IExecute() {
					@Override
					public void execute() {
						parts().perception().perceive().execute();
						parts().decision().decide().execute();
						parts().action().act().execute();
					}
				};
			}

			@Override
			protected IActuators make_actuators() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override 
			protected IPerception make_sensors() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			protected IKnowledge make_knowledge() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			protected Environment make_env() {
				// TODO Auto-generated method stub
				return null;
			}
		};
	}
}
