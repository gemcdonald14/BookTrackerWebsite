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

@WebServlet("/SetCurrentRead") 
public class SetCurrentRead extends HttpServlet {
	private static final long serialVersionUID = 1L;
 
    public SetCurrentRead() {
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
    
    public Boolean setBook(int id, String bookId) {
		String updatesql = "UPDATE Book SET IsCurrentRead=1 WHERE UserID=? AND BookID=?";
		String selectsql = "SELECT COUNT(*) FROM Book WHERE UserID=? AND IsCurrentRead=1";
		Boolean hasCurrentRead = false;
		Boolean result = false;
		
		try (Connection conn = this.connect(); PreparedStatement pstmt  = conn.prepareStatement(selectsql)){
			 
			
			pstmt.setInt(1, id);
	        ResultSet rs = pstmt.executeQuery();

	        if (rs.next()) {
	            int count = rs.getInt(1);
	            System.out.println("count" + count);
	            if (count > 0) {
	            	hasCurrentRead = true;
	            } else if (count == 0) {
	            	hasCurrentRead = false;
	            }
	        }

            
	     } catch (SQLException e) {
	            System.out.println(e.getMessage());
	            e.printStackTrace();
	     }

		try (Connection conn = this.connect(); PreparedStatement pstmt  = conn.prepareStatement(updatesql)){
	            
				int bookid =Integer.parseInt(bookId); 
				pstmt.setInt(1,id);
	            pstmt.setInt(2,bookid);
	            
	            if (hasCurrentRead == false) {
	            	int rowsAffected = pstmt.executeUpdate();
	   	         
		            if(rowsAffected > 0) {
			            result = true;
			        }
	            } 
	            
	     } catch (SQLException e) {
	            System.out.println(e.getMessage());
	            e.printStackTrace();
	     }
		return result;
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		SetCurrentRead newCurrentRead = new SetCurrentRead();
		
		ServletContext servletContext = getServletContext();
		int id = (int) servletContext.getAttribute("userID");
    	
		String bookid = request.getParameter("bookId");
		
		System.out.println("book id: " + bookid);
		
		if (newCurrentRead.setBook(id, bookid)) {
			System.out.println("book set");
		} else {
			response.setContentType("text/html");
	    	response.getWriter().write("Already have a current book");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
