package model.user;

import java.util.List;

import model.map.Edge;
import model.map.Loc;
import model.map.MapLoc;

public interface User {
	double getSpeed();

	Edge getFromEdge();

	Edge getToEdge();

	double getUntilLoc();

	void proceed();
	
	void putSelfInMap(MapLoc toMap, List<Edge> toWalk);
	
	void removeSelfFromMap();
}
