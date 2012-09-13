package net.tailriver.java.science;


public class IsoparametricInterpolator {
	Point[] x;
	double[] n;
	boolean isInElement;

	public IsoparametricInterpolator() {
		n = new double[4];
	}

	public IsoparametricInterpolator(Point... nodes) {
		this();
		setNode(nodes);
	}

	public void setNode(Point... nodes) {
		if (nodes == null)
			throw new IllegalArgumentException();

		if (nodes.length != n.length)
			throw new UnsupportedOperationException();

		for (Point node : nodes) {
			if (node == null)
				throw new IllegalArgumentException();
		}

		// TODO throw if nodes[*] = node[*]

		x = nodes;
	}

	public void setUnknownPoint(Point p) {
		if (p == null)
			throw new IllegalArgumentException();

		double xi = 0;
		double eta = 0;
		try {
			double[] xi_eta = getXiEta(p);
			xi  = xi_eta[0];
			eta = xi_eta[1];
		} catch (Exception e) {
			throw e;
		}

		isInElement = Math.abs(xi) <= 1 && Math.abs(eta) <= 1;

		n[0] = (1 - xi) * (1 - eta) / 4;
		n[1] = (1 + xi) * (1 - eta) / 4;
		n[2] = (1 + xi) * (1 + eta) / 4;
		n[3] = (1 - xi) * (1 + eta) / 4;
	}

	public boolean isInElement() {
		return isInElement;
	}

	public double getUnknownValue(double... value) {
		if (value.length != n.length)
			throw new UnsupportedOperationException();

		double result = 0;
		for (int i = 0; i < n.length; i++)
			result += n[i] * value[i];
		return result;
	}

	private double[] getXiEta(Point p) {
		// solves:
		//		a1 * xi * eta + a2 * xi + a3 * eta + a4 = 0
		//		b1 * xi * eta + b2 * xi + b3 * eta + b4 = 0

		double a1 = ( + x[0].x() - x[1].x() + x[2].x() - x[3].x() ) / 4;
		double a2 = ( - x[0].x() + x[1].x() + x[2].x() - x[3].x() ) / 4;
		double a3 = ( - x[0].x() - x[1].x() + x[2].x() + x[3].x() ) / 4;
		double a4 = ( + x[0].x() + x[1].x() + x[2].x() + x[3].x() ) / 4 - p.x();
		double b1 = ( + x[0].y() - x[1].y() + x[2].y() - x[3].y() ) / 4;
		double b2 = ( - x[0].y() + x[1].y() + x[2].y() - x[3].y() ) / 4;
		double b3 = ( - x[0].y() - x[1].y() + x[2].y() + x[3].y() ) / 4;
		double b4 = ( + x[0].y() + x[1].y() + x[2].y() + x[3].y() ) / 4 - p.y();

		double xi, eta;
		if (a1 != 0) {
			// solve:
			//		sa * xi^2 + sb * xi + sc = 0

			double sa = a1 * b2 - a2 * b1;
			double sb = a1 * b4 - a4 * b1 + a3 * b2 - a2 * b3;
			double sc = a3 * b4 - a4 * b3;

			xi  = solveQuadraticFormula(sa, sb, sc);
			eta = - (a2 * xi + a4) / (a1 * xi + a3);
		}
		else if (b1 != 0) {
			// solve:
			//		sa * eta^2 + sb * eta + sc = 0

			double sa = a1 * b3 - a3 * b1;
			double sb = a1 * b4 - a4 * b1 + a2 * b3 - a3 * b2;
			double sc = a2 * b4 - a4 * b2;

			eta = solveQuadraticFormula(sa, sb, sc);
			xi  = - (b3 * eta + a4) / (b1 * eta + b2);
		}
		else {	
			// a1 and b1 are zero.
			// now solves:
			//		a2 * xi + a3 * eta + a4 = 0
			//		b2 * xi + b3 * eta + b4 = 0

			double determinant = a2 * b3 - a3 * b2;
			if (determinant == 0)
				throw new RuntimeException("singular?");

			xi  = a3 * b4 - a4 * b3 / determinant;
			eta = a4 * b2 - a2 * b4 / determinant;
		}

		return new double[]{ xi, eta };
	}

	private double solveQuadraticFormula(double a, double b, double c) {
		// solve:
		//		a * x^2 + b * x + c = 0

		if (a != 0) {
			double sqrtDiscriminant = Math.sqrt( b * b - 4 * a * c );
			double root1 = (-b + sqrtDiscriminant) / (2 * a);
			double root2 = (-b - sqrtDiscriminant) / (2 * a);

			// select normalized solution area from the roots
			if (Math.abs(root1) <= 1)
				return root1;
			if (Math.abs(root2) <= 1)
				return root2;
		}

		// in fact, this is linear
		// solve:
		//		b * x + c = 0

		return -c / b;
	}

	public static void main(String... args) {
		Point pi = new Point(-2.3, -1.5);
		Point pj = new Point( 1.8, -2.6);
		Point pk = new Point( 2.4,  1.7);
		Point pl = new Point(-2.1,  1.5);

		IsoparametricInterpolator iso = new IsoparametricInterpolator(pi, pj, pk, pl);

		for (int xdiv = 0; xdiv <= 50; xdiv++) {
			double px = 6 * (xdiv / 50.0) - 3;
			for (int ydiv = 0; ydiv <= 50; ydiv++) {
				double py = 6 * (ydiv / 50.0) - 3;
				Point p = new Point(px, py);
				iso.setUnknownPoint(p);
				double v = iso.getUnknownValue(1, 2, 3.5, 5.5);
				System.out.println(px + "\t" + py + "\t" + (iso.isInElement ? v : "?"));
			}
			System.out.println();
		}
	}
}
