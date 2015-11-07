package Commands;

import java.io.IOException;

import model.Model;
import view.View;

/**
 * The Class MazeSizeCommand.
 * 
 * display the size of maze maze_name in memory.
 */
public class MazeSizeCommand extends userCommands{

	/**
	 * Instantiates a new maze size command.
	 *
	 * @param model the model
	 * @param view the view
	 */
	public MazeSizeCommand(Model model, View view) {
		super(model, view);
	}

	/* (non-Javadoc)
	 * @see Commands.userCommands#doCommand(java.lang.String[])
	 */
	@Override
	public void doCommand(String[] args){
		try {
			model.showMazeSize(args[0]);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
