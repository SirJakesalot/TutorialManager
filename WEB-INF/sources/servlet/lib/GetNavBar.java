package tutorialdb_lib;

import tutorialdb_model.DataModel;
import tutorialdb_model.Logger;
import tutorialdb_model.Tutorial;
import tutorialdb_model.Category;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Gather all information needed for the navigation bar.
 * @author Jake Armentrout
 */
public class GetNavBar {
	/**
	 * Queries tutorialdb for all information needed to generate the
	 * navigation bar.
	 * @param dm Uses a DataModel object to communicate with tutorialdb
	 * @return List<Category> Categories with their associated tutorials
	 */
    public static List<Category> getCategories(DataModel dm) {
		/* safely close any open connections */
        dm.closeStatement();
        try {
			/* query tutorialdb for all available categories */
            List<Category> categories = dm.getCategoriesForQuery(Category.SELECT_ALL, null);
			
			/* categories query for all tutorials they are associated with */
            List<String> statement_parameters;
            for (Category category: categories) {
                dm.closeStatement();
                statement_parameters = new ArrayList<String>();
                statement_parameters.add(category.id());
                category.tutorials(dm.getTutorialsForQuery(Category.SELECT_TUTORIALS, statement_parameters));
            }
			
			/* safely close any open connections and return categories*/
            dm.closeStatement();
            return categories;
        } catch (Exception e) {
            Logger.log(Logger.Status.ERROR, "GetNavbar getCategories", e);
        }
		/* safely close any open connections */
		dm.closeStatement();
        return null;
    }
}
