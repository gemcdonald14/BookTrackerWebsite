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

@WebServlet("/DisplayReadingStats") 
public class DisplayReadingStats extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public DisplayReadingStats() {
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
    
    public void init(ServletConfig config) throws ServletException {
		super.init(config);
		int readShelf = 0;
		getServletContext().setAttribute("readShelfID", readShelf);
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
    
    public String displayStats(int id, int shelfid) {
    	String readbookssql = "SELECT NumBooks FROM Shelf WHERE UserID=? AND ShelfID=?";
    	String totalbookssql = "SELECT SUM(NumBooks) FROM Shelf WHERE UserID=?";
    	String totalpagessql = "SELECT SUM(NumPages) FROM Book WHERE UserID=? AND ShelfID=?";
    	int readBooks = 0;
    	int totalBooks = 0;
    	int totalPages = 0;
    	String result = "";
		
    	//get read books 
		try (Connection conn = this.connect(); PreparedStatement pstmt  = conn.prepareStatement(readbookssql)){
			 
			pstmt.setInt(1, id);
			pstmt.setInt(2, shelfid);
	        ResultSet rs = pstmt.executeQuery();

	        if (rs.next()) {
	            readBooks = rs.getInt("NumBooks");
	            System.out.println("Read Books: " + readBooks);
	        }
	     } catch (SQLException e) {
	            System.out.println(e.getMessage());
	            e.printStackTrace();
	     }
		
		
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
		
		//get total read pages books 
		try (Connection conn = this.connect(); PreparedStatement pstmt  = conn.prepareStatement(totalpagessql)){
			 
			pstmt.setInt(1, id);
			pstmt.setInt(2, shelfid);
	        ResultSet rs = pstmt.executeQuery();

	        if (rs.next()) {
	        	totalPages = rs.getInt(1);
	            System.out.println("Total Pages: " + totalPages);
	        }
	     } catch (SQLException e) {
	            System.out.println(e.getMessage());
	            e.printStackTrace();
	     }

		result = "<ul><li>You have read " + readBooks + " books</li>"
				+ "<li>You have read " + totalPages + " pages</li>"
				+ "<li>You have " + totalBooks + " books on shelves in your library</li></ul>";
		
		return result;
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		DisplayReadingStats newStats = new DisplayReadingStats();
    	
    	ServletContext servletContext = getServletContext();
    	int id = (int) servletContext.getAttribute("userID");
    	System.out.println(id);
    	
    	
    	int shelfid = newStats.getShelf(id);
    	
    	String statsResult = newStats.displayStats(id, shelfid);
    	
    	System.out.println(statsResult);
    	response.setContentType("text/html");
    	response.getWriter().write(statsResult);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}

}
