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

@WebServlet("/dashboard_select")
public class DashboardSelect extends HttpServlet {

    // ONLY USED FOR DEBUGGING!!!
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doPost(request,response);
    }
    // Fetches tutorial from the database and prints a JSON message
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        // Used for writing JSON
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        try {
            String tutorial_id = request.getParameter("tutorial_id");
            // Invalid tutorial id
            if (tutorial_id == null || tutorial_id.length() == 0) {
                out.println("{error:\"invalid tutorial id\"}");
                return;
            }

            // Parameters for the search query
            ArrayList<String> statement_parameters = new ArrayList<String>();
            statement_parameters.add(tutorial_id);

            DataModel dm = new DataModel();
            ArrayList<Tutorial> tutorials = dm.getTutorialsForQuery(Tutorial.SELECT_ID, statement_parameters);
            dm.closeConnection();

            out.println("[" + tutorials.get(0).toJSON() + "]");

        } catch (Exception e) {
            out.println("{error:\"DashboardSelect doGet " + e.toString() + "\"}");
        } 
    }
}
