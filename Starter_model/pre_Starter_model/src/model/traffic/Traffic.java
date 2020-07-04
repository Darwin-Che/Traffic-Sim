package model.traffic;

import java.util.ArrayList;
import java.util.List;

import model.map.Loc;
import model.map.MapLoc;
import model.user.User;

public class Traffic {
	
	private MapLoc map;
	private List<User> users; 
	
	void step(double interval) {

	}

	List<User> getAllUsers(){
		return users;
	}

	List<User> getAllUsers(Loc locFrom, Loc locTo){
		List<User> ret = new ArrayList<User>();
		for (User u : users) {
			if (u.getFromLoc() == locFrom && u.getToloc() == locTo) 
				ret.add(u);
		}
		return ret;
	}

	List<User> getAllUsersTo(Loc locTo){
		List<User> ret = new ArrayList<User>();
		for (User u : users) {
			if (u.getToloc() == locTo) 
				ret.add(u);
		}
		return ret;
	}

	List<User> getAllUsersFrom(Loc locFrom){
		List<User> ret = new ArrayList<User>();
		for (User u : users) {
			if (u.getFromLoc() == locFrom) 
				ret.add(u);
		}
		return ret;
	}
}
