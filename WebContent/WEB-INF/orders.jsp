<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
	<%@page import="security.InputsValidation"%>
	<%@page import="models.Order"%>
	
<!DOCTYPE html>
<html>

<!-- head -->

<jsp:include page="head.jsp">
	<jsp:param name="title" value="<%=request.getAttribute(\"title\") %>" />
</jsp:include>
<!-- end head -->

<% 
	HttpSession ses=request.getSession();
%>
<body style="font-family: Arial, Helvetica, sans-serif !important;">
	<div class="bg">
		<img src="img/login-bg.jpg" alt="">
	</div>
	<div class="container  p-0" style="background-color: #ffffffa3;">

<jsp:include page="topBar.jsp"/>


		<div class="jumbotron d-flex search-jum">
			<div class="m-auto search-title ">
				<span>Orders</span>
			</div>
			</div>

		<!-- rooms -->
		<%
			
			Order []orders=(Order[])request.getAttribute("orders");
		 if(orders!=null&&orders.length>0){
			
			 for(Order od:orders){
		%>
		<div class="row search-room">
			<div class="col">
				<h3>order</h3>
				<p><%=od.getId() %></p>
			</div>
			<div class="col">
				<h3>email</h3>
				<p><%=od.getEmail() %></p>
			</div>
			<div class="col">
				<h3>checkIn</h3>
				<p><%=od.getCheckIn() %></p>
			</div>
			<div class="col">
				<h3>checkOut</h3>
				<p><%=od.getCheckOut()%></p>
			</div>
			<div class="col">
				<h3>total</h3>
				<p><%=od.getTotalPrice() %> dz</p>
			</div>
			<div class="col">
				<h3>type</h3>
				<p><%=od.getType() %></p>
			</div>
			<div class="col">
				<h3>room </h3>
				<p><%=od.getRoomNumber() %></p>
			</div>
			<div class="col">
				<h3>payment</h3>
				<p><%=od.getPaymentMethod() %></p>
			</div>
			<div></div>
		</div>
		<%}} %>
				</div>
		<!-- end rooms -->


 


	<!-- End Container -->
	<!-- footer -->
	<jsp:include page="footer.jsp" />
	<!-- end footer -->