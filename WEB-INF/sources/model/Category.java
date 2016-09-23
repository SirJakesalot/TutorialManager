package tutorialdb_model;

import java.util.List;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.commons.lang3.StringEscapeUtils;

/**
 * Category is a model of a row in the 'categories' table. It also stores its
 * own SQL statements and can convert itself to JSON.
 * @author Jake Armentrout
 */
public class Category {
    /* category columns */
    private String id;
    private String name;
    private List<Tutorial> tutorials;

    /* SQL statements that are performed on the database */
    public static final String SELECT_ALL= "SELECT * FROM categories ORDER BY name ASC;";
    public static final String SELECT_ID = "SELECT * FROM categories WHERE id=? LIMIT 1;";
    public static final String INSERT    = "INSERT INTO categories(name) VALUES (?);";
    public static final String UPDATE_ID = "UPDATE categorys SET name=? WHERE id=?;";
    public static final String DELETE_ID = "DELETE FROM categories WHERE id=?;";
    public static final String SELECT_TUTORIALS = "SELECT * FROM tutorials WHERE id IN (SELECT tutorial_id FROM tutorial_categories WHERE category_id=?) ORDER BY title ASC;";

    /**
     * Category is constructed with a row from a ResultSet that was returned
     * from a query.
     * @param rs The current cursor in the ResultSet
     */
    public Category(ResultSet rs) {
        try {
            this.id   = rs.getString("id");
            this.name = rs.getString("name");
        } catch (SQLException se) {
            Logger.log(Logger.Status.ERROR, "Category ResultSet", se);
        }
    }

    /* category get functions */
    public String id()   { return this.id; }
    public String name() { return this.name; }
    public List<Tutorial> tutorials() { return this.tutorials; }

    /* category set functions */
    public String id(String new_id)     { this.id = new_id; return id(); }
    public String name(String new_name) { this.name = new_name; return name(); }
    public List<Tutorial> tutorials(List<Tutorial> new_tutorials) { this.tutorials = new_tutorials; return this.tutorials; }

    /**
     * Convert this Category object to a JSON string.
     * @return String JSON string with escaped special characters for the name
     */
    public String toJSON() {
        /* id and name should always be set */
        if (id() == null) {
            return Logger.log(Logger.Status.ERROR, "Category toJSON: id() cannot be null");
        } else if (name() == null || name().trim().length() == 0) {
            return Logger.log(Logger.Status.ERROR, "Category toJSON: name() cannot be null or empty");
        }

        return "{\"id\":\"" + id() + "\"," +
                "\"tutorials\":" + Tutorial.toJSON(tutorials()) + "," +
                "\"name\":\"" + StringEscapeUtils.escapeJava(name()) + "\"}";

    }

	/**
	 * Convert a list of Category objects to a JSON string.
	 * @param categories A list of categories
	 * @return String of json representing the array of categories
	 */
    public static String toJSON(List<Category> categories) {
      if (categories == null || categories.isEmpty()) {
          return "[]";
      }
      String json = "";
      for (Category category: categories) {
          json += category.toJSON() + ",";
      }
      /* remove trailing comma */
      return "[" + json.substring(0, json.length() - 1) + "]";
    }
}
