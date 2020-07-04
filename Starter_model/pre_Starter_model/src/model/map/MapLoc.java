package model.map;

import java.util.List;

public interface MapLoc {

	boolean isOpenEnd(Loc loc);

	boolean isExit(Loc loc);

	boolean isEntry(Loc loc);

	boolean isIntersect(Loc loc);

	boolean isEdge(Loc loc1, Loc loc2);

	double getLengthEdge(Loc loc1, Loc loc2);

	List<Loc> getAllTo(Loc loc);

	List<Loc> getAllFrom(Loc loc);

	List<Loc[]> getAllRoute(Loc loc);

	List<Light> getAllLight(Loc loc);

	Light getLight(Loc locFrom, Loc locCur, Loc locTo);

	void changeCorssStatus(Loc locCur);

}
