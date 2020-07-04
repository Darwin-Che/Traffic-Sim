package model.map;

public class Loc {
	private int xLoc;
	private int yLoc;

	public Loc(int x, int y) {
		xLoc = x;
		yLoc = y;
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
		return getX() == otherLoc.getX() && getX() == otherLoc.getX();
	}

	public String toString()
    {
        return "(" + getX() + ", " + getY() + ")";
    }
}
