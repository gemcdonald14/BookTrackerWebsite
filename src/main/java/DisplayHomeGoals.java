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

@WebServlet("/DisplayHomeGoals") 
public class DisplayHomeGoals extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public DisplayHomeGoals() {
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
    
    public String displayGoals(int id) {
    	String monthsql = "SELECT * FROM Goal WHERE UserID=? AND GoalType=1 LIMIT 1";
    	String yearsql = "SELECT * FROM Goal WHERE UserID=? AND GoalType=2 LIMIT 1";
    	String finalResult = "";
    	String monthResult = "<div class=\"row mb-4\" id=\"goalMonthRow\"><div class=\"card\" style=\"border-radius: 1rem;\">"
    						+ "<div class=\"card-body\">";
    	
    	String yearResult = "<div class=\"row mb-4\" id=\"goalYearRow\"><div class=\"card\" style=\"border-radius: 1rem;\">"
    						+ "<div class=\"card-body\">";
    	
    	String goalListItem = "";

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
		DisplayHomeGoals newDisplay = new DisplayHomeGoals();
    	
    	ServletContext servletContext = getServletContext();
    	int id = (int) servletContext.getAttribute("userID");
    	System.out.println(id);
    	
    	String goalResult = newDisplay.displayGoals(id);
    	System.out.println(goalResult);
    	response.setContentType("text/html");
    	response.getWriter().write(goalResult);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
