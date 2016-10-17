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

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;

/**
 * TutorialUpdate servlet updates information for a given tutorial's info.
 * @author Jake Armentrout
 */
 
/* this servlet is accessed by the url ${context}/tutorialUpdate */
@WebServlet("/updateTutorial")


public class UpdateTutorial extends HttpServlet {

	/**
	 * parseCategories will convert a json string to a set of string ints.
	 * @param json A json string representing an array of category ids
	 * @return Set<String> A set of category ids
	 */
    private Set<String> parseCategories(String json) {
        Set<String> categories = new HashSet<String>();
        if (json != null && json.length() > 2) {
            String[] ids = json.substring(1, json.length() - 1).split(",");
            for (String id: ids) {
                categories.add(id);
            }
        }
        return categories;
    }
    
    private void updateTutorialTable(DataModel dm, String id, String title, String content) {
        /* parameters for the tutorial update statement */
        List<String> statementParameters = new ArrayList<String>();
        statementParameters.add(title);
        statementParameters.add(content);
        statementParameters.add(id);

        /* number of tutorials affected by update */
        int tutorialsUpdated = dm.executeUpdate(Tutorial.UPDATE_ID, statementParameters);
        dm.closeStatement();
    }
    
    private void updateTutorialCategoryTable(DataModel dm, String id, String categories) {
        /* get the tutorial and all of its categories */
        List<String> statementParameters = new ArrayList<String>();
        statementParameters.add(id);
        Tutorial tutorial = dm.getTutorialsForQuery(Tutorial.SELECT_ID, statementParameters).get(0);
        dm.closeStatement();
        tutorial.categories(dm.getCategoriesForQuery(Tutorial.SELECT_CATEGORIES, statementParameters));
        dm.closeStatement();
        
        /* convert tutorial categories into a set */
        Set<String> currentCategories = new HashSet<String>();
        if (tutorial.categories() != null) {
            for (Category category: tutorial.categories()) {
                currentCategories.add(category.id());
            }
        }

        /* convert categories to a set of ints */
        Set<String> newCategories = parseCategories(categories);
        
        /* subtract the currentCategories from the newCategories to get
           what categories should be inserted */
        Set<String> categoriesToInsert = new HashSet<String>(newCategories);
        categoriesToInsert.removeAll(currentCategories);
        
        if (categoriesToInsert != null && categoriesToInsert.size() > 0) {
            /* batch insert each category */
            String insert = Tutorial.BATCH_INSERT_CATEGORIES;
            for (String category: categoriesToInsert) {
                insert += "(" + tutorial.id() + ", " + category + "),";
            }
            insert = insert.substring(0, insert.length() - 1);
            dm.executeUpdate(insert, null);
            dm.closeStatement();
        }
        
        /* subtract the newCategories from the currentCategories to get
           what categories should be deleted */
        Set<String> categoriesToDelete = new HashSet<String>(currentCategories);
        categoriesToDelete.removeAll(newCategories);
        if (categoriesToDelete != null && categoriesToDelete.size() > 0) {
            /* batch delete each category */
            String delete = Tutorial.BATCH_DELETE_CATEGORIES;
            for (String category: categoriesToDelete) {
                delete += "(" + tutorial.id() + "," + category + "),";
            }
            delete = delete.substring(0, delete.length() - 1);
            delete = "(" + delete + ")";
            dm.executeUpdate(delete, null);
            dm.closeStatement();
        }
    }

	/**
	 * A POST request will update the information for a tutorial.
	 * @param request HttpServletRequest for the web page
	 * @param response HttpServletResponse for the request
	 */
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        /* used to write JSON to the page */
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        /* used for communicating with tutorialdb */
        DataModel dm = null;

        try {
            /* request parameters */
            String id         = request.getParameter("id");
            String title      = request.getParameter("title");
            String content    = request.getParameter("content");
            String categories = request.getParameter("categories");
            
            /* invalid tutorial id */
            if (id == null || id.length() == 0) {
                throw new TutorialException("null or empty tutorial id");
            }
            /* invalid tutorial title */
            if (title == null || title.length() == 0) {
                throw new TutorialException("UpdateTutorial doPost - null or empty tutorial title");
            }

            dm = new DataModel();
			updateTutorialTable(dm, id, title, content);
            updateTutorialCategoryTable(dm, id, categories);
            out.println(Logger.log(Logger.Status.SUCCESS, "Successfully updated tutorial: " + title));

        } catch(TutorialException te) {
            out.println(Logger.log(Logger.Status.ERROR, te.getMessage()));
        } catch (Exception e) {
            out.println(Logger.log(Logger.Status.ERROR, "UpdateTutorial doPost", e));
        } finally {
            if (dm != null) { dm.closeConnection(); }
            out.close();
        }
    }
}
