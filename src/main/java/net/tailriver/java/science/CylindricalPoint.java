package net.tailriver.java.science;


public class CylindricalPoint extends PolarPoint {
	double z;

	public CylindricalPoint(double r, double t, double z, boolean isRadian) {
		super(r, t, isRadian);
		this.z = z;
	}

	public CylindricalPoint(PolarPoint p, double z) {
		super(p);
		this.z = z;
	}

	public CylindricalPoint(CylindricalPoint p) {
		this(p, p.z);
	}

	public double z() {
		return z;
	}

	public Point3D toPoint3D() {
		return toPoint3D(this);
	}

	public static Point3D toPoint3D(CylindricalPoint p) {
		return new Point3D(p.toPoint(), p.z);
	}

	@Override
	public CylindricalPoint clone() {
		return new CylindricalPoint(super.clone(), z);
	}
}
