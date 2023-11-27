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
        <script src="http://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js" ></script>
        <title>My Goals</title>
        
        <script>
			$(document).on("click", "#addNewGoalBtn", function() { 
				var title = $("#newGoalTitle").val();
				var target = $("#newGoalTarget").val();
				var type = $("#newGoalType").val();
				alert(title);
				alert(target);
				alert(type);
				
				console.log("Button clicked. Sending AJAX request.");
				$.get("AddGoal?timestamp=" + new Date().getTime(), { newGoalTitle: title, newGoalTarget: target, newGoalType: type }, function(responseText) {  
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
        
        <div class="container-fluid">
            <div class="row m-3">
                <div id="createNewGoal">
                    <div class="card" style="border-radius: 1rem;">
                        <div class="card-body" style="display: inline-flex; justify-content: space-evenly;">
							<h5 class="card-title">Create New Goal</h5>
                            <form name="newGoalForm" method="post" action="AddGoal">
                            	<!--<label class="my-1 mr-2" for="newGoalType" id="goalTypeLabel">Select a Goal Type:</label>-->
								<select class="custom-select" id="newGoalType" name="newGoalType">
								    <option value="1">Monthly Goal</option>
								    <option value="2">Yearly Goal</option>
								 </select>
                                <div class="form-outline" style="display: inline-flex;">
                                    <input type="text" id="newGoalTitle" name="newGoalTitle" class="form-control form-control-lg" placeholder="Goal Name"/>
                                    <!--<label class="form-label" for="newGoalTitle" style="font-size: 1.1rem;">Goal Title:&nbsp;</label>-->
                                </div>
                                <div class="form-outline" style="display: inline-flex;">
                                    <input type="text" id="newGoalTarget" name="newGoalTarget" class="form-control form-control-lg" placeholder="# of Books To Read" />
                                    <!--<label class="form-label" for="newGoalTarget" style="font-size: 1.1rem;"># of Books:&nbsp;</label>-->
                                </div>
                                <button class="btn btn-primary" id="addNewGoalBtn">Add Goal</button>
                            </form>
                        </div>
                      </div>
                </div>
                <div id="updateGoal">
                    <div class="card mt-2 mb-2" style="border-radius: 1rem;">
                        <div class="card-body" style="display: inline-flex; justify-content: space-evenly;">
							<h5 class="card-title">Update A Goal</h5>
                            <form name="updateGoalForm" method="post" action="UpdateGoals">
                                <div class="form-outline" style="display: inline-flex;">
                                    <input type="text" id="updateGoalTitle" name="updateGoalTitle" class="form-control form-control-lg" placeholder="Goal Name"/>
                                </div>
                                <div class="form-outline" style="display: inline-flex;">
                                    <input type="text" id="updateGoalCompleted" name="updateGoalCompleted" class="form-control form-control-lg" placeholder="# of Books Read" />
                                </div>
                                <button class="btn btn-primary" id="updateGoalBtn">Update Goal</button>
                            </form>
                        </div>
                      </div>
                </div>
                <script>
							$(document).ready(function() { 
								$.get("DisplayGoals?timestamp=" + new Date().getTime(), function(responseText) {  
									console.log("Received response from server:", responseText);
									alert("before response text");
									var div = $("#updateGoal");
									div.append(responseText);
								});
								return false;
							});
				</script>
                <!--<div class="col-md-6 p-4" id="monthGoalCol">
                    <div class="card" style="border-radius: 1rem;">
                    	
                        <div class="card-header" style="font-size: larger;">My Monthly Reading Goals</div>
                        <ul class="list-group list-group-flush" id="monthGoalsList"></ul>
                    </div>
                </div>
                <div class="col-md-6 p-4" id="yearGoalCol">
                    <div class="card" style="border-radius: 1rem;">
                        <div class="card-header" style="font-size: larger;">My Yearly Reading Goals</div>
                        <ul class="list-group list-group-flush" id="yearGoalsList"></ul>
                    </div>
                </div>-->
            </div>
        </div>
    </body>
</html>
