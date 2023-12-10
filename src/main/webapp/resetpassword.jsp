<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="ISO-8859-1">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-0evHe/X+R7YkIZDRvuzKMRqM+OrBnVFBL6DOitfPri4tjfHxaWutUpFmBp4vmVor" crossorigin="anonymous">
        <link href="mystyles.css" rel="stylesheet">
        <script src="https://code.jquery.com/jquery-1.10.2.js" type="text/javascript"></script>
        <script src="javascript.js"></script>
        <title>Forgot My Password</title>
        
        <script>
			$(document).on("click", "#getQuestionBtn", function() { 
				event.preventDefault();
				var username = $("#forgotUsername").val();
				var error = $("#invalidUserReset");
				
				if (username != "") {
					$.get("VerifyUser", { forgotUsername: username }, function(responseText) {  
						if (responseText == "Invalid username") {
							error.html(responseText);
						} else {
							$("#forgotSecQuestion").html(responseText); 
							document.getElementById("enterQuesAndPass").style.display = "inherit";
							error.html("");
						}
						
					});
					return false;
				} else {
					$("#forgotUsername").css("border", "3px solid #B3746F");
				}
				
			});
			
			$(document).on("click", "#updatePassBtn", function() { 
				event.preventDefault();
				var answer = $("#forgotSecAnswer").val();
				var password = $("#updatePassword").val();
				var error = $("#invalidAnswerReset");
				
				if (validatePassReset()) {
					$.get("UpdatePassword", { forgotSecAnswer: answer, updatePassword: password }, function(responseText) {  
						if (responseText == "Invalid answer") {
							error.html(responseText);
						} else {
							window.location.href = "login.jsp";
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
                    <div class="col col-xl-8">
                        <div class="card" style="border-radius: 1rem;">
                            <div class="card-body p-4 p-lg-5 text-black">
                            	<form name="verifyUsername">
                            		<div class="card-title" style="letter-spacing: 1px;">Forgot your password?</div>
                                    <h6 class="fw-normal mb-3 pb-3">Enter the username associated with your account to retrieve your security question.</h6>
                          
                                    <div class="form-outline mb-4">
                                        <input type="text" class="form-control form-control-lg" name="forgotUsername" id="forgotUsername"/>
                                        <label class="form-label" for="forgotUsername">Username</label>
                                    </div>
                                    <div id="invalidUserReset" style="color: #B3746F; margin-bottom: 10px; font-weight: 500;"></div>
                                     <div class="pt-1 mb-4">
                                        <button class="btn btn-md" id="getQuestionBtn">Submit</button>
                                    </div>
                                    <div id="forgotSecQuestion"></div>
                            	</form>
                           		 <div id="enterQuesAndPass" style="display: none;"><br>
                                <form name="resetForm">
                                    
										<h6 class="fw-normal mb-3 pb-3">Please answer the security question that you created to reset your password.</h6>
	                                     
	                                     <div class="form-outline mb-4">
	                                        <input type="text" class="form-control form-control-lg" name="forgotSecAnswer" id="forgotSecAnswer"/>
	                                        <label class="form-label" for="forgotSecAnswer">Your Answer</label>
	                                    </div>
	                                    <div id="invalidAnswerReset" style="color: #B3746F; margin-bottom: 10px; margin-top: 10px; font-weight: 500;"></div>
	                                    <div class="form-outline mb-4">
	                                        <input type="text" class="form-control form-control-lg" name="updatePassword" id="updatePassword"/>
	                                        <label class="form-label" for="updatePassword">New Password</label>
	                                    </div>
	                                    
	                                    <div class="pt-1 mb-4">
                                        	<button class="btn btn-md" id="updatePassBtn">Reset Password</button>
                                    	</div>
									
                                </form>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </section> 
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        <!--  
          
        <section class="vh-100" style="background-color: #D9C9BA;">
            <div class="container p-3 h-100">
                <div class="row d-flex justify-content-center align-items-center h-100">
                    <div class="col col-xl-8">
                        <div class="card" style="border-radius: 1rem;">
                            <div class="card-body p-4 p-lg-5 text-black">
                                <form name="resetForm" method="post" action="UpdatePassword">
                                    <div class="card-title" style="letter-spacing: 1px;">Forgot your password?</div>
                                    <h6 class="fw-normal mb-3 pb-3">Enter the username associated with your account to retrieve your security question.</h6>
                          
                                    <div class="form-outline mb-4">
                                        <input type="text" class="form-control form-control-lg" name="forgotUsername" id="forgotUsername"/>
                                        <label class="form-label" for="forgotUsername">Username</label>
                                    </div>
                                    <div id="somediv"></div>
                                     <div class="pt-1 mb-4">
                                        <button class="btn btn-md" type="button" id="getQuestionBtn" onclick="showSecQuestion()">Submit</button>
                                    </div>
                                    <div id="enterQuesAndPass" style="display: none;">
										<h6 class="fw-normal mb-3 pb-3">Please answer the security question that you created to reset your password.</h6>
	                                     <div>
											 <p id="forgotSecQuestion">question</p>
										 </div>
	                                     <div class="form-outline mb-4">
	                                        <input type="text" class="form-control form-control-lg" name="forgotSecAnswer" id="forgotSecAnswer"/>
	                                        <label class="form-label" for="forgotSecAnswer">Your Answer</label>
	                                    </div>
	                                    <div class="form-outline mb-4">
	                                        <input type="text" class="form-control form-control-lg" name="updatePassword" id="updatePassword"/>
	                                        <label class="form-label" for="updatePassword">New Password</label>
	                                    </div>
	                                    <div id="errorMessage" style="color: #732222;"></div>
	                                    <div class="pt-1 mb-4">
                                        	<button class="btn btn-md" type="submit" id="updatePassBtn">Reset Password</button>
                                    	</div>
									</div>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </section> -->
    </body>
</html>