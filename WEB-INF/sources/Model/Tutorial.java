package tutorialdb_model;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.commons.lang3.StringEscapeUtils;

public class Tutorial {
    // Tutorial variables
    private String id;
    private String title;
    private String content;

    // Query statements that are performed on the database
    public static final String SELECT = "SELECT id,title,UNCOMPRESS(content) as content FROM tutorials;";
    public static final String SELECT_ID = "SELECT id,title,UNCOMPRESS(content) as content FROM tutorials WHERE id=? LIMIT 1;";
    public static final String SELECT_TITLE = "SELECT id,title,UNCOMPRESS(content) as content FROM tutorials WHERE title=? LIMIT 1;";
    public static final String DELETE_ID = "DELETE FROM tutorials WHERE id=?;";
    public static final String INSERT = "INSERT INTO tutorials(title,content) VALUES (?,COMPRESS(?));"; 
    public static final String UPDATE_ID = "UPDATE tutorials SET title=?,content=COMPRESS(?) WHERE id=?;"; 

    public Tutorial(ResultSet rs) {
        try {
            this.id = rs.getString("id");
            this.title = rs.getString("title");
            this.content = rs.getString("content");
        } catch (SQLException se) {
            DataModel.log("ERROR", "Tutorial resultset", se);
        }
    }

    // Tutorial get functions
    public String id() { return id; }
    public String title() { return title; }
    public String content() { return content; }

    // Tutorial set functions
    public String id(String new_id) { id = new_id; return id(); }
    public String title(String new_title) { title = new_title; return title(); }
    public String content(String new_content) { content = new_content; return content(); }

    // Converts the Tutorial object to JSON while escaping special characters in the content
    public String toJSON() {
        return "{\"id\":\"" + id + "\",\"title\":\"" + title + "\",\"content\":\"" + StringEscapeUtils.escapeJava(content) + "\"}";
    }
}
