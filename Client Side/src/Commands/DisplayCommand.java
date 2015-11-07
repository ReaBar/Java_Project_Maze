package Commands;

import model.Model;
import view.View;

/**
 * The Class DisplayCommand.
 * 
 * display the maze
 */
public class DisplayCommand extends userCommands{

	/**
	 * Instantiates a new display command.
	 *
	 * @param model the model
	 * @param view the view
	 */
	public DisplayCommand(Model model, View view) {
		super(model, view);
	}

	/* (non-Javadoc)
	 * @see Commands.userCommands#doCommand(java.lang.String[])
	 */
	@Override
	public void doCommand(String[] name) {
		model.askForMaze(name[0]);
	}
}
