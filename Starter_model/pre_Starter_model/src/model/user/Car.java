package model.user;

import java.util.ArrayList;
import java.util.List;

import model.map.Loc;
import model.map.MapLoc;
import model.map.Edge;
import model.map.Light.LIGHT;

public class Car implements User {

	private MapLoc map;
	private double speed;
	private List<Edge> walk;
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
		return speed;
	}

	@Override
	public Edge getFromEdge() {
		return walk.get(0);
	}

	@Override
	public Edge getToEdge() {
		return walk.get(1);
	}

	@Override
	public double getUntilLoc() {
		return untilLoc;
	}

	@Override
	public void proceed() {
		double progress = speed / 60;
		double nextUntilLoc = untilLoc - progress;
		if (nextUntilLoc <= 0) {
			if (map.getLight(walk.get(0), walk.get(1)).getStatus() == LIGHT.GREEN) {
				walk = walk.subList(1, walk.size());
				untilLoc = walk.get(0).getLength() + nextUntilLoc;
			} else {
				untilLoc = 0;
			}
		} else {
			untilLoc = nextUntilLoc;
		}
	}

	@Override
	public void putSelfInMap(MapLoc toMap, List<Edge> toWalk) {
		map = toMap;
		walk = toWalk;
		untilLoc = walk.get(0).getLength();
	}

	@Override
	public void removeSelfFromMap() {
		map = null;
		walk = null;
	}

}
