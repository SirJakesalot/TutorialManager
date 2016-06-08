import tutorialdb_model.DataSource;
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
            ArrayList<Tutorial> tutorials = DataSource.getTutorialsForQuery("SELECT id,title,UNCOMPRESS(content) as content FROM tutorials;", new ArrayList<String>());

            request.setAttribute("tutorials", tutorials);
            request.getRequestDispatcher("jsp/tutorials.jsp").forward(request,response);

        } catch (Exception e) {
            e.printStackTrace();

        } 
    }

    // Same as http GET
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doGet(request, response);
    }
}
