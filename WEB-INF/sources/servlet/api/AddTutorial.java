package tutorialdb_api;

import tutorialdb_model.DataModel;
import tutorialdb_model.Logger;
import tutorialdb_model.Tutorial;
import tutorialdb_model.Category;
import tutorialdb_model.TutorialException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.sql.SQLException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.ArrayList;


/**
 * AddTutorial servlet adds the tutorial if the title does not already exist.
 * @author Jake Armentrout
 */
 
@WebServlet("/api/addtutorial")


public class AddTutorial extends HttpServlet {

	/**
	 * You can only add a tutorial if it is a POST request. JSON is printed
     * to the screen with details about the add.
	 * @param request HttpServletRequest for ${context}/addTutorial
	 * @param response HttpServletResponse for the request
	 */
    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        /* used to write JSON to the page */
        PrintWriter out = null;
        DataModel dm = null;

        try {
            response.setContentType("application/json");
            out = response.getWriter();
			/* request parameters */
            String title      = request.getParameter("title");
            String content    = request.getParameter("content");
            String categories = request.getParameter("categories");

            /* invalid tutorial title */
            if (title == null || title.length() == 0) {
                throw new ServletException("AddTutorial doPost - null or empty tutorial title");
            }
            
            /* used for communicating with tutorialdb */
            dm = new DataModel();
            addTutorialTable(dm, title, content);
            addTutorialCategoriesTable(dm, title, categories);
            out.println(Logger.log(Logger.Status.SUCCESS, "Successfully added tutorial: " + title));
        } catch(TutorialException te) {
            out.println(Logger.log(Logger.Status.ERROR, te.getMessage()));
        } catch (Exception e) {
            out.println(Logger.log(Logger.Status.ERROR, "AddTutorial", e));
        } finally {
            if (dm != null) { dm.closeConnection(); }
            out.close();
        }
    }
    
    public String[] parseCategories(String categories) {
        if (categories == null || categories.length() <= 2) {
            return new String[0];
        }
        return categories.substring(1, categories.length() - 1).split(",");
    }
    
    private void addTutorialTable(DataModel dm, String title, String content) throws TutorialException, SQLException {
        
        /* check if tutorial already exists */
        List<String> statementParameters = new ArrayList<String>();
        statementParameters.add(title);
        int tutorialCount = dm.getAggregateQuery(Tutorial.COUNT_SELECT_TITLE, statementParameters);
        dm.closeStatement();
        if (tutorialCount > 0) {
            /* title already exist */
            throw new TutorialException("addTutorialTable - title already exists: " + title);
        }
        
        statementParameters.add(content);
        /* get number of records updated by insert */
        int rowsUpdated = dm.executeUpdate(Tutorial.INSERT, statementParameters);
        dm.closeStatement();
        if (rowsUpdated < 1) {
            throw new TutorialException("addTutorialTable - insert was not successful");
        } else if (rowsUpdated > 1) {
            throw new TutorialException("addTutorialTable - " + rowsUpdated + " records were affected");
        }
    }
    
    private void addTutorialCategoriesTable(DataModel dm, String title, String categories) throws TutorialException, SQLException {
        /* convert categories to an array of string ints */
        String[] categoryIds = parseCategories(categories);
        
        if (categoryIds.length > 0) {
            /* get the id of the tutorial added */
            List<String> statementParameters = new ArrayList<String>();
            statementParameters.add(title);
            List<Tutorial> tutorials = dm.getTutorialsForQuery(Tutorial.SELECT_TITLE, statementParameters);
            if (tutorials == null || tutorials.isEmpty()) {
                throw new TutorialException("addTutorialCategoriesTable - Unabled to locate tutorial for title: " + title);
            }
            String tutorial = tutorials.get(0).id();
            
            String insert = Tutorial.BATCH_INSERT_CATEGORIES;
            for (String category: categoryIds) {
                insert += "(" + tutorial + ", " + category + "),";
            }
            insert = insert.substring(0, insert.length() - 1);
            dm.executeUpdate(insert, null);
            dm.closeStatement();
        }
    }
}
