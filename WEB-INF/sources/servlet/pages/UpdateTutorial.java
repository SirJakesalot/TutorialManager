package tutorial_site;

import tutorialdb_model.DataModel;
import tutorialdb_model.Tutorial;
import tutorialdb_model.Category;
import tutorialdb_model.Logger;
import tutorialdb_lib.GetNavBar;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

/**
 * UpdateTutorial servlet allows the administrator to update a tutorials
 * information.
 * @author Jake Armentrout
 */
 
/* this servlet is accessed by the url ${context}/updateTutorial */
@WebServlet("/updatetutorial")


public class UpdateTutorial extends HttpServlet {

    /**
	 * A GET request will get a list of tutorials and categories, then forward
	 * to updateTutorial.jsp.
	 * @param request HttpServletRequest for the web page
	 * @param response HttpServletResponse for the request
	 */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

		/* communicates with tutorialdb */
        DataModel dm = null;
        
        try {
            dm = new DataModel();
            
            /* gather list of tutorials and categories */
            List<Tutorial> tutorials  = dm.getTutorialsForQuery(Tutorial.SELECT_ALL, null);
            /* gather all navbar category objects */
            List<Category> categories = GetNavBar.getCategories(dm);
            dm.closeStatement();

            /* set page's parameters */
            request.setAttribute("title", "Update Tutorial");
            request.setAttribute("tutorials", tutorials);
            request.setAttribute("categories", categories);
            
            request.getRequestDispatcher("jsp/updatetutorial.jsp").forward(request,response);
        } catch (Exception e) {
            Logger.log(Logger.Status.ERROR, "UpdateTutorial", e);
            request.setAttribute("msg", e.getMessage());
            request.getRequestDispatcher("jsp/error.jsp").forward(request,response);
        } finally {
            if (dm != null) { dm.closeConnection(); }
        }
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
