import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.DatabaseMetaData;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SqlKeywords {

	public static String url = "jdbc:sqlite:temp.db";
	
	public static void main(String[] args) {
		checkDatabase();
		//removeData("keywords", "name", "temp");
		selectData("keywords");
	}
	
	//Checks if database is on local machine
	 public static void checkDatabase() 
	 {
		 String dbName = "temp.db";
		 File file = new File(dbName);

		  if(file.exists() == true)
	     {
	         System.out.print("This database name already exists\n");
	     }
	     else
	     {   
	    	createNewDatabase();
	    	createNewTable("keywords");
	    	addKeywords("Resources/Keywords.txt", "../Resources/Keywords.txt");
	     }
	 }

	public static boolean connect() {
	        // SQLite connection string
	        Connection conn = null;
	        boolean check = false;
	        try {
	            conn = DriverManager.getConnection(url);
	            check = true;
	        } catch (SQLException e) {
	            System.out.println(e.getMessage());
	        }
	        return check;
	    }
	
	//CREATE A DATABASE
	 public static boolean createNewDatabase() 
	 {
		 boolean check = false;
        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println("The driver name is " + meta.getDriverName());
                System.out.println("A new database has been created.");
                check = true;
            }
 
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return check;
    }

	//CREATE A TABLE
	public static boolean createNewTable(String tableName) {
        // SQL statement for creating a new table
		boolean check = false;
        String sql = "CREATE TABLE IF NOT EXISTS " + tableName +" (\n"
        		+ "id int PRIMARY KEY, name VARCHAR(50), category VARCHAR(50));";
        try (Connection conn = DriverManager.getConnection(url);
                Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Table " + tableName + " Created");
            check = true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return check;
    }
	
	public static boolean insertData(String tableName, String nameValue, String catValue) {
		boolean check = false;
		String command = "INSERT INTO " + tableName + "(name, category) \n"
			+ "VALUES ('" + nameValue + "', '" + catValue + "')";
		
		try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) 
		{
            stmt.execute(command);
            System.out.println(nameValue + "," + catValue + " inserted into " + tableName + ".");
            check = true;
        } 
		
		catch (SQLException e) {
            System.out.println(e.getMessage());
        }
		return check;
	}
	
	public static boolean removeData(String tableName, String colName, String value) {
		boolean check = false;
		String command = "DELETE FROM " + tableName + "\n"
				+ "WHERE " + colName + "='" + value +"'";
		
		try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) 
		{
            stmt.execute(command);
            System.out.println(value + " deleted from " + tableName + ".");
            check = true;
        } 
		
		catch (SQLException e) {
            System.out.println(e.getMessage());
        }
		return check;
	}
	
	public static boolean selectData(String tableName) {
		String command = "SELECT * FROM " + tableName;
		boolean check = false;
		ResultSet results;
		
		try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) 
		{
            results = stmt.executeQuery(command);
            System.out.println("Name\t\tCategory");
            System.out.println("------\t\t-------");
            while (results.next()) {
            	String name = results.getString("name");
            	String category = results.getString("category");
            	
            	System.out.println(name + "\t\t" + category);
            }
            check = true;
        } 
		catch (SQLException e) {
            System.out.println(e.getMessage());
        }
		return check;
	}
	
	public static boolean addKeywords(String fileName, String altFilename)
	{
		List<String> list = new ArrayList<>();
		boolean check = false;

		try (BufferedReader br = Files.newBufferedReader(Paths.get(fileName))) {

			//br returns as stream and convert it into a List
			list = br.lines().collect(Collectors.toList());
			check = true;

		} catch (IOException e) {
			try (BufferedReader br = Files.newBufferedReader(Paths.get("../../Resources/Keywords.txt"))){
				list = br.lines().collect(Collectors.toList());
				check = true;
			}catch(Exception ex){
				//ex.printStackTrace();
				try (BufferedReader br = Files.newBufferedReader(Paths.get(altFilename))) {

					//br returns as stream and convert it into a List
					list = br.lines().collect(Collectors.toList());
					check = true;

				} catch (IOException exx) {
					
					exx.printStackTrace();
				}
			}
		}


		for (int i = 0; i < list.size(); i++)
		{
			String temp = list.get(i);
			String[] result = temp.split("\\s"); //SPLIT STRING WITH SPACES

			insertData("keywords", result[0], result[1]);			
		}

		return check;
	}

}
