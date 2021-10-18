package models;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import security.InputsValidation;

public class Client {
	private int id;
	private String email;
	private String password;
	private String firstName;
	private String lastName;
	private Date birthDay;
	private String gender;
	private String phone;
	public Address address;
	
	public Client(){}
	public Client(int id, String email, String password, String firstName, String lastName, Date birthDay,
			String gender, String phone, Address address) {
		this.id = id;
		this.email = email;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.birthDay = birthDay;
		this.gender = gender;
		this.phone = phone;
		this.address = address;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public Date getBirthDay() {
		return birthDay;
	}
	public void setBirthDay(Date birthDay) {
		this.birthDay = birthDay;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getphone() {
		return phone;
	}
	public void setphone(String phone) {
		this.phone = phone;
	}
//	****************
	public static int addClient(String email, String password, String firstName, String lastName, Date birthDay,
			String gender, String phone, String street,String city,String state,String zipCode) {
		
		int[] _addresResult=Address.addAddress(street, city, state, zipCode);
		if(_addresResult[0]==-2)
			return _addresResult[1];
		DbConnection cnx=new DbConnection();
		try {
		
		String quary="INSERT INTO client (firstName,lastName,birthDay,email,password,gender,phone,addressId)VALUES(?,?,?,?,?,?,?,?);";
		cnx.preparedStatement= cnx.connection.prepareStatement(quary);
		cnx.preparedStatement.setString(1, firstName);
		cnx.preparedStatement.setString(2, lastName);
		cnx.preparedStatement.setDate(3, new java.sql.Date(birthDay.getTime()));
		System.out.println(new java.sql.Date(birthDay.getTime()));
		cnx.preparedStatement.setString(4, email);
		cnx.preparedStatement.setString(5, password);
		cnx.preparedStatement.setString(6, gender);
		cnx.preparedStatement.setString(7, phone);
		cnx.preparedStatement.setInt(8, _addresResult[1]);
		cnx.preparedStatement.execute();
		cnx.preparedStatement.close();
		cnx.connection.close();
		
		return -1;
		}catch(SQLException e) {
			System.out.println(e.getErrorCode());
			System.out.println("from Client add "+e.getMessage());
			return e.getErrorCode();
			
		}
	}

//	*****************
	public static int editClient(int id,String email, String password, String firstName, String lastName, Date birthDay,
			String gender, String phone, String street,String city,String state,String zipCode) {
		if(!Client.isClientExiste(id))
			return 0;
		Client cl=Client.getClientById(id);
		int addressId=cl.address.getId();
		int _editResult=Address.editAddress(addressId,street, city, state, zipCode);
		if(_editResult!=-1)
			return _editResult;
		DbConnection cnx=new DbConnection();
		try {
			String quary;
		if(password!=null) {
			quary="UPDATE  client SET  firstName=?,lastName=?,birthDay=?,email=?,password=?,gender=?,phone=?WHERE id=?;";
			cnx.preparedStatement= cnx.connection.prepareStatement(quary);
			cnx.preparedStatement.setString(1, firstName);
			cnx.preparedStatement.setString(2, lastName);
			cnx.preparedStatement.setDate(3, new java.sql.Date(birthDay.getTime()));
			cnx.preparedStatement.setString(4, email);
			cnx.preparedStatement.setString(5, password);
			cnx.preparedStatement.setString(6, gender);
			cnx.preparedStatement.setString(7, phone);
			cnx.preparedStatement.setInt(8, id);
			cnx.preparedStatement.executeUpdate();
			cnx.preparedStatement.close();
			cnx.connection.close();
			
			
		}
		else {
			quary="UPDATE  client SET firstName=?,lastName=?,birthDay=?,email=?,gender=?,phone=?WHERE id=?;";
			cnx.preparedStatement= cnx.connection.prepareStatement(quary);
			cnx.preparedStatement.setString(1, firstName);
			cnx.preparedStatement.setString(2, lastName);
			cnx.preparedStatement.setDate(3, new java.sql.Date(birthDay.getTime()));
			cnx.preparedStatement.setString(4, email);
			cnx.preparedStatement.setString(5, gender);
			cnx.preparedStatement.setString(6, phone);
			cnx.preparedStatement.setInt(7, id);
			cnx.preparedStatement.executeUpdate();
			cnx.preparedStatement.close();
			cnx.connection.close();
			
		}

		return -1;


		}catch(SQLException e) {
			System.out.println(e.getErrorCode());
			System.out.println("from Client edit "+e.getMessage());
			return e.getErrorCode();
			
		}
	}
//	***************
	public static int deleteClient(int id) {
		DbConnection cnx=new DbConnection();
		if(!Client.isClientExiste(id))
			return -1;
		Client cl=Client.getClientById(id);
		int _addressResult=Address.deleteAddress(cl.address.getId());
		if(_addressResult!=-1)
			return _addressResult;	
		try {
		String quary="DELETE FROM  client  WHERE id=?";
		cnx.preparedStatement= cnx.connection.prepareStatement(quary);
		cnx.preparedStatement.setInt(1, id);
		cnx.preparedStatement.executeUpdate();
		cnx.preparedStatement.close();
		cnx.connection.close();
		
		return -1;
		}catch(SQLException e) {
			System.out.println(e.getErrorCode());
			System.out.println("from Client delete "+e.getMessage());
			return e.getErrorCode();
			}
	}
//	*********************
	public static Client[] getClients() {
		List<Client> _clients=new ArrayList<Client>();
		Client[] clients=null;
		DbConnection cnx=new DbConnection();
		try {
			String quary="SELECT * FROM  client ";
			cnx.statement=cnx.connection.createStatement();
			cnx.result=cnx.statement.executeQuery(quary);
			
			while(cnx.result.next()) {
				_clients.add(new Client(cnx.result.getInt("id"),cnx.result.getString("email"),cnx.result.getString("password"),cnx.result.getString("firstName"),cnx.result.getString("lastName"),cnx.result.getDate("birthDay"),
						cnx.result.getString("gender"),cnx.result.getString("phone"),Address.getAddressById(cnx.result.getInt("addressId"))));
			}
			clients =new Client[_clients.size()];
			for(int i=0;i<_clients.size();i++) 
				clients[i]=_clients.get(i);
			cnx.result.close();
			cnx.statement.close();
			cnx.connection.close();
			
		}catch(SQLException e) {
			System.out.println(e.getErrorCode());
			System.out.println("from Client getAll "+e.getMessage());
			}
		
		return clients;
		
	}
//	************
	public static Client getClientById(int id) {
		Client client=null;
		
		DbConnection cnx=new DbConnection();
		try {
		String quary="SELECT * FROM  client  WHERE id=?";
		cnx.preparedStatement= cnx.connection.prepareStatement(quary);
		cnx.preparedStatement.setInt(1, id);
		cnx.result=cnx.preparedStatement.executeQuery();
		if(cnx.result.next())
			client=new Client(cnx.result.getInt("id"),cnx.result.getString("email"),cnx.result.getString("password"),cnx.result.getString("firstName"),cnx.result.getString("lastName"),cnx.result.getDate("birthDay"),
					cnx.result.getString("gender"),cnx.result.getString("phone"),Address.getAddressById(cnx.result.getInt("addressId")));
		cnx.result.close();
		cnx.preparedStatement.close();
		cnx.connection.close();
		
		
		}catch(SQLException e) {
			System.out.println(e.getErrorCode());
			System.out.println("from Client getById "+e.getMessage());
		}
		return client;
		
	}
	
//	************
	public static Client getClientByEmail(String email) {
		Client client=null;
		
		DbConnection cnx=new DbConnection();
		try {
		String quary="SELECT * FROM  client  WHERE email=?";
		cnx.preparedStatement= cnx.connection.prepareStatement(quary);
		cnx.preparedStatement.setString(1, email);
		cnx.result=cnx.preparedStatement.executeQuery();
		if(cnx.result.next())
			client=new Client(cnx.result.getInt("id"),cnx.result.getString("email"),cnx.result.getString("password"),cnx.result.getString("firstName"),cnx.result.getString("lastName"),cnx.result.getDate("birthDay"),
					cnx.result.getString("gender"),cnx.result.getString("phone"),Address.getAddressById(cnx.result.getInt("addressId")));
		cnx.result.close();
		cnx.preparedStatement.close();
		cnx.connection.close();
		
		
		}catch(SQLException e) {
			System.out.println(e.getErrorCode());
			System.out.println("from Client GetByEmail "+e.getMessage());
		}
		return client;
		
	}
//**********************
		public static boolean isClientExiste(int id) {
			Client client =Client.getClientById(id);
			if(client==null)
				return false;
			return true;
			
			
		}
//	*************************	
		public  int booking(Date checkIn, Date checkOut,Client client, Room room,String typeOfPayment) {
			int totalPrice=InputsValidation.daysBetween(checkIn, checkOut)*room.type.getPrice();
			DbConnection cnx = new DbConnection();
			try {
				String quary = "INSERT INTO booking (checkIn,checkOut,totalPrice,clientEmail,roomNumber,roomType,typeOfPayment)VALUES(?,?,?,?,?,?,?);";
				cnx.preparedStatement = cnx.connection.prepareStatement(quary);
				cnx.preparedStatement.setDate(1, new java.sql.Date(checkIn.getTime()));
				cnx.preparedStatement.setDate(2, new java.sql.Date(checkOut.getTime()));
				cnx.preparedStatement.setInt(3,totalPrice );
				cnx.preparedStatement.setString(4, client.getEmail());
				cnx.preparedStatement.setInt(5, room.getNumber());
				cnx.preparedStatement.setString(6, room.type.getName());
				cnx.preparedStatement.setString(7, typeOfPayment);
				cnx.preparedStatement.executeUpdate();
				cnx.preparedStatement.close();
				cnx.connection.close();
				return -1;
			} catch (SQLException e) {
				System.out.println(e.getErrorCode());
				System.out.println("from Client Booking "+e.getMessage());
				return e.getErrorCode();
			}

		}
//		*************************
//		*************************
			public  static Room[] search(Date checkIn, Date checkOut,int guests) {
				List<Room> _rooms=new ArrayList<Room>();
				Room[] rooms=Room.getRooms();

				for(Room rm :rooms) {
					if(rm.type.getGuests()<guests||rm.isBooked(checkIn,checkOut))
						continue;
					else 
						_rooms.add(rm);
				}
				if(!(_rooms.size() > 0))
					return null;
				Room[] availableRooms=new Room[_rooms.size()];
				for(int i=0;i<_rooms.size();i++) 
					availableRooms[i]=_rooms.get(i);
				return availableRooms;
			}
//			*****************
		
		
		
		
		
}
