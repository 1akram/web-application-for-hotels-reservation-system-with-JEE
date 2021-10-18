package models;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Type {
	private int id;
	private String name;
	private int price;
	private int guests;
	private String description;
	public Amenitie[] amenities;
	public Image[] imgs;

//	*********
	public Type() {
	};

	public Type(int id, String name, int price, int guests, String descroption, Amenitie[] amenities, Image[] imgs) {
		this.id = id;
		this.name = name;
		this.price = price;
		this.guests = guests;
		this.description = descroption;
		this.amenities = amenities;
		this.imgs = imgs;
	}

	public int getId() {
		return id;
	}

	public void setAmenities(Amenitie[] amenities) {
		this.amenities = amenities;
	}

	public void setImgs(Image[] imgs) {
		this.imgs = imgs;
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

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getGuests() {
		return guests;
	}

	public void setGuests(int guests) {
		this.guests = guests;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
//	************

//	*************************
	public static int addType(String name, int price, int guests, String description,List<Integer> amenitieIds) {
		DbConnection cnx = new DbConnection();
		try {
			String quary = "INSERT INTO type (name,price,guests,description)VALUES(?,?,?,?);";
			cnx.preparedStatement = cnx.connection.prepareStatement(quary, Statement.RETURN_GENERATED_KEYS);
			cnx.preparedStatement.setString(1, name);
			cnx.preparedStatement.setInt(2, price);
			cnx.preparedStatement.setInt(3, guests);
			cnx.preparedStatement.setString(4, description);
			cnx.preparedStatement.executeUpdate();
			cnx.result = cnx.preparedStatement.getGeneratedKeys();
			int id;
			if (cnx.result.next())
				id = cnx.result.getInt(1);
			else
				return 0;
			int _addResult = Amenitie.addAmenitiesFor(id, amenitieIds);
			if (_addResult != -1)
				return 0;
			cnx.result.close();
			cnx.preparedStatement.close();
			cnx.connection.close();
			return -1;
		} catch (SQLException e) {
			System.out.println(e.getErrorCode());
			System.out.println("from Type add "+e.getMessage());
			return e.getErrorCode();
		}
	}

//	*********************
	public static int editType(int id, String name, int price, int guests, String description, List<Integer> amenitieIds) {
		if (!Type.isTypeExiste(id))
			return 0;
		int _editResult = Amenitie.deleteAmenitiesOf(id);
		if (_editResult != -1)
			return _editResult;
		DbConnection cnx = new DbConnection();
		try {

			String quary = "UPDATE  type SET  name=?,price=?,guests=?,description=? WHERE id=?;";
			cnx.preparedStatement = cnx.connection.prepareStatement(quary);
			cnx.preparedStatement.setString(1, name);
			cnx.preparedStatement.setInt(2, price);
			cnx.preparedStatement.setInt(3, guests);
			cnx.preparedStatement.setString(4, description);
			cnx.preparedStatement.setInt(5, id);
			cnx.preparedStatement.executeUpdate();
			cnx.preparedStatement.close();
			cnx.connection.close();
			int _addResult = Amenitie.addAmenitiesFor(id, amenitieIds);
			if (_addResult != -1)
				return _addResult;
			return -1;

		} catch (SQLException e) {
			System.out.println(e.getErrorCode());
			System.out.println("from Type edit "+e.getMessage());
			return e.getErrorCode();

		}
	}

//	*********************
	public static int deleteType(int id) {
		DbConnection cnx = new DbConnection();
		try {
			String quary = "DELETE FROM  type  WHERE id=?";
			cnx.preparedStatement = cnx.connection.prepareStatement(quary);
			cnx.preparedStatement.setInt(1, id);
			cnx.preparedStatement.executeUpdate();
			cnx.preparedStatement.close();
			cnx.connection.close();
			Amenitie.deleteAmenitiesOf(id);
			Image.deleteImagesOf(id);
			Room.deleteRoomsOf(id);
			return -1;
		} catch (SQLException e) {
			System.out.println(e.getErrorCode());
			System.out.println("from Type delete "+e.getMessage());
			return e.getErrorCode();
		}
	}

//	*********************
	public static Type getTypeById(int id) {
		Type type = null;

		DbConnection cnx = new DbConnection();
		try {
			String quary = "SELECT * FROM  type  WHERE id=?";
			cnx.preparedStatement = cnx.connection.prepareStatement(quary);
			cnx.preparedStatement.setInt(1, id);
			cnx.result = cnx.preparedStatement.executeQuery();
			if (cnx.result.next())
				type = new Type(cnx.result.getInt("id"), cnx.result.getString("name"), cnx.result.getInt("price"),
						cnx.result.getInt("guests"), cnx.result.getString("description"), Amenitie.getAmenitiesOf(id),
						Image.getImagesOf(id));
			cnx.result.close();
			cnx.preparedStatement.close();
			cnx.connection.close();

		} catch (SQLException e) {
			System.out.println(e.getErrorCode());
			System.out.println("from Type getById "+e.getMessage());
		}
		return type;
	}

//		*********************
	public static Type[] getTypes() {
		List<Type> _types = new ArrayList<Type>();
		Type[] types = null;
		DbConnection cnx = new DbConnection();
		try {
			String quary = "SELECT * FROM  type ";
			cnx.statement = cnx.connection.createStatement();
			cnx.result = cnx.statement.executeQuery(quary);

			while (cnx.result.next()) {
				int id = cnx.result.getInt("id");
				_types.add(new Type(id, cnx.result.getString("name"), cnx.result.getInt("price"),
						cnx.result.getInt("guests"), cnx.result.getString("description"), Amenitie.getAmenitiesOf(id),
						Image.getImagesOf(id)));
			}
			types = new Type[_types.size()];
			for (int i = 0; i < _types.size(); i++)
				types[i] = _types.get(i);
			cnx.result.close();
			cnx.statement.close();
			cnx.connection.close();

		} catch (SQLException e) {
			System.out.println(e.getErrorCode());
			System.out.println("from Type getAll "+e.getMessage());
		}

		return types;

	}
	
	//**********************
		public static boolean isTypeExiste(int id) {
			Type type =Type.getTypeById(id);
			if(type==null)
				return false;
			return true;
			
			
		}
}
