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

@WebServlet("/LoginUser")
public class LoginUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
    public LoginUser() {
        super();
        // TODO Auto-generated constructor stub
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
		int userID = 0;
		getServletContext().setAttribute("userID", userID);
    }
		
	
	public void destroy() {
		// TODO Auto-generated method stub
	}
	
	public Boolean verifyUser(String providedUsername, String providedPassword) {
		String sql = "SELECT Username,Password,UserID FROM User WHERE Username=? AND Password=?";
		Boolean result = false;
		
		try (Connection conn = this.connect(); PreparedStatement pstmt  = conn.prepareStatement(sql)){
	            
	            pstmt.setString(1,providedUsername);
	            pstmt.setString(2,providedPassword);
	            
	            
	            ResultSet rs  = pstmt.executeQuery();
	            
	            if(rs.next()) {
	            	String dbUsername = rs.getString("Username");
	            	String dbPassword = rs.getString("Password");
	            	int userID = rs.getInt("UserID");
		            System.out.println("DB Username: " + dbUsername);
		            System.out.println("Provided Username: " + providedUsername);
		            System.out.println("DB Password: " + dbPassword);
		            System.out.println("Provided Password: " + providedPassword);
		            System.out.println("UserID: " + userID);
		            
		            if(dbUsername.equals(providedUsername) && dbPassword.equals(providedPassword)) {
		            	result = true;
		            }
	            	
	            }
	     } catch (SQLException e) {
	            System.out.println(e.getMessage());
	            e.printStackTrace();
	     }
		return result;
	}
	
	public int getID(String username, String password) {
		String sql = "SELECT UserID FROM User WHERE Username=? AND Password=?";
		int userID = 0;
		
		try (Connection conn = this.connect(); PreparedStatement pstmt  = conn.prepareStatement(sql)){
	            
	            pstmt.setString(1,username);
	            pstmt.setString(2,password);
	            
	            ResultSet rs  = pstmt.executeQuery();
	            
	            if(rs.next()) {
	            	userID = rs.getInt("UserID");
		            System.out.println("UserID: " + userID);
	            }
	     } catch (SQLException e) {
	            System.out.println(e.getMessage());
	            e.printStackTrace();
	     }
		return userID;
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		LoginUser returningUser = new LoginUser();
    	
		String username = request.getParameter("inputUsername");
		String password = request.getParameter("inputPassword");
		
		System.out.println("Username: " + username);

		//password security 
		String safePass = PasswordHash(password);
		
		if (returningUser.verifyUser(username,safePass)) {
			System.out.print("made it to redirect");
			
			ServletContext servletContext = getServletContext();
        	int id = (int) servletContext.getAttribute("userID");
        	id = returningUser.getID(username, safePass);
        	System.out.println(id);
        	servletContext.setAttribute("userID", id);
			
			response.sendRedirect("homepage.html");
		} else {
			PrintWriter writer = response.getWriter();
			writer.println("failed");
		}
		
		/*
		
		String username = request.getParameter("inputUsername");
		String password = request.getParameter("inputPassword");
		
		PrintWriter writer = response.getWriter();
		
		String htmlResponse = "<html>";
		htmlResponse += "<h2>username is " + username + "</h2>";
		htmlResponse += "<h2>password is " + password + "</h2>";
		htmlResponse += "</html>";
		
		writer.println(htmlResponse);
		*/
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
