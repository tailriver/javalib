package net.tailriver.java.science;

public class PolarPoint implements Cloneable {
	double r, t;
	AngleType angleType;

	public PolarPoint(double r, double t, AngleType type) {
		this.r = r;
		this.t = t;
		this.angleType = type;
	}

	public PolarPoint(PolarPoint p) {
		this(p.r, p.t, p.angleType);
	}

	public double r() {
		return r;
	}

	public double t(AngleType type) {
		return type.equals(AngleType.RADIAN) ? tRadian() : tDegree();
	}

	public double tRadian() {
		return angleType.equals(AngleType.RADIAN) ? t : Math.toRadians(t);
	}

	public double tDegree() {
		return angleType.equals(AngleType.DEGREE) ? t : Math.toDegrees(t);
	}

	@Deprecated
	public void rotate(double angle, boolean isRadian) {
		rotate(angle, isRadian ? AngleType.RADIAN : AngleType.DEGREE);
	}

	public void rotate(double angle, AngleType type) {
		if (!this.angleType.equals(type)) {
			t = t(type);
			this.angleType = type;
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
