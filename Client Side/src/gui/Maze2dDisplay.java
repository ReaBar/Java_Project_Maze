package gui;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;

/**
 * The Class Maze2dDisplay.
 * 
 * @author Rea Bar and Tom Eileen Hirsch
 */
public class Maze2dDisplay extends MazeDisplay {

	/** The count step. */
	protected int countStep;

	/** The goal. */
	boolean goal = false;

	/** The maze data. */
	private int[][][] mazeData;

	/** The start position. */
	private Position startPosition;

	/** The maze cross. */
	private int[][] mazeCross;

	/** The goal position. */
	private Position goalPosition;

	/* (non-Javadoc)
	 * @see gui.MazeDisplay#getCountStep()
	 */
	@Override
	public int getCountStep() {
		return countStep;
	}

	/* (non-Javadoc)
	 * @see gui.MazeDisplay#setCountStep(int)
	 */
	@Override
	public void setCountStep(int countStep) {
		this.countStep = countStep;
	}

	/**
	 * Gets the maze data.
	 *
	 * @return the maze data
	 */
	public int[][][] getMazeData() {
		return mazeData;
	}

	/**
	 * Sets the maze data.
	 *
	 * @param mazeData the new maze data
	 */
	public void setMazeData(int[][][] mazeData) {
		this.mazeData = mazeData;
	}

	/**
	 * Gets the start position.
	 *
	 * @return the start position
	 */
	public Position getStartPosition() {
		return startPosition;
	}

	/**
	 * Sets the start position.
	 *
	 * @param startPosition the new start position
	 */
	public void setStartPosition(Position startPosition) {
		this.startPosition = startPosition;
	}

	/**
	 * Gets the maze cross.
	 *
	 * @return the maze cross
	 */
	public int[][] getMazeCross() {
		return mazeCross;
	}

	/**
	 * Sets the maze cross.
	 *
	 * @param mazeCross the new maze cross
	 */
	public void setMazeCross(int[][] mazeCross) {
		this.mazeCross = mazeCross;
	}

	/**
	 * Gets the goal position.
	 *
	 * @return the goal position
	 */
	public Position getGoalPosition() {
		return goalPosition;
	}

	/**
	 * Sets the goal position.
	 *
	 * @param goalPosition the new goal position
	 */
	public void setGoalPosition(Position goalPosition) {
		this.goalPosition = goalPosition;
	}

	/**
	 * Instantiates a new maze2d display.
	 *
	 * @param arg0 the Composite
	 * @param arg1 the int
	 */
	public Maze2dDisplay(Composite arg0, int arg1) {
		super(arg0, arg1);
		setBackground(new Color(null, 255, 255, 255));

		addPaintListener(new PaintListener() {

			@Override
			public void paintControl(PaintEvent e) {
				e.gc.setForeground(new Color(null,0,0,0));
				e.gc.setBackground(new Color(null,0,0,0));

				int width=getSize().x;
				int height=getSize().y;

				if(mazeCross != null){
					int w=width/mazeCross[0].length;
					int h=height/mazeCross.length;

					for(int i=0;i<mazeCross.length;i++)
						for(int j=0;j<mazeCross[i].length;j++){
							int x=j*w;
							int y=i*h;
							if(mazeCross[i][j]!=0)
								e.gc.fillRectangle(x,y,w,h);
							if(i==character.getCurrentPosition().getX() && j==character.getCurrentPosition().getZ() && character.getCurrentPosition().equals(character.getTempPosition()) && (i!=getGoalPosition().getX() || j!=getGoalPosition().getZ() || character.getCurrentPosition().getY()!=getGoalPosition().getY()))
								((MyCharacter2D) character).paintCharacter(x,y,w,h,e);
							if((mazeCross[i][j]==0 && character.getCurrentPosition().getY()==getGoalPosition().getY() && i==getGoalPosition().getX() && j==getGoalPosition().getZ() && (i!=character.getCurrentPosition().getX() || j!=character.getCurrentPosition().getZ()) && character.getCurrentPosition().equals(character.getTempPosition()))|| mazeCross[i][j]==0 && character.getTempPosition().getY()==getGoalPosition().getY() && i==getGoalPosition().getX() && j==getGoalPosition().getZ() && character.getCurrentPosition()!=character.getTempPosition())
								paintGoal(x, y, w, h, e);
							if(character.getCurrentPosition().getY()==getGoalPosition().getY() && i==getGoalPosition().getX() && j==getGoalPosition().getZ() && i==character.getCurrentPosition().getX() && j==character.getCurrentPosition().getZ() && character.getCurrentPosition().equals(character.getTempPosition())){
								paintfinish(x, y, w, h, e);
								if(!goal){
									popUpImg();
									goal = true;
								}
							}

						}
				}
			}
		});
	}

	/**
	 * Paint goal.
	 * 
	 * paint an image at the goal
	 *
	 * @param x the cell place on the X axis
	 * @param y the cell place on the Y axis
	 * @param w the cell width
	 * @param h the cell height
	 * @param e the PaintEvent
	 */
	protected void paintGoal(int x,int y,int w,int h, PaintEvent e){
		Image image = new Image(e.display, "./resources/Honey.png");
		e.gc.drawImage(image,  0, 0, image.getBounds().width,image.getBounds().height, x, y, w, h);
	}

