import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import com.microsoft.sqlserver.jdbc.SQLServerDriver;
import jakarta.servlet.ServletContext;

@WebServlet("/VerifyUser")
public class VerifyUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
 
    public VerifyUser() {
        super();
        
    }
    
    private Connection connect() {
    	Connection conn = null;
		String url = "jdbc:sqlite:C:/sqlite/db/capstone.db";
		
        try {
            // create a connection to the database
            conn = DriverManager.getConnection(url);
            System.out.println("Connection to SQLite has been established.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }
    
    public void init(ServletConfig config) throws ServletException {
		super.init(config);
		String question = "";
		getServletContext().setAttribute("question", question);
    }
   
    public String getSecurityQuestion(String username) {
		String sql = "SELECT SecurityQuestion FROM User WHERE Username=?";
		String question = "";
		
		try (Connection conn = this.connect(); PreparedStatement pstmt  = conn.prepareStatement(sql)){
	            
	            pstmt.setString(1,username);
	            
	            ResultSet rs  = pstmt.executeQuery();
	            
	            if(rs.next()) {
	            	question = rs.getString("SecurityQuestion");
		            System.out.println("Question: " + question);
	            }
	     } catch (SQLException e) {
	            System.out.println(e.getMessage());
	            e.printStackTrace();
	     }
		return question;
	}
    
    public Boolean verifyUsername(String username) {
    	String sql = "SELECT * FROM User WHERE Username=?";
		Boolean result = false;
		
		try (Connection conn = this.connect(); PreparedStatement pstmt  = conn.prepareStatement(sql)){
	            
	            pstmt.setString(1,username);
	            ResultSet rs  = pstmt.executeQuery();
	            
	            if(rs.next()) {
	            	String dbUsername = rs.getString("Username");
		            System.out.println("DB Username: " + dbUsername);
		            System.out.println("Provided Username: " + username);
		            
		            if(dbUsername.equals(username)) {
		            	result = true;
		            }
	            }
	     } catch (SQLException e) {
	            System.out.println(e.getMessage());
	            e.printStackTrace();
	     }
		return result;
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		VerifyUser verify = new VerifyUser();
		
		String username = request.getParameter("forgotUsername");
		
		
		if (username != null && !username.trim().isEmpty()) {
			String trimUsername = username.trim();
			System.out.println("Forgot Username: " + trimUsername);
			
			if (verify.verifyUsername(trimUsername)) {
				ServletContext servletContext = getServletContext();
	        	String secQues = (String) servletContext.getAttribute("question");
	        	secQues = verify.getSecurityQuestion(trimUsername);
	        	System.out.println(secQues);
	        	servletContext.setAttribute("question", secQues);
				
				
				
				//response.setContentType("text/plain"); 
			    //response.setCharacterEncoding("UTF-8"); 
			    response.getWriter().write(secQues);
			} else {
				response.getWriter().write("Invalid username");
			}
			
		}
		
		
		
		/*
		if (resetPass.verifyUsername(username)) {
			String secQuestion = resetPass.getSecurityQuestion(username);
			request.setAttribute("securityQuestion", secQuestion);
			
			RequestDispatcher dispatcher = request.getRequestDispatcher("forgotpass.jsp");
			dispatcher.forward(request, response);
		}
		*/
		
		
		/*
		
		String secQuestion = resetPass.getSecurityQuestion(username);
		String secAnswer = request.getParameter("forgotSecAnswer");
		String newPass = request.getParameter("updatePassword");
		
		
		if (resetPass.resetPassword(username, secQuestion, secAnswer, newPass)) {
			System.out.print("made it to redirect");
			response.sendRedirect("login.html");
		} else {
			PrintWriter writer = response.getWriter();
			writer.println("failed");
		}
		
		*/
		/*
		UpdatePassword resetPass = new UpdatePassword();
    	
		String username = request.getParameter("forgotUsername");
		
		System.out.println("Forgot Username: " + username);
		
		if (resetPass.verifyUsername(username)) {
			
			//CALL THE JAVASCRIPT FUNCTION TO DISPLAY THE NEXT SECTION 
			//PrintWriter out = response.getWriter();
			//out.println("<script>function showSecQuestion(){document.getElementById('enterQuesAndPass').className = 'd-block';}</script>");
			//CALL THE JAVASCRIPT FUNCTION TO DISPLAY THE SECURITY QUESTION 
			String secQuestion = resetPass.getSecurityQuestion(username);
			response.getWriter().write(secQuestion);
			String secAnswer = request.getParameter("forgotSecAnswer");
			String newPass = request.getParameter("updatePassword");
			
			String safePass = PasswordHash(newPass);
			
			//resetPass.resetPassword(username, secQuestion, secAnswer);
			
			
			if (resetPass.resetPassword(username, secQuestion, secAnswer, safePass)) {
				System.out.print("made it to redirect");
				response.sendRedirect("login.html");
			} else {
				PrintWriter writer = response.getWriter();
				writer.println("failed");
			}
		}*/
		//	USING JQUERY
		/*
		String username = request.getParameter("forgotUsername").trim();
		if(username == null || "".equals(username)) {
			username = "Guest";
		}
		
		String greetings = "hello " + username;
		
		response.setContentType("text/plain");
		response.getWriter().write(greetings);	*/
		/*
		
		String username = "username";

	    response.setContentType("text/plain");  // Set content type of the response so that jQuery knows what it can expect.
	    response.setCharacterEncoding("UTF-8"); // You want world domination, huh?
	    response.getWriter().write(username);    */
		
		
		
		
		
		
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/*
		
		UpdatePassword resetPass = new UpdatePassword();
		
		String username = request.getParameter("forgotUsername");
		String secAnswer = request.getParameter("forgotSecAnswer");
		String newPass = request.getParameter("updatePassword");
		String secQuestion = (String) request.getAttribute("securityQuestion");
		String safePass = PasswordHash(newPass);
		
		if (resetPass.resetPassword(username, secQuestion, secAnswer, safePass)) {
			System.out.print("made it to redirect");
			response.sendRedirect("login.html");
		} else {
			PrintWriter writer = response.getWriter();
			writer.println("failed");
		}*/
		
		doGet(request, response);
	}

}
