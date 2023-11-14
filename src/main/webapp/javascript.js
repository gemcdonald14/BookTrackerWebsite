/*---------------------------------------------VALIDATE LOGIN FUNCTION-----------------------------------------------*/
function validateLogin() {
    var notEmpty = true;
    
    if (document.forms["loginForm"]["inputUsername"].value == "") {
        document.getElementById("inputUsername").style.border = "3px solid #732222";
        notEmpty = false;
    } else {
        document.getElementById("inputUsername").style.border = "";
        notEmpty = true;
    }
    if (document.forms["loginForm"]["inputPassword"].value == "") {
        document.getElementById("inputPassword").style.border = "3px solid #732222";
        notEmpty = false;
    } else {
        document.getElementById("inputPassword").style.border = "";
        notEmpty = true;
    }
    if (notEmpty == false) {
        document.getElementById("errorMessage").innerHTML = "*** Please provide the required information ***";
    }
    return notEmpty;
}
/*---------------------------------------------VALIDATE REGISTRATION FUNCTION-----------------------------------------------*/
function validateRegistration() {
    var notEmpty = true;
    var matchPass = true;
    
    if (document.forms["registerForm"]["newUsername"].value == "") {
        document.getElementById("newUsername").style.border = "3px solid #732222";
        notEmpty = false;
    } else {
        document.getElementById("newUsername").style.border = "";
        notEmpty = true;
    }
    if (document.forms["registerForm"]["newEmail"].value == "") {
        document.getElementById("newEmail").style.border = "3px solid #732222";
        notEmpty = false;
    } else {
        document.getElementById("newEmail").style.border = "";
        notEmpty = true;
    }
    if (document.forms["registerForm"]["newPassword"].value == "") {
        document.getElementById("newPassword").style.border = "3px solid #732222";
        notEmpty = false;
    } else {
        document.getElementById("newPassword").style.border = "";
        notEmpty = true;
    }
    if (document.forms["registerForm"]["retypePassword"].value == "") {
        document.getElementById("retypePassword").style.border = "3px solid #732222";
        notEmpty = false;
    } else {
        document.getElementById("retypePassword").style.border = "";
        notEmpty = true;
    }
    if (document.forms["registerForm"]["securityQuestion"].value == "") {
        document.getElementById("securityQuestion").style.border = "3px solid #732222";
        notEmpty = false;
    } else {
        document.getElementById("securityQuestion").style.border = "";
        notEmpty = true;
    }
    if (document.forms["registerForm"]["securityAnswer"].value == "") {
        document.getElementById("securityAnswer").style.border = "3px solid #732222";
        notEmpty = false;
    } else {
        document.getElementById("securityAnswer").style.border = "";
        notEmpty = true;
    }
    if (document.forms["registerForm"]["newPassword"].value == document.forms["registerForm"]["retypePassword"].value) {
		matchPass = true;
	} else {
		matchPass = false;
		document.getElementById("matchPassMessage").innerHTML = "Passwords must match!";
	}
    if (notEmpty == false) {
        document.getElementById("errorMessage").innerHTML = "*** Please provide the required information ***";
    }
    
    var result = false;
    if (notEmpty && matchPass) {
		result = true
	} else {
		result = false;
	}
    return result;
}
/*---------------------------------------------VALIDATE RESET PASSWORD FUNCTION-----------------------------------------------*/
function validatePassReset() {
    var notEmpty = true;
    
    if (document.forms["resetForm"]["forgotUsername"].value == "") {
        document.getElementById("forgotUsername").style.border = "3px solid #732222";
        notEmpty = false;
    } else {
        document.getElementById("forgotUsername").style.border = "";
        notEmpty = true;
    }
    if (document.forms["resetForm"]["forgotSecAnswer"].value == "") {
        document.getElementById("forgotSecAnswer").style.border = "3px solid #732222";
        notEmpty = false;
    } else {
        document.getElementById("forgotSecAnswer").style.border = "";
        notEmpty = true;
    }
    if (document.forms["resetForm"]["updatePassword"].value == "") {
        document.getElementById("updatePassword").style.border = "3px solid #732222";
        notEmpty = false;
    } else {
        document.getElementById("updatePassword").style.border = "";
        notEmpty = true;
    }
    if (notEmpty == false) {
        document.getElementById("errorMessage").innerHTML = "*** Please provide the required information ***";
    }
    
    return notEmpty;
}
/*---------------------------------------------CHANGE THE DISPLAY TO SHOW NEXT SECTION (FORGOT PASSWORD)-----------------------------------------------*/
function showSecQuestion(){
	document.getElementById("enterQuesAndPass").style.display = "inherit";
	
	let secQuestion = 'question';
	let data = new FormData();
	data.append('question', secQuestion);
	let xhr = new XMLHttpRequest();

    xhr.onreadystatechange = function () {
        if (xhr.readyState === XMLHttpRequest.DONE) {
            if (xhr.status === 200) {
                const newHTML = xhr.responseText;

                // Select the container div based on the goal type (monthly or yearly)
                let questionP = document.getElementById(forgotSecQuestion);

                // Append the new goal content to the container
                questionP.innerHTML += newHTML;
            } else {
                console.log('Error: ' + xhr.status);
            }
        }
    };
    // Open the connection with your servlet URL
    xhr.open('POST', 'UpdatePassword', true);
    xhr.send(data);
}
/*---------------------------------------------ADD NEW GOAL-----------------------------------------------*/
function addGoal() {
    // Your AJAX code to send the data to the servlet...
    // Assuming you have these variables storing the new goal data
    let newGoalTitle = 'New Goal Title';
    let newGoalType = '1'; // Assuming 1 is for monthly, 2 is for yearly
    let newGoalTarget = '5'; // Assuming a target of 5 books

    // Construct a data object to send via AJAX
    let data = new FormData();
    data.append('newGoalTitle', newGoalTitle);
    data.append('newGoalType', newGoalType);
    data.append('newGoalTarget', newGoalTarget);

    // AJAX call to retrieve the updated content after adding the goal
    let xhr = new XMLHttpRequest();

    xhr.onreadystatechange = function () {
        if (xhr.readyState === XMLHttpRequest.DONE) {
            if (xhr.status === 200) {
                const newGoalHTML = xhr.responseText;

                // Select the container div based on the goal type (monthly or yearly)
                let containerUl = document.getElementById(newGoalType === '1' ? 'monthGoalsList' : 'yearGoalsList');

                // Append the new goal content to the container
                containerUl.innerHTML += newGoalHTML;
            } else {
                console.log('Error: ' + xhr.status);
            }
        }
    };
    // Open the connection with your servlet URL
    xhr.open('POST', 'AddGoal', true);
    xhr.send(data);
}













