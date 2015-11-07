package Commands;

import model.Model;
import view.View;

/**
 * The Class userCommands.
 */
public abstract class userCommands implements Command {

	/** The model. */
	Model model;

	/** The view. */
	View view;

	/**
	 * Instantiates a new user commands.
	 *
	 * @param model the model
	 * @param view the view
	 */
	public userCommands(Model model, View view) {
		this.model = model;
		this.view = view;
	}

	/* (non-Javadoc)
	 * @see Commands.Command#doCommand(java.lang.String[])
	 */
	@Override
	public abstract void doCommand(String[] args);
}
