package model.map;

public class Route {

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
		return getInEdge() == otherRoute.getInEdge() && getOutEdge() == otherRoute.getOutEdge();
	}

	public String toString() {
		return "{" + getInEdge() + ", " + getOutEdge() + "}";
	}

}
