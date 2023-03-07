/**
 * Student name: Chentao Jin
 * Student ID: 300220076
 */

package Project_Part1;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;

/**
 * @author chent
 *
 */
public class PlaneRANSAC implements Runnable {

	/**
	 * Cloud to find dominant planes
	 */
	private PointCloud cloud;

	/**
	 * maximum value to determine if a point is on a plane
	 */
	private double eps;

	/**
	 * Terminated thread number
	 */
	private int count = 0;

	/**
	 * Current dominant plane
	 */
	private Plane3D plane;

	public PlaneRANSAC(PointCloud pc) {
		this.cloud = pc;
	}

	/**
	 * @return epsilon
	 */
	public double getEps() {
		return eps;
	}

	/**
	 * @param eps Set epsilon
	 */
	public void setEps(double eps) {
		this.eps = eps;
	}

	/**
	 * @param confidence                Expected confidence
	 * @param percentageOfPointsOnPlane Estimates percentage value of points on the
	 *                                  dominant plane
	 * @return Estimated number of iterations for confidence
	 */
	public int getNumberOfIterations(double confidence, double percentageOfPointsOnPlane) {
		double k = (Math.log(1 - confidence)) / (Math.log(1 - Math.pow(percentageOfPointsOnPlane, 3)));
		return (int) Math.ceil(k);
	}

	/**
	 * @param numberOfIterations Estimated number of iterations for confidence
	 * @param filename           File to store points
	 * @throws InterruptedException
	 */
	public void run(int numberOfIterations, String filename) throws InterruptedException {
		Thread[] threads = new Thread[numberOfIterations];
		PointCloud dominantPlane = new PointCloud();
		Point3D point;
		Iterator<Point3D> it = cloud.iterator();

		// For each iteration, create a thread and run the algorithm
		for (int i = 0; i < numberOfIterations; i++) {
			String name = "name" + i;
			threads[i] = new Thread(this, name);
			threads[i].start();
		}

		// wait for all threads terminate
		while (true) {
			Thread.sleep(100);
			if (count == numberOfIterations)
				break;
		}

		System.out.println("Done!\nbiggest support is " + this.plane.getSupport());
		System.out.println("Start writing to file: " + filename + " ...");

		// Write points belong to the most dominant plane to file and remove from cloud
		while (it.hasNext()) {
			point = it.next();
			if (this.plane.getDistance(point) <= this.eps) {
				dominantPlane.addPoint(point);
				it.remove();
			}
		}

		dominantPlane.save(filename);

		// Remove dominant plane after writing out
		this.plane = null;
		// Reset terminated thread number
		count = 0;
	}

	/**
	 * Run RANSAC algorithm
	 */
	@Override
	public synchronized void run() {
		double distance;

		// Pick 3 random points from cloud and form a plane
		Plane3D plane = new Plane3D(cloud.getPoint(), cloud.getPoint(), cloud.getPoint());
		Iterator<Point3D> it = cloud.iterator();

		// Measure the distance for every point in the cloud to this plane
		// Increment support number if distance <= epsilon
		while (it.hasNext()) {
			distance = plane.getDistance((Point3D) it.next());

			if (distance <= this.eps)
				plane.incSupport();
		}

		// Store the plane to the most dominant plane if this plane has bigger support
		// points, or no dominant plane yet
		if (this.plane == null || plane.getSupport() > this.plane.getSupport()) {
			this.plane = plane;
		}

		// Increment the terminated thread number
		++count;
	}

	public static void main(String[] args) throws InterruptedException {
		// Read cloud file 1 and generated and write 3 most dominant plane and points
		// not in the 3 planes
		PointCloud pc1 = new PointCloud("PointCloud1.xyz");
		PlaneRANSAC ransac1 = new PlaneRANSAC(pc1);
		int estimatedTimes = ransac1.getNumberOfIterations(0.99, 0.1);
		System.out.println("Cloud1 estimated times: " + estimatedTimes);

		ransac1.setEps(0.1);
		ransac1.run(estimatedTimes, "PointCloud1_p1.xyz");
		ransac1.run(estimatedTimes, "PointCloud1_p2.xyz");
		ransac1.run(estimatedTimes, "PointCloud1_p3.xyz");
		pc1.save("PointCloud1_p0.xyz");

		// Read cloud file 2 and generated and write 3 most dominant planes and points
		// not in the 3 planes
		PointCloud pc2 = new PointCloud("PointCloud2.xyz");
		PlaneRANSAC ransac2 = new PlaneRANSAC(pc2);
		int estimatedTimes2 = ransac2.getNumberOfIterations(0.99, 0.1);
		System.out.println("Cloud2 estimated times: " + estimatedTimes2);

		ransac2.setEps(0.1);
		ransac2.run(estimatedTimes2, "PointCloud2_p1.xyz");
		ransac2.run(estimatedTimes2, "PointCloud2_p2.xyz");
		ransac2.run(estimatedTimes2, "PointCloud2_p3.xyz");
		pc2.save("PointCloud2_p0.xyz");

		// Read cloud file 3 and generated and write 3 most dominant planes and points
		// not in the 3 planes
		PointCloud pc3 = new PointCloud("PointCloud3.xyz");
		PlaneRANSAC ransac3 = new PlaneRANSAC(pc3);
		int estimatedTimes3 = ransac3.getNumberOfIterations(0.99, 0.1);
		System.out.println("Cloud3 estimated times: " + estimatedTimes3);

		ransac3.setEps(0.1);
		ransac3.run(estimatedTimes3, "PointCloud3_p1.xyz");
		ransac3.run(estimatedTimes3, "PointCloud3_p2.xyz");
		ransac3.run(estimatedTimes3, "PointCloud3_p3.xyz");
		pc3.save("PointCloud3_p0.xyz");
	}
}
