package sma.system.agents.ecoRobot.impl;

import java.util.Collection;
import java.util.Map;

import sma.common.pojo.Colors;
import sma.common.pojo.Position;
import sma.common.pojo.exceptions.EmptyGridBoxException;
import sma.common.pojo.exceptions.NonEmptyGridBoxException;
import sma.common.pojo.exceptions.ServiceUnavailableException;
import sma.common.services.impl.RandomGeneratorImpl;
import sma.system.agents.ecoRobot.interfaces.IExecute;
import sma.system.agents.ecoRobot.interfaces.IRobotStatus;
import sma.system.agents.pojo.ActionDecided;
import sma.system.agents.pojo.RobotState;
import sma.system.agents.pojo.interfaces.IAgentOperations;
import sma.system.environment.pojo.ColorBox;
import sma.system.environment.pojo.exceptions.NotABoxException;
import sma.system.environment.services.interfaces.IInteraction;
import sma.system.environment.services.interfaces.IPerception;

import components.agent.ecoRobot.Action;
import components.agent.ecoRobot.Decision;
import components.agent.ecoRobot.EcoRobot;
import components.agent.ecoRobot.Perception;
import components.agent.ecoRobot.ReusableJoiningComp;
import components.common.Generator;

public class EcoRobotImpl extends EcoRobot {

