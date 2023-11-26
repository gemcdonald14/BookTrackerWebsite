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

@WebServlet("/FinishBook") 
public class FinishBook extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public FinishBook() {
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
    	String sql = "SELECT ShelfID FROM Shelf WHERE UserID=? AND ShelfName=Read";
    	
    	try (Connection conn = this.connect(); PreparedStatement pstmt  = conn.prepareStatement(sql)){
            
			pstmt.setInt(1,id);
            
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
    
    public Boolean finishBook(int id, int shelfid) {
		String finishsql = "UPDATE Book SET ReadPages=NumPages, IsCurrentRead=0 WHERE UserID=? AND IsCurrentRead=1";
		String shelfsql = "UPDATE Book SET ShelfID=? WHERE UserID=? AND ShelfName=Read";
		Boolean result = false;

		try (Connection conn = this.connect(); PreparedStatement pstmt  = conn.prepareStatement(finishsql)){
	            
				pstmt.setInt(1,id);
	            
	            int rowsAffected = pstmt.executeUpdate();
	         
	            if(rowsAffected > 0) {
		            result = true;
		        }
	     } catch (SQLException e) {
	            System.out.println(e.getMessage());
	            e.printStackTrace();
	     }
		 try (Connection conn = this.connect(); PreparedStatement pstmt  = conn.prepareStatement(shelfsql)){
            
			pstmt.setInt(1,shelfid);
			pstmt.setInt(2,id);
            
            int rowsAffected = pstmt.executeUpdate();
         
            if(rowsAffected > 0) {
	            result = true;
	        }
	     } catch (SQLException e) {
	            System.out.println(e.getMessage());
	            e.printStackTrace();
	     }
		 
		return result;
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		FinishBook finishedBook = new FinishBook();
		
		ServletContext servletContext = getServletContext();
		int id = (int) servletContext.getAttribute("userID");
		
		String rating = "<form name=\"rateBookForm\">"
						+ "<div class=\"form-outline\" style=\"display: inline-flex;\">"
						+ "<input type=\"text\" id=\"bookRating\" class=\"form-control form-control-sm\" placeholder=\"Rating (0 stars - 5 stars)\"/>"
						+ "</div>"
						+ "<button class=\"btn\" id=\"rateBook\">Rate Book</button>"
						+ "</form>";
		int shelfid = finishedBook.getShelf(id);
		
		if (finishedBook.finishBook(id, shelfid)) {
			System.out.println("book finshed");
			response.setContentType("text/html");
	    	response.getWriter().write(rating);
		} else {
			PrintWriter writer = response.getWriter();
			writer.println("failed");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
