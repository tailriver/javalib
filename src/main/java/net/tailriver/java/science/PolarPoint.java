package net.tailriver.java.science;


public class PolarPoint implements Cloneable {
	/** radius (r) coordinate value. */
	double r;

	/** angle (&theta;) coordinate value. */
	double t;

	/** angle unit type of {@link #t}. */
	AngleType angleType;

	/**
	 * Constructs a point (r, &theta;) with specified coordinate values.
	 * @param r - Radius coordinate value.
	 * @param t - Angle coordinate value. (t means &theta;)
	 * @param type - Angle unit type.
	 */
	public PolarPoint(double r, double t, AngleType type) {
		r(r);
		t(t, type);
	}

	/**
	 * Gets radius value of the point.
	 * @return radius
	 */
	public final double r() {
		return r;
	}

	public final void r(double r) {
		this.r = r;
	}

	/**
	 * Gets angle value of the point in specified {@link AngleType}.
	 * This is syntax sugars of {@link #tRadian()} and {@link #tDegree()}.
	 * @param type - angle unit type which you want to get.
	 * @return angle in specified {@code type}.
	 * @throws UnsupportedOperationException When called with unknown {@code type}.
	 */
	public final double t(AngleType type) {
		switch (type) {
		case RADIAN:
			return tRadian();
		case DEGREE:
			return tDegree();
		default:
			throw new UnsupportedOperationException();
		}
	}

	/**
	 * Gets angle in radian.
	 * @return angle in radian.
	 */
	public final double tRadian() {
		switch (angleType) {
		case RADIAN:
			return t;
		case DEGREE:
			return Math.toRadians(t);
		default:
			throw new UnsupportedOperationException();
		}
	}

	/**
	 * Gets angle in degree.
	 * @return angle in degree.
	 */
	public final double tDegree() {
		switch (angleType) {
		case RADIAN:
			return Math.toDegrees(t);
		case DEGREE:
			return t;
		default:
			throw new UnsupportedOperationException();
		}
	}

	public final void t(double t, AngleType type) {
		this.t = t;
		this.angleType = type;
	}

	public final PolarPoint move(double dr) {
		r(r + dr);
		return this;
	}

	public PolarPoint rotate(double dt, AngleType type) {
		t(t(type) + dt, type);
		return this;
	}

	public PolarPoint scale(double scale) {
		r(r * scale);
		return this;
	}

	public final Point toPoint() {
		return toPoint(this);
	}

	@Override
	public PolarPoint clone() {
		try {
			PolarPoint c = (PolarPoint) super.clone();
			c.angleType = angleType;
			return c;
		} catch (CloneNotSupportedException e) {
			throw new AssertionError();
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof PolarPoint) {
			PolarPoint p = (PolarPoint) obj;
			return Double.compare(r, p.r) == 0 && Double.compare(t, p.t) == 0
					&& angleType.equals(p.angleType);
		}
		return false;
	}

	@Override
	public int hashCode() {
		return 2 * Double.valueOf(r).hashCode() + 3 * Double.valueOf(t).hashCode()
				+ 5 * angleType.hashCode();
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(getClass().getSimpleName());
		sb.append("@r=").append(r).append(",t=").append(t);
		sb.append("[").append(angleType.name()).append("]");
		return sb.toString();
	}

	/**
	 * 
	 * @param p
	 * @return A converted point in two-dimensional orthogonal coordinate system.
	 */
	public static final Point toPoint(PolarPoint p) {
		double x = p.r * Math.cos(p.tRadian());
		double y = p.r * Math.sin(p.tRadian());
		return new Point(x, y);
	}
}
