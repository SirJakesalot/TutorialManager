import tutorialdb_model.DataModel;
import tutorialdb_model.Tutorial;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;

@WebServlet("/tutorial")
public class SingleTutorial extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        try {
            String tutorial_id = request.getParameter("tutorial_id");

            // Invalid tutorial id
            if (tutorial_id == null || tutorial_id.length() == 0) {
                DataModel.log("WARNING", "Single Tutorial - Absent tutorial id");
                response.sendRedirect("");
                return;
            }
            
            // Parameters for the search query
            ArrayList<String> statement_parameters = new ArrayList<String>();
            statement_parameters.add(tutorial_id);

            DataModel dm = new DataModel();
            ArrayList<Tutorial> tutorials = dm.getTutorialsForQuery(Tutorial.SELECT_ID, statement_parameters);
            dm.closeConnection();

            // Tutorial id doesn't exist
            if (tutorials == null) {
                DataModel.log("WARNING", "Single Tutorial - Invalid tutorial id");
                response.sendRedirect("");
                return;
            }

            // Tell Facebook to listen/remember for comments on this page
            String fbdata_url = request.getRequestURL() + "?" + request.getQueryString();
             
            request.setAttribute("tutorial", tutorials.get(0));
            request.setAttribute("fbdata_url", fbdata_url);
            request.getRequestDispatcher("jsp/tutorial.jsp").forward(request,response);

        } catch (Exception e) {
            DataModel.log("ERROR", "SingleTutorial", e);
        } 
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doGet(request, response);
    }
}
