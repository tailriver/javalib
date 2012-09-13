package net.tailriver.java.science;

public class Point {
	double x, y;

	public Point(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public Point(Point p) {
		this(p.x, p.y);
	}

	public double x() {
		return x;
	}

	public double y() {
		return y;
	}

	public void move(double dx, double dy) {
		x += dx;
		y += dy;
	}

	public double getDistance(Point p) {
		return getDistance(this, p);
	}

	public static double getDistance(Point p1, Point p2) {
		double dx = p1.x - p2.x;
		double dy = p1.y - p2.y;
		return Math.sqrt(dx * dx + dy * dy);
	}

	public PolarPoint toPolar() {
		return toPolar(this);
	}

	public static PolarPoint toPolar(Point p) {
		double r = Math.sqrt(p.x * p.x + p.y * p.y);
		double t = Math.atan2(p.y, p.x);
		return new PolarPoint(r, t, true);
	}
}
