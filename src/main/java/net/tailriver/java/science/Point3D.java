package net.tailriver.java.science;


public class Point3D extends Point {
	double z;

	public Point3D(double x, double y, double z) {
		super(x, y);
		z(z);
	}

	public Point3D(Point p, double z) {
		this(p.x, p.y, z);
	}

	public final double z() {
		return z;
	}

	public final void z(double z) {
		this.z = z;
	}

	public final Point3D move(double dx, double dy, double dz) {
		move(dx, dy);
		z(z + dz);
		return this;
	}

	@Override
	public Point3D scale(double scale) {
		return scale(scale, scale, scale);
	}

	public final Point3D scale(double xscale, double yscale, double zscale) {
		scale(xscale, yscale);
		z(z * zscale);
		return this;
	}

	public final double getDistance(Point3D p) {
		return getDistance(this, p);
	}

	public final CylindricalPoint toCylindrical() {
		return toCylindrical(this);
	}

	@Override
	public Point3D clone() {
		return (Point3D) super.clone();
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof Point3D)
			return super.equals(o) && Double.compare(z, ((Point3D) o).z) == 0;
		return false;
	}

	@Override
	public int hashCode() {
		return super.hashCode() + 5 * Double.valueOf(z).hashCode();
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(super.toString());
		sb.append(",z=").append(z);
		return sb.toString();
	}

	public static final double getDistance(Point3D p1, Point3D p2) {
		double dx = p1.x - p2.x;
		double dy = p1.y - p2.y;
		double dz = p1.z - p2.z;
		return Math.sqrt(dx * dx + dy * dy + dz * dz);
	}

	public static final CylindricalPoint toCylindrical(Point3D p) {
		return new CylindricalPoint(p.toPolar(), p.z);
	}
}
