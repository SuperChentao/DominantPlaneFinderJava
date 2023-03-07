/**
 * Student name: Chentao Jin
 * Student ID: 300220076
 */

package Project_Part1;

public class Plane3D {
	/**
	 * 3 points to form the plane
	 */
	private Point3D[] points = new Point3D[3];

	/**
	 * equation to determine the plane
	 * a*X+b*y+c*Z+d=0
	 */
	private double a, b, c, d = 0;

	/**
	 * Support point number for the plane
	 */
	private int support=0;

	public Plane3D(Point3D p1, Point3D p2, Point3D p3) {
		this.points[0] = p1;
		this.points[1] = p2;
		this.points[2] = p3;
		
		//generate normalized plane equation for later comparison
		this.generateNormalizedEquation(p1.getX(), p1.getY(), p1.getZ(), p2.getX(), p2.getY(), p2.getZ(), p3.getX(),
				p3.getY(), p3.getZ());
	}

	public Plane3D(double a, double b, double c, double d) {
		this.a = a;
		this.b = b;
		this.c = c;
		this.d = d;
	}

	
	/**
	 * @param pt Point to calculate distance to the plane
	 * @return The distance from the point to the current plane
	 */
	public double getDistance(Point3D pt) {
		double x = pt.getX();
		double y = pt.getY();
		double z = pt.getZ();
		double distance = (Math.abs(a * x + b * y + c * z + d))
				/ (Math.sqrt(Math.pow(a, 2) + Math.pow(b, 2) + Math.pow(c, 2)));
		return distance;
	}

	
	/**
	 * Increment support
	 */
	public void incSupport() {
		++support;
	}

	/**
	 * @return Current plane support number
	 */
	public int getSupport() {
		return support;
	}

	/**
	 * @param x1 X in point1
	 * @param y1 Y in point1
	 * @param z1 Z in point1
	 * @param x2 X in point2
	 * @param y2 Y in point2
	 * @param z2 Z in point2
	 * @param x3 X in point3
	 * @param y3 Y in point3
	 * @param z3 Z in point3
	 * 
	 * Generate normalized plane equation
	 */
	private void generateNormalizedEquation(float x1, float y1, float z1, float x2, float y2, float z2, float x3,
			float y3, float z3) {
		float a1 = x2 - x1;
		float b1 = y2 - y1;
		float c1 = z2 - z1;
		float a2 = x3 - x1;
		float b2 = y3 - y1;
		float c2 = z3 - z1;
		this.a = b1 * c2 - b2 * c1;
		this.b = a2 * c1 - a1 * c2;
		this.c = a1 * b2 - b1 * a2;
		this.d = (-a * x1 - b * y1 - c * z1);
		this.b = b / a;
		this.c = c / a;
		this.d = d / a;
		this.a = 1;
//		System.out.println(
//				"equation of plane is " + this.a + " x + " + this.b + " y + " + this.c + " z + " + this.d + " = 0.");

	}

}
