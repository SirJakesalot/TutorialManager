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

@WebServlet("/images")
public class Images extends HttpServlet {

    // Name of the directory where uploaded files are saved, relative to the web application directory.
    private static final String UPLOAD_DIR = "uploads";

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        // Gets absolute path of the web application
        String appPath = request.getServletContext().getRealPath("");
        // Constructs path of the directory with the uploaded files
        String uploadPath = appPath + File.separator + UPLOAD_DIR;

        // Do not entertain if no file has been uploaded yet
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            request.setAttribute("message", "No files have been uploaded yet!");
            request.getRequestDispatcher("jsp/images.jsp").forward(request,response);
            return; 
        }

        // Get list of files in the UPLOAD_DIR
        // TODO: Only get images
        List<File> files = (List<File>) FileUtils.listFiles(uploadDir, TrueFileFilter.INSTANCE, TrueFileFilter.INSTANCE);

        // Construct list of application paths for each file
        ArrayList<String> appPaths = new ArrayList<String>();
        for (File file: files) {
            appPaths.add(UPLOAD_DIR + File.separator + file.getName());
        }

        request.setAttribute("appPaths", appPaths);
        request.getRequestDispatcher("jsp/images.jsp").forward(request,response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doGet(request, response);
    }
}
