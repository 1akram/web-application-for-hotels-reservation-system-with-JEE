package controllers;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import security.InputsValidation;

@WebServlet({ "/dashboard", "/dashboard/login","/dashboard/" })
public class Dashboard extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public Dashboard() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession ses = request.getSession();
		System.out.println(request.getRequestURI()+" "+InputsValidation.isLogIn(ses));
		if (!InputsValidation.isLogIn(ses)) {
			request.setAttribute("from", "dashboard");
			String msg = (String) ses.getAttribute("msg");
			request.setAttribute("msg", msg);
			ses.setAttribute("msg", null);
			request.setAttribute("title", " Dashboard Login");
			getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
			return;
		}
		HashMap<String, Integer> sesInfo=InputsValidation.getSessionInfo(ses);
		if(sesInfo.get("role")==0)//client
		{
			response.sendRedirect(request.getContextPath()+"/index");
			return;
		}
		response.sendRedirect(request.getContextPath()+"/dashboard/client");
		return;
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.sendRedirect(request.getContextPath()+"/index");
		return;

	}

}
