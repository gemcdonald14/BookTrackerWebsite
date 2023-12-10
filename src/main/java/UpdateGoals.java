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

@WebServlet("/UpdateGoals")
public class UpdateGoals extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
    public UpdateGoals() {
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
    
    public Boolean updateGoal(String goal, String numRead, int id) {
		String updatesql = "UPDATE Goal SET CompletedBooks=? WHERE UserID=? AND GoalID=?";
		String selectsql = "SELECT CompletedBooks FROM Goal WHERE UserID=? AND GoalID=?";
		Boolean result = false;
		int completed = 0;
		
		try (Connection conn = this.connect(); PreparedStatement pstmt  = conn.prepareStatement(selectsql)){
            
			int goalid =Integer.parseInt(goal);  
			
			pstmt.setInt(1,id);
            pstmt.setInt(2,goalid);
            
            ResultSet rs  = pstmt.executeQuery();
            
            while(rs.next()) {
	            completed = rs.getInt("CompletedBooks");
	            System.out.println("Completed: " + completed);
            }
	     } catch (SQLException e) {
	            System.out.println(e.getMessage());
	            e.printStackTrace();
	     }

		try (Connection conn = this.connect(); PreparedStatement pstmt  = conn.prepareStatement(updatesql)){
	            
				int booksRead =Integer.parseInt(numRead);  
				int goalid =Integer.parseInt(goal);  
				int newTotal = booksRead + completed;
				pstmt.setInt(1,newTotal);
	            pstmt.setInt(2,id);
	            pstmt.setInt(3,goalid);
	            
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
		UpdateGoals updateGoal = new UpdateGoals();
		
		ServletContext servletContext = getServletContext();
		int id = (int) servletContext.getAttribute("userID");
    	
		String goalId = request.getParameter("updateGoalTitle");
		String numRead = request.getParameter("updateGoalCompleted");
		
		System.out.println("goal id: " + goalId);
		System.out.println("num read: " + numRead);
		
		if (updateGoal.updateGoal(goalId, numRead, id)) {
			System.out.println("goal updated");
		} 
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
