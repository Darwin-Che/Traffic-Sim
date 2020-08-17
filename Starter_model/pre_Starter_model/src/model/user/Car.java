package model.user;

import java.util.ArrayList;
import java.util.List;

import model.map.Loc;
import model.map.MapLoc;
import model.map.Light.LIGHT;

public class Car implements User {

	private MapLoc map;
	private double speed;
	private List<Loc> walk;
	private double untilLoc;

//	public Car() {
//		map = null;
//		speed = 30;
//		walk = null;
//		untilLoc = -1;
//	}

	public Car(double speed_t) {
		map = null;
		speed = speed_t;
		walk = null;
		untilLoc = 0;
	}

	@Override
	public double getSpeed() {
		// TODO Auto-generated method stub
		return speed;
	}

	@Override
	public Loc getFromLoc() {
		// TODO Auto-generated method stub
		return walk.get(0);
	}

	@Override
	public Loc getToloc() {
		// TODO Auto-generated method stub
		return walk.get(1);
	}

	@Override
	public double getUntilLoc() {
		// TODO Auto-generated method stub
		return untilLoc;
	}

	@Override
	public void proceed() {
		// TODO Auto-generated method stub
		double progress = speed / 60;
		double nextUntilLoc = untilLoc - progress;
		if (nextUntilLoc <= 0) {
			if (map.getLight(walk.get(0), walk.get(1), walk.get(2)).getStatus() == LIGHT.GREEN) {
				walk = walk.subList(1, walk.size());
				untilLoc = map.getLengthEdge(walk.get(0), walk.get(1)) + nextUntilLoc;
			} else {
				untilLoc = 0;
			}
		} else {
			untilLoc = nextUntilLoc;
		}
	}

	@Override
	public void putSelfInMap(MapLoc toMap, List<Loc> toWalk) {
		// TODO Auto-generated method stub
		map = toMap;
		walk = toWalk;
		untilLoc = map.getLengthEdge(walk.get(0), walk.get(1));
	}

	@Override
	public void removeSelfFromMap() {
		// TODO Auto-generated method stub
		map = null;
		walk = null;
	}

}
