package model;

import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Observable;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import GZIP.MyGzipCompressor;
import Hibernate.MySql;
import algorithms.demo.Maze3dSearchableAdapter;
import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.AStarAlgorithm;
import algorithms.search.BFSAlgorithm;
import algorithms.search.MazeAirDistance;
import algorithms.search.MazeManhattanDistance;
import algorithms.search.Searcher;
import algorithms.search.Solution;
import configuration.ServerProperties;
import network.MyTCPIPServer;

/**
 * The Class MyModel.
 * 
 * the server model
 * 
 * @author Rea Bar and Tom Eileen Hirsch
 */
public class MyModel extends Observable{

	/** The Constant SOLUTION_HASHMAP_PATH. */
	private static final String SOLUTION_HASHMAP_PATH = "./Properties/Hashmap.gz";

	/** The Constant THREAD_POOL. */
	private static final int THREAD_POOL = 5;

	/** The my server. */
	MyTCPIPServer myServer = null;

	/** The server running. */
	private boolean serverRunning = false;
	/** The solutions. */
	protected HashMap<Maze3d, Solution<Position>> solutions = new HashMap<>();

	/** The array list we send the presenter. */
	protected ArrayList<Object> arrayList = new ArrayList<>();

	/** The thread pool. */
	private ExecutorService threadPool;

	/** The future solution. */
	private Future<Solution<Position>> futureSolution;

	private MySql hibernate = null;
	/**
	 * Instantiates a new my model.
	 */
	public MyModel(){
		threadPool = Executors.newFixedThreadPool(THREAD_POOL);
		hibernate = new MySql();
		// We deleted the saving to GZip because we did the Hibernate bonus but we kept it here for future use
		/*File file = new File(SOLUTION_HASHMAP_PATH);
		if(file.exists()){
			MyGzipCompressor gzip = new MyGzipCompressor();
			try {
				this.solutions = gzip.decompress();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}*/
		this.solutions = hibernate.readFromDB();
	}

	/**
	 * Solutions size.
	 *
	 * @param num the count of solution
	 */
	public void solutionsSize(int num){
		setChanged();
		notifyObservers(num);
	}

	/**
	 * Delete client.
	 *
	 * @param string the string
	 */
	public void deleteClient(String string){
		myServer.deleteClient(string);
	}

	/**
	 * Adds the to list.
	 *
	 * @param someClient the some client
	 */
	public void addToList(Socket someClient){
		setChanged();
		notifyObservers(someClient);
	}

	/**
	 * Notify string.
	 *
	 * @param string the string
	 */
	public void notifyString(String string){
		setChanged();
		notifyObservers(string);
	}

	/**
	 * Start.
	 */
	public void start(){
		myServer = new MyTCPIPServer(this);
		new Thread(new Runnable() {
			public void run() {
				myServer.startServer();
			}
		}).start();
		serverRunning = true;
		setChanged();
		notifyObservers("server was started with port and maximum number of clients from the saved properties file");
	}

	/**
	 * Start.
	 *
	 * @param numOfClients the number of clients
	 * @param port the port
	 */
	public void start(int numOfClients, int port){
		myServer = new MyTCPIPServer(port, numOfClients,this);
		new Thread(new Runnable() {
			public void run() {
				myServer.startServer();
			}
		}).start();
		ServerProperties p = new ServerProperties(port, numOfClients);
		serverRunning = true;
		setChanged();
		notifyObservers("server was started with port: " + port + " and maximum number of clients: " + numOfClients);
	}

	/**
	 * Solve maze.
	 *
	 * @param maze the maze
	 * @param algorithm the algorithm
	 */
	/* (non-Javadoc)
	 * @see model.Model#solveMaze(java.lang.String, java.lang.String)
	 */
	public void solveMaze(Maze3d maze, String algorithm) {
		boolean flag = false;
		Iterator it = solutions.entrySet().iterator();
		while (it.hasNext() && !flag) {
			Map.Entry pair = (Map.Entry)it.next();
			if(pair.getKey().equals(maze))
				flag = true;
		}

		if(!solutions.containsKey(maze) && !flag){
			futureSolution = threadPool.submit(new Callable<Solution<Position>>(){
				@Override
				public Solution<Position> call() throws Exception {
					Solution<Position> sol = new Solution<Position>();
					Searcher<Position> searcher = null;
					if (algorithm.contains("BFS") || algorithm.equals("BFS")){
						searcher = new BFSAlgorithm<>();
					}
					else if(algorithm.contains("AStar")){
						if(algorithm.contains("Manhattan"))
							searcher= new AStarAlgorithm<Position>(new MazeManhattanDistance());
						else if (algorithm.contains("Air"))
							searcher = new AStarAlgorithm<Position>(new MazeAirDistance());
					}

					else if(algorithm.equals("A* Air")){
						searcher = new AStarAlgorithm<Position>(new MazeAirDistance());
					}

					else if(algorithm.equals("A* Manhattan")){
						searcher= new AStarAlgorithm<Position>(new MazeManhattanDistance());
					}
					sol = searcher.search(new Maze3dSearchableAdapter(maze));
					return sol;
				}
			});
			try {
				this.solutions.put(maze, futureSolution.get());
				hibernate.writeToDB(maze, this.solutions.get(maze));
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (ExecutionException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			/*try {
				File file = new File(SOLUTION_HASHMAP_PATH);
				if(file.exists()){
					MyGzipCompressor gzip = new MyGzipCompressor();
					try {
						this.solutions = gzip.decompress();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				this.solutions.put(maze, futureSolution.get());
				MyGzipCompressor gzip = new MyGzipCompressor();
				try {
					gzip.compress(solutions);
				} catch (IOException e) {
					e.printStackTrace();
				}
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}*/
			setChanged();
			try {
				notifyObservers(futureSolution.get());
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
		}

		else if(flag==true){
			setChanged();
			notifyObservers(solutions.get(maze));
		}

		threadPool.shutdown();
		try {
			if(!threadPool.awaitTermination(3, TimeUnit.SECONDS)){
				threadPool.shutdownNow();	
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Shut down.
	 */
	/* (non-Javadoc)
	 * @see model.Model#shutDown()
	 */
	public void shutDown() {
		if(serverRunning){
			myServer.stopServer();
			setChanged();
			notifyObservers("stop");
		}
	}
}
