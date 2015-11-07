package gui;


import java.io.IOException;
import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseWheelListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import algorithms.mazeGenerators.Position;
import view.View;

/**
 * The Class MazeWindow.
 * (MyViewGui) extends BasicWindow and implements View
 * UI - GUI window
 * 
 * @author Rea Bar and Tom Eileen Hirsch
 */
//Buttons
public class MazeWindow extends BasicWindow implements View{

	/** The text. */
	Text text;

	/** The maze display. */
	protected MazeDisplay mazeDisplay;

	/** The view. */
	public boolean view;

	/**
	 * Gets the maze display.
	 *
	 * @return the maze display
	 */
	public MazeDisplay getMazeDisplay() {
		return mazeDisplay;
	}

	/**
	 * Sets the maze display.
	 *
	 * @param mazeDisplay the new maze display
	 */
	public void setMazeDisplay(MazeDisplay mazeDisplay) {
		this.mazeDisplay = mazeDisplay;
	}

	/** The result. */
	private String result = null;

	/** The maze name. */
	private String mazeName = null;

	/**
	 * Instantiates a new maze window.
	 *
	 * @param title the title
	 * @param width the width
	 * @param height the height
	 */
	public MazeWindow(String title, int width, int height) {
		super(title, width, height);
	}

	/* (non-Javadoc)
	 * @see gui.BasicWindow#initWidgets()
	 */
	@Override
	void initWidgets() {

		shell.setLayout(new GridLayout(2, false));

		Menu menuBar = new Menu(shell, SWT.BAR);

		MenuItem propertiesMenuHeader = new MenuItem(menuBar, SWT.CASCADE);
		propertiesMenuHeader.setText("&Open Properties");

		Menu fileMenu2 = new Menu(shell, SWT.DROP_DOWN);
		propertiesMenuHeader.setMenu(fileMenu2);

		MenuItem newProperties = new MenuItem(fileMenu2, SWT.PUSH);
		newProperties.setText("new Properties");

		MenuItem loadProperties = new MenuItem(fileMenu2, SWT.PUSH);
		loadProperties.setText("load Properties");

		MenuItem fileMenuHeader = new MenuItem(menuBar, SWT.CASCADE);
		fileMenuHeader.setText("&Maze Option");

		Menu fileMenu = new Menu(shell, SWT.DROP_DOWN);
		fileMenuHeader.setMenu(fileMenu);

		MenuItem solveMaze = new MenuItem(fileMenu, SWT.CASCADE);
		solveMaze.setText("&Solve");

		Menu submenu = new Menu(shell, SWT.DROP_DOWN);
		solveMaze.setMenu(submenu);

		MenuItem bfs = new MenuItem(submenu, SWT.PUSH);
		bfs.setText("&BFS");
		MenuItem AstarAir = new MenuItem(submenu, SWT.PUSH);
		AstarAir.setText("&A* Air");
		MenuItem AstarManhattan = new MenuItem(submenu, SWT.PUSH);
		AstarManhattan.setText("&A* Manhattan");

		shell.addListener(SWT.CLOSE, new Listener() {	
			@Override
			public void handleEvent(Event arg0) {
				shell.close();
				display.close();
			}
		});

		newProperties.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent e) {
				String[] properties = new String[7];
				final Shell dialog =
						new Shell(shell, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
				dialog.setLayout(new GridLayout(1, false));
				Composite composite = new Composite(dialog, SWT.NULL);
				Composite composite2 = new Composite(dialog, SWT.NULL);
				Composite composite3 = new Composite(dialog, SWT.NULL);
				Composite composite4 = new Composite(dialog, SWT.NULL);
				Composite composite5 = new Composite(dialog, SWT.NULL);
				Composite composite6 = new Composite(dialog, SWT.NULL);

				GridLayout gridLayout = new GridLayout();
				gridLayout.numColumns = 2;
				composite.setLayout(gridLayout);	
				GridLayout gridLayout2 = new GridLayout();
				gridLayout2.numColumns = 3;
				composite2.setLayout(gridLayout2);

				composite4.setLayout(gridLayout2);
				GridLayout gridLayout3 = new GridLayout();
				gridLayout3.numColumns = 4;
				composite3.setLayout(gridLayout3);

				composite5.setLayout(gridLayout);
				composite6.setLayout(gridLayout);
				new Label(composite, SWT.LEFT).setText("Number of Threads:");
				Text numOfThreads = new Text(composite, SWT.BORDER);

				new Label(composite2, SWT.LEFT).setText("Generate Algorithm: ");
				final String[] generateAlgorithms =
						new String[] {"DFS","Simple"};

				final Button[] generateAlgoRadioButtons = new Button[generateAlgorithms.length];
				for(int i = 0; i < generateAlgorithms.length;i++){
					generateAlgoRadioButtons[i] = new Button(composite2, SWT.RADIO);
					generateAlgoRadioButtons[i].setText(generateAlgorithms[i]);
				}

				new Label(composite3, SWT.LEFT).setText("Solve Algorithm: ");
				final String[] algorithms =
						new String[] {"BFS","A* Air", "A* Manhattan"};

				final Button[] algoRadioButtons = new Button[algorithms.length];
				for(int i = 0; i < algorithms.length;i++){
					algoRadioButtons[i] = new Button(composite3, SWT.RADIO);
					algoRadioButtons[i].setText(algorithms[i]);
				}

				new Label(composite4, SWT.LEFT).setText("UI: ");
				final String[] ui =
						new String[] {"Gui","CLI"};

				final Button[] radios = new Button[ui.length];
				for (int i = 0; i < ui.length; i++) {
					radios[i] = new Button(composite4, SWT.RADIO);
					radios[i].setText(ui[i]);
				}

				new Label(composite5,SWT.LEFT).setText("Server IP:");
				Text ipText = new Text(composite5, SWT.BORDER);
				new Label(composite5,SWT.LEFT).setText("Server Port:");
				Text portText = new Text(composite5, SWT.BORDER);

				Button setButton = new Button(dialog, SWT.PUSH);
				setButton.setText("Set");
				setButton.addSelectionListener(new SelectionListener() {
					public void widgetDefaultSelected (SelectionEvent e) {}
					@Override
					public void widgetSelected(SelectionEvent arg0) {
						properties[0] = "setProperties";
						properties[1] = numOfThreads.getText();

						if(generateAlgoRadioButtons[0].getSelection()){
							properties[2] = "DFS";
						}

						if(generateAlgoRadioButtons[1].getSelection()){
							properties[2] = "Simple";
						}

						if(algoRadioButtons[0].getSelection()){
							properties[3] = "BFS";
						}

						if(algoRadioButtons[1].getSelection()){
							properties[3] = "A* Air";
						}

						if(algoRadioButtons[2].getSelection()){
							properties[3] = "A* Manhattan";
						}

						if(radios[0].getSelection()){
							properties[4] = "GUI";
							view=false;
						}

						if(radios[1].getSelection()){
							properties[4] = "CLI";
							view=true;
						}
						
						properties[5] = ipText.getText();
						if(properties[5].equals("localhost")){
							properties[5] = new String("127.0.0.1");
						}
						properties[6] = portText.getText();


						dialog.close();
						setChanged();
						notifyObservers(properties);		
					}
				});

				dialog.pack();
				dialog.open();

				// Move the dialog to the center of the top level shell.
				Rectangle shellBounds = shell.getBounds();
				Point dialogSize = dialog.getSize();

				dialog.setLocation(
						shellBounds.x + (shellBounds.width - dialogSize.x) / 2,
						shellBounds.y + (shellBounds.height - dialogSize.y) / 2);
			}

			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});

		loadProperties.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				FileDialog fd=new FileDialog(shell,SWT.OPEN); //opens a dialog box in which we can select a xml file and load it
				fd.setText("open");
				fd.setFilterPath("C:\\");
				String[] filterExt = { "*.xml"};
				fd.setFilterExtensions(filterExt);
				String fileName=fd.open(); //choose the file
				if(fileName!=null){
					String[] loadProperties = new String[2];
					loadProperties[0] = "loadProperties";
					loadProperties[1] = fileName;
					setChanged();
					notifyObservers(loadProperties);
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {}
		});

		MenuItem exitMenuHeader = new MenuItem(menuBar, SWT.PUSH);
		exitMenuHeader.setText("&Exit");

		exitMenuHeader.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				setChanged();
				notifyObservers("exit");
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {}
		});

		bfs.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				if(mazeName!=null){
					result = "solve "+mazeName +" BFS";

					setChanged();
					notifyObservers(result);
				}
				else
					stringPrinting("no maze to solve");
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {}
		});

		AstarAir.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				if(mazeName!=null){
					result = "solve "+mazeName +" AStarAir";

					setChanged();
					notifyObservers(result);
				}
				else
					stringPrinting("no maze to solve");
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {}
		});

		AstarManhattan.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				if(mazeName!=null){
					result = "solve "+mazeName +" AStarManhattan";

					setChanged();
					notifyObservers(result);
				}
				else
					stringPrinting("no maze to solve");
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {}
		});

		shell.setMenuBar(menuBar);

		Composite c = new Composite(shell, SWT.NONE);
		c.setLayout(new GridLayout(1, false));

		Group group1 = new Group(c, SWT.NONE);
		Group group3 = new Group(c, SWT.SHADOW_OUT);
		Group group2 = new Group(c, SWT.SHADOW_OUT);
		Group group4 = new Group(c, SWT.SHADOW_OUT);

		group1.setText("Generate new maze");
		group1.setLayout(new GridLayout(2, false)); 

		Label lblName = new Label(group1, SWT.NONE);
		lblName.setText("Maze name:");
		Text txtName = new Text(group1, SWT.BORDER);
		txtName.setLayoutData(new GridData(GridData.BEGINNING, GridData.CENTER, false, false));

		Label lblParams = new Label(group1, SWT.NONE);
		lblParams.setText("Maze Dimensions:");
		Text txtParams = new Text(group1, SWT.BORDER);
		txtParams.setLayoutData(new GridData(GridData.BEGINNING, GridData.CENTER, false, false));

		Button button = new Button(group1, SWT.PUSH);
		button.setSize(200, 30);
		button.setText("Generate 3D Maze");

		button.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {}

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				try{
					result ="generate 3d maze "+ txtName.getText()+" "+ txtParams.getText();
					mazeName = txtName.getText();

					setChanged();
					notifyObservers(result);
				}catch(ArrayIndexOutOfBoundsException e){
					stringPrinting("You need to give me your maze name and params for this action");
				}
				catch (NullPointerException e) {
					stringPrinting("please open properties first");
				}

			}
		});

		group3.setText("Display and Solve");
		group3.setLayout(new GridLayout(2, true));

		Label lbldisplay = new Label(group3, SWT.NONE);
		lbldisplay.setText("Maze name:");
		Text txtdisplay = new Text(group3, SWT.BORDER);
		txtdisplay.setLayoutData(new GridData(GridData.BEGINNING, GridData.CENTER, false, false));

		Button displayMaze = new Button(group3, SWT.PUSH);
		displayMaze.setSize(200, 30);
		displayMaze.setText("Display maze");

		Button displayHint = new Button(group3, SWT.PUSH);
		displayHint.setSize(200, 30);
		displayHint.setText("Display hint");

		Button displayCrossUP = new Button(group3, SWT.PUSH);
		displayCrossUP.setSize(200, 30);
		displayCrossUP.setText("Display up section");

		Button displayCrossDOWM = new Button(group3, SWT.PUSH);
		displayCrossDOWM.setSize(200, 30);
		displayCrossDOWM.setText("Display down section");

		Button displayCrossCurrent = new Button(group3, SWT.PUSH);
		displayCrossCurrent.setSize(200, 30);
		displayCrossCurrent.setText("Display current section");

		Button solve = new Button(group3, SWT.PUSH);
		solve.setSize(200, 30);
		solve.setText("Solve Maze");

		displayMaze.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				try{
					result = "display "+txtdisplay.getText();

					setChanged();
					notifyObservers(result);
				}catch(Exception e){
					stringPrinting("You need to give me a name for this action");
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {}
		});

		displayHint.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent arg0) {

				try{
					result = "display solution "+ txtdisplay.getText();

					setChanged();
					notifyObservers(result);
				}catch(Exception e){
					stringPrinting("You need to give me a name for this action");
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {}
		});

		displayCrossUP.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				try{
					int u = ((Position) mazeDisplay.character.getCurrentPosition()).getY();
					int up = u+1;

					result = "display cross section by Y "+ up +" for "+ txtdisplay.getText();
					mazeDisplay.character.setTempPosition(new Position(((Position) mazeDisplay.character.getCurrentPosition()).getX(), ((Position) mazeDisplay.character.getCurrentPosition()).getY()+1, ((Position) mazeDisplay.character.getCurrentPosition()).getZ()));
					setChanged();
					notifyObservers(result);

				}catch(Exception e){
					stringPrinting("You need to give me a name for this action");
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {}
		});


		displayCrossDOWM.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				try{
					int u = ((Position) mazeDisplay.character.getCurrentPosition()).getY();
					int ul = u-1;
					result = "display cross section by Y "+ ul +" for "+ txtdisplay.getText();
					mazeDisplay.character.setTempPosition(new Position(((Position) mazeDisplay.character.getCurrentPosition()).getX(), ((Position) mazeDisplay.character.getCurrentPosition()).getY()-1, ((Position) mazeDisplay.character.getCurrentPosition()).getZ()));
					setChanged();
					notifyObservers(result);

				}catch(Exception e){
					stringPrinting("You need to give me a name for this action");
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {}
		});

		displayCrossCurrent.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				try{
					result = "display cross section by Y "+ ((Position) mazeDisplay.character.getCurrentPosition()).getY() +" for "+ txtdisplay.getText();
					mazeDisplay.character.setTempPosition((Position) mazeDisplay.character.getCurrentPosition());
					setChanged();
					notifyObservers(result);
				}catch(Exception e){
					stringPrinting("You need to give me a name for this action");
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {}
		});

		solve.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				try{
					result = "solve " +txtdisplay.getText() + " null";

					setChanged();
					notifyObservers(result);
				}catch(ArrayIndexOutOfBoundsException e){
					stringPrinting("You need to give me a name for this action");
				}
				catch (NullPointerException e) {
					stringPrinting("the server is closed");
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {}
		});

		group2.setText("File");
		group2.setLayout(new GridLayout(2, false));

		Label lblMazeName = new Label(group2, SWT.NONE);
		lblMazeName.setText("Maze name:");
		Text txtMazeName = new Text(group2, SWT.BORDER);
		txtMazeName.setLayoutData(new GridData(GridData.BEGINNING, GridData.CENTER, false, false));

		Label lblFileName = new Label(group2, SWT.NONE);
		lblFileName.setText("File name:");
		Text txtFileName = new Text(group2, SWT.BORDER);
		txtFileName.setLayoutData(new GridData(GridData.BEGINNING, GridData.CENTER, false, false));

		Button saveMaze = new Button(group2, SWT.PUSH);
		saveMaze.setSize(200, 30);
		saveMaze.setText("save maze");

		Button loadMaze = new Button(group2, SWT.PUSH);
		loadMaze.setSize(200, 30);
		loadMaze.setText("load maze");

		saveMaze.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				try{
					result = "save maze "+txtMazeName.getText()+" "+txtFileName.getText();

					setChanged();
					notifyObservers(result);
				}catch(Exception e){
					stringPrinting("You need to give me a maze name and a file name for this action");
				}

			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {}
		});

		loadMaze.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				try{
					result = "load maze "+txtFileName.getText()+" "+txtMazeName.getText();

					setChanged();
					notifyObservers(result);
				}catch(Exception e){
					stringPrinting("You need to give me a maze name and a file name for this action");
				}

			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {}
		});

		group4.setText("Size");
		group4.setLayout(new GridLayout(2, false)); 

		Label lblMazeSize = new Label(group4, SWT.NONE);
		lblMazeSize.setText("Maze name:");
		Text txtMazeSize = new Text(group4, SWT.BORDER);
		txtMazeSize.setLayoutData(new GridData(GridData.BEGINNING, GridData.CENTER, false, false));

		Button MazeSize = new Button(group4, SWT.PUSH);
		MazeSize.setSize(200, 30);
		MazeSize.setText("in memory");

		Button FileSize = new Button(group4, SWT.PUSH);
		FileSize.setSize(200, 30);
		FileSize.setText("in file");

		MazeSize.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				try{
					result = "maze size "+ txtMazeSize.getText();

					setChanged();
					notifyObservers(result);
				}catch(Exception e){
					stringPrinting("You need to give me a name for this action");
				}

			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {}
		});

		FileSize.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				try{
					result = "file size "+ txtMazeSize.getText();

					setChanged();
					notifyObservers(result);
				}catch(Exception e){
					stringPrinting("You need to give me a name for this action");
				}

			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {}
		});

		mazeDisplay = new Maze2dDisplay(shell, SWT.BORDER);
		setMazeDisplay(mazeDisplay);
		mazeDisplay.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true,1,1));

		mazeDisplay.addKeyListener(new KeyListener() {

			@Override
			public void keyReleased(KeyEvent arg0) {}

			@Override
			public void keyPressed(KeyEvent arg0) {

				if(arg0.keyCode == 120){
					setChanged();
					notifyObservers("exit");
				}

				if(arg0.keyCode == SWT.KEYPAD_9 || arg0.keyCode == SWT.PAGE_UP){
					setChanged();
					notifyObservers(9);
				}
				if(arg0.keyCode == SWT.KEYPAD_3 || arg0.keyCode == SWT.PAGE_DOWN){
					setChanged();
					notifyObservers(3);
				}
				if(arg0.keyCode == SWT.KEYPAD_4 || arg0.keyCode == SWT.ARROW_LEFT){
					setChanged();
					notifyObservers(4);
				}
				if(arg0.keyCode == SWT.KEYPAD_6  || arg0.keyCode == SWT.ARROW_RIGHT){
					setChanged();
					notifyObservers(6);
				}
				if(arg0.keyCode == SWT.KEYPAD_8 || arg0.keyCode == SWT.ARROW_UP){
					setChanged();
					notifyObservers(8);
				}
				if(arg0.keyCode == SWT.KEYPAD_2 || arg0.keyCode == SWT.ARROW_DOWN){
					setChanged();
					notifyObservers(2);
				}
			}
		});

		//Bonus!!
		mazeDisplay.addMouseWheelListener(new MouseWheelListener(){
			@Override
			public void mouseScrolled(MouseEvent arg0) {
				if((arg0.stateMask& SWT.CTRL)!=0){ //if control is pressed
					if(arg0.count>0){ //and we scroll up
						//up zoom in										
						mazeDisplay.setSize(mazeDisplay.getSize().x+30, mazeDisplay.getSize().y+30);
					}
					if(arg0.count<0){ //and we scroll down
						mazeDisplay.setSize(mazeDisplay.getSize().x-30, mazeDisplay.getSize().y-30);
						//down zoom out	
					}
				}	
			}
		});
	}

	/* (non-Javadoc)
	 * @see view.View#start()
	 */
	@Override
	public void start() throws IOException {
		this.run();

	}

	/* (non-Javadoc)
	 * @see view.View#stringPrinting(java.lang.String)
	 */
	@Override
	public void stringPrinting(String stringArray) {
		MessageBox popMsg = new MessageBox(shell, SWT.OK | SWT.ICON_ERROR);
		popMsg.setText("info!");
		popMsg.setMessage(stringArray);
		popMsg.open();

	}

	/* (non-Javadoc)
	 * @see view.View#dirPathCommand(java.lang.String)
	 */
	@Override
	public void dirPathCommand(String path) {}

	/* (non-Javadoc)
	 * @see view.View#showMazeSize(int)
	 */
	@Override
	public void showMazeSize(int num) {
		MessageBox popMsg = new MessageBox(shell, SWT.OK | SWT.ICON_INFORMATION);
		popMsg.setText("info!");
		popMsg.setMessage("maze size in memory: "+num);
		popMsg.open();

	}

	/* (non-Javadoc)
	 * @see view.View#showSizeInFile(long)
	 */
	@Override
	public void showSizeInFile(long size) {
		MessageBox popMsg = new MessageBox(shell, SWT.OK | SWT.ICON_INFORMATION);
		popMsg.setText("info!");
		popMsg.setMessage("maze size in file: "+size);
		popMsg.open();

	}

	/* (non-Javadoc)
	 * @see view.View#printMaze(byte[])
	 */
	@Override
	public void printMaze(byte[] mazeByteArray) {
		mazeDisplay.printMaze(mazeByteArray);

	}

	/* (non-Javadoc)
	 * @see view.View#DisplayCross(int[][])
	 */
	@Override
	public void DisplayCross(int[][] crossSectionBy) {
		mazeDisplay.DisplayCross(crossSectionBy);

	}

	/* (non-Javadoc)
	 * @see view.View#MazeReady(java.lang.Object)
	 */
	@Override
	public void MazeReady(Object generate3dMaze) {
		MessageBox popMsg = new MessageBox(shell, SWT.OK | SWT.ICON_INFORMATION);
		popMsg.setText("info!");
		popMsg.setMessage("maze <"+ ((ArrayList<?>)generate3dMaze).get(1) +"> is ready");
		popMsg.open();
	}

	/* (non-Javadoc)
	 * @see view.View#displaySolution(java.lang.Object)
	 */
	@Override
	public void displaySolution(Object solution) {
		mazeDisplay.displaySolution(solution);
	}

	/* (non-Javadoc)
	 * @see view.View#solutionReady(java.lang.Object)
	 */
	@Override
	public void solutionReady(Object name) {
		MessageBox popMsg = new MessageBox(shell, SWT.ICON_INFORMATION);
		popMsg.setText("info!");
		popMsg.setMessage("solution for <"+((ArrayList<?>)name).get(1)+"> is ready");
		popMsg.open();
	}

	/* (non-Javadoc)
	 * @see view.View#stopRunning()
	 */
	@Override
	public void stopRunning() {
		MessageBox popMsg = new MessageBox(shell, SWT.ICON_INFORMATION);
		popMsg.setText("info!");
		popMsg.setMessage("A successful exit");
		popMsg.open();

		shell.close();
		display.close();
	}

	/* (non-Javadoc)
	 * @see view.View#moveCaracter(java.lang.Object)
	 */
	@Override
	public void moveCharacter(Object arg) {
		mazeDisplay.setCountStep(mazeDisplay.getCountStep()+1);
		mazeDisplay.moveCaracter(arg);
	}

	/* (non-Javadoc)
	 * @see view.View#moveCaracterY(java.lang.Object)
	 */
	@Override
	public void moveCharacterY(Object arg) {
		mazeDisplay.setCountStep(mazeDisplay.getCountStep()+1);
		mazeDisplay.character.setCurrentPosition((Position) ((ArrayList<?>) arg).get(1));
		mazeDisplay.character.setTempPosition(mazeDisplay.character.getCurrentPosition());
		mazeDisplay.DisplayCross((int[][]) ((ArrayList<?>) arg).get(2));		

	}

	/* (non-Javadoc)
	 * @see view.View#whatKindOfView()
	 */
	@Override
	public String whatKindOfView() {
		if(this.view == true)
			return "CLI";
		return "GUI";
	}

}
