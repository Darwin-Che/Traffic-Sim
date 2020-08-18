package model.traffic;

import java.util.ArrayList;
import java.util.List;

import model.map.GridMapLoc;
import model.map.Loc;
import model.map.MapLoc;
import model.user.Car;
import model.user.User;

public class Traffic {

	public MapLoc map;
	private List<User> users;

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
		List<Loc> walk = map.generateWalk(loc);
		User u = new Car(30);
		u.putSelfInMap(map, walk);
	}

	List<User> getAllUsers() {
		return users;
	}

	List<User> getAllUsers(Loc locFrom, Loc locTo) {
		List<User> ret = new ArrayList<User>();
		for (User u : users) {
			if (u.getFromLoc() == locFrom && u.getToloc() == locTo)
				ret.add(u);
		}
		return ret;
	}

	List<User> getAllUsersTo(Loc locTo) {
		List<User> ret = new ArrayList<User>();
		for (User u : users) {
			if (u.getToloc() == locTo)
				ret.add(u);
		}
		return ret;
	}

	List<User> getAllUsersFrom(Loc locFrom) {
		List<User> ret = new ArrayList<User>();
		for (User u : users) {
			if (u.getFromLoc() == locFrom)
				ret.add(u);
		}
		return ret;
	}

	void changeMapLight() {
		List<Loc> all = map.getAllLoc();
		for (int i = 0; i < 20; i++) {
			int r = (int) (Math.random() * all.size());
			map.changeCrossStatus(all.get(r));
		}
	}

}
