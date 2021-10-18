package models;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Image {
	private int id;
	private String url;

//	**************
	public Image() {
	}

	public Image(int id, String url) {
		this.id = id;
		this.url = url;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
//	***************

	public static int addImage(String url, int typeId) {
		DbConnection cnx = new DbConnection();
		try {
			String quary = "INSERT INTO image (url,typeId)VALUES(?,?);";
			cnx.preparedStatement = cnx.connection.prepareStatement(quary);
			cnx.preparedStatement.setString(1, url);
			cnx.preparedStatement.setInt(2, typeId);
			cnx.preparedStatement.execute();
			cnx.preparedStatement.close();
			cnx.connection.close();

			return -1;
		} catch (SQLException e) {
			System.out.println(e.getErrorCode());
			System.out.println("from Image add "+e.getMessage());
			return e.getErrorCode();

		}
	}
//	*****************

	public static int deleteImage(int id) {
		DbConnection cnx = new DbConnection();
		try {
			String quary = "DELETE FROM  image  WHERE id=?";
			cnx.preparedStatement = cnx.connection.prepareStatement(quary);
			cnx.preparedStatement.setInt(1, id);
			cnx.preparedStatement.executeUpdate();
			cnx.preparedStatement.close();
			cnx.connection.close();

			return -1;
		} catch (SQLException e) {
			System.out.println(e.getErrorCode());
			System.out.println("from Image delete "+e.getMessage());
			return e.getErrorCode();
		}
	}

//	*****************
	public static Image[] getImagesOf(int typeId) {
		List<Image> _images = new ArrayList<Image>();
		Image[] images = null;
		DbConnection cnx = new DbConnection();
		try {
			String quary = "SELECT * FROM  image  WHERE typeId=?";
			cnx.preparedStatement = cnx.connection.prepareStatement(quary);
			cnx.preparedStatement.setInt(1, typeId);
			cnx.result = cnx.preparedStatement.executeQuery();

			while (cnx.result.next()) {
				int id = cnx.result.getInt("id");
				String url = cnx.result.getString("url");
				_images.add(new Image(id, url));
			}
			images = new Image[_images.size()];
			for (int i = 0; i < _images.size(); i++)
				images[i] = _images.get(i);
			cnx.result.close();
			cnx.preparedStatement.close();
			cnx.connection.close();

		} catch (SQLException e) {
			System.out.println(e.getErrorCode());
			System.out.println("from Image getOfType "+e.getMessage());
		}
		return images;

	}

//	************
//	*****************
	public static int deleteImagesOf(int typeId) {
		DbConnection cnx = new DbConnection();
		try {
			String quary = "DELETE FROM  image  WHERE typeId=?";
			cnx.preparedStatement = cnx.connection.prepareStatement(quary);
			cnx.preparedStatement.setInt(1, typeId);
			cnx.preparedStatement.executeUpdate();
			cnx.preparedStatement.close();
			cnx.connection.close();

			return -1;
		} catch (SQLException e) {
			System.out.println(e.getErrorCode());
			System.out.println("from Image deleteOfType "+e.getMessage());
			return e.getErrorCode();
		}
	}
//	*********************

}
