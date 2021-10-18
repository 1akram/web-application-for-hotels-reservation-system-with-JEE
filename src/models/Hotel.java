package models;

import java.sql.SQLException;


public class Hotel {

	private int id;
	private String name;
	private String email;
	private String phone;
	private String description;
	public String address;
	public Hotel() {};
	public Hotel(int id, String name, String email, String phone,String description, String address) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.phone=phone;
		this.description = description;
		this.address = address;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

//*****************************
//	****************************
	public static int addHotelInfo(String email, String name, String phone,String description,String address) {
		
		DbConnection cnx=new DbConnection();
		try {
		
			String quary="UPDATE  hotel SET  name=?,email=?,description=?,address=?,phone=?WHERE id=1;";
			cnx.preparedStatement= cnx.connection.prepareStatement(quary);
			cnx.preparedStatement.setString(1, name);
			cnx.preparedStatement.setString(2, email);
			cnx.preparedStatement.setString(3, description);
			cnx.preparedStatement.setString(4, address);
			cnx.preparedStatement.setString(5, phone);
			cnx.preparedStatement.executeUpdate();
			cnx.preparedStatement.close();
			cnx.connection.close();
		return -1;
		}catch(SQLException e) {
			System.out.println(e.getErrorCode());
			System.out.println("from hotelInfo add "+e.getMessage());
			return e.getErrorCode();
			
		}
	}
	
//	*****************
//	************
	public static Hotel getHotelInfo() {
		Hotel hotel = null;
		DbConnection cnx=new DbConnection();
		try {
		String quary="SELECT * FROM  hotel  WHERE id=1";
		cnx.preparedStatement= cnx.connection.prepareStatement(quary);
		cnx.result=cnx.preparedStatement.executeQuery();
		if(cnx.result.next())
			hotel=new Hotel(cnx.result.getInt("id"),cnx.result.getString("name"),cnx.result.getString("email"),cnx.result.getString("phone"),
					cnx.result.getString("description"),cnx.result.getString("address"));
		cnx.result.close();
		cnx.preparedStatement.close();
		cnx.connection.close();
		
		
		}catch(SQLException e) {
			System.out.println(e.getErrorCode());
			System.out.println("from hotel getinfo "+e.getMessage());
		}
		return hotel;
		
	}
	
	

	
}
