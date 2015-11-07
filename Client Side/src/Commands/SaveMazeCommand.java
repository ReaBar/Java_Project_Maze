package Commands;

import java.io.IOException;

import model.Model;
import view.View;

/**
 * The Class SaveMazeCommand.
 * 
 * save the maze maze_name in a compressed File name file_name.
 */
public class SaveMazeCommand extends userCommands{

	/**
	 * Instantiates a new save maze command.
	 *
	 * @param model the model
	 * @param view the view
	 */
	public SaveMazeCommand(Model model, View view) {
		super(model, view);
	}

	/* (non-Javadoc)
	 * @see Commands.userCommands#doCommand(java.lang.String[])
	 */
	@Override
	public void doCommand(String[] args){
		try {
			model.saveCompressedMazed(args[0], args[1]);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
