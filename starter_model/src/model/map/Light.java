package model.map;


public class Light {
	public enum LIGHT {
		GREEN, YELLOW, RED
	}
	
	private LIGHT lightStatus;
	
	public Light(LIGHT status) {
		lightStatus = status;
	}
	
	public LIGHT getStatus() {
		return lightStatus;
	}
	
	public void setStatus(LIGHT status) {
		lightStatus = status;
	}
	
	public String toString() {
		return lightStatus.toString();
	}
}
