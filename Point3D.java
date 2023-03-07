/**
 * Student name: Chentao Jin
 * Student ID: 300220076
 */

package Project_Part1;

public class Point3D {
	/**
	 * point x y z value
	 */
	private float x, y, z;

	public Point3D(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public float getZ() {
		return z;
	}

	
	/**
	 * return point value
	 */
	@Override
	public String toString() {
		return x + "\t" + y + "\t" + z;
	}

}