	@Override
	protected Robot make_Robot(final float maxEnergy, final Colors robotColor, final Position initPosition) {

		requires().elog().addLine("Création d'un nouveau robot : " + maxEnergy + " " + robotColor + " " + initPosition);

		return new Robot() {

			private final IAgentOperations robotState = new RobotState(maxEnergy, robotColor, initPosition);
			private Map<Colors, Position> nests;
			private Map<Position, Object> lookAround;
			private Map<Position, Object> lookAroundOne;
			private ActionDecided actionDecided;
			private Position targetPosition;
			private int offset = 5;

			public void addLogLine(String line) {
				requires().log().addLine(line);
			}

			@Override
			protected IRobotStatus make_status() {
				return new IRobotStatus() {

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
									nests = requires().envPerception().getNests();
									lookAround = requires().envPerception().lookAround(robotState.getCurrentPosition(), offset);
									lookAroundOne = requires().envPerception().lookAround(robotState.getCurrentPosition(), 1);
									addLogLine("Perception : " + lookAround);
								} catch (ServiceUnavailableException e) {
									e.printStackTrace();
								} catch (sma.common.pojo.exceptions.InvalidPositionException e) {
									e.printStackTrace();
								}
							}
						};
					}

				};
			}

			/**
			 * Retourne la position du premier objet correspondant
			 * 
			 * @param map
			 *            Map avec les objets à vérifier
			 * @param clazz
			 *            Classe que l'on cherche dans la map
			 * @return Position d'un objet de type clazz dans la map
			 */
			public <T> Position getOneKey(Map<Position, Object> map, Class<T> clazz) {
				for (Map.Entry<Position, Object> o : map.entrySet()) {
					if (o != null && o.getValue().getClass() == clazz) {
						return o.getKey();
					}
				}
				return null;
			}

			/**
			 * Retourne la position du premier objet correspondant
			 * 
			 * @param map
			 *            Map avec les objets à vérifier
			 * @param clazz
			 *            Classe que l'on cherche dans la map
			 * @param color
			 *            Couleur qui doit correspondre
			 * @return Position d'un objet de type clazz dans la map
			 */
			public <T> Position getOneKey(Map<Position, Object> map, Class<T> clazz, String color) {
				for (Map.Entry<Position, Object> o : map.entrySet()) {
					if (o != null && o.getValue().getClass() == clazz && o.getValue().toString() == color) {
						return o.getKey();
					}
				}
				return null;
			}

			/**
			 * Retourne le premier objet correspondant
			 * 
			 * @param map
			 *            Map avec les objets à vérifier
			 * @param clazz
			 *            Classe que l'on cherche dans la map
			 * @return Position d'un objet de type clazz dans la map
			 */
			public <T> T getOneValue(Map<Position, Object> map, Class<T> clazz) {
				for (Map.Entry<Position, Object> o : map.entrySet()) {
					if (o != null && o.getValue().getClass() == clazz) {
						return clazz.cast(o.getValue());
					}
				}
				return null;
			}

			/**
			 * Retourne le premier objet correspondant
			 * 
			 * @param map
			 *            Map avec les objets à vérifier
			 * @param clazz
			 *            Classe que l'on cherche dans la map
			 * @param color
			 *            Couleur qui doit correspondre
			 * @return Position d'un objet de type clazz dans la map
			 */
			public <T> T getOneValue(Map<Position, Object> map, Class<T> clazz, String color) {
				for (Map.Entry<Position, Object> o : map.entrySet()) {
					if (o != null && o.getValue().getClass() == clazz && o.getValue().toString() == color) {
						return clazz.cast(o.getValue());
					}
				}
				return null;
			}

			/**
			 * Vérifie si une position target est juste à côté d'une position source
			 * 
			 * @param source
			 *            Position source
			 * @param target
			 *            Position cible à vérifier
			 * @return Si une position target est juste à côté d'une position source
			 */
			public boolean isNext(Position source, Position target) {
				return (((source.getCoordX() == target.getCoordX()) && (source.getCoordY() == target.getCoordY() + 1))
						|| ((source.getCoordX() == target.getCoordX() + 1) && (source.getCoordY() == target.getCoordY()))
						|| ((source.getCoordX() == target.getCoordX()) && (source.getCoordY() == target.getCoordY() - 1)) || ((source.getCoordX() == target
						.getCoordX() - 1) && (source.getCoordY() == target.getCoordY())));
			}

			/**
			 * Retourne la position près de la currentPosition qui est en direction de la targetPosition éloignée
			 * 
			 * @param currentPosition
			 *            Position actuelle
			 * @param targetPosition
			 *            Position cible
			 * @return Position près de la position actuelle en direction de la position cible
			 */
			public Position getDirection(Position currentPosition, Position targetPosition) {
				int xDirection;
				int yDirection;

				if (currentPosition.getCoordX() < targetPosition.getCoordX()) {
					xDirection = 1;
				} else if (currentPosition.getCoordX() > targetPosition.getCoordX()) {
					xDirection = -1;
				} else {
					xDirection = 0;
				}

				if (currentPosition.getCoordY() < targetPosition.getCoordY()) {
					yDirection = 1;
				} else if (currentPosition.getCoordY() > targetPosition.getCoordY()) {
					yDirection = -1;
				} else {
					yDirection = 0;
				}

				return new Position(currentPosition.getCoordX() + xDirection, currentPosition.getCoordY() + yDirection);
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

								// si on a une boite
								if (robotState.getColorBox() != null) {
									String color = robotState.getColorBox().toString();
									// si le nid est à côté
									if (isNext(robotState.getCurrentPosition(), nests.get(Colors.valueOf(color)))) {
										// POSER dans le nid
										actionDecided = ActionDecided.DROP;
										targetPosition = nests.get(Colors.valueOf(color));
									} else {
										// sinon
										// MOVE aller vers le nid
										actionDecided = ActionDecided.MOVE;
										targetPosition = nests.get(Colors.valueOf(color));
									}
								} else {
									// sinon
									// si boite de même couleur juste à côté
									if (getOneValue(lookAroundOne, ColorBox.class, robotState.getRobotColor().toString()) != null) {
										// TAKE prendre la boite
										actionDecided = ActionDecided.TAKE;
										targetPosition = getOneKey(lookAroundOne, ColorBox.class, robotState.getRobotColor().toString());
									} else {
										// sinon
										// s'il y a une boite de la même couleur dans la vue
										if (getOneValue(lookAround, ColorBox.class, robotState.getRobotColor().toString()) != null) {
											// MOVE se diriger vers celle ci
											actionDecided = ActionDecided.MOVE;
											targetPosition = getOneKey(lookAround, ColorBox.class, robotState.getRobotColor().toString());
										} else // sinon s'il y a une boite juste à côté
										if (getOneValue(lookAroundOne, ColorBox.class) != null) {
											// TAKE la prendre
											actionDecided = ActionDecided.TAKE;
											targetPosition = getOneKey(lookAroundOne, ColorBox.class);
										} else
										// sinon s'il y a une boite dans la vue
										if (getOneValue(lookAround, ColorBox.class) != null) {
											// MOVE se diriger vers celle ci
											actionDecided = ActionDecided.MOVE;
											targetPosition = getOneKey(lookAround, ColorBox.class);
										} else {
											// sinon
											// MOVE aléatoirement
											actionDecided = ActionDecided.MOVE;
											Position random = eco_parts().randomGenerator().generationService()
													.generatePosition(0, 50, 0, 50, false, false);
											targetPosition = random;
										}

										addLogLine("Decision : " + actionDecided);
									}
								}

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

								switch (actionDecided) {
								case TAKE:
									try {
										robotState.setColorBox(requires().envInteraction().takeColorBox(targetPosition));
										robotState.decreaseEnergy();
										addLogLine("TAKE : Prendre la boite");
									} catch (EmptyGridBoxException | NotABoxException | ServiceUnavailableException e) {
										e.printStackTrace();
									}
									break;
								case DROP:
									float energyReceived = 0;
									try {
										energyReceived = requires().envInteraction().dropColorBox(robotState);
										robotState.increaseEnergy(energyReceived);
										robotState.setColorBox(null);
										robotState.decreaseEnergy();
										addLogLine("DROP : Poser la boite");
									} catch (ServiceUnavailableException e) {
										e.printStackTrace();
									}

									break;
								case MOVE:
									try {
										targetPosition = getDirection(robotState.getCurrentPosition(), targetPosition);
										requires().envInteraction().move(robotState.getCurrentPosition(), targetPosition);
										robotState.updatePosition(targetPosition);
										robotState.decreaseEnergy();
										addLogLine("MOVE : Se déplacer vers " + targetPosition);
									} catch (NonEmptyGridBoxException | sma.common.pojo.exceptions.InvalidPositionException
											| ServiceUnavailableException e) {
										e.printStackTrace();
									}
									break;
								}

								addLogLine("Action, robot state : " + robotState.getCurrentEnergyLevel());
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
						while (robotState.getCurrentEnergyLevel() != 0) {
							parts().decision().decide().execute();
							try {
								Thread.sleep(1000);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
					}
				};
			}

		};
	}

	@Override
	protected ReusableJoiningComp make_rjc() {
		return new ReusableJoiningComp() {

			@Override
			protected JoiningEntity make_JoiningEntity() {
				return new JoiningEntity() {

					@Override
					protected IPerception make_joinEnvPerception() {
						return eco_requires().universalEnvPerception();
					}

					@Override
					protected IInteraction make_joinEnvInteraction() {
						return eco_requires().universalEnvInteraction();
					}
				};
			}
		};
	}

	@Override
	protected Generator make_randomGenerator() {
		return new RandomGeneratorImpl();
	}

}
