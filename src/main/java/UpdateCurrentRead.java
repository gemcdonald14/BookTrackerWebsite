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

@WebServlet("/UpdateCurrentRead") 
public class UpdateCurrentRead extends HttpServlet {
	private static final long serialVersionUID = 1L;
  
    public UpdateCurrentRead() {
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
    
    public Boolean updateBook(int id, String pages) {
		String sql = "UPDATE Book SET ReadPages=? WHERE UserID=? AND IsCurrentRead=1";
		Boolean result = false;

		try (Connection conn = this.connect(); PreparedStatement pstmt  = conn.prepareStatement(sql)){
	            
				int readPages =Integer.parseInt(pages); 
				pstmt.setInt(1,readPages);
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
		UpdateCurrentRead updateCurrentRead = new UpdateCurrentRead();
		
		ServletContext servletContext = getServletContext();
		int id = (int) servletContext.getAttribute("userID");
    	
		String currentPage = request.getParameter("updateCurrentRead");
		
		if(currentPage.equals("")) {
			currentPage = "0";
		}
		
		System.out.println("current page: " + currentPage);
		
		if (updateCurrentRead.updateBook(id, currentPage)) {
			System.out.println("book updated");
			response.getWriter().write("Updated");
		} else {
			response.getWriter().write("Unable to update current read");
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
