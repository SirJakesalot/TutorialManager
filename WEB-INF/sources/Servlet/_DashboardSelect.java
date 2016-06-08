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

public class _DashboardSelect extends HttpServlet {
    // If http GET, populate an ArrayList with all Tutorial objects and redirect to the tutorials.jsp page
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        try {
            String tutorial_id = request.getParameter("tutorial_id");
            // Invalid tutorial id
            if (tutorial_id == null || tutorial_id.length() == 0) {
                // Need to test this
                out.println("{error:\"invalid tutorial id\"}");
                return;
            }
            Tutorial tutorial = new Tutorial(tutorial_id);
            // Tutorial id doesn't exist
            if (tutorial.id() == null) {
                out.println("{error:\"invalid tutorial id\"}");
                return;
            }
            out.println("[" + tutorial.toJSON() + "]");

        } catch (Exception e) {
            out.println("{error:\"_DashboardSelect doGet " + e.toString() + "\"}");
        } 
    }

    // Same as http GET
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doGet(request, response);
    }
}
