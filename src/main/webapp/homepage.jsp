<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="ISO-8859-1">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-0evHe/X+R7YkIZDRvuzKMRqM+OrBnVFBL6DOitfPri4tjfHxaWutUpFmBp4vmVor" crossorigin="anonymous">
        <link href="mystyles.css" rel="stylesheet">
        <script src="http://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js" ></script>
        <title>Homepage</title>
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
                            <a class="nav-link" href="stats.html">Stats</a>
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
                        <input class="form-control me-2" id="searchInput" type="search" placeholder="Search books..." aria-label="Search">
                        <button class="btn btn-outline-success" id="searchBooksBtn">Search</button>
                    </form>
                </div>
            </div>
        </nav>

		<script>
			$(document).ready(function() { 
				$.get("HomeDisplay?timestamp=" + new Date().getTime(), function(responseText) {  
					console.log("Received response from server:", responseText);
					//alert("before response text");
					var div = $("#homepageRow");
					div.html(responseText);
				});
				return false;
			});
		
			$(document).on("click", "#updateCurrentBookBtn", function() { 
				var numPages = $("#updateCurrentRead").val();
				alert(numPages);
				
				console.log("Button clicked. Sending AJAX request.");
				$.get("UpdateCurrentRead?timestamp=" + new Date().getTime(), { newGoalTitle: title }, function(responseText) {  
					console.log("Received response from server:", responseText);
					alert("before response text");
					if (type == 1) {
						var ul = $("#monthGoalsList");
						ul.append(responseText);
					} else {
						var ul = $("#yearGoalsList");
						ul.append(responseText);
					}
					
					//document.getElementById("monthGoalsList").append = responseText;
				});
				return false;
			});
		</script>

        <div class="container-fluid">
            <div class="row m-3" id="homepageRow">
                <div class="col-md-6 p-4" id="leftColHome">
                    <div class="row mb-4" id="goalMRow">
                        <div class="card" style="border-radius: 1rem;">
                            <div class="card-body">
                              <h5 class="card-title"></h5>
                              <p class="card-text"></p>
                            </div>
                          </div>
                    </div>
                    <div class="row mb-4" id="goalYRow">
                        <div class="card" style="border-radius: 1rem;">
                            <div class="card-body">
                              <h5 class="card-title"></h5>
                              <p class="card-text"></p>
                            </div>
                        </div>
                    </div>
                    <div class="row" id="progressRow">
                        <div class="card" style="border-radius: 1rem;">
                            <div class="row g-0">
                              <div class="col-md-4">
                                <img src="dark-bookshelf.jpg" class="img-fluid" style="border-radius: 1rem;">
                              </div>
                              <div class="col-md-8">
                                <div class="card-body">
                                  <h5 class="card-title">Current Book</h5>
                                  <p class="card-text">Title</p>
                                  <p class="card-text">Author</p>
                                  <p class="card-text">Progress Bar</p>
                                  <p class="card-text"><small class="text-muted">Started reading on 4/23/20</small></p>
                                  <form name="updateCurrentBook" method="post" action="UpdateCurrentRead">
	                                  <div class="form-outline" style="display: inline-flex;">
	                                   	<input type="text" id="updateCurrentRead" name="updateCurrentRead" class="form-control form-control-md" placeholder="I'm on page..."/>
	                                  </div>
	                                  <button class="btn btn-primary" id="updateCurrentBookBtn">Update Progress</button>
                                  </form>
                                  <form method="post" action="FinishBook">
                                  	  <button class="btn btn-primary" id="finishBookBtn">I've finished this book!</button>
                                  </form>
                                </div>
                              </div>
                            </div>
                          </div>
                    </div>
                </div>
                <div class="col-md-6 p-4" id="rightColHome">
                    <div class="card" style="border-radius: 1rem;">
                        <div class="card-body">
                            <h5 class="card-title">My Bookshelves</h5>
                            <ul class="list-group list-group-flush">
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>