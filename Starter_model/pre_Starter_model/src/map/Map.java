package map;

import java.util.List;

import map.Light.LIGHT;


public interface Map {

	boolean isOpenEnd(Loc loc);
	
	boolean isExit(Loc loc);
	
	boolean isEntry(Loc loc);
	
	boolean isInterSect(Loc loc);
	
	boolean isEdge(Loc loc1, Loc loc2);
	
	double getLengthEdge(Loc loc1, Loc loc2);
	
	List<Loc> getAllDest(Loc loc);
	
	List<Loc> getAllSource(Loc loc);
	
	List<Loc[]> getAllRoute(Loc loc);
	
	List<Light> getAllLight(Loc loc);
	
	Light getLight(Loc locFrom, Loc locCur, Loc locTo);
	
	Light setLightStatus(Loc locFrom, Loc locCur, Loc locTo, LIGHT lightStatus);

}
