package model.user;

import java.util.List;

import model.map.Loc;
import model.map.MapLoc;

public class Car implements User {

	private MapLoc map;
	private double speed;
	private List<Loc> walk;
	private double untilLoc;
	
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
	public void proceed(double interval) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void putSelfInMap(MapLoc toMap, List<Loc> toWalk) {
		// TODO Auto-generated method stub
		map = toMap;
		walk = toWalk;
	}

	@Override
	public void removeSelfFromMap() {
		// TODO Auto-generated method stub
		map = null;
		walk = null;
	}

}