	/**
	 * Paint finish.
	 * 
	 * paint an image while the character is at the goal 
	 *
	 * @param x the cell place on the X axis
	 * @param y the cell place on the Y axis
	 * @param w the cell width
	 * @param h the cell height
	 * @param e the PaintEvent
	 */
	protected void paintfinish(int x,int y,int w,int h, PaintEvent e){
		Image image = new Image(e.display, "./resources/poohhoney2.png");
		e.gc.drawImage(image,  0, 0, image.getBounds().width,image.getBounds().height, x, y, w, h);
	}

	/**
	 * Pop up image for solving the maze.
	 */
	protected void popUpImg() {
		final Shell dialog = new Shell(getShell(),  SWT.APPLICATION_MODAL |  SWT.DIALOG_TRIM);

		dialog.setText("Congratulations!");

		Image im = new Image(dialog.getDisplay(), "./resources/Winnie-The-Pooh-Wallpaper.jpg");

		dialog.addListener(SWT.Paint, new Listener() {

			@Override
			public void handleEvent(Event e) {
				dialog.setSize(im.getBounds().width/2, im.getBounds().height/2);
				e.gc.drawImage(im, 0, 0, im.getBounds().width, im.getBounds().height, 0, 0, im.getBounds().width/2, im.getBounds().height/2);

			}
		});

		dialog.pack();
		dialog.open();

	}

	/* (non-Javadoc)
	 * @see gui.MazeDisplay#moveCaracter(java.lang.Object)
	 */
	@Override
	public void moveCaracter(Object arg) {
		goal = false;
		character.setCurrentPosition((Position) arg);
		character.setTempPosition(character.getCurrentPosition());
		redraw();
	}

	/* (non-Javadoc)
	 * @see gui.MazeDisplay#DisplayCross(int[][])
	 */
	@Override
	public void DisplayCross(int[][] crossSectionBy) {
		setMazeCross(crossSectionBy);
		redraw();
	}

	/* (non-Javadoc)
	 * @see gui.MazeDisplay#printMaze(byte[])
	 */
	@Override
	public void printMaze(byte[] mazeByteArray) {
		countStep = 0;
		Maze3d maze = new Maze3d(mazeByteArray);
		setMazeData(maze.getMaze());
		setStartPosition(maze.getStartPosition());
		setGoalPosition(maze.getGoalPosition());
		character = new MyCharacter2D();
		character.setCurrentPosition(this.getStartPosition());
		character.setTempPosition(character.getCurrentPosition());

		DisplayCross(maze.getCrossSectionByY(getStartPosition().getY()));
		redraw();
	}

	/* (non-Javadoc)
	 * @see gui.MazeDisplay#displaySolution(java.lang.Object)
	 */
	@Override
	public void displaySolution(Object solution) {
		ArrayList<Position> tempsolution = new ArrayList<>();
		ArrayList<Position> hint = new ArrayList<>();
		String[] tempString = solution.toString().replace("[", "").replace("{", "").replace("}", "").replace(" ", "").replace("]", "").split(",");
		int[] tempIntArray = new int[tempString.length];
		for(int i=0;i<tempString.length;i++){
			tempIntArray[i] = Integer.parseInt(tempString[i]);
		}
		for(int j=0;j<tempIntArray.length-2;j+=3){
			tempsolution.add(new Position(tempIntArray[j], tempIntArray[j+1], tempIntArray[j+2]));
		}

		for(int i=0;i<tempsolution.size();i++){
			if(character.getCurrentPosition().equals(tempsolution.get(i))){
				hint.add(tempsolution.get(i));
				for(int j=i+1;j<i+6;j++)
					if(j<tempsolution.size())
						hint.add(tempsolution.get(j));
			}
		}

		if(hint.isEmpty()){
			MessageBox popMsg = new MessageBox(getShell(), SWT.OK | SWT.ICON_INFORMATION);
			popMsg.setText("hint");
			popMsg.setMessage("sorry, there is no hint for your position");
			popMsg.open();
		}
		else{
			ArrayList<String> out = new ArrayList<>();
			for(int i=0; i<hint.size()-1;i++){
				Position p = hint.get(i);
				Position next = hint.get(i+1);
				if(p.getX()!=next.getX())
					if(p.getX()>next.getX())
						out.add("forward");
					else
						out.add("backward");
				if(p.getY()!=next.getY())
					if(p.getY()>next.getY())
						out.add("down");
					else
						out.add("up");
				if(p.getZ()!=next.getZ())
					if(p.getZ()>next.getZ())
						out.add("left");
					else
						out.add("right");
			}

			MessageBox popMsg = new MessageBox(getShell(), SWT.OK | SWT.ICON_INFORMATION);
			popMsg.setText("hint");
			popMsg.setMessage(out.toString());
			popMsg.open();
		}
	}

}
