package particle;

import vectors.Point2D;

public class Cell {
double mass;
Point2D vel = Point2D.Origin();
	public Cell(double density) {
		mass=density;
	}
	public Cell(double density, Point2D velocity) {
		mass=density;
		vel = velocity;
	}
	public Cell Merge(Cell a, Cell b){
		return new Cell(a.mass+b.mass, Point2D.add(a.vel, b.vel));
	}
	public Cell Scale(double scale){
		return new Cell(mass*scale, vel.scale(scale));
	}
}
