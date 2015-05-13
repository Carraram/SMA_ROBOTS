package sma.common.pojo;

/**
 * Position definie par des coordonnees
 */
public class Position {
	private int coordX;
	private int coordY;
	
	/**
	 * Cree une position
	 * @param coordX Coordonnee X
	 * @param coordY Coordonnee Y
	 */
	public Position(int coordX, int coordY) {
		super();
		this.coordX = coordX;
		this.coordY = coordY;
	}

	public int getCoordX() {
		return coordX;
	}

	public int getCoordY() {
		return coordY;
	}
	
	@Override
	public String toString() {
	    return "(" + coordX + "," + coordY + ")";
	}
}
