package model;

import java.beans.XMLDecoder;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Observable;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.MyMaze3dGenerator;
import algorithms.mazeGenerators.Position;
import algorithms.mazeGenerators.SimpleMaze3dGenerator;
import algorithms.search.Solution;
import io.MyCompressorOutputStream;
import io.MyDecompressorInputStream;
import myXMLEncodeDecode.myXMLEncodeDecode;
import presenter.Properties;

/**
 * The Class ClientModel.
 * 
 * @author Rea Bar and Tom Eileen Hirsch
 */
public class ClientModel extends Observable implements Model {

	/** The server socket. */
	Socket myServer;

	/** The server input. */
	private ObjectInputStream serverInput;

	/** The server output. */
	private ObjectOutputStream serverOutput;

	/** The server running. */
	private boolean serverRunning = false;

	/** The Constant CLIENT_PROPERTIES. */
	private static final String CLIENT_PROPERTIES = "./Properties/clientProperties.xml";

	/** The solutions. */
	protected HashMap<Maze3d, Solution<Position>> solutions = new HashMap<>();

	/** The maze hash map. */
	protected HashMap<String, Maze3d> mazeHashMap = new HashMap<>();

	/** The compressed maze. */
	protected HashMap<String, String> compressedMaze = new HashMap<>();

	/** The possible moves from position. */
	protected ArrayList<Position> possible = new ArrayList<>();

	/** The array list we send the presenter. */
	protected ArrayList<Object> arrayList = new ArrayList<>();

	/** The array list server. */
	protected ArrayList<Object> arrayListServer = new ArrayList<>();

	/** The maze. */
	private Maze3d maze;

	/** The current position. */
	private Position currentPosition;

	/** The user name. */
	private String userName;

	/** The object. */
	private Object object;

	/** The thread pool. */
	private ExecutorService threadPool;

	/** The future maze3d. */
	private Future<Maze3d> futureMaze3d;

	/** The view. */
	private String view;

	/** The pool size. */
	private int poolSize;

	/** The algorithm to solve. */
	private String algoToSolve;

	/** The algorithm to generate. */
	private String algoToGenerate;

	/** The  server ip address. */
	private String ip;

	/** The server port. */
	private int port;

	/**
	 * Gets the view.
	 *
	 * @return the view
	 */
	public String getView() {
		return view;
	}

	/**
	 * Sets the view.
	 *
	 * @param view the new view
	 */
	public void setView(String view) {
		this.view = view;
	}

	/**
	 * Gets the pool size.
	 *
	 * @return the pool size
	 */
	public int getPoolSize() {
		return poolSize;
	}

	/**
	 * Sets the pool size.
	 *
	 * @param poolSize the new pool size
	 */
	public void setPoolSize(int poolSize) {
		this.poolSize = poolSize;
	}

	/**
	 * Gets the algo to solve.
	 *
	 * @return the algo to solve
	 */
	public String getAlgoToSolve() {
		return algoToSolve;
	}

	/**
	 * Gets the IP address.
	 *
	 * @return the IP address
	 */
	public String getIp() {
		return ip;
	}

	/**
	 * Sets the IP address.
	 *
	 * @param ip the new IP address
	 */
	public void setIp(String ip) {
		this.ip = ip;
	}

	/**
	 * Gets the port.
	 *
	 * @return the port
	 */
	public int getPort() {
		return port;
	}

	/**
	 * Sets the port.
	 *
	 * @param port the new port
	 */
	public void setPort(int port) {
		this.port = port;
	}

	/**
	 * Sets the algorithm to solve.
	 *
	 * @param algoToSolve the new algorithm to solve
	 */
	public void setAlgoToSolve(String algoToSolve) {
		this.algoToSolve = algoToSolve;
	}

	/**
	 * Gets the maze.
	 *
	 * @return the maze
	 */
	public Maze3d getMaze() {
		return maze;
	}

	/**
	 * Sets the maze.
	 *
	 * @param maze the new maze
	 */
	public void setMaze(Maze3d maze) {
		this.maze = maze;
	}

	/**
	 * Gets the current position.
	 *
	 * @return the current position
	 */
	public Position getCurrentPosition() {
		return currentPosition;
	}


	/**
	 * Sets the current position.
	 *
	 * @param currentPosition the new current position
	 */
	public void setCurrentPosition(Position currentPosition) {
		this.currentPosition = currentPosition;
	}


	/**
	 * Gets the user name.
	 *
	 * @return the user name
	 */
	public String getUserName() {
		return userName;
	}


