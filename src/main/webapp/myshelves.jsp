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
        <title>My Shelves</title>
        
        <script>
			$(document).on("click", "#addNewShelfBtn", function() { 
				var name = $("#newShelfName").val();
				alert(name);
				
				console.log("Button clicked. Sending AJAX request.");
				$.get("AddShelf?timestamp=" + new Date().getTime(), { newShelfName: name }, function(responseText) {  
					console.log("Received response from server:", responseText);
					alert("before response text");
					var ul = $("#shelfList");
					ul.append(responseText);
				});
				return false;
			});
			
			$(document).on("click", ".listTitleShelf", function() { 
				var shelfName = $(this).text();
				alert(shelfName);
				
				console.log("Button clicked. Sending AJAX request.");
				$.get("DisplayBooks?timestamp=" + new Date().getTime(), { listTitleShelf: shelfName }, function(responseText) {  
					console.log("Received response from server:", responseText);
					alert("before response text");
					var ul = $("#bookList");
					ul.html(responseText);
				});
				return false;
			});
		</script>
    </head>
    <body style="background-color: #F2EDE4;">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/js/bootstrap.bundle.min.js" integrity="sha384-pprn3073KE6tl6bjs2QrFaJGz5/SUsLqktiwsUTF55Jfv3qYSDhgCecCxMW52nD2" crossorigin="anonymous"></script>

        <nav class="navbar navbar-expand-lg" style="background-color:#D9C9BA;">
            <div class="container-fluid ">
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
                            <a class="nav-link" href="stats.html">My Stats</a>
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

        <div class="container-fluid">
            <div class="row m-3">
                <div class="col-xl-5 col-md-12" id="leftColShelves">
                    <div class="row mb-2">
                        <div class="card" style="border-radius: 1rem;">
                            <div class="card-body p-3 text-black">
                                <h5 class="card-title">Create A New Bookshelf</h5>
                                <form name="newShelfForm" method="post" action="AddShelf">
                                    <div class="form-outline mb-4 col-auto">
                                        <label class="form-label" for="newShelfName" style="font-size: 1.1rem;">Shelf Name:</label>
                                        <input type="text" id="newShelfName" class="form-control w-75" name="newShelfName"/>
                                    </div>
                  
                                    <div class="pt-1 mt-4">
                                      <button class="btn btn-md btn-block" id="addNewShelfBtn">Create Shelf</button>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                    <script>
							$(document).ready(function() { 
								$.get("DisplayShelves?timestamp=" + new Date().getTime(), function(responseText) {  
									console.log("Received response from server:", responseText);
									alert("before response text");
									var ul = $("#shelfList");
									ul.html(responseText);
								});
								return false;
							});
				</script>
                    <div class="row mb-2">
                        <div class="card" style="border-radius: 1rem;">
                            <div class="card-body">
                                <h5 class="card-title">My Bookshelves</h5>
                                <ul class="list-group list-group-flush" id="shelfList">
                                </ul>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-xl-7 col-md-12" id="rightColShelves">
                    <div class="card" style="border-radius: 1rem;">
                        <div class="card-body" id="bookList">
                            <h5 class="card-title">Read</h5>
                            <ul class="list-group list-group-flush">
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>