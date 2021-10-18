package controllers;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.Floor;
import models.Room;
import models.Type;
import security.InputsValidation;

@WebServlet({"/dashboard/room","/dashboard/room/"})
public class RoomController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public RoomController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession ses=request.getSession();
		
		if (!InputsValidation.isLogIn(ses)) {
			response.sendRedirect(request.getContextPath()+"/dashboard/login");
			 return; 
		}
		HashMap<String, Integer> sesInfo=InputsValidation.getSessionInfo(ses);
		if(sesInfo.get("role")==0)//client  don't have access to this area 
		{
			response.sendRedirect(request.getContextPath()+"/index");
			return;
		}
		
		
		String op = request.getParameter("op");
		if (InputsValidation.isValid("alphabetNoSpace", op) && op.equals("delete")) {
			String _id = request.getParameter("id");
			if (InputsValidation.isValid("numeric", _id)) {
				int id = Integer.parseInt(_id);
				int _deletResult = Room.deleteRoom(id);
				if (_deletResult == -1) {
					ses.setAttribute("msg", "Room deleted");
					ses.setAttribute("alartType", "Success");
				}
				else {
					ses.setAttribute("msg", "there is a problem can't delete");
					ses.setAttribute("alartType", "Warning");
				}
			} else {
				ses.setAttribute("msg", "id not correct");
			ses.setAttribute("alartType", "Warning");
		}
			response.sendRedirect(request.getRequestURI());
			return;
		}
		
		String msg = (String) ses.getAttribute("msg");
		request.setAttribute("msg", msg);
		ses.setAttribute("msg", null);
		request.setAttribute("title", "Room");
		request.setAttribute("types", Type.getTypes());
		request.setAttribute("floors",Floor.getFloors());
		request.setAttribute("rooms",Room.getRooms());
		getServletContext().getRequestDispatcher("/WEB-INF/dashboard/room.jsp").forward(request, response);
		return;
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession ses=request.getSession();
		if (!InputsValidation.isLogIn(ses)) {
			response.sendRedirect(request.getContextPath()+"/dashboard/login");
			 return; 
		}
		HashMap<String, Integer> sesInfo=InputsValidation.getSessionInfo(ses);
		if(sesInfo.get("role")==0)//client  don't have access to this area 
		{
			response.sendRedirect(request.getContextPath()+"/index");
			return;
		}
		
		ses.setAttribute("msg", null);
		String op=request.getParameter("op");
		if(InputsValidation.isValid("alphabetNoSpace", op)) {
			switch(op) {
				
				case "add":
					String _number=request.getParameter("number");
					String _typeId=request.getParameter("type");
					String _floorId=request.getParameter("floor");
					if(InputsValidation.isValid("numeric", _number)&&InputsValidation.isValid("numeric", _typeId)&&InputsValidation.isValid("numeric", _floorId)) {
						int number=Integer.parseInt(_number);
						int typeId=Integer.parseInt(_typeId);
						int floorId=Integer.parseInt(_floorId);
						if(Floor.isFloorExiste(floorId)&&Type.isTypeExiste(typeId)) {
								int _addResult=Room.addRoom(number, floorId, typeId);
								if(_addResult==-1) {
									ses.setAttribute("msg", "Room add ");
									ses.setAttribute("alartType", "Success");
								}
								else if(_addResult==1062) {
									ses.setAttribute("msg", "Room with this number is exist");
									ses.setAttribute("alartType", "Warning");
								}
								else { 
									ses.setAttribute("msg", "there is a problem can't add");
									ses.setAttribute("alartType", "Warning");
								}
						}else {
							ses.setAttribute("msg", "one or more inputs not correct");
							ses.setAttribute("alartType", "Warning");
						}
					}else {
						ses.setAttribute("msg", "one or more inputs not correct");
						ses.setAttribute("alartType", "Warning");
					}
					break;
					
					
				case "edit":
					String _id=request.getParameter("id");
					 _number=request.getParameter("number");
					 _typeId=request.getParameter("type");
					 _floorId=request.getParameter("floor");
					if(InputsValidation.isValid("numeric", _id)&&InputsValidation.isValid("numeric", _number)&&InputsValidation.isValid("numeric", _typeId)&&InputsValidation.isValid("numeric", _floorId)) {
						int id=Integer.parseInt(_id);
						int number=Integer.parseInt(_number);
						int typeId=Integer.parseInt(_typeId);
						int floorId=Integer.parseInt(_floorId);
						if(Room.isRoomExiste(id)&&Floor.isFloorExiste(floorId)&&Type.isTypeExiste(typeId)) {
							int _addResult=Room.editRoom(id,number, floorId, typeId);
							if(_addResult==-1) {
								ses.setAttribute("msg", "Room update ");
								ses.setAttribute("alartType", "Success");
							}
							else if(_addResult==1062) {
								ses.setAttribute("msg", "room with this number is exist");
								ses.setAttribute("alartType", "Warning");
							}
							else { 
								ses.setAttribute("msg", "there is a problem can't add");
								ses.setAttribute("alartType", "Warning");
							}
					}else {
						ses.setAttribute("msg", "one or more inputs not correct");
						ses.setAttribute("alartType", "Warning");
					}
				}else {
					ses.setAttribute("msg", "one or more inputs not correct");
					ses.setAttribute("alartType", "Warning");
				}
				break;
			}
		}
		response.sendRedirect(request.getRequestURI());
		return;
		
	}

}


















