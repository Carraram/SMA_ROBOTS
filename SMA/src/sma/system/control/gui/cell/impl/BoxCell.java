package sma.system.control.gui.cell.impl;

import javax.swing.ImageIcon;

import sma.common.pojo.Colors;
import sma.system.control.gui.cell.Cell;
import sma.system.environment.pojo.ColorBox;

/**
 * Case d'une boite
 */
public class BoxCell extends Cell{
	public BoxCell(ColorBox color) {
		if(color == ColorBox.BLUE){
			super.setIcon(new ImageIcon("resources/images/box_blue.png"));
		}else if(color == ColorBox.RED){
			super.setIcon(new ImageIcon("resources/images/box_red.png"));
		}else if(color == ColorBox.GREEN){
			super.setIcon(new ImageIcon("resources/images/box_green.png"));
		}
		
	}

}
