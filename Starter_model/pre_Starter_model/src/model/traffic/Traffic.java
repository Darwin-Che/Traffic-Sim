package model.traffic;

import java.util.ArrayList;
import java.util.List;

import model.map.Edge;
import model.map.GridMapLoc;
import model.map.Loc;
import model.map.MapLoc;
import model.user.Car;
import model.user.User;

public class Traffic {

	public MapLoc map;
	public List<User> users;

	private int changeLight;

	public Traffic(MapLoc tm) {
		map = tm;
		users = new ArrayList<User>();
		changeLight = 0;
	}

	void step() {
		if (changeLight % 30 == 0) {
			changeMapLight();
			changeLight = changeLight % 30 + 1;
		}
		for (User u : users) {
			u.proceed();
		}
	}

	void addUser(Loc loc) {
		if (!(map.isEntry(loc)))
			return;
		List<Edge> walk = map.generateWalk(loc);
		User u = new Car(30);
		u.putSelfInMap(map, walk);
	}

	List<User> getAllUsers() {
		return users;
	}

	List<User> getAllUsers(Loc locFrom, Loc locTo) {
		List<User> ret = new ArrayList<User>();
		for (User u : users) {
			if (u.getFromEdge().getFrom() == locFrom && u.getFromEdge().getTo() == locTo)
				ret.add(u);
		}
		return ret;
	}

	List<User> getAllUsers(Edge edge) {
		List<User> ret = new ArrayList<User>();
		for (User u : users) {
			if (u.getFromEdge() == edge)
				ret.add(u);
		}
		return ret;
	}

	List<User> getAllUsersTo(Loc locTo) {
		List<User> ret = new ArrayList<User>();
		for (User u : users) {
			if (u.getFromEdge().getTo() == locTo)
				ret.add(u);
		}
		return ret;
	}

	List<User> getAllUsersFrom(Loc locFrom) {
		List<User> ret = new ArrayList<User>();
		for (User u : users) {
			if (u.getFromEdge().getFrom() == locFrom)
				ret.add(u);
		}
		return ret;
	}

	void changeMapLight() {

	}

}
