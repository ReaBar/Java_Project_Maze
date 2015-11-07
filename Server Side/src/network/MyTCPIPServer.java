package network;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import GZIP.MyGzipCompressor;
import Hibernate.MySql;
import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;
import configuration.ServerProperties;
import model.MyModel;

/**
 * The Class MyTCPIPServer.
 * 
 * TCP protocol
 * the server is listening for clients to connect
 * 
 * @author Rea Bar and Tom Eileen Hirsch
 */
public class MyTCPIPServer{

	/** The Constant SOLUTION_HASHMAP_PATH. */
	private static final String SOLUTION_HASHMAP_PATH = "./Properties/Hashmap.gz";

	/** The model. */
	MyModel model;

	/** The port. */
	private int port;

	/** The number of clients. */
	private int numOfClients;

	/** The client counter. */
	private int clientCounter=0;

	/** The kill server. */
	private volatile boolean killServer = false;

	/** The executer. */
	private ExecutorService executer;

	/** The array of clients. */
	private ClientHandler[] arrayOfClients;

	/** The method return. */
	private Future<Integer> methodReturn;

	/** The solutions. */
	protected HashMap<Maze3d, Solution<Position>> solutions = new HashMap<>();

	/**
	 * Instantiates a new my TCP IP server.
	 *
	 * @param port the port
	 * @param numOfClients the number of clients
	 * @param model the model
	 */
	public MyTCPIPServer(int port, int numOfClients,MyModel model) {
		this.model = model;
		this.port = port;
		this.numOfClients = numOfClients;
		arrayOfClients = new ClientHandler[numOfClients];
	}

	/**
	 * Instantiates a new my TCP IP server.
	 *
	 * @param model the model
	 */
	public MyTCPIPServer(MyModel model) {
		this.model = model;
		ServerProperties properties = new ServerProperties();
		properties.loadProperties();
		this.port = properties.getPort();
		this.numOfClients = properties.getNumOfClients();
		arrayOfClients = new ClientHandler[numOfClients];
	}

	/**
	 * Delete client.
	 *
	 * @param string the string
	 */
	public void deleteClient(String string){
		for(int i=0;i<clientCounter;i++){
			if(arrayOfClients[i].equals(string)){
				arrayOfClients[i].shutDown();
				arrayOfClients[i] = null;
			}
		}
	}

	/**
	 * Start server.
	 */
	public void startServer(){
		ServerSocket server = null;
		try {
			server=new ServerSocket(this.port);
			executer = Executors.newFixedThreadPool(numOfClients);
			server.setSoTimeout(500);

			while(!killServer){
				try{
					final Socket someClient = server.accept();
					model.addToList(someClient);
					ClientHandler client = new ClientHandler(someClient);
					arrayOfClients[clientCounter+1] = client;
					model.notifyString("Got new connection to server");
					clientCounter++;
					methodReturn = executer.submit(new Callable<Integer>() {
						@Override
						public Integer call() throws Exception {
							int num = client.run();
							someClient.close();
							return num;
						}
					});
					try {
						if(methodReturn.get()==1){
							clientCounter--;
							MySql hibernate = new MySql();
							model.solutionsSize(hibernate.getNumOfRows());
							/*File file = new File(SOLUTION_HASHMAP_PATH);
							if(file.exists()){
								MyGzipCompressor gzip = new MyGzipCompressor();
								try {
									this.solutions = gzip.decompress();
									model.solutionsSize(solutions.size());
								} catch (IOException e) {
									e.printStackTrace();
								}
							}*/
						}
					} catch (InterruptedException e) {
						e.printStackTrace();
					} catch (ExecutionException e) {
						e.printStackTrace();
					}
				} catch (SocketTimeoutException e) {
				}
			}
			executer.shutdown();
			server.close();
		} catch (IOException e) {
			model.notifyString("tired of waiting for connection");		
			executer.shutdown();
			try {
				server.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	/**
	 * Stop server.
	 */
	public void stopServer(){
		killServer = true;
	}
}