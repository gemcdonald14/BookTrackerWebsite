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
            conn = DriverManager.getConnection(url);
            System.out.println("Connection to SQLite has been established.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }
	
	public Boolean insert(String name, String type, int id, String targetBooks) {
		String insertsql = "INSERT INTO Goal(GoalName,GoalType,UserID,TargetBooks) VALUES(?,?,?,?)";
		String checksql = "SELECT COUNT(GoalName) FROM Goal WHERE UserID=? AND GoalName=?";
		Boolean result = false;
		Boolean checkresult = false;
		
		try (Connection conn = this.connect(); PreparedStatement pstmt  = conn.prepareStatement(checksql)){
			 
			pstmt.setInt(1, id);
			pstmt.setString(2, name);
	        ResultSet rs = pstmt.executeQuery();

	        if (rs.next()) {
	            int num = rs.getInt(1);
	            
	            if (num == 0) {
	            	checkresult = false; //no goal same goal name
	            } else {
	            	checkresult = true; //same goal name; already have 
	            }
	        }
	     } catch (SQLException e) {
	            System.out.println(e.getMessage());
	            e.printStackTrace();
	     }
		
		if (checkresult == false) {
			try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(insertsql)) {
	            pstmt.setString(1, name);
	            pstmt.setString(2, type);
	            pstmt.setInt(3, id);
	            pstmt.setString(4, targetBooks);
	            pstmt.executeUpdate();
	            
	            result = true;
	            
	        } catch (SQLException e) {
	            System.out.println(e.getMessage());
	        }
		} 
		
        return result;
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		AddGoal newGoal = new AddGoal();
		
		String title = request.getParameter("newGoalTitle");
		String type = request.getParameter("newGoalType");
		String target = request.getParameter("newGoalTarget");
		
		ServletContext servletContext = getServletContext();
    	int id = (int) servletContext.getAttribute("userID");
    	System.out.println(id);
		
		System.out.println("title: " + title);
		System.out.println("type: " + type);
		System.out.println("target: " + target);
		
		String newGoalListItem = "<li class='list-group-item'><h6>" + title + "</h6><div class='progress' style='height: 1.2rem;'>"
				+ "<div class='progress-bar w-75' role='progressbar' aria-valuenow='10' aria-valuemin='0' aria-valuemax='100'></div></div></li>";
			
		if (newGoal.insert(title, type, id, target)) {
			System.out.println("return true");
			System.out.println(newGoalListItem);
			response.setContentType("text/html");
			response.getWriter().write(newGoalListItem);
		} else {
			response.getWriter().write("Goal name already used");
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
