package Commands;

import java.io.IOException;

import model.Model;
import view.View;

/**
 * The Class LoadMazeCommand.
 * 
 * load a new maze from the File file_name and save it with the name maze_name.
 */
public class LoadMazeCommand extends userCommands{

	/**
	 * Instantiates a new load maze command.
	 *
	 * @param model the model
	 * @param view the view
	 */
	public LoadMazeCommand(Model model, View view) {
		super(model, view);
	}

	/* (non-Javadoc)
	 * @see Commands.userCommands#doCommand(java.lang.String[])
	 */
	@Override
	public void doCommand(String[] args){
		try {
			model.loadMaze(args[0], args[1]);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
