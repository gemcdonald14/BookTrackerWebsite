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

@WebServlet("/HomeDisplay") 
public class HomeDisplay extends HttpServlet {
	private static final long serialVersionUID = 1L;
 
    public HomeDisplay() {
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
    
	
    public String displayShelves(int id) {
    	String sql = "SELECT * FROM Shelf WHERE UserID=? LIMIT 4";
    	String resultShelves = "<div class=\"col-md-6 p-4\" id=\"rightColHome\"><div class=\"card\" style=\"border-radius: 1rem;\">"
    			+ "<div class=\"card-body\"><h5 class=\"card-title\">My Bookshelves</h5>"
				+ "<ul class=\"list-group list-group-flush\">";

        try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
        	pstmt.setInt(1, id);
        	
        	ResultSet rs  = pstmt.executeQuery();
            
            while(rs.next()) {
            	String name = rs.getString("ShelfName");
	            System.out.println("Name: " + name);
	            
	            int books = rs.getInt("NumBooks");
	            System.out.println("Books: " + books);
	            
	            String shelfListItem = "<li class=\"list-group-item\"><div class=\"listItemHome\" style=\"display: inline-flex;\">"
	            					+ "<div class=\"listImgHome\"><img src=\"cat.jpg\" class=\"img-fluid\" style=\"border-radius: 1rem;  width: 100px; height: 125px;\"></div>"
	            					+ "<div class=\"listTitleHome\"><p>" + name + "</p></div>"
	            					+ "<div class=\"listNumBooksHome\"><p>" +  books + " books</p></div></div></li>";
	            
	            resultShelves +=shelfListItem;
            }
            
            resultShelves += "</ul></div></div></div>";
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        System.out.println(resultShelves);
        
        return resultShelves;
    }
    
    public String displayCurrentRead(int id) {
    	String sql = "SELECT * FROM Book WHERE UserID=? AND IsCurrentRead=1 LIMIT 1";
    	String resultCurrent = "<div class=\"row\" id=\"progressRow\"><div class=\"card\" style=\"border-radius: 1rem;\">"
				+ " <div class=\"row g-0\"><div class=\"col-md-4\">";
    	
        try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
        	pstmt.setInt(1, id);
        	
        	ResultSet rs  = pstmt.executeQuery();
            
            while(rs.next()) {
            	String title = rs.getString("Title");
	            System.out.println("Title: " + title);
	            
	            String author = rs.getString("Author");
	            System.out.println("Author: " + author);
	            
	            int numpages = rs.getInt("NumPages");
	            System.out.println("Number of Pages: " + numpages);
	            
	            int readpages = rs.getInt("ReadPages");
	            System.out.println("Read Pages: " + readpages);
	            
	            int percent = (int)  (int) ((readpages * 100.0) / numpages);
	            System.out.println(percent);
	            
	            String currentReadItem = "<img src=\"dark-bookshelf.jpg\" class=\"img-fluid\" style=\"border-radius: 1rem;\"></div>"
	    				+ "<div class=\"col-md-8\"> <div class=\"card-body\">"
	    				+ "<h5 class=\"card-title\">Currently Reading</h5>"
	    				+ "<p class=\"card-text\">" + title + "</p>"
	    				+ "<p class=\"card-text\">" + author + "</p>"
	    				+ "<div class='progress' style='height: 0.8rem;'>"
	    				+ "<div class='progress-bar' role='progressbar' style='width:" + percent + "%' aria-valuenow='" + readpages + "' aria-valuemin='0' aria-valuemax='" + numpages + "'></div></div>"
	    				+ "<p class=\"card-text\"><small class=\"text-muted\">Started reading on 4/23/20</small></p>"
	    				+ "<form name=\"updateCurrentBook\" method=\"post\" action=\"UpdateCurrentRead\">"
	    				+ "<div class=\"form-outline\" style=\"display: inline-flex;\">"
	    				+ "<input type=\"text\" id=\"updateCurrentRead\" name=\"updateCurrentRead\" class=\"form-control form-control-md\" placeholder=\"I'm on page...\"/></div>"
	    				+ "<button class=\"btn btn-primary\" id=\"updateCurrentBookBtn\">Update Progress</button>"
	    				+ "<button class=\"btn btn-primary\" id=\"finishBookBtn\">Finished Book!</button>"
	    				+ "</div></div></div></div></div></div>";

	            resultCurrent +=currentReadItem;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        System.out.println(resultCurrent);
        
        return resultCurrent;
    }
    
    public String displayGoals(int id) {
    	String monthsql = "SELECT * FROM Goal WHERE UserID=? AND GoalType=1 LIMIT 1";
    	String yearsql = "SELECT * FROM Goal WHERE UserID=? AND GoalType=2 LIMIT 1";
    	
    	String finalResult = "<div class=\"col-md-6 p-4\" id=\"leftColHome\">";
    	
    	String monthResult = "<div class=\"row mb-4\" id=\"goalMonthRow\"><div class=\"card\" style=\"border-radius: 1rem;\">"
    						+ "<div class=\"card-body\">";
    	
    	String yearResult = "<div class=\"row mb-4\" id=\"goalYearRow\"><div class=\"card\" style=\"border-radius: 1rem;\">"
    						+ "<div class=\"card-body\">";
    	
    	String goalListItem = "";
    	
    	//String progressRowAndFinal = "";

        try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(monthsql)) {
        	pstmt.setInt(1, id);
        	
        	ResultSet rs  = pstmt.executeQuery();
            
            while(rs.next()) {
            	String name = rs.getString("GoalName");
	            System.out.println("Name: " + name);
	            
	            String type = rs.getString("GoalType");
	            System.out.println("Type: " + type);
	            
	            int target = rs.getInt("TargetBooks");
	            System.out.println("Target: " + target);
	            
	            int completed = rs.getInt("CompletedBooks");
	            System.out.println("Completed: " + completed);
	            
	            int percent = (int) ((completed * 100.0) / target);
	            System.out.println(percent);

	            goalListItem = "<h5 class=\"card-title\">" + name + "</h5><div class='progress' style='height: 1rem;'>"
		    				+ "<div class=\"progress-bar\" role=\"progressbar\" style='width:" + percent + "%' aria-valuenow='" + completed + "' aria-valuemin=\"0\" aria-valuemax='" + target + "'></div>"
		    				+ "</div></div></div>";
	       

	            monthResult += goalListItem;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
        try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(yearsql)) {
        	pstmt.setInt(1, id);
        	
        	ResultSet rs  = pstmt.executeQuery();
            
            while(rs.next()) {
            	String name = rs.getString("GoalName");
	            System.out.println("Name: " + name);
	            
	            String type = rs.getString("GoalType");
	            System.out.println("Type: " + type);
	            
	            int target = rs.getInt("TargetBooks");
	            System.out.println("Target: " + target);
	            
	            int completed = rs.getInt("CompletedBooks");
	            System.out.println("Completed: " + completed);
	            
	            int percent = (int) ((completed * 100.0) / target);
	            System.out.println(percent);
	            
	            goalListItem = "<h5 class=\"card-title\">" + name + "</h5><div class='progress' style='height: 1rem;'>"
	    				+ "<div class=\"progress-bar\" role=\"progressbar\" style='width:" + percent + "%' aria-valuenow='" + completed + "' aria-valuemin=\"0\" aria-valuemax='" + target + "'></div>"
	    				+ "</div></div></div>";
	            	
	            yearResult += goalListItem;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
        finalResult = monthResult + yearResult;
        
        //finalResult += progressRowAndFinal;
        
        return finalResult;
    }
    
    
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	HomeDisplay newDisplay = new HomeDisplay();
    	
    	ServletContext servletContext = getServletContext();
    	int id = (int) servletContext.getAttribute("userID");
    	System.out.println(id);
    	
    	String shelfResult = newDisplay.displayShelves(id);
    	String goalResult = newDisplay.displayGoals(id);
    	String currentReadResult = newDisplay.displayCurrentRead(id);
    	String finalResult = goalResult + currentReadResult + shelfResult;
    	
    	System.out.println(shelfResult);
    	System.out.println(goalResult);
    	System.out.println(currentReadResult);
    	System.out.println(finalResult);
    	response.setContentType("text/html");
    	response.getWriter().write(finalResult);
    	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
