package view;

import java.io.IOException;

/**
 * The Interface View.
 * 
 * @author Rea Bar and Tom Eileen Hirsch
 */
public interface View {
		
	/**
	 * Start.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	void start() throws IOException;
	/**
	 * Error printing.
	 *
	 * @param stringArray the error info
	 */
	
	public void stringPrinting(String stringArray);

	/**
	 * Dir path command.
	 *
	 * @param path the path
	 */
	void dirPathCommand(String path);
	

	/**
	 * Show maze size.
	 *
	 * @param num the size
	 */
	void showMazeSize(int num);
	
	/**
	 * Show size in file.
	 *
	 * @param size the size
	 */
	void showSizeInFile(long size);
		
	/**
	 * Prints the maze.
	 *
	 * @param mazeByteArray the maze byte array
	 */
	void printMaze(byte[] mazeByteArray);
	
	/**
	 * Display cross.
	 *
	 * @param crossSectionBy the cross section by
	 */
	void DisplayCross(int[][] crossSectionBy);
	
	/**
	 * Gets the solution byte array.
	 *
	 * @param generate3dMaze the generate3d maze
	 */

	void MazeReady(Object generate3dMaze);
	
	/**
	 * Display solution.
	 *
	 * @param solution the solution
	 */
	void displaySolution(Object solution);
	
	/**
	 * Solution ready.
	 *
	 * @param name the name
	 */
	void solutionReady(Object name);
	
	/**
	 * Stop running.
	 */
	void stopRunning();
	
	/**
	 * Move character.
	 * move the character at the same cross section(right,left,backward,forward)
	 *
	 * @param arg the character
	 */
	void moveCharacter(Object arg);
	
	/**
	 * Move character y.
	 * move the character cross section up and down
	 *
	 * @param arg the character
	 */
	void moveCharacterY(Object arg);
	
	/**
	 * What kind of view the user interface.
	 *
	 * @return string that describe the view
	 */
	String whatKindOfView();
}
