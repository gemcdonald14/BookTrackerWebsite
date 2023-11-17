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

@WebServlet("/AddShelf")
public class AddShelf extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public AddShelf() {
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
	
	public Boolean insert(String name, int id) {
		String sql = "INSERT INTO Shelf(ShelfName,UserID) VALUES(?,?)";
		Boolean result = false;

        try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setInt(2, id);
            pstmt.executeUpdate();
            
            result = true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return result;
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		AddShelf newShelf = new AddShelf();
    	
    	String name = request.getParameter("newShelfName");
		
    	ServletContext servletContext = getServletContext();
    	int id = (int) servletContext.getAttribute("userID");
    	System.out.println(id);
    	System.out.println("shelf name: " + name);
    	
    	String newShelfListItem = "<li class=\"list-group-item d-flex justify-content-between align-items-center\">" 
    							+ "<div class=\"listItemShelf\" style=\"display: inline-flex;\">" 
    							+ " <div class=\"d-flex align-items-center\">"
    							+ "<div class=\"listImgShelf\">"
    							+ "<img src=\"colors.jpg\" style=\"border-radius: 1rem;  width: 100px; height: 150px;\">"
    							+ "</div>"
    							+ "<div class=\"listTitleShelf\"><p>" + name + "</p></div>"
    							+ "<div class=\"listNumBooksShelf\"><p>0 books</p></div>"
    							+ " </div></div></li>";
    			
    	if (newShelf.insert(name, id)) {
			System.out.println("return true");
			System.out.println(newShelfListItem);
			response.setContentType("text/html");
			response.getWriter().write(newShelfListItem);
		} else {
				response.getWriter().write("failed");
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