	/**
	 * Sets the user name.
	 *
	 * @param userName the new user name
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * Gets the object.
	 *
	 * @return the object
	 */
	public Object getObject() {
		return object;
	}


	/**
	 * Sets the object.
	 *
	 * @param object the new object
	 */
	public void setObject(Object object) {
		this.object = null;
		this.object = object;
	}

	/**
	 * Gets the algorithm to generate.
	 *
	 * @return the algorithm to generate
	 */
	public String getAlgoToGenerate() {
		return algoToGenerate;
	}


	/**
	 * Sets the algorithm to generate.
	 *
	 * @param algoToGenerate the new algorithm to generate
	 */
	public void setAlgoToGenerate(String algoToGenerate) {
		this.algoToGenerate = algoToGenerate;
	}


	/**
	 * Checks if is server running.
	 *
	 * @return true, if is server running
	 */
	public boolean isServerRunning() {
		return serverRunning;
	}

	/**
	 * Sets the server running.
	 *
	 * @param serverRunning the new server running
	 */
	public void setServerRunning(boolean serverRunning) {
		this.serverRunning = serverRunning;
	}

	/**
	 * Instantiates a new my model.
	 */
	public ClientModel() {
		File properties = new File(CLIENT_PROPERTIES);
		if(properties.exists()){
			this.loadProperties();
			threadPool = Executors.newFixedThreadPool(this.getPoolSize());
		}
		else{
			threadPool = Executors.newFixedThreadPool(10);
		}
	}

	/* (non-Javadoc)
	 * @see model.Model#generate3dMaze(java.lang.String, int, int, int)
	 */
	@Override
	public void generate3dMaze(String name, int x, int y, int z){
		if(algoToGenerate.equals("DFS")||algoToGenerate.equals("Simple")){
			if(!mazeHashMap.containsKey(name)){
				// Generate(x,y,z) and save the maze as "name" in hashMap
				futureMaze3d = threadPool.submit(new Callable<Maze3d>() {
					@Override
					public Maze3d call() throws Exception {
						Maze3d maze = null;
						if(algoToGenerate.equals("DFS"))
							maze = new MyMaze3dGenerator().generate(x, y, z);
						else if(algoToGenerate.equals("Simple"))
							maze = new SimpleMaze3dGenerator().generate(x, y, z);
						currentPosition = maze.getStartPosition();
						userName = name;
						//send a message to the view via the presenter that the maze is ready
						arrayList.add("mazeIsReady");
						arrayList.add(name);
						setObject(arrayList);

						return maze;

					}
				});
				try {
					this.mazeHashMap.put(name,futureMaze3d.get());
				} catch (InterruptedException | ExecutionException e) {
					e.printStackTrace();
				}
				this.setChanged();
				this.notifyObservers();
				arrayList.clear();
			}
			else{
				setChanged();
				notifyObservers("please enter another name, this name already exists");
			}
		}
	}

	/* (non-Javadoc)
	 * @see model.Model#saveCompressedMazed(java.lang.String, java.lang.String)
	 */
	@Override
	public void saveCompressedMazed(String name, String fileName) throws IOException {
		File f = new File(fileName);
		if(mazeHashMap.containsKey(name) && !f.exists()){
			OutputStream out = new MyCompressorOutputStream(new FileOutputStream(fileName));
			out.write(mazeHashMap.get(name).toByteArray());
			out.flush();
			out.close();
			compressedMaze.put(name, fileName);
		}

		else if(!mazeHashMap.containsKey(name)){
			setChanged();
			notifyObservers("The maze <" + name + "> does not exist");
		}

		else{
			setChanged();
			notifyObservers("The file name <" + fileName + "> already exists, please choose another file name");
		}
	}

	/* (non-Javadoc)
	 * @see model.Model#loadMaze(java.lang.String, java.lang.String)
	 */
	@Override
	public void loadMaze(String fileName, String name) throws IOException {
		File f = new File(fileName);
		if(f.exists() && !mazeHashMap.containsKey(name)){
			InputStream in = new MyDecompressorInputStream(new FileInputStream(fileName));
			byte b[] = new byte[36];
			in.read(b);
			int row = ByteBuffer.wrap(Arrays.copyOfRange(b, 24, 28)).getInt();
			int height = ByteBuffer.wrap(Arrays.copyOfRange(b, 28, 32)).getInt();
			int column = ByteBuffer.wrap(Arrays.copyOfRange(b, 32, 36)).getInt();
			byte byteArray[] = new byte[row*height*column*4];
			in.read(byteArray);
			in.close();
			byte[] combined = new byte[b.length + byteArray.length];
			System.arraycopy(b, 0, combined, 0, b.length);
			System.arraycopy(byteArray, 0, combined, b.length, byteArray.length);

			Maze3d loaded = new Maze3d(combined);
			mazeHashMap.put(name, loaded);
			compressedMaze.put(name, fileName);
			arrayList.add("loadMaze");
			arrayList.add(name);
			arrayList.add(loaded.getCrossSectionByY(loaded.getStartPosition().getY()));
			setChanged();
			notifyObservers(arrayList);
			arrayList.clear();
		}

		else if(!f.exists()){
			setChanged();
			notifyObservers("The fileName <" + fileName + "> does not exist");
		}

		else if(mazeHashMap.containsKey(name)){
			setChanged();
			notifyObservers("please enter another name, this name <" + name + "> already exists");
		}
	}

