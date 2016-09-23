package tutorialdb_model;

import java.util.List;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.commons.lang3.StringEscapeUtils;

/**
 * Tutorial is a model of a row in the 'tutorials' table with its associated
 * categories in the 'tutorial_categories' table. It also stores its own SQL
 * statements and can convert itself to JSON.
 * @author Jake Armentrout
 */
public class Tutorial {
    /* tutorial columns */
    private String id;
    private String title;
    private String content;
    /* all categories associated with tutorial */
    private List<Category> categories;

    /* SQL statements that are performed on the database */
    public static final String SELECT_ALL   = "SELECT id,title FROM tutorials ORDER BY title ASC;";
    public static final String SELECT_ID    = "SELECT id,title,UNCOMPRESS(content) as content FROM tutorials WHERE id=? LIMIT 1;";
    public static final String SELECT_TITLE = "SELECT id,title,UNCOMPRESS(content) as content FROM tutorials WHERE title=? LIMIT 1;";
    public static final String INSERT       = "INSERT INTO tutorials(title,content) VALUES (?,COMPRESS(?));";
    public static final String UPDATE_ID    = "UPDATE tutorials SET title=?,content=COMPRESS(?) WHERE id=?;";
    public static final String DELETE_ID    = "DELETE FROM tutorials WHERE id=?;";
    public static final String SELECT_CATEGORIES = "SELECT * FROM categories WHERE id IN (SELECT category_id FROM tutorial_categories WHERE tutorial_id=?) ORDER BY name ASC;";

    /**
     * Tutorial is constructed with a row from a ResultSet that was returned
     * from a query.
     * @param rs The current cursor in the ResultSet
     */
    public Tutorial(ResultSet rs) {
        try {
            this.id    = rs.getString("id");
            this.title = rs.getString("title");
        } catch (SQLException se) {
            Logger.log(Logger.Status.ERROR, "Tutorial ResultSet", se);
        }
        try {
            this.content = rs.getString("content");
        } catch (SQLException se) {
			/* do nothing, content is not necessary for a Tutorial object */
        }
    }

    /* tutorial get functions */
    public String id()      { return this.id; }
    public String title()   { return this.title; }
    public String content() { return this.content; }
    public List<Category> categories() { return this.categories; }

    /* tutorial set functions */
    public String id(String new_id)           { this.id = new_id; return id(); }
    public String title(String new_title)     { this.title = new_title; return title(); }
    public String content(String new_content) { this.content = new_content; return content(); }
    public List<Category> categories(List<Category> new_categories) {
        this.categories = new_categories;
        return this.categories();
    }

    /**
     * Convert this Tutorial object to a JSON string.
     * @return String JSON string with escaped special characters for the name
     */
    public String toJSON() {
        /* id and title should always be set */
        if (id() == null) {
            return Logger.log(Logger.Status.ERROR, "Tutorial toJSON: id() cannot be null");
        } else if (title() == null || title().trim().length() == 0) {
            return Logger.log(Logger.Status.ERROR, "Tutorial toJSON: title() cannot be null or empty");
        }

        return "{\"id\":\"" + id() + "\"," +
                "\"title\":\"" + title() + "\"," +
                "\"categories\":" + Category.toJSON(categories()) + "," +
                "\"content\":\"" + StringEscapeUtils.escapeJava(content()) + "\"}";
    }
	/**
	 * Convert a list of Tutorial objects to a JSON string.
	 * @param tutorials A list of tutorials
	 * @return String of json representing the array of tutorials.
	 */
    public static String toJSON(List<Tutorial> tutorials) {
      if (tutorials == null || tutorials.isEmpty()) {
          return "[]";
      }
      String json = "";
      for (Tutorial tutorial: tutorials) {
          json += tutorial.toJSON() + ",";
      }
      /* remove trailing comma */
      return "[" + json.substring(0, json.length() - 1) + "]";
    }
}
