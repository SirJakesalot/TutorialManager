import tutorialdb_model.DataSource;
import tutorialdb_model.Tutorial;

import java.io.*;
import java.net.*;
import java.sql.*;
import java.text.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.ArrayList;

public class _DashboardDelete extends HttpServlet {

    public String getServletInfo() {
        return "Servlet connects to MySQL database and displays result of a SELECT";
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("[{\"message\":\"Performing GET instead of POST\"}]"); 
        System.out.println("[{\"message\":\"Performing GET instead of POST\"}]"); 
    }

    // Same as http GET
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        try {
            String tutorial_id = request.getParameter("tutorial_id");

            // Invalid tutorial id
            ArrayList<String> statement_parameters = new ArrayList<String>();
            if (tutorial_id == null || tutorial_id.length() == 0 || tutorial_id.equals("-1")) {
                out.println("[{\"message\":\"Invalid tutorial_id\"}]"); 
                System.out.println("[{\"message\":\"Invalid tutorial_id\"}]"); 
                return;
            }

            String delete = "DELETE FROM tutorials WHERE id=?;";
            statement_parameters.add(tutorial_id);

            DataSource ds = new DataSource();
            int rows_affected = ds.executeUpdate(delete, statement_parameters);

            switch (rows_affected) {
                case 0:
                    out.println("[{\"message\":\"Nothing was deleted\"}]"); 
                    System.out.println("[{\"message\":\"Nothing was deleted\"}]"); 
                    break;
                case 1:
                    out.println("[{\"message\":\"Successfully Deleted " + tutorial_id + "\"},{\"id\":\"" + tutorial_id + "\"}]"); 
                    System.out.println("[{\"message\":\"Successfully Deleted " + tutorial_id + "\"},{\"id\":\"" + tutorial_id + "\"}]"); 
                    break;
                default:
                    out.println("[{\"message\":\"Multiple rows were deleted\"}]"); 
                    System.out.println("[{\"message\":\"Multiple rows were deleted\"}]"); 
            }
            ds.closeQuery();
        } catch (Exception e) {
            out.println("{error:\"_DashboardDelete doGet " + e.toString() + "\"}");
        } 
    }
}
