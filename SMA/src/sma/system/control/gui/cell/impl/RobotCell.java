package sma.system.control.gui.cell.impl;

import javax.swing.ImageIcon;

import sma.system.control.gui.cell.Cell;
import sma.system.environment.pojo.ColorBox;

public class RobotCell extends Cell{
	private final static ImageIcon image = new ImageIcon("res/robot.png");
	public RobotCell(ColorBox color) {
		if(color == ColorBox.BLUE){
			super.setIcon(new ImageIcon("resources/images/robot_blue.png"));
		}else if(color == ColorBox.RED){
			super.setIcon(new ImageIcon("resources/images/robot_red.png"));
		}else if(color == ColorBox.GREEN){
			super.setIcon(new ImageIcon("resources/images/robot_green.png"));
		}
	}

}
