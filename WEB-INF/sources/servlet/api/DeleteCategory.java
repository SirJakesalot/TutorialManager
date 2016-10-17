package tutorialdb_api;

import tutorialdb_model.DataModel;
import tutorialdb_model.Logger;
import tutorialdb_model.Category;
import tutorialdb_model.TutorialException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

@WebServlet("/api/deletecategory")
public class DeleteCategory extends HttpServlet {

    // Deletes tutorial from the database and prints a JSON message
    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        
        
        PrintWriter out = null;
        /* used for communicating with tutorialdb */
        DataModel dm = null;

        try {
            
            /* used to write JSON to the page */
            response.setContentType("application/json");
            out = response.getWriter();
            
            String id = request.getParameter("id");
            /* invalid tutorial id */
            if (id == null || id.length() == 0) {
                throw new TutorialException("tutorial id is either null or empty");
            }

            dm = new DataModel();
            
            /* parameters for the tutorial delete statement */
            ArrayList<String> statementParameters = new ArrayList<String>();
            statementParameters.add(id);
            
            int rowsAffected = dm.executeUpdate(Category.DELETE_ID, statementParameters);
            dm.closeConnection();

            switch (rowsAffected) {
                case 0:
                    out.println(Logger.log(Logger.Status.ERROR, "nothing was deleted for id = " + id));
                    break;
                case 1:
                    out.println(Logger.log(Logger.Status.SUCCESS, "successfully deleted id = " + id));
                    break;
                default:
                    out.println(Logger.log(Logger.Status.ERROR, "multiple rows were deleted for id = " + id));
            }
        } catch(TutorialException te) {
            out.println(Logger.log(Logger.Status.ERROR, te.getMessage()));
        } catch (Exception e) {
            out.println(Logger.log(Logger.Status.ERROR, "DeleteCategory", e));
        } finally {
            if (dm != null) { dm.closeConnection(); }
            out.close();
        }
    }
}
