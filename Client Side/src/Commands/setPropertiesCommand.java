package Commands;

import model.Model;
import view.View;

/**
 * The Class setPropertiesCommand.
 * 
 * set Properties
 */
public class setPropertiesCommand extends userCommands{

	/**
	 * Instantiates a new sets the properties command.
	 *
	 * @param model the model
	 * @param view the view
	 */
	public setPropertiesCommand(Model model, View view) {
		super(model, view);
	}

	/* (non-Javadoc)
	 * @see Commands.userCommands#doCommand(java.lang.String[])
	 */
	@Override
	public void doCommand(String[] args) {
		model.setProperties(args);
	}

}
