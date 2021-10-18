package controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.Amenitie;
import models.Type;
import security.InputsValidation;

@WebServlet("/dashboard/type")
public class TypeController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public TypeController() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession ses = request.getSession();
		
		
		
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
		if (op != null && op.equals("delete")) {
			String _id = request.getParameter("id");
			if (InputsValidation.isValid("numeric", _id)) {
				int id = Integer.parseInt(_id);
				int _deletResult = Type.deleteType(id);
				if (_deletResult == -1) {
					ses.setAttribute("msg", "Type deleted");
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
		ses.setAttribute("msg", null);
		request.setAttribute("msg", msg);
		request.setAttribute("title", " Type");
		request.setAttribute("types", Type.getTypes());
		request.setAttribute("amenities", Amenitie.getAmenities());
		getServletContext().getRequestDispatcher("/WEB-INF/dashboard/type.jsp").forward(request, response);
		return;
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession ses = request.getSession();
		
		
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

		String op = request.getParameter("op");
		if (op != null) {
			switch (op) {
			case "add":
				String name = request.getParameter("name");
				String price = request.getParameter("price");
				String guests = request.getParameter("guests");
				String description = request.getParameter("description");
				String[] amenities=request.getParameterValues("amenities");
				
				if (InputsValidation.isValid("alphabet", name) && InputsValidation.isValid("numeric", price)
						&& InputsValidation.isValid("numeric", guests)
						&& description!=null) {
					int _price = Integer.parseInt(price);
					int _guests = Integer.parseInt(guests);
					List<Integer>_amenitiesIds=new ArrayList<Integer>();
					if(amenities!=null) {
						for(String amenitie:amenities)
							if(InputsValidation.isValid("numeric", amenitie))
								_amenitiesIds.add(Integer.parseInt(amenitie));		
					}
					int _addResult = Type.addType(name, _price, _guests, description, _amenitiesIds);
					if (_addResult == -1) {
						ses.setAttribute("msg", "Type add ");
						ses.setAttribute("alartType", "Success");
					}
					else if (_addResult == 1062) {
						ses.setAttribute("msg", "Type is exist");
						ses.setAttribute("alartType", "Warning");
					}
					else {
						ses.setAttribute("msg", "there is a problem can't add");
						ses.setAttribute("alartType", "Warning");
					}
				} else {
					ses.setAttribute("msg", "one or more inputs not correct");
					ses.setAttribute("alartType", "Warning");
				}
				break;
			case "edit":
				String id = request.getParameter("id");
				name = request.getParameter("name");
				price = request.getParameter("price");
				guests = request.getParameter("guests");
				description = request.getParameter("description");
			    amenities=request.getParameterValues("amenities");
				if (InputsValidation.isValid("numeric", id) && InputsValidation.isValid("alphabet", name)
						&& InputsValidation.isValid("numeric", price) && InputsValidation.isValid("numeric", guests)
						&& description!=null) {

					int _id = Integer.parseInt(id);
					int _price = Integer.parseInt(price);
					int _guests = Integer.parseInt(guests);
					List<Integer>_amenitiesIds=new ArrayList<Integer>();
					System.out.println(_amenitiesIds);
					if(amenities!=null) {
						for(String amenitie:amenities)
							if(InputsValidation.isValid("numeric", amenitie))
								_amenitiesIds.add(Integer.parseInt(amenitie));	
						System.out.println("dd "+_amenitiesIds);
					}
					System.out.println("aa"+_amenitiesIds);
					int _editResult = Type.editType(_id, name, _price, _guests, description, _amenitiesIds);
					if (_editResult == -1) {
						ses.setAttribute("msg", "Type update ");
						ses.setAttribute("alartType", "Success");
					}
					else if (_editResult == 1062) {
						ses.setAttribute("msg", "Type is exist");
						ses.setAttribute("alartType", "Warning");
					}
					else {
						ses.setAttribute("msg", "there is a problem can't update");
						ses.setAttribute("alartType", "Warning");
					}
				} else {
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
