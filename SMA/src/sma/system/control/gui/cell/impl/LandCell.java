package sma.system.control.gui.cell.impl;

import javax.swing.ImageIcon;

import sma.system.control.gui.cell.Cell;

public class LandCell extends Cell{
	private final static ImageIcon image = new ImageIcon("resources/images/land.png");
	public LandCell() {
		super.setIcon(image);
	}

}
