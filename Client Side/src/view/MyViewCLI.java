package view;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Observable;


/**
 * The Class MyViewCLI.
 * 
 * extends Observable and implements View and Runnable
 * 
 * @author Rea Bar and Tom Eileen Hirsch
 */
public class MyViewCLI extends Observable implements View, Runnable {

	/** The running. */
	private volatile boolean running = true;

	/** The out - PrintWriter. */
	private PrintWriter out;

	/** The in - BufferedReader. */
	private BufferedReader in;

	/** The commands. */
	private String[] commands = new String[]{"dir <path>", "generate 3d maze <name> <x,y,z>","display <name>", 
			"display cross section by {X,Y,Z} <index> for <name>", "save maze <name> <filename>", 
			"load maze <file name> <name>", "maze size <name>", "file size <name>", "solve <name> <algorithm>", 
			"display solution <name>", "exit"};

	/**
	 * Instantiates a new my view CLI.
	 */
	public MyViewCLI() {
		System.out.println("The commands:");
		for(String s : commands)
			out.println(s);
	}

	/**
	 * Instantiates a new my view CLI.
	 *
	 * @param out the PrintWriter
	 * @param in the BufferedReader
	 */
	public MyViewCLI(PrintWriter out ,BufferedReader in) {
		this.out = out;
		this.in = in;
		out.println("The commands:");
		for(String s : commands)
			out.println(s);
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	public void run() {
		try
		{
			String commandName = null;

			String line;
			while (running)
			{
				out.println("Enter command: ");
				line = in.readLine();
				String[] splitLine = line.split(" ");
				commandName = splitLine[0];
				switch(commandName){
				// Note that this is assuming valid input
				case "dir":
					this.dirPathCommand(splitLine[1]);
					break;
				default: 
					this.setChanged();
					this.notifyObservers(line); 
					break;
				}
			}
			out.println("A successful exit from the program");
		} 
		catch (IOException e) 
		{			
			e.printStackTrace();
		} 
		finally 
		{
			try 
			{
				in.close();
				out.close();
			} 
			catch (IOException e) 
			{				
				e.printStackTrace();
			}				
		}

	}
	/**
	 * Stop running.
	 */
	@Override
	public void stopRunning(){
		running = false;
	}

	/* (non-Javadoc)
	 * @see view.View#dirPathCommand(java.lang.String)
	 */
	@Override
	public void dirPathCommand(String path){
		File file = new File(path);
		if(file.exists()){
			System.out.println("Files in this path: ");
			for(File f : file.listFiles())
				System.out.println(f.getName());
		}

		else{
			this.stringPrinting("The path <"+ path +"> does not exist");
		}

	}

	/* (non-Javadoc)
	 * @see view.View#printMaze(byte[])
	 */
	//print the maze
	@Override
	public void printMaze(byte[] mazeByteArray) {
		int row = ByteBuffer.wrap(Arrays.copyOfRange(mazeByteArray, 24, 28)).getInt();
		int height = ByteBuffer.wrap(Arrays.copyOfRange(mazeByteArray, 28, 32)).getInt();
		int column = ByteBuffer.wrap(Arrays.copyOfRange(mazeByteArray, 32, 36)).getInt();
		int k = 36;
		for(int i=0;i<height;i++)
		{
			if(i!=0)
				System.out.println();
			for(int j=0;j<row;j++)
			{
				System.out.println();
				for(int g=0;g<column;g++)
				{
					System.out.print(ByteBuffer.wrap(Arrays.copyOfRange(mazeByteArray, k, (k+4))).getInt());
					k+=4;
				}
			}
		}
		System.out.println();
	}

	/* (non-Javadoc)
	 * @see view.View#DisplayCross(int[][])
	 */
	//print the crossSection
	@Override
	public void DisplayCross(int[][] crossSectionBy) {

		int t = crossSectionBy.length;
		int p = crossSectionBy[0].length;
		for(int tempT = 0;tempT<t;tempT++)
		{
			System.out.println();
			for(int tempP = 0;tempP<p;tempP++)
			{
				System.out.print(crossSectionBy[tempT][tempP]);
			}
		}
		System.out.println();
	}

	/* (non-Javadoc)
	 * @see view.View#errorPrinting(java.lang.String)
	 */
	@Override
	public void stringPrinting(String stringArray){
		System.out.println(stringArray);
	}

	/* (non-Javadoc)
	 * @see view.View#start()
	 */
	@Override
	public void start(){
		this.run();
	}

	/* (non-Javadoc)
	 * @see view.View#MazeReady(java.lang.Object)
	 */
	@Override
	public void MazeReady(Object object) {
		System.out.println("maze <"+ ((ArrayList<?>)object).get(1) +"> is ready");
	}

	/* (non-Javadoc)
	 * @see view.View#displaySolution(java.lang.Object)
	 */
	@Override
	public void displaySolution(Object solution) {
		System.out.println(solution.toString());
	}

	/* (non-Javadoc)
	 * @see view.View#showMazeSize(int)
	 */
	@Override
	public void showMazeSize(int size) {
		System.out.println("Maze size in memory: " +size);
	}

	/* (non-Javadoc)
	 * @see view.View#showSizeInFile(long)
	 */
	@Override
	public void showSizeInFile(long size) {
		System.out.println("Maze size in file: " +size);		
	}

	/* (non-Javadoc)
	 * @see view.View#solutionReady(java.lang.Object)
	 */
	@Override
	public void solutionReady(Object object) {
		System.out.println("solution for <"+((ArrayList<?>)object).get(1)+"> is ready");

	}

	/* (non-Javadoc)
	 * @see view.View#moveCaracter(java.lang.Object)
	 */
	@Override
	public void moveCharacter(Object arg) {}

	/* (non-Javadoc)
	 * @see view.View#moveCaracterY(java.lang.Object)
	 */
	@Override
	public void moveCharacterY(Object arg) {}

	/* (non-Javadoc)
	 * @see view.View#whatKindOfView()
	 */
	@Override
	public String whatKindOfView() {
		return null;
	}
}
