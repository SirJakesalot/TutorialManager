package tutorialdb_api;

import tutorialdb_model.Logger;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

@WebServlet("/api/fileupload")
@MultipartConfig(fileSizeThreshold=1024*1024*2, // 2MB
                 maxFileSize=1024*1024*10,      // 10MB
                 maxRequestSize=1024*1024*50)   // 50MB

public class FileUpload extends HttpServlet {

    // Name of the directory where uploaded files will be saved, relative to the web application directory.
    private static final String SAVE_DIR = "uploads";

    // Handles file upload
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
		
		PrintWriter out = null;
		try {
            /* used for writing the JSON to the page */
            response.setContentType("application/json");
            out = response.getWriter();
            // Gets absolute path of the web application
            String appPath = request.getServletContext().getRealPath("");
            // Constructs path of the directory to save uploaded file
            String savePath = appPath + File.separator + SAVE_DIR;
            
			// Creates the save directory if it does not exist
			File fileSaveDir = new File(savePath);
			if (!fileSaveDir.exists()) {
				fileSaveDir.mkdir();
			}

			// Write files to savePath
			for (Part part: request.getParts()) {
				String fileName = extractFileName(part);
				part.write(savePath + File.separator + fileName);
			}
			
			out.println(Logger.log(Logger.Status.SUCCESS, "FileUpload uploaded " + request.getParts().size() + " files"));
			out.flush();
        } catch (Exception e) {
            out.println(Logger.log(Logger.Status.ERROR, "FileUpload", e));
        } finally {
            out.close();
        }
    }
    
    // Extracts file name from HTTP header content-disposition
    private String extractFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        String[] items = contentDisp.split(";");
        for (String s: items) {
            if (s.trim().startsWith("filename")) {
                return s.substring(s.indexOf("=") + 2, s.length()-1);
            }
        }
        return "";
    }
}
