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
			<a href="<%=request.getContextPath() %>">Home</a>
		</div>
		<% 
               	String from=(String)request.getAttribute("from");
               boolean isFromDashboard= from!=null&&from.equals("dashboard");
              	if(!isFromDashboard) {
               %>
		<div>
			<a href="<%=request.getContextPath() %>/signin">SignIn</a>
		</div>
		<%} %>
	</div>
	<!--End Top Bar-->
	<!-- Container  -->
	<div
		class="log-container d-flex direc justify-content-center align-items-center flex-column">
		<!-- Avatar -->
		<div>
			<img class="rounded-circle avatar "
				src="<%=request.getContextPath() %>/img/avatar.png" alt="">
		</div>
		<!-- End Avatar  -->

		<!-- Login Form  -->
		<form class="log-form mb-5"
			action="<%=request.getContextPath() %>/login" method="POST">
			
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
			<div class="input-group input-group-lg mb-2">
				<div class="input-group-prepend">
					<span class="input-group-text">Email @ </span>
				</div>
				<input type="email" class="form-control" name="email" id="email"
					required>
			</div>
			<div class="input-group input-group-lg mb-2">
				<div class="input-group-prepend">
					<span class="input-group-text ">Password</span>
				</div>
				<input type="password" class="form-control" name="password"
					id="password" required>
			</div>
			<!-- ******** -->
			<% if(isFromDashboard) {%>
			<div class="input-group input-group-lg mb-2">
				<div class="input-group-prepend">
					<span class="input-group-text ">Role</span>
				</div>

				<select class="form-control" name="role" required>
					<option value=1>employee</option>
					<option value=2>manager</option>
				</select>
			</div>
			<%} %>
			<!-- ********* -->
			<div class="d-flex flex-wrap  mb-3">
				<input type="submit" id="login" value="LogIn" name="login"
					class="flex-fill btn  log-btn  ">
			</div>
		</form>
		<!-- End Login Form  -->
	</div>
	<!-- End Container -->
	<!-- footer -->
	<jsp:include page="footer.jsp" />
	<!-- end footer -->