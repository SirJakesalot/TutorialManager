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

public class SingleTutorial extends HttpServlet {

    // If http GET, populate an ArrayList with all Tutorial objects and redirect to the tutorials.jsp page
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        try {
            String tutorial_id = request.getParameter("tutorial_id");
            // Invalid tutorial id
            if (tutorial_id == null || tutorial_id.length() == 0) {
                response.sendRedirect("");
                return;
            }
            Tutorial tutorial = new Tutorial(tutorial_id);
            // Tutorial id doesn't exist
            if (tutorial.id() == null) {
                response.sendRedirect("");
                return;
            }

            String fbdata_url = request.getRequestURL() + "?" + request.getQueryString();
             
            request.setAttribute("tutorial", tutorial);
            request.setAttribute("fbdata_url", fbdata_url);
            
            request.getRequestDispatcher("jsp/single_tutorial.jsp").forward(request,response);

        } catch (Exception e) {

            response.setContentType("text/html");    // Response mime type

            // Output stream to STDOUT
            PrintWriter out = response.getWriter();
            out.println("<HTML><HEAD><TITLE>SingleTutorial Error</TITLE></HEAD>");
            out.println(e.toString());
            DataSource.logError("SingleTutorial doGet", e);
        } 
    }

    // Same as http GET
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doGet(request, response);
    }
}
