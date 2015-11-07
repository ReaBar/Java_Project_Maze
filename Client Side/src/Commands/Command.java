package Commands;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * The Interface Command.
 * 
 * @author Rea Bar and Tom Eileen Hirsch
 */
public interface Command {

	/**
	 * Do command.
	 *
	 * @param args the Arguments
	 * @throws FileNotFoundException the file not found exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	void doCommand(String[] args) throws FileNotFoundException, IOException;

}
