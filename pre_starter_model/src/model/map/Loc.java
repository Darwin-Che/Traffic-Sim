package model.map;

public class Loc implements Comparable<Loc> {
	private int xLoc;
	private int yLoc;

	public Loc(int x, int y) {
		xLoc = x;
		yLoc = y;
	}

	@Override
	public int hashCode() {
		return (xLoc << 9) | yLoc;
	}
	
	public int getX() {
		return xLoc;
	}

	public int getY() {
		return yLoc;
	}

	public boolean equals(Object other) {
		if (!(other instanceof Loc))
			return false;

		Loc otherLoc = (Loc) other;
		return getX() == otherLoc.getX() && getY() == otherLoc.getY();
	}

	public String toString() {
		return "(" + getX() + ", " + getY() + ")";
	}

	@Override
	public int compareTo(Loc other) {
		if (getX() < other.getX()) return -2;
		if (getX() > other.getX()) return 2;
		if (getX() == other.getX()) {
			if (getY() < other.getY()) return -1;
			if (getY() > other.getY()) return 1;
			if (getY() == other.getY()) return 0;
		}
		return 0;
	}
}
