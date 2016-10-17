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
 * AddTutorialPage servlet allows the admin to add additional tutorials.
 * @author Jake Armentrout
 */
 
/* this servlet is accessed by the url ${context}/addTutorial */
@WebServlet("/addcategory")


public class AddCategory extends HttpServlet {

    /**
	 * A GET ...
	 * @param request HttpServletRequest for the web page
	 * @param response HttpServletResponse for the request
	 */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        DataModel dm = null;
        try {
            /* communicates with tutorialdb */
            dm = new DataModel();
            
            /* gather list of tutorials and categories */
            List<Tutorial> tutorials  = dm.getTutorialsForQuery(Tutorial.SELECT_ALL, null);
            dm.closeStatement();
            /* gather all navbar category objects */
            List<Category> categories = GetNavBar.getCategories(dm);
            dm.closeStatement();

            /* set page's parameters */
            request.setAttribute("title", "Add Tutorial");
            request.setAttribute("tutorials", tutorials);
            request.setAttribute("categories", categories);
            
            request.getRequestDispatcher("jsp/addcategory.jsp").forward(request,response);
        } catch (Exception e) {
            Logger.log(Logger.Status.ERROR, "AddCategory", e);
            request.setAttribute("msg", e.getMessage());
            request.getRequestDispatcher("jsp/error.jsp").forward(request, response);
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
