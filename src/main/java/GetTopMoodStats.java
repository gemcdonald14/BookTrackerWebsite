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

@WebServlet("/GetTopMoodStats")
public class GetTopMoodStats extends HttpServlet {
	private static final long serialVersionUID = 1L;
 
    public GetTopMoodStats() {
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
    	String sql = "SELECT COUNT(BookID) FROM Book WHERE UserID=? AND ShelfID=? AND Mood=?";
    	int one = 0, two = 0, three = 0, four = 0, five = 0, six = 0, seven = 0;
    	int eight = 0, nine = 0, ten = 0, eleven = 0, twelve = 0, thirteen = 0, fourteen = 0;
    	String result = "";
		
		try (Connection conn = this.connect(); PreparedStatement pstmt  = conn.prepareStatement(sql)){
			 
			pstmt.setInt(1, id);
			pstmt.setInt(2, shelfid);
			pstmt.setInt(3, 1);
	        ResultSet rs = pstmt.executeQuery();

	        if (rs.next()) {
	            one = rs.getInt(1);
	            System.out.println("one: " + one);
	        }
	     } catch (SQLException e) {
	            System.out.println(e.getMessage());
	            e.printStackTrace();
	     }
		
		try (Connection conn = this.connect(); PreparedStatement pstmt  = conn.prepareStatement(sql)){
			 
			pstmt.setInt(1, id);
			pstmt.setInt(2, shelfid);
			pstmt.setInt(3, 2);
	        ResultSet rs = pstmt.executeQuery();

	        if (rs.next()) {
	            two = rs.getInt(1);
	            System.out.println("two: " + two);
	        }
	     } catch (SQLException e) {
	            System.out.println(e.getMessage());
	            e.printStackTrace();
	     }
		
		try (Connection conn = this.connect(); PreparedStatement pstmt  = conn.prepareStatement(sql)){
			 
			pstmt.setInt(1, id);
			pstmt.setInt(2, shelfid);
			pstmt.setInt(3, 3);
	        ResultSet rs = pstmt.executeQuery();

	        if (rs.next()) {
	            three = rs.getInt(1);
	            System.out.println("three: " + three);
	        }
	     } catch (SQLException e) {
	            System.out.println(e.getMessage());
	            e.printStackTrace();
	     }
		
		try (Connection conn = this.connect(); PreparedStatement pstmt  = conn.prepareStatement(sql)){
			 
			pstmt.setInt(1, id);
			pstmt.setInt(2, shelfid);
			pstmt.setInt(3, 4);
	        ResultSet rs = pstmt.executeQuery();

	        if (rs.next()) {
	            four = rs.getInt(1);
	            System.out.println("four: " + four);
	        }
	     } catch (SQLException e) {
	            System.out.println(e.getMessage());
	            e.printStackTrace();
	     }
		
		try (Connection conn = this.connect(); PreparedStatement pstmt  = conn.prepareStatement(sql)){
			 
			pstmt.setInt(1, id);
			pstmt.setInt(2, shelfid);
			pstmt.setInt(3, 5);
	        ResultSet rs = pstmt.executeQuery();

	        if (rs.next()) {
	            five = rs.getInt(1);
	            System.out.println("five: " + five);
	        }
	     } catch (SQLException e) {
	            System.out.println(e.getMessage());
	            e.printStackTrace();
	     }
		
		try (Connection conn = this.connect(); PreparedStatement pstmt  = conn.prepareStatement(sql)){
			 
			pstmt.setInt(1, id);
			pstmt.setInt(2, shelfid);
			pstmt.setInt(3, 6);
	        ResultSet rs = pstmt.executeQuery();

	        if (rs.next()) {
	            six = rs.getInt(1);
	            System.out.println("six: " + six);
	        }
	     } catch (SQLException e) {
	            System.out.println(e.getMessage());
	            e.printStackTrace();
	     }
		
		try (Connection conn = this.connect(); PreparedStatement pstmt  = conn.prepareStatement(sql)){
			 
			pstmt.setInt(1, id);
			pstmt.setInt(2, shelfid);
			pstmt.setInt(3, 7);
	        ResultSet rs = pstmt.executeQuery();

	        if (rs.next()) {
	            seven = rs.getInt(1);
	            System.out.println("seven: " + seven);
	        }
	     } catch (SQLException e) {
	            System.out.println(e.getMessage());
	            e.printStackTrace();
	     }
		
		try (Connection conn = this.connect(); PreparedStatement pstmt  = conn.prepareStatement(sql)){
			 
			pstmt.setInt(1, id);
			pstmt.setInt(2, shelfid);
			pstmt.setInt(3, 8);
	        ResultSet rs = pstmt.executeQuery();

	        if (rs.next()) {
	            eight = rs.getInt(1);
	            System.out.println("eight: " + eight);
	        }
	     } catch (SQLException e) {
	            System.out.println(e.getMessage());
	            e.printStackTrace();
	     }
		
		try (Connection conn = this.connect(); PreparedStatement pstmt  = conn.prepareStatement(sql)){
			 
			pstmt.setInt(1, id);
			pstmt.setInt(2, shelfid);
			pstmt.setInt(3, 9);
	        ResultSet rs = pstmt.executeQuery();

	        if (rs.next()) {
	            nine = rs.getInt(1);
	            System.out.println("nine: " + nine);
	        }
	     } catch (SQLException e) {
	            System.out.println(e.getMessage());
	            e.printStackTrace();
	     }
		
		try (Connection conn = this.connect(); PreparedStatement pstmt  = conn.prepareStatement(sql)){
			 
			pstmt.setInt(1, id);
			pstmt.setInt(2, shelfid);
			pstmt.setInt(3, 10);
	        ResultSet rs = pstmt.executeQuery();

	        if (rs.next()) {
	            ten = rs.getInt(1);
	            System.out.println("ten: " + ten);
	        }
	     } catch (SQLException e) {
	            System.out.println(e.getMessage());
	            e.printStackTrace();
	     }
		
		try (Connection conn = this.connect(); PreparedStatement pstmt  = conn.prepareStatement(sql)){
			 
			pstmt.setInt(1, id);
			pstmt.setInt(2, shelfid);
			pstmt.setInt(3, 11);
	        ResultSet rs = pstmt.executeQuery();

	        if (rs.next()) {
	            eleven = rs.getInt(1);
	            System.out.println("eleven: " + eleven);
	        }
	     } catch (SQLException e) {
	            System.out.println(e.getMessage());
	            e.printStackTrace();
	     }
		
		try (Connection conn = this.connect(); PreparedStatement pstmt  = conn.prepareStatement(sql)){
			 
			pstmt.setInt(1, id);
			pstmt.setInt(2, shelfid);
			pstmt.setInt(3, 12);
	        ResultSet rs = pstmt.executeQuery();

	        if (rs.next()) {
	            twelve = rs.getInt(1);
	            System.out.println("twelve: " + twelve);
	        }
	     } catch (SQLException e) {
	            System.out.println(e.getMessage());
	            e.printStackTrace();
	     }
		
		try (Connection conn = this.connect(); PreparedStatement pstmt  = conn.prepareStatement(sql)){
			 
			pstmt.setInt(1, id);
			pstmt.setInt(2, shelfid);
			pstmt.setInt(3, 13);
	        ResultSet rs = pstmt.executeQuery();

	        if (rs.next()) {
	            thirteen = rs.getInt(1);
	            System.out.println("thirteen: " + thirteen);
	        }
	     } catch (SQLException e) {
	            System.out.println(e.getMessage());
	            e.printStackTrace();
	     }
		
		try (Connection conn = this.connect(); PreparedStatement pstmt  = conn.prepareStatement(sql)){
			 
			pstmt.setInt(1, id);
			pstmt.setInt(2, shelfid);
			pstmt.setInt(3, 14);
	        ResultSet rs = pstmt.executeQuery();

	        if (rs.next()) {
	            fourteen = rs.getInt(1);
	            System.out.println("fourteen: " + fourteen);
	        }
	     } catch (SQLException e) {
	            System.out.println(e.getMessage());
	            e.printStackTrace();
	     }

		
		result = one + "/" + two + "/" + three + "/" + four + "/" + five + "/" + six + "/" + seven + "/" + eight
				+ "/" + nine + "/" + ten + "/" + eleven + "/" + twelve + "/" + thirteen + "/" + fourteen;
		
		return result;
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		GetTopMoodStats newStats = new GetTopMoodStats();
    	
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
