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
import java.util.Arrays;
import java.util.Base64;
import java.util.UUID;

@WebServlet("/AddShelfPicture")
public class AddShelfPicture extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public AddShelfPicture() {
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
    
    public void insert(int id, String shelf, byte[] imageData) {
		String updatesql = "UPDATE Shelf SET ShelfPic=? WHERE UserID=? AND ShelfID=?";
		
		try (Connection conn = this.connect(); PreparedStatement pstmt  = conn.prepareStatement(updatesql)){
            
			int shelfid =Integer.parseInt(shelf); 
			pstmt.setBytes(1,imageData);
			pstmt.setInt(2,id);
			pstmt.setInt(3,shelfid);
            
            pstmt.executeUpdate();
            System.out.println("executed picture update");
         
	     } catch (SQLException e) {
	            System.out.println(e.getMessage());
	            e.printStackTrace();
	     }
    }
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		AddShelfPicture newImage = new AddShelfPicture();
		String shelfPictureData = request.getParameter("shelfPicture");
		String shelfid = request.getParameter("shelfId");
		System.out.println("image data: " + shelfPictureData);
		System.out.println("Shelf ID: " + shelfid);
		
		if (shelfPictureData == null || shelfPictureData.isEmpty()) {
		    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing or empty shelfPicture parameter");
		    return;
		}

		
		ServletContext servletContext = getServletContext();
    	int id = (int) servletContext.getAttribute("userID");
    	System.out.println(id);
    	
    	System.out.println("Received shelfPictureData: " + shelfPictureData);

        try {
            // Decode Base64 string to byte array
        	System.out.println("Before decoding: " + shelfPictureData);
        	byte[] imageData = Base64.getDecoder().decode(shelfPictureData.trim());
        	System.out.println("After decoding: " + Arrays.toString(imageData));
            // Insert the image data into the database
            newImage.insert(id, shelfid, imageData);

            request.setCharacterEncoding("UTF-8");

            response.getWriter().write("<img class='shelfPic' name='" + shelfid + "' src='data:image/png;base64," + Base64.getEncoder().encodeToString(imageData) + "'>");

        } catch (IllegalArgumentException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid Base64 encoding: " + e.getMessage());
        }
	}

}
