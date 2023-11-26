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

@WebServlet("/AddBook")
public class AddBook extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public AddBook() {
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
    
    public void updateShelf(int id, int shelfid) {
		String updatesql = "UPDATE Shelf SET NumBooks=? WHERE UserID=? AND ShelfID=?";
		String selectsql = "SELECT COUNT(BookID) FROM Book WHERE UserID=? AND ShelfID=?";
		int count = 0;
		
		try (Connection conn = this.connect(); PreparedStatement pstmt  = conn.prepareStatement(selectsql)){
			 
			pstmt.setInt(1, id);
			pstmt.setInt(2, shelfid);
	        ResultSet rs = pstmt.executeQuery();

	        if (rs.next()) {
	            count = rs.getInt(1);
	            System.out.println("count" + count);
	        }
            
	     } catch (SQLException e) {
	            System.out.println(e.getMessage());
	            e.printStackTrace();
	     }
		
		try (Connection conn = this.connect(); PreparedStatement pstmt  = conn.prepareStatement(updatesql)){
            
			pstmt.setInt(1,count);
			pstmt.setInt(2,id);
            pstmt.setInt(3,shelfid);
            
            pstmt.executeUpdate();
         
	     } catch (SQLException e) {
	            System.out.println(e.getMessage());
	            e.printStackTrace();
	     }
    }
    
    public int getShelfID(String name, int id) {
		String sql = "SELECT ShelfID FROM Shelf WHERE ShelfName=? AND UserID=?";
		int shelfid = 0;
		
		try (Connection conn = this.connect(); PreparedStatement pstmt  = conn.prepareStatement(sql)){
	            
	            pstmt.setString(1,name);
	            pstmt.setInt(2,id);
	            
	            ResultSet rs  = pstmt.executeQuery();
	            
	            if(rs.next()) {
	            	shelfid = rs.getInt("ShelfID");
		            System.out.println("ShelfID: " + shelfid);
	            }
	     } catch (SQLException e) {
	            System.out.println(e.getMessage());
	            e.printStackTrace();
	     }
		return shelfid;
	}
    
    public Boolean insert(String title, String author, String numPages, int id, int shelf) {
		String sql = "INSERT INTO Book(Title,Author,NumPages,UserID,ShelfID) VALUES(?,?,?,?,?)";
		Boolean result = false;

        try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
        	int pages =Integer.parseInt(numPages);  
        	pstmt.setString(1, title);
            pstmt.setString(2, author);
            pstmt.setInt(3, pages);
            pstmt.setInt(4, id);
            pstmt.setInt(5, shelf);
            pstmt.executeUpdate();
            
            result = true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return result;
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		AddBook newBook = new AddBook();
    	
    	String title = request.getParameter("bookTitle");
    	String author = request.getParameter("bookAuthor");
    	String pages = request.getParameter("bookNumPages");
    	String shelf = request.getParameter("bookShelf");
    	
    	System.out.println("title: " + title);
    	System.out.println("author: " + author);
    	System.out.println("pages: " + pages);
    	System.out.println("shelf: " + shelf);
		
    	ServletContext servletContext = getServletContext();
    	int id = (int) servletContext.getAttribute("userID");
    	System.out.println(id);
    	
    	int shelfID = newBook.getShelfID(shelf, id);
    	
    	newBook.insert(title, author, pages, id, shelfID);
    	newBook.updateShelf(id,shelfID);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
