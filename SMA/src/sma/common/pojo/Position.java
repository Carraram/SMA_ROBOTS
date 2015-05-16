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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + coordX;
        result = prime * result + coordY;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Position other = (Position) obj;
        if (coordX != other.coordX || coordY != other.coordY)
            return false;
        return true;
    }
	
}
