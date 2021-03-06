package sma.system.control.gui.cell.impl;

import javax.swing.ImageIcon;

import sma.common.pojo.Colors;
import sma.system.control.gui.cell.Cell;
import sma.system.environment.pojo.ColorBox;

/**
 * Case d'un robot avec une boite
 */
public class RobotBoxCell extends Cell{
	public RobotBoxCell(ColorBox color) {
		if(color == ColorBox.BLUE){
			super.setIcon(new ImageIcon("resources/images/robot_box_blue.png"));
		}else if(color == ColorBox.RED){
			super.setIcon(new ImageIcon("resources/images/robot_box_red.png"));
		}else if(color == ColorBox.GREEN){
			super.setIcon(new ImageIcon("resources/images/robot_box_green.png"));
		}
	}

}
