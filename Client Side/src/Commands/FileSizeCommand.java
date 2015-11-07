package Commands;

import model.Model;
import view.View;

/**
 * The Class FileSizeCommand.
 * 
 * display the size of maze maze_name in file.
 */
public class FileSizeCommand extends userCommands{

	/**
	 * Instantiates a new file size command.
	 *
	 * @param model the model
	 * @param view the view
	 */
	public FileSizeCommand(Model model, View view) {
		super(model, view);
	}

	/* (non-Javadoc)
	 * @see Commands.userCommands#doCommand(java.lang.String[])
	 */
	@Override
	public void doCommand(String[] name) {
		model.showSizeInFile(name[0]);
	}
}
