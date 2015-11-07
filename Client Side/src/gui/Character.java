package gui;

import algorithms.mazeGenerators.Position;

/**
 * The Interface Character.
 * 
 * @author Rea Bar and Tom Eileen Hirsch
 */
public interface Character {

	/**
	 * Sets the current position.
	 *
	 * @param startPosition the new current position
	 */
	void setCurrentPosition(Position startPosition);

	/**
	 * Gets the current position.
	 *
	 * @return the current position
	 */
	Position getCurrentPosition();

	/**
	 * Sets the temp position.
	 *
	 * @param currentPosition the new temp position
	 */
	void setTempPosition(Position currentPosition);

	/**
	 * Gets the temp position.
	 *
	 * @return the temp position
	 */
	Position getTempPosition();

}
