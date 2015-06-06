package sma.system.control.gui.window;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import sma.common.pojo.Colors;
import sma.common.pojo.Position;
import sma.system.control.gui.cell.Cell;
import sma.system.control.gui.cell.impl.BoxCell;
import sma.system.control.gui.cell.impl.LandCell;
import sma.system.control.gui.cell.impl.NestCell;
import sma.system.control.gui.cell.impl.RobotBoxCell;
import sma.system.control.gui.cell.impl.RobotCell;
import sma.system.environment.pojo.ColorBox;

public class GridDisplay extends JPanel {
	private int width;
	private int height;
	private JPanel panel;
	private GridLayout gridLayout;
	
	public GridDisplay(int x, int y) {
		width = x;
		height = y;
		panel = new JPanel();
		gridLayout = new GridLayout(y, x);
		panel.setLayout(gridLayout);
		initial(x, y);
		JScrollPane scrollPane = new JScrollPane();
		
		scrollPane.add(panel);
		scrollPane.setPreferredSize(new Dimension(1250,650));
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setViewportView(panel);
		add(scrollPane);
	}
	
	public void setCell(Cell cell, int x, int y){
		int index = getCellIndex(x, y);
//		set
//		System.out.println("num index:"+panel.getComponentCount());
		panel.remove(index);
//		System.out.println("num index:"+panel.getComponentCount());
		panel.add(cell, index);
	}
	
	private int getCellIndex(int x, int y){
		return  ((y-1)*width + x ) - 1 ;
	}
	
	public void setNests(Map<Colors, Position> nests){
		for (Entry<Colors, Position> entry : nests.entrySet()) {
			Colors color = entry.getKey();
			Position pos = entry.getValue();
//			System.out.println("color="+color+", x="+pos.getCoordX()+", y="+pos.getCoordY());
			setCell(new NestCell(color), pos.getCoordX(), pos.getCoordY());
		}
    }
    
    public void setRobots(){
    	
    }
    
    public void setBoxs(Map<ColorBox, List<Position>> boxs){
    	for (Entry<ColorBox, List<Position>> entry : boxs.entrySet()) {
    		ColorBox color = entry.getKey();
			List<Position> positions = entry.getValue();
//			System.out.println("color="+color+", x="+pos.getCoordX()+", y="+pos.getCoordY());
			for(Position p : positions){
				setCell(new BoxCell(color), p.getCoordX(), p.getCoordY());
			}
			
		}
    }
    
    private void initial(int x , int y){
		for(int i = 0; i< x*y ; i++){
			panel.add(new LandCell());
		}
    }
}
