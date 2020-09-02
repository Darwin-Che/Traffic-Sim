package model.map;

public class Edge implements Comparable<Edge> {

	private String ID;

	private Loc from, to;

	private double length;

	public Edge(String ID, Loc from, Loc to, double length) {
		this.ID = ID;
		this.from = from;
		this.to = to;
		this.length = length;
	}

	public String getID() {
		return ID;
	}

	public Loc getFrom() {
		return from;
	}

	public Loc getTo() {
		return to;
	}

	public double getLength() {
		return length;
	}

	public boolean equals(Object other) {
		if (!(other instanceof Edge))
			return false;

		Edge otherEdge = (Edge) other;
		return getFrom().equals(otherEdge.getFrom()) && getTo().equals(otherEdge.getTo())
				&& getID().equals(otherEdge.getID());
	}

	public String toString() {
		return "[" + getFrom() + ", " + getTo() + "]" + getID();
	}

	@Override
	public int compareTo(Edge o) {
		int c1 = getFrom().compareTo(o.getFrom());
		int c2 = getTo().compareTo(o.getTo());
		if (c1 < 0)
			return -2;
		if (c1 > 0)
			return 2;
		// now c1 == 0
		if (c2 < 0)
			return -1;
		if (c2 > 0)
			return 1;
		// now c2 == 0
		return 0;
	}

}
