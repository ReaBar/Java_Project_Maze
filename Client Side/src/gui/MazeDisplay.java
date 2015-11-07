package gui;

import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;

/**
 * The Class MazeDisplay.
 * 
 * @author Rea Bar and Tom Eileen Hirsch
 */
public abstract class MazeDisplay extends Canvas{

	/**
	 * Instantiates a new maze display.
	 *
	 * @param arg0 the Composite
	 * @param arg1 the int
	 */
	public MazeDisplay(Composite arg0, int arg1) {
		super(arg0, arg1);
	}

	/** The character. */
	Character character;

	/**
	 * Move caracter.
	 *
	 * @param arg the arg
	 */
	public abstract void moveCaracter(Object arg);

	/**
	 * Display cross section by Y.
	 *
	 * @param is the is
	 */
	public abstract void DisplayCross(int[][] is);

	/**
	 * Prints the maze to the window.
	 *
	 * @param mazeByteArray the maze byte array
	 */
	public abstract void printMaze(byte[] mazeByteArray);

	/**
	 * Display solution.
	 * Display hint to solve the maze
	 *
	 * @param solution the solution
	 */
	public abstract void displaySolution(Object solution);

	/**
	 * Gets the count step.
	 *
	 * @return the count step
	 */
	public abstract int getCountStep();

	/**
	 * Sets the count step.
	 *
	 * @param i the new count step
	 */
	public abstract void setCountStep(int i);
}
