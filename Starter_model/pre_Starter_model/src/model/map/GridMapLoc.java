package model.map;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.map.Light.LIGHT;

public class GridMapLoc implements MapLoc {

	private int xLimit; // 0 <= x <= xLimit
	private int yLimit; // 0 <= y <= yLimit

	private GridCross[][] allLight;

	public GridMapLoc(int xl, int yl) {
		xLimit = xl;
		yLimit = yl;
		allLight = new GridCross[xLimit - 1][yLimit - 1];
		for (int i = 0; i < xLimit - 1; ++i) {
			for (int j = 0; j < yLimit - 1; ++j) {
				allLight[i][j] = new GridCross();
			}
		}
	}

	/********************** functions on Loc ***********************/

	@Override
	public boolean isOpenEnd(Loc loc) {
		// TODO Auto-generated method stub
		return loc.getX() == 0 || loc.getX() == xLimit || loc.getY() == 0 || loc.getY() == yLimit;
	}

	@Override
	public boolean isEntry(Loc loc) {
		// TODO Auto-generated method stub
		return loc.getX() == 0 || loc.getY() == 0;
	}

	@Override
	public boolean isExit(Loc loc) {
		// TODO Auto-generated method stub
		return loc.getX() == xLimit || loc.getY() == yLimit;
	}

	@Override
	public boolean isIntersect(Loc loc) {
		// TODO Auto-generated method stub
		return !isOpenEnd(loc);
	}

	@Override
	public List<Loc> getAllLoc() {
		List<Loc> ret = new ArrayList<Loc>();
		for (int i = 0; i <= xLimit; i++) {
			for (int j = 0; j <= yLimit; j++) {
				if ((i == 0 || i == xLimit) && (j == 0 || j == yLimit))
					continue;
				ret.add(new Loc(i, j));
			}
		}
		return ret;
	}

	/************************ functions on Edge **************************/

	@Override
	public boolean existEdge(Loc loc1, Loc loc2) {
		boolean ret = false;
		int x1 = loc1.getX();
		int x2 = loc2.getX();
		int y1 = loc1.getY();
		int y2 = loc2.getY();
		// on the same column, make sure is not on border
		if (x1 == x2 && x1 != 0 && x1 != xLimit) {
			// if y2-y1 == 1
//			if (y1 - y2 == 1)
//				ret = true;
			if (y2 - y1 == 1)
				ret = true;
		}
		// on the same row, make sure is not on border
		if (y1 == y2 && y1 != 0 && y1 != yLimit) {
			// if x2-x1 == 1
//			if (x1 - x2 == 1)
//				ret = true;
			if (x2 - x1 == 1)
				ret = true;
		}
		return ret;
	}

	@Override
	public boolean existEdge(Loc loc1, Loc loc2, String id) {
		return existEdge(loc1, loc2);
	}

	public List<Edge> getAllEdge() {
		List<Edge> ret = new ArrayList<Edge>();
		// add edge from start point
		// upper border
		for (int i = 1; i < xLimit; ++i) {
			Loc from = new Loc(i, 0);
			Loc to = new Loc(i, 1);
			ret.add(new Edge("", from, to, 1));
		}
		// left border
		for (int j = 1; j < yLimit; ++j) {
			Loc from = new Loc(0, j);
			Loc to = new Loc(1, j);
			ret.add(new Edge("", from, to, 1));
		}
		// internal points
		for (int i = 1; i < xLimit; ++i) {
			for (int j = 1; j < yLimit; ++j) {
				Loc from = new Loc(i, j);
				// horizontal
				Loc toh = new Loc(i + 1, j);
				// vertical
				Loc tov = new Loc(i, j + 1);
				// add both
				ret.add(new Edge("", from, toh, 1));
				ret.add(new Edge("", from, tov, 1));
			}
		}
		return ret;
	}

	@Override
	public List<Edge> getAllEdge(Loc loc1, Loc loc2) {
		List<Edge> ret = new ArrayList<Edge>();
		if (existEdge(loc1, loc2)) {
			ret.add(new Edge("", loc1, loc2, 1));
		}
		return ret;
	}

	@Override
	public List<Edge> getAllEdgeTo(Loc loc) {
		List<Edge> ret = new ArrayList<Edge>();
		// make sure not on the exits
		if (loc.getX() < xLimit && loc.getY() < yLimit) {
			// if have vertical edge
			if (loc.getX() != 0) {
				Loc dest = new Loc(loc.getX(), loc.getY() + 1);
				ret.add(new Edge("", loc, dest, 1));
			}
			// if have horizontal edge
			if (loc.getY() != 0) {
				Loc dest = new Loc(loc.getX() + 1, loc.getY());
				ret.add(new Edge("", loc, dest, 1));
			}
		}
		return ret;
	}

	@Override
	public List<Edge> getAllEdgeFrom(Loc loc) {
		List<Edge> ret = new ArrayList<Edge>();
		// make sure not on the entries
		if (loc.getX() > 0 && loc.getY() > 0) {
			// if have vertical edge
			if (loc.getX() != xLimit) {
				Loc dest = new Loc(loc.getX(), loc.getY() - 1);
				ret.add(new Edge("", loc, dest, 1));
			}
			// if have horizontal edge
			if (loc.getY() != yLimit) {
				Loc dest = new Loc(loc.getX() - 1, loc.getY());
				ret.add(new Edge("", loc, dest, 1));
			}
		}
		return ret;
	}

