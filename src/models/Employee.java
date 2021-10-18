package models;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Employee extends Client{

	public Employee() {
		super();
		
	}

	public Employee(int id, String email, String password, String firstName, String lastName, Date birthDay,
			String gender, String phone, Address address) {
		super(id, email, password, firstName, lastName, birthDay, gender, phone, address);
	}
	
//	****************
	public static int addEmployee(String email, String password, String firstName, String lastName, Date birthDay,
			String gender, String phone, String street,String city,String state,String zipCode) {
		
		int[] _addresResult=Address.addAddress(street, city, state, zipCode);
		if(_addresResult[0]==-2)
			return _addresResult[1];
		DbConnection cnx=new DbConnection();
		try {
		
		String quary="INSERT INTO employee (firstName,lastName,birthDay,email,password,gender,phone,addressId)VALUES(?,?,?,?,?,?,?,?);";
		cnx.preparedStatement= cnx.connection.prepareStatement(quary);
		cnx.preparedStatement.setString(1, firstName);
		cnx.preparedStatement.setString(2, lastName);
		cnx.preparedStatement.setDate(3, new java.sql.Date(birthDay.getTime()));
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
			System.out.println("from Employee add "+e.getMessage());
			return e.getErrorCode();
			
		}
	}
	
//	*****************

	public static int editEmployee(int id,String email, String password, String firstName, String lastName, Date birthDay,
			String gender, String phone, String street,String city,String state,String zipCode) {
		if(!Employee.isEmployeeExiste(id))
			return 0;
		Employee em=Employee.getEmployeeById(id);
		int addressId=em.address.getId();
		int _editResult=Address.editAddress(addressId,street, city, state, zipCode);
		if(_editResult!=-1)
			return _editResult;
		DbConnection cnx=new DbConnection();
		try {
			String quary;
		if(password!=null) {
			quary="UPDATE  employee SET  firstName=?,lastName=?,birthDay=?,email=?,password=?,gender=?,phone=?WHERE id=?;";
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
			quary="UPDATE  employee SET firstName=?,lastName=?,birthDay=?,email=?,gender=?,phone=?WHERE id=?;";
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
			System.out.println("from Employee edit "+e.getMessage());
			return e.getErrorCode();
			
		}
	}
	
//	*****************
	public static int deleteEmployee(int id) {
		DbConnection cnx=new DbConnection();
		if(!Employee.isEmployeeExiste(id))
			return-1;
		Employee em=Employee.getEmployeeById(id);
		
		int _addressResult=Address.deleteAddress(em.address.getId());
		if(_addressResult!=-1)
			return _addressResult;
		try {
		String quary="DELETE FROM  employee  WHERE id=?";
		cnx.preparedStatement= cnx.connection.prepareStatement(quary);
		cnx.preparedStatement.setInt(1, id);
		cnx.preparedStatement.executeUpdate();
		cnx.preparedStatement.close();
		cnx.connection.close();
		
		return -1;
		}catch(SQLException e) {
			System.out.println(e.getErrorCode());
			System.out.println("from Employee delete "+e.getMessage());
			return e.getErrorCode();
			}
	}
//	*********************
	public static Employee[] getEmployees() {
		List<Employee> _employees=new ArrayList<Employee>();
		Employee[] employees=null;
		DbConnection cnx=new DbConnection();
		try {
			String quary="SELECT * FROM  employee ";
			cnx.statement=cnx.connection.createStatement();
			cnx.result=cnx.statement.executeQuery(quary);
			
			while(cnx.result.next()) {
				_employees.add(new Employee(cnx.result.getInt("id"),cnx.result.getString("email"),cnx.result.getString("password"),cnx.result.getString("firstName"),cnx.result.getString("lastName"),cnx.result.getDate("birthDay"),
						cnx.result.getString("gender"),cnx.result.getString("phone"),Address.getAddressById(cnx.result.getInt("addressId"))));
			}
			employees =new Employee[_employees.size()];
			for(int i=0;i<_employees.size();i++) 
				employees[i]=_employees.get(i);
			cnx.result.close();
			cnx.statement.close();
			cnx.connection.close();
			
		}catch(SQLException e) {
			System.out.println(e.getErrorCode());
			System.out.println("from Employee getAll "+e.getMessage());
			}
		
		return employees;
		
	}
//	************
	public static Employee getEmployeeById(int id) {
		Employee employee=null;
		
		DbConnection cnx=new DbConnection();
		try {
		String quary="SELECT * FROM  employee  WHERE id=?";
		cnx.preparedStatement= cnx.connection.prepareStatement(quary);
		cnx.preparedStatement.setInt(1, id);
		cnx.result=cnx.preparedStatement.executeQuery();
		if(cnx.result.next())
			employee=new Employee(cnx.result.getInt("id"),cnx.result.getString("email"),cnx.result.getString("password"),cnx.result.getString("firstName"),cnx.result.getString("lastName"),cnx.result.getDate("birthDay"),
					cnx.result.getString("gender"),cnx.result.getString("phone"),Address.getAddressById(cnx.result.getInt("addressId")));
		cnx.result.close();
		cnx.preparedStatement.close();
		cnx.connection.close();
		
		
		}catch(SQLException e) {
			System.out.println(e.getErrorCode());
			System.out.println("from Employee getById "+e.getMessage());
		}
		return employee;
		
	}
//	************
	public static Employee getEmployeeByEmail(String email) {
		Employee employee=null;
		
		DbConnection cnx=new DbConnection();
		try {
		String quary="SELECT * FROM  employee  WHERE email=?";
		cnx.preparedStatement= cnx.connection.prepareStatement(quary);
		cnx.preparedStatement.setString(1, email);
		cnx.result=cnx.preparedStatement.executeQuery();
		if(cnx.result.next())
			employee=new Employee(cnx.result.getInt("id"),cnx.result.getString("email"),cnx.result.getString("password"),cnx.result.getString("firstName"),cnx.result.getString("lastName"),cnx.result.getDate("birthDay"),
					cnx.result.getString("gender"),cnx.result.getString("phone"),Address.getAddressById(cnx.result.getInt("addressId")));
		cnx.result.close();
		cnx.preparedStatement.close();
		cnx.connection.close();
		
		
		}catch(SQLException e) {
			System.out.println(e.getErrorCode());
			System.out.println("from Employee getByEmail "+e.getMessage());
		}
		return employee;
		
	}

	//**********************
	public static boolean isEmployeeExiste(int id) {
		Employee employee =Employee.getEmployeeById(id);
		if(employee==null)
			return false;
		return true;
		
		
	}
}
