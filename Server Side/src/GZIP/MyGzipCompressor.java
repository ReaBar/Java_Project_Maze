package GZIP;


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;

/**
 * The Class myGzipCompressor.
 * 
 * compress and deCompress the solution HashMap to a .gz file
 * 
 * @author Rea Bar and Tom Eileen Hirsch
 */
public class MyGzipCompressor {
	private static final String SOLUTION_HASHMAP_PATH = "./Properties/Hashmap.gz";
	/**
	 * Compress.
	 *
	 * @param hash the solution hashMap
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void compress(HashMap<Maze3d, Solution<Position>> hash) throws IOException{  
		FileOutputStream fos = new FileOutputStream(SOLUTION_HASHMAP_PATH);
		GZIPOutputStream gzip = new GZIPOutputStream(fos);
		ObjectOutputStream oos = new ObjectOutputStream(gzip);
		oos.writeObject(hash);
		oos.close();
	}

	/**
	 * DeCompress.
	 *
	 * @return the hash map
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public HashMap<Maze3d, Solution<Position>> decompress() throws IOException {
		FileInputStream fin = new FileInputStream(SOLUTION_HASHMAP_PATH);
		GZIPInputStream gis = new GZIPInputStream(fin);
		ObjectInputStream ois = new ObjectInputStream(gis);
		HashMap<Maze3d, Solution<Position>> hash = null;
		try {
			hash = (HashMap<Maze3d, Solution<Position>>) ois.readObject();
			ois.close();
			return hash;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		ois.close();
		return hash;
	}

}

