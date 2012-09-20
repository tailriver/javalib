package net.tailriver.java.science;


public class CylindricalPoint extends PolarPoint {
	double z;

	public CylindricalPoint(double r, double t, double z, AngleType type) {
		super(r, t, type);
		z(z);
	}

	public CylindricalPoint(PolarPoint p, double z) {
		this(p.r, p.t, z, p.angleType);
	}

	public final double z() {
		return z;
	}

	public final void z(double z) {
		this.z = z;
	}

	public final CylindricalPoint move(double dr, double dz) {
		move(dr);
		z(z + dz);
		return this;
	}

	@Override
	public CylindricalPoint rotate(double dt, AngleType type) {
		super.rotate(dt, type);
		return this;
	}

	@Override
	public CylindricalPoint scale(double scale) {
		return scale(scale, scale);
	}

	public final CylindricalPoint scale(double rscale, double zscale) {
		super.scale(rscale);
		z(z * zscale);
		return this;
	}

	public final Point3D toPoint3D() {
		return toPoint3D(this);
	}

	@Override
	public CylindricalPoint clone() {
		return (CylindricalPoint) super.clone();
	}

	@Override
	public boolean equals(Object obj) {
		if (super.equals(obj) && obj instanceof CylindricalPoint)
			return Double.compare(z, ((CylindricalPoint)obj).z) == 0;
		return false;
	}

	@Override
	public int hashCode() {
		return super.hashCode() + 7 * Double.valueOf(z).hashCode();
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(super.toString());
		sb.append(",z=").append(z);
		return sb.toString();
	}

	public final static Point3D toPoint3D(CylindricalPoint p) {
		return new Point3D(p.toPoint(), p.z);
	}
}
