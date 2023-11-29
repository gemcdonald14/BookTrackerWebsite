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

@WebServlet("/GetPageCountStats")
public class GetPageCountStats extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public GetPageCountStats() {
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
    
    public String displayStats(int id, int shelfid) {
    	String smallsql = "SELECT COUNT(BookID) FROM Book WHERE UserID=? AND ShelfID=? AND NumPages <= 299";
    	String medsql = "SELECT COUNT(BookID) FROM Book WHERE UserID=? AND ShelfID=? AND NumPages BETWEEN 300 AND 499";
    	String largesql = "SELECT COUNT(BookID) FROM Book WHERE UserID=? AND ShelfID=? AND NumPages >= 500";
    	int small = 0;
    	int med = 0;
    	int large = 0;
    	String result = "";
		
		try (Connection conn = this.connect(); PreparedStatement pstmt  = conn.prepareStatement(smallsql)){
			 
			pstmt.setInt(1, id);
			pstmt.setInt(2, shelfid);
	        ResultSet rs = pstmt.executeQuery();

	        if (rs.next()) {
	            small = rs.getInt(1);
	            System.out.println("small: " + small);
	        }
	     } catch (SQLException e) {
	            System.out.println(e.getMessage());
	            e.printStackTrace();
	     }
	
		try (Connection conn = this.connect(); PreparedStatement pstmt  = conn.prepareStatement(medsql)){
			 
			pstmt.setInt(1, id);
			pstmt.setInt(2, shelfid);
	        ResultSet rs = pstmt.executeQuery();

	        if (rs.next()) {
	            med = rs.getInt(1);
	            System.out.println("med: " + med);
	        }
	     } catch (SQLException e) {
	            System.out.println(e.getMessage());
	            e.printStackTrace();
	     }
		
		try (Connection conn = this.connect(); PreparedStatement pstmt  = conn.prepareStatement(largesql)){
			 
			pstmt.setInt(1, id);
			pstmt.setInt(2, shelfid);
	        ResultSet rs = pstmt.executeQuery();

	        if (rs.next()) {
	            large = rs.getInt(1);
	            System.out.println("large: " + large);
	        }
	     } catch (SQLException e) {
	            System.out.println(e.getMessage());
	            e.printStackTrace();
	     }
	
		
		result = small + "/" + med + "/" + large;
		
		return result;
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		GetPageCountStats newStats = new GetPageCountStats();
    	
    	ServletContext servletContext = getServletContext();
    	int id = (int) servletContext.getAttribute("userID");
    	System.out.println(id);
    	
    	int shelfid = newStats.getShelf(id);
    	String statsResult = newStats.displayStats(id, shelfid);
    	System.out.println(statsResult);
    	response.getWriter().write(statsResult);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
