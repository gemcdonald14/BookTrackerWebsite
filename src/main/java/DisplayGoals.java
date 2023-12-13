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

@WebServlet("/DisplayGoals")
public class DisplayGoals extends HttpServlet {
	private static final long serialVersionUID = 1L;
 
    public DisplayGoals() {
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
    	
    	String sql = "SELECT * FROM Goal WHERE UserID=?";
    	String finalResult = "";

    	String monthResult = "<div class=\"col-xl-6 col-lg-5 col-md-6 p-4\" id=\"monthGoalCol\"><div class=\"card mt-2 mb-2\" style=\"border-radius: 1rem;\">"
    						+ "<div class=\"card-body\" style=\"justify-content: space-evenly;\">"
    						+ "<h5 class=\"card-title\" style=\"text-align:center;\">My Monthly Reading Goals</h5><br>"
    						+ "<ul class=\"list-group list-group-flush\" id=\"monthGoalsList\">";
    	String yearResult = "<div class=\"col-xl-6 col-lg-5 col-md-6 p-4\" id=\"yearGoalCol\"><div class=\"card mt-2 mb-2\" style=\"border-radius: 1rem;\">"
							+ "<div class=\"card-body\" style=\"justify-content: space-evenly;\">"
							+ "<h5 class=\"card-title\" style=\"text-align:center;\">My Yearly Reading Goals</h5><br>"
							+ "<ul class=\"list-group list-group-flush\" id=\"yearGoalsList\">";
    	String goalListItem = "";
    	
        try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
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
	            
	            if (type.equals("1")) {
	            	goalListItem = "<li class='list-group-item'><h6>" + name + "</h6><div class='progress' style='height: 1.2rem;'>"
		    				+ "<div class='progress-bar' role='progressbar' style='width:" + percent + "%' aria-valuenow='" + completed + "' aria-valuemin='0' aria-valuemax='" + target + "'></div></div></li>";
	            	
	            	monthResult += goalListItem;
	            } else if (type.equals("2")) {
	            	goalListItem = "<li class='list-group-item'><h6>" + name + "</h6><div class='progress' style='height: 1.2rem;'>"
		    				+ "<div class='progress-bar' role='progressbar' style='width:" + percent + "%' aria-valuenow='" + completed + "' aria-valuemin='0' aria-valuemax='" + target + "'></div></div></li>";
	            	
	            	yearResult += goalListItem;
	            }
            }
            
            monthResult +=  "</ul></div></div></div>";
            yearResult += "</ul></div></div>";
            finalResult = monthResult + yearResult;
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        System.out.println(finalResult);
    	response.setContentType("text/html");
    	response.getWriter().write(finalResult);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
