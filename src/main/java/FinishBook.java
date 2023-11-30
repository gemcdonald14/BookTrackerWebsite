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

@WebServlet("/FinishBook") 
public class FinishBook extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public FinishBook() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String rating = "<form name=\"rateBookForm\" id=\"rateBookForm\">"
						+ "<p id=\"reviewTitle\">Review your book!</p>"
						+ "<div class=\"form-outline\" style=\"display: inline-flex;\">"
						+ "<input type=\"text\" id=\"bookRating\" class=\"form-control form-control-sm\" placeholder=\"Rating (0 &#9734; - 5 &#9734;)\"/>"
						+ "</div><br>"
						+ "<label class=\"form-label\" for=\"bookGenre\" style=\"font-size: 1.1rem; font-weight:500; margin-right: 20px;\">Top Genre:</label>"
						+ "<select class=\"custom-select\" id=\"bookGenre\" name=\"bookGenre\" style=\"margin-bottom: 20px;\">"
						+ "<option value=\"1\">Fiction</option>"
						+ "<option value=\"2\">Non-Fiction</option>"
						+ "<option value=\"3\">Young Adult</option>"
						+ "<option value=\"4\">Children's Lit</option>"
						+ "<option value=\"5\">Romance</option>"
						+ "<option value=\"6\">Fantasy</option>"
						+ "<option value=\"7\">Mystery</option>"
						+ "<option value=\"8\">Thriller</option>"
						+ "<option value=\"9\">Historical</option>"
						+ "<option value=\"10\">Informational</option>"
						+ "</select><br>"
						+ "<label class=\"form-label\" for=\"bookMood\" style=\"font-size: 1.1rem; font-weight:500; margin-right: 20px;\">Top Mood:</label>"
						+ "<select class=\"custom-select\" id=\"bookMood\" name=\"bookMood\" style=\"margin-bottom: 20px;\">"
						+ "<option value=\"1\">Adventurous</option>"
						+ "<option value=\"2\">Tense</option>"
						+ "<option value=\"3\">Dark</option>"
						+ "<option value=\"4\">Funny</option>"
						+ "<option value=\"5\">Emotional</option>"
						+ "<option value=\"6\">Light-Hearted</option>"
						+ "<option value=\"7\">Mysterious</option>"
						+ "<option value=\"8\">Challenging</option>"
						+ "<option value=\"9\">Reflective</option>"
						+ "<option value=\"10\">Informative</option>"
						+ "<option value=\"11\">Inspiring</option>"
						+ "<option value=\"12\">Hopeful</option>"
						+ "<option value=\"13\">Relaxing</option>"
						+ "<option value=\"14\">Sad</option>"
						+ "</select><br>"
						+ "<label class=\"form-label\" for=\"bookPace\" style=\"font-size: 1.1rem; font-weight:500; margin-right: 20px;\">Pace:</label>"
						+ "<input type=\"radio\" id=\"slow\" name=\"bookPace\" value=\"1\">"
						+ "<label for=\"slow\" style=\"font-size: 1.1rem; margin-right: 10px;\">Slow</label>"
						+ "<input type=\"radio\" id=\"medium\" name=\"bookPace\" value=\"2\">"
						+ "<label for=\"medium\" style=\"font-size: 1.1rem; margin-right: 10px;\">Medium</label>"
						+ "<input type=\"radio\" id=\"fast\" name=\"bookPace\" value=\"3\">"
						+ "<label for=\"fast\" style=\"font-size: 1.1rem; margin-right: 10px;\">Fast</label><br>"
						+ "<div style=\"text-align: center; margin-top: 20px;\"><button class=\"btn\" id=\"rateBook\">Rate Book</button></div>"
						+ "</form>";

			response.setContentType("text/html");
	    	response.getWriter().write(rating);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
