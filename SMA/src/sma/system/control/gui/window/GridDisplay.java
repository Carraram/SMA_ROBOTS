package sma.system.control.gui.window;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import sma.system.control.gui.cell.Cell;
import sma.system.control.gui.cell.impl.RobotBoxCell;
import sma.system.control.gui.cell.impl.RobotCell;
import sma.system.environment.pojo.ColorBox;

public class GridDisplay extends JPanel {
	
	public GridDisplay(int x, int y) {
		
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(y, x));
//		panel.setPreferredSize(new Dimension(700,490));
		for(int i = 0; i< x*y ; i++){
			panel.add(new RobotBoxCell(ColorBox.RED));
		}
		JScrollPane scrollPane = new JScrollPane();
		
		scrollPane.add(panel);
		scrollPane.setPreferredSize(new Dimension(1250,650));
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setViewportView(panel);
		add(scrollPane);
	}
	
	public void setCell(Cell cell){
		
	}
}
