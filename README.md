# Traffic-simulator

## UML Link

Starter Model : https://app.lucidchart.com/invitations/accept/312b7d10-62e1-4811-a88f-d754d09b2b0a

## Introduction

What factors are involved in a traffic? 

- Users: Cars, Pedestrians...
- Road lanes
- Intersections 
- Traffic Lights
- RoundAbouts
- ...

There are a lot of factors to consider if we want to simulate the whole traffic. 

However, there are something worth noting: 

1. Is the map self-closed? Does it have open-ends where traffic goes in/out?
2. Do lanes have specific purposes? how are they defined? 
3. The state of the map is defined by the states of traffic lights.
4. Cars react to other cars and to the traffic lights.
5. How much space does a Car occupy?

Hence, we need some assumptions to simplify the model. The assumptions can be different in what is the purpose of the model. 

Some general assumptions apply to all: 

1. Routes in a Map is unchangeable. 
2. Every car has similar reaction to other cars and the traffic lights. 

## Starter Model

### Add-on Assumptions

In a Starter Model, we simplify the model as much as possible. 

3. Map is a 2D square grid from $[0,0]$ to $[L,L] $. 
4. No Roundabouts. 
5. Enter points are $[x,0]$, $[0,y]$ where $1 \le x,y \le L-1$, $x_i,y_i \in \mathbb{N}$.
6. Exit points are $[x,L]$, $[L,y]$ where $1 \le x,y \le L-1$, $x_i,y_i \in \mathbb{N}$.
7. Intersections are all $(x_i, y_i)$, where $x_i,y_i \in \mathbb{N}$, and in accpectable range $[1,L-1]$. 
8. Roads are horizontal and vertical line segments connecting adjacent intersections and also connecting Entering points and exiting points to their adjacent intersection. 
9. Roads are one way, from lower index to higher index. 
10. Roads have the same length. 
11. Intersect can be passed without time.
12. Cars occupy no space, they can overlap. 
13. Cars maintain a constant speed on road. 
14. Each Car has a determined route when they are initialized. 

Effects:

- There is only one possible route from one Enter Point, which would Exit in a finite number of steps. 

### Structure Design

Lets abstract the objects involved and their properties:

Interface to the traffic:

```java
User { 
	double getSpeed();
	Loc getFromLoc();
	Loc getToloc();
	double getUntilLoc();
	void proceed(double interval);
	void putSelfInMap(MapLoc toMap, List<Loc> toWalk);
	void removeSelfFromMap();
}
InterSection {
	List<Light> getAllLight();
	void change();
}
Loc {
	int xLoc;
	int yLoc;

	int getX()
	int getY()
	boolean equals(Object other)
}
Light {
	enum LIGHT {
		GREEN, YELLOW, RED
	}
	LIGHT lightStatus;

	Light(LIGHT status)
	LIGHT getStatus()
	void setStatus(LIGHT status)
}
MapLoc {
	boolean isOpenEnd(Loc loc);
	boolean isExit(Loc loc);
	boolean isEntry(Loc loc);
	boolean isIntersect(Loc loc);
	boolean isEdge(Loc loc1, Loc loc2);
	double getLengthEdge(Loc loc1, Loc loc2);
	List<Loc> getAllTo(Loc loc);
	List<Loc> getAllFrom(Loc loc);
	List<Loc[]> getAllRoute(Loc loc);
	List<Light> getAllLight(Loc loc);
	Light getLight(Loc locFrom, Loc locCur, Loc locTo);
	void changeCorssStatus(Loc locCur);
}
Traffic{
	MapLoc map;
	List<User> users; 
	
	void step(double interval)
	List<User> getAllUsers()
	List<User> getAllUsers(Loc locFrom, Loc locTo)
	List<User> getAllUsersTo(Loc locTo)
	List<User> getAllUsersFrom(Loc locFrom)
}
```
