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

@WebServlet("/GetAvgRatingStats")
public class GetAvgRatingStats extends HttpServlet {
	private static final long serialVersionUID = 1L;
 
    public GetAvgRatingStats() {
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
    	String onesql = "SELECT COUNT(BookID) FROM Book WHERE UserID=? AND ShelfID=? AND Rating = 1";
    	String twosql = "SELECT COUNT(BookID) FROM Book WHERE UserID=? AND ShelfID=? AND Rating = 2";
    	String threesql = "SELECT COUNT(BookID) FROM Book WHERE UserID=? AND ShelfID=? AND Rating = 3";
    	String foursql = "SELECT COUNT(BookID) FROM Book WHERE UserID=? AND ShelfID=? AND Rating = 4";
    	String fivesql = "SELECT COUNT(BookID) FROM Book WHERE UserID=? AND ShelfID=? AND Rating = 5";
    	int one = 0;
    	int two = 0;
    	int three = 0;
    	int four = 0;
    	int five = 0;
    	String result = "";
		
		try (Connection conn = this.connect(); PreparedStatement pstmt  = conn.prepareStatement(onesql)){
			 
			pstmt.setInt(1, id);
			pstmt.setInt(2, shelfid);
	        ResultSet rs = pstmt.executeQuery();

	        if (rs.next()) {
	            one = rs.getInt(1);
	            System.out.println("one star: " + one);
	        }
	     } catch (SQLException e) {
	            System.out.println(e.getMessage());
	            e.printStackTrace();
	     }
		
		try (Connection conn = this.connect(); PreparedStatement pstmt  = conn.prepareStatement(twosql)){
			 
			pstmt.setInt(1, id);
			pstmt.setInt(2, shelfid);
	        ResultSet rs = pstmt.executeQuery();

	        if (rs.next()) {
	            two = rs.getInt(1);
	            System.out.println("two star: " + two);
	        }
	     } catch (SQLException e) {
	            System.out.println(e.getMessage());
	            e.printStackTrace();
	     }
		
		try (Connection conn = this.connect(); PreparedStatement pstmt  = conn.prepareStatement(threesql)){
			 
			pstmt.setInt(1, id);
			pstmt.setInt(2, shelfid);
	        ResultSet rs = pstmt.executeQuery();

	        if (rs.next()) {
	            three = rs.getInt(1);
	            System.out.println("three star: " + three);
	        }
	     } catch (SQLException e) {
	            System.out.println(e.getMessage());
	            e.printStackTrace();
	     }
		
		try (Connection conn = this.connect(); PreparedStatement pstmt  = conn.prepareStatement(foursql)){
			 
			pstmt.setInt(1, id);
			pstmt.setInt(2, shelfid);
	        ResultSet rs = pstmt.executeQuery();

	        if (rs.next()) {
	            four = rs.getInt(1);
	            System.out.println("four star: " + four);
	        }
	     } catch (SQLException e) {
	            System.out.println(e.getMessage());
	            e.printStackTrace();
	     }
		
		try (Connection conn = this.connect(); PreparedStatement pstmt  = conn.prepareStatement(fivesql)){
			 
			pstmt.setInt(1, id);
			pstmt.setInt(2, shelfid);
	        ResultSet rs = pstmt.executeQuery();

	        if (rs.next()) {
	            five = rs.getInt(1);
	            System.out.println("five star: " + five);
	        }
	     } catch (SQLException e) {
	            System.out.println(e.getMessage());
	            e.printStackTrace();
	     }

		result = one + "/" + two + "/" + three + "/" + four + "/" + five;
		
		return result;
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		GetAvgRatingStats newStats = new GetAvgRatingStats();
    	
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
