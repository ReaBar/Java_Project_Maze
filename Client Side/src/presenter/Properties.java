package presenter;

import java.io.File;
import java.io.Serializable;
import java.util.Scanner;

import myXMLEncodeDecode.myXMLEncodeDecode;

/**
 * The Class Properties.
 * 
 * client properties
 * 
 * @author Rea Bar and Tom Eileen Hirsch
 */
public class Properties implements Serializable {

	/** The Constant FILE_NAME. */
	private static final String FILE_NAME = "./Properties/clientProperties.xml";

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 2315970338694591223L;

	/** The number of threads. */
	private int numOfThreads;

	/** The algorithm to generate. */
	private String algorithmToGenerate;

	/** The algorithm to solve. */
	private String algorithmToSolve;

	/** The view. */
	private String view;

	/** The remote server IP. */
	private String ip;

	/** The remote server port. */
	private int port;

	/**
	 * Instantiates a new properties.
	 */
	public Properties(){
	}

	/**
	 * Instantiates a new properties.
	 *
	 * @param view the view
	 * @param algoToGenerate the algorithm to generate
	 * @param algoToSolve the algorithm to solve
	 * @param numOfThreads the number of threads
	 * @param ip the server IP
	 * @param port the port
	 */
	public Properties(String view,String algoToGenerate ,String algoToSolve, int numOfThreads, String ip, int port){
		this.deletePropeties();
		this.view = view;
		this.algorithmToGenerate = algoToGenerate;
		this.algorithmToSolve = algoToSolve;
		this.numOfThreads = numOfThreads;
		this.ip = ip;
		this.port = port;
		myXMLEncodeDecode xml = new myXMLEncodeDecode();
		new File("./Properties").mkdir();
		xml.writeToXml(FILE_NAME, this);
	}

	/**
	 * Gets the view.
	 *
	 * @return the view
	 */
	public String getView() {
		return view;
	}

	/**
	 * Gets the ip.
	 *
	 * @return the ip
	 */
	public String getIp() {
		return ip;
	}

	/**
	 * Sets the IP.
	 *
	 * @param ip the new IP
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
	 * Sets the view.
	 *
	 * @param view the new view
	 */
	public void setView(String view) {
		this.view = view;
	}

	/**
	 * Gets the algorithm to solve.
	 *
	 * @return the algorithm to solve
	 */
	public String getAlgorithmToSolve() {
		return algorithmToSolve;
	}

	/**
	 * Sets the algorithm to solve.
	 *
	 * @param algorithmToSolve the new algorithm to solve
	 */
	public void setAlgorithmToSolve(String algorithmToSolve) {
		this.algorithmToSolve = algorithmToSolve;
	}

	/**
	 * Gets the algorithm to generate.
	 *
	 * @return the algorithm to generate
	 */
	public String getAlgorithmToGenerate() {
		return algorithmToGenerate;
	}

	/**
	 * Sets the algorithm to generate.
	 *
	 * @param algorithmToGenerate the new algorithm to generate
	 */
	public void setAlgorithmToGenerate(String algorithmToGenerate) {
		this.algorithmToGenerate = algorithmToGenerate;
	}

	/**
	 * Gets the serial version UID.
	 *
	 * @return the serial version UID.
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * Gets the number of threads.
	 *
	 * @return the number of threads
	 */
	public int getNumOfThreads() {
		return numOfThreads;
	}

	/**
	 * Sets the number of threads.
	 *
	 * @param numOfThreads the new number of threads
	 */
	public void setNumOfThreads(int numOfThreads) {
		this.numOfThreads = numOfThreads;
	}

	/**
	 * Start.
	 * get the properties from the xml file or get a new properties if there is no file
	 *
	 * @return the properties
	 */
	public Properties Start(){
		Properties p = null;
		int num;
		myXMLEncodeDecode xml = new myXMLEncodeDecode();
		File f = new File(FILE_NAME);
		if(f.exists()){
			p = (Properties) xml.readFromXml(FILE_NAME);
		}
		else{
			Scanner in = new Scanner(System.in);
			p = new Properties();
			do {
				System.out.println("How many threads to run in the thread pool?");
				p.setNumOfThreads(in.nextInt());
			} while (p.getNumOfThreads() <= 0);

			do {
				System.out.println("With which algorithm to generate the maze:\n1.DFS\n2.Randomly");
				num = in.nextInt();
			} while (num > 2 || num < 1 );
			switch (num) {
			case 1:
				p.setAlgorithmToGenerate("MyMaze3dGenerator");
				break;
			case 2:
				p.setAlgorithmToGenerate("SimpleMaze3dGenerator");
				break;
			}

			do {
				System.out.println("With which algorithm to solve the maze:\n1.A* - Manhattan Distance\n2.A* - Air Distance\n3.BFS");
				num = in.nextInt();
			} while (num > 3 || num < 1);

			switch (num) {
			case 1:
				p.setAlgorithmToSolve("AStarManhattan");
				break;
			case 2:
				p.setAlgorithmToSolve("AStarAir");
				break;
			case 3:
				p.setAlgorithmToSolve("BFS");
				break;
			}

			do {
				System.out.println("Which view would you like to use?\n1.Command line\n2.Graphic User Interface");
				num = in.nextInt();
			} while (num > 2 || num < 1 );
			switch (num) {
			case 1:
				p.setView("CLI");
				break;
			case 2:
				p.setView("GUI");
				break;
			}

			System.out.println("Please enter server IP address");
			p.setIp(in.nextLine());

			System.out.println("Please enter server port");
			p.setPort(in.nextInt());

			new File("./Properties").mkdir();
			xml.writeToXml(FILE_NAME, p);
			in.close();
		}

		return p;
	}

	/**
	 * Sets the properties.
	 *
	 * @param view the view
	 * @param algorithmGenerate the algorithm generate
	 * @param algorithmSolve the algorithm solve
	 * @param numOfThreads the number of threads
	 * @param ip the server IP address
	 * @param port the server port
	 * @return the properties
	 */
	public Properties setProperties(String view, String algorithmGenerate, String algorithmSolve, int numOfThreads,String ip, int port){
		this.setView(view);
		this.setAlgorithmToGenerate(algorithmGenerate);
		this.setAlgorithmToSolve(algorithmSolve);
		this.setNumOfThreads(numOfThreads);
		this.setIp(ip);
		this.setPort(port);
		myXMLEncodeDecode xml = new myXMLEncodeDecode();
		new File("./Properties").mkdir();
		xml.writeToXml(FILE_NAME, this);
		return this;
	}

	/**
	 * Delete properties.
	 */
	public void deletePropeties(){
		File file = new File(FILE_NAME);
		if(file.exists()){
			file.delete();
		}
	}
}


