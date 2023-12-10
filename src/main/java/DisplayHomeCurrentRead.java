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

@WebServlet("/DisplayHomeCurrentRead") 
public class DisplayHomeCurrentRead extends HttpServlet {
	private static final long serialVersionUID = 1L;
 
    public DisplayHomeCurrentRead() {
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
    
    public String displayCurrentRead(int id) {
    	String sql = "SELECT * FROM Book WHERE UserID=? AND IsCurrentRead=1 LIMIT 1";
    	String resultCurrent = "<div class=\"card\" style=\"border-radius: 1rem;\">"
				+ "<div class=\"row g-0\"><div class=\"col-md-4 d-none d-lg-block\">";
    	
        try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
        	pstmt.setInt(1, id);
        	
        	ResultSet rs  = pstmt.executeQuery();
            
            if(rs.next()) {
            	String title = rs.getString("Title");
	            System.out.println("Title: " + title);
	            
	            String author = rs.getString("Author");
	            System.out.println("Author: " + author);
	            
	            int numpages = rs.getInt("NumPages");
	            System.out.println("Number of Pages: " + numpages);
	            
	            int readpages = rs.getInt("ReadPages");
	            System.out.println("Read Pages: " + readpages);
	            
	            int percent = (int)  (int) ((readpages * 100.0) / numpages);
	            System.out.println(percent);
	            
	            String currentReadItem = "<img src=\"./images/bookpile.jpg\" class=\"img-fluid\" style=\"border-radius: 1rem;\"></div>"
	    				+ "<div class=\"col-md-8\"> <div class=\"card-body\">"
	    				+ "<h5 class=\"card-title\">Currently Reading</h5>"
	    				
	    				+ "<ul><li>" + title + "</li>"
	    				+"<li>" + author + "</li>"
	    				+ "<li>" + readpages + " / " + numpages + " pages </li></ul>"
	    				+ "<div class='progress' style='height: 1.2rem; margin-top: 20px; margin-bottom: 20px;'>"
	    				+ "<div class='progress-bar' role='progressbar' style='width:" + percent + "%' aria-valuenow='" + readpages + "' aria-valuemin='0' aria-valuemax='" + numpages + "'></div></div>"
	    				+ "<form name=\"updateCurrentBook\" id=\"updateCurrentBook\">"
	    				+ "<div class=\"form-outline\" style=\"display: inline-flex;\">"
	    				+ "<input type=\"text\" id=\"updateCurrentRead\" name=\"updateCurrentRead\" class=\"form-control form-control-sm\" placeholder=\"I'm on page...\"/></div>"
	    				+ "<div id=\"updateBookError\"></div>"
	    				+ "<button class=\"btn btn-primary\" id=\"updateCurrentBookBtn\">Update Progress</button><br>"
	    				+ "<button class=\"btn btn-primary\" id=\"finishBookBtn\">I finished my book</button>"
	    				+ "</form></div></div></div></div>";

	            resultCurrent +=currentReadItem;
            } else {
            	String currentReadItem = "<img src=\"./images/dogbook.jpg\" class=\"img-fluid\" style=\"border-radius: 1rem;\"></div>"
	    				+ "<div class=\"col-md-8\"><div class=\"card-body\">"
	    				+ "<h5 class=\"card-title\">Currently Reading</h5>"
	    				+ "<p class=\"card-text\">You are currently not reading any book.</p>"
	    				+ "<p class=\"card-text\">Set a book as a currently reading on the My Shelves to track your progress.</p>"
	    				+ "</div></div></div></div>";
            	
            	resultCurrent +=currentReadItem;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        System.out.println(resultCurrent);
        
        return resultCurrent;
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		DisplayHomeCurrentRead newDisplay = new DisplayHomeCurrentRead();
    	
    	ServletContext servletContext = getServletContext();
    	int id = (int) servletContext.getAttribute("userID");
    	System.out.println(id);
    	
    	String currentReadResult = newDisplay.displayCurrentRead(id);
    	System.out.println(currentReadResult);
    	response.setContentType("text/html");
    	response.getWriter().write(currentReadResult);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
