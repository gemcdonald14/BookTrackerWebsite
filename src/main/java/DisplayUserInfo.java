import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
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

@WebServlet("/DisplayUserInfo")
public class DisplayUserInfo extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public DisplayUserInfo() {
        super();
    }
    
    public void init(ServletConfig config) throws ServletException {
		super.init(config);
		String username = "";
		String email = "";
		String bio = "";
		String totalBooks = "";
		String favBook = "";
		String favAuthor = "";
		
		getServletContext().setAttribute("username", username);
		getServletContext().setAttribute("email", email);
		getServletContext().setAttribute("bio", bio);
		getServletContext().setAttribute("totalBooks", totalBooks);
		getServletContext().setAttribute("favAuthor", favAuthor);
		getServletContext().setAttribute("favBook", favBook);
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
    
    public int getTotalBooks(int id, int shelfid) {
    	String totalbookssql = "SELECT SUM(NumBooks) FROM Shelf WHERE UserID=?";
    	int totalBooks = 0;
		
		//get total saved books 
		try (Connection conn = this.connect(); PreparedStatement pstmt  = conn.prepareStatement(totalbookssql)){
			 
			pstmt.setInt(1, id);
	        ResultSet rs = pstmt.executeQuery();

	        if (rs.next()) {
	        	totalBooks = rs.getInt(1);
	            System.out.println("Total Books: " + totalBooks);
	        }
	     } catch (SQLException e) {
	            System.out.println(e.getMessage());
	            e.printStackTrace();
	     }
		
		return totalBooks;
    }

	
	public String[] display(int id) {
		String sql = "SELECT * FROM User WHERE UserID=?";
		String[] userInfo = new String[6];

        try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
        	pstmt.setInt(1, id);
        	
        	ResultSet rs  = pstmt.executeQuery();
            
            if(rs.next()) {
            	String username = rs.getString("Username");
            	userInfo[0] = username;
	            System.out.println("Username: " + username);
	            
	            String email = rs.getString("Email");
	            userInfo[1] = email;
	            System.out.println("Email: " + email);
	            
	            String bio = rs.getString("Bio");
            	userInfo[2] = bio;
	            System.out.println("Bio: " + bio);
	            
	            String favBook = rs.getString("FavBook");
            	userInfo[3] = favBook;
	            System.out.println("Fav Book: " + favBook);
	            
	            String favAuthor = rs.getString("FavAuthor");
            	userInfo[4] = favAuthor;
	            System.out.println("Fav Author: " + favAuthor);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return userInfo;
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletContext servletContext = getServletContext();
    	int id = (int) servletContext.getAttribute("userID");
    	System.out.println(id);
    	
    	int shelfid = (int) servletContext.getAttribute("readShelfID");
    	
    	String[] infoArr = display(id);
    	String usernameVal = infoArr[0];
    	String emailVal = infoArr[1];
    	String bioVal = infoArr[2];
    	int totalVal = getTotalBooks(id, shelfid);
    	String favBookVal = infoArr[4];
    	String favAuthorVal = infoArr[5];
    	
    	servletContext.getAttribute("username");
    	servletContext.setAttribute("username", usernameVal);
    	System.out.println(usernameVal);
    	
    	servletContext.getAttribute("email");
    	servletContext.setAttribute("email", emailVal);
    	System.out.println(emailVal);
    	
    	servletContext.getAttribute("totalBooks");
    	servletContext.setAttribute("totalBooks", totalVal);
    	System.out.println(totalVal);
    	
    	if (bioVal != null) {
    		servletContext.getAttribute("bio");
        	servletContext.setAttribute("bio", bioVal);
        	System.out.println(bioVal);
    	} else {
    		servletContext.getAttribute("bio");
        	servletContext.setAttribute("bio", "N/A");
        	bioVal = "N/A";
        	System.out.println(bioVal);
    	}
    	
    	if (favBookVal != null) {
    		servletContext.getAttribute("favBook");
        	servletContext.setAttribute("favBook", favBookVal);
        	System.out.println(favBookVal);
    	} else {
    		servletContext.getAttribute("favBook");
        	servletContext.setAttribute("favBook", "N/A");
        	favBookVal = "N/A";
        	System.out.println(favBookVal);
    	}
    	
    	if (favAuthorVal != null) {
    		servletContext.getAttribute("favAuthor");
        	servletContext.setAttribute("favAuthor", favAuthorVal);
        	System.out.println(favAuthorVal);
    	} else {
    		servletContext.getAttribute("favAuthor");
        	servletContext.setAttribute("favAuthor", "N/A");
        	favAuthorVal = "N/A";
        	System.out.println(favAuthorVal);
    	}

    	String info = "<div class=\"form-outline mb-4\">Username:&nbsp;<div id=\"accUsername\" style=\"display: inline-flex;\" class=\"accInfo\">" + usernameVal + "</div></div>"
    				+ "<div class=\"form-outline mb-4\">Email:&nbsp;<div id=\"accEmail\" style=\"display: inline-flex;\" class=\"accInfo\">" + emailVal + "</div></div>"
    				+ "<div class=\"form-outline mb-4\">Bio:&nbsp;<div id=\"accBio\" style=\"display: inline-flex;\" class=\"accInfo\">" + bioVal + "</div></div>"
    				+ "<div class=\"form-outline mb-4\">Total Books:&nbsp;<div id=\"accNumBooks\" style=\"display: inline-flex;\" class=\"accInfo\">" + totalVal + "</div></div>"
    				+ "<div class=\"form-outline mb-4\">Favorite Book:&nbsp;<div id=\"accFavBook\" style=\"display: inline-flex;\" class=\"accInfo\">" + favBookVal + "</div></div>"
    				+ "<div class=\"form-outline mb-4\">Favorite Author:&nbsp;<div id=\"accfavAuthor\" style=\"display: inline-flex;\" class=\"accInfo\">" + favAuthorVal + "</div></div>";
    	
    	response.setContentType("text/html");
    	response.getWriter().write(info);
    	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