	/* (non-Javadoc)
	 * @see model.Model#solveMaze(java.lang.String, java.lang.String)
	 */
	@Override
	public void solveMaze(String name, String algorithm) {

		try {
			if(algorithm.equals("null")){
				algorithm = this.getAlgoToSolve();
				if(algorithm == null){
					setChanged();
					notifyObservers("please choose with which algorithm to solve the maze");
				}
			}
			if(!mazeHashMap.containsKey(name)){
				setChanged();
				notifyObservers("the maze <" + name + "> does not exist");
			}
			else{
				try {
					myServer = new Socket(this.ip, this.port);
					setServerRunning(true);
				} catch (UnknownHostException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
				}
				try {
					serverOutput = new ObjectOutputStream(myServer.getOutputStream());
					serverInput = new ObjectInputStream(myServer.getInputStream());
				} catch (IOException e) {
					setChanged();
					notifyObservers("the server is closed");
				}
				arrayListServer.add(mazeHashMap.get(name));
				arrayListServer.add(algorithm);
				try {
					serverOutput.writeObject(arrayListServer);
					serverOutput.reset();
					serverOutput.flush();
				} catch (IOException e) {

				}
				arrayListServer.clear();
			}

			Object obj;
			try {
				obj = serverInput.readObject();
				if(obj instanceof String){
					String solution2 = (String)obj;
					setChanged();
					notifyObservers(solution2);
				}
				if(obj instanceof Solution<?>){
					solutions.put(mazeHashMap.get(name), (Solution<Position>)obj);
					ArrayList<Object> solutionArrayList = new ArrayList<>();
					solutionArrayList.add("solutionReady");
					solutionArrayList.add(name);
					setChanged();
					notifyObservers(solutionArrayList);
				}
			} catch (ClassNotFoundException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
			}

		}
		finally{
			try {
				serverInput.close();
				serverOutput.close();
				myServer.close();
				setServerRunning(false);
			} catch (IOException e) {
				//e.printStackTrace();
			}

		}
	}

	/* (non-Javadoc)
	 * @see model.Model#askSolution(java.lang.String)
	 */
	@Override
	public void getDisplaySolution(String name) {

		if(solutions.containsKey(mazeHashMap.get(name))){
			setChanged();
			notifyObservers(solutions.get(mazeHashMap.get(name)));
		}
		else{
			setChanged();
			notifyObservers("the maze <" + name + "> was not solved yet, please solve first");
		}
	}

	/* (non-Javadoc)
	 * @see model.Model#showMazeSize(java.lang.String)
	 */
	@Override
	public void showMazeSize(String name) throws IOException {
		if(mazeHashMap.containsKey(name)){
			arrayList.add("mazeSize");
			arrayList.add(mazeHashMap.get(name).toByteArray().length);
			setChanged();
			notifyObservers(arrayList);
			arrayList.clear();
		}

		else{
			setChanged();
			notifyObservers("the maze <" + name + "> does not exist");
		}
	}

