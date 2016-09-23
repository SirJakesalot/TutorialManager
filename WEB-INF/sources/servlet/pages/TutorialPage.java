package tutorial_site;

import tutorialdb_model.DataModel;
import tutorialdb_model.Logger;
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
import java.util.ArrayList;

/**
 * TutorialPage servlet gathers the information required for the tutorial page.
 * @author Jake Armentrout
 */

/* this servlet is accessed by the url ${context}/tutorial */
@WebServlet("/tutorial")


public class TutorialPage extends HttpServlet {

	/**
	 * A GET request will query tutorialdb for the tutorial, populate the
	 * sidebar, and forward to tutorial.jsp.
	 * @param request HttpServletRequest for the web page
	 * @param response HttpServletResponse for the request
	 */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String id = request.getParameter("id");
		
        /* invalid tutorial id */
        if (id == null || id.trim().isEmpty()) {
            Logger.log(Logger.Status.WARNING, "TutorialPage doGet: Tutorial id is null or empty \"" + id + "\"");
            response.sendRedirect("");
            return;
        }
		
		/* communicates with tutorialdb */
		DataModel dm = new DataModel();
		
        try {
            /* query tutorialdb for tutorial */
            List<String> statement_parameters = new ArrayList<String>();
            statement_parameters.add(id);
            List<Tutorial> tutorials = dm.getTutorialsForQuery(Tutorial.SELECT_ID, statement_parameters);
            dm.closeStatement();
			
			/* report error if query was empty */
            if (tutorials == null || tutorials.isEmpty()) {
                Logger.log(Logger.Status.ERROR, "TutorialPage Invalid tutorial id: " + id);
				dm.closeConnection();
                response.sendRedirect("");
                return;
            }
			
			/* only one tutorial should be returned */
            Tutorial tutorial = tutorials.get(0);

            /* set the page's fbdata_url, title, tutorial, and categories */
            String fbdata_url = request.getRequestURL() + "?" + request.getQueryString();
            request.setAttribute("fbdata_url", fbdata_url);
            request.setAttribute("title", tutorial.title());
            request.setAttribute("tutorial", tutorial);
            request.setAttribute("categories", GetNavBar.getCategories(dm));
			/* close tutorialdb connection */
            dm.closeConnection();

			/* forward this response to tutorial.jsp */
            request.getRequestDispatcher("jsp/tutorial.jsp").forward(request,response);
        } catch (Exception e) {
            Logger.log(Logger.Status.ERROR, "TutorialPage", e);
        }
		/* close tutorialdb connection */
		dm.closeConnection();
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doGet(request, response);
    }
}
