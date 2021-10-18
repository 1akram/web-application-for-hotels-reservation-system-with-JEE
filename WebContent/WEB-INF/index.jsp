<%@page import="security.InputsValidation"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="models.Type"%>
<%@page import="models.Image"%>
<%@page import="models.Hotel"%>


<!DOCTYPE html>
<html>
<!-- head -->

<jsp:include page="head.jsp">
	<jsp:param name="title" value="<%=request.getAttribute(\"title\") %>" />
</jsp:include>
<!-- end head -->
<% 
	Type []types=(Type[])request.getAttribute("types");
	HttpSession ses=request.getSession();
	 Hotel hotel=Hotel.getHotelInfo(); 
%>
<body>



	<div class="bg">
		<img src="img/login-bg.jpg" alt="">
	</div>

	<!--Top Bar -->
	<nav class="navbar navbar-expand-sm  topBar">
		<button class="navbar-toggler" type="button" data-toggle="collapse"
			data-target="#collapsibleNavbar">
			<span class="navbar-toggler-icon"><strong>+</strong></span>
		</button>
		<div class="collapse navbar-collapse" id="collapsibleNavbar">
			<ul class="navbar-nav ">
				<li class="nav-item active"><a class="nav-link"
					href="<%=request.getContextPath() %>/home">Home</a></li>
				<li class="nav-item"><a class="nav-link" href="#Rooms">Rooms</a>
				</li>
				<li class="nav-item"><a class="nav-link" href="#AboutUs">About
						Us</a></li>
				<li class="nav-item"><a class="nav-link " href="#Location">Location</a>
				</li>
				<li class="nav-item"><a class="nav-link " href="#Contact">Contact</a>
				</li>
					<%if(!InputsValidation.isLogIn(ses)){ %>
					<li class="nav-item"><a class="nav-link "
						href="<%=request.getContextPath() %>/login">LogIn</a></li>
					<%}else{ %>
					<%if(InputsValidation.getSessionInfo(ses).get("role")!=0){ %>
						<li class="nav-item"><a class="nav-link "
						href="<%=request.getContextPath() %>/dashboard">Dashboard</a></li>
					<%} else{%>
					<li class="nav-item"><a class="nav-link "
						href="<%=request.getContextPath() %>/orders">Orders</a></li>
					<li class="nav-item"><a class="nav-link "
						href="<%=request.getContextPath() %>/logout">LogOut</a></li>
					<%}} %>
			</ul>
		</div>
	</nav>
	<!--End Top Bar-->
	<div class="">
		<!-- slider start -->

		<div id="slider" class="carousel slide" data-ride="carousel"
			data-interval="5000">
			<div class=" searchTab">
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
					<div class="position-relative row"></div>
				</form>
			</div>
			<div class="carousel-inner carousel_inner_ctm">
				<%
        	if(types.length>0){
        		String activeItemInSlider="active";
        		for(Type tp:types){
        		if(tp.imgs.length>0){%>
				<div class="carousel-item <%=activeItemInSlider%>">
					<img src="<%=request.getContextPath() %>/<%=tp.imgs[0].getUrl() %>"
						alt="<%=tp.getName() %>">
				</div>
				<%activeItemInSlider="";
        		 }
        		}
        		if(activeItemInSlider.equals("active"))
        		{//no Images in this hotel use defualt image%>
				<div class="carousel-item active">
					<img src="<%=request.getContextPath() %>/img/defualt.jpg"
						alt="defualt">
				</div>
				<%}
        		
        	}else{
        		//no rooms in this hotel use defualt image%>
				<div class="carousel-item active">
					<img src="<%=request.getContextPath() %>/img/defualt.jpg"
						alt="defualt">
				</div>
				<% }%>
			</div>
			<a class="carousel-control-prev" href="#slider" data-slide="prev">
				<span class="carousel-control-prev-icon"></span>
			</a> <a class="carousel-control-next" href="#slider" data-slide="next">
				<span class="carousel-control-next-icon"></span>
			</a>
		</div>
		<!-- slider end  -->
		<!-- rooms section  -->
		<div id="Rooms"
			class="container-fluid section sectionOne d-flex flex-column flex-fill">
			<div class="container ">
				<%
        	if(types.length>0){
        		boolean isreverse=false;
        		String reverse;
        		for(Type tp:types){
        		if(tp.imgs.length>0){%>

				<!-- room section  -->
				<%if(isreverse)reverse="flex-row-reverse";else reverse=""; %>
				<div class=" subSection row <%=reverse%>">
					<%isreverse= !isreverse; %>
					<div class="leftSection col-lg-6  ">
						<div class="carousel slide" data-ride="carousel"
							data-interval="5000">
							<div class="carousel-caption">
								<h3>
									<a href="?type=<%=tp.getId() %>>"><%=tp.getName() %></a>
								</h3>
							</div>
							<div class="carousel-inner carousel_inner_ctm">

								<% String activeItemInSlider="active";
               for(Image img:tp.imgs){
        		%>
								<div class="carousel-item <%=activeItemInSlider%>">
									<img src="<%=request.getContextPath() %>/<%=img.getUrl() %>"
										alt="<%=tp.getName() %>">
								</div>
								<%activeItemInSlider="";
        		}%>
							</div>
						</div>
					</div>
					<div class="rightSection col-lg-6 ">

						<div class="row justify-content-center flex-column no-gutters">
							<span> <strong><%=tp.getPrice() %></strong>
							</span> <span> <strong>DZ</strong>/night
							</span>
						</div>
						<div class="row justify-content-center flex-column no-gutters">
							<span> <strong><%=tp.getGuests() %></strong>
							</span> <span> <strong>Guests</strong>
							</span>
						</div>


					</div>
				</div>

				<!--end  room section  -->


				<%
        		}
        		}
        	}
        		%>
			</div>
		</div>
		<!-- end rooms section  -->
		<!-- about us section  -->

		<div id="AboutUs" class="container-fluid section sectionTwo">
			<div class="container  ">
				<%if(hotel.getDescription()!=null){ %>
				<h1>About Us</h1>
				<div class="jumbotron">
					<p class=""><%=hotel.getDescription() %>
					</p>
				</div>
				<%}%>
			</div>
		</div>
		<!-- end about us section  -->
		<!-- google map section  -->
		<div id="Location" class="container-fluid section sectionThree"><iframe
				src="https://www.google.com/maps/embed?pb=!1m10!1m8!1m3!1d1583.7862513493064!2d7.1431837!3d35.398633!3m2!1i1024!2i768!4f13.1!5e1!3m2!1sfr!2sdz!4v1578856029902!5m2!1sfr!2sdz"
				width="100%" height="100%" style="border: 0;"> </iframe></div>
		<!-- end google map section  -->
		<!-- Contact section  -->
		<div id="Contact" class="container-fluid section sectionFour">
			<div class="container  ">
				<div class="d-flex flex-row  contactInfo">
					<div class="col-sm-4">
						<span> address </span><%=hotel.getAddress() %></div>
					<div class="col-sm-4">
						<span> Phone </span><%=hotel.getPhone() %></div>
					<div class="col-sm-4">
						<span> email </span><%= hotel.getEmail() %></div>
				</div>
			</div>
		</div>
		<!-- end contact section   -->
		<!-- Contact form  -->
		<div class="container-fluid section sectionFive">
			<div class="container">
				<form class="" action="" method="POST">
					<div class="input-group input-group-lg mb-2">
						<input id="sign-input" type="email" class="form-control"
							name="email" required placeholder="Email">
					</div>
					<div class="input-group input-group-lg mb-2">
						<input type="text" class="form-control" name="name" required
							placeholder="name">
					</div>
					<div class="input-group input-group-lg mb-2">
						<textarea name="subject" rows="8" required placeholder="Subject"></textarea>
					</div>
					<div class="d-flex flex-wrap  ">
						<input type="submit" value="Send" name="signin"
							class="flex-fill btn  submit-btn  ">
					</div>
				</form>
			</div>



		</div>
		<!-- end contact Form  -->








	</div>



	<!-- footer -->
	<jsp:include page="footer.jsp" />
	<!-- end footer -->