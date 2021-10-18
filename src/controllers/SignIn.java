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

/**
 * Servlet implementation class SignIn
 */
@WebServlet({ "/signin", "/signin/" })
public class SignIn extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public SignIn() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession ses = request.getSession();

		if (InputsValidation.isLogIn(ses)) {
			HashMap<String, Integer> sesInfo = InputsValidation.getSessionInfo(ses);
			if (sesInfo.get("role") == 0) {
				response.sendRedirect(request.getContextPath()+"/index");
				return;
			}
			response.sendRedirect(request.getContextPath() + "/dashboard");
			return;
		}

		String msg = (String) ses.getAttribute("msg");
		request.setAttribute("msg", msg);
		ses.setAttribute("msg", null);
		request.setAttribute("title", "SignIn");
		getServletContext().getRequestDispatcher("/WEB-INF/signin.jsp").forward(request, response);
		return;
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession ses = request.getSession();

		if (InputsValidation.isLogIn(ses)) {
			HashMap<String, Integer> sesInfo = InputsValidation.getSessionInfo(ses);
			if (sesInfo.get("role") == 0) {
				response.sendRedirect(request.getContextPath()+"/index");
				return;
			}
			response.sendRedirect(request.getContextPath() + "/dashboard");
			return;
		}

		ses.setAttribute("msg", null);

		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String dateOfBirth = request.getParameter("dateOfBirth");
		String gender = request.getParameter("gender");
		String street = request.getParameter("street");
		String city = request.getParameter("city");
		String state = request.getParameter("state");
		String zipCode = request.getParameter("zipCode");
		String phone = request.getParameter("phone");
		System.out.println(InputsValidation.isValid("email", email)+""+ InputsValidation.isValid("alphanumeric", password)
		+""+ InputsValidation.isValid("alphabet", firstName)+""+ InputsValidation.isValid("alphabet", lastName)
		+""+ InputsValidation.isValid("date", dateOfBirth) +""+ InputsValidation.isValid("gender", gender)
		+""+ InputsValidation.isValid("alphNumSpace", street) +""+ InputsValidation.isValid("alphabet", city)
		+""+ InputsValidation.isValid("alphabet", state)+""+ InputsValidation.isValid("numeric", zipCode)
		+""+ InputsValidation.isValid("numeric", phone));
		System.out.println(gender);

		
		if (InputsValidation.isValid("email", email) && InputsValidation.isValid("alphanumeric", password)
				&& InputsValidation.isValid("alphabet", firstName) && InputsValidation.isValid("alphabet", lastName)
				&& InputsValidation.isValid("date", dateOfBirth) && InputsValidation.isValid("gender", gender)
				&& InputsValidation.isValid("alphNumSpace", street) && InputsValidation.isValid("alphabet", city)
				&& InputsValidation.isValid("alphabet", state) && InputsValidation.isValid("numeric", zipCode)
				&& InputsValidation.isValid("numeric", phone)) {
			Date _dateOfBirth;
			try {
				_dateOfBirth = new SimpleDateFormat("yyyy-MM-dd").parse(dateOfBirth);
			} catch (ParseException e) {
				ses.setAttribute("msg", "there is a problem can't Singin");
				ses.setAttribute("alartType", "Warning");
				response.sendRedirect(request.getRequestURI());
				return;
			}
			int _addResult = Client.addClient(email, password, firstName, lastName, _dateOfBirth, gender, phone, street,
					city, state, zipCode);
			if (_addResult == -1) {
				ses.setAttribute("msg", " you are signIn successfully you can logIn now");
				ses.setAttribute("alartType", "Success");
				response.sendRedirect(request.getContextPath()+"/login");
				return;
			}
				
				
			else if (_addResult == 1062) {
				ses.setAttribute("msg", "Client is exist");
				ses.setAttribute("alartType", "Warning");
			}
			else {
				ses.setAttribute("msg", "there is a problem can't  Signin");
				ses.setAttribute("alartType", "Warning");
			}
		}else {
			ses.setAttribute("msg", "one or more inputs not correct");
			ses.setAttribute("alartType", "Warning");
		}
		response.sendRedirect(request.getRequestURI());
		return;
	}

}
