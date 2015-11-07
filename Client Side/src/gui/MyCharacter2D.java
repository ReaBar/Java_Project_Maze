package gui;

import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.graphics.Image;

import algorithms.mazeGenerators.Position;

/**
 * The Class MyCharacter2D.
 * 
 * @author Rea Bar and Tom Eileen Hirsch
 */
public class MyCharacter2D implements Character{

	/** The current position. */
	private Position currentPosition;

	/** The temporary position. */
	private Position tempPosition;

	/* (non-Javadoc)
	 * @see gui.Character#getCurrentPosition()
	 */
	public Position getCurrentPosition() {
		return currentPosition;
	}

	/* (non-Javadoc)
	 * @see gui.Character#setCurrentPosition(algorithms.mazeGenerators.Position)
	 */
	public void setCurrentPosition(Position currentPosition) {
		this.currentPosition = currentPosition;
	}

	/* (non-Javadoc)
	 * @see gui.Character#getTempPosition()
	 */
	public Position getTempPosition() {
		return tempPosition;
	}

	/* (non-Javadoc)
	 * @see gui.Character#setTempPosition(algorithms.mazeGenerators.Position)
	 */
	public void setTempPosition(Position tempPosition) {
		this.tempPosition = tempPosition;
	}

	/**
	 * Paint character.
	 *
	 * @param x the cell place on the X axis
	 * @param y the cell place on the Y axis
	 * @param w the cell width
	 * @param h the cell height
	 * @param e the PaintEvent
	 */
	public void paintCharacter(int x,int y,int w,int h, PaintEvent e){
		Image image = new Image(e.display, "./resources/winnie_the_pooh.png");
		e.gc.drawImage(image,  0, 0, image.getBounds().width,image.getBounds().height, x, y, w, h);

	}

}
