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
    public static List<Category> getCategories(DataModel dm) throws Exception {
        dm.closeStatement();
        /* query tutorialdb for all available categories */
        List<Category> categories = dm.getCategoriesForQuery(Category.SELECT_ALL, null);
        
        /* categories query for all tutorials they are associated with */
        List<String> statementParameters;
        for (Category category: categories) {
            dm.closeStatement();
            statementParameters = new ArrayList<String>();
            statementParameters.add(category.id());
            category.tutorials(dm.getTutorialsForQuery(Category.SELECT_TUTORIALS, statementParameters));
        }
        dm.closeStatement();
        return categories;
    }
}
