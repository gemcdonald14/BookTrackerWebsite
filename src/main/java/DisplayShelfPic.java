import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;
import java.util.UUID;

@WebServlet("/DisplayShelfPic")
public class DisplayShelfPic extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public DisplayShelfPic() {
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
    	
    	int shelfId = Integer.parseInt(request.getParameter("shelfId"));
    	System.out.println("Received ShelfID: " + shelfId);

    	
    	String sql = "SELECT ShelfPic FROM Shelf WHERE UserID=? AND ShelfID=?";
    	try (Connection conn = this.connect(); PreparedStatement pstmt  = conn.prepareStatement(sql)){
            
			pstmt.setInt(1,id);
			pstmt.setInt(2, shelfId);
			ResultSet rs = pstmt.executeQuery();
			
			if (rs.next()) {
	            // Retrieve the image data from the database
	            byte[] imageData = rs.getBytes("ShelfPic");

	            // Set the content type to image/png
	            response.setContentType("image/png");

	            // Write the image data to the response output stream if not null
	            if (imageData != null) {
	                try (OutputStream out = response.getOutputStream()) {
	                    out.write(imageData);
	                }
	            } else {
	            	try (InputStream defaultImageStream = servletContext.getResourceAsStream("/images/bookpile.png")) {
		                if (defaultImageStream != null) {
		                    byte[] defaultImageData = defaultImageStream.readAllBytes();

		                    // Set the content type to image/png
		                    response.setContentType("image/png");

		                    // Write the default image data to the response output stream if not null
		                    if (defaultImageData != null) {
		                        try (OutputStream out = response.getOutputStream()) {
		                            out.write(defaultImageData);
		                        }
		                    }
		                }
	            }
	            }
	        } else {
	            // If no image data is found, set a default image
	            try (InputStream defaultImageStream = servletContext.getResourceAsStream("/images/bookpile.png")) {
	                if (defaultImageStream != null) {
	                    byte[] defaultImageData = defaultImageStream.readAllBytes();

	                    // Set the content type to image/png
	                    response.setContentType("image/png");

	                    // Write the default image data to the response output stream if not null
	                    if (defaultImageData != null) {
	                        try (OutputStream out = response.getOutputStream()) {
	                            out.write(defaultImageData);
	                        }
	                    }
	                }
	            }
	        }
	        
	    } catch (SQLException e) {
	        System.out.println(e.getMessage());
	        e.printStackTrace();
	    }
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
