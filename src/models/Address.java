package models;

import java.sql.SQLException;
import java.sql.Statement;

public class Address {
	private int id;
	private String street;
	private String city;
	private String state;
	private String zipCode;
//	*************
	public Address() { };
	public Address(int id,String street,String city ,String state,String zipCode) {
		this.id=id;
		this.street=street;
		this.city=city;
		this.state=state;
		this.zipCode=zipCode;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getZipCode() {
		return zipCode;
	}
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
//	*************************
	public static int[] addAddress(String street,String city,String state,String zipCode) {
		DbConnection cnx=new DbConnection();
		try {
		String quary="INSERT INTO address (street,city,state,zipCode)VALUES(?,?,?,?);";
		cnx.preparedStatement= cnx.connection.prepareStatement(quary,Statement.RETURN_GENERATED_KEYS);
		cnx.preparedStatement.setString(1, street);
		cnx.preparedStatement.setString(2, city);
		cnx.preparedStatement.setString(3, state);
		cnx.preparedStatement.setString(4, zipCode);
		cnx.preparedStatement.executeUpdate();
		cnx.result=cnx.preparedStatement.getGeneratedKeys();
		int _id = 0;
		if(cnx.result.next())
			_id=cnx.result.getInt(1);
		cnx.preparedStatement.close();
		cnx.connection.close();
		return new int[] {-1,_id};
		}catch(SQLException e) {
			System.out.println(e.getErrorCode());
			System.out.println("from Address add "+e.getMessage());
			return new int[] {-2,e.getErrorCode()};
		}
	}
	
//	*********************
	public static int editAddress(int id ,String street,String city,String state,String zipCode) {
		DbConnection cnx=new DbConnection();
		try {
		String quary="UPDATE  address SET street=?,city=?,state=?,zipCode=?WHERE id=?;";
		cnx.preparedStatement= cnx.connection.prepareStatement(quary);
		cnx.preparedStatement.setString(1, street);
		cnx.preparedStatement.setString(2, city);
		cnx.preparedStatement.setString(3, state);
		cnx.preparedStatement.setString(4, zipCode);
		cnx.preparedStatement.setInt(5, id);
		cnx.preparedStatement.executeUpdate();
		cnx.preparedStatement.close();
		cnx.connection.close();
		return  -1;
		}catch(SQLException e) {
			System.out.println(e.getErrorCode());
			System.out.println("from Address edit "+e.getMessage());
			return e.getErrorCode();
		}
	}
	
//	*********************
	public static int deleteAddress(int id) {
		DbConnection cnx=new DbConnection();
		try {
		String quary="DELETE FROM  address  WHERE id=?";
		cnx.preparedStatement= cnx.connection.prepareStatement(quary);
		cnx.preparedStatement.setInt(1, id);
		cnx.preparedStatement.executeUpdate();
		cnx.preparedStatement.close();
		cnx.connection.close();
		
		return -1;
		}catch(SQLException e) {
			System.out.println(e.getErrorCode());
			System.out.println("from Address delete "+e.getMessage());
			return e.getErrorCode();
		}
	}
//	*********************

//	*********************
	public static Address getAddressById(int id) {
		Address address=null;
		
		DbConnection cnx=new DbConnection();
		try {
		String quary="SELECT * FROM  address  WHERE id=?";
		cnx.preparedStatement= cnx.connection.prepareStatement(quary);
		cnx.preparedStatement.setInt(1, id);
		cnx.result=cnx.preparedStatement.executeQuery();
		if(cnx.result.next())
			address=new Address(cnx.result.getInt("id"),cnx.result.getString("street"),cnx.result.getString("city"),cnx.result.getString("state"),cnx.result.getString("zipCode"));
		cnx.result.close();
		cnx.preparedStatement.close();
		cnx.connection.close();
		
		
		}catch(SQLException e) {
			System.out.println(e.getErrorCode());
			System.out.println("from Address GetById "+e.getMessage());
		}
		return address;
		
	}
	
	
}
