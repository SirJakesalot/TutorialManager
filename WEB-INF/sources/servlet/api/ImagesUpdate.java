// http://stackoverflow.com/questions/1158777/rename-a-file-using-java
package tutorialdb_api;

import tutorialdb_model.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

/* this servlet is accessed by the url ${context}/images_update */
@WebServlet("/images_update")


public class ImagesUpdate extends HttpServlet {
	
	/* directory where uploaded files are saved */
    private static final String SAVE_DIR = "uploads";
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/* used for writing the JSON to the page */
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
		
		/* absolute path of the web application */
        String appPath = request.getServletContext().getRealPath("");
		
		try {
            String from = request.getParameter("from");
			String to = request.getParameter("to");
			
			String fromPath = appPath + File.separator + SAVE_DIR + File.separator + from;
			String toPath   = appPath + File.separator + SAVE_DIR + File.separator + to;
			
			/* check if from file exists */
			File fromFile = new File(fromPath);
			if (!fromFile.isFile()) {
                out.println(Logger.log(Logger.Status.ERROR, "ImageUpdate fromPath is not a file: " + fromPath));
				out.flush();
                return;
			}
			
			/* check if to file exists */
			File toFile = new File(toPath);
			if (toFile.exists()) {
				out.println(Logger.log(Logger.Status.ERROR, "ImageUpdate toPath already exists: " + toPath));
				out.flush();
                return;
			}
			
			/* check if file naming was successful */
			boolean success = fromFile.renameTo(toFile);
			if (!success) {
				out.println(Logger.log(Logger.Status.ERROR, "ImageUpdate unable to rename fromFile: " + fromPath + " toFile: " + toPath));
				out.flush();
                return;
			}
			out.println(Logger.log(Logger.Status.SUCCESS, "Successfully updated filename"));
			
		} catch (Exception e) {
            out.println(Logger.log(Logger.Status.ERROR, "ImageUpdate doPost", e));
        }
	}
}
