package tutorialdb_api;

import tutorialdb_model.DataModel;
import tutorialdb_model.Logger;
import tutorialdb_model.Tutorial;
import tutorialdb_model.Category;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;
import org.json.simple.parser.JSONParser;

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
 
/* this servlet is accessed by the url ${context}/tutorial_update */
@WebServlet("/tutorial_update")


public class TutorialUpdate extends HttpServlet {

	/**
	 * parse_categories will convert a json string to a set of ints.
	 * @param json A json string representing an array of category ids
	 * @return Set<String> A set of category ids
	 */
    private Set<String> parse_categories(String json) {
        Set<String> categories = new HashSet<String>();
        if (json != null && json.length() > 2) {
            String[] ids = json.substring(1, json.length() - 1).split(",");
            for (String id: ids) {
                categories.add(id);
            }
        }
        return categories;
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

        try {
			/* request parameters */
            String id         = request.getParameter("id");
            String title      = request.getParameter("title");
            String content    = request.getParameter("content");
            String categories = request.getParameter("categories");
            /* convert categories to a set of ints */
            Set<String> associated_categories = parse_categories(categories);

            /* invalid tutorial id */
            if (id == null || id.length() == 0) {
                out.println(Logger.log(Logger.Status.ERROR, "TutorialUpdate doPost - Invalid tutorial_id"));
                return;
            }
            /* invalid tutorial title */
            if (title == null || title.length() == 0) {
                out.println(Logger.log(Logger.Status.ERROR, "TutorialUpdate doPost - Invalid tutorial_title"));
                return;
            }

			/* used for communicating with tutorialdb */
            DataModel dm = new DataModel();
			
			/* parameters for the tutorial update statement */
            List<String> statement_parameters = new ArrayList<String>();
            String update = "";
            // Having an id of -1 signals that we are inserting a NEW Tutorial into the database
            // else update an existing tutorial
            if (id.equals("-1")) {
                update = Tutorial.INSERT;
                statement_parameters.add(title);
                statement_parameters.add(content);
            } else {
                update = Tutorial.UPDATE_ID;
                statement_parameters.add(title);
                statement_parameters.add(content);
                statement_parameters.add(id);
            }

            // Get number of records affected after update
            int rows_affected = dm.executeUpdate(update, statement_parameters);
            dm.closeStatement(); // leave connection open

            // Query for that record
            statement_parameters = new ArrayList<String>();
            statement_parameters.add(title);

            List<Tutorial> tutorials = dm.getTutorialsForQuery(Tutorial.SELECT_TITLE, statement_parameters);
            dm.closeStatement();
            if (tutorials == null || tutorials.isEmpty()) {
                out.println(Logger.log(Logger.Status.ERROR, "TutorialUpdate doPost - Unable to find the last updated record for tutorial_id = " + id));
				dm.closeConnection();
                return;
            }
			Tutorial tutorial = tutorials.get(0);
			statement_parameters = new ArrayList<String>();
            statement_parameters.add(tutorial.id());
			tutorial.categories(dm.getCategoriesForQuery(Tutorial.SELECT_CATEGORIES, statement_parameters));
            dm.closeStatement();
			
			Set<String> current_categories = new HashSet<String>();
            List<Category> tutorial_categories = tutorial.categories();
            if (tutorial_categories != null) {
                for (Category category: tutorial_categories) {
                    current_categories.add(category.id());
                    System.out.println("current_category: " + category.id());
                }
            }

            Set<String> associated_add = new HashSet<String>(associated_categories);
            associated_add.removeAll(current_categories);
			if (associated_add != null && associated_add.size() > 0) {
				String add = "INSERT INTO tutorial_categories (tutorial_id, category_id) VALUES";
				for (String category: associated_add) {
					add += "(" + tutorial.id() + ", " + category + "),";
				}
				add = add.substring(0, add.length() - 1);
				dm.executeUpdate(add, null);
				dm.closeStatement();
			}
            for (String category: associated_add) {
                System.out.println("associated_add: " + category);
            }
			
            Set<String> associated_remove = new HashSet<String>(current_categories);
            associated_remove.removeAll(associated_categories);
			if (associated_remove != null && associated_remove.size() > 0) {
				String rm = "DELETE FROM tutorial_categories WHERE (tutorial_id, category_id) in (";
				for (String category: associated_remove) {
					rm += "(" + tutorial.id() + "," + category + "),";
				}
				rm = rm.substring(0, rm.length() - 1) + ")";
				dm.executeUpdate(rm, null);
				dm.closeStatement();
			}
            for (String category: associated_remove) {
                System.out.println("associated_remove: " + category);
            }

            dm.closeConnection();

            switch (rows_affected) {
                case 0:
                    out.println(Logger.log(Logger.Status.ERROR, "Nothing was updated for tutorial_id = " + id));
                    break;
                case 1:
                    out.println(Logger.log(Logger.Status.SUCCESS, "Successfully updated"));
                    break;
                default:
                    out.println(Logger.log(Logger.Status.ERROR, "Multiple rows were updated"));
            }
        } catch (Exception e) {
            out.println(Logger.log(Logger.Status.ERROR, "TutorialUpdate doPost", e));
        }
    }
}
