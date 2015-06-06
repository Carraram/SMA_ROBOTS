package sma.system.control.gui.cell.impl;

import javax.swing.ImageIcon;

import sma.common.pojo.Colors;
import sma.system.control.gui.cell.Cell;
import sma.system.environment.pojo.ColorBox;

public class RobotBoxCell extends Cell{
	public RobotBoxCell(Colors color) {
		if(color == Colors.BLUE){
			super.setIcon(new ImageIcon("resources/images/robot_box_blue.png"));
		}else if(color == Colors.RED){
			super.setIcon(new ImageIcon("resources/images/robot_box_red.png"));
		}else if(color == Colors.GREEN){
			super.setIcon(new ImageIcon("resources/images/robot_box_green.png"));
		}
	}

}
