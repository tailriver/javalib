package net.tailriver.java.science;

public class PolarPoint implements Cloneable {
	double r, t;
	boolean isRadian;

	public PolarPoint(double r, double t, boolean isRadian) {
		this.r = r;
		this.t = t;
		this.isRadian = isRadian;
	}

	public PolarPoint(PolarPoint p) {
		this(p.r, p.t, p.isRadian);
	}

	public double r() {
		return r;
	}

	public double tRadian() {
		return isRadian ? t : Math.toRadians(t);
	}

	public double tDegree() {
		return isRadian ? Math.toDegrees(t) : t;
	}

	public void rotate(double angle, boolean isRadian) {
		if (this.isRadian != isRadian) {
			t = isRadian ? tRadian() : tDegree();
			this.isRadian = isRadian;
		}
		t += angle;
	}

	public Point toPoint() {
		return toPoint(this);
	}

	public static Point toPoint(PolarPoint p) {
		double x = p.r * Math.cos(p.tRadian());
		double y = p.r * Math.sin(p.tRadian());
		return new Point(x, y);
	}

	@Override
	public PolarPoint clone() {
		return new PolarPoint(this);
	}
}
