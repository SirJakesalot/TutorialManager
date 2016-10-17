package tutorial_site;

import tutorialdb_model.DataModel;
import tutorialdb_model.Category;
import tutorialdb_model.Logger;
import tutorialdb_lib.GetNavBar;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;

import java.io.File;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.annotation.WebServlet;
import java.util.List;
import java.util.ArrayList;

/**
 * Images servlet gathers the information required for the images page.
 * @author Jake Armentrout
 */
 
/* this servlet is accessed by the url ${context}/images */
@WebServlet("/images")


public class Images extends HttpServlet {

    /* directory where uploaded files are saved */
    private static final String UPLOAD_DIR = "uploads";

	/**
	 * A GET request will popoulate the sidebar and forward to main.jsp.
	 * @param request HttpServletRequest for the web page
	 * @param response HttpServletResponse for the request
	 */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        /* communicates with tutorialdb */
        DataModel dm = null;
        try {
            /* absolute path of the web application */
            String appPath = request.getServletContext().getRealPath("");
            /* local upload directory */
            String localUploadPath = appPath + File.separator + UPLOAD_DIR;

            /* check if directory exists */
            // TODO: send message in msgs div
            File uploadDir = new File(localUploadPath);
            if (!uploadDir.exists()) {
                request.setAttribute("message", "No files have been uploaded yet!");
                request.getRequestDispatcher("jsp/images.jsp").forward(request,response);
                return; 
            }

            // TODO: Only get images
            /* list of files in the upload directory */
            List<File> files = (List<File>) FileUtils.listFiles(uploadDir, TrueFileFilter.INSTANCE, TrueFileFilter.INSTANCE);

            List<String> fileNames = new ArrayList<String>();
            for (File file: files) {
                fileNames.add(file.getName());
            }
            
            dm = new DataModel();
            /* gather all navbar category objects */
            List<Category> categories = GetNavBar.getCategories(dm);
            dm.closeStatement();
            
            /* set page's parameters */
            request.setAttribute("title", "Images");
            request.setAttribute("uploadDir", UPLOAD_DIR);
            request.setAttribute("fileNames", fileNames);
            request.setAttribute("categories", categories);
            
            request.getRequestDispatcher("jsp/images.jsp").forward(request,response);
        } catch (Exception e) {
            Logger.log(Logger.Status.ERROR, "Images", e);
            request.setAttribute("msg", e.getMessage());
            request.getRequestDispatcher("jsp/error.jsp").forward(request,response);
        } finally {
            if (dm != null) { dm.closeConnection(); }
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doGet(request, response);
    }
}
