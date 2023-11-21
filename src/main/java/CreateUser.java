import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Random;
import java.sql.SQLException;
import com.microsoft.sqlserver.jdbc.SQLServerDriver;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


@WebServlet("/CreateUser")
public class CreateUser extends HttpServlet {
       
	private static final long serialVersionUID = 1L;

	public CreateUser() {
        super();
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
	
	public void insert(String username, String password, String email, String secQuestion, String secAnswer, String bio, String book, String author) {
		String sql = "INSERT INTO User(Username,Password,Email,SecurityQuestion,SecurityAnswer,Bio,FavBook,FavAuthor) VALUES(?,?,?,?,?,?,?,?)";

        try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.setString(3, email);
            pstmt.setString(4, secQuestion);
            pstmt.setString(5, secAnswer);
            pstmt.setString(6, bio);
            pstmt.setString(7, book);
            pstmt.setString(8, author);
            pstmt.executeUpdate();
            System.out.println("inserting user");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
	}
	
	public void readShelf(String username) {
		String insertsql = "INSERT INTO Shelf(ShelfName,UserID) VALUES(?,?)";
		String getidsql = "SELECT UserID FROM User WHERE Username=?";
		int userID = 0;
		
		try (Connection conn = this.connect(); PreparedStatement pstmt  = conn.prepareStatement(getidsql)){
	            
	            pstmt.setString(1,username);
	            ResultSet rs  = pstmt.executeQuery();
	            
	            if(rs.next()) {
	            	userID = rs.getInt("UserID");
	            }
	     } catch (SQLException e) {
	            System.out.println(e.getMessage());
	            e.printStackTrace();
	     }
		
        try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(insertsql)) {
            pstmt.setString(1, "Read");
            pstmt.setInt(2, userID);
            pstmt.executeUpdate();
            System.out.println("inserting shelf");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
	}
	
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		CreateUser newUser = new CreateUser();
    	
    	String username = request.getParameter("newUsername");
		String email = request.getParameter("newEmail");
		String password = request.getParameter("newPassword");
		String repassword = request.getParameter("retypePassword");
		String question = request.getParameter("securityQuestion");
		String answer = request.getParameter("securityAnswer");
		String bio = request.getParameter("newBio");
		String book = request.getParameter("newFavBook");
		String author = request.getParameter("newFavAuthor");
		
		if (repassword.equals(password)) {
			String safePass = PasswordHash(password);
			
			newUser.insert(username, safePass, email, question, answer, bio, book, author);
			
			newUser.readShelf(username);
			
			response.sendRedirect("login.html");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
