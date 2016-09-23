package tutorialdb_api;

import tutorialdb_model.DataModel;
import tutorialdb_model.Logger;
import tutorialdb_model.Tutorial;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

@WebServlet("/tutorial_delete")
public class TutorialDelete extends HttpServlet {

    // Deletes tutorial from the database and prints a JSON message
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        // Used to write JSON to the screen
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        try {
            String id = request.getParameter("id");

            // Invalid tutorial id
            if (id == null || id.length() == 0 || id.equals("-1")) {
                out.println(Logger.log(Logger.Status.ERROR, "TutorialDelete doPost - Invalid id"));
                //out.println("[{\"message\":\"Invalid id\"}]");
                return;
            }

            // Search query parameters
            ArrayList<String> statement_parameters = new ArrayList<String>();
            statement_parameters.add(id);

            DataModel dm = new DataModel();
            int rows_affected = dm.executeUpdate(Tutorial.DELETE_ID, statement_parameters);
            dm.closeConnection();

            switch (rows_affected) {
                case 0:
                    out.println(Logger.log(Logger.Status.ERROR, "Nothing was deleted for id = " + id));
                    //out.println("[{\"message\":\"Nothing was deleted for id = " + id + "\"}]");
                    break;
                case 1:
                    out.println(Logger.log(Logger.Status.SUCCESS, "Successfully deleted id = " + id));
                    //out.println("[{\"message\":\"Successfully Deleted " + id + "\"},{\"id\":\"" + id + "\"}]");
                    break;
                default:
                    out.println(Logger.log(Logger.Status.ERROR, "Multiple rows were deleted for id = " + id));
                    //out.println("[{\"message\":\"Multiple rows were deleted for id = " + id + "\"}]");
            }
        } catch (Exception e) {
            //out.println("[{\"message\":\"ERROR: DashboardDelete\"}]");
            out.println(Logger.log(Logger.Status.ERROR, "TutorialDelete", e));
        }
    }
}
