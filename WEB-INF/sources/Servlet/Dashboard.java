import tutorialdb_model.DataModel;
import tutorialdb_model.Tutorial;
import tutorialdb_model.Category;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;

@WebServlet("/dashboard")
public class Dashboard extends HttpServlet {

    // Fetches tutorials from the database
    // TODO: Only fetch the tutorial id and title. Do not need the content
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        DataModel dm = new DataModel();
        ArrayList<Tutorial> tutorials = dm.getTutorialsForQuery(Tutorial.SELECT, null);
        ArrayList<Category> categories = dm.getCategoriesForQuery(Category.SELECT, null);
        dm.closeConnection();

        request.setAttribute("tutorials", tutorials);
        request.setAttribute("categories", categories);
        request.getRequestDispatcher("jsp/dashboard.jsp").forward(request,response);
    }

    // Deletes tutorial from the database and prints a JSON message
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doGet(request,response);
    }
}
