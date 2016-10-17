package tutorial_site;

import tutorialdb_model.DataModel;
import tutorialdb_model.Logger;
import tutorialdb_model.Category;
import tutorialdb_model.TutorialException;
import tutorialdb_lib.GetNavBar;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * MainPage servlet gathers the information required for the main page.
 * @author Jake Armentrout
 */

/* this servlet is accessed by the url ${context}/main */
@WebServlet("/main")


public class Main extends HttpServlet {

	/**
	 * A GET request will popoulate the sidebar and forward to main.jsp.
	 * @param request HttpServletRequest for the web page
	 * @param response HttpServletResponse for the request
	 */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		/* communicates with tutorialdb */
        DataModel dm = null;
        
		try {
            dm = new DataModel();
			/* gather all navbar category objects */
			List<Category> categories = GetNavBar.getCategories(dm);
            dm.closeStatement();
			
			/* set page's parameters */
			request.setAttribute("title", "Jake's Tutorial Website");
			request.setAttribute("categories", categories);
			
			request.getRequestDispatcher("jsp/main.jsp").forward(request, response);
        } catch (Exception e) {
            Logger.log(Logger.Status.ERROR, "Main", e);
            request.setAttribute("msg", e.getMessage());
            request.getRequestDispatcher("jsp/error.jsp").forward(request,response);
        } finally {
            if (dm != null) { dm.closeConnection(); }
        }
		/* close tutorialdb connection */
		dm.closeConnection();
    }

	/**
	 * A POST request will act the same as a GET request
	 * @param request HttpServletRequest for the web page
	 * @param response HttpServletResponse for the request
	 */
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doGet(request, response);
    }
}
