<%@page import="models.Client"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<HTML>
<!-- head -->
<jsp:include page="../head.jsp">
	<jsp:param name="title" value='<%=request.getAttribute(\"title\") %>' />
</jsp:include>
<!-- end head -->
<BODY CLASS="dashboard">
	<!-- side bar -->
	<jsp:include page="sideBar.jsp">
		<jsp:param name="active" value="0" />
	</jsp:include>
	<!-- end side bar -->
	<!-- content -->
	<DIV CLASS="dashboard-content">
		<!-- navbar -->
		<NAV CLASS="navbar navbar-expand-md ">
			<INPUT CLASS="form-control mr-sm-2 col-sm col-md-9"
				ONKEYUP="search()" ID="searchInput" TYPE="text"
				PLACEHOLDER="Search for user">
			<!-- add user button -->
			<BUTTON TYPE="button" CLASS="btn  col-sm col-md-3"
				DATA-TOGGLE="modal" DATA-TARGET="#addModel">Add new user
			</BUTTON>
		</NAV>
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
            <!-- add User modal -->
		<DIV CLASS="modal position-relative userModel" ID="addModel">
			<DIV CLASS="modal-dialog">
				<DIV CLASS="modal-content">
					<!-- header -->
					<DIV CLASS="modal-header header">
						<H4 CLASS="modal-title m-auto">Add New User</H4>
					</DIV>
					<!--  body -->
					<DIV CLASS="modal-body">
						<!-- user Form  -->
						<FORM CLASS="log-form " ACTION="?op=add" METHOD="POST"
							ID="addForm">
							<!-- email and password fields  -->
							<DIV CLASS="input-group input-group-lg mb-2">
								<INPUT ID="sign-input" TYPE="email" CLASS="form-control"
									NAME="email" REQUIRED PLACEHOLDER="Email">
							</DIV>
							<DIV CLASS="input-group input-group-lg mb-2">
								<INPUT ID="sign-input" TYPE="password" CLASS="form-control"
									NAME="password" REQUIRED PLACEHOLDER="Password">
							</DIV>
							<!-- first and last name fields  -->
							<DIV CLASS="input-group input-group-lg mb-2">
								<INPUT ID="sign-input" TYPE="text" CLASS="form-control"
									NAME="firstName" REQUIRED PLACEHOLDER="First Name"> <INPUT
									ID="sign-input" TYPE="text" CLASS="form-control"
									NAME="lastName" REQUIRED PLACEHOLDER="Last Name">
							</DIV>
							<!-- date of birth field -->
							<DIV CLASS="input-group input-group-lg mb-2">
								<INPUT ID="sign-input" TYPE="date" CLASS="form-control"
									NAME="dateOfBirth" REQUIRED>
							</DIV>
							<!-- gender field -->
							<DIV CLASS="input-group input-group-lg mb-2">
								<SELECT CLASS="form-control" NAME="gender" REQUIRED>
									<OPTION VALUE="">select gender</OPTION>
									<OPTION VALUE="male">male</OPTION>
									<OPTION VALUE="female">female</OPTION>
								</SELECT>
							</DIV>
							<!-- address fields -->
							<DIV CLASS="input-group input-group-lg mb-2">
								<INPUT ID="sign-input" TYPE="text" CLASS="form-control"
									NAME="street" REQUIRED PLACEHOLDER="street">
							</DIV>
							<DIV CLASS="input-group input-group-lg mb-2">
								<INPUT ID="sign-input" TYPE="text" CLASS="form-control"
									NAME="city" REQUIRED PLACEHOLDER="city"> <INPUT
									ID="sign-input" TYPE="text" CLASS="form-control" NAME="state"
									REQUIRED PLACEHOLDER="state"> <INPUT ID="sign-input"
									TYPE="number" CLASS="form-control" NAME="zipCode" REQUIRED
									PLACEHOLDER="zipCode">
							</DIV>
							<!-- phone number fields  -->
							<DIV CLASS="input-group input-group-lg mb-2">
								<DIV CLASS="input-group-prepend">
									<SPAN CLASS="input-group-text" ID="sign-input">+213 </SPAN>
								</DIV>
								<INPUT ID="sign-input" TYPE="text" CLASS="form-control"
									NAME="phone" REQUIRED PLACEHOLDER="Phone Number">
							</DIV>
						</FORM>
						<!-- End user Form  -->
					</DIV>
					<!-- footer -->
					<DIV CLASS="modal-footer footer ">
						<BUTTON TYPE="submit" CLASS="col-sm-8 " FORM="addForm">Add</BUTTON>
						<BUTTON CLASS="col-sm-4 " DATA-DISMISS="modal">Cancel</BUTTON>
					</DIV>
				</DIV>
			</DIV>
		</DIV>
		<!-- end add User modal  -->
		<!-- edit user model  -->
		<DIV CLASS="modal position-relative userModel" ID="editModel">
			<DIV CLASS="modal-dialog">
				<DIV CLASS="modal-content">
					<!-- header -->
					<DIV CLASS="modal-header header">
						<H4 CLASS="modal-title m-auto">Edit User</H4>
					</DIV>
					<!--  body -->
					<DIV CLASS="modal-body">
						<!-- user Form  -->
						<FORM CLASS="log-form " ACTION="?op=edit" METHOD="POST"
							ID="editForm">
							<!-- email and password fields  -->
							<DIV CLASS="input-group input-group-lg mb-2">
								<INPUT ID="sign-input " TYPE="email" CLASS="form-control email"
									NAME="email" REQUIRED PLACEHOLDER="Email" VALUE="">
							</DIV>
							<DIV CLASS="input-group input-group-lg mb-2">
								<INPUT ID="sign-input" TYPE="password"
									CLASS="form-control password" NAME="password"
									PLACEHOLDER="Password" VALUE="">
							</DIV>
							<!-- first and last name fields  -->
							<DIV CLASS="input-group input-group-lg mb-2">
								<INPUT ID="sign-input" TYPE="text"
									CLASS="form-control firstName" NAME="firstName" REQUIRED
									PLACEHOLDER="First Name" VALUE=""> <INPUT
									ID="sign-input" TYPE="text" CLASS="form-control lastName"
									NAME="lastName" REQUIRED PLACEHOLDER="Last Name" VALUE="">
							</DIV>
							<!-- date of birth field -->
							<DIV CLASS="input-group input-group-lg mb-2">
								<INPUT ID="sign-input" TYPE="date"
									CLASS="form-control dateOfBirth" NAME="dateOfBirth" REQUIRED
									VALUE="">
							</DIV>
							<!-- gender field -->
							<DIV CLASS="input-group input-group-lg mb-2">
								<SELECT CLASS="form-control gender" NAME="gender" REQUIRED>
									<OPTION VALUE="">select gender</OPTION>
									<OPTION VALUE="male">male</OPTION>
									<OPTION VALUE="female">female</OPTION>
								</SELECT>
							</DIV>
							<!-- address fields -->
							<DIV CLASS="input-group input-group-lg mb-2">
								<INPUT ID="sign-input" TYPE="text" CLASS="form-control street"
									NAME="street" REQUIRED PLACEHOLDER="street" VALUE="">
							</DIV>
							<DIV CLASS="input-group input-group-lg mb-2">
								<INPUT ID="sign-input" TYPE="text" CLASS="form-control city"
									NAME="city" REQUIRED PLACEHOLDER="city" VALUE=""> <INPUT
									ID="sign-input" TYPE="text" CLASS="form-control state"
									NAME="state" REQUIRED PLACEHOLDER="state" VALUE=""> <INPUT
									ID="sign-input" TYPE="number" CLASS="form-control zipCode"
									NAME="zipCode" REQUIRED PLACEHOLDER="zipCode" VALUE="">
							</DIV>
							<!-- phone number fields  -->
							<DIV CLASS="input-group input-group-lg mb-2">
								<DIV CLASS="input-group-prepend">
									<SPAN CLASS="input-group-text" ID="sign-input">+213 </SPAN>
								</DIV>
								<INPUT ID="sign-input" TYPE="text" CLASS="form-control phoneN"
									NAME="phone" REQUIRED PLACEHOLDER="Phone Number" VALUE="">
							</DIV>
							<!-- id field -->
							<INPUT TYPE="text" NAME="id" CLASS="d-none id" READONLY>
						</FORM>
						<!-- End user Form  -->
					</DIV>

					<!-- footer -->
					<DIV CLASS="modal-footer footer ">
						<BUTTON TYPE="submit" CLASS="col-sm-8 " FORM="editForm">Save</BUTTON>
						<A CLASS="col-sm-4 btn " ID="deleteUser" HREF="#">delete</A>
					</DIV>

				</DIV>
			</DIV>
		</DIV>
		<!-- end edit user model  -->
		<!-- user table  -->
		<TABLE CLASS="table table-bordered" ID="userTable">
			<THEAD>
				<TR>
					<TH>First name</TH>
					<TH>Last name</TH>
					<TH>Email</TH>
					<TH></TH>
				</TR>
			</THEAD>
			<TBODY>
				<%	Client[]clients=(Client[])request.getAttribute("clients");
                	for(Client cl:clients){
                %>
				<TR ID="i<%= cl.getId() %>">
					<TD><%= cl.getFirstName() %></TD>
					<TD><%= cl.getLastName() %></TD>
					<TD><%=cl.getEmail() %></TD>
					<TD CLASS="d-none"><%=cl.getBirthDay() %></TD>
					<TD CLASS="d-none"><%=cl.getGender() %></TD>
					<TD CLASS="d-none"><%=cl.address.getStreet() %></TD>
					<TD CLASS="d-none"><%=cl.address.getCity() %></TD>
					<TD CLASS="d-none"><%=cl.address.getState() %></TD>
					<TD CLASS="d-none"><%=cl.address.getZipCode() %></TD>
					<TD CLASS="d-none"><%=cl.getphone() %></TD>
					<TD>
						<BUTTON TYPE="button" CLASS="btn col-sm"
							ONCLICK="editUser(<%= cl.getId() %>)">Edit
						</BUTTON>
					</TD>
				</TR>
				<% } %>
			</TBODY>
		</TABLE>
		<!-- end table  -->
	</DIV>
	<!-- end content -->
	<!-- footer -->
	<jsp:include page="../footer.jsp" />
	<!-- end footer -->