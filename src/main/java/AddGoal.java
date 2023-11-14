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

@WebServlet("/AddGoal")
public class AddGoal extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public AddGoal() {
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
	
	public void insert(String name, String type, int id, String targetBooks) {
		String sql = "INSERT INTO Goal(GoalName,GoalType,UserID,TargetBooks) VALUES(?,?,?,?)";

        try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, type);
            pstmt.setInt(3, id);
            pstmt.setString(4, targetBooks);
            pstmt.executeUpdate();
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		AddGoal newGoal = new AddGoal();
    	
    	String name = request.getParameter("newGoalTitle");
		String type = request.getParameter("newGoalType");
		String target = request.getParameter("newGoalTarget");
		
		ServletContext servletContext = getServletContext();
    	int id = (int) servletContext.getAttribute("userID");
    	System.out.println(id);
    	
		newGoal.insert(name, type, id, target);
		
		String newGoalListItem = "<li class='list-group-item'>" 
								+ "<h6>" + name + "</h6>" 
								+ "<div class='progress' style='height: 1.2rem;'>"
								+ "<div class='progress-bar w-75' role='progressbar' aria-valuenow='10' aria-valuemin='0' aria-valuemax='100'></div>"
								+ "</div>"
								+ "</li>";
		/*
		
		if (type.equals("1")) {
			String monthGoalDiv = "<div class='col-md-6 p-4' id='monthGoalCol'>"
								+ "<div class='card' style='border-radius: 1rem;'>"
								+ "<div class='card-header' style='font-size: larger;'>"
								+ "Monthly Reading Goals</div>"
								+ "<ul class='list-group list-group-flush'>"
								+ newGoalListItem
								+ "</ul>"
								+ "</div>"
								+ "</div>";
			
			response.setContentType("text/html");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(monthGoalDiv);
			
		} else if (type.equals("2")) {
			String yearGoalDiv = "<div class='col-md-6 p-4' id='yearGoalCol'>"
								+ "<div class='card' style='border-radius: 1rem;'>"
								+ "<div class='card-header' style='font-size: larger;'>"
								+ "Yearly Reading Goals</div>"
								+ "<ul class='list-group list-group-flush'>"
								+ newGoalListItem
								+ "</ul>"
								+ "</div>"
								+ "</div>";
			
			response.setContentType("text/html");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(yearGoalDiv);
		}*/
			
		//response.sendRedirect("mygoals.html");
		
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(newGoalListItem);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
