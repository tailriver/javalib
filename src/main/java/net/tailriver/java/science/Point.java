package net.tailriver.java.science;


public class Point implements Cloneable {
	double x;
	double y;

	public Point(double x, double y) {
		x(x);
		y(y);
	}

	public final double x() {
		return x;
	}

	public final void x(double x) {
		this.x = x;
	}

	public final double y() {
		return y;
	}

	public final void y(double y) {
		this.y = y;
	}

	public final Point move(double dx, double dy) {
		x(x + dx);
		y(y + dy);
		return this;
	}

	public Point scale(double scale) {
		return scale(scale, scale);
	}

	public final Point scale(double xscale, double yscale) {
		x(x * xscale);
		y(y * yscale);
		return this;
	}

	public final double getDistance(Point p) {
		return getDistance(this, p);
	}

	public final PolarPoint toPolar() {
		return toPolar(this);
	}

	@Override
	public Point clone() {
		try {
			return (Point) super.clone();
		} catch (CloneNotSupportedException e) {
			throw new AssertionError();
		}
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
		StringBuilder sb = new StringBuilder(getClass().getSimpleName());
		sb.append("x=").append(x).append(",y=").append(y);
		return sb.toString();
	}

	public static final double getDistance(Point p1, Point p2) {
		double dx = p1.x - p2.x;
		double dy = p1.y - p2.y;
		return Math.sqrt(dx * dx + dy * dy);
	}

	public static final PolarPoint toPolar(Point p) {
		double r = Math.sqrt(p.x * p.x + p.y * p.y);
		double t = Math.atan2(p.y, p.x);
		return new PolarPoint(r, t, AngleType.RADIAN);
	}
}
