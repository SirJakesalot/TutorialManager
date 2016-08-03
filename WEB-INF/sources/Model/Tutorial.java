package tutorialdb_model;

import java.util.ArrayList;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.commons.lang3.StringEscapeUtils;

public class Tutorial {
    // Tutorial variables
    private String id;
    private String title;
    private String content;
    private ArrayList<Category> categories;

    // Query statements that are performed on the database
    public static final String SELECT       = "SELECT id,title,UNCOMPRESS(content) as content FROM tutorials ORDER BY title ASC;";
    public static final String SELECT_ID    = "SELECT id,title,UNCOMPRESS(content) as content FROM tutorials WHERE id=? LIMIT 1;";
    public static final String SELECT_TITLE = "SELECT id,title,UNCOMPRESS(content) as content FROM tutorials WHERE title=? LIMIT 1;";
    public static final String INSERT       = "INSERT INTO tutorials(title,content) VALUES (?,COMPRESS(?));"; 
    public static final String UPDATE_ID    = "UPDATE tutorials SET title=?,content=COMPRESS(?) WHERE id=?;"; 
    public static final String DELETE_ID    = "DELETE FROM tutorials WHERE id=?;";
    public static final String SELECT_CATEGORIES = "SELECT * FROM categories WHERE id IN (SELECT category_id FROM tutorial_categories WHERE tutorial_id=?);";
    
    public Tutorial(ResultSet rs) {
        try {
            this.id      = rs.getString("id");
            this.title   = rs.getString("title");
            this.content = rs.getString("content");
        } catch (SQLException se) {
            DataModel.log("ERROR", "Tutorial resultset", se);
        }
    }

    // Tutorial get functions
    public String id()      { return id; }
    public String title()   { return title; }
    public String content() { return content; }
    public ArrayList<Category> categories() { return categories; }

    // Tutorial set functions
    public String id(String new_id)           { id = new_id; return id(); }
    public String title(String new_title)     { title = new_title; return title(); }
    public String content(String new_content) { content = new_content; return content(); }
    public ArrayList<Category> categories(ArrayList<Category> new_categories) { categories = new_categories; return categories(); }

    // Converts Tutorial object to JSON while escaping special characters in the content
    public String toJSON() {
        String categories_json = "[";
        // Add category objects
        if (categories != null && !categories.isEmpty()) {
            for (Category category: categories) {
                categories_json += category.toJSON() + ",";
            }
            // Remove trailing comma
            categories_json = categories_json.substring(0,categories_json.length() - 1);
        } else {
            DataModel.log("ERROR", "No categories for tutorial with id=" + id());
        }
        categories_json += "]";

        return "{\"id\":\"" + id + "\"," + 
                "\"title\":\"" + title + "\"," +
                "\"categories\":" + categories_json + "," +
                "\"content\":\"" + StringEscapeUtils.escapeJava(content) + "\"}";
    }
}
