package configuration;

import java.io.File;
import java.io.Serializable;
import java.util.Scanner;

import myXMLEncodeDecode.myXMLEncodeDecode;

/**
 * The Class ServerProperties.
 * 
 * @author Rea Bar and Tom Eileen Hirsch
 */
public class ServerProperties implements Serializable{

	/** The Constant FILE_NAME witch gets the path of xml file. */
	private static final String FILE_NAME = "./Properties/serverProperties.xml";

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 2315970338694591223L;

	/** The number of port. */
	private int port;

	/** The number of clients that can connect to server. */
	private int numOfClients;

	/**
	 * Instantiates a new server properties.
	 */
	public ServerProperties(){
	}

	/**
	 * Instantiates a new server properties.
	 *
	 * @param port the port
	 * @param numOfClients the num of clients
	 */
	public ServerProperties(int port,int numOfClients){
		this.deletePropeties();
		this.port = port;
		this.numOfClients = numOfClients;
		myXMLEncodeDecode xml = new myXMLEncodeDecode();
		new File("./Properties").mkdir();
		xml.writeToXml(FILE_NAME, this);
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
	 * Gets the number of clients.
	 *
	 * @return the number of clients
	 */
	public int getNumOfClients() {
		return numOfClients;
	}

	/**
	 * Sets the number of clients.
	 *
	 * @param numOfClients the new number of clients
	 */
	public void setNumOfClients(int numOfClients) {
		this.numOfClients = numOfClients;
	}

	/**
	 * Creates the properties.
	 *
	 * @return the server properties
	 */
	public ServerProperties createProperties(){
		ServerProperties p = new ServerProperties();
		myXMLEncodeDecode xml = new myXMLEncodeDecode();
		Scanner in = new Scanner(System.in);
		do {
			System.out.println("How many clients can connect at once?");
			p.setNumOfClients(in.nextInt());
		} while (p.getNumOfClients() <= 1);

		do {
			System.out.println("Which port would you like to use?");
			p.setPort(in.nextInt());
		} while (p.getPort() < 1024 || p.getPort() > 64000);

		new File("./Properties").mkdir();
		xml.writeToXml(FILE_NAME, p);
		in.close();

		return p;
	}

	/**
	 * Sets the properties.
	 *
	 * @param port the port
	 * @param numOfClients the number of clients
	 * @return the server properties
	 */
	public ServerProperties setProperties(int port, int numOfClients){
		this.setPort(port);
		this.setNumOfClients(numOfClients);
		return this;
	}

	/**
	 * Load properties.
	 *
	 * @return the server properties
	 */
	public ServerProperties loadProperties(){
		File file = new File(FILE_NAME);
		if(!file.exists()){
			ServerProperties p = this.createProperties();
			this.setNumOfClients(p.getNumOfClients());
			this.setPort(p.getPort());
			return this;
		}
		else {
			myXMLEncodeDecode decode = new myXMLEncodeDecode();
			ServerProperties p = (ServerProperties) decode.readFromXml(FILE_NAME);
			this.setNumOfClients(p.getNumOfClients());
			this.setPort(p.getPort());
			return this;
		}
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
