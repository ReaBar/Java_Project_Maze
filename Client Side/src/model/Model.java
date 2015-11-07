package model;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * The Interface Model.
 *
 * @author Rea Bar and Tom Eileen Hirsch
 */
public interface Model {
	
	/**
	 * Generate3d maze.
	 *
	 * @param args the arguments.
	 * @param x the row
	 * @param y the height
	 * @param z the column
	 */
	void generate3dMaze(String args, int x, int y,int z);

	/**
	 * Save compressed mazed.
	 *
	 * @param name the maze name
	 * @param fileName the File name
	 * @throws FileNotFoundException the file not found exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	void saveCompressedMazed(String name, String fileName) throws FileNotFoundException, IOException;

	/**
	 * Load maze.
	 *
	 * @param name the maze name
	 * @param fileName the File name
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	void loadMaze(String name, String fileName) throws IOException;

	/**
	 * Solve maze.
	 *
	 * @param name the maze name
	 * @param algorithm the algorithm
	 */
	void solveMaze(String name, String algorithm);

	/**
	 * Ask solution.
	 *
	 * @param string the maze name
	 */
	void getDisplaySolution(String string);

	/**
	 * Show maze size.
	 *
	 * @param name the maze name
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	void showMazeSize(String name) throws IOException;

	/**
	 * Ask for maze.
	 *
	 * @param name the maze name
	 */
	void askForMaze(String name);

	/**
	 * Gets the cross section.
	 *
	 * @param axis the axis
	 * @param parseInt the index
	 * @param name the maze name
	 * return the cross section
	 */
	void getCrossSection(String axis,int parseInt, String name);

	/**
	 * Show size in file.
	 *
	 * @param properties the new properties
	 */
	
	void setProperties(String[] properties);
	
	/**
	 * Load properties.
	 *
	 * @param fileName the file name
	 */
	public void loadProperties(String fileName);
	
	/**
	 * Load properties.
	 */
	public void loadProperties();
	
	/**
	 * Show size in file.
	 *
	 * @param string the string
	 */
	void showSizeInFile(String string);

	/**
	 * Move up.
	 */
	void moveUp();

	/**
	 * Move down.
	 */
	void moveDown();

	/**
	 * Move left.
	 */
	void moveLeft();

	/**
	 * Move right.
	 */
	void moveRight();

	/**
	 * Move forward.
	 */
	void moveForward();

	/**
	 * Move backward.
	 */
	void moveBackward();

	/**
	 * Gets the data.
	 *
	 * @return the data
	 */
	Object getData();

	/**
	 * Shut down.
	 */
	void shutDown();

}
