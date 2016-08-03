package tutorialdb_model;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.commons.lang3.StringEscapeUtils;

public class Category {
    // Category variables
    private String id;
    private String name;

    // Query statements that are performed on the database
    public static final String SELECT    = "SELECT * FROM categories ORDER BY name ASC;";
    public static final String SELECT_ID = "SELECT * FROM categories WHERE id=? LIMIT 1;";
    public static final String INSERT    = "INSERT INTO categories(name) VALUES (?);"; 
    public static final String UPDATE_ID = "UPDATE categorys SET name=? WHERE id=?;"; 
    public static final String DELETE_ID = "DELETE FROM categories WHERE id=?;";


    public Category(ResultSet rs) {
        try {
            this.id   = rs.getString("id");
            this.name = rs.getString("name");
        } catch (SQLException se) {
            DataModel.log("ERROR", "Category resultset", se);
        }
    }

    // Category get functions
    public String id()   { return id; }
    public String name() { return name; }

    // Category set functions
    public String id(String new_id)     { id = new_id; return id(); }
    public String name(String new_name) { name = new_name; return name(); }

    // Converts the Category object to JSON while escaping special characters in the name
    public String toJSON() {
        return "{\"id\":\"" + id + "\"," +
                "\"name\":\"" + StringEscapeUtils.escapeJava(name) + "\"}";
    }
}
