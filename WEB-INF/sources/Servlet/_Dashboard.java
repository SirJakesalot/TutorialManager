import tutorialdb_model.DataSource;
import tutorialdb_model.Comment;
import tutorialdb_model.Tutorial;

import java.io.*;
import java.net.*;
import java.sql.*;
import java.text.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.ArrayList;

public class _Dashboard extends HttpServlet {

    // If http GET, populate an ArrayList with all Tutorial objects and redirect to the tutorials.jsp page
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        try {
            ArrayList<Tutorial> tutorials = DataSource.getTutorialsForQuery("SELECT id,title,UNCOMPRESS(content) AS content FROM tutorials ORDER BY title", new ArrayList<String>());
            request.setAttribute("tutorials", tutorials);
            request.getRequestDispatcher("jsp/_dashboard.jsp").forward(request,response);

        } catch (Exception e) {
            response.setContentType("text/html");    // Response mime type

            // Output stream to STDOUT
            PrintWriter out = response.getWriter();
            out.println("<HTML><HEAD><TITLE>Tutorials Error</TITLE></HEAD>");
            out.println(e.toString());
            DataSource.logError("Tutorials doGet", e);
        } 
    }

    // Same as http GET
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doGet(request, response);
    }
}
