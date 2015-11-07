package Hibernate;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Writer;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import com.mysql.jdbc.Blob;
import com.mysql.jdbc.Clob;
import com.mysql.jdbc.PreparedStatement;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;


/**
 *
 * @author 
 */
public class MySql {
	private static int numOfRows;
	
	public static int getNumOfRows() {
		readFromDB();
		return numOfRows;
	}

	/**
	 * @param args the command line arguments
	 */
	// JDBC driver name and database URL
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	static final String DB_URL = "jdbc:mysql://localhost/mazehibernate";

	//  Database credentials
	static final String USER = "root";
	static final String PASS = "root";

	public static void writeToDB(Maze3d maze, Solution<Position> solution){
		Connection conn = null;
		Statement stmt = null;
		PreparedStatement pstmt = null;
		try{
			//STEP 2: Register JDBC driver
			Class.forName("com.mysql.jdbc.Driver");

			//STEP 3: Open a connection
			System.out.println("Connecting to database...");
			conn = DriverManager.getConnection(DB_URL,USER,PASS);
			Blob blob = (Blob) conn.createBlob();
			//Blob blob2 = (Blob) conn.createBlob();

			//STEP 4: Execute a query
			System.out.println("Inserting records into the table...");
			stmt = conn.createStatement();

			String sql = "INSERT INTO maze3d(Maze3dCol,SolutionCol) " + "VALUES(?,?)";
			blob.setBytes(1, maze.toByteArray());
			pstmt = (PreparedStatement) conn.prepareStatement(sql);
			pstmt.setObject(1, maze);
			pstmt.setObject(2, solution);
			pstmt.executeUpdate();

			System.out.println("Inserted records into the table...");

			//STEP 6: Clean-up environment
			stmt.close();
			conn.close();
		}catch(SQLException se){
			//Handle errors for JDBC
			se.printStackTrace();
		}catch(Exception e){
			//Handle errors for Class.forName
			e.printStackTrace();
		}finally{
			//finally block used to close resources
			try{
				if(stmt!=null)
					stmt.close();
			}catch(SQLException se2){
			}// nothing we can do
			try{
				if(conn!=null)
					conn.close();
			}catch(SQLException se){
				se.printStackTrace();
			}
		}
	}

	public static HashMap<Maze3d,Solution<Position>> readFromDB(){
		Connection conn = null;
		Statement stmt = null;
		PreparedStatement pstmt = null;
		HashMap<Maze3d,Solution<Position>> hm = new HashMap<>();
		try{
			//STEP 2: Register JDBC driver
			Class.forName("com.mysql.jdbc.Driver");

			//STEP 3: Open a connection
			System.out.println("Connecting to database...");
			conn = DriverManager.getConnection(DB_URL,USER,PASS);

			//STEP 4: Execute a query
			System.out.println("Creating statement...");
			stmt = conn.createStatement();
			String sql;
			sql = "SELECT * FROM maze3d";
			pstmt = (PreparedStatement) conn.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery(sql);


			//STEP 5: Extract data from result set
			while(rs.next()){
				//Retrieve by column name
				
				byte[] maze  = (byte[]) rs.getObject("Maze3dCol");
				byte[] sol = (byte[]) rs.getObject("SolutionCol");
				//Display values
				//System.out.print("ID: " + id);
				ByteArrayInputStream in = new ByteArrayInputStream(maze);
				ObjectInputStream is = new ObjectInputStream(in);
				Object obj1 = is.readObject();
				ByteArrayInputStream in2 = new ByteArrayInputStream(sol);
				ObjectInputStream is2 = new ObjectInputStream(in2);
				Object obj2 = is2.readObject();
				hm.put((Maze3d) obj1, (Solution<Position>) obj2);
				System.out.print("Maze3d: " + obj1);
				System.out.println("Solution: " + obj2);
			}
			//STEP 6: Clean-up environment
			rs.close();
			stmt.close();
			conn.close();
		}catch(SQLException se){
			//Handle errors for JDBC
			se.printStackTrace();
		}catch(Exception e){
			//Handle errors for Class.forName
			e.printStackTrace();
		}finally{
			//finally block used to close resources
			try{
				if(stmt!=null)
					stmt.close();
			}catch(SQLException se2){
			}// nothing we can do
			try{
				if(conn!=null)
					conn.close();
			}catch(SQLException se){
				se.printStackTrace();
			}//end finally try
		}//end try
		numOfRows = hm.size();
		return hm;

	}
}
