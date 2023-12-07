<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="ISO-8859-1">
		<title>Search Books</title>
		<script defer type="text/javascript" src="https://www.google.com/books/jsapi.js"></script>
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-0evHe/X+R7YkIZDRvuzKMRqM+OrBnVFBL6DOitfPri4tjfHxaWutUpFmBp4vmVor" crossorigin="anonymous">
        <link href="mystyles.css" rel="stylesheet">
        <script src="http://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js" ></script>
		<script type="text/javascript">
		/*
		$(document).on("click", "#searchBtn", function() { 
	   		 $.get("DisplayShelvesSearch?timestamp=" + new Date().getTime(), function(responseText) {  
					console.log("Received response from server:", responseText);
					var select = $(".custom-select");
					console.log("Selected element:", select);
					select.html(responseText);
				});
	   		 return false;
		 });*/
		
		
		
		function uploadFile(inputElement) {
			  var file = inputElement.files[0];
			  var reader = new FileReader();
			  reader.onloadend = function() {
			    console.log('Encoded Base 64 File String:', reader.result);
			    
			    /******************* for Binary ***********************/
			    var data=(reader.result).split(',')[1];
			     var binaryBlob = atob(data);
			     console.log('Encoded Binary File String:', binaryBlob);
			  }
			  reader.readAsDataURL(file);
		}
		
		/**
	     * This function is the call-back function for the JSON scripts which
	     * executes a Google book search response.
	     *
	     * @param {JSON} booksInfo is the JSON object pulled from the Google books service.
	     */
	     function listEntries(booksInfo) {
	    	 
	    	// Clear any old data to prepare to display the Loading... message.
	    	 var div = document.getElementById("data");
	    	 if (div.firstChild) {
	    		 div.removeChild(div.firstChild);
	    	 }
	    	 
	    	 //create a new div element 
	    	 var mainDiv = document.createElement("div");
	    	 
	    	 for (var i = 0; i < booksInfo.items.length; i++) {
	    		 
	    		 if (i % 10 === 0) {
	    			 var row = document.createElement("div");
	    			 row.className = "row";
	    			 mainDiv.appendChild(row);
	    		 }
	    
	    		 //create a div for each book in the returned list 
	    		 var book = booksInfo.items[i];
	    		 var thumbnailDiv = document.createElement("div");
	    		 thumbnailDiv.className = "thumbnail col-md-5";
	    		 thumbnailDiv.id = book.id;
		    	
		    	if (book.volumeInfo && book.volumeInfo.imageLinks && book.volumeInfo.imageLinks.thumbnail) {
		    		var img = document.createElement("img");
		    		img.className = "bookImg";
			    	img.src = book.volumeInfo.imageLinks.thumbnail;
			    	thumbnailDiv.appendChild(img);
		        }
	    	
	    		 var p1 = document.createElement("p");
	    		 p1.innerHTML = book.volumeInfo && book.volumeInfo.title ? book.volumeInfo.title : "Unknown Title";
	    		 p1.className = "title";
	    		 thumbnailDiv.appendChild(p1);
	    		 
	    		 var p2 = document.createElement("p");
	    		 p2.innerHTML = book.volumeInfo && book.volumeInfo.authors ? book.volumeInfo.authors.join(", ") : "Unknown Author";
	    		 p2.className = "author";
	    		 thumbnailDiv.appendChild(p2);
	    	
	    		 var p3 = document.createElement("p");
	    		 p3.className = "pageCount";
	    		 if (book.volumeInfo && book.volumeInfo.pageCount) {
	    		     p3.innerHTML = book.volumeInfo.pageCount + " pages";
	    		 } else {
	    		     p3.innerHTML = "Unknown Page Count";
	    		 }

	    		 thumbnailDiv.appendChild(p3);
	    		 /*
	    		 var form = document.createElement("form");
	    		 form.setAttribute("name", "addToShelfForm");
	    		 var shelfName = document.createElement("input");
	    		 shelfName.setAttribute("type", "text");
	    		 shelfName.setAttribute("class", "shelf form-control form-control-md");
	    		 shelfName.setAttribute("placeholder", "Enter shelf name...");
	    		 shelfName.style.width = "60%";
	    		 shelfName.style.margin = "auto";
	    		 form.appendChild(shelfName);
	    		 */
	    		 //------
	    		 var form = document.createElement("form");
	    		 form.setAttribute("name", "addToShelfForm");
	    		 var shelfName = document.createElement("select");
				 shelfName.className = "custom-select";
	    		 shelfName.setAttribute("name", "addToShelfSelect");
	    		 shelfName.style.width = "60%";
	    		 shelfName.style.margin = "auto";
	    		 
	    		 form.appendChild(shelfName);
	    		 form.appendChild(document.createElement("br"));
	    		 //------
	    		 
	    		 var addbutton = document.createElement("button");
	    		 addbutton.className = "addToShelfBtn";
	    		 addbutton.innerHTML = "Add To Shelf";
	    		 addbutton.style.height = "50px";
	    		 addbutton.style.width = "100px";
	    		 form.appendChild(addbutton);
	    		 
	    		 thumbnailDiv.appendChild(form);
	    		 
	    		 row.appendChild(thumbnailDiv);
	    	 }
	    	 div.appendChild(mainDiv);
	    	 
	    	 
	    	 $.get("DisplayShelvesSearch?timestamp=" + new Date().getTime(), function(responseText) {  
					console.log("Received response from server:", responseText);
					var select = $(".custom-select");
					console.log("Selected element:", select);
					select.html(responseText);
				});
	     }
	     
	     
	     
	     /**
	     *
	     * @param {DOM object} query The form element containing the
	     *                     input parameters "isbns"
	     */
	     
	     function search(query) {
	         // Clear any old data to prepare to display the Loading... message.
	         var div = document.getElementById("data");
	         if (div.firstChild) div.removeChild(div.firstChild);

	         // Show a "Loading..." indicator.
	         var div = document.getElementById('data');
	         var p = document.createElement('p');
	         p.appendChild(document.createTextNode('Loading...'));
	         div.appendChild(p);

	         // Delete any previous Google Booksearch JSON queries.
	         var jsonScript = document.getElementById("jsonScript");
	         if (jsonScript) {
	           jsonScript.parentNode.removeChild(jsonScript);
	         }

	         // Add a script element with the src as the user's Google Booksearch query.
	         // JSON output is specified by including the alt=json-in-script argument
	         // and the callback funtion is also specified as a URI argument.
	         var scriptElement = document.createElement("script");
	         scriptElement.setAttribute("id", "jsonScript");
	         
	         scriptElement.setAttribute("src","https://www.googleapis.com/books/v1/volumes?q=intitle:" 
	        		 + escape(query.searchInput.value) +"&orderBy:relevance&key=AIzaSyBTRflMnF-kNth_pkLhhADRQ7jWPcptTCY&jscmd=viewapi&callback=listEntries");
	        
	         scriptElement.setAttribute("type", "text/javascript");
	         // make the request to Google booksearch
	         document.documentElement.firstChild.appendChild(scriptElement);
	       }
	     
	     //-------------------------------------------------------------------------------
	     // add a book to a shelf 
	     
	     $(document).on("click", ".addToShelfBtn", function() {
		    var parent = $(this).closest('.thumbnail');
		    var title = parent.find(".title").text().trim();
		    var author = parent.find(".author").text().trim();
		    var shelf = parent.find(".custom-select option:selected").html();

		   
		    var numPagesElement = parent.find(".pageCount");
		    var numPages = numPagesElement ? parseInt(numPagesElement.text().replace(/\D/g, ''), 10) : null;
		
		    console.log("Title: " + title);
		    console.log("Author: " + author);
		    console.log("Number of Pages: " + numPages);
		    console.log("Shelf name: " + shelf);
		
		    console.log("Button clicked. Sending AJAX request.");
		    $.get("AddBook?timestamp=" + new Date().getTime(), { bookTitle: title, bookAuthor: author, bookNumPages: numPages, bookShelf: shelf }, function(responseText) {  
		        console.log("Received response from server:", responseText);
		    });
		    return false;
		});
	     
	     function getImageData(parent) {
	    	 var image = parent.find(".bookImg")[0];
	    	 var canvas = document.createElement("canvas");
	    	 canvas.width = image.width;
	    	 canvas.height = image.height;
	    	 var ctx = canvas.getContext("2d");
	    	 ctx.drawImage(image, 0, 0, canvas.width, canvas.height);
	    	 
	    	 try {
	    	        ctx.drawImage(image, 0, 0, canvas.width, canvas.height);

	    	        // Get the Base64 data
	    	        var base64Image = canvas.toDataURL("image/png");

	    	        // Remove the data:image/png;base64, part
	    	        var base64ImageData = base64Image.replace(/^data:image\/(png|jpg|jpeg);base64,/, "");

	    	        return base64ImageData;
	    	    } catch (error) {
	    	        console.error("Error in getImageData:", error);
	    	        return null; // Return null or handle the error appropriately
	    	    }
	     }

	     /*
	     $(document).on("click", ".addToShelfBtn", function() {
	    	 
	    	var parent = this.parentElement; // get the parent element
		    if (parent) {
		        var parentId = parent.id; // get the parent element id
		        
		        var title = parent.querySelector(".title").innerHTML;
		        var author = parent.querySelector(".author").innerHTML;
		        var shelfInput = parent.querySelector("input.shelf");
		        var shelf = shelfInput ? shelfInput.value : null;
		        
		        var numPagesElement = parent.querySelector(".pageCount");
		        var numPages = numPagesElement ? parseInt(numPagesElement.innerHTML.replace(/\D/g, ''), 10) : null;
		        
		        console.log("Title: " + title);
		        console.log("Author: " + author);
		        console.log("Number of Pages: " + numPages);
		        console.log("Shelf name: " + shelf);
		        
		        console.log("Button clicked. Sending AJAX request.");
				$.get("AddBook?timestamp=" + new Date().getTime(), { bookTitle: title, bookAuthor: author, bookNumPages: numPages, bookShelf: shelf }, function(responseText) {  
					console.log("Received response from server:", responseText);
				});
				return false;
		    } else {
		        console.error("Parent element is undefined");
		    }
		});*/
		</script>
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
		
		<div class="container-fluid">
			<div class="row m-3" style="justify-content:center;">
				<div id="searchRow" class="col-xl-10 col-md-10">
					<div class="card mt-2 mb-2" style="border-radius: 1rem;">
	                        <div class="card-body" style="display: inline-flex; justify-content: space-evenly;">
								<h5 class="card-title">Search For Books</h5>
	                            <form name="searchBooks" onSubmit="return false">
	                                <div class="form-outline" style="display: inline-flex;">
	                                    <input type="text" id="searchInput" name="searchInput" class="form-control form-control-xl" placeholder="Search for books by title..."/>
	                                </div>
	                                <button class="btn btn-primary" id="searchBtn" onClick="search(this.form)">Search Books</button>
	                            </form>
	                        </div>
					</div>
				</div>
			</div>
			
			<div class="row m-3" style="justify-content:center;">
				<div id="resultsCol" class="col-xl-10 col-md-10">
					<div id="data"></div>
				</div>
			</div>
		</div>
	</body>
</html>