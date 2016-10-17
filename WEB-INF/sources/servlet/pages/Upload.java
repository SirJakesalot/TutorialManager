package tutorial_site;

import tutorialdb_model.DataModel;
import tutorialdb_model.Logger;
import tutorialdb_model.Category;
import tutorialdb_lib.GetNavBar;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/upload")
public class Upload extends HttpServlet {

    // TODO: Get space available
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
		/* communicates with tutorialdb */
        DataModel dm = null;
		try {
            dm = new DataModel();
			/* gather all navbar category objects */
			List<Category> categories = GetNavBar.getCategories(dm);
			
			/* set the page's title and categories */
			request.setAttribute("title", "Tutorial Uploads");
			request.setAttribute("categories", categories);
			
			/* forward this response to main.jsp */
			request.getRequestDispatcher("jsp/upload.jsp").forward(request, response);
		} catch (Exception e) {
			Logger.log(Logger.Status.ERROR, "UploadPage", e);
		} finally {
            if (dm != null) { dm.closeConnection(); }
        }		
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doGet(request,response);
    }
}
