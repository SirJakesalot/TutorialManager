package tutorialdb_api;

import tutorialdb_model.DataModel;
import tutorialdb_model.Tutorial;
import tutorialdb_model.Logger;
import tutorialdb_model.TutorialException;

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
@WebServlet("/api/selecttutorial")


public class SelectTutorial extends HttpServlet {

    /**
	 * A POST request will gather a tutorial and its associated categories.
	 * @param request HttpServletRequest for the web page
	 * @param response HttpServletResponse for the request
	 */
    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        PrintWriter out = null;
		/* used for communicating with tutorialdb */
		DataModel dm = null;
        
        try {
            /* used for writing the JSON to the page */
            response.setContentType("application/json");
            out = response.getWriter();
            dm = new DataModel();
            
            String id = request.getParameter("id");
            /* invalid tutorial id */
            if (id == null || id.length() == 0) {
                throw new TutorialException("invalid tutorial id: " + id);
            }

            /* parameters for tutorial search query */
            List<String> statementParameters = new ArrayList<String>();
            statementParameters.add(id);
            List<Tutorial> tutorials = dm.getTutorialsForQuery(Tutorial.SELECT_ID, statementParameters);
            dm.closeStatement();
			
			/* report error if query was empty */
			if (tutorials == null || tutorials.isEmpty()) {
                throw new TutorialException("unable to find tutorial id: " + id);
            }
			
			/* only one tutorial should be returned */
            Tutorial tutorial = tutorials.get(0);
			
			/* gather all categories associated with the tutorial */
			statementParameters = new ArrayList<String>();
            statementParameters.add(tutorial.id());
			tutorial.categories(dm.getCategoriesForQuery(Tutorial.SELECT_CATEGORIES, statementParameters));
            dm.closeStatement();
			
			/* print tutorial to the screen */
            out.println(tutorial.toJSON());
        } catch(TutorialException te) {
            out.println(Logger.log(Logger.Status.ERROR, te.getMessage()));
        } catch (Exception e) {
            out.println(Logger.log(Logger.Status.ERROR, "SelectTutorial", e));
        } finally {
            if (dm != null) { dm.closeConnection(); }
            out.close();
        }
    }
}
