<%@page import="security.InputsValidation"%>
<%@page import="java.util.HashMap"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<DIV CLASS="dashboard-sidebar">
	<DIV CLASS="sidebar-header">
		<SPAN>DashBoard</SPAN>
	</DIV>
	<%
		String activeSidBar = "sidBarActive";
		String[] active = new String[] { "", "", "", "", "", "", "" };
		String index = request.getParameter("active");
		if (index != null)
			active[Integer.parseInt(index)] = activeSidBar;
		HttpSession ses=request.getSession();
		HashMap<String, Integer> sesInfo = InputsValidation.getSessionInfo(ses);
		
	%>
	<UL CLASS="nav flex-column">
		<LI CLASS="nav-item"><A CLASS="nav-link <%=active[0]%>"
			HREF="client">client</A></LI>
		<LI CLASS=" nav-item "><A CLASS="nav-link <%=active[2]%>"
			HREF="floor">floor </A></LI>
		<LI CLASS="nav-item "><A CLASS="nav-link <%=active[3]%> "
			HREF="amenities">amenities </A></LI>
		<LI CLASS="nav-item "><A CLASS="nav-link <%=active[4]%>"
			HREF="type">type</A></LI>
		<LI CLASS="nav-item"><A CLASS="nav-link <%=active[1]%>"
					HREF="room">room</A></LI>

		<LI CLASS="nav-item "><A CLASS="nav-link "
			HREF="<%=request.getContextPath() %>/search">search</A></LI>
		<LI CLASS="nav-item "><A CLASS="nav-link "
			HREF="<%=request.getContextPath() %>/orders">orders</A></LI>
		<%
			if ( sesInfo.get("role") == 2) {
		%>
		<LI CLASS="nav-item"><A CLASS="nav-link  <%=active[5]%>"
			HREF="employee">employee</A></LI>
		<LI CLASS="nav-item"><A CLASS="nav-link <%=active[6]%>"
			HREF="setting">setting</A></LI>
		<%}%>
		<LI CLASS="nav-item "><A CLASS="nav-link "
			HREF="logout">logout</A></LI>
	</UL>
</DIV>