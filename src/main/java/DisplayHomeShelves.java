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
    
    public int getShelf(int id) {
    	int shelfid = 0;
    	String shelfname = "Read";
    	String sql = "SELECT ShelfID FROM Shelf WHERE UserID=? AND ShelfName=?";
    	
    	try (Connection conn = this.connect(); PreparedStatement pstmt  = conn.prepareStatement(sql)){
            
			pstmt.setInt(1,id);
			pstmt.setString(2,shelfname);
            
			ResultSet rs  = pstmt.executeQuery();
            
            while(rs.next()) {
            	shelfid = rs.getInt("ShelfID");
	            System.out.println("shelf id: " + shelfid);
            }
         
	     } catch (SQLException e) {
	            System.out.println(e.getMessage());
	            e.printStackTrace();
	     }
    	return shelfid;
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
	            
	            int shelf = rs.getInt("ShelfID");
	            System.out.println("Shelf: " + shelf);
	            
	            String shelfListItem = "<li class=\"list-group-item d-flex justify-content-between align-items-center\">"
	            					+ "<div class=\"listItemHome\" style=\"display: inline-flex;\">"
	            					+ "<div class=\"d-flex align-items-center\">"
	            					+ "<div class=\"listImgHome\"><img name=\'" + shelf + "\' src='DisplayShelfPic?shelfId=" + shelf + "\' class='shelfPic' style=\"border-radius: 1rem;  width: 100px; height: 150px;\"></div>"
	            					+ "<div class=\"listTitleHome\">" + name + "</div>"
	            					+ "<div class=\"listNumBooksHome\">" +  books + " books</div></div></div></li>";
	            
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
    	
    	int shelfid = newDisplay.getShelf(id);
    	servletContext.getAttribute("readShelfID");
    	servletContext.setAttribute("readShelfID", shelfid);
    	
    	String shelfResult = newDisplay.displayShelves(id);
    	
    	System.out.println(shelfResult);
    	response.setContentType("text/html");
    	response.getWriter().write(shelfResult);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