	/* (non-Javadoc)
	 * @see model.Model#askForMaze(java.lang.String)
	 */
	@Override
	public void askForMaze(String name) {
		if(mazeHashMap.containsKey(name)){
			try {
				byte[] mazeByteArray = mazeHashMap.get(name).toByteArray();
				maze =  mazeHashMap.get(name);
				setChanged();
				notifyObservers(mazeByteArray);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		else{
			setChanged();
			notifyObservers("Error in maze name <"+name+">");
		}
	}

	/* (non-Javadoc)
	 * @see model.Model#getCrossSection(java.lang.String, int, java.lang.String)
	 */
	@Override
	public void getCrossSection(String by, int parseInt, String name) {

		if(mazeHashMap.containsKey(name)){
			if (by.equals("X") || by.equals("x") ){
				try{
					setChanged();
					this.notifyObservers(mazeHashMap.get(name).getCrossSectionByX(parseInt));
				}
				catch (IndexOutOfBoundsException e) {
					setChanged();
					this.notifyObservers("Index Out Of Bounds");
				}
			}
			else if (by.equals("Y") || by.equals("y")){
				try{			
					setChanged();
					this.notifyObservers(mazeHashMap.get(name).getCrossSectionByY(parseInt));
				}
				catch (IndexOutOfBoundsException e) {
					setChanged();
					this.notifyObservers("Index Out Of Bounds");
				}
			}
			else if (by.equals("Z") || by.equals("z")){
				try{
					setChanged();
					this.notifyObservers(mazeHashMap.get(name).getCrossSectionByZ(parseInt));
				}
				catch (IndexOutOfBoundsException e) {
					setChanged();
					this.notifyObservers("Index Out Of Bounds");
				}
			}
			else {
				setChanged();
				this.notifyObservers("Error in axis");
			}

		}
		else {
			setChanged();
			this.notifyObservers("Error in maze name <"+name+">");
		}	
	}

	/* (non-Javadoc)
	 * @see model.Model#showSizeInFile(java.lang.String)
	 */
	@Override
	public void showSizeInFile(String name) {

		if(compressedMaze.containsKey(name)){
			File f = new File(compressedMaze.get(name));
			if(f.exists()){
				arrayList.add("fileSize");
				arrayList.add(f.length());
				setChanged();
				notifyObservers(arrayList);
				arrayList.clear();
			}
			//controller.sizeInFile(f.length());
			else{
				setChanged();
				notifyObservers("The maze wasn't saved to a file");
			}
		}
		else{
			setChanged();
			notifyObservers("The maze wasn't saved to a file");
		}
	}

	/* (non-Javadoc)
	 * @see model.Model#moveUp()
	 */
	@Override
	public void moveUp() {
		if(currentPosition.getY()<this.maze.getHeight()){
			possible = this.maze.getPossibleMoves(currentPosition);
			Position temp = new Position(currentPosition.getX(), currentPosition.getY()+1, currentPosition.getZ());
			if(possible.contains(temp)){
				currentPosition = temp;
				arrayList.add("move");
				arrayList.add(currentPosition);
				arrayList.add(this.maze.getCrossSectionByY(currentPosition.getY()));
				setChanged();
				notifyObservers(arrayList);
				arrayList.clear();
			}
			else{
				setChanged();
				notifyObservers("unable to move this direction");
			}
		}
		else{
			setChanged();
			notifyObservers("unable to move this direction");
		}

	}

	/* (non-Javadoc)
	 * @see model.Model#moveDown()
	 */
	@Override
	public void moveDown() {
		if(currentPosition.getY()>0){
			possible = this.maze.getPossibleMoves(currentPosition);
			Position temp = new Position(currentPosition.getX(), currentPosition.getY()-1, currentPosition.getZ());
			if(possible.contains(temp)){
				//currentPosition.setY(currentPosition.getY()-1);
				currentPosition = temp;
				arrayList.add("move");
				arrayList.add(currentPosition);
				arrayList.add(this.maze.getCrossSectionByY(currentPosition.getY()));
				setChanged();
				notifyObservers(arrayList);
				arrayList.clear();
			}
			else{
				setChanged();
				notifyObservers("unable to move this direction");
			}
		}
		else{
			setChanged();
			notifyObservers("unable to move this direction");
		}
	}

	/* (non-Javadoc)
	 * @see model.Model#moveForward()
	 */
	@Override
	public void moveForward() {
		if(currentPosition.getX()>0){
			possible = this.maze.getPossibleMoves(currentPosition);
			Position temp = new Position(currentPosition.getX()-1, currentPosition.getY(), currentPosition.getZ());
			if(possible.contains(temp)){
				//notifyObservers(this.maze.getCrossSectionByY(currentPosition.getY()));
				//currentPosition.setX(currentPosition.getX()-1);
				currentPosition = temp;
				setChanged();
				notifyObservers(currentPosition);
			}
			else{
				setChanged();
				notifyObservers("unable to move this direction");
			}
		}
		else{
			setChanged();
			notifyObservers("unable to move this direction");
		}

	}

	/* (non-Javadoc)
	 * @see model.Model#moveBackward()
	 */
	@Override
	public void moveBackward() {
		if(currentPosition.getX()<this.maze.getRow()){
			possible = this.maze.getPossibleMoves(currentPosition);
			Position temp = new Position(currentPosition.getX()+1, currentPosition.getY(), currentPosition.getZ());
			if(possible.contains(temp)){
				//notifyObservers(this.maze.getCrossSectionByY(currentPosition.getY()));
				//currentPosition.setZ(currentPosition.getX()+1);
				currentPosition = temp;
				setChanged();
				notifyObservers(currentPosition);
			}
			else{
				setChanged();
				notifyObservers("unable to move this direction");
			}
		}
		else{
			setChanged();
			notifyObservers("unable to move this direction");
		}
	}

	/* (non-Javadoc)
	 * @see model.Model#moveRight()
	 */
	@Override
	public void moveRight() {
		if(currentPosition.getZ()<this.maze.getColumn()){
			possible = this.maze.getPossibleMoves(currentPosition);
			Position temp = new Position(currentPosition.getX(), currentPosition.getY(), currentPosition.getZ()+1);
			if(possible.contains(temp)){
				//notifyObservers(this.maze.getCrossSectionByY(currentPosition.getY()));
				currentPosition = temp;
				setChanged();
				notifyObservers(currentPosition);
			}
			else{
				setChanged();
				notifyObservers("unable to move this direction");
			}
		}
		else{
			setChanged();
			notifyObservers("unable to move this direction");
		}
	}

	/* (non-Javadoc)
	 * @see model.Model#moveLeft()
	 */
	@Override
	public void moveLeft() {
		if(currentPosition.getZ()>0){
			possible = this.maze.getPossibleMoves(currentPosition);
			Position temp = new Position(currentPosition.getX(), currentPosition.getY(), currentPosition.getZ()-1);
			if(possible.contains(temp)){
				//notifyObservers(this.maze.getCrossSectionByY(currentPosition.getY()));
				currentPosition = temp;
				setChanged();
				notifyObservers(currentPosition);
			}
			else{
				setChanged();
				notifyObservers("unable to move this direction");
			}
		}
		else{
			setChanged();
			notifyObservers("unable to move this direction");
		}
	}

	/* (non-Javadoc)
	 * @see model.Model#setProperties(java.lang.String[])
	 */
	public void setProperties(String[] properties){
		Properties newProperties = new Properties();
		this.view = properties[3];
		this.algoToGenerate = properties[1];
		this.algoToSolve = properties[2];
		this.poolSize = Integer.parseInt(properties[0]);
		this.ip = properties[4];
		this.port = Integer.parseInt(properties[5]);
		newProperties.setProperties(view, algoToGenerate, algoToSolve, poolSize, ip, port);
		
		if(this.getView().equals("CLI")){
			setChanged();
			notifyObservers("exit");
			Properties p = new Properties();
			p = p.Start();
			this.view = p.getView();
			this.algoToGenerate = p.getAlgorithmToGenerate();
			this.algoToSolve = p.getAlgorithmToSolve();
			this.poolSize = p.getNumOfThreads();
			this.ip = p.getIp();
			this.port = p.getPort();
		}
	}

	/* (non-Javadoc)
	 * @see model.Model#loadProperties()
	 */
	public void loadProperties(){
		String filePath = new String(CLIENT_PROPERTIES);
		myXMLEncodeDecode xml = new myXMLEncodeDecode();
		Properties p = (Properties) xml.readFromXml(filePath);
		this.setAlgoToGenerate(p.getAlgorithmToGenerate());
		this.setView(p.getView());
		this.setAlgoToSolve(p.getAlgorithmToSolve());
		this.setPoolSize(p.getNumOfThreads());
		this.setIp(p.getIp());
		this.setPort(p.getPort());
	}

	/* (non-Javadoc)
	 * @see model.Model#loadProperties(java.lang.String)
	 */
	public void loadProperties(String fileName){
		FileInputStream in;
		try {
			XMLDecoder d;
			in = new FileInputStream(fileName);
			d=new XMLDecoder(in);
			Properties properties=(Properties)d.readObject();
			this.view = properties.getView();
			this.algoToGenerate = properties.getAlgorithmToGenerate();
			this.algoToSolve = properties.getAlgorithmToSolve();
			this.poolSize = properties.getNumOfThreads();
			d.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	/* (non-Javadoc)
	 * @see model.Model#getData()
	 */
	@Override
	public Object getData() {
		return this.getObject();
	}

	/* (non-Javadoc)
	 * @see model.Model#shutDown()
	 */
	@Override
	public void shutDown() {
		threadPool.shutdown();
		try {
			if(threadPool.awaitTermination(3, TimeUnit.SECONDS)){
				this.setChanged();
				this.notifyObservers("exit");
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
			threadPool.shutdownNow();
		}
	}
}
