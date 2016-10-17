package tutorialdb_api;

import tutorialdb_model.DataModel;
import tutorialdb_model.Tutorial;
import tutorialdb_model.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.ArrayList;

/**
 * TutorialSelect servlet gathers information for a given tutorial id.
 * @author Jake Armentrout
 */
 
/* this servlet is accessed by the url ${context}/tutorial_select */
@WebServlet("/tutorialSelect")


public class TutorialSelect extends HttpServlet {

    /**
	 * A POST request will gather a tutorial and its associated categories.
	 * @param request HttpServletRequest for the web page
	 * @param response HttpServletResponse for the request
	 */
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        /* used for writing the JSON to the page */
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
		
		/* used for communicating with tutorialdb */
		DataModel dm = new DataModel();
        try {
            String id = request.getParameter("id");
            /* invalid tutorial id */
            if (id == null || id.length() == 0) {
				dm.closeConnection();
                out.println(Logger.log(Logger.Status.ERROR, "TutorialSelect Invalid tutorial id: " + id));
				out.flush();
                return;
            }

            /* parameters for tutorial search query */
            List<String> statement_parameters = new ArrayList<String>();
            statement_parameters.add(id);
            List<Tutorial> tutorials = dm.getTutorialsForQuery(Tutorial.SELECT_ID, statement_parameters);
            dm.closeStatement();
			
			/* report error if query was empty */
			if (tutorials == null || tutorials.isEmpty()) {
				dm.closeConnection();
                out.println(Logger.log(Logger.Status.ERROR, "Unable to find tutorial id: " + id));
				out.flush();
                return;
            }
			
			/* only one tutorial should be returned */
            Tutorial tutorial = tutorials.get(0);
			
			/* gather all categories associated with the tutorial */
			statement_parameters = new ArrayList<String>();
            statement_parameters.add(tutorial.id());
			tutorial.categories(dm.getCategoriesForQuery(Tutorial.SELECT_CATEGORIES, statement_parameters));
			
			/* print tutorial to the screen */
            out.println(tutorial.toJSON());

        } catch (Exception e) {
            out.println(Logger.log(Logger.Status.ERROR, "TutorialSelect doPost", e));
        }
		dm.closeConnection();
		out.flush();
    }
}
