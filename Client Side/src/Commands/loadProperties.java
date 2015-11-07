package Commands;

import model.Model;
import view.View;

/**
 * The Class loadProperties.
 * 
 * load Properties from xml
 */
public class loadProperties extends userCommands {

	/**
	 * Instantiates a new load properties.
	 *
	 * @param model the model
	 * @param view the view
	 */
	public loadProperties(Model model, View view) {
		super(model, view);
	}

	/* (non-Javadoc)
	 * @see Commands.userCommands#doCommand(java.lang.String[])
	 */
	@Override
	public void doCommand(String[] args) {
		model.loadProperties(args[0]);
	}

}
