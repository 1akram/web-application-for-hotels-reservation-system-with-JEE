<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
	<%@page import="security.InputsValidation"%>
	<%@page import="models.Room"%>
	
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
				<span>Search</span>
			</div>
			<div class=" searchTab " style="position: relative;">
				<form class="h-100 " action="search">
				
					<div class="position-relative row ">
						<div class="col-md-10 row">
							<div class="col-md-4">
								<label class="d-block" for="checkIn ">CheckIn</label> <input
									class="form-control" type="date" required name="checkIn">
							</div>
							<div class="col-md-4">
								<label class="d-block" for="Checkout ">Checkout</label> <input
									class="form-control" type="date" required name="checkOut">
							</div>
							<div class="col-md-3 col">
								<label class="d-block" for="Guests ">Guests</label> <input
									class="form-control" type="number" required value="1"
									name="guests">
							</div>
						</div>
						<div class="col-md-2 search">
							<button type="submit" name="op" value="search">Search</button>
						</div>
					</div>

				</form>
			</div>
		<!-- start  Alart   -->
		<%String msg=(String)request.getAttribute("msg");
            if(msg!=null){
            String alartType=(String)request.getSession().getAttribute("alartType");
            %>
		<div class="alert alert-success alert-dismissible <%=alartType%>">
		    <button type="button" class="close" data-dismiss="alert">&times;</button>
		    <strong><%=alartType%>!</strong><%=msg %>
		</div>

		<%}%>
		<!-- end Alart -->
		</div>
		<!-- rooms -->
		<%
			
			Room []avilabelRooms=(Room[])request.getAttribute("avilabelRooms");
		 if(avilabelRooms!=null){
			 int days=(int)request.getAttribute("numberOfDayes");
			 for(Room rm:avilabelRooms){
		%>
		<div class="row search-room">

			<div class="col-sm-3 col-4">
				<h3>Room #</h3>
				<p><%=rm.getNumber() %></p>
			</div>
			<div class="col-sm-3 col-4">
				<h3>Type</h3>
				<p><%=rm.type.getName() %></p>
			</div>
			<div class="col-sm-3 col-4">
				<h3>Total</h3>
				<p><%=rm.type.getPrice()*days %> dz</p>
			</div>
			<div class="col-sm-3 col">
			<form action="booking"  style="width: 100%;height: 100%;">
			<INPUT TYPE="date" NAME="checkIn"  value="<%=request.getParameter("checkIn")%>"CLASS="d-none id" READONLY>
			<INPUT TYPE="date" NAME="checkOut"  value="<%=request.getParameter("checkOut")%>"CLASS="d-none id" READONLY>
			<INPUT TYPE="text" NAME="id"  value="<%=rm.getId() %>"CLASS="d-none id" READONLY>
			<%String style="style=height:100%";
				if(InputsValidation.isLogIn(ses)&&InputsValidation.getSessionInfo(ses).get("role")!=0){
					 style="";
			%>
				<select  name="typeOfPayment" class="  form-control"   >
					<option value="card" class="select-items ">card</option>
					<option value="cash">cash</option>
				</select>
				<%} %>
			
				<button class="d-flex flex-row  form-control "<%=style %>  >
				<span class="m-auto"> Book now</span>
				</button>
			</form>
			</div>
		</div>
		<%}} else if(msg==null){%>
				<div class="alert alert-success alert-dismissible Warning">
		    <button type="button" class="close" data-dismiss="alert">&times;</button>
		    <strong>SOORY!</strong>there is no rooms avilabel in this date
		</div>
		<%} %>
		
		<!-- end rooms -->


	</div>


	<!-- End Container -->
	<!-- footer -->
	<jsp:include page="footer.jsp" />
	<!-- end footer -->