# Traffic-simulator

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

### Objects

Lets abstract the objects involved and their properties:

```
Car : speed, position
Light : state
OnewayRoad : a group of cars, start point, end point
OpenEnd : a group of connecting Roads
Intersection: a traffice light, a group of connecting Roads
TrafficMap: a group of Intersections, a group of Roads
```

Structure to represent the traffic:

```java
Car { 
  int speed; 
  int location;
  Grid start;
  Grid end; 
}
interface Grid {
  List<int[]> from; // (x,y) there is a road from (x,y) to this
  List<int[]> to; // (x,y) there is a road from this to (x,y)
}
OpenEnd implements Grid{
}
Intersect implements Grid{
  Map<int[], int> light; // (x1,y1,x2,y2) the choice's traffic light status
  Map<int[], int[]> choice; // (x1, y1) -> (x2, y2) there is a lane available to let cars coming from (x1,y1) to turn to the lane toward (x2,y2)
}
TrafficMap {
  Map<int[], Grid> grids_map; // (x,y) -> Grid
  List<Car> cars;
}
```

Set up the Map:

```
// create a square Map with side length l with grid connection
t_map = New TrafficMap(int l); 
```

Change state of the Map:

```
t_map.setLightAt(int[], int state);
```

Run cars around the Map:

```
t_map.run_cars()
```

