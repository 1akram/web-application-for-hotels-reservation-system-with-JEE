package controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Type;


@WebServlet({"/index","/home" })
public class FrontEndController extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public FrontEndController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Type[] types=Type.getTypes();
		//Hotel hotel=Hotel.getInfo();
		//request.setAttribute("hotel",hotel);
		request.setAttribute("title", "Home");
		request.setAttribute("types", types);
		getServletContext().getRequestDispatcher("/WEB-INF/index.jsp").forward(request,response);
		return;	
		}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
