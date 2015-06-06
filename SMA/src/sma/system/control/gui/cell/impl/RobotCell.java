package sma.system.control.gui.cell.impl;

import javax.swing.ImageIcon;

import sma.common.pojo.Colors;
import sma.system.control.gui.cell.Cell;
import sma.system.environment.pojo.ColorBox;

/**
 * Case d'un robot
 */
public class RobotCell extends Cell{
	private final static ImageIcon image = new ImageIcon("res/robot.png");
	public RobotCell(Colors color) {
		if(color == Colors.BLUE){
			super.setIcon(new ImageIcon("resources/images/robot_blue.png"));
		}else if(color == Colors.RED){
			super.setIcon(new ImageIcon("resources/images/robot_red.png"));
		}else if(color == Colors.GREEN){
			super.setIcon(new ImageIcon("resources/images/robot_green.png"));
		}
	}

}
