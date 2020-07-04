package user;

import map.Loc;

public interface User {
	double getSpeed();

	Loc getFromLoc();

	Loc getToloc();

	double getUntilLoc();

	void proceed(double interval);
	
	void putSelfInMap(Loc loc);
	
	void removeSelfFromMap();
}
