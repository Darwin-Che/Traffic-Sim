package model.map;

import java.util.List;
import java.util.Map;

public interface MapLoc {

	/******** functions on Loc ************/
	
	public boolean isOpenEnd(Loc loc);

	public boolean isExit(Loc loc);

	public boolean isEntry(Loc loc);

	public boolean isIntersect(Loc loc);

	public List<Loc> getAllLoc();

	/******** functions on Edge ************/
	
	public boolean existEdge(Loc loc1, Loc loc2);
	
	public boolean existEdge(Loc loc1, Loc loc2, String id);

	public List<Edge> getAllEdge();
	
	public List<Edge> getAllEdge(Loc loc1, Loc loc2);

	public List<Edge> getAllEdgeTo(Loc loc);
	
	public List<Edge> getAllEdgeFrom(Loc loc);
	
	/******** functions on Route - Light ************/
	
	public List<Route> getAllRoute(Loc loc);
	
	public List<Route> getRouteOut(Edge inEdge);
	
	public Map<Route, Light> getAllLight(Loc loc);

	public Light getLight(Route route);
	
	public Light getLight(Edge inEdge, Edge outEdge);

	public Light getLight(Loc from, Loc cur, Loc to);

	/******** functions on Intersection ************/
	// Do we really need them?
//	public Intersection getCross(Loc loc);
//
//	public List<Intersection> getAllCross();

	public void changeCrossStatus(Loc locCur);
	
//	public void changeCrossStatus(Intersection is);

	/******** Others ************/
	
	public List<Edge> generateWalk(Loc start);

}
