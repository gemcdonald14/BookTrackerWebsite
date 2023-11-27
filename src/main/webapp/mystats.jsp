<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="ISO-8859-1">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-0evHe/X+R7YkIZDRvuzKMRqM+OrBnVFBL6DOitfPri4tjfHxaWutUpFmBp4vmVor" crossorigin="anonymous">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.4.1/dist/js/bootstrap.min.js" integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/4.4.0/chart.min.js" integrity="sha512-7U4rRB8aGAHGVad3u2jiC7GA5/1YhQcQjxKeaVms/bT66i3LVBMRcBI9KwABNWnxOSwulkuSXxZLGuyfvo7V1A==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>
        <script src="http://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js" ></script>
        <link href="mystyles.css" rel="stylesheet">
        <script src="javascript.js"></script>
        <title>Stats</title>
    </head>
    <style>
        .chart-container {
          width: 50%;
          height: 50%;
          margin: auto;
        }
      </style>
    <body style="background-color: #F2EDE4;">

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
        
        <script>
	        $(document).ready(function() { 
				$.get("DisplayReadingStats?timestamp=" + new Date().getTime(), function(responseText) {  
					console.log("Received response from server:", responseText);
					alert("before response text");
					var div = $("#readingStats");
					div.html(responseText);
				});
				return false;
			});
        </script>
        
        <div class="container-fluid">
            <div class="row m-3 d-flex justify-content-center">
                <div class="col-xl-6 col-md-12 p-4" id="leftStatCol">
                	<div class="row mb-2">
                        <div class="card" style="border-radius: 1rem;">
                            <div class="card-body p-3 text-black">
                                <h5 class="card-title">My Reading Stats</h5>
                                <div id="readingStats"></div>
                            </div>
                        </div>
                    </div>
                    <div class="row mb-2">
                        <div class="card" style="border-radius: 1rem;">
                            <div class="card-body p-3 text-black">
                                <h5 class="card-title">Average Rating</h5>
                                
                            </div>
                        </div>
                    </div>
                    <div class="row mb-2">
                        <div class="card" style="border-radius: 1rem;">
                            <div class="card-body p-3 text-black">
                                <h5 class="card-title">Genre</h5>
                                
                            </div>
                        </div>
                    </div>
                    
                </div>
                
                <div class="col-xl-6 col-md-12 p-4" id="rightStatCol">
                	<div class="row mb-2">
                        <div class="card" style="border-radius: 1rem;">
                            <div class="card-body p-3 text-black">
                                <h5 class="card-title">Page Count</h5>
                                
                            </div>
                        </div>
                    </div>
                    <div class="row mb-2">
                        <div class="card" style="border-radius: 1rem;">
                            <div class="card-body p-3 text-black">
                                <h5 class="card-title">Mood</h5>
                                
                            </div>
                        </div>
                    </div>
                    <div class="row mb-2">
                        <div class="card" style="border-radius: 1rem;">
                            <div class="card-body p-3 text-black">
                                <h5 class="card-title">Pace</h5>
                                
                            </div>
                        </div>
                    </div>
                    
                </div>
            </div>
        </div>
        
        














        <div class="container-fluid">
            <div class="row m-3 d-flex justify-content-center">
                <div class="col-md-8 p-4" id="statCol">
                    <canvas id="lineChart"></canvas>
                    <div class="card" style="border-radius: 1rem;">
                        <div class="card-body">
                            <h5 class="card-title" style="text-align: center;">My Reading Statistics</h5>
                            <ul class="list-group list-group-flush">
                                <li class="list-group-item">
                                    <div class="listItem" id="readBooksData">
                                        <p>You have read 99 books and 1000 pages</p> 
                                    </div>
                                </li>
                                <li class="list-group-item">
                                    <div class="listItem" id="moodData">
                                        <canvas id="moodChart" aria-label="chart" height="350" width="580"></canvas>
                                        <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
                                        <script>
                                            var chrt = document.getElementById("moodChart").getContext("2d");
                                            var chartId = new Chart(chrt, {
                                                type: 'pie',
                                                data: {
                                                    labels: ["mysterious", "tense", "dark", "adventurous", "hopeful", "emotional"],
                                                    datasets: [{
                                                    data: [20, 40, 13, 35, 20, 38],
                                                    backgroundColor: ['#744845', '#A29670', '#DAB48F', '#4D4728', '#8C5835', '#736C49'],
                                                    hoverOffset: 5 
                                                    }],
                                                },
                                                options: {
                                                    responsive: false,
                                                },
                                            });
                                        </script>
                                    </div>
                                </li>
                                <li class="list-group-item">
                                    <div class="listItem" id="paceData">
                                        <canvas id="paceChart" aria-label="chart" height="350" width="580"></canvas>
                                        <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
                                        <script>
                                            var chrt = document.getElementById("paceChart").getContext("2d");
                                            var chartId = new Chart(chrt, {
                                                type: 'pie',
                                                data: {
                                                    labels: ["slow", "medium", "fast"],
                                                    datasets: [{
                                                    data: [20, 40, 40],
                                                    backgroundColor: ['#744845', '#A29670', '#DAB48F'],
                                                    hoverOffset: 5
                                                    }],
                                                },
                                                options: {
                                                    responsive: false,
                                                },
                                            });
                                        </script>
                                    </div>
                                </li>
                                <li class="list-group-item">
                                    <div class="listItem" id="pageNumData">
                                        <canvas id="pageNumChart" aria-label="chart" height="350" width="580"></canvas>
                                        <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
                                        <script>
                                            var chrt = document.getElementById("pageNumChart").getContext("2d");
                                            var chartId = new Chart(chrt, {
                                                type: 'pie',
                                                data: {
                                                    labels: ["1-299", "300-499", "500+"],
                                                    datasets: [{
                                                    data: [20, 40, 40],
                                                    backgroundColor: ['#744845', '#A29670', '#DAB48F'],
                                                    hoverOffset: 5
                                                    }],
                                                },
                                                options: {
                                                    responsive: false,
                                                },
                                            });
                                        </script>
                                    </div>
                                </li>
                                <li class="list-group-item">
                                    <div class="listItem" id="genreData">
                                        <canvas id="genreChart" aria-label="chart" height="350" width="580"></canvas>
                                        <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
                                        <script>
                                            var chrt = document.getElementById("genreChart").getContext("2d");
                                            var chartId = new Chart(chrt, {
                                                type: 'pie',
                                                data: {
                                                    labels: ["fantasy", "young adult", "mystery", "thriller", "romance", "horror"],
                                                    datasets: [{
                                                    data: [20, 40, 13, 35, 20, 38],
                                                    backgroundColor: ['#744845', '#A29670', '#DAB48F', '#4D4728', '#8C5835', '#736C49'],
                                                    hoverOffset: 5
                                                    }],
                                                },
                                                options: {
                                                    responsive: false,
                                                },
                                            });
                                        </script>
                                    </div>
                                </li>
                                <li class="list-group-item">
                                    <div class="listItem" id="ratingData">
                                        <canvas id="ratingChart" aria-label="chart" height="350" width="580"></canvas>
                                        <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
                                        <script>
                                            var chrt = document.getElementById("ratingChart").getContext("2d");
                                            var chartId = new Chart(chrt, {
                                               type: 'bar',
                                               data: {
                                                  labels: ["1 star", "2 stars", "3 stars", "4 stars", "5 stars"],
                                                  datasets: [{
                                                  data: [20, 40, 30, 35, 30],
                                                  backgroundColor: ['#744845', '#A29670', '#DAB48F', '#4D4728', '#8C5835']
                                                  }],
                                               },
                                               options: {
                                                  responsive: false,
                                               },
                                            });
                                         </script>
                                    </div>
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>