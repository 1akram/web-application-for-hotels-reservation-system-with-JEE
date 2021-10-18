package models;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Manager extends Client {

	public Manager() {
		super();
	}

	public Manager(int id, String email, String password, String firstName, String lastName, Date birthDay,
			String gender, String phoneNumber, Address address) {
		super(id, email, password, firstName, lastName, birthDay, gender, phoneNumber, address);
	}
//	****************
	public static int addManager(String email, String password, String firstName, String lastName, Date birthDay,
			String gender, String phone, String street,String city,String state,String zipCode) {
		
		int[] _addresResult=Address.addAddress(street, city, state, zipCode);
		if(_addresResult[0]==-2)
			return _addresResult[1];
		DbConnection cnx=new DbConnection();
		try {
		
		String quary="INSERT INTO manager (firstName,lastName,birthDay,email,password,gender,phone,addressId)VALUES(?,?,?,?,?,?,?,?);";
		cnx.preparedStatement= cnx.connection.prepareStatement(quary);
		cnx.preparedStatement.setString(1, firstName);
		cnx.preparedStatement.setString(2, lastName);
		cnx.preparedStatement.setDate(3, (java.sql.Date) birthDay);
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
			System.out.println("from Manager add "+e.getMessage());
			return e.getErrorCode();
			
		}
	}
	
//	*****************
	public static int deleteManager(int id) {
		DbConnection cnx=new DbConnection();
		if(!Manager.isManagerExiste(id))
			return -1;
		int _addressResult=Address.deleteAddress(Manager.getManagerById(id).address.getId());
		if(_addressResult!=-1)
			return _addressResult;	
		try {
		String quary="DELETE FROM  manager  WHERE id=?";
		cnx.preparedStatement= cnx.connection.prepareStatement(quary);
		cnx.preparedStatement.setInt(1, id);
		cnx.preparedStatement.executeUpdate();
		cnx.preparedStatement.close();
		cnx.connection.close();
		
		return -1;
		}catch(SQLException e) {
			System.out.println(e.getErrorCode());
			System.out.println("from Manager edit "+e.getMessage());
			return e.getErrorCode();
			}
	}
//	*********************
	public static Manager[] getManagers() {
		List<Manager> _managers=new ArrayList<Manager>();
		Manager[] managers=null;
		DbConnection cnx=new DbConnection();
		try {
			String quary="SELECT * FROM  manager ";
			cnx.statement=cnx.connection.createStatement();
			cnx.result=cnx.statement.executeQuery(quary);
			
			while(cnx.result.next()) {
				_managers.add(new Manager(cnx.result.getInt("id"),cnx.result.getString("email"),cnx.result.getString("password"),cnx.result.getString("firstName"),cnx.result.getString("lastName"),cnx.result.getDate("birthDay"),
						cnx.result.getString("gender"),cnx.result.getString("phone"),Address.getAddressById(cnx.result.getInt("addressId"))));
			}
			managers =new Manager[_managers.size()];
			for(int i=0;i<_managers.size();i++) 
				managers[i]=_managers.get(i);
			cnx.result.close();
			cnx.statement.close();
			cnx.connection.close();
			
		}catch(SQLException e) {
			System.out.println(e.getErrorCode());
			System.out.println("from Manager getAll "+e.getMessage());
			}
		
		return managers;
		
	}
//	************
	public static Manager getManagerById(int id) {
		Manager manager=null;
		
		DbConnection cnx=new DbConnection();
		try {
		String quary="SELECT * FROM  manager  WHERE id=?";
		cnx.preparedStatement= cnx.connection.prepareStatement(quary);
		cnx.preparedStatement.setInt(1, id);
		cnx.result=cnx.preparedStatement.executeQuery();
		if(cnx.result.next())
			manager=new Manager(cnx.result.getInt("id"),cnx.result.getString("email"),cnx.result.getString("password"),cnx.result.getString("firstName"),cnx.result.getString("lastName"),cnx.result.getDate("birthDay"),
					cnx.result.getString("gender"),cnx.result.getString("phone"),Address.getAddressById(cnx.result.getInt("addressId")));
		cnx.result.close();
		cnx.preparedStatement.close();
		cnx.connection.close();
		
		
		}catch(SQLException e) {
			System.out.println(e.getErrorCode());
			System.out.println("from Manager getById "+e.getMessage());
		}
		return manager;
		
	}
//	************
	public static Manager getManagerByEmail(String email) {
		Manager manager=null;
		
		DbConnection cnx=new DbConnection();
		try {
		String quary="SELECT * FROM  manager  WHERE email=?";
		cnx.preparedStatement= cnx.connection.prepareStatement(quary);
		cnx.preparedStatement.setString(1, email);
		cnx.result=cnx.preparedStatement.executeQuery();
		if(cnx.result.next())
			manager=new Manager(cnx.result.getInt("id"),cnx.result.getString("email"),cnx.result.getString("password"),cnx.result.getString("firstName"),cnx.result.getString("lastName"),cnx.result.getDate("birthDay"),
					cnx.result.getString("gender"),cnx.result.getString("phone"),Address.getAddressById(cnx.result.getInt("addressId")));
		cnx.result.close();
		cnx.preparedStatement.close();
		cnx.connection.close();
		
		
		}catch(SQLException e) {
			System.out.println(e.getErrorCode());
			System.out.println("from Manager getByEmail "+e.getMessage());
		}
		return manager;
		
	}
//	*************
	public static boolean isManagerExiste(int id) {
		Manager manager =Manager.getManagerById(id);
		if(manager==null)
			return false;
		return true;
		
		
	}
}
