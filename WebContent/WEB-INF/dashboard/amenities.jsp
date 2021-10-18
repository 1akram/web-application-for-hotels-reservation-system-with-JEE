<%@page import="models.Amenitie"%>
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
		<jsp:param name="active" value="3" />
	</jsp:include>
	<!-- end side bar -->
	<!-- content -->
	<DIV CLASS="dashboard-content ">
		<!-- amenities form -->
		<NAV CLASS="navbar navbar-expand-md ">
			<DIV CLASS="w-100">
				<FORM ACTION="amenities" METHOD="POST"
					CLASS="form-inline  justify-content-around  ">
					<INPUT CLASS="form-control flex-grow-1 " REQUIRED NAME="amenities"
						TYPE="text" PLACEHOLDER="Add New Amenitie">
					<!-- add floor button -->
					<BUTTON TYPE="submit" CLASS="btn ">Add new amenitie</BUTTON>
				</FORM>
			</DIV>
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
        <!-- amenities table  -->
		<DIV CLASS="">
			<TABLE CLASS="table table-bordered floor">
				<THEAD>
					<TR>
						<TH>Amenities</TH>
						<TH></TH>
					</TR>
				</THEAD>
				<TBODY>
					<% Amenitie[]amenities=(Amenitie[])request.getAttribute("amenities");
                    	for(Amenitie a:amenities){%>
					<TR>
						<TD CLASS="col-sm-9"><%=a.getName()%></TD>
						<TD CLASS="col-sm-3"><A
							HREF="amenities?op=delete&amp;id=<%= a.getId() %>"
							CLASS="btn col-sm">delete</A>
						</TD>
					</TR>
					<%}%>
				</TBODY>
			</TABLE>
		</DIV>
		<!-- end table  -->
	</DIV>
	<!-- end content -->

	<!-- footer -->
	<jsp:include page="../footer.jsp" />
	<!-- end footer -->