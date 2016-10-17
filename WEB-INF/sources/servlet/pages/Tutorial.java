package tutorial_site;

import tutorialdb_model.DataModel;
import tutorialdb_model.Logger;
import tutorialdb_model.Category;
import tutorialdb_model.TutorialException;

import tutorialdb_lib.GetNavBar;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

/**
 * tutorialdb_model.TutorialPage servlet gathers the information required for the tutorial page.
 * @author Jake Armentrout
 */

/* this servlet is accessed by the url ${context}/tutorial */
@WebServlet("/tutorial")


public class Tutorial extends HttpServlet {

	/**
	 * A GET request will query tutorialdb for the tutorial, populate the
	 * sidebar, and forward to tutorial.jsp.
	 * @param request HttpServletRequest for the web page
	 * @param response HttpServletResponse for the request
	 */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        
        /* communicates with tutorialdb */
        DataModel dm = null;
        try {
            String id = request.getParameter("id");
            
            /* invalid tutorial id */
            if (id == null || id.trim().isEmpty()) {
                throw new TutorialException("tutorial id is null or empty");
            }
            
            dm = new DataModel();
            /* query tutorialdb for tutorial */
            List<String> statement_parameters = new ArrayList<String>();
            statement_parameters.add(id);
            List<tutorialdb_model.Tutorial> tutorials = dm.getTutorialsForQuery(tutorialdb_model.Tutorial.SELECT_ID, statement_parameters);
            dm.closeStatement();
			
			/* report error if query was empty */
            if (tutorials == null || tutorials.isEmpty()) {
                throw new TutorialException("invalid tutorial id: " + id);
            }
			
			/* only one tutorial should be returned */
            tutorialdb_model.Tutorial tutorial = tutorials.get(0);

            /* set page paramaters */
            String fbdata_url = request.getRequestURL() + "?" + request.getQueryString();
            request.setAttribute("fbdata_url", fbdata_url);
            request.setAttribute("title", tutorial.title());
            request.setAttribute("tutorial", tutorial);
            request.setAttribute("categories", GetNavBar.getCategories(dm));

            request.getRequestDispatcher("jsp/tutorial.jsp").forward(request,response);
        } catch (Exception e) {
            Logger.log(Logger.Status.ERROR, "tutorialdb_model.Tutorial", e);
            request.setAttribute("msg", e.getMessage());
            request.getRequestDispatcher("jsp/error.jsp").forward(request,response);
        } finally {
            if (dm != null) { dm.closeConnection(); }
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doGet(request, response);
    }
}
