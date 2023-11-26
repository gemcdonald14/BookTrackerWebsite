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

@WebServlet("/RateBook") 
public class RateBook extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
    public RateBook() {
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
    
    public Boolean rateBook(int id, String rating) {
		String sql = "UPDATE Book SET Rating=? WHERE UserID=? AND IsCurrentRead=1";
		Boolean result = false;

		try (Connection conn = this.connect(); PreparedStatement pstmt  = conn.prepareStatement(sql)){
	            
				int bookRating =Integer.parseInt(rating); 
				pstmt.setInt(1,bookRating);
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
		RateBook newRating = new RateBook();
		
		ServletContext servletContext = getServletContext();
		int id = (int) servletContext.getAttribute("userID");
    	
		String rating = request.getParameter("bookRating");
		
		System.out.println("book rating: " + rating);
		
		if (newRating.rateBook(id, rating)) {
			System.out.println("book rated");
			response.sendRedirect("homepage.jsp");
		} else {
			PrintWriter writer = response.getWriter();
			writer.println("failed");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
