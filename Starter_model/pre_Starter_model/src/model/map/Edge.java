package model.map;

public class Edge {

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
		return getFrom() == otherEdge.getFrom() && getTo() == otherEdge.getTo() && getID() == otherEdge.getID();
	}
	
	public String toString()
    {
        return "[" + getFrom() + ", " + getTo() + "] : " + getID();
    }
	
}
