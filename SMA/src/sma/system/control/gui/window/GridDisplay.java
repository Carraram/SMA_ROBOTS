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
import sma.system.agents.pojo.interfaces.IAgentReadOnly;
import sma.system.control.gui.cell.Cell;
import sma.system.control.gui.cell.impl.BoxCell;
import sma.system.control.gui.cell.impl.LandCell;
import sma.system.control.gui.cell.impl.NestCell;
import sma.system.control.gui.cell.impl.RobotBoxCell;
import sma.system.control.gui.cell.impl.RobotCell;
import sma.system.environment.pojo.ColorBox;

/**
 * Panel d'affichage du système
 */
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
		initial();
		JScrollPane scrollPane = new JScrollPane();

		scrollPane.add(panel);
		scrollPane.setPreferredSize(new Dimension(1250, 650));
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setViewportView(panel);
		add(scrollPane);
	}

	/**
	 * Afficher une cell
	 * 
	 * @param cell
	 *            cell à afficher
	 * @param x
	 *            position x
	 * @param y
	 *            position y
	 */
	public void setCell(Cell cell, int x, int y) {
		int index = getCellIndex(x, y);
		panel.remove(index);
		panel.add(cell, index);
	}

	/**
	 * Retourne l'index normalisé de la cell
	 * 
	 * @param x
	 *            position x
	 * @param y
	 *            position y
	 * @return index normalisé
	 */
	private int getCellIndex(int x, int y) {
		return ((y - 1) * width + x) - 1;
	}

	/**
	 * Affiche les nids dans l'interface
	 * 
	 * @param nests
	 *            map de couleur et position des nids à afficher
	 */
	public void setNests(Map<Colors, Position> nests) {
		for (Entry<Colors, Position> entry : nests.entrySet()) {
			Colors color = entry.getKey();
			Position pos = entry.getValue();
			setCell(new NestCell(color), pos.getCoordX(), pos.getCoordY());
		}
	}

	/**
	 * Affiche les robots dans l'interface
	 * 
	 * @param robots
	 *            liste des états des robots à afficher
	 */
	public void setRobots(List<IAgentReadOnly> robots) {
		for (IAgentReadOnly robot : robots) {
			if (robot.getColorBox() != null)
				setCell(new RobotBoxCell(robot.getColorBox()), robot.getCurrentPosition().getCoordX(), robot.getCurrentPosition().getCoordY());
			else
				setCell(new RobotCell(robot.getRobotColor()), robot.getCurrentPosition().getCoordX(), robot.getCurrentPosition().getCoordY());
		}
	}

	/**
	 * Affiche les boites dans l'interface
	 * 
	 * @param boxs
	 *            Map couleur et position des boites à afficher
	 */
	public void setBoxs(Map<ColorBox, List<Position>> boxs) {
		for (Entry<ColorBox, List<Position>> entry : boxs.entrySet()) {
			ColorBox color = entry.getKey();
			List<Position> positions = entry.getValue();
			for (Position p : positions) {
				setCell(new BoxCell(color), p.getCoordX(), p.getCoordY());
			}
		}
	}

	/**
	 * Réinitialise l'affichage
	 */
	public void reinitial() {
		for (int i = 0; i < width * height; i++) {
			panel.remove(i);
			panel.add(new LandCell(), i);
		}
		panel.revalidate();
	}

	/**
	 * Initialise l'affichage
	 */
	public void initial() {
		for (int i = 0; i < width * height; i++) {
			panel.add(new LandCell());
		}
	}
}
