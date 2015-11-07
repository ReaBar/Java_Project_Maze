package view;

import java.net.Socket;

/**
 * The Interface ServerView.
 * 
 * @author Rea Bar and Tom Eileen Hirsch
 */
public interface ServerView {

	/**
	 * Prints the message.
	 *
	 * @param string the string
	 */
	public void printMessage(String string);

	/**
	 * Run.
	 */
	public void Run();

	/**
	 * Stop running.
	 */
	public void stopRunning();

	/**
	 * Adds the to list.
	 * add a new client connection to the list
	 * @param arg the socket
	 */
	public void addToList(Socket arg);

	/**
	 * Solutions size.
	 *
	 * @param num the number of solutions
	 */
	public void solutionsSize(int num);
}
