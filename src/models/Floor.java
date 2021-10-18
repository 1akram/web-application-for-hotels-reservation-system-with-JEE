package models;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Floor {
	private int id;
	private String name;
	
//	**************
	public Floor() { }
	public Floor(int id,String name) {
		this.id=id;
		this.name=name;
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
//	***************
	public static int addFloor(String name) {
		DbConnection cnx=new DbConnection();
		try {
		String quary="INSERT INTO floor (name)VALUES(?);";
		cnx.preparedStatement= cnx.connection.prepareStatement(quary);
		cnx.preparedStatement.setString(1, name);
		cnx.preparedStatement.execute();
		cnx.preparedStatement.close();
		cnx.connection.close();
		
		return -1;
		}catch(SQLException e) {
			System.out.println(e.getErrorCode());
			System.out.println("from Floor add "+e.getMessage());
			return e.getErrorCode();
			
		}
	}
	
//	*****************
	public static int deleteFloor(int id) {
		DbConnection cnx=new DbConnection();
		
		try {
		String quary="DELETE FROM  floor  WHERE id=?";
		cnx.preparedStatement= cnx.connection.prepareStatement(quary);
		cnx.preparedStatement.setInt(1, id);
		cnx.preparedStatement.executeUpdate();
		cnx.preparedStatement.close();
		cnx.connection.close();
		Room.deleteRoomsOn(id);
		
		return -1;
		}catch(SQLException e) {
			System.out.println(e.getErrorCode());
			System.out.println("from Floor delete "+e.getMessage());
			return e.getErrorCode();
			}
	}
//	*********************
	public static Floor[] getFloors() {
		List<Floor> _floors=new ArrayList<Floor>();
		Floor[] floors=null;
		DbConnection cnx=new DbConnection();
		try {
			String quary="SELECT * FROM  floor ";
			cnx.statement=cnx.connection.createStatement();
			cnx.result=cnx.statement.executeQuery(quary);
			
			while(cnx.result.next()) {
				int id =cnx.result.getInt("id");
				String name=cnx.result.getString("name");
				_floors.add(new Floor(id,name));
			}
			floors =new Floor[_floors.size()];
			for(int i=0;i<_floors.size();i++) 
				floors[i]=_floors.get(i);
			cnx.result.close();
			cnx.statement.close();
			cnx.connection.close();
			
		}catch(SQLException e) {
			System.out.println(e.getErrorCode());
			System.out.println("from Floor getAll "+e.getMessage());
			}
		
		return floors;
		
	}
//	************
	public static Floor getFloorById(int id) {
		Floor floor=null;
		
		DbConnection cnx=new DbConnection();
		try {
		String quary="SELECT * FROM  floor  WHERE id=?";
		cnx.preparedStatement= cnx.connection.prepareStatement(quary);
		cnx.preparedStatement.setInt(1, id);
		cnx.result=cnx.preparedStatement.executeQuery();
		if(cnx.result.next())
			floor=new Floor(cnx.result.getInt("id"),cnx.result.getString("name"));
		cnx.result.close();
		cnx.preparedStatement.close();
		cnx.connection.close();
		
		
		}catch(SQLException e) {
			System.out.println(e.getErrorCode());
			System.out.println("from Floor GetById "+e.getMessage());
		}
		return floor;
		
	}
//**********************
	public static boolean isFloorExiste(int id) {
		Floor floor =Floor.getFloorById(id);
		if(floor==null)
			return false;
		return true;
		
		
	}
	
}
