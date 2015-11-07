package view;

import java.net.Socket;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Scanner;

/**
 * The Class ServerCLI.
 * 
 * Server CLI UI
 * 
 * @author Rea Bar and Tom Eileen Hirsch
 */
public class ServerCLI extends Observable implements ServerView{

	/** The commands. */
	String[] commands = {"start","start port numOfClients","stop"};

	/** The server running. */
	private boolean serverRunning = false;

	/**
	 * Instantiates a new server CLI.
	 */
	public ServerCLI() {
	}

	/* (non-Javadoc)
	 * @see view.ServerView#Run()
	 */
	public void Run(){
		System.out.println("Server side");
		String action = "";
		for(String command : commands){
			System.out.println(command);
		}
		Scanner scanner = new Scanner(System.in);
		do
		{
			System.out.print("Enter command: ");
			action = scanner.nextLine();		
			this.Commands(action);
		} while (!(action.equals("stop")));
		scanner.close();
	}

	/* (non-Javadoc)
	 * @see view.ServerView#printMessage(java.lang.String)
	 */
	public void printMessage(String string){
		//System.out.println(string);
	}

	/**
	 * Commands.
	 *
	 * @param action the action
	 */
	public void Commands(String action){
		String[] command = action.split(" ");
		ArrayList<Object> commandsArray = new ArrayList<Object>();
		if(command[0].equals("start") && serverRunning == false){
			if(command.length == 1){
				commandsArray.add("start");
				serverRunning = true;
			}
			else{
				commandsArray.add("start");
				commandsArray.add(command[1]);
				commandsArray.add(command[2]);
				serverRunning = true;
			}
		}
		else if(command[0].equals("stop")){
			commandsArray.add("stop");
		}
		if(commandsArray.size()!=0){
			setChanged();
			notifyObservers(commandsArray);
		}
	}

	/* (non-Javadoc)
	 * @see view.ServerView#stopRunning()
	 */
	@Override
	public void stopRunning() {
		System.out.println("exited succefully");
	}

	/* (non-Javadoc)
	 * @see view.ServerView#addToList(java.net.Socket)
	 */
	@Override
	public void addToList(Socket arg) {}

	/* (non-Javadoc)
	 * @see view.ServerView#solutionsSize(int)
	 */
	@Override
	public void solutionsSize(int num) {}
}
