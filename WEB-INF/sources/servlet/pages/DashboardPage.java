package tutorial_site;

import tutorialdb_model.DataModel;
import tutorialdb_model.Tutorial;
import tutorialdb_model.Category;
import tutorialdb_lib.GetNavBar;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

/**
 * DashboardPage servlet gathers the information required for the
 * administrator's dashboard page.
 * @author Jake Armentrout
 */
 
/* this servlet is accessed by the url ${context}/dashboard */
@WebServlet("/dashboard")


public class DashboardPage extends HttpServlet {

    /**
	 * A GET request will get a list of tutorials and categories, then forward
	 * to dashboard.jsp.
	 * @param request HttpServletRequest for the web page
	 * @param response HttpServletResponse for the request
	 */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

		/* communicates with tutorialdb */
        DataModel dm = new DataModel();
		
		/* gather list of tutorials and categories */
        List<Tutorial> tutorials  = dm.getTutorialsForQuery(Tutorial.SELECT_ALL, null);
        /* gather all navbar category objects */
		List<Category> categories = GetNavBar.getCategories(dm);
		/* close tutorialdb connection */
        dm.closeConnection();

		/* set the page's title, tutorials, and categories */
		request.setAttribute("title", "Admin Dashboard");
        request.setAttribute("tutorials", tutorials);
        request.setAttribute("categories", categories);
		
		/* forward this response to dashboard.jsp */
        request.getRequestDispatcher("jsp/dashboard.jsp").forward(request,response);
    }

	/**
	 * A POST request will act the same as a GET request
	 * @param request HttpServletRequest for the web page
	 * @param response HttpServletResponse for the request
	 */
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doGet(request,response);
    }
}
