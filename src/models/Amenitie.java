package models;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Amenitie {
	private int id;
	private String name;
	
//	**************
	public Amenitie() { }
	public Amenitie(int id,String name) {
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
	public static int addAmenities(String name) {
		DbConnection cnx=new DbConnection();
		try {
		String quary="INSERT INTO amenitie (name)VALUES(?);";
		cnx.preparedStatement= cnx.connection.prepareStatement(quary);
		cnx.preparedStatement.setString(1, name);
		cnx.preparedStatement.execute();
		cnx.preparedStatement.close();
		cnx.connection.close();
		
		return -1;
		}catch(SQLException e) {
			System.out.println(e.getErrorCode());
			System.out.println("from Amenitie add "+e.getMessage());
			return e.getErrorCode();
			
		}
	}
//	***************
	public static int addAmenitiesFor(int typeId,List<Integer>amenitieIds) {
		DbConnection cnx=new DbConnection();
		if(!Type.isTypeExiste(typeId))
			return 0;
		try {
			for (int _id : amenitieIds) {
				if (!Amenitie.isAmenitieExiste(_id))
					continue;
				String quary = "INSERT INTO amenities (amenitieId,typeId)VALUES(?,?);";
				cnx.preparedStatement = cnx.connection.prepareStatement(quary);
				cnx.preparedStatement.setInt(1, _id);
				cnx.preparedStatement.setInt(2, typeId);
				cnx.preparedStatement.executeUpdate();
				}
			cnx.preparedStatement.close();
			cnx.connection.close();
			return -1;
		}catch(NullPointerException n ){
			// there is no amenities to add them for this type ==> this type deosn't have amenities ;
			return -1;
		}catch(SQLException e) {
			System.out.println(e.getErrorCode());
			System.out.println("from Amenitie addForType "+e.getMessage());
			return e.getErrorCode();
			
		}
		
	}
//	*****************
	public static int deleteAmenities(int id) {
		DbConnection cnx=new DbConnection();
		try {
		String quary="DELETE FROM  amenitie  WHERE id=?";
		cnx.preparedStatement= cnx.connection.prepareStatement(quary);
		cnx.preparedStatement.setInt(1, id);
		cnx.preparedStatement.executeUpdate();
	
		
		quary="DELETE FROM  amenities  WHERE amenitieId=?";
		cnx.preparedStatement= cnx.connection.prepareStatement(quary);
		cnx.preparedStatement.setInt(1, id);
		cnx.preparedStatement.executeUpdate();
		cnx.preparedStatement.close();
		cnx.connection.close();
		
		
		return -1;
		}catch(SQLException e) {
			System.out.println(e.getErrorCode());
			System.out.println("from Amenitie delete "+e.getMessage());
			return e.getErrorCode();
			}
	}
//	*********************
	public static int deleteAmenitiesOf(int typeId) {
		DbConnection cnx=new DbConnection();
		try {
		String quary="DELETE FROM  amenities  WHERE typeId=?";
		cnx.preparedStatement= cnx.connection.prepareStatement(quary);
		cnx.preparedStatement.setInt(1, typeId);
		cnx.preparedStatement.executeUpdate();
		cnx.preparedStatement.close();
		cnx.connection.close();
		return -1;
		}catch(SQLException e) {
			System.out.println(e.getErrorCode());
			System.out.println("from Amenitie deletOfType "+e.getMessage());
			return e.getErrorCode();
			}
	}
//	*********************
	public static Amenitie[] getAmenities() {
		List<Amenitie> _amenities=new ArrayList<Amenitie>();
		Amenitie[] amenities=null;
		DbConnection cnx=new DbConnection();
		try {
			String quary="SELECT * FROM  amenitie ";
			cnx.statement=cnx.connection.createStatement();
			cnx.result=cnx.statement.executeQuery(quary);
			
			while(cnx.result.next()) {
				int id =cnx.result.getInt("id");
				String name=cnx.result.getString("name");
				_amenities.add(new Amenitie(id,name));
			}
			amenities =new Amenitie[_amenities.size()];
			for(int i=0;i<_amenities.size();i++) 
				amenities[i]=_amenities.get(i);
			cnx.result.close();
			cnx.statement.close();
			cnx.connection.close();
			
		}catch(SQLException e) {
			System.out.println(e.getErrorCode());
			System.out.println("from Amenitie getAll "+e.getMessage());
			}
		
		return amenities;
		
	}
//	************
	public static Amenitie getAmenitieById(int id) {
		Amenitie amenitie=null;
		
		DbConnection cnx=new DbConnection();
		try {
		String quary="SELECT * FROM  amenitie  WHERE id=?";
		cnx.preparedStatement= cnx.connection.prepareStatement(quary);
		cnx.preparedStatement.setInt(1, id);
		cnx.result=cnx.preparedStatement.executeQuery();
		if(cnx.result.next())
			amenitie=new Amenitie(cnx.result.getInt("id"),cnx.result.getString("name"));
		cnx.result.close();
		cnx.preparedStatement.close();
		cnx.connection.close();
		
		
		}catch(SQLException e) {
			System.out.println(e.getErrorCode());
			System.out.println("from Amenitie getById "+e.getMessage());
		}
		return amenitie;
		
	}
	
//	*****************
	public static Amenitie[] getAmenitiesOf(int typeId) {
		List<Amenitie> _amenities=new ArrayList<Amenitie>();
		Amenitie[] amenities=null;
		DbConnection cnx=new DbConnection();
		try {
		String quary="SELECT * FROM  amenities  WHERE typeId=?";
		cnx.preparedStatement= cnx.connection.prepareStatement(quary);
		cnx.preparedStatement.setInt(1, typeId);
		cnx.result=cnx.preparedStatement.executeQuery();
		
		while(cnx.result.next()) {
			int id =cnx.result.getInt("amenitieId");
			Amenitie am=Amenitie.getAmenitieById(id);
			if(am!=null)
				_amenities.add(am);	
		}
		amenities =new Amenitie[_amenities.size()];
		for(int i=0;i<_amenities.size();i++) 
			amenities[i]=_amenities.get(i);
		cnx.result.close();
		cnx.preparedStatement.close();
		cnx.connection.close();
		
		
		}catch(SQLException e) {
			System.out.println(e.getErrorCode());
			System.out.println("from Amenitie getOfType "+e.getMessage());
		}
		return amenities;
		
	}
	//**********************
		public static boolean isAmenitieExiste(int id) {
			Amenitie amenitie =Amenitie.getAmenitieById(id);
			if(amenitie==null)
				return false;
			return true;
			
			
		}
	
}