	/******************** functions on Route - Light ******************/

	@Override
	public List<Route> getAllRoute(Loc loc) {
		List<Route> ret = new ArrayList<Route>();
		try {
			// must be an intersect, if not, there is not route
			if (isIntersect(loc)) {
				// there are two routes:
				{
					// horizontal
					Loc h1 = new Loc(loc.getX() - 1, loc.getY());
					Loc h2 = new Loc(loc.getX() + 1, loc.getY());
					Edge eh1 = new Edge("", h1, loc, 1);
					Edge eh2 = new Edge("", loc, h2, 1);
					ret.add(new Route(eh1, eh2));
				}
				{
					// vertical
					Loc v1 = new Loc(loc.getX(), loc.getY() - 1);
					Loc v2 = new Loc(loc.getX(), loc.getY() + 1);
					Edge ev1 = new Edge("", v1, loc, 1);
					Edge ev2 = new Edge("", loc, v2, 1);
					ret.add(new Route(ev1, ev2));
				}
			}
		} catch (Exception e) {
			System.err.println("getAllRoute : error constructing new Route");
		}
		return ret;
	}

	@Override
	public List<Route> getRouteOut(Edge inEdge) {
		List<Route> ret = new ArrayList<Route>();
		List<Route> ans = getAllRoute(inEdge.getTo());
		for (Route route : ans) {
			if (route.getInEdge() == inEdge) {
				ret.add(route);
			}
		}
		return ret;
	}

	@Override
	public Map<Route, Light> getAllLight(Loc loc) {
		return allLight[loc.getX() - 1][loc.getY() - 1].getAllLight();
	}

	@Override
	public Light getLight(Edge inEdge, Edge outEdge) {
		Route route = null;
		try {
			route = new Route(inEdge, outEdge);
		} catch (Exception e) {
			System.err.println("getLight(Edge, Edge) : error constructing new Route");
		}
		return getLight(route);
	}

	@Override
	public Light getLight(Route route) {
		Loc cur = route.getCurLoc();
		return getAllLight(cur).get(route);
	}

	@Override
	public Light getLight(Loc from, Loc cur, Loc to) {
		Light ret = null;
		try {
			Edge in = new Edge("", from, cur, 1);
			Edge out = new Edge("", cur, to, 1);
			Route route = new Route(in, out);
			ret = getAllLight(cur).get(route);
		} catch (Exception e) {
			System.err.println("GridMapLoc getLight(from, cur, to) error constructing Route!");
		}
		return ret;
	}

	/******************** functions on Intersection **********************/
	// Do we really need them?

	public Intersection getCross(Loc loc) {
		return allLight[loc.getX() - 1][loc.getY() - 1];
	}

//	public List<Intersection> getAllCross();

	@Override
	public void changeCrossStatus(Loc locCur) {
		allLight[locCur.getX() - 1][locCur.getY() - 1].change();
	}

//	public void changeCrossStatus(Intersection is);

	/*************************** Others ********************************/

	public List<Edge> generateWalk(Loc start) {
		List<Edge> ret = new ArrayList<Edge>();
		List<Edge> next = getAllEdgeFrom(start);
		List<Route> tmp = null;
		// next should not be empty
		ret.add(next.get((int) Math.random() * next.size()));
		while (!(isExit(ret.get(ret.size() - 1).getTo()))) {
			tmp = getRouteOut(ret.get(ret.size() - 1));
			ret.add(tmp.get((int) Math.random() * tmp.size()).getOutEdge());
		}
		return ret;
	}

}

class GridCross implements Intersection {

	private Loc loc;

	private Light horizontal;
	private Light vertical;

	GridCross(LIGHT hori, LIGHT vert) {
		horizontal = new Light(hori);
		vertical = new Light(vert);
	}

	GridCross() {
		horizontal = new Light(LIGHT.GREEN);
		vertical = new Light(LIGHT.RED);
	}

	@Override
	public Map<Route, Light> getAllLight() {
		Map<Route, Light> ret = new HashMap<Route, Light>();
		try {
			{
				// horizontal
				Loc h1 = new Loc(loc.getX() - 1, loc.getY());
				Loc h2 = new Loc(loc.getX() + 1, loc.getY());
				Edge eh1 = new Edge("", h1, loc, 1);
				Edge eh2 = new Edge("", loc, h2, 1);
				ret.put(new Route(eh1, eh2), horizontal);
			}
			{
				// vertical
				Loc v1 = new Loc(loc.getX(), loc.getY() - 1);
				Loc v2 = new Loc(loc.getX(), loc.getY() + 1);
				Edge ev1 = new Edge("", v1, loc, 1);
				Edge ev2 = new Edge("", loc, v2, 1);
				ret.put(new Route(ev1, ev2), vertical);
			}
		} catch (Exception e) {
			System.err.println("GridCross error contructing route at getAllLight!");
		}
		return ret;
	}

	@Override
	public void change() {
		if (horizontal.getStatus() == LIGHT.GREEN) {
			horizontal.setStatus(LIGHT.RED);
			vertical.setStatus(LIGHT.GREEN);
		} else {
			horizontal.setStatus(LIGHT.GREEN);
			vertical.setStatus(LIGHT.RED);
		}
	}

	@Override
	public void change(Map<Route, Light> setLight) {
		// do nothing here

	}

}
