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

@WebServlet("/DisplayHomeShelves") 
public class DisplayHomeShelves extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public DisplayHomeShelves() {
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
    
    public String displayShelves(int id) {
    	String sql = "SELECT * FROM Shelf WHERE UserID=? LIMIT 4";
    	String resultShelves = "<div class=\"card\" style=\"border-radius: 1rem;\">"
    			+ "<div class=\"card-body\"><h5 class=\"card-title\">My Bookshelves</h5>"
				+ "<ul class=\"list-group list-group-flush\">";

        try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
        	pstmt.setInt(1, id);
        	
        	ResultSet rs  = pstmt.executeQuery();
            
            while(rs.next()) {
            	String name = rs.getString("ShelfName");
	            System.out.println("Name: " + name);
	            
	            int books = rs.getInt("NumBooks");
	            System.out.println("Books: " + books);
	            
	            String shelfListItem = "<li class=\"list-group-item\"><div class=\"listItemHome\" style=\"display: inline-flex;\">"
	            					+ "<div class=\"listImgHome\"><img src=\"cat.jpg\" class=\"img-fluid\" style=\"border-radius: 1rem;  width: 100px; height: 125px;\"></div>"
	            					+ "<div class=\"listTitleHome\"><p>" + name + "</p></div>"
	            					+ "<div class=\"listNumBooksHome\"><p>" +  books + " books</p></div></div></li>";
	            
	            resultShelves +=shelfListItem;
            }
            
            resultShelves += "</ul></div></div>";
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        System.out.println(resultShelves);
        
        return resultShelves;
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		DisplayHomeShelves newDisplay = new DisplayHomeShelves();
    	
    	ServletContext servletContext = getServletContext();
    	int id = (int) servletContext.getAttribute("userID");
    	System.out.println(id);
    	
    	String shelfResult = newDisplay.displayShelves(id);
    	
    	System.out.println(shelfResult);
    	response.setContentType("text/html");
    	response.getWriter().write(shelfResult);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
