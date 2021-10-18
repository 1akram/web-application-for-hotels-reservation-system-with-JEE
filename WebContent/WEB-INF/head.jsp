<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
        <%@page import="models.Hotel"%>
    <head>
     <% Hotel hotel=Hotel.getHotelInfo(); %>
        <title><%= hotel.getName()%> || <%=request.getParameter("title") %></title>	
		<meta charset="UTF-8">		
		<meta name="description" content="<%=hotel.getDescription() %>>">
        <link rel="stylesheet" href="<%=request.getContextPath() %>/css/bootstrap.css">
        <link rel="stylesheet" href="<%=request.getContextPath() %>/css/main.css">
    </head>
    