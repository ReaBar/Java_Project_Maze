package Commands;

import model.Model;
import view.View;

/**
 * The Class ExitCommand.
 * 
 * exit from the program
 */
public class ExitCommand extends userCommands implements Command {

	/**
	 * Instantiates a new exit command.
	 *
	 * @param model the model
	 * @param view the view
	 */
	public ExitCommand(Model model, View view) {
		super(model, view);
	}

	/* (non-Javadoc)
	 * @see Commands.userCommands#doCommand(java.lang.String[])
	 */
	@Override
	public void doCommand(String[] args) {
		model.shutDown();
	}
}
