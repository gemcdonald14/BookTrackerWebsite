import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import com.microsoft.sqlserver.jdbc.SQLServerDriver;
import jakarta.servlet.ServletContext;

@WebServlet("DisplayBooks")
public class DisplayBooks extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public DisplayBooks() {
        super();
    }
    
    private Connection connect() {
    	Connection conn = null;
		String url = "jdbc:sqlite:C:/sqlite/db/capstone.db";
		
        try {
            // create a connection to the database
            conn = DriverManager.getConnection(url);
            System.out.println("Connection to SQLite has been established.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }
    
    public int getShelfId(String name, int id) {
    	String sql = "SELECT ShelfID FROM Shelf WHERE UserID=? AND ShelfName=?";
    	int shelfID = 0;
    	
    	try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
        	pstmt.setInt(1, id);
        	pstmt.setString(2, name);
        	
        	ResultSet rs  = pstmt.executeQuery();
            
            while(rs.next()) {
            	shelfID = rs.getInt("ShelfID");
	            System.out.println("shelf id: " + shelfID);
            }
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    	
    	return shelfID;
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletContext servletContext = getServletContext();
    	int id = (int) servletContext.getAttribute("userID");
    	System.out.println(id);
    	
    	//String shelf = request.getParameter("ShelfID");
    	String shelfName = request.getParameter("listTitleShelf");
    	int shelfID = getShelfId(shelfName, id);
    	
    	String sql = "SELECT * FROM Book WHERE UserID=? AND ShelfID=?";
    	String resultList = "<h5 class=\"card-title\">" + shelfName + "</h5>"
    					+ "<ul class=\"list-group list-group-flush\">";

        try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
        	pstmt.setInt(1, id);
        	pstmt.setInt(2, shelfID);
        	
        	ResultSet rs  = pstmt.executeQuery();
            
            while(rs.next()) {
            	String title = rs.getString("Title");
	            System.out.println("Title: " + title);
	            
	            String author = rs.getString("Author");
	            System.out.println("Author: " + author);
	            
	            int rating = rs.getInt("Rating");
	            System.out.println("Rating: " + rating);
	            
	            int bookid = rs.getInt("BookID");
	            System.out.println("BookID: " + bookid);

	            String bookListItem = "<li class=\"list-group-item d-flex justify-content-between align-items-center\" id=" + bookid + ">"
	            					+ "<div class=\"listItemDisplayShelf\"style=\"display: inline-flex; justify-content: space-between; align-items: center; width: 100%;\">"
	            					+ "<div class=\"d-flex align-items-center\">"
	            					+ "<div class=\"listBookImgDisplayShelf\">"
	            					+ "<img src=\"colors.jpg\" style=\"border-radius: 1rem;  width: 50px; height: 75px;\">"
	            					+ "</div>"
	            					+ "<div class=\"listBookTitleDisplayShelf\"><p>" + title + "</p></div>"
	            					+ "<div class=\"listBookAuthorDisplayShelf\"><p>" + author + "</p></div>"
	            					+ "<div class=\"listBookRatingDisplayShelf\"><p>" + rating + "&#9734;</p></div>"
	            					+ "<button class=\"btn btn-md btn-block setAsCurrentBook\">Currently<br>Reading</button>"
	            					+ "<div class=\"error\"></div>"
	            					+ "</div></div></li></ul>";

	            resultList +=bookListItem;
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
