<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@page import="models.Hotel"%>

<!DOCTYPE html>
<html>
<!-- head -->
<jsp:include page="../head.jsp">
	<jsp:param name="title" value='Setting' />
</jsp:include>
<!-- end head -->
    <body class="dashboard">
        <!-- side bar -->
         <jsp:include page="sideBar.jsp"> 
        <jsp:param name="active" value="6" /> 
		</jsp:include> 
        
        
        
        <!-- end side bar -->
        <% Hotel hotel=(Hotel)request.getAttribute("hotel"); %>
        <!-- content -->
        <div class="dashboard-content ">
            <!--   -->
            <nav class="navbar navbar-expand-md "> <h4 class="m-auto">Setting</h4></nav>
            
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
            <!--    -->
            <div class="container">
                <div class=" userModel" id="addModel" style=" position: relative !important;">
                            <!--  body -->
                            <div class="modal-body mt-5">
                                <!-- user Form  -->
                                <form class="log-form "action="" method="POST" id="hotelInfo">
                                    <!-- hotel Name field  -->
                                    <div class="input-group  input-group-lg mb-2">
                                        <div class="input-group-prepend col-md-2  "> <span class="input-group-text" id="sign-input">Name </span></div>
                                        <input id="sign-input" type="text" class="form-control col-md-10" name="name"    placeholder="Hotel Name"
                                         value="<%= hotel.getName()%>">
                                    </div>
                                    <!--  email field  -->
                                    <div class="input-group input-group-lg mb-2">
                                        <div class="input-group-prepend col-md-2"> <span class="input-group-text" id="sign-input">email </span></div>
                                        <input id="sign-input" type="email" class="form-control col-md-10" name="email"    placeholder=" email"
                                        value="<%= hotel.getEmail() %>">
                                    </div>
                                       <!--   field  -->
                                    <div class="input-group input-group-lg mb-2">
                                        <div class="input-group-prepend col-md-2"> <span class="input-group-text" id="sign-input">address </span></div>
                                        <input id="sign-input" type="text" class="form-control col-md-10" name="address"    placeholder="address"
                                         value="<%= hotel.getAddress()%>">
                                    </div>
                                    <div class="input-group input-group-lg mb-2">
                                        <div class="input-group-prepend col-md-2"> <span class="input-group-text" id="sign-input">phone </span></div>
                                        <input id="sign-input" type="text" class="form-control col-md-10" name="phone"    placeholder="phone"
                                         value="<%= hotel.getPhone() %>">
                                    </div>
                                    <!--  fields -->

                                    <!--  fields -->
                                    <div class="input-group input-group-lg mb-2">
                                        <div class="input-group-prepend col-md-2"> <span class="input-group-text" id="sign-input">description </span></div>
                                        <textarea id="sign-input" rows="8"class="form-control col-md-10" name="description"    placeholder="description" ><%= hotel.getDescription() %></textarea>
                                    </div>
                                </form>
                                <!-- End user Form  -->
                            </div>
                            
                            <!-- footer -->
                            <div class="modal-footer footer ">
                                    <button type="submit" class="col-sm " form="hotelInfo">Add</button>
                            </div>
                </div>
            </div>
              <!-- end table  -->
        </div>
        <!-- end content -->
	<!-- footer -->
	<jsp:include page="../footer.jsp" />
	<!-- end footer -->