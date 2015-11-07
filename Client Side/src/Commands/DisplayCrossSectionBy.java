package Commands;

import model.Model;
import view.View;

/**
 * The Class DisplayCrossSectionBy.
 * 
 * display cross section by {X/Y/Z} index for maze_name.
 * display the 2-dimensional maze for the right axis, for the right index, of the maze maze_name.
 */
public class DisplayCrossSectionBy extends userCommands{

	/**
	 * Instantiates a new display cross section by.
	 *
	 * @param model the model
	 * @param view the view
	 */
	public DisplayCrossSectionBy(Model model, View view) {
		super(model, view);
	}

	/* (non-Javadoc)
	 * @see Commands.userCommands#doCommand(java.lang.String[])
	 */
	@Override
	public void doCommand(String[] args) {
		try {
			Integer.parseInt(args[1]);
			model.getCrossSection(args[0], Integer.parseInt(args[1]) ,args[2]);
		}
		catch (NumberFormatException e) {
			view.stringPrinting("Error in index");
		} 
	}
}
