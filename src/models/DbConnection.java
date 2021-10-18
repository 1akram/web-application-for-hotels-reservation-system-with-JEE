package models;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.TimeZone;
public class DbConnection {
	private String dbUserName="root";
	private String dbPassword="rootpass";
	private String dbName="hoteldb";
	private String dbHost="127.0.0.1";
	
	public Connection connection;
	public Statement statement;
	public PreparedStatement preparedStatement;
	public ResultSet result;
	public DbConnection() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println("from Db loadDriver "+e.getMessage());

		}
		try {
			String url="jdbc:mysql://"+dbHost+":3306/"+dbName+"?serverTimezone="+TimeZone.getDefault().getID()+"&useTimezone=false&useSSPSCompatibleTimezoneShift=false&useJDBCCompliantTimezoneShift=false&useLegacyDatetimeCode=false";
			connection=DriverManager.getConnection(url,dbUserName,dbPassword);
			
		}
		catch(SQLException e) { 
			e.printStackTrace();
			System.out.println("from Db connection "+e.getMessage());

			}
	}

	

}
