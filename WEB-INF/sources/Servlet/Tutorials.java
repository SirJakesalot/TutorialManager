import tutorialdb_model.DataModel;
import tutorialdb_model.Tutorial;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("")
public class Tutorials extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        try {
            DataModel dm = new DataModel();
            ArrayList<Tutorial> tutorials = dm.getTutorialsForQuery(Tutorial.SELECT, null);
            dm.closeConnection();

            request.setAttribute("tutorials", tutorials);
            request.getRequestDispatcher("jsp/tutorials.jsp").forward(request,response);

        } catch (Exception e) {
            DataModel.log("ERROR", "Tutorials", e);
        } 
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doGet(request, response);
    }
}
