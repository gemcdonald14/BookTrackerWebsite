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
import java.sql.Blob;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Random;
import java.sql.SQLException;

@WebServlet("/DisplayGoalsDropDown")
public class DisplayGoalsDropDown extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public DisplayGoalsDropDown() {
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
    	
    	String sql = "SELECT GoalName FROM Goal WHERE UserID=? AND TargetBooks!=CompletedBooks";
    	String resultList = "";
    	int i = 0;

        try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
        	pstmt.setInt(1, id);
        	
        	ResultSet rs  = pstmt.executeQuery();
            
            while(rs.next()) {
            	String name = rs.getString("GoalName");
	            System.out.println("Name: " + name);
	            
	            String goalListItem = "<option value=\'" + i + "\'>" + name + "</option>";
	            
	            resultList +=goalListItem;
	            i++;
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
