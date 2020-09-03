package model.traffic;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import model.gui.ViewInterface;
import model.map.Edge;
import model.map.GridMapLoc;
import model.map.Loc;
import model.map.MapLoc;
import model.user.Car;
import model.user.User;

public class Traffic {

	public MapLoc map;
	public List<User> users;
	public ViewInterface view;

	public int crowded;
	public int allUserNum;

	public int changeLight;

	public boolean paused;
	public boolean step;

	public Traffic(MapLoc tm) {
		paused = true;
		allUserNum = 40;
		crowded = 30;
		map = tm;
		users = new ArrayList<User>();
		changeLight = 0;
	}


	public void step() {
		for (int i = 0; i < users.size();) {
			users.get(i).proceed();
			if (users.get(i).isInTraffic()) {
				users.remove(i);
				--i;
			}
			++i;
		}
		if (getAllUsers().size() < allUserNum) {
			while (getAllUsers().size() < allUserNum) {
				List<Loc> allEntry = map.getAllEntry();
				addUser(allEntry.get((int) (Math.random() * allEntry.size())));
			}
		} else {
			for (int k = 0; k < 5; ++k) {
				List<Loc> allEntry = map.getAllEntry();
				addUser(allEntry.get((int) (Math.random() * allEntry.size())));
			}
		}
		view.redraw(this);
		for (Edge e : map.getAllEdge()) {
			if (getAllUsers(e).size() > crowded) {
				System.out.println(e + " has " + getAllUsers(e).size() + " users.");
				break;
			}
		}
	}

	public void addUser(Loc loc) {
		if (!(map.isEntry(loc)))
			return;
		List<Edge> walk = map.generateWalk(loc);
		User u = new Car(20);
		u.putSelfInMap(this, walk);
		users.add(u);
		for (Edge e : u.getWalk()) {
			System.out.println(e);
		}
	}

	public List<User> getAllUsers() {
		return users;
	}

	public List<User> getAllUsers(Loc locFrom, Loc locTo) {
		List<User> ret = new ArrayList<User>();
		for (User u : users) {
			if (u.getFromEdge().getFrom().equals(locFrom) && u.getFromEdge().getTo().equals(locTo))
				ret.add(u);
		}
		return ret;
	}

	public List<User> getAllUsers(Edge edge) {
		List<User> ret = new ArrayList<User>();
		for (User u : users) {
			if (u.getFromEdge().equals(edge))
				ret.add(u);
		}
		return ret;
	}

	public List<User> getAllUsersTo(Loc locTo) {
		List<User> ret = new ArrayList<User>();
		for (User u : users) {
			if (u.getFromEdge().getTo().equals(locTo))
				ret.add(u);
		}
		return ret;
	}

	public List<User> getAllUsersFrom(Loc locFrom) {
		List<User> ret = new ArrayList<User>();
		for (User u : users) {
			if (u.getFromEdge().getFrom().equals(locFrom))
				ret.add(u);
		}
		return ret;
	}

	void changeMapLight() {

	}

	public void removeUser(User u) {
		users.remove(u);
	}

}
