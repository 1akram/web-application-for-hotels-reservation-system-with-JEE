package models;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Room {
	private int id ;
	private int number;	
	public Floor floor;
	public Type type;
//	**************
	public Room() { }

	public Room(int id, int number, Floor floor, Type type) {
		super();
		this.id = id;
		this.number = number;
		this.floor = floor;
		this.type = type;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
//########################
//#######################
	public boolean isBooked(Date checkIn,Date checkOut) {
		
			DbConnection cnx = new DbConnection();
			try {

				String quary = "SELECT * FROM  booking WHERE roomNumber=? AND checkIn < ? AND 	checkOut >?; ";
				cnx.preparedStatement = cnx.connection.prepareStatement(quary);
				cnx.preparedStatement.setInt(1, this.getNumber());
				cnx.preparedStatement.setDate(2, new java.sql.Date(checkOut.getTime()));
				cnx.preparedStatement.setDate(3, new java.sql.Date(checkIn.getTime()));
				cnx.result=cnx.preparedStatement.executeQuery();
				if(cnx.result.next()) {
					cnx.preparedStatement.close();
					cnx.connection.close();
					return true;
				}	
				return false;
			} catch (SQLException e) {
				System.out.println(e.getErrorCode());
				System.out.println("from Rooom add "+e.getMessage());
				
			}

		return true;
		
	}

//#########################
//	*************************
	public static int addRoom(int number, int floorId, int typeId) {
		DbConnection cnx = new DbConnection();
		try {
			if(!Floor.isFloorExiste(floorId)||!Type.isTypeExiste(typeId))
				return 0;
			String quary = "INSERT INTO room (number,floorId,typeId)VALUES(?,?,?);";
			cnx.preparedStatement = cnx.connection.prepareStatement(quary);
			cnx.preparedStatement.setInt(1, number);
			cnx.preparedStatement.setInt(2, floorId);
			cnx.preparedStatement.setInt(3, typeId);
			cnx.preparedStatement.executeUpdate();
			cnx.preparedStatement.close();
			cnx.connection.close();
			return -1;
		} catch (SQLException e) {
			System.out.println(e.getErrorCode());
			System.out.println("from Rooom add "+e.getMessage());
			return e.getErrorCode();
		}
	}
//	*****************
	public static int deleteRoom(int id) {
		DbConnection cnx=new DbConnection();
		try {
		String quary="DELETE FROM  room  WHERE id=?";
		cnx.preparedStatement= cnx.connection.prepareStatement(quary);
		cnx.preparedStatement.setInt(1, id);
		cnx.preparedStatement.executeUpdate();
		cnx.preparedStatement.close();
		cnx.connection.close();
		
		return -1;
		}catch(SQLException e) {
			System.out.println(e.getErrorCode());
			System.out.println("from Rooom delete "+e.getMessage());
			return e.getErrorCode();
			}
	}
//	*********************
	public static int editRoom(int id, int number, int floorId, int typeId) {
		if (!Room.isRoomExiste(id)||!Floor.isFloorExiste(floorId)||!Type.isTypeExiste(typeId))
			return 0;
		DbConnection cnx = new DbConnection();
		try {

			String quary = "UPDATE  room SET  number=?,floorId=?,TypeId=? WHERE id=?;";
			cnx.preparedStatement = cnx.connection.prepareStatement(quary);
			cnx.preparedStatement.setInt(1, number);
			cnx.preparedStatement.setInt(2, floorId);
			cnx.preparedStatement.setInt(3, typeId);
			cnx.preparedStatement.setInt(4, id);
			cnx.preparedStatement.executeUpdate();
			cnx.preparedStatement.close();
			cnx.connection.close();
			return -1;

		} catch (SQLException e) {
			System.out.println(e.getErrorCode());
			System.out.println("from Rooom edit "+e.getMessage());
			return e.getErrorCode();

		}
	}
//	*****************
	public static Room[] getRooms() {
		List<Room> _rooms=new ArrayList<Room>();
		Room[] rooms=null;
		DbConnection cnx=new DbConnection();
		try {
			String quary="SELECT * FROM  room ";
			cnx.statement=cnx.connection.createStatement();
			cnx.result=cnx.statement.executeQuery(quary);
			
			while(cnx.result.next()) {
				_rooms.add(new Room(cnx.result.getInt("id"),cnx.result.getInt("number"),
						Floor.getFloorById(cnx.result.getInt("floorId")),Type.getTypeById(cnx.result.getInt("typeId"))));
			}
			rooms =new Room[_rooms.size()];
			for(int i=0;i<_rooms.size();i++) 
				rooms[i]=_rooms.get(i);
			cnx.result.close();
			cnx.statement.close();
			cnx.connection.close();
			
		}catch(SQLException e) {
			System.out.println(e.getErrorCode());
			System.out.println("from Rooom getAll "+e.getMessage());
			}
		
		return rooms;
		
	}
//	************
	public static int deleteRoomsOn(int floorId) {
		DbConnection cnx = new DbConnection();
		try {
			String quary = "DELETE FROM  room  WHERE floorId=?";
			cnx.preparedStatement = cnx.connection.prepareStatement(quary);
			cnx.preparedStatement.setInt(1, floorId);
			cnx.preparedStatement.executeUpdate();
			cnx.preparedStatement.close();
			cnx.connection.close();

			return -1;
		} catch (SQLException e) {
			System.out.println(e.getErrorCode());
			System.out.println("from Room deleteOnFloor "+e.getMessage());
			return e.getErrorCode();
		}
	}
//	************
	public static int deleteRoomsOf(int typeId) {
		DbConnection cnx = new DbConnection();
		try {
			String quary = "DELETE FROM  room  WHERE typeId=?";
			cnx.preparedStatement = cnx.connection.prepareStatement(quary);
			cnx.preparedStatement.setInt(1, typeId);
			cnx.preparedStatement.executeUpdate();
			cnx.preparedStatement.close();
			cnx.connection.close();

			return -1;
		} catch (SQLException e) {
			System.out.println(e.getErrorCode());
			System.out.println("from Room deleteOfType "+e.getMessage());
			return e.getErrorCode();
		}
	}
//	*********************
	public static Room[] getRoomsOf(Floor floor) {
		List<Room> _rooms=new ArrayList<Room>();
		Room[] rooms=null;
		DbConnection cnx=new DbConnection();
		try {
			String quary="SELECT * FROM  room WHERE floorId=?";
			cnx.preparedStatement= cnx.connection.prepareStatement(quary);
			cnx.preparedStatement.setInt(1, floor.getId());
			cnx.result=cnx.preparedStatement.executeQuery();
			while(cnx.result.next()) {
				_rooms.add(new Room(cnx.result.getInt("id"),cnx.result.getInt("number"),
						Floor.getFloorById(cnx.result.getInt("floorId")),Type.getTypeById(cnx.result.getInt("typeId"))));
			}
			rooms =new Room[_rooms.size()];
			for(int i=0;i<_rooms.size();i++) 
				rooms[i]=_rooms.get(i);
			cnx.result.close();
			cnx.statement.close();
			cnx.connection.close();
		}catch(SQLException e) {
			System.out.println(e.getErrorCode());
			System.out.println("from Rooom getOfFloor "+e.getMessage());
			}
		return rooms;
	}
//	*********************
	public static Room[] getRoomsOf(Type type) {
		List<Room> _rooms=new ArrayList<Room>();
		Room[] rooms=null;
		DbConnection cnx=new DbConnection();
		try {
			String quary="SELECT * FROM  room WHERE typeId=?";
			cnx.preparedStatement= cnx.connection.prepareStatement(quary);
			cnx.preparedStatement.setInt(1, type.getId());
			cnx.result=cnx.preparedStatement.executeQuery();
			while(cnx.result.next()) {
				_rooms.add(new Room(cnx.result.getInt("id"),cnx.result.getInt("number"),
						Floor.getFloorById(cnx.result.getInt("floorId")),Type.getTypeById(cnx.result.getInt("typeId"))));
			}
			rooms =new Room[_rooms.size()];
			for(int i=0;i<_rooms.size();i++) 
				rooms[i]=_rooms.get(i);
			cnx.result.close();
			cnx.statement.close();
			cnx.connection.close();
		}catch(SQLException e) {
			System.out.println(e.getErrorCode());
			System.out.println("from Rooom getOfType "+e.getMessage());
			}
		return rooms;
	}
//	************
	public static Room getRoomById(int id) {
		Room room=null;
		DbConnection cnx=new DbConnection();
		try {
		String quary="SELECT * FROM  room  WHERE id=?";
		cnx.preparedStatement= cnx.connection.prepareStatement(quary);
		cnx.preparedStatement.setInt(1, id);
		cnx.result=cnx.preparedStatement.executeQuery();
		if(cnx.result.next())
			room=new Room(cnx.result.getInt("id"),cnx.result.getInt("number"),
					Floor.getFloorById(cnx.result.getInt("floorId")),Type.getTypeById(cnx.result.getInt("typeId")));
		cnx.result.close();
		cnx.preparedStatement.close();
		cnx.connection.close();
		}catch(SQLException e) {
			System.out.println(e.getErrorCode());
			System.out.println("from Rooom getById "+e.getMessage());
		}
		return room;
	}
//	*********************
	public static Room getRoomByNumber(int number) {
		Room room=null;
		DbConnection cnx=new DbConnection();
		try {
		String quary="SELECT * FROM  room  WHERE id=?";
		cnx.preparedStatement= cnx.connection.prepareStatement(quary);
		cnx.preparedStatement.setInt(1, number);
		cnx.result=cnx.preparedStatement.executeQuery();
		if(cnx.result.next())
			room=new Room(cnx.result.getInt("id"),cnx.result.getInt("number"),
					Floor.getFloorById(cnx.result.getInt("floorId")),Type.getTypeById(cnx.result.getInt("typeId")));
		cnx.result.close();
		cnx.preparedStatement.close();
		cnx.connection.close();
		}catch(SQLException e) {
			System.out.println(e.getErrorCode());
			System.out.println("from Rooom getBynNumber "+e.getMessage());
		}
		return room;
	}
//	*********************
	public static boolean isRoomExiste(int id) {
		Room room =Room.getRoomById(id);
		if(room==null)
			return false;
		return true;
	}
}
