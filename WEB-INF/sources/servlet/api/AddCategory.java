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
 
@WebServlet("/api/addcategory")


public class AddCategory extends HttpServlet {

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
            String name = request.getParameter("name");

            /* invalid tutorial name */
            if (name == null || name.length() == 0) {
                throw new ServletException("null or empty category name");
            }
            
            /* used for communicating with tutorialdb */
            dm = new DataModel();
            addCategoryTable(dm, name);
            out.println(Logger.log(Logger.Status.SUCCESS, "Successfully added category: " + name));
        } catch(TutorialException te) {
            out.println(Logger.log(Logger.Status.ERROR, te.getMessage()));
        } catch (Exception e) {
            out.println(Logger.log(Logger.Status.ERROR, "AddCategory", e));
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
    
    private void addCategoryTable(DataModel dm, String name) throws TutorialException, SQLException {
        
        /* check if category already exists */
        List<String> statementParameters = new ArrayList<String>();
        statementParameters.add(name);
        int categoryCount = dm.getAggregateQuery(Category.COUNT_SELECT_NAME, statementParameters);
        dm.closeStatement();
        if (categoryCount > 0) {
            /* category already exist */
            throw new TutorialException("addCategoryTable - category already exists: " + name);
        }
        
        /* get number of records updated by insert */
        int rowsUpdated = dm.executeUpdate(Category.INSERT, statementParameters);
        dm.closeStatement();
        if (rowsUpdated < 1) {
            throw new TutorialException("addCategoryTable - insert was not successful");
        } else if (rowsUpdated > 1) {
            throw new TutorialException("addCategoryTable - " + rowsUpdated + " records were affected");
        }
    }
}
