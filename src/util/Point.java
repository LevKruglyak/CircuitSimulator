package util;

import java.awt.event.MouseEvent;

public class Point {
	private float x;
	private float y;
	
	public Point(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public Point(Point add) {
		this.x = add.getX();
		this.y = add.getY();
	}

	public String toString() {
		return this.x + "&" + this.y;
	}

	public Point(String actionCommand) {
		this.x = Float.parseFloat(actionCommand.split("&")[0]);
		this.y = Float.parseFloat(actionCommand.split("&")[1]);
	}
	
	public Point(MouseEvent e) {
		this.x = e.getX();
		this.y = e.getY();
	}

	public void edit(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public float distanceTo(Point p1, Point p2) {
		Point diff = p2.subtract(p1);
		float num = Math.abs(diff.getY() * getX() - diff.getX() * getY() + p2.getX() * p1.getY() - p2.getY() * p1.getX());
		return num/diff.magnitude();
	}

	public boolean isProjInside(Point p1, Point p2) {
		float proj_1 = (this.subtract(p1)).dot(p2.subtract(p1));
		float proj_2 = (p2.subtract(p1)).dot(p2.subtract(p1));
		
		return (proj_1 >= 0) && (proj_1 <= proj_2);
	}
	
	public Point toPoint() {
		return new Point((int) Math.round(x), (int) Math.round(y));
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public float distanceTo(Point v) {
		return new Point(v).subtract(this).magnitude();
	}

	public float magnitude() {
		return (float) Math.sqrt(x * x + y * y);
	}

	public Point add(Point v) {
		return new Point(this.x + v.x, this.y + v.y);
	}

	public Point subtract(Point v) {
		return new Point(this.x - v.x, this.y - v.y);
	}

	public float angle_to(Point v) {
		float dot = x * v.getX() + y * v.getY();
		float mag1 = magnitude();
		float mag2 = v.magnitude();
		float acos = dot / (mag1 * mag2);
		return (float) Math.acos(acos);
	}

	public static Point angle(float angle, float scale) {
		return new Point((float) (scale * Math.cos(angle)), (float) (scale * Math.sin(angle)));
	}

	public Point normalize() {
		if (x == 0 && y == 0) {
			return new Point(this);
		}
		return new Point(x / magnitude(), y / magnitude());
	}

	public Point times(float a) {
		return new Point(a * x, a * y);
	}

	public static Point random(int width, int height) {
		return new Point((float) (width * Math.random()), (float) (height * Math.random()));
	}
	
	public float dot(Point p1) {
		return p1.getX()*getX() + p1.getY()*getY();
	}

	public static boolean intersects(Point center, float radius, Point pos1, Point pos2) {
		float x0 = center.getX();
		float x1 = pos1.getX();
		float x2 = pos2.getX();

		float y0 = center.getY();
		float y1 = pos1.getY();
		float y2 = pos2.getY();

		return Math.abs((x2 - x1) * x0 + (y1 - y2) * y0 + (x1 - x2) * y1 + x1 * (y2 - y1))
				/ (pos2.subtract(pos1).magnitude()) <= radius;
	}
}