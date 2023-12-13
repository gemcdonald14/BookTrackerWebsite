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

@WebServlet("/AddProfilePicture")
public class AddProfilePicture extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public AddProfilePicture() {
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
    
    public void insert(int id, byte[] imageData) {
		String updatesql = "UPDATE User SET ProfilePic=? WHERE UserID=?";
		
		try (Connection conn = this.connect(); PreparedStatement pstmt  = conn.prepareStatement(updatesql)){
            
			pstmt.setBytes(1,imageData);
			pstmt.setInt(2,id);
            
            pstmt.executeUpdate();
            System.out.println("executed picture update");
         
	     } catch (SQLException e) {
	            System.out.println(e.getMessage());
	            e.printStackTrace();
	     }
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		AddProfilePicture newImage = new AddProfilePicture();
		String profilePictureData = request.getParameter("profilePicture");
		System.out.println("image data: " + profilePictureData);
		
		ServletContext servletContext = getServletContext();
    	int id = (int) servletContext.getAttribute("userID");
    	System.out.println(id);

        if (profilePictureData == null || profilePictureData.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing or empty profilePicture parameter");
            return;
        }

        try {
            // Decode Base64 string to byte array
            byte[] imageData = Base64.getDecoder().decode(profilePictureData);

            // Insert the image data into the database
            newImage.insert(id, imageData);

            response.getWriter().write("<img src='data:image/png;base64," + Base64.getEncoder().encodeToString(imageData) + "' id='profilePic'>");

        } catch (IllegalArgumentException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid Base64 encoding: " + e.getMessage());
        }
	}

}
