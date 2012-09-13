package net.tailriver.java.science;


public class Point3D extends Point {
	double z;

	public Point3D(double x, double y, double z) {
		super(x, y);
		this.z = z;
	}

	public Point3D(Point3D p) {
		this(p.x, p.y, p.z);
	}

	public Point3D(Point p, double z) {
		super(p);
		this.z = z;
	}

	public double z() {
		return z;
	}

	public void move(double dx, double dy, double dz) {
		move(dx, dy);
		z += dz;
	}

	public CylindricalPoint toCylindrical() {
		return toCylindrical(this);
	}

	public static CylindricalPoint toCylindrical(Point3D p) {
		return new CylindricalPoint(p.toPolar(), p.z);
	}
}
