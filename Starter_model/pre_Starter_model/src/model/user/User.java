package model.user;

import java.util.List;

import model.map.Edge;
import model.map.Loc;
import model.map.MapLoc;
import model.traffic.Traffic;

public interface User {
	
	public double getSpeed();

	public Edge getFromEdge();

	public Edge getToEdge();

	public double getUntilLoc();

	public void proceed();
	
	public void putSelfInMap(Traffic traffic, List<Edge> toWalk);
	
	public void removeSelfFromMap();
	
	public List<Edge> getWalk();
	
	public boolean isInTraffic();
}
