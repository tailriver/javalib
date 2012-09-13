package net.tailriver.java.science;


public class IsoparametricInterpolator {
	private static final int LENGTH = 4;
	private static final double epsilon = 1e-8;

	Point[] x;
	double[] nodeValues;
	double xi, eta;

	public IsoparametricInterpolator(Point... nodes) {
		setNode(nodes);
	}

	public void setNode(Point... nodes) {
		if (nodes.length != LENGTH)
			throw new UnsupportedOperationException();

		for (Point node : nodes) {
			if (node == null)
				throw new IllegalArgumentException();
		}

		// TODO throw if nodes[*] = node[*]

		x = nodes;
		clearXiEta();
	}

	public void setNodeValue(double... nodeValues) {
		if (nodeValues.length != LENGTH)
			throw new UnsupportedOperationException();

		this.nodeValues = nodeValues;		
	}
	
	public double getUnknownValue(Point p) {
		if (p == null)
			throw new IllegalArgumentException();

		clearXiEta();
		calculateXiEta(p);

		double[] N = new double[LENGTH];
		N[0] = (1 - xi) * (1 - eta) / 4;
		N[1] = (1 + xi) * (1 - eta) / 4;
		N[2] = (1 + xi) * (1 + eta) / 4;
		N[3] = (1 - xi) * (1 + eta) / 4;

		double result = 0;
		for (int i = 0; i < LENGTH; i++)
			result += N[i] * nodeValues[i];
		return result;
	}

	public double getXi() {
		return xi;
	}

	public double getEta() {
		return eta;
	}

	public boolean isInElement() {
		return isInDefinitionArea(xi) && isInDefinitionArea(eta);
	}

	private void clearXiEta() {
		xi  = Double.NaN;
		eta = Double.NaN;
	}

	private void calculateXiEta(Point p) {
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

		if (a1 != 0) {
			// solve:
			//		sa * xi^2 + sb * xi + sc = 0

			double sa = a1 * b2 - a2 * b1;
			double sb = a1 * b4 - a4 * b1 + a3 * b2 - a2 * b3;
			double sc = a3 * b4 - a4 * b3;

			xi  = solveQuadraticFormula(sa, sb, sc);
		}

		if (b1 != 0) {
			// solve:
			//		sa * eta^2 + sb * eta + sc = 0

			double sa = a1 * b3 - a3 * b1;
			double sb = a1 * b4 - a4 * b1 + a2 * b3 - a3 * b2;
			double sc = a2 * b4 - a4 * b2;

			eta = solveQuadraticFormula(sa, sb, sc);
		}

		if (!Double.isNaN(xi) && Double.isNaN(eta)) {
			eta = - (a2 * xi + a4) / (a1 * xi + a3);
		}
		if (Double.isNaN(xi) && !Double.isNaN(eta)) {
			xi = - (b3 * eta + b4) / (b1 * eta + b2);
		}

		if (!Double.isNaN(xi) && !Double.isNaN(eta))
			return;

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

	private double solveQuadraticFormula(double a, double b, double c) {
		// solve:
		//		a * x^2 + b * x + c = 0

		if (a != 0) {
			double sqrtDiscriminant = Math.sqrt( b * b - 4 * a * c );
			double root1 = (-b + sqrtDiscriminant) / (2 * a);
			double root2 = (-b - sqrtDiscriminant) / (2 * a);

			// select normalized solution area from the roots
			if (isInDefinitionArea(root1))
				return root1;
			if (isInDefinitionArea(root2))
				return root2;

			return Double.NaN;
		}

		// in fact, this is linear
		// solve:
		//		b * x + c = 0

		return -c / b;
	}

	private static boolean isInDefinitionArea(double v) {
		return Math.abs(v) <= 1 + epsilon;
	}

	public static void main(String... args) {
		Point pi = new Point(-2.3, -1.5);
		Point pj = new Point( 1.8, -2.6);
		Point pk = new Point( 2.4,  1.7);
		Point pl = new Point(-2.1,  1.5);

		IsoparametricInterpolator iso = new IsoparametricInterpolator(pi, pj, pk, pl);
		iso.setNodeValue(1, 2, 3.5, 5.5);

		for (int xdiv = 0; xdiv <= 50; xdiv++) {
			double px = 6 * (xdiv / 50.0) - 3;
			for (int ydiv = 0; ydiv <= 50; ydiv++) {
				double py = 6 * (ydiv / 50.0) - 3;
				Point p = new Point(px, py);
				double v = iso.getUnknownValue(p);
				System.out.println(px + "\t" + py + "\t" + (iso.isInElement() ? v : "?"));
			}
			System.out.println();
		}
	}
}
