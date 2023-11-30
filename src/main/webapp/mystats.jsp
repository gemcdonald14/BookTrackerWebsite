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
        <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
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
	        //display charts and graphs
	        $(document).ready(function() { 
	        	//display my reading stats 
	        	$.get("DisplayReadingStats?timestamp=" + new Date().getTime(), function(responseText) {  
					console.log("Received response from server:", responseText);
					alert("before response text");
					var div = $("#readingStats");
					div.html(responseText);
				});
	        	
	        	
	        	//display page count chart 
	        	$.get("GetPageCountStats?timestamp=" + new Date().getTime(), function(responseText) {  
					console.log("Received response from server:", responseText);
					
					var text = responseText;
			        var myArray = text.split("/");
			        var smallPage = myArray[0];
			        var medPage = myArray[1];
			        var largePage = myArray[2];
			        
		        	var chart = document.getElementById("pageCountChart").getContext("2d");
		            var chartId = new Chart(chart, {
		                type: 'pie',
		                data: {
		                    labels: ["1-299", "300-499", "500+"],
		                    datasets: [{
		                    data: [smallPage, medPage, largePage],
		                    backgroundColor: ['#C3C7AF', '#453C43', '#B3746F'],
		                    hoverOffset: 5
		                    }],
		                },
		                options: {
		                    responsive: false,
		                    
		                    plugins: {
		                        legend: {
		                          position: "right",
		                          align: "middle"
		                      }
		                    }
		                },
		            });
				});
				
				//display average rating chart
				$.get("GetAvgRatingStats?timestamp=" + new Date().getTime(), function(responseText) {  
					console.log("Received response from server:", responseText);
					
					var text = responseText;
			        var myArray = text.split("/");
			        var one = myArray[0];
			        var two = myArray[1];
			        var three = myArray[2];
			        var four = myArray[3];
			        var five = myArray[4];
			        
		        	var chart = document.getElementById("avgRatingChart").getContext("2d");
		            var chartId = new Chart(chart, {
		                type: 'bar',
		                data: {
		                    labels: ["1 star", "2 stars", "3 stars", "4 stars", "5 stars"],
		                    datasets: [{
		                    data: [one, two, three, four, five],
		                    backgroundColor: ['#C3C7AF', '#453C43', '#B3746F', '#8B8AA2', '#F7E89C']
		                    }],
		                },
		                options: {
		                    responsive: false,
		                },
		            });
				});
				
				//display top genres chart
				$.get("GetTopGenreStats?timestamp=" + new Date().getTime(), function(responseText) {  
					console.log("Received response from server:", responseText);
					
					var text = responseText;
			        var myArray = text.split("/");
			        var fiction = myArray[0];
			        var nonFiction = myArray[1];
			        var youngAdult = myArray[2];
			        var children = myArray[3];
			        var romance = myArray[4];
			        var fantasy = myArray[5];
			        var mystery = myArray[6];
			        var thriller = myArray[7];
			        var historical = myArray[8];
			        var informational = myArray[9];
			        
		        	var chart = document.getElementById("topGenresChart").getContext("2d");
		            var chartId = new Chart(chart, {
		                type: 'pie',
		                data: {
		                	labels: ["Fiction", "Non-Fiction", "Young Adult", "Children's Lit", "Romance", "Fantasy", "Mystery", "Thriller", "Historical", "Informational"],
		                    datasets: [{
		                    	//#453C43    D99E8F   #507858   587850
		                    data: [fiction, nonFiction, youngAdult, children, romance, fantasy, mystery, thriller, historical, informational],
		                    backgroundColor: ['#507858', '#C3C7AF', '#453C43', '#78506E', '#AFBAC7', '#4F312F', '#C8B1AF', '#1E3147', '#F7E89C', '#8B8AA2'],
		                    hoverOffset: 5
		                    }],
		                },
		                options: {
		                    responsive: false,
		                    
		                    plugins: {
		                        legend: {
		                          position: "right",
		                          align: "middle"
		                      }
		                    }
		                },
		            });
				});
				
				//display top moods chart
				$.get("GetTopMoodStats?timestamp=" + new Date().getTime(), function(responseText) {  
					console.log("Received response from server:", responseText);
					
					var text = responseText;
			        var myArray = text.split("/");
			        var adventurous = myArray[0];
			        var tense = myArray[1];
			        var dark = myArray[2];
			        var funny = myArray[3];
			        var emotional = myArray[4];
			        var lightHearted = myArray[5];
			        var mysterious = myArray[6];
			        var challenging = myArray[7];
			        var reflective = myArray[8];
			        var informative = myArray[9];
			        var inspiring = myArray[10];
			        var hopeful = myArray[11];
			        var relaxing = myArray[12];
			        var sad = myArray[13];
			        
		        	var chart = document.getElementById("topMoodsChart").getContext("2d");
		            var chartId = new Chart(chart, {
		                type: 'pie',
		                data: {
		                	labels: ["Adventurous", "Tense", "Dark", "Funny", "Emotional", "Light-Hearted", "Mysterious", "Challenging", "Reflective", "Informative", "Inspiring", "Hopeful", "Relaxing", "Sad"],
		                    datasets: [{
		                    data: [adventurous, tense, dark, funny, emotional, lightHearted, mysterious, challenging, reflective, informative, inspiring, hopeful, relaxing, sad],
		                    backgroundColor: ['#507858', '#C3C7AF', '#453C43', '#78506E', '#AFBAC7', '#4F312F', '#C8B1AF', '#1E3147', '#F7E89C', '#8B8AA2', '#B3746F', '#F5E2D7', '#471E3D', '#4C627A'],
		                    hoverOffset: 5
		                    }],
		                },
		                options: {
		                    responsive: false,
		                    
		                    plugins: {
		                        legend: {
		                          position: "right",
		                          align: "middle"
		                      }
		                    }
		                },
		            });
				});
				
				//display pace chart
				$.get("GetPaceStats?timestamp=" + new Date().getTime(), function(responseText) {  
					console.log("Received response from server:", responseText);
					
					var text = responseText;
			        var myArray = text.split("/");
			        var slow = myArray[0];
			        var medium = myArray[1];
			        var fast = myArray[2];
			        
		        	var chart = document.getElementById("paceChart").getContext("2d");
		            var chartId = new Chart(chart, {
		                type: 'pie',
		                data: {
		                	labels: ["Slow", "Medium", "Fast"],
		                    datasets: [{
		                    data: [slow, medium, fast],
		                    backgroundColor: ['#C3C7AF', '#453C43', '#B3746F'],
		                    hoverOffset: 5
		                    }],
		                },
		                options: {
		                    responsive: false,
		                    
		                    plugins: {
		                        legend: {
		                          position: "right",
		                          align: "middle"
		                      }
		                    }
		                },
		            });
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
                                <h5 class="card-title">Genre</h5>
                                <div id="topGenresStats">
                                	<canvas id="topGenresChart" aria-label="chart"></canvas>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row mb-2">
                        <div class="card" style="border-radius: 1rem;">
                            <div class="card-body p-3 text-black">
                               <h5 class="card-title">Page Count</h5>
                                <div id="pageCountStats">
                                	<canvas id="pageCountChart" aria-label="chart"></canvas>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                
                <div class="col-xl-6 col-md-12 p-4" id="rightStatCol">
                	<div class="row mb-2">
                        <div class="card" style="border-radius: 1rem;">
                            <div class="card-body p-3 text-black">
                                <div class="card-body p-3 text-black">
                                <h5 class="card-title">Average Rating</h5>
                                <div id="avgRatingStats">
                                	<canvas id="avgRatingChart" aria-label="chart"></canvas>
                                </div>
                            </div>
                            </div>
                        </div>
                    </div>
                    <div class="row mb-2">
                        <div class="card" style="border-radius: 1rem;">
                            <div class="card-body p-3 text-black">
                                <h5 class="card-title">Mood</h5>
                                <div id="topMoodsStats">
                                	<canvas id="topMoodsChart" aria-label="chart"></canvas>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row mb-2">
                        <div class="card" style="border-radius: 1rem;">
                            <div class="card-body p-3 text-black">
                                <h5 class="card-title">Pace</h5>
                                <div id="paceStats">
                                	<canvas id="paceChart" aria-label="chart"></canvas>
                                </div>
                            </div>
                        </div>
                    </div>
                    
                </div>
            </div>
        </div>
        
        












<!-- 

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
            </div> -->
        </div>
    </body>
</html>