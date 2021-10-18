package controllers;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import models.Hotel;
import security.InputsValidation;

@WebServlet("/dashboard/setting")
public class HotelController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HotelController() {
        super();
       
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
		if(sesInfo.get("role")==1)//employee && client  don't have access to this area 
		{
			response.sendRedirect(request.getContextPath()+"/dashboard");
			return;
		}
			String msg=(String)ses.getAttribute("msg");
			ses.setAttribute("msg", null);
			request.setAttribute("msg", msg);
			Hotel hotel =Hotel.getHotelInfo();
			request.setAttribute("hotel", hotel);
			getServletContext().getRequestDispatcher("/WEB-INF/dashboard/setting.jsp").forward(request,response);
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
		String name=request.getParameter("name");
		String description=request.getParameter("description");
		String email=request.getParameter("email");
		String phone=request.getParameter("phone");
		String address=request.getParameter("address");
		System.out.println(address);
		if(name==""||description==""||email==""||phone==""||address=="")
		{
			ses.setAttribute("msg", "inputs not correct");
			ses.setAttribute("alartType", "Warning");
			response.sendRedirect(request.getRequestURI());
			return;
		}
		int _result=Hotel.addHotelInfo(email, name, phone, description, address);
		if(_result==-1){
				ses.setAttribute("msg", "Info Update ");
				ses.setAttribute("alartType", "Success");
			}
			else{
				ses.setAttribute("msg", "Info not Update try again");
				ses.setAttribute("alartType", "Warning");
			}
		
		response.sendRedirect(request.getRequestURI());
		return;
	}

}
