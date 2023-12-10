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
		<title>Login</title>
		
		<script>
			$(document).on("click", "#loginBtn", function(event) { 
				event.preventDefault();
				var username = $("#inputUsername").val();
				var password = $("#inputPassword").val();
				
				if (validateLogin()) {
					console.log("Button clicked. Sending AJAX request.");
					$.get("LoginUser?timestamp=" + new Date().getTime(), { inputUsername: username, inputPassword: password }, function(responseText) {  
						console.log("Received response from server:", responseText);
						
						if (responseText == "True") {
							window.location.href = "homepage.jsp";
							
						} else {
							var error = $("#errorMessage");
							error.html("*** Invalid username or password ***")
						}
						
					});
					return false;
				} 
			});
		</script>
		
	</head>
	
	<body>
		<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/js/bootstrap.bundle.min.js" integrity="sha384-pprn3073KE6tl6bjs2QrFaJGz5/SUsLqktiwsUTF55Jfv3qYSDhgCecCxMW52nD2" crossorigin="anonymous"></script>
	
		<section class="vh-100" style="background-color: #D9C9BA;">
			<div class="container p-3 h-100">
				<div class="row d-flex justify-content-center align-items-center h-100">
					<div class="col col-xl-10">
						<div class="card" style="border-radius: 1rem;">
							<div class="row g-0">
								<div class="col-md-6 col-lg-7 d-flex align-items-center">
									<div class="card-body p-4 p-lg-5 text-black">
										<form name="loginForm">
											<h5 class="fw-normal mb-3 pb-3" style="letter-spacing: 1px;">Sign into your account</h5>
	
											<div class="form-outline mb-4">
												<input type="text" id="inputUsername" name="inputUsername" class="form-control form-control-lg" />
												<label class="form-label" for="inputUsername">Username</label>
											</div>
	
											<div class="form-outline mb-4">
												<input type="password" id="inputPassword" name="inputPassword" class="form-control form-control-lg" />
												<label class="form-label" for="inputPassword">Password</label>
											</div>
	
											<div id="errorMessage" style="color: #B3746F; margin-bottom: 10px; font-weight: 500;"></div>
	
											<div class="pt-1 mb-4">
												<button class="btn btn-dark btn-lg btn-block" type="submit" id="loginBtn">Log In</button>
											</div>
	
											<a class="small" href="resetpassword.jsp" id="forgotPassLink">Forgot your password?</a>
											<p class="mb-5 pb-lg-2">Don't have an account? <a id="createAccLink" href="register.jsp">Create An Account</a></p>
										</form>
	
									</div><!--end card body-->
								</div>
								<div class="col-md-6 col-lg-5 d-none d-md-block">
									<img src="./images/dark-bookshelf.jpg" alt="bookshelf" class="img-fluid" style="border-radius: 1rem;" />
								</div>
							</div><!--end row class gutter 0-->
						</div><!--end card class-->
					</div><!--end col class-->
				</div><!-- end row d-flex class-->
			</div><!--end container class-->
		</section> <!--includes the entire screen-->
	</body>
</html>