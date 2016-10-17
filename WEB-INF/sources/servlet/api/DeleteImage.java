// http://stackoverflow.com/questions/1158777/rename-a-file-using-java
package tutorialdb_api;

import tutorialdb_model.Logger;
import tutorialdb_model.TutorialException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

/* this servlet is accessed by the url ${context}/images_delete */
@WebServlet("/deleteimage")


public class DeleteImage extends HttpServlet {

	/* directory where uploaded files are saved */
    private static final String SAVE_DIR = "uploads";
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) {
		
        PrintWriter out = null;
		try {
            /* used for writing the JSON to the page */
            response.setContentType("application/json");
            out = response.getWriter();
        
            /* absolute path of the web application */
            String appPath = request.getServletContext().getRealPath("");
            String fileName = request.getParameter("fileName");
			String filePath = appPath + File.separator + SAVE_DIR + File.separator + fileName;
			
			/* check if from file exists */
			File file = new File(filePath);
			if (!file.isFile()) {
                throw new TutorialException("filePath is not a file: " + filePath);
			}
			file.delete();
            if (file.isFile()) {
                throw new TutorialException("filePath was not deleted: " + filePath);
			}
			out.println(Logger.log(Logger.Status.SUCCESS, "Successfully deleted file: " + fileName));
        } catch(TutorialException te) {
            out.println(Logger.log(Logger.Status.ERROR, te.getMessage()));
        } catch (Exception e) {
            out.println(Logger.log(Logger.Status.ERROR, "DeleteImage", e));
        } finally {
            out.close();
        }
	}
}
