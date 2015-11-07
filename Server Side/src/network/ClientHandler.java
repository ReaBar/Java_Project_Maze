package network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import algorithms.mazeGenerators.Maze3d;
import model.MyModel;

/**
 * The Class ClientHandler.
 * 
 * Responsible for communication server - client
 * for each client
 * 
 * @author Rea Bar and Tom Eileen Hirsch
 */
public class ClientHandler implements Observer{

	/** The socket. */
	private Socket socket;

	/** The model. */
	private MyModel model;

	/** The output. */
	ObjectOutputStream output = null;

	/** The input. */
	ObjectInputStream input = null;

	/**
	 * Instantiates a new client handler.
	 *
	 * @param socket the socket
	 */
	public ClientHandler(Socket socket)
	{
		this.socket = socket;
	}

	/* (non-Javadoc)
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	@Override
	public void update(Observable o, Object arg) {
		try {
			output.writeObject(arg);
			output.flush();
			output.reset();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Run.
	 * 
	 * run the model for the client
	 * return 1 when finished
	 *
	 * @return int
	 */
	public int run() {
		this.model = new MyModel();
		((Observable) model).addObserver(this);
		try {
			output = new ObjectOutputStream(socket.getOutputStream());
			input = new ObjectInputStream(socket.getInputStream());

		} catch (IOException e1) {
			e1.printStackTrace();
		}
		Object obj = null;
		try {
			if((obj = input.readObject()) != null){
				if(obj instanceof String){
					model.shutDown();
				}

				else{
					ArrayList<Object> solve = (ArrayList<Object>) obj;
					Maze3d maze = (Maze3d)solve.get(0);
					String algo = (String)solve.get(1);
					model.solveMaze(maze, algo);
				}

				try{
					Thread.sleep(1000);
				}
				catch(Exception e){}
				output.close();
				input.close();
				socket.close();
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		try {
			model.shutDown();
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return 1;
	}

	/**
	 * Shut down.
	 */
	public void shutDown(){
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		model.shutDown();
		try {
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

