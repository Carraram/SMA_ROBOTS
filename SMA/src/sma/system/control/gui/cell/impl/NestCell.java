package sma.system.control.gui.cell.impl;

import javax.swing.ImageIcon;

import sma.common.pojo.Colors;
import sma.system.control.gui.cell.Cell;
import sma.system.environment.pojo.ColorBox;

/**
 * Case d'un nid
 */
public class NestCell extends Cell{
	public NestCell(Colors color) {
		if(color == Colors.BLUE){
			super.setIcon(new ImageIcon("resources/images/nest_blue.png"));
		}else if(color == Colors.RED){
			super.setIcon(new ImageIcon("resources/images/nest_red.png"));
		}else if(color == Colors.GREEN){
			super.setIcon(new ImageIcon("resources/images/nest_green.png"));
		}
	}

}
