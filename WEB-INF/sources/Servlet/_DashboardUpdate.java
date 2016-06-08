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

public class _DashboardUpdate extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doPost(request, response);
    }

    // Same as http GET
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        try {
            String tutorial_id = request.getParameter("tutorial_id");
            String tutorial_title = request.getParameter("tutorial_title");
            String tutorial_content = request.getParameter("tutorial_content");

            // Invalid tutorial id
            String update;
            ArrayList<String> statement_parameters = new ArrayList<String>();
            if (tutorial_id == null || tutorial_id.length() == 0) {
                out.println("[{\"message\":\"Invalid tutorial_id\"}]"); 
                System.out.println("[{\"message\":\"Invalid tutorial_id\"}]"); 
                return;
            }
            if (tutorial_id.equals("-1")) {
                // Insert
                update = "INSERT INTO tutorials(title,content) VALUES (?,COMPRESS(?));";
                statement_parameters.add(tutorial_title);
                statement_parameters.add(tutorial_content);
            } else {
                update = "UPDATE tutorials SET title=?,content=COMPRESS(?) WHERE id=?;";
                statement_parameters.add(tutorial_title);
                statement_parameters.add(tutorial_content);
                statement_parameters.add(tutorial_id);
            }
            DataSource ds = new DataSource();
            int rows_affected = ds.executeUpdate(update, statement_parameters);

            statement_parameters = new ArrayList<String>();
            statement_parameters.add(tutorial_title);
            ds.executeQuery("SELECT id,title,UNCOMPRESS(content) AS content FROM tutorials WHERE title=? LIMIT 1;", statement_parameters);
            Tutorial tutorial; 
            String json = "";
            try {
                // If the query was not empty
                if (ds.rs.isBeforeFirst()) {
                    ds.rs.next();
                    tutorial = new Tutorial(ds.rs); 
                    json = tutorial.toJSON();
                }
            } catch (SQLException se) {
                DataSource.logError("ERROR: _DashboardUpdate getting the tutorial after update. ", se);
            } finally {
                ds.closeQuery();
            }

            switch (rows_affected) {
                case 0:
                    out.println("[{\"message\":\"Nothing was updated\"}]"); 
                    System.out.println("[{\"message\":\"Nothing was updated\"}]"); 
                    break;
                case 1:
                    out.println("[{\"message\":\"Successfully Updated\"}," + json + "]"); 
                    System.out.println("[{\"message\":\"Successfully Updated\"}," + json + "]"); 
                    break;
                default:
                    out.println("[{\"message\":\"Multiple rows were updated\"}]"); 
                    System.out.println("[{\"message\":\"Multiple rows were updated\"}]"); 
            }
            ds.closeQuery();
        } catch (Exception e) {
            out.println("{error:\"_DashboardUpdate doPost " + e.toString() + "\"}");
        } 
    }
}
