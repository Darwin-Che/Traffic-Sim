package model.map;

import java.util.List;
import java.util.Map;

public interface Intersection {

	public Map<Route, Light> getAllLight();

	public void change(Map<Route, Light> setLight);

	public void change();

}
