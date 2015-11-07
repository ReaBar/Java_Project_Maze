package presenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import Commands.Command;
import Commands.DisplayCommand;
import Commands.DisplayCrossSectionBy;
import Commands.DisplaySolutionCommand;
import Commands.ExitCommand;
import Commands.FileSizeCommand;
import Commands.GenerateMaze3dCommand;
import Commands.LoadMazeCommand;
import Commands.MazeSizeCommand;
import Commands.SaveMazeCommand;
import Commands.SolveCommand;
import Commands.loadProperties;
import Commands.setPropertiesCommand;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;
import model.Model;
import view.View;

/**
 * The Class Presenter.
 * 
 * Responsible for communication model - view
 * 
 * @author Rea Bar and Tom Eileen Hirsch
 */
public class Presenter implements Observer{


	/** The hash map. */
	protected HashMap<String, Command> hm;

	/** The view. */
	protected View view;

	/** The model. */
	protected Model model;

	/**
	 * Gets the view.
	 *
	 * @return the view
	 */
	public View getView() {
		return view;
	}

	/**
	 * Sets the view.
	 *
	 * @param view the new view
	 */
	public void setView(View view) {
		this.view = view;
	}

	/**
	 * Gets the model.
	 *
	 * @return the model
	 */
	public Model getModel() {
		return model;
	}

	/**
	 * Sets the model.
	 *
	 * @param model the new model
	 */
	public void setModel(Model model) {
		this.model = model;
	}

	/**
	 * Instantiates a new my controller.
	 * Instantiates the command hash map
	 *
	 * @param view the view
	 * @param model the model
	 */
	public Presenter(View view,  Model model) {
		this.view = view;
		this.model = model;

		this.hm = new HashMap<String, Command>();
		hm.put("generate", new GenerateMaze3dCommand(model,view));
		hm.put("display", new DisplayCommand(model,view));
		hm.put("cross", new DisplayCrossSectionBy(model,view));
		hm.put("save", new SaveMazeCommand(model,view));
		hm.put("load", new LoadMazeCommand(model,view));
		hm.put("maze", new MazeSizeCommand(model,view));
		hm.put("file", new FileSizeCommand(model,view));
		hm.put("solve", new SolveCommand(model,view));
		hm.put("solution", new DisplaySolutionCommand(model,view));
		hm.put("setProperties", new setPropertiesCommand(model, view));
		hm.put("loadProperties", new loadProperties(model, view));
		hm.put("exit", new ExitCommand(model,view));
	}


	/* (non-Javadoc)
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	@Override
	public void update(Observable o, Object arg) {

		if(o == this.getView() ){
			if(arg instanceof Integer){
				//int input = view.getUserCommand(null);
				switch((int)arg){
				//up
				case 9:
					model.moveUp();
					break;
					//down
				case 3: 
					model.moveDown();
					break;
					//left 
				case 4:
					model.moveLeft();
					break;
					//right 
				case 6:
					model.moveRight();
					break;
					//forward
				case 8:
					model.moveForward();
					break;
					//backward
				case 2: 
					model.moveBackward();
					break;
				}
			}
			if(arg instanceof String){
				String[] splitLine = ((String) arg).split(" ");
				String commandName = splitLine[0];
				String[] args = null;
				if (commandName.equals("display")){
					if(splitLine.length>2)
						commandName = splitLine[1];
				}
				switch(commandName){
				// Note that this is assuming valid input
				case "generate":
					args = new String[4];
					args[0] = splitLine[3];
					String ints = splitLine[4];
					String[] xyz = ints.split(",");
					for(int i = 1;i <4;i++)
					{
						args[i] = xyz[i-1];
					}
					break;
				case "display":
					args = new String[1];
					args[0] = splitLine[1];
					break;
				case "cross":
					args = new String[3];
					for(int i=0;i<2;i++){
						args[i] = splitLine[i+4];
					}
					args[2] = splitLine[7];

					break;
				case "save":
					args = new String[2];
					for(int i=0;i<2;i++){
						args[i] = splitLine[i+2];
					}
					break;
				case "load":
					args = new String[2];
					for(int i=0;i<2;i++){
						args[i] = splitLine[i+2];
					}
					break;
				case "maze":
					args = new String[1];
					args[0] = splitLine[2];
					break;
				case "file":
					args = new String[1];
					args[0] = splitLine[2];
					break;
				case "solve":
					args = new String[2];
					for(int i=0;i<2;i++){
						args[i] = splitLine[i+1];
					}
					break;
				case "solution":
					args = new String[1];
					args[0] = splitLine[2];
					break;
				case "exit":
					args = null;
					break;
				default: 
					break;
				}

				if(hm.containsKey(commandName)){
					Command command = hm.get(commandName);
					try {
						command.doCommand(args);
					} catch (IOException e) {
						view.stringPrinting("Error in command");
					}
				}
				else
					view.stringPrinting("Wrong Command");
			}

			if(arg instanceof String[]){
				String[] args = null;
				String commandName = ((String[])arg)[0];
				switch (commandName) {
				case "setProperties":
					args = new String[6];
					for(int i=0;i<6;i++){
						args[i] = ((String[])arg)[i+1];
					}
					break;
				case "loadProperties":
					args = new String[1];
					args[0] = ((String[])arg)[1];
					break;
				default:
					break;
				}

				if(hm.containsKey(commandName)){
					Command command = hm.get(commandName);
					try {
						command.doCommand(args);
					} catch (IOException e) {
						view.stringPrinting("Error in command");
					}
				}
				else
					view.stringPrinting("Wrong Command");
			}

		}

		else if( o == this.getModel()){
			if(arg instanceof Position){
				view.moveCharacter(arg);
			}
			if(arg instanceof String){
				if (arg.equals("exit")){
					view.stopRunning();
				}
				else
					view.stringPrinting((String)arg);
			}
			else if(arg instanceof Solution<?>){
				view.displaySolution(arg);
			}
			else if(arg instanceof ArrayList<?>){
				String command = (String) ((ArrayList<?>) arg).get(0);
				switch(command){
				case "mazeSize":
					view.showMazeSize((int)((ArrayList<?>)arg).get(1));
					break;
				case "fileSize":
					view.showSizeInFile((long)((ArrayList<?>)arg).get(1));
					break;
				case "loadMaze":
					view.MazeReady(arg);
					break;
				case "solutionReady": 
					view.solutionReady(arg);
					break;
				case "move":
					view.moveCharacterY(arg);
					break;
				}
			}

			else if(arg == null){
				ArrayList<?> userData = (ArrayList<?>) model.getData();
				String command = (String) ((ArrayList<?>) userData).get(0);
				switch(command){
				case "mazeIsReady":
					view.MazeReady(userData);
					break;
				case "solutionReady": 
					view.solutionReady(userData);
					break;
				}
			}

			else if(arg instanceof byte[]){
				view.printMaze((byte[])arg);
			}

			else if(arg instanceof int[][]){
				view.DisplayCross((int[][])arg);
			}
		}
	}
}
