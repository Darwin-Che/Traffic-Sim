package model.map;

public class Route implements Comparable<Route> {

	private Edge inEdge, outEdge;

	public Route(Edge e1, Edge e2) throws Exception {
		if (e1.getTo() != e2.getFrom()) {
			throw new Exception("This is not a route!");
		}

		this.inEdge = e1;
		this.outEdge = e2;
	}

	public Edge getInEdge() {
		return inEdge;
	}

	public Edge getOutEdge() {
		return outEdge;
	}

	public Loc getFromLoc() {
		return inEdge.getFrom();
	}

	public Loc getCurLoc() {
		return inEdge.getTo();
	}

	public Loc getToLoc() {
		return outEdge.getTo();
	}

	public boolean equals(Object other) {
		if (!(other instanceof Route))
			return false;

		Route otherRoute = (Route) other;
		return getInEdge().equals(otherRoute.getInEdge()) && getOutEdge().equals(otherRoute.getOutEdge());
	}

	public String toString() {
		return "{" + getInEdge() + ", " + getOutEdge() + "}";
	}

	@Override
	public int compareTo(Route o) {
		int c1 = getCurLoc().compareTo(o.getCurLoc());
		int c2 = getFromLoc().compareTo(o.getFromLoc());
		int c3 = getToLoc().compareTo(o.getToLoc());
		if (c1 < 0)
			return -3;
		if (c1 > 0)
			return 3;
		// now c1 == 0
		if (c2 < 0)
			return -2;
		if (c2 > 0)
			return 2;
		// now c2 == 0
		if (c3 < 0)
			return -1;
		if (c3 > 0)
			return 1;
		return 0;
	}

}
