package models;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Order {
	private int id;
	private Date checkIn;
	private Date checkOut;
	private int totalPrice;
	private String email;
	private int roomNumber;
	private String type;
	private String paymentMethod;
	public Order(int id, Date checkIn, Date checkOut, int totalPrice, String email, int roomNumber, String type,
			String paymentMethod) {
		super();
		this.id = id;
		this.checkIn = checkIn;
		this.checkOut = checkOut;
		this.totalPrice = totalPrice;
		this.email = email;
		this.roomNumber = roomNumber;
		this.type = type;
		this.paymentMethod = paymentMethod;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Date getCheckIn() {
		return checkIn;
	}
	public void setCheckIn(Date checkIn) {
		this.checkIn = checkIn;
	}
	public Date getCheckOut() {
		return checkOut;
	}
	public void setCheckOut(Date checkOut) {
		this.checkOut = checkOut;
	}
	public int getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(int totalPrice) {
		this.totalPrice = totalPrice;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getRoomNumber() {
		return roomNumber;
	}
	public void setRoomNumber(int roomNumber) {
		this.roomNumber = roomNumber;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getPaymentMethod() {
		return paymentMethod;
	}
	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}
	//***************************
	public static Order[] getOrders() {
		List<Order> _orders=new ArrayList<Order>();
		Order[] orders=null;
		DbConnection cnx=new DbConnection();
		try {
			String quary="SELECT * FROM  booking ";
			cnx.statement=cnx.connection.createStatement();
			cnx.result=cnx.statement.executeQuery(quary);
			
			while(cnx.result.next()) {
				_orders.add(new Order(cnx.result.getInt("id"),cnx.result.getDate("checkIn"),cnx.result.getDate("checkOut"),cnx.result.getInt("totalPrice"),cnx.result.getString("clientEmail"),cnx.result.getInt("roomNumber"),
						cnx.result.getString("roomType"),cnx.result.getString("typeOfPayment")));
			}
			orders =new Order[_orders.size()];
			for(int i=0;i<_orders.size();i++) 
				orders[i]=_orders.get(i);
			cnx.result.close();
			cnx.statement.close();
			cnx.connection.close();
		
		
		}catch(SQLException e) {
			System.out.println(e.getErrorCode());
			System.out.println("from Client GetByEmail "+e.getMessage());
		}
		
		
		return orders;
	}
	//*************************************
	public static Order[] getOrders(String email){
		List<Order> _orders=new ArrayList<Order>();
		Order[] orders=null;
		DbConnection cnx=new DbConnection();
		try {
			String quary="SELECT * FROM  booking WHERE clientEmail=? ";
			cnx.preparedStatement= cnx.connection.prepareStatement(quary);
			cnx.preparedStatement.setString(1, email);
			cnx.result=cnx.preparedStatement.executeQuery();
			
			while(cnx.result.next()) {
				_orders.add(new Order(cnx.result.getInt("id"),cnx.result.getDate("checkIn"),cnx.result.getDate("checkOut"),cnx.result.getInt("totalPrice"),cnx.result.getString("clientEmail"),cnx.result.getInt("roomNumber"),
						cnx.result.getString("roomType"),cnx.result.getString("typeOfPayment")));
			}
			orders =new Order[_orders.size()];
			for(int i=0;i<_orders.size();i++) 
				orders[i]=_orders.get(i);
			cnx.result.close();
			cnx.preparedStatement.close();
			cnx.connection.close();

		}catch(SQLException e) {
			System.out.println(e.getErrorCode());
			System.out.println("from Client GetByEmail "+e.getMessage());
		}

		return orders;

	}
}
