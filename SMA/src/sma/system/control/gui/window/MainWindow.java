package sma.system.control.gui.window;

import java.awt.Container;
import java.awt.GridLayout;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;

import sma.common.pojo.Colors;
import sma.common.pojo.Position;
import sma.system.environment.pojo.ColorBox;

public class MainWindow extends JFrame {
	
	private GridDisplay gridDisplay;
	
    public MainWindow(int x, int y){
        setTitle("Robot SMA");
        this.setSize(1300,700);
        gridDisplay = new GridDisplay(x , y);
        Container content = this.getContentPane();
        content.add( gridDisplay );
        setVisible(true);
    }
    
    public void setNests(Map<Colors, Position> nests){
    	gridDisplay.setNests(nests);
    }
    
    public void setRobots(){
    	gridDisplay.setRobots();
    }
    
    public void setBoxs(Map<ColorBox, List<Position>> boxs){
    	gridDisplay.setBoxs(boxs);
    }
}
