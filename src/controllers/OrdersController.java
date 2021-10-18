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
import models.Order;
import security.InputsValidation;

/**
 * Servlet implementation class OrdersController
 */
@WebServlet("/orders")
public class OrdersController extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public OrdersController() {
        super();

    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession ses=request.getSession();
		Order[] orders ;
		if (!InputsValidation.isLogIn(ses)) {
			response.sendRedirect(request.getContextPath()+"/login");
			 return; 
		}
		HashMap<String, Integer> sesInfo=InputsValidation.getSessionInfo(ses);
		if(sesInfo.get("role")==0)
		{
			int id =sesInfo.get("userId");
			Client client =Client.getClientById(id);
			
			orders=Order.getOrders(client.getEmail());
		}else {
			orders=Order.getOrders();
		}
		
		


		request.setAttribute("title", " Orders");


		request.setAttribute("orders",orders);

		getServletContext().getRequestDispatcher("/WEB-INF/orders.jsp").forward(request,response);
		return;
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}

}
