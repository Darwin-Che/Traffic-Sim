package model.map;

import java.util.ArrayList;
import java.util.List;

import model.map.Light.LIGHT;

public class GridMapLoc implements MapLoc {

	private int xLimit; // 0 <= x <= xLimit
	private int yLimit; // 0 <= y <= yLimit

	private GridCross[][] allLight;

	GridMapLoc(int xl, int yl) {
		xLimit = xl;
		yLimit = yl;
		allLight = new GridCross[xLimit + 1][yLimit + 1];
	}

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
	public boolean isEdge(Loc loc1, Loc loc2) {
		// TODO Auto-generated method stub
		boolean ret = false;
		int x1 = loc1.getX();
		int x2 = loc2.getX();
		int y1 = loc1.getY();
		int y2 = loc2.getY();
		if (x1 == x2 && x1 != 0 && x1 != xLimit) {
			if (y1 - y2 == 1)
				ret = true;
			if (y2 - y1 == 1)
				ret = true;
		}
		if (y1 == y2 && y1 != 0 && y1 != yLimit) {
			if (x1 - x2 == 1)
				ret = true;
			if (x2 - x1 == 1)
				ret = true;
		}
		return ret;
	}

	@Override
	public double getLengthEdge(Loc loc1, Loc loc2) {
		// TODO Auto-generated method stub
		if (isEdge(loc1, loc2)) {
			return 1;
		}
		return -1;
	}

	@Override
	public List<Loc> getAllTo(Loc loc) {
		// TODO Auto-generated method stub
		List<Loc> ret = new ArrayList<Loc>();
		if (loc.getX() < xLimit && loc.getY() < yLimit) {
			if (loc.getX() != 0) {
				ret.add(new Loc(loc.getX(), loc.getY() + 1));
			}
			if (loc.getY() != 0) {
				ret.add(new Loc(loc.getX() + 1, loc.getY()));
			}
		}
		return ret;
	}

	@Override
	public List<Loc> getAllFrom(Loc loc) {
		// TODO Auto-generated method stub
		List<Loc> ret = new ArrayList<Loc>();
		if (loc.getX() > 0 && loc.getY() > 0) {
			if (loc.getX() != xLimit) {
				ret.add(new Loc(loc.getX(), loc.getY() - 1));
			}
			if (loc.getY() != yLimit) {
				ret.add(new Loc(loc.getX() - 1, loc.getY()));
			}
		}
		return ret;
	}

	@Override
	public List<Loc[]> getAllRoute(Loc loc) {
		// TODO Auto-generated method stub
		List<Loc[]> ret = new ArrayList<Loc[]>();
		if (isIntersect(loc)) {
			Loc[] r1 = new Loc[2];
			r1[0] = new Loc(loc.getX() - 1, loc.getY());
			r1[0] = new Loc(loc.getX() + 1, loc.getY());
			Loc[] r2 = new Loc[2];
			r2[0] = new Loc(loc.getX(), loc.getY() - 1);
			r2[0] = new Loc(loc.getX(), loc.getY() + 1);
			ret.add(r1);
			ret.add(r2);
		}
		return ret;
	}

	@Override
	public List<Light> getAllLight(Loc loc) {
		return allLight[loc.getX()][loc.getY()].getAllLight();
	}

	@Override
	public Light getLight(Loc locFrom, Loc locCur, Loc locTo) {
		Loc[] r = { locFrom, locTo };
		if (getAllRoute(locCur).contains(r)) {
			if (locFrom.getX() == locCur.getX())
				return allLight[locCur.getX()][locCur.getY()].getAllLight().get(1);
			if (locFrom.getY() == locCur.getY())
				return allLight[locCur.getX()][locCur.getY()].getAllLight().get(0);
		}
		return null;
	}

	@Override
	public void changeCorssStatus(Loc locCur) {
		allLight[locCur.getX()][locCur.getY()].change();
	}

}

class GridCross implements Intersection {

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
	public List<Light> getAllLight() {
		List<Light> ret = new ArrayList<Light>();
		ret.add(horizontal);
		ret.add(vertical);
		return ret;
	}

}
