package model.user;

import java.util.List;

import model.map.Loc;
import model.map.MapLoc;

public interface User {
	double getSpeed();

	Loc getFromLoc();

	Loc getToloc();

	double getUntilLoc();

	void proceed(double interval);
	
	void putSelfInMap(MapLoc toMap, List<Loc> toWalk);
	
	void removeSelfFromMap();
}
