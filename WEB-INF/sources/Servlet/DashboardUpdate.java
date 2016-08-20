import tutorialdb_model.DataModel;
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
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;

@WebServlet("/dashboard_update")
public class DashboardUpdate extends HttpServlet {

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

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        // Used to write JSON to the page
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        try {
            String tutorial_id      = request.getParameter("tutorial_id");
            String tutorial_title   = request.getParameter("tutorial_title");
            String tutorial_content = request.getParameter("tutorial_content");
            String tutorial_associated_categories = request.getParameter("tutorial_associated_categories");
            Set<String> associated_categories = parse_categories(tutorial_associated_categories);
            for (String category: associated_categories) {
                System.out.println("associated_category: " + category);
            }
            
            // Invalid tutorial id
            if (tutorial_id == null || tutorial_id.length() == 0) {
                DataModel.log("ERROR", "DashboardUpdate doPost - Invalid tutorial_id");
                out.println("[{\"message\":\"Invalid tutorial_id\"}]"); 
                return;
            }
            // Invalid tutorial title
            if (tutorial_title == null || tutorial_title.length() == 0) {
                DataModel.log("ERROR", "DashboardUpdate doPost - Invalid tutorial_title");
                out.println("[{\"message\":\"Invalid tutorial_title\"}]"); 
                return;
            }

            DataModel dm = new DataModel();
            ArrayList<String> statement_parameters = new ArrayList<String>();
            String update = "";
            // Having an id of -1 signals that we are inserting a NEW Tutorial into the database
            // else update an existing tutorial
            if (tutorial_id.equals("-1")) {
                update = Tutorial.INSERT;
                statement_parameters.add(tutorial_title);
                statement_parameters.add(tutorial_content);
            } else {
                update = Tutorial.UPDATE_ID;
                statement_parameters.add(tutorial_title);
                statement_parameters.add(tutorial_content);
                statement_parameters.add(tutorial_id);
            }

            // Get number of records affected after update
            int rows_affected = dm.executeUpdate(update, statement_parameters);
            dm.closeStatement(); // leave connection open

            // Query for that record
            statement_parameters = new ArrayList<String>();
            statement_parameters.add(tutorial_title);

            ArrayList<Tutorial> tutorials = dm.getTutorialsForQuery(Tutorial.SELECT_TITLE, statement_parameters);
            dm.closeStatement();


            if (tutorials == null || tutorials.isEmpty()) {
                dm.log("ERROR", "DashboardUpdate doPost - Unable to find the last updated record for tutorial_id = " + tutorial_id);
                out.println("[{\"message\":\"Unable to find tutorial_id = " + tutorial_id + "\"}]"); 
                return;
            }

            
            Set<String> current_categories = new HashSet<String>();
            ArrayList<Category> tutorial_categories = tutorials.get(0).categories();
            if (tutorial_categories != null) {
                for (Category category: tutorial_categories) {
                    current_categories.add(category.id());
                    System.out.println("current_category: " + category.id());
                }
            }

            Set<String> associated_add = new HashSet<String>(associated_categories);
            associated_add.removeAll(current_categories);
            for (String category: associated_add) {
                System.out.println("associated_add: " + category);
            }

            Set<String> associated_remove = new HashSet<String>(current_categories);
            associated_remove.removeAll(associated_categories);
            for (String category: associated_remove) {
                System.out.println("associated_remove: " + category);
            }
            
           
 
            dm.closeConnection();



            switch (rows_affected) {
                case 0:
                    DataModel.log("ERROR", "Nothing was updated for tutorial_id = " + tutorial_id);
                    out.println("[{\"message\":\"Nothing was updated for tutorial_id = " + tutorial_id + "\"}]"); 
                    break;
                case 1:
                    DataModel.log("SUCCESS", "Successfully updated");
                    out.println("[{\"message\":\"Successfully Updated tutorial_id = " + tutorials.get(0).id() + "\"}," + tutorials.get(0).toJSON() + "]");
                    break;
                default:
                    DataModel.log("ERROR", "Multiple rows were updated");
                    out.println("[{\"message\":\"Multiple rows were updated for tutorial_id = " + tutorials.get(0).id() + "\"}]"); 
            }
        } catch (Exception e) {
            DataModel.log("ERROR", "DashboardUpdate doPost", e);
            out.println("{error:\"DashboardUpdate doPost " + e.toString() + "\"}");
        } 
    }
}
