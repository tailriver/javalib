package net.tailriver.java.science;


public class Point implements Cloneable {
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

	public PolarPoint toPolar() {
		return toPolar(this);
	}

	@Override
	protected Point clone() {
		return new Point(this);
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof Point) {
			Point p = (Point)o;
			return Double.compare(x, p.x) == 0 && Double.compare(y, p.y) == 0;
		}
		return false;
	}

	@Override
	public int hashCode() {
		return 2 * Double.valueOf(x).hashCode() + 3 * Double.valueOf(y).hashCode();
	}

	@Override
	public String toString() {
		return (new StringBuilder()).append(x).append("\t").append(y).toString();
	}

	public static double getDistance(Point p1, Point p2) {
		double dx = p1.x - p2.x;
		double dy = p1.y - p2.y;
		return Math.sqrt(dx * dx + dy * dy);
	}

	public static PolarPoint toPolar(Point p) {
		double r = Math.sqrt(p.x * p.x + p.y * p.y);
		double t = Math.atan2(p.y, p.x);
		return new PolarPoint(r, t, AngleType.RADIAN);
	}
}
