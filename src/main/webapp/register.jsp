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
        <script src="javascript.js"></script>
        <title>Create Account</title>
        
        <script>
			$(document).on("click", "#registerBtn", function(event) { 
				event.preventDefault();
				$("#matchPassMessage").val("");
				$("#errorRegister").val("");
				var username = $("#newUsername").val();
				var email = $("#newEmail").val();
				var password = $("#newPassword").val();
				var resetpassword = $("#retypePassword").val();
				var question = $("#securityQuestion").val();
				var answer = $("#securityAnswer").val();
				var bio = $("#newBio").val();
				var author = $("#newFavAuthor").val();
				var book = $("#newFavBook").val();
				
				
				
				if (validateRegistration()) {
					if (password != resetpassword) {
						$("#matchPassMessage").html("*** Passwords do not match ***");
						$(".col-lg-4").css("height", "100%");
					} else {
						console.log("Button clicked. Sending AJAX request.");
						$.get("CreateUser?timestamp=" + new Date().getTime(), { newUsername: username, newEmail: email, newPassword: password, 
										securityQuestion: question, securityAnswer: answer, newBio: bio, newFavAuthor: author, newFavBook: book }, function(responseText) {  
							console.log("Received response from server:", responseText);
							
							if (responseText == "Error inserting user") {
								var error = $("#errorRegister");
								error.html(responseText);
							} else if (responseText == "Username already taken"){
								var error = $("#errorRegister");
								error.html(responseText);
							} else {
								window.location.href = "login.jsp";
							}
							
						});
						return false;
					}
				} else {
					$(".col-lg-4").css("height", "100%");
					var error = $("#errorRegister");
					error.html("*** Please provide the required information ***");
					
				}
			});
		</script>
    </head>
    <body style="background-color: #A68F7B;">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/js/bootstrap.bundle.min.js" integrity="sha384-pprn3073KE6tl6bjs2QrFaJGz5/SUsLqktiwsUTF55Jfv3qYSDhgCecCxMW52nD2" crossorigin="anonymous"></script>
        
        <section class="vh-100" style="background-color: #A68F7B;">
            <div class="container p-3 h-100">
                <div class="row d-flex justify-content-center align-items-center h-100">
                    <div class="col col-xl-11 col-lg-12 col-md-12">
                        <div class="card mb-3" style="border-radius: 1rem;">
                            <div class="row g-0">
                                <div class="col-xl-4 d-none d-xl-block">
                                    <img src="./images/bookpile.jpg" alt="bookshelf" class="img-fluid" style="border-radius: 1rem;" />
                                </div>
                                <div class="col-xl-8 col-md-12 col-lg-12 d-flex align-items-center">
                                    <div class="card-body p-4 text-black">
                                        <h5 class="fw-normal mb-3 pb-3" style="letter-spacing: 1px;">Create an account</h5>
                                        <form class="row g-1" name="registerForm">
                                            <div class="col-lg-6">
                                                <div class="form-outline mb-4">
                                                    <input type="text" id="newUsername" name="newUsername" class="form-control" required/>
                                                    <label class="form-label" for="newUsername">Username</label>
                                                    <div class="invalid-feedback" style="display: inline;"></div>
                                                </div>
                                                
                                                <div class="form-outline mb-4">
                                                    <input type="email" id="newEmail" name="newEmail" class="form-control" required/>
                                                    <label class="form-label" for="newEmail">Email address</label>
                                                    <div class="invalid-feedback" style="display: inline;"></div>
                                                </div>
      
                                                <div class="form-outline mb-4">
                                                    <input type="password" id="newPassword" name="newPassword" class="form-control" required/>
                                                    <label class="form-label" for="newPassword">Password</label>
                                                    <div class="invalid-feedback" style="display: inline;"></div>
                                                </div>
                                                <div class="form-outline mb-4">
                                                    <input type="password" id="retypePassword" name="retypePassword" class="form-control" required/>
                                                    <label class="form-label" for="retypePassword">Re-enter password</label>
                                                    <div class="invalid-feedback" style="display: inline;"></div>
                                                </div>
                                            </div>
                                            <div class="col-lg-6">
                                                <div class="form-outline mb-4">
                                                    <input type="text" id="securityQuestion" name="securityQuestion" class="form-control" required/>
                                                    <label class="form-label" for="securityQuestion">Create a security question</label>
                                                    <div class="invalid-feedback" style="display: inline;"></div>
                                                </div>
                                                <div class="form-outline mb-4">
                                                    <input type="text" id="securityAnswer" name="securityAnswer" class="form-control" required/>
                                                    <label class="form-label" for="securityAnswer">Security question answer</label>
                                                    <div class="invalid-feedback" style="display: inline;"></div>
                                                </div>
                                                <div class="form-outline mb-4">
                                                    <input type="text" id="newBio" name="newBio" class="form-control"/>
                                                    <label class="form-label" for="newBio">Add a bio</label>
                                                </div>
                                                <div class="form-outline mb-4">
                                                    <input type="text" id="newFavBook" name="newFavBook" class="form-control"/>
                                                    <label class="form-label" for="newFavBook">What's your favorite book?</label>
                                                </div>
                                                <div class="form-outline mb-4">
                                                    <input type="text" id="newFavAuthor" name="newFavAuthor" class="form-control"/>
                                                    <label class="form-label" for="newFavAuthor">Who's your favorite author?</label>
                                                </div>
                                                <div id="errorRegister" style="color: #B3746F; font-weight: bold;"></div>
                                                <div id="matchPassMessage" style="color: #B3746F; font-weight: bold;"></div>
                                            </div>
                                            <div class="pt-1">
	                                              <button class="btn btn-dark btn-lg btn-block" type="submit" id="registerBtn" onclick="validateRegistration()">Create Account</button>
	                                        </div>
                                        </form>

                                    </div><!--end card body-->
                                </div>
                            </div><!--end row class gutter 0-->
                        </div><!--end card class-->
                    </div><!--end col class-->
                </div><!-- end row d-flex class-->
            </div><!--end container class-->
        </section> <!--includes the entire screen--> 
    </body>
</html>