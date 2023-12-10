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
    
    public void insertFinishedBook(int id, int shelfid) {
		String selectsql = "SELECT Title,Author,NumPages FROM Book WHERE UserID=? AND IsCurrentRead=1";
		String insertsql = "INSERT INTO Book(Title,Author,NumPages,UserID,ShelfID,ReadPages,IsCurrentRead) VALUES(?,?,?,?,?,?,?)";
		
		String author = "";
		String title = "";
		int pages = 0;

        try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(selectsql)) {
        	
        	pstmt.setInt(1, id);
            
            ResultSet rs = pstmt.executeQuery();
            
            if(rs.next()) {
            	author = rs.getString("Author");
            	pages = rs.getInt("NumPages");
            	title = rs.getString("Title");       
            	System.out.println("title: " + title);
            	System.out.println("author: " + author);
            	System.out.println("pages: " + pages);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
        try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(insertsql)) {
        	
        	pstmt.setString(1, title);
        	pstmt.setString(2, author);
        	pstmt.setInt(3, pages);
        	pstmt.setInt(4, id);
        	pstmt.setInt(5, shelfid);
        	pstmt.setInt(6, pages);
        	pstmt.setInt(7, 1);
        	pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
	}
    
    public Boolean rateBook(int id, String rating, String genre, String mood, String pace) {
		String sql = "UPDATE Book SET Rating=?, Genre=?, Mood=?, Pace=? WHERE UserID=? AND IsCurrentRead=1";
		Boolean result = false;

		try (Connection conn = this.connect(); PreparedStatement pstmt  = conn.prepareStatement(sql)){
	            
				int bookRating =Integer.parseInt(rating); 
				int bookGenre =Integer.parseInt(genre); 
				int bookMood =Integer.parseInt(mood); 
				int bookPace =Integer.parseInt(pace); 
				
				pstmt.setInt(1,bookRating);
				pstmt.setInt(2,bookGenre);
				pstmt.setInt(3,bookMood);
				pstmt.setInt(4,bookPace);
	            pstmt.setInt(5,id);
	            
	            System.out.println("book rated");
	            
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

    public Boolean finishBook(int id) {
		String finishsql = "UPDATE Book SET ReadPages=NumPages, IsCurrentRead=0 WHERE UserID=? AND IsCurrentRead=1";
		//String finishsql = "UPDATE Book SET ReadPages=NumPages, IsCurrentRead=0, ShelfID=? WHERE UserID=? AND IsCurrentRead=1";
		//String shelfsql = "UPDATE Book SET ShelfID=? WHERE UserID=? AND IsCurrentRead=1";
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
		 
		return result;

    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RateBook newRating = new RateBook();
		
		ServletContext servletContext = getServletContext();
		int id = (int) servletContext.getAttribute("userID");
		
		int shelfid = newRating.getShelf(id); //get shelf id 
		newRating.insertFinishedBook(id, shelfid); //insert book onto read shelf
		
		String rating = request.getParameter("bookRating");
		String genre = request.getParameter("bookGenre");
		String mood = request.getParameter("bookMood");
		String pace = request.getParameter("bookPace");
		System.out.println("book rating: " + rating);
		System.out.println("book genre: " + genre);
		System.out.println("book mood: " + mood);
		System.out.println("book pace: " + pace);
		
		if (rating == "") {
			rating = "0";
		}
		
		newRating.rateBook(id, rating, genre, mood, pace); //rate book
		newRating.finishBook(id); //finish book, update info & make not current read
		newRating.updateShelf(id,shelfid); //update the numbooks for shelf 
		
		String result = "<div class=\"card\" style=\"border-radius: 1rem;\">"
				+ "<div class=\"row g-0\"><div class=\"col-md-4 d-none d-lg-block\">"
				+ "<img src=\"./images/dogbook.jpg\" class=\"img-fluid\" style=\"border-radius: 1rem;\"></div>"
				+ "<div class=\"col-md-8\"> <div class=\"card-body\">"
				+ "<h5 class=\"card-title\">Currently Reading</h5>"
				+ "<p class=\"card-text\">You are currently not reading any book.</p>"
				+ "<p class=\"card-text\">Set a book as a current book to track your progress.</p>"
				+ "</div></div></div></div>";
		
		response.getWriter().write(result);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
