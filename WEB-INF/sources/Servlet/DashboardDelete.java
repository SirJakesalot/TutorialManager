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

@WebServlet("/dashboard_delete")
public class DashboardDelete extends HttpServlet {

    // Deletes tutorial from the database and prints a JSON message
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        // Used to write JSON to the screen
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        try {
            String tutorial_id = request.getParameter("tutorial_id");

            // Invalid tutorial id
            if (tutorial_id == null || tutorial_id.length() == 0 || tutorial_id.equals("-1")) {
                DataModel.log("ERROR", "DashboardDelete doPost - Invalid tutorial_id");
                out.println("[{\"message\":\"Invalid tutorial_id\"}]");
                return;
            }

            // Search query parameters
            ArrayList<String> statement_parameters = new ArrayList<String>();
            statement_parameters.add(tutorial_id);

            DataModel dm = new DataModel();
            int rows_affected = dm.executeUpdate(Tutorial.DELETE_ID, statement_parameters);
            dm.closeConnection();
             
            switch (rows_affected) {
                case 0:
                    DataModel.log("ERROR", "Nothing was deleted for tutorial_id = " + tutorial_id);
                    out.println("[{\"message\":\"Nothing was deleted for tutorial_id = " + tutorial_id + "\"}]"); 
                    break;
                case 1:
                    DataModel.log("Success", "Successfully deleted tutorial_id = " + tutorial_id);
                    out.println("[{\"message\":\"Successfully Deleted " + tutorial_id + "\"},{\"id\":\"" + tutorial_id + "\"}]"); 
                    break;
                default:
                    DataModel.log("ERROR", "Multiple rows were deleted for tutorial_id = " + tutorial_id);
                    out.println("[{\"message\":\"Multiple rows were deleted for tutorial_id = " + tutorial_id + "\"}]"); 
            }
        } catch (Exception e) {
            out.println("[{\"message\":\"ERROR: DashboardDelete\"}]"); 
            DataModel.log("ERROR", "DashboardDelete", e);
        } 
    }
}
