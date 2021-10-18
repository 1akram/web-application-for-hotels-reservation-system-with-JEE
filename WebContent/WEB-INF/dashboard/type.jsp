<%@page import="models.Amenitie"%>
<%@page import="models.Type"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
        <!-- head -->
        <jsp:include page="../head.jsp"> 
        <jsp:param name="title" value="<%=request.getAttribute(\"title\") %>" /> 
		</jsp:include> 
        <!-- end head -->
    <body class="dashboard">
        <!-- side bar -->
         <jsp:include page="sideBar.jsp"> 
        <jsp:param name="active" value="4" /> 
		</jsp:include> 
        <!-- end side bar -->
        <!-- content -->
        <div class="dashboard-content">
            <!-- navbar -->
            <nav class="navbar navbar-expand-md ">
                  <input class="form-control mr-sm-2 col-sm col-md-9" onkeyup="search()" id="searchInput" type="text" placeholder="Search for ...">
                  <!-- add  button -->
                  <button type="button" class="btn  col-sm col-md-3" data-toggle="modal" data-target="#addModel">
                      Add new type
                  </button>
              </nav>
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
            <!-- add  modal -->
            <div class="modal position-relative userModel" id="addModel">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <!-- header -->
                        <div class="modal-header header">
                            <h4 class="modal-title m-auto">Add New type</h4>
                        </div>
                        <!--  body -->
                        <div class="modal-body">
                            <!--  Form  -->
                            <form class="log-form "action="?op=add" method="POST" id="addForm">
                                <!--   fields  -->
                                <div class="input-group input-group-lg mb-2">
                                    <input id="sign-input" type="text" class="form-control" name="name" required placeholder="Name">
                                </div>
                                <div class="input-group input-group-lg mb-2">
                                    <input id="sign-input" type="number" class="form-control" name="price" required placeholder="price  dz/night">
                                </div>
                                <div class="input-group input-group-lg mb-2">
                                    <input id="sign-input" type="number" class="form-control" name="guests" required placeholder="guests">
                                </div>
                                <div class="input-group input-group-lg mb-2">
                                    <textarea id="sign-input" rows="5" class="form-control" name="description" required placeholder="description"></textarea>
                                </div>
                                <!-- amenities list  -->
                                <div class="checkboxList form-control">
                                    <% Amenitie []amenities=(Amenitie[])request.getAttribute("amenities");
                                       for(Amenitie amenitie:amenities){
                                    %>
                                    <div class="chekboxListItems">
                                        <div class="custom-control custom-checkbox m-auto">
                                            <input type="checkbox" class="custom-control-input" id="<%= amenitie.getName() %>add" name="amenities" value="<%= amenitie.getId() %>">
                                            <label class="custom-control-label" for="<%= amenitie.getName() %>add"><%=amenitie.getName() %></label>
                                        </div>
                                    </div>
                                    <%} %>

                                </div>
                                <!-- end amenities list  -->

                            </form>
                            <!-- End  Form  -->
                        </div>
                        
                        <!-- footer -->
                        <div class="modal-footer footer ">
                                <button type="submit" class="col-sm-8 " form="addForm">Add</button>
                                <button  class="col-sm-4 "data-dismiss="modal">Cancel</button>
                        </div>
                        
                    </div>
                </div>
            </div>
            <!-- end add  modal  -->
            <!-- edit  model  -->
            <div class="modal position-relative userModel" id="editModel">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <!-- header -->
                        <div class="modal-header header">
                            <h4 class="modal-title m-auto">Edit type</h4>
                        </div>
                        <!--  body -->
                        <div class="modal-body">
                            <!--  Form  -->
                            <form class="log-form "action="?op=edit" method="POST" id="editForm">
                                <!--   fields  -->
                                <div class="input-group input-group-lg mb-2">
                                    <input id="sign-input" type="text" class="form-control name" name="name"  placeholder="Name">
                                </div>
                                <div class="input-group input-group-lg mb-2">
                                    <input id="sign-input" type="number" class="form-control price" name="price"  placeholder="price  dz/night">
                                </div>
                                <div class="input-group input-group-lg mb-2">
                                    <input id="sign-input" type="number" class="form-control guests" name="guests"  placeholder="guests">
                                </div>
                                <div class="input-group input-group-lg mb-2">
                                    <textarea id="sign-input"  class="form-control description" name="description"  placeholder="description"></textarea>
                                </div>
                                <!-- amenities list  -->
                                <div class="checkboxList form-control">
                                       <% 
                                       for(Amenitie amenitie:amenities){
                                    %>
                                    <div class="chekboxListItems ">
                                        <div class="custom-control custom-checkbox m-auto">
                                            <input type="checkbox" class="custom-control-input" id="<%= amenitie.getName() %>edit" name="amenities" value="<%= amenitie.getId() %>">
                                            <label class="custom-control-label" for="<%= amenitie.getName() %>edit"><%=amenitie.getName() %></label>
                                        </div>
                                    </div>
                                    <%} %>
                                    
                                    
                                </div>
                                <!-- end amenities list  -->
                                <!-- id field -->
                                <input type="text" name="id" class="d-none id" readonly>

                            </form>
                            <!-- End  Form  -->
                        </div>
                        
                        <!-- footer -->
                        <div class="modal-footer footer ">
                                <button type="submit" class="col-sm-8 " form="editForm">Save</button>
                                <a class="col-sm-4 btn " id="deleteType" href="#">delete</a>
                        </div>
                        
                    </div>
                </div>
            </div>
            <!-- end edit  model  -->
            <!-- upload  model  -->
            <div class="modal position-relative userModel" id="uploadModel">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <!-- header -->
                        <div class="modal-header header">
                            <h4 class="modal-title m-auto">upload</h4>
                        </div>
                        <!--  body -->
                        <div class="modal-body">
                            <!--  Form  -->
                            <form class="log-form "action="" method="POST" id="uploadForm" enctype="multipart/form-data">
                                <!--   fields  -->
                                <div class="input-group input-group-lg mb-2">
                                    <input id="sign-input" type="file" class="form-control name" name="files" multiple> 
                                </div>
                            </form>
                            <!-- End  Form  -->
                        </div>
                        
                        <!-- footer -->
                        <div class="modal-footer footer ">
                                <button type="submit" class="col-sm-8 " form="uploadForm">upload</button>
                                <button  class="col-sm-4 "data-dismiss="modal">Cancel</button>
                        </div>
                        
                    </div>
                </div>
            </div>
            <!-- end upload  model  -->
              <!--  table  -->
              <table class="table table-bordered" id="userTable">
                <thead>
                  <tr>
                    <th>type</th>
                    <th>price dz/night</th>
                    <th>guests</th>
                    <th></th>
                    <th></th>

                  </tr>
                </thead>
                <tbody >
                        
                    <%	Type[]types=(Type[])request.getAttribute("types");
                	for(Type tp:types){
                
                %>
                        <tr id="i<%=tp.getId() %>">
                            <td><%=tp.getName() %></td>
                            <td><%=tp.getPrice()%></td>
                            <td><%=tp.getGuests() %></td>
                            <td class="d-none"><%=tp.getDescription() %></td>
                            <td class="d-none"  >
                            		<%for(Amenitie am:tp.amenities){ %>
                            			<span><%= am.getId()%></span>
                            		<%} %>
                             </td>
                            <td> 
                                <button  type="button" class="btn col-sm" onclick="editType(<%=tp.getId() %>)">Edit</button>
                            </td> 
                             <td> 
                                <button  type="button" class="btn col-sm" onclick="upload(<%=tp.getId() %>)">upload</button>
                            </td>                       
                        </tr>
                        <% } %>

                    </tbody>
              </table>
              <!-- end table  -->
        </div>
        <!-- end content -->
        <!-- footer -->
        <jsp:include page="../footer.jsp"/> 
        <!-- end footer -->