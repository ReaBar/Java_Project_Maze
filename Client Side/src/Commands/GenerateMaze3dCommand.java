package Commands;


import model.Model;
import view.View;

/**
 * The Class GenerateMaze3dCommand.
 * 
 * generate 3d maze maze_name {int x, int y, int z}.
 * Create a maze with the maze_name.
 * will notify when the maze in ready.
 */
public class GenerateMaze3dCommand extends userCommands {


	/**
	 * Instantiates a new generate maze3d command.
	 *
	 * @param model the model
	 * @param view the view
	 */
	public GenerateMaze3dCommand(Model model, View view) {
		super(model, view);
	}

	/* (non-Javadoc)
	 * @see Commands.userCommands#doCommand(java.lang.String[])
	 */
	@Override
	public void doCommand(String[] args) {
		try {
			Integer.parseInt(args[1]);
			Integer.parseInt(args[2]);
			Integer.parseInt(args[3]);

			model.generate3dMaze(args[0], Integer.parseInt(args[1]), Integer.parseInt(args[2]), Integer.parseInt(args[3]));

		}
		catch (NumberFormatException e) {
			view.stringPrinting("Error in Dimensions");
		} 

	}
}
