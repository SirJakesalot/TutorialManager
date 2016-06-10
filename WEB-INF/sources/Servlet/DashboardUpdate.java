import tutorialdb_model.DataModel;
import tutorialdb_model.Tutorial;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

@WebServlet("/dashboard_update")
public class DashboardUpdate extends HttpServlet {

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        // Used to write JSON to the page
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        try {
            String tutorial_id = request.getParameter("tutorial_id");
            String tutorial_title = request.getParameter("tutorial_title");
            String tutorial_content = request.getParameter("tutorial_content");

            // Invalid tutorial id
            ArrayList<String> statement_parameters = new ArrayList<String>();
            if (tutorial_id == null || tutorial_id.length() == 0) {
                DataModel.log("ERROR", "DashboardUpdate doPost - Invalid tutorial_id");
                out.println("[{\"message\":\"Invalid tutorial_id\"}]"); 
                return;
            }

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
            DataModel dm = new DataModel();
            int rows_affected = dm.executeUpdate(update, statement_parameters);
            dm.closeStatement(); // leave connection open

            // Query for that record
            statement_parameters = new ArrayList<String>();
            statement_parameters.add(tutorial_title);

            ArrayList<Tutorial> tutorials = dm.getTutorialsForQuery(Tutorial.SELECT_TITLE, statement_parameters);
            dm.closeConnection();

            if (tutorials == null) {
                dm.log("ERROR", "DashboardUpdate doPost - Unable to find the last updated record for tutorial_id = " + tutorial_id);
                out.println("[{\"message\":\"Unable to find tutorial_id = " + tutorial_id + "\"}]"); 
                return;
            }
            String json = tutorials.get(0).toJSON();

            switch (rows_affected) {
                case 0:
                    DataModel.log("ERROR", "Nothing was updated for tutorial_id = " + tutorial_id);
                    out.println("[{\"message\":\"Nothing was updated for tutorial_id = " + tutorial_id + "\"}]"); 
                    break;
                case 1:
                    DataModel.log("SUCCESS", "Successfully updated");
                    out.println("[{\"message\":\"Successfully Updated tutorial_id = " + tutorial_id + "\"}," + json + "]"); 
                    break;
                default:
                    DataModel.log("ERROR", "Multiple rows were updated");
                    out.println("[{\"message\":\"Multiple rows were updated for tutorial_id = " + tutorial_id + "\"}]"); 
            }
        } catch (Exception e) {
            DataModel.log("ERROR", "DashboardUpdate doPost", e);
            out.println("{error:\"DashboardUpdate doPost " + e.toString() + "\"}");
        } 
    }
}
