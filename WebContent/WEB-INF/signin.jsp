<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<!-- head -->
<jsp:include page="head.jsp">
	<jsp:param name="title" value="<%=request.getAttribute(\"title\") %>" />
</jsp:include>
<!-- end head -->
<body>
	<div
		class="position-fixed fixed-top d-flex justify-content-around log-top-bar ">
		<!--Top Bar -->
		<div>
			<a href="<%=request.getContextPath() %>/index">Home</a>
		</div>
		<div>
			<a href="<%=request.getContextPath() %>/login">LogIn</a>
		</div>
	</div>
	<!--End Top Bar-->
	<!-- Container  -->

	<div
		class="log-container d-flex direc justify-content-center align-items-center flex-column">
		<!-- signin Form  -->
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

		<form class="log-form mb-5" action="<%=request.getContextPath() %>/signin" method="POST">
			<!-- email and password fields  -->
			<div class="input-group input-group-lg mb-2">
				<input id="sign-input" type="email" class="form-control"
					name="email" required placeholder="Email">
			</div>
			<div class="input-group input-group-lg mb-2">
				<input id="sign-input" type="password" class="form-control"
					name="password" required placeholder="Password">
			</div>
			<!-- first and last name fields  -->
			<div class="input-group input-group-lg mb-2">
				<input id="sign-input" type="text" class="form-control"
					name="firstName" required placeholder="First Name"> <input
					id="sign-input" type="text" class="form-control" name="lastName"
					required placeholder="Last Name">
			</div>
			<!-- date of birth field -->
			<div class="input-group input-group-lg mb-2">
				<input id="sign-input" type="date" class="form-control"
					name="dateOfBirth" required>
			</div>
			<!-- gender field -->
			<div class="input-group input-group-lg mb-2">
				<select class="form-control" name="gender">
					<option>male</option>
					<option>female</option>
				</select>
			</div>
			<!-- address fields -->
			<div class="input-group input-group-lg mb-2">
				<input id="sign-input" type="text" class="form-control"
					name="street" placeholder="street">
			</div>
			<div class="input-group input-group-lg mb-2">
				<input id="sign-input" type="text" class="form-control" name="city"
					placeholder="city"> <input id="sign-input" type="text"
					class="form-control" name="state" placeholder="state"> <input
					id="sign-input" type="number" class="form-control" name="zipCode"
					placeholder="zipCode">
			</div>
			<!-- phone number fields  -->
			<div class="input-group input-group-lg mb-2">
				<div class="input-group-prepend">
					<span class="input-group-text" id="sign-input">+213 </span>
				</div>
				<input id="sign-input" type="text" class="form-control"
					name="phone" id="phoneN" placeholder="Phone Number">
			</div>
			<!-- submit button  -->
			<div class="d-flex flex-wrap  mb-3">
				<input type="submit" id="signin" value="signin" name="signin"
					class="flex-fill btn  log-btn  ">
			</div>
		</form>
		<!-- End signin Form  -->


	</div>
	<!-- End Container -->



	<!-- End Container -->
	<!-- footer -->
	<jsp:include page="footer.jsp" />
	<!-- end footer -->