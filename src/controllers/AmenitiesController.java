package controllers;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import models.Amenitie;
import security.InputsValidation;

/**
 * Servlet implementation class AmenitiesController
 */
@WebServlet("/dashboard/amenities")
public class AmenitiesController extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public AmenitiesController() {
        super();
       
    }



	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession ses =request.getSession();
		
		
		if (!InputsValidation.isLogIn(ses)) {
			response.sendRedirect(request.getContextPath()+"/dashboard/login");
			 return; 
		}
		HashMap<String, Integer> sesInfo=InputsValidation.getSessionInfo(ses);
		if(sesInfo.get("role")==0)//client  don't have access to this area 
		{
			response.sendRedirect(request.getContextPath());
			return;
		}
		String op=request.getParameter("op");
		if(op!=null) {
			if(op.equals("delete")) {
				String _id=request.getParameter("id");
				if(InputsValidation.isValid("numeric", _id)) {
					int id=Integer.parseInt(_id);
					int _deletResult=Amenitie.deleteAmenities(id);
					if(_deletResult==-1) {
						ses.setAttribute("msg", "amenitie deleted");
						ses.setAttribute("alartType", "Success");
					}
					else {
						ses.setAttribute("msg", "there is a problem cant delete");
						ses.setAttribute("alartType", "Warning");
					}
				}else {
					ses.setAttribute("msg", "id not correct");
				ses.setAttribute("alartType", "Warning");
			}
			}
			response.sendRedirect(request.getRequestURI());
			return;
			
		}else {
			String msg=(String)ses.getAttribute("msg");
			ses.setAttribute("msg", null);
			request.setAttribute("msg", msg);
			request.setAttribute("title", " Amenities");
			request.setAttribute("amenities",Amenitie.getAmenities());
			getServletContext().getRequestDispatcher("/WEB-INF/dashboard/amenities.jsp").forward(request,response);
			return;
		}

			
	
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
		
		String amenitie=request.getParameter("amenities");
		ses.setAttribute("msg", null);
		if(InputsValidation.isValid("alphabet", amenitie)) {
			int _addResult=Amenitie.addAmenities(amenitie);
			if(_addResult==-1) {
				ses.setAttribute("msg", "amenitie add ");
				ses.setAttribute("alartType", "Success");
			}
			else if(_addResult==1062) {
				ses.setAttribute("msg", "amenitie is exist");
				ses.setAttribute("alartType", "Warning");
			}
			else {
				ses.setAttribute("msg", "there is a problem can't  add");
				ses.setAttribute("alartType", "Warning");
			}
		}
		else {
			ses.setAttribute("msg", "input not correct");
			ses.setAttribute("alartType", "Warning");
		}
		
		
		response.sendRedirect(request.getRequestURI());
		return;

	}

}
