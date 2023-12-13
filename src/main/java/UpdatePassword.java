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
	      MessageDigest md = MessageDigest.getInstance("MD5");

	      md.update(passwordToHash.getBytes());

	      byte[] bytes = md.digest();

	      StringBuilder sb = new StringBuilder();
	      for (int i = 0; i < bytes.length; i++) {
	        sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
	      }

	      generatedPassword = sb.toString();
	    } catch (NoSuchAlgorithmException e) {
	      e.printStackTrace();
	    }
	    
		return generatedPassword;
	}
    
    public Boolean resetPassword(String question, String answer, String password) {
		String sql = "UPDATE User SET Password=? WHERE SecurityQuestion=? AND SecurityAnswer=?";
		Boolean result = false;
		
		try (Connection conn = this.connect(); PreparedStatement pstmt  = conn.prepareStatement(sql)){
	            
	            pstmt.setString(1,password);
	            pstmt.setString(2,question);
	            pstmt.setString(3,answer);
	            
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
    

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UpdatePassword resetPass = new UpdatePassword();
		
		ServletContext servletContext = getServletContext();
		String secQues = (String) servletContext.getAttribute("question");
    	
		String secAnswer = request.getParameter("forgotSecAnswer");
		String newPass = request.getParameter("updatePassword");
		String safePass = PasswordHash(newPass);
		
		System.out.println("question: " + secQues);
		System.out.println("answer: " + secAnswer);
		System.out.println("password: " + safePass);
		
		
		if (resetPass.resetPassword(secQues, secAnswer, safePass)) {
			
			System.out.println("question: " + secQues);
			System.out.println("answer: " + secAnswer);
			System.out.println("password: " + safePass);
			
		} else {
			response.getWriter().write("Invalid answer");
		}
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}

}
