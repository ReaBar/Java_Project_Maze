package Commands;

import model.Model;
import view.View;

/**
 * The Class SolveCommand.
 * 
 * solve the maze maze_name using the algorithm.
 * will notify when the solution in ready.
 */
public class SolveCommand extends userCommands{

	/**
	 * Instantiates a new solve command.
	 *
	 * @param model the model
	 * @param view the view
	 */
	public SolveCommand(Model model, View view) {
		super(model, view);
	}

	/* (non-Javadoc)
	 * @see Commands.userCommands#doCommand(java.lang.String[])
	 */
	@Override
	public void doCommand(String[] args) {
		try {
			if(args[1].equals("BFS") || args[1].equals("AStarManhattan") || args[1].equals("AStarAir") || args[1].equals("null"))
				model.solveMaze(args[0],args[1]);
		}
		catch (NumberFormatException e) {
			view.stringPrinting("Error in Algorithm");
		} 
	}
}
