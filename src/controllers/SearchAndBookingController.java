package controllers;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;



import models.Client;
import models.Employee;
import models.Manager;
import models.Room;
import security.InputsValidation;


@WebServlet({ "/search", "/booking" })
public class SearchAndBookingController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public SearchAndBookingController() {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession ses=request.getSession();
		String requestFrom=request.getRequestURI();
		Date _checkIn = null;
		Date _checkOut = null;
		if(requestFrom.endsWith("/search")) {
			Room[] avilabelRooms=null;
			if(request.getParameter("op")!=null&&request.getParameter("op").equals("search")) {
			String checkIn=request.getParameter("checkIn");
			String checkOut=request.getParameter("checkOut");
			String guests=request.getParameter("guests");

			if(InputsValidation.isValid("date", checkIn) && InputsValidation.isValid("date",checkOut) && InputsValidation.isValid("numericPos", guests)) {
				int _guests=Integer.parseInt(guests);

				try {
					_checkIn = new SimpleDateFormat("yyyy-MM-dd").parse(checkIn);
					_checkOut = new SimpleDateFormat("yyyy-MM-dd").parse(checkOut);
					if(_checkIn.compareTo(_checkOut)>=0) {
						ses.setAttribute("msg", "checkIn > or = checkOut");
						ses.setAttribute("alartType", "Warning");
					}else {	
						avilabelRooms=Client.search(_checkIn, _checkOut, _guests);	
						request.setAttribute("numberOfDayes", InputsValidation.daysBetween(_checkIn, _checkOut));
					}
				} catch (ParseException e) {
					ses.setAttribute("msg", "ther is a problem cant update");
					ses.setAttribute("alartType", "Warning");
				}

			}else
			{
				ses.setAttribute("msg", "one or more inputs not correct");
				ses.setAttribute("alartType", "Warning");
			}
		}
			
			String msg=(String)ses.getAttribute("msg");
			ses.setAttribute("msg", null);
			request.setAttribute("msg", msg);
			request.setAttribute("title", " Search");
			request.setAttribute("avilabelRooms",avilabelRooms);
			request.setAttribute("avilabelRooms",avilabelRooms);
			request.setAttribute("avilabelRooms",avilabelRooms);
			getServletContext().getRequestDispatcher("/WEB-INF/search.jsp").forward(request,response);
			return;
			
			
		}else if(requestFrom.endsWith("/booking")) {
			
			String checkIn=request.getParameter("checkIn");
			String checkOut=request.getParameter("checkOut");
			String id=request.getParameter("id");
			System.out.println(""+InputsValidation.isValid("date", checkIn) + InputsValidation.isValid("date",checkOut) + InputsValidation.isValid("numericPos", id));
			if(InputsValidation.isValid("date", checkIn) && InputsValidation.isValid("date",checkOut) && InputsValidation.isValid("numericPos", id)) {
				int _id=Integer.parseInt(id);
				try {
					_checkIn = new SimpleDateFormat("yyyy-MM-dd").parse(checkIn);
					_checkOut = new SimpleDateFormat("yyyy-MM-dd").parse(checkOut);
					if(_checkIn.compareTo(_checkOut)<0 && Room.isRoomExiste(_id) &&  !Room.getRoomById(_id).isBooked(_checkIn, _checkOut)) {
						if (!InputsValidation.isLogIn(ses)) {
							ses.setAttribute("msg", "logIn first ");
							ses.setAttribute("alartType", "Warning");
							response.sendRedirect(request.getContextPath()+"/login");
							 return; 
						}
						HashMap<String, Integer> sesInfo=InputsValidation.getSessionInfo(ses);
						Room room=Room.getRoomById(_id);
						Client  client;
						if(sesInfo.get("role")==0) {//client
							 client =Client.getClientById(sesInfo.get("userId"));
							 /*************
							  card info process 
							  
							  
							  *********/
							
							client.booking(_checkIn, _checkOut, client, room, "card");
							response.sendRedirect(request.getContextPath()+"/orders");
							return;
						}else {
							if(sesInfo.get("role")==1) 
								client =Employee.getEmployeeById(sesInfo.get("userId"));
							else
							     client =Manager.getManagerById(sesInfo.get("userId"));
							String typeOfPayment=request.getParameter("typeOfPayment");
							if(typeOfPayment!=null && typeOfPayment.equals("cash")) {
								client.booking(_checkIn, _checkOut, client, room, "cash");
								response.sendRedirect(request.getContextPath()+"/orders");
								return;
							}
							/************
							card info process 

							***************/

							client.booking(_checkIn, _checkOut, client, room, "card");
							response.sendRedirect(request.getContextPath()+"/orders");
							return;	
						}
					}		
				} catch (ParseException e) { /*send to search page */
					System.out.println(e.getMessage());
				} 
			}	
		}


		
		
			response.sendRedirect(request.getContextPath()+"/search");
			return;
		
		
		
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.sendRedirect(request.getContextPath()+"/search");

	}

}
