/**
 * Student name: Chentao Jin
 * Student ID: 300220076
 */

package Project_Part1;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class PointCloud {
	/**
	 * ArrayList to store all the points
	 */
	private ArrayList<Point3D> cloud;

	/**
	 * File to read points
	 */
	private String fileName;

	/**
	 * @param filename Read points from file and store to clouds
	 */
	public PointCloud(String filename) {
		this.fileName = "./src/Project_Part1/" + filename;
		float x, y, z;
		cloud = new ArrayList<Point3D>();

		try {
			File file = new File(fileName);
			Scanner myReader = new Scanner(file);
			myReader.nextLine();
			while (myReader.hasNextLine() && myReader.hasNext()) {
				x = Float.valueOf(myReader.next());
				y = Float.valueOf(myReader.next());
				z = Float.valueOf(myReader.next());
				this.addPoint(new Point3D(x, y, z));
			}
			myReader.close();

		} catch (FileNotFoundException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
	}

	/**
	 * Empty cloud
	 */
	public PointCloud(){
		this.cloud= new ArrayList<Point3D>();
	}

	/**
	 * @param pt Point to be added Add point to this cloud
	 */
	public void addPoint(Point3D pt) {
		cloud.add(pt);
	}

	/**
	 * @return a random point from this cloud
	 */
	public Point3D getPoint() {
		int index = (int) (Math.random() * cloud.size());
		return cloud.get(index);
	}

	/**
	 * @param filename Write remaining points in the cloud to file
	 */
	public void save(String filename) {
		try {
			FileWriter myWriter = new FileWriter(filename);
			System.out.println("Start writing to file: " + filename + " ...");
			myWriter.write("x\t\ty\t\tz\n");
			Iterator<Point3D> it = this.iterator();
			System.out.println("Cloud support size: " + this.cloudSize() + "\n");

			while (it.hasNext()) {
				myWriter.write(it.next().toString() + "\n");
			}

			myWriter.close();
		} catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
	}

	/**
	 * @return Point array list iterator
	 */
	public Iterator<Point3D> iterator() {
		Iterator<Point3D> iterator = cloud.iterator();
		return iterator;
	}

	/**
	 * @return Cloud point number Update cloud point information and display in
	 *         console for better monitor
	 */
	public int cloudSize() {
		return this.cloud.size();
	}

}
