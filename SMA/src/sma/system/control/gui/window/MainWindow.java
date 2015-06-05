package sma.system.control.gui.window;

import java.awt.Container;
import java.awt.GridLayout;

import javax.swing.JFrame;

public class MainWindow extends JFrame {
    public MainWindow(int x, int y){
        setTitle("Robot SMA");
        this.setSize(1300,700);
        Container content = this.getContentPane();
        content.add(new GridDisplay(x , y));
        setVisible(true);
    }
}
