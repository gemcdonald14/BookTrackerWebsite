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
            	<div class="d-flex align-items-center">
            		<a class="navbar-brand" href="homepage.jsp">
                    <img src="./images/ella.jpg" alt="" width="60" height="60" class="d-inline-block center" style="border-radius: 2rem">
                    	Ella's Books
                    <img alt="" src="./images/openbook.png" width="35" height="35" class="d-inline-block center">
                	</a>
            	</div>
                
                <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"></span>
                </button>

                <div class="collapse navbar-collapse" id="navbarSupportedContent" style="justify-content: right;">
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
                </div>
            </div>
        </nav>

		<script>
			$(document).ready(function() { 
				
				
				$.get("DisplayHomeGoals?timestamp=" + new Date().getTime(), function(responseText) {  
					console.log("Received response from server:", responseText);
					var div = $("#homeGoals");
					div.html(responseText);
				});
				$.get("DisplayHomeCurrentRead?timestamp=" + new Date().getTime(), function(responseText) {  
					console.log("Received response from server:", responseText);
					var div = $("#progressRow");
					div.html(responseText);
				});
				$.get("DisplayHomeShelves?timestamp=" + new Date().getTime(), function(responseText) {  
					console.log("Received response from server:", responseText);
					var div = $("#rightColHome");
					div.html(responseText);
				});
				
				function updateShelfPictures() {
					var baseUrl = "http://localhost:8443/BookWebsite/";

					$.ajax({
					    url: baseUrl + "DisplayShelfPic",
					    type: "GET",
					    dataType: "html",
					    success: function (responseText) {
					        var tempElement = $('<div>').html(responseText);

					        tempElement.find('.listImgHome img').each(function () {
					            var shelfId = $(this).attr('name');
					            var shelfPicData = $(this).attr('src');

					            $("img[name='" + shelfId + "']").attr('src', baseUrl + shelfPicData);
					        });
					    },
					    error: function (error) {
					        console.error("Error:", error);
					    }
					});

			    }

			    updateShelfPictures();
				return false;
			});
		
			$(document).on("click", "#updateCurrentBookBtn", function() { 
				var numPages = $("#updateCurrentRead").val();
				
				console.log("Button clicked. Sending AJAX request.");
				$.get("UpdateCurrentRead?timestamp=" + new Date().getTime(), { updateCurrentRead:numPages }, function(responseText) {  
					console.log("Received response from server:", responseText);
					
					if (responseText != "True") {
						var errorDiv = $("#updateBookError");
						errorDiv.html(responseText);
					}
					
				});
			});
			
			$(document).on("click", "#finishBookBtn", function() { 
				$.get("FinishBook?timestamp=" + new Date().getTime(), function(responseText) {  
					console.log("Received response from server:", responseText);
					var form = $("#updateCurrentBook");
					form.append(responseText);
				});
				return false;
			});
			
			$(document).on("click", "#rateBook", function() { 
				var rating = $("#bookRating").val();
				var genre = $("#bookGenre").val();
				var mood = $("#bookMood").val();
				var pace = $("input[name='bookPace']:checked").val();
				
				$.get("RateBook?timestamp=" + new Date().getTime(), { bookRating:rating, bookGenre:genre, bookMood:mood, bookPace:pace }, function(responseText) {  
					console.log("Received response from server:", responseText);
					var form = $("#progressRow");
					form.html(responseText);
					
				});
				return false;
			});
		</script>

        <div class="container-fluid">
            <div class="row m-3" id="homepageRow">
                <div class="col-md-6 p-4" id="leftColHome">
                	<div id="homeGoals">
	                    <div class="row mb-4" id="goalMRow">
	                        <div class="card mt-2 mb-2" style="border-radius: 1rem;">
	                            <div class="card-body" style="justify-content: space-evenly;">
	                              <h5 class="card-title"></h5>
	                              <p class="card-text"></p>
	                            </div>
	                          </div>
	                    </div>
	                    <div class="row mb-4" id="goalYRow">
	                        <div class="card mt-2 mb-2" style="border-radius: 1rem;">
	                            <div class="card-body" style="justify-content: space-evenly;">
	                              <h5 class="card-title"></h5>
	                              <p class="card-text"></p>
	                            </div>
	                        </div>
	                    </div>
	                </div>
                    <div class="row g-0" id="progressRow">
                        <div class="card" style="border-radius: 1rem;">
                            <div class="row g-0">
                              <div class="col-md-4">
                                <img src="dark-bookshelf.jpg" class="img-fluid" style="border-radius: 1rem;">
                              </div>
                              <div class="col-md-8">
                                <div class="card-body">
                                  <h5 class="card-title">Currently Reading</h5>
                                  <p class="card-text">Title</p>
                                  <p class="card-text">Author</p>
                                  <div class='progress' style='height: 1.2rem;'>
                              		  <div class='progress-bar' role='progressbar' style='width:0%' aria-valuenow='0'></div>
                              	  </div>
                                  <form name="updateCurrentBook" id="updateCurrentBook">
	                                  <div class="form-outline" style="display: inline-flex;">
	                                   	<input type="text" id="updateCurrentRead" name="updateCurrentRead" class="form-control form-control-sm" placeholder="I'm on page..."/>
	                                  </div>
	                                  <button class="btn btn-primary" id="updateCurrentBookBtn">Update Progress</button>
	                                  <button class="btn btn-primary" id="finishBookBtn">Finished Book!</button>
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
