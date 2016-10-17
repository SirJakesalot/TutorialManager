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

@WebServlet("/api/updateimage")


public class UpdateImage extends HttpServlet {
	
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
            
            String from = request.getParameter("from");
			String to = request.getParameter("to");
			
			String fromPath = appPath + File.separator + SAVE_DIR + File.separator + from;
			String toPath   = appPath + File.separator + SAVE_DIR + File.separator + to;
			
			/* check if from file exists */
			File fromFile = new File(fromPath);
			if (!fromFile.isFile()) {
                throw new TutorialException("fromPath is not a file: " + fromPath);
			}
			
			/* check if to file exists */
			File toFile = new File(toPath);
			if (toFile.exists()) {
                throw new TutorialException("toPath already exists: " + toPath);
			}
			
			/* check if file naming was successful */
			boolean success = fromFile.renameTo(toFile);
			if (!success) {
                throw new TutorialException("unable to rename: " + fromPath + "-> " + toPath);
			}
			out.println(Logger.log(Logger.Status.SUCCESS, "Successfully updated filename"));
        } catch(TutorialException te) {
            out.println(Logger.log(Logger.Status.ERROR, te.getMessage()));
        } catch (Exception e) {
            out.println(Logger.log(Logger.Status.ERROR, "UpdateImage", e));
        } finally {
            out.close();
        }
	}
}
