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

@WebServlet("/UpdatePassword")
public class UpdatePassword extends HttpServlet {
	private static final long serialVersionUID = 1L;
 
    public UpdatePassword() {
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
    
    public String PasswordHash(String password) {
		String passwordToHash = password;
	    String generatedPassword = null;

	    try 
	    {
	      // Create MessageDigest instance for MD5
	      MessageDigest md = MessageDigest.getInstance("MD5");

	      // Add password bytes to digest
	      md.update(passwordToHash.getBytes());

	      // Get the hash's bytes
	      byte[] bytes = md.digest();

	      // This bytes[] has bytes in decimal format. Convert it to hexadecimal format
	      StringBuilder sb = new StringBuilder();
	      for (int i = 0; i < bytes.length; i++) {
	        sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
	      }

	      // Get complete hashed password in hex format
	      generatedPassword = sb.toString();
	    } catch (NoSuchAlgorithmException e) {
	      e.printStackTrace();
	    }
	    
		return generatedPassword;
	}
    
    public Boolean resetPassword(String username, String question, String answer, String password) {
		String sql = "UPDATE User SET Password=? WHERE Username=? AND SecurityQuestion=? AND SecurityAnswer=?";
		Boolean result = false;
		
		try (Connection conn = this.connect(); PreparedStatement pstmt  = conn.prepareStatement(sql)){
	            
	            pstmt.setString(1,password);
	            pstmt.setString(2,username);
	            pstmt.setString(3,question);
	            pstmt.setString(4,answer);
	            
	            int rowsAffected = pstmt.executeUpdate();
	         
	            if(rowsAffected > 0) {
		            result = true;
		        }
	     } catch (SQLException e) {
	            System.out.println(e.getMessage());
	            e.printStackTrace();
	     }
		return result;
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
    	String sql = "SELECT Username FROM User WHERE Username=?";
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
		}
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
	}

}
