package Commands;

import model.Model;
import view.View;

/**
 * The Class DisplaySolutionCommand.
 * display The solution moves for maze_name.
 */
public class DisplaySolutionCommand extends userCommands{

	/**
	 * Instantiates a new display solution command.
	 *
	 * @param model the model
	 * @param view the view
	 */
	public DisplaySolutionCommand(Model model, View view) {
		super(model, view);
	}

	/* (non-Javadoc)
	 * @see Commands.userCommands#doCommand(java.lang.String[])
	 */
	@Override
	public void doCommand(String[] args) {
		model.getDisplaySolution(args[0]);
	}
}
