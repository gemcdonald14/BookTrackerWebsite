/*---------------------------------------------VALIDATE LOGIN-----------------------------------------------*/
function validateLogin() {
    var notEmpty = true;
    
    if (document.forms["loginForm"]["inputUsername"].value == "") {
        document.getElementById("inputUsername").style.border = "3px solid #B3746F";
        notEmpty = false;
    } else {
        document.getElementById("inputUsername").style.border = "";
        notEmpty = true;
    }
    if (document.forms["loginForm"]["inputPassword"].value == "") {
        document.getElementById("inputPassword").style.border = "3px solid #B3746F";
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
/*---------------------------------------------VALIDATE REGISTRATION-----------------------------------------------*/
function validateRegistration() {
    var notEmpty = true;
    var matchPass = true;
    
    if (document.forms["registerForm"]["newUsername"].value == "") {
        document.getElementById("newUsername").style.border = "3px solid #B3746F";
        notEmpty = false;
    } else {
        document.getElementById("newUsername").style.border = "";
        notEmpty = true;
    }
    if (document.forms["registerForm"]["newEmail"].value == "") {
        document.getElementById("newEmail").style.border = "3px solid #B3746F";
        notEmpty = false;
    } else {
        document.getElementById("newEmail").style.border = "";
        notEmpty = true;
    }
    if (document.forms["registerForm"]["newPassword"].value == "") {
        document.getElementById("newPassword").style.border = "3px solid #B3746F";
        notEmpty = false;
    } else {
        document.getElementById("newPassword").style.border = "";
        notEmpty = true;
    }
    if (document.forms["registerForm"]["retypePassword"].value == "") {
        document.getElementById("retypePassword").style.border = "3px solid #B3746F";
        notEmpty = false;
    } else {
        document.getElementById("retypePassword").style.border = "";
        notEmpty = true;
    }
    if (document.forms["registerForm"]["securityQuestion"].value == "") {
        document.getElementById("securityQuestion").style.border = "3px solid #B3746F";
        notEmpty = false;
    } else {
        document.getElementById("securityQuestion").style.border = "";
        notEmpty = true;
    }
    if (document.forms["registerForm"]["securityAnswer"].value == "") {
        document.getElementById("securityAnswer").style.border = "3px solid #B3746F";
        notEmpty = false;
    } else {
        document.getElementById("securityAnswer").style.border = "";
        notEmpty = true;
    }/*
    if (document.forms["registerForm"]["newPassword"].value == document.forms["registerForm"]["retypePassword"].value) {
		matchPass = true;
	} else {
		matchPass = false;
		document.getElementById("matchPassMessage").innerHTML = "Passwords must match!";
	}
    if (notEmpty == false) {
        document.getElementById("errorMessage").innerHTML = "*** Please provide the required information ***";
    }*/
    
    
    return notEmpty;
}
/*---------------------------------------------VALIDATE RESET PASSWORD-----------------------------------------------*/
function validatePassReset() {
    var notEmpty = true;
    
    if (document.forms["resetForm"]["forgotSecAnswer"].value == "") {
        document.getElementById("forgotSecAnswer").style.border = "3px solid #B3746F";
        notEmpty = false;
    } else {
        document.getElementById("forgotSecAnswer").style.border = "";
        notEmpty = true;
    }
    if (document.forms["resetForm"]["updatePassword"].value == "") {
        document.getElementById("updatePassword").style.border = "3px solid #B3746F";
        notEmpty = false;
    } else {
        document.getElementById("updatePassword").style.border = "";
        notEmpty = true;
    }/*
    if (notEmpty == false) {
        document.getElementById("errorMessage").innerHTML = "*** Please provide the required information ***";
    }*/
    
    return notEmpty;
}
/*-----------------------------CHANGE THE DISPLAY TO SHOW NEXT SECTION (FORGOT PASSWORD)-----------------------------------------------*/

/*---------------------------------------------LOG OUT USER-----------------------------------------------*/
function logOut() {
	window.location.href = "login.jsp";
}
/*---------------------------------------------VALIDATE CREATE GOAL-----------------------------------------------*/
function validateNewGoal() {
    var notEmpty = true;
    
    if (document.forms["newGoalForm"]["newGoalTitle"].value == "") {
        document.getElementById("newGoalTitle").style.border = "3px solid #B3746F";
        notEmpty = false;
    } else {
        document.getElementById("newGoalTitle").style.border = "";
        notEmpty = true;
    }
    if (document.forms["newGoalForm"]["newGoalTarget"].value == "") {
        document.getElementById("newGoalTarget").style.border = "3px solid #B3746F";
        notEmpty = false;
    } else {
        document.getElementById("newGoalTarget").style.border = "";
        notEmpty = true;
    }
    
    return notEmpty;
}
/*---------------------------------------------VALIDATE UPDATE GOAL-----------------------------------------------*/
function validateUpdateGoal() {
    var notEmpty = true;
    
    if (document.forms["updateGoalForm"]["updateGoalCompleted"].value == "") {
        document.getElementById("updateGoalCompleted").style.border = "3px solid #B3746F";
        notEmpty = false;
    } else {
        document.getElementById("updateGoalCompleted").style.border = "";
        notEmpty = true;
    }
    
    return notEmpty;
}
/*---------------------------------------------VALIDATE CREATE SHELF-----------------------------------------------*/
function validateCreateShelf() {
    var notEmpty = true;
    
    if (document.forms["newShelfForm"]["newShelfName"].value == "") {
        document.getElementById("newShelfName").style.border = "3px solid #B3746F";
        notEmpty = false;
    } else {
        document.getElementById("newShelfName").style.border = "";
        notEmpty = true;
    }
    
    return notEmpty;
}



