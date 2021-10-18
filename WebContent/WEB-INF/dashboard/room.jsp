<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="models.Type"%>
<%@page import="models.Floor"%>
<%@page import="models.Room"%>



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
		<jsp:param name="active" value="1" />
	</jsp:include>
	<!-- end side bar -->
	<!-- content -->
	<DIV CLASS="dashboard-content">
		<!-- navbar -->
		<NAV CLASS="navbar navbar-expand-md ">
			<INPUT CLASS="form-control mr-sm-2 col-sm col-md-9"
				ONKEYUP="search()" ID="searchInput" TYPE="text"
				PLACEHOLDER="Search for room">
			<!-- add room button -->
			<BUTTON TYPE="button" CLASS="btn  col-sm col-md-3"
				DATA-TOGGLE="modal" DATA-TARGET="#addModel">Add new room</BUTTON>
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
		<!-- add room modal -->
		<DIV CLASS="modal position-relative userModel" ID="addModel">
			<DIV CLASS="modal-dialog">
				<DIV CLASS="modal-content">
					<!-- header -->
					<DIV CLASS="modal-header header">
						<H4 CLASS="modal-title m-auto">Add New room</H4>
					</DIV>
					<!--  body -->
					<DIV CLASS="modal-body">
						<!-- room Form  -->
						<FORM CLASS="log-form " ACTION="?op=add" METHOD="POST"
							ID="addForm">
							<!-- room number  -->
							<DIV CLASS="input-group input-group-lg mb-2">
								<INPUT ID="sign-input" TYPE="number" CLASS="form-control"
									NAME="number" REQUIRED PLACEHOLDER="room number">
							</DIV>
							<!-- room type  -->
							<DIV CLASS="input-group input-group-lg mb-2">
								<SELECT CLASS="form-control" NAME="type" REQUIRED>
									<OPTION VALUE>Select type</OPTION>
									<% Type[] types=(Type[])request.getAttribute("types");
                                	   for(Type tp:types){
                              		 %>
									<OPTION VALUE="<%=tp.getId()%>"><%=tp.getName() %></OPTION>
									<%}%>
								</SELECT>
							</DIV>
							<!-- room floor  -->
							<DIV CLASS="input-group input-group-lg mb-2">
								<SELECT CLASS="form-control" NAME="floor" REQUIRED>
									<OPTION VALUE>Select floor</OPTION>
									<% Floor[] floors=(Floor[])request.getAttribute("floors");
                                	   for(Floor fr:floors){
                              		 %>
									<OPTION VALUE="<%=fr.getId()%>"><%=fr.getName() %></OPTION>
									<%}%>
								</SELECT>
							</DIV>
						</FORM>
						<!-- End room Form  -->
					</DIV>
					<!-- footer -->
					<DIV CLASS="modal-footer footer ">
						<BUTTON TYPE="submit" CLASS="col-sm-8 " FORM="addForm">Add</BUTTON>
						<BUTTON CLASS="col-sm-4 " DATA-DISMISS="modal">Cancel</BUTTON>
					</DIV>
				</DIV>
			</DIV>
		</DIV>
		<!-- end add room modal  -->
		<!-- edit room model  -->
		<DIV CLASS="modal position-relative userModel" ID="editModel">
			<DIV CLASS="modal-dialog">
				<DIV CLASS="modal-content">
					<!-- header -->
					<DIV CLASS="modal-header header">
						<H4 CLASS="modal-title m-auto">Edit room</H4>
					</DIV>
					<!--  body -->
					<DIV CLASS="modal-body">
						<!-- room Form  -->
						<FORM CLASS="log-form " ACTION="?op=edit" METHOD="POST"
							ID="editForm">
							<!-- room number  -->
							<DIV CLASS="input-group input-group-lg mb-2">
								<INPUT ID="sign-input" TYPE="number"
									CLASS="form-control roomNumber" NAME="number"
									PLACEHOLDER="room number">
							</DIV>
							<!-- room type  -->
							<DIV CLASS="input-group input-group-lg mb-2">
								<SELECT CLASS="form-control type" NAME="type" REQUIRED>
									<OPTION VALUE>Select type</OPTION>
									<%for(Type tp:types){%>
									<OPTION VALUE="<%=tp.getId()%>"><%=tp.getName() %></OPTION>
									<%}%>
								</SELECT>
							</DIV>
							<!-- room floor  -->
							<DIV CLASS="input-group input-group-lg mb-2">
								<SELECT CLASS="form-control floor" NAME="floor" REQUIRED>
									<OPTION VALUE>Select floor</OPTION>
									<% for(Floor fr:floors){%>
									<OPTION VALUE="<%=fr.getId()%>"><%=fr.getName() %></OPTION>
									<%}%>
								</SELECT>
							</DIV>
							<!-- id field -->
							<input type="text" name="id" class="d-none id" readonly>
						</FORM>
					</DIV>
					<!-- footer -->
					<DIV CLASS="modal-footer footer ">
						<BUTTON TYPE="submit" CLASS="col-sm-8 " FORM="editForm">Save</BUTTON>
						<A CLASS="col-sm-4 btn" ID="deleteRoom" >delet</A>
					</DIV>
				</DIV>
			</DIV>
		</DIV>
		<!-- end edit room model  -->

		<!-- user table  -->
		<TABLE CLASS="table table-bordered" ID="userTable">
			<THEAD>
				<TR>
					<TH>room number</TH>
					<TH>type</TH>
					<TH>floor</TH>
					<TH></TH>
				</TR>
			</THEAD>
			<TBODY>
				<% Room []rooms=(Room[])request.getAttribute("rooms");
			for(Room rm:rooms){ %>
				<TR ID="i<%=rm.getId()%>">
					<TD><%=rm.getNumber()%></TD>
					<TD ID="<%=rm.type.getId() %>"><%=rm.type.getName()%></TD>
					<TD ID="<%=rm.floor.getId() %>"><%=rm.floor.getName()%> </TD>
					<TD>
						<BUTTON TYPE="button" CLASS="btn col-sm" ONCLICK="editRoom(<%=rm.getId()%>)">Edit</BUTTON>
					</TD>
				</TR>
				<%} %>
			</TBODY>
		</TABLE>
		<!-- end table  -->
	</DIV>
	<!-- end content -->
	<!-- footer -->
	<jsp:include page="../footer.jsp" />
	<!-- end footer -->