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
				//alert(name);
				
				console.log("Button clicked. Sending AJAX request.");
				$.get("AddShelf?timestamp=" + new Date().getTime(), { newShelfName: name }, function(responseText) {  
					console.log("Received response from server:", responseText);
					//alert("before response text");
					var ul = $("#shelfList");
					ul.append(responseText);
				});
				return false;
			});
			
			$(document).on("click", ".listTitleShelf", function() { 
				var shelfName = $(this).text();
				//alert(shelfName);
				
				console.log("Button clicked. Sending AJAX request.");
				$.get("DisplayBooks?timestamp=" + new Date().getTime(), { listTitleShelf: shelfName }, function(responseText) {  
					console.log("Received response from server:", responseText);
					//alert("before response text");
					var ul = $("#bookList");
					ul.html(responseText);
				});
				return false;
			});
			
			$(document).on("click", ".setAsCurrentBook", function() {
			    var parent = $(this).closest('.list-group-item');
			    var parentId = parent.attr('id');
			
			    console.log("Book ID: " + parentId);
			
			    console.log("Button clicked. Sending AJAX request.");
			    $.get("SetCurrentRead?timestamp=" + new Date().getTime(), { bookId: parentId }, function(responseText) {  
			        console.log("Received response from server:", responseText);
			        $(".error").html(responseText);
			    });
			    return false;
			});
			
			$(document).on("click", ".listImgShelf", function() { 
				var parent = $(this).closest('.list-group-item');
			    var parentId = parent.attr('id');
			    var fileUpload = "<br><div class=\"listAddPicShelf\">Change Shelf Pic&nbsp;"
								+ "<form id=\"addShelfPicForm\">"
								+ "<input class=\"addShelfPic form-control form-control-sm\" type=\"file\" accept=\"image/png\">"
								+ "<button class=\"btn addShelfPicBtn\">Update Picture</button>"
								+ "</form></div>";
			    
				parent.append(fileUpload);
			});
			
			function uploadFile(inputElement, callback) {
			    // Ensure inputElement is a jQuery object
			    var input = $(inputElement);

			    // Access the underlying DOM element using [0]
			    var file = input[0].files ? input[0].files[0] : null;

			    // Check if files array is not empty
			    if (file) {
			        var reader = new FileReader();

			        reader.onloadend = function () {
			            console.log('Encoded Base64 File String:', reader.result);
			            var data = (reader.result).split(',')[1]; // Extract only the Base64-encoded part
			            callback(data);
			        };

			        reader.readAsDataURL(file);
			    } else {
			        console.error('No file selected');
			    }
			}




			
			$(document).on("click", ".addShelfPicBtn", function(event) {
				event.preventDefault(); 
				var parent = $(this).closest('.list-group-item');
			    var parentId = parent.attr('id');
			
			    console.log("Shelf ID: " + parentId);
			    
			    
        		var inputElement = parent.find('.addShelfPic');

        		
                uploadFile(inputElement, function (pic) {
                    console.log("Button clicked. Sending AJAX request.");
                    console.log("Profile Picture Data:", pic);
                    $.ajax({
                        url: "AddShelfPicture",
                        type: "POST",
                        contentType: "application/x-www-form-urlencoded; charset=UTF-8", // Set content type
                        data: { shelfPicture: pic, shelfId: parentId },
                        success: function (responseText) {
                            console.log("Received response from server:", responseText);

                            // Extract profile picture data from the response
                            var shelfPicData = $(responseText).find(".shelfPic").attr("src");
                            
                            // Extract shelf ID from the response
						    //var shelfId = $(responseText).find(".shelfPic").attr("id");
						
						    // Log the shelf ID to verify
						    //console.log("Shelf ID from response:", shelfId);
                            
                            // Update the profile picture on the page
                            parent.find(".shelfPic").attr("src", shelfPicData);
                            //$("id='" + shelfId + "Img'").attr("src", responseText);

                            // You may also update other user information on the page if needed

                            // Handle the response as needed
                        },
                        error: function (error) {
                            console.error("Error uploading profile picture:", error);
                        }
                    });
		
			        
			    });
                return false;
			});
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
            <div class="row m-3">
                <div class="col-xl-5 col-md-12" id="leftColShelves">
                    <div class="row mb-2">
                        <div class="card" style="border-radius: 1rem;">
                            <div class="card-body p-3 text-black">
                                <h5 class="card-title">Create A New Bookshelf</h5>
                                <form name="newShelfForm" method="post" action="AddShelf">
                                    <div class="form-outline mb-4 col-auto">
                                        <!--<label class="form-label" for="newShelfName" style="font-size: 1.1rem;">Shelf Name:</label>-->
                                        <input type="text" id="newShelfName" class="form-control w-75" name="newShelfName" placeholder="Shelf Name"/>
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
									//alert("before response text");
									var ul = $("#shelfList");
									ul.html(responseText);
								});
								
								
								var shelfName = "Read";
								$.get("DisplayBooks?timestamp=" + new Date().getTime(), { listTitleShelf: shelfName }, function(responseText) {  
									console.log("Received response from server:", responseText);
									//alert("before response text");
									var ul = $("#bookList");
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
                <div class="col-xl-6 col-md-12 col-sm-12 justify-content-end" id="rightColShelves">
                    <div class="card" style="border-radius: 1rem;">
                        <div class="card-body" id="bookList"></div>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>