<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="ISO-8859-1">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-0evHe/X+R7YkIZDRvuzKMRqM+OrBnVFBL6DOitfPri4tjfHxaWutUpFmBp4vmVor" crossorigin="anonymous">
        <link href="mystyles.css" rel="stylesheet">
        <script src="javascript.js"></script>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js" ></script>
        <title>My Account</title>
        
        
    </head>
    <body style="background-color: #F2EDE4;">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/js/bootstrap.bundle.min.js" integrity="sha384-pprn3073KE6tl6bjs2QrFaJGz5/SUsLqktiwsUTF55Jfv3qYSDhgCecCxMW52nD2" crossorigin="anonymous"></script>
	
        <nav class="navbar navbar-expand-lg" style="background-color:#D9C9BA;">
            <div class="container-fluid">
                 <a class="navbar-brand" href="#">
                    <img src="./images/ella.jpg" alt="" width="60" height="60" class="d-inline-block align-text-top" style="border-radius: 2rem">
                    Ella's Books
                </a>
                <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"></span>
                </button>

                <div class="collapse navbar-collapse justify-content-between" id="navbarSupportedContent">
                    <ul class="navbar-nav">
                        <li class="nav-item">
                            <a class="nav-link" href="homepage.jsp">Home</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="searchbooks.jsp">Search Books</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="mystats.jsp">My Stats</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="mygoals.jsp">My Goals</a>
                        </li>

                        <li class="nav-item">
                            <a class="nav-link" href="myshelves.jsp">My Shelves</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="myaccount.jsp">My Account</a>
                        </li>
                    </ul>
                    <form class="d-flex">
                        <input class="form-control me-2" type="search" placeholder="Search books..." aria-label="Search">
                        <button class="btn btn-outline-success" type="submit"  id="searchBtn">Search</button>
                    </form>
                </div>
            </div>
        </nav>

        <section class="vh-100" style="background-color: #F2EDE4;">
            <div class="container p-3 h-100">
                <div class="row d-flex justify-content-center h-100">
                    <div class="col col-xl-8">
                        <div class="card" style="border-radius: 1rem;">
                            <div class="row g-0">
                                <div class="col-md-4" style="display: block;">
                                    <img src="./images/ella.jpg" class="img-fluid" id="profilePic" style="border-radius: 1rem;">
                                    <button type="button" class="btn btn-primary" id="logOutBtn" onclick="logOut()">Log Out</button>
                                </div>
                                <div class="col-md-8">
                                    <div class="card-body" id="accountCard">
                                        <h5 class="card-title mb-3 pb-3" id="accountTitle" style="text-align: center;">My Account Information</h5>
                          				<div id="userInfo">
                          					<script>
												$(document).ready(function() { 
													var username = $("#accUsername");
													var email = $("#accEmail");
													alert(username);
													alert(email);
													
													console.log("Button clicked. Sending AJAX request.");
													$.get("DisplayUserInfo?timestamp=" + new Date().getTime(), function(responseText) {  
														console.log("Received response from server:", responseText);
														alert("before response text");
														var div = $("#userInfo");
														div.html(responseText);
													});
													return false;
												});
											</script>
                          					<div class="form-outline mb-4">
	                                            Username:&nbsp;<div id="accUsername" style="display: inline-flex;"></div>
	                                        </div>
	
	                                        <div class="form-outline mb-4">
	                                            Email:&nbsp;<div id="accEmail" style="display: inline-flex;"></div>
	                                        </div>
	
	                                        <div class="form-outline mb-4">
	                                            Bio:&nbsp;<div id="accBio" style="display: inline-flex;">I love to read!</div>
	                                        </div>
	
	                                        <div class="form-outline mb-4">
	                                            Total Books:&nbsp;<div id="accNumBooks" style="display: inline-flex;"> 120</div>
	                                        </div>
	
	                                        <div class="form-outline mb-4">
	                                            Favorite Book:&nbsp;<div id="accFavBook" style="display: inline-flex;">Trace of Evil</div>
	                                        </div>
	
	                                        <div class="form-outline mb-4">
	                                            Favorite Author:&nbsp;<div id="accfavAuthor" style="display: inline-flex;">Alice Blanchard</div>
	                                        </div>
	                                    </div>
	                                    <!--<button type="button" class="btn btn-primary" id="logOutBtn">Log Out</button>-->
                          				
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div><!--end col class-->
                </div><!-- end row d-flex class-->
            </div><!--end container class-->
        </section> <!--includes the entire screen--> 

    </body>
</html>
