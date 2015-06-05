package sma.system.control.gui.cell.impl;

import javax.swing.ImageIcon;

import sma.system.control.gui.cell.Cell;
import sma.system.environment.pojo.ColorBox;

public class BoxCell extends Cell{
	public BoxCell(ColorBox color) {
//		if(color == ColorBox.BLUE){
			super.setIcon(new ImageIcon("resources/images/box.png"));
//		}
		
	}

}
