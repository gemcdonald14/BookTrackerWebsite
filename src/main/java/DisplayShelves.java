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

@WebServlet("/DisplayShelves")
public class DisplayShelves extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public DisplayShelves() {
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

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletContext servletContext = getServletContext();
    	int id = (int) servletContext.getAttribute("userID");
    	System.out.println(id);
    	
    	String sql = "SELECT * FROM Shelf WHERE UserID=?";
    	String resultList = "";

        try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
        	pstmt.setInt(1, id);
        	
        	ResultSet rs  = pstmt.executeQuery();
            
            while(rs.next()) {
            	String name = rs.getString("ShelfName");
	            System.out.println("Name: " + name);
	            
	            String shelf = rs.getString("ShelfID");
	            System.out.println("ID: " + shelf);
	            
	            int books = rs.getInt("NumBooks");
	            System.out.println("Books: " + books);
	            
	            String shelfListItem = "<li class=\"list-group-item d-flex justify-content-between align-items-center\" id=" + shelf + ">"
						+ "<div class=\"listItemShelf\" style=\"display: inline-flex;\">"
						+ "<div class=\"d-flex align-items-center\">"
						+ "<div class=\"listImgShelf\">"
						+"<img name=\'" + shelf + "\' src='DisplayShelfPic?shelfId=" + shelf + "\' class='shelfPic' style=\"border-radius: 1rem;  width: 100px; height: 150px;\"></div>"
						+ "<div class=\"listTitleShelf\">" + name + "</div>"
						+ "<div class=\"listNumBooksShelf\">" + books + " books</div>"
						+ "</div></div></li>";
	            
	            resultList +=shelfListItem;
            }
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        System.out.println(resultList);
    	response.setContentType("text/html");
    	response.getWriter().write(resultList);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
