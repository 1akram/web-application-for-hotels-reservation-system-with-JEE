<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    	<%@page import="security.InputsValidation"%>
<% 
	HttpSession ses=request.getSession();
%>
		<!--Top Bar -->
		<nav class="navbar navbar-expand-md  topBar "
			style="position: relative !important; background-color: #f44336e8; width: 100%; left: 0px; height: inherit;">
			<button class="navbar-toggler" type="button" data-toggle="collapse"
				data-target="#collapsibleNavbar">
				<span class="navbar-toggler-icon"><strong>+</strong></span>
			</button>
			<div class="collapse navbar-collapse" id="collapsibleNavbar">
				<ul class="navbar-nav ">
					<li class="nav-item active"><a class="nav-link"
						href="<%=request.getContextPath() %>/home">Home</a></li>
					<li class="nav-item"><a class="nav-link" href="<%=request.getContextPath() %>/home#Rooms">Rooms</a>
					</li>
					<li class="nav-item"><a class="nav-link" href="<%=request.getContextPath() %>/home#AboutUs">About
							Us</a></li>
					<li class="nav-item"><a class="nav-link " href="<%=request.getContextPath() %>/home#Location">Location</a>
					</li>
					<li class="nav-item"><a class="nav-link " href="<%=request.getContextPath() %>/home#Contact">Contact</a>
					</li>
					<%if(!InputsValidation.isLogIn(ses)){ %>
					<li class="nav-item"><a class="nav-link "
						href="<%=request.getContextPath() %>/login">LogIn</a></li>
					<%}else{ %>
					<%if(InputsValidation.getSessionInfo(ses).get("role")!=0){ %>
						<li class="nav-item"><a class="nav-link "
						href="<%=request.getContextPath() %>/dashboard">Dashboard</a></li>
					<%} else{%>
					<li class="nav-item"><a class="nav-link "
						href="<%=request.getContextPath() %>/orders">Orders</a></li>
					<li class="nav-item"><a class="nav-link "
						href="<%=request.getContextPath() %>/logout">LogOut</a></li>
					<%}} %>
				</ul>
			</div>
		</nav>
		<!--End Top Bar-->