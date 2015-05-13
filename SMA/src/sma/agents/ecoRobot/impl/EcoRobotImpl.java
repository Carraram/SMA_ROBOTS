package sma.agents.ecoRobot.impl;

import java.util.concurrent.atomic.AtomicInteger;

import sma.agents.ecoRobot.interfaces.IRobotOperations;
import sma.agents.ecoRobot.interfaces.IRobotStatus;
import sma.agents.pojo.EtatRobot;
import sma.common.pojo.Couleur;
import sma.common.pojo.Position;
import agent.ecoRobot.EcoRobot;

public class EcoRobotImpl extends EcoRobot {
	
	@Override
	protected Robot make_Robot(final float maxEnergie, final Couleur couleur, final Position positionInitiale) {
		return new Robot() {
			
			private final EtatRobot etatRobot = new EtatRobot(maxEnergie, couleur, positionInitiale);
			
			private final AtomicInteger balance = new AtomicInteger();
			
			@Override
			protected void start() {
				eco_requires().elog().addLine("Added a new Robot with :" + maxEnergie + ","+ couleur + ","+ positionInitiale + ",");
			}
			
			@Override
			protected IRobotOperations make_operations() {
				return new IRobotOperations() {
					
					@Override
					public void withdraw(int value) {
						provides().operations().deposit(-value);
						requires().log().addLine(value+" were withdrawn, current balance: "+provides().status().getBalance());
					}
					
					@Override
					public void deposit(int value) {
						balance.addAndGet(value);
						requires().log().addLine(value+" were deposited, current balance: "+provides().status().getBalance());
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
					public EtatRobot getEtatRobot() {
						return etatRobot;
					}
				};
			}
		};
	}
}
