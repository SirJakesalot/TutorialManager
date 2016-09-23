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

/* this servlet is accessed by the url ${context}/images_delete */
@WebServlet("/images_delete")


public class ImagesDelete extends HttpServlet {

	/* directory where uploaded files are saved */
    private static final String SAVE_DIR = "uploads";
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/* used for writing the JSON to the page */
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
		
		/* absolute path of the web application */
        String appPath = request.getServletContext().getRealPath("");
		
		try {
            String fileName = request.getParameter("fileName");
			String filePath = appPath + File.separator + SAVE_DIR + File.separator + fileName;
			
			/* check if from file exists */
			File file = new File(filePath);
			if (!file.isFile()) {
                out.println(Logger.log(Logger.Status.ERROR, "ImageDelete filePath is not a file: " + filePath));
				out.flush();
                return;
			}
			
			file.delete();
			
			out.println(Logger.log(Logger.Status.SUCCESS, "Successfully deleted filename"));
			
		} catch (Exception e) {
            out.println(Logger.log(Logger.Status.ERROR, "ImageDelete doPost", e));
        }
	}
}
