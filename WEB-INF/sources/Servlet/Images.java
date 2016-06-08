import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;

import java.io.File;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.util.List;
import java.util.ArrayList;

public class Images extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doPost(request, response);
    }

    // Same as http GET
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        File dir = new File("/var/lib/tomcat7/webapps/JakesTutorials/img");
        List<File> files = (List<File>) FileUtils.listFiles(dir, TrueFileFilter.INSTANCE, TrueFileFilter.INSTANCE);
        ArrayList<String> filenames = new ArrayList<String>();
        for (File file: files) {
            filenames.add(file.getName());
        }

        request.setAttribute("filenames", filenames);
        request.getRequestDispatcher("jsp/images.jsp").forward(request,response);
    }
}
