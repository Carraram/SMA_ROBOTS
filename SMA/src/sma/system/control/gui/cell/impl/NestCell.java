package sma.system.control.gui.cell.impl;

import javax.swing.ImageIcon;

import sma.system.control.gui.cell.Cell;
import sma.system.environment.pojo.ColorBox;

public class NestCell extends Cell{
	public NestCell(ColorBox color) {
		if(color == ColorBox.BLUE){
			super.setIcon(new ImageIcon("resources/images/nest_blue.png"));
		}else if(color == ColorBox.RED){
			super.setIcon(new ImageIcon("resources/images/nest_red.png"));
		}else if(color == ColorBox.GREEN){
			super.setIcon(new ImageIcon("resources/images/nest_green.png"));
		}
	}

}
