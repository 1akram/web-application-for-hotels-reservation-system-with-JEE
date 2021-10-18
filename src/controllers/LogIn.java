package controllers;

import java.io.IOException;
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
import security.InputsValidation;

@WebServlet("/login")
public class LogIn extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public LogIn() {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession ses=request.getSession();
		System.out.println("islogin "+InputsValidation.isLogIn(ses));
		if(InputsValidation.isLogIn(ses)) {
			HashMap<String,Integer>sesInfo=InputsValidation.getSessionInfo(ses);
			
			if(sesInfo.get("role")==0)//0 ==> client ; 1==> employee;  2 ==> manager; 
			{
				response.sendRedirect(request.getContextPath()+"index");
				return;
			}
			response.sendRedirect(request.getContextPath()+"/dashboard");
			return;
		}
		String msg = (String) ses.getAttribute("msg");
		request.setAttribute("msg", msg);
		ses.setAttribute("msg", null);
		request.setAttribute("from",  null);
		request.setAttribute("title", "Login");
		getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
		return;
		
		
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession ses =request.getSession();
		if(InputsValidation.isLogIn(ses))
			doGet(request,response);
		String email=request.getParameter("email");
		String password=request.getParameter("password");
		String _role=request.getParameter("role");
		try {
			int role=Integer.parseInt(_role);
			
			if(role==1) {
				//employee
				if(InputsValidation.isValid("email", email)&&InputsValidation.isValid("alphanumeric", password)) {
					Employee em=Employee.getEmployeeByEmail(email);
					try {
						String pass=em.getPassword();
						if(pass.equals(password)) {
							createSession(ses,em.getId(),role);
							ses.setAttribute("msg", null);
							response.sendRedirect(request.getContextPath()+"/dashboard");
							return;
						}
					}catch(NullPointerException nul) {/*no Employee with this email in data base*/}
				}
				ses.setAttribute("msg", "email or password not correct");
				ses.setAttribute("alartType", "Warning");
				response.sendRedirect(request.getContextPath()+"/dashboard");
				return;

				
					
			}else if(role==2) {
				//manager
				if(InputsValidation.isValid("email", email)&&InputsValidation.isValid("alphanumeric", password)) {
					Manager ma= Manager.getManagerByEmail(email);
					try {
						String pass=ma.getPassword();
						if(pass.equals(password)) {
							createSession(ses,ma.getId(),role);
							ses.setAttribute("msg", null);
							response.sendRedirect(request.getContextPath()+"/dashboard");
							return;
						}
					}catch(NullPointerException nul) {/*no Manager with this email in data base*/}
				}
				ses.setAttribute("msg", "email or password not correct");
				ses.setAttribute("alartType", "Warning");
				response.sendRedirect(request.getContextPath()+"/dashboard");
				return;
		
			}else// problem hacker °O° 
			{
				ses.setAttribute("msg", "you don't have access to this page");
				ses.setAttribute("alartType", "Warning");
				response.sendRedirect(request.getContextPath()+"/404");
				return;
			}	
		}catch(NumberFormatException ne) {
			// client 
			if(InputsValidation.isValid("email", email)&&InputsValidation.isValid("alphanumeric", password)) {
				Client cl=Client.getClientByEmail(email);
				try {
					String pass=cl.getPassword();
					if(pass.equals(password)) {
						createSession(ses,cl.getId(),0);
						ses.setAttribute("msg", null);
						response.sendRedirect(request.getContextPath()+"/index");
						return;
					}
				}catch(NullPointerException nul) {/*no client with this email in data base*/}
			}
			ses.setAttribute("msg", "email or password not corecte");
			ses.setAttribute("alartType", "Warning");
			response.sendRedirect(request.getContextPath()+"/login");
			return;	
		}	
	}
	
	private void  createSession ( HttpSession ses,int userId,int role){							
		ses.setAttribute("isLogIn", "true");
		ses.setAttribute("userId", userId+"");
		ses.setAttribute("role", role+"");
	}

}
