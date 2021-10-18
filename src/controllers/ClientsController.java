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
import security.InputsValidation;


@WebServlet("/dashboard/client")
public class ClientsController extends HttpServlet {
	private static final long serialVersionUID = 1L;


    public ClientsController() {
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
		
		if(op!=null&&op.equals("delete")) {
				String _id=request.getParameter("id");
				if(_id.matches("[0-9]+")) {
					int id=Integer.parseInt(_id);
					int _deletResult=Client.deleteClient(id);
					if(_deletResult==-1) {
						ses.setAttribute("msg", "Client deleted");
						ses.setAttribute("alartType", "Success");
					}
					else {
						ses.setAttribute("msg", "there is a problem cant delete");
						ses.setAttribute("alartType", "Warning");
					}
						
				}else{
					ses.setAttribute("msg", "id not correct");
				ses.setAttribute("alartType", "Warning");
			}
				
			
			response.sendRedirect(request.getRequestURI());
			return;
			
		}else {
			String msg=(String)ses.getAttribute("msg");
			ses.setAttribute("msg", null);
			request.setAttribute("msg", msg);
			request.setAttribute("title", " Clients");
			request.setAttribute("clients",Client.getClients());
			getServletContext().getRequestDispatcher("/WEB-INF/dashboard/client.jsp").forward(request,response);
			return;
		}
	


	}



	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession ses =request.getSession();
		
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
		if(op!=null) {
			switch(op) {
			case "add":
				String email=request.getParameter("email");
				String password=request.getParameter("password");
				String firstName=request.getParameter("firstName");
				String lastName=request.getParameter("lastName");
				String dateOfBirth=request.getParameter("dateOfBirth");
				String gender=request.getParameter("gender");
				String street=request.getParameter("street");
				String city =request.getParameter("city");
				String state=request.getParameter("state");
				String zipCode=request.getParameter("zipCode");
				String phone=request.getParameter("phone");
				if(InputsValidation.isValid("email",email)&&InputsValidation.isValid("alphanumeric",password)&&InputsValidation.isValid("alphabet",firstName)&&InputsValidation.isValid("alphabet",lastName)&&InputsValidation.isValid("date",dateOfBirth)
						&&InputsValidation.isValid("gender",gender)&&InputsValidation.isValid("alphNumSpace",street)&&InputsValidation.isValid("alphabet",city)&&InputsValidation.isValid("alphabet",state)&&InputsValidation.isValid("numeric",zipCode)
						&&InputsValidation.isValid("numeric",phone)) {
					Date _dateOfBirth;
					try {
						_dateOfBirth = new SimpleDateFormat("yyyy-MM-dd").parse(dateOfBirth);
					} catch (ParseException e) {
						ses.setAttribute("msg", "ther is a problem cant add");
						ses.setAttribute("alartType", "Warning");
						break;
					}
					int _addResult=Client.addClient(email, password, firstName, lastName, _dateOfBirth, gender, phone, street, city, state, zipCode);
					if(_addResult==-1){
						ses.setAttribute("msg", "Client add ");
						ses.setAttribute("alartType", "Success");
					}
					else if(_addResult==1062) {
						ses.setAttribute("msg", "Client is exist");
						ses.setAttribute("alartType", "Warning");
					}
					else  {
						ses.setAttribute("msg", "there is a problem can't  add");
						ses.setAttribute("alartType", "Warning");
					}
				}else{
					ses.setAttribute("msg", "input not correct");
					ses.setAttribute("alartType", "Warning");
				}
				
				break;
				
			case"edit":
				String id=request.getParameter("id");
				 email=request.getParameter("email");
				 password=request.getParameter("password");
				 firstName=request.getParameter("firstName");
				 lastName=request.getParameter("lastName");
				 dateOfBirth=request.getParameter("dateOfBirth");
				 gender=request.getParameter("gender");
				 street=request.getParameter("street");
				 city =request.getParameter("city");
				 state=request.getParameter("state");
				 zipCode=request.getParameter("zipCode");
				 phone=request.getParameter("phone");

				if(InputsValidation.isValid("numeric", id)&&InputsValidation.isValid("email",email)&&InputsValidation.isValid("alphabet",firstName)&&InputsValidation.isValid("alphabet",lastName)&&InputsValidation.isValid("date",dateOfBirth)
						&&InputsValidation.isValid("gender",gender)&&InputsValidation.isValid("alphNumSpace",street)&&InputsValidation.isValid("alphabet",city)&&InputsValidation.isValid("alphabet",state)&&InputsValidation.isValid("numeric",zipCode)
						&&InputsValidation.isValid("numeric",phone)) {
					if(!InputsValidation.isValid("alphanumeric", password))
						password=null;
					int _id =Integer.parseInt(id);
					Date _dateOfBirth;
					try {
						_dateOfBirth = new SimpleDateFormat("yyyy-MM-dd").parse(dateOfBirth);
					} catch (ParseException e) {
						ses.setAttribute("msg", "ther is a problem cant update");
						ses.setAttribute("alartType", "Warning");
						break;
					}
					int _editResult=Client.editClient(_id,email, password, firstName, lastName, _dateOfBirth, gender, phone, street, city, state, zipCode);
					if(_editResult==-1){
						ses.setAttribute("msg", "Client update ");
						ses.setAttribute("alartType", "Success");
					}
					else if(_editResult==1062) {
						ses.setAttribute("msg", "Client is exist");
						ses.setAttribute("alartType", "Warning");
					}
					else {
						ses.setAttribute("msg", "there is a problem can't  add");
						ses.setAttribute("alartType", "Warning");
					}
				}else {
					ses.setAttribute("msg", "input not correct");
					ses.setAttribute("alartType", "Warning");
				}
				
				break;
			
			
			}	
		}


		response.sendRedirect(request.getRequestURI());
		return;

	}

}
