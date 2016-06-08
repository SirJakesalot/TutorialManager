package tutorialdb_model;

import java.sql.*;
import java.util.ArrayList;
import org.apache.commons.lang3.StringEscapeUtils;

public class Tutorial {
    // May need to may the id an int
    private String id;
    private String title;
    private String content;

    public Tutorial(String id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }

    public Tutorial(ResultSet rs) {
        try {
            this.id = rs.getString("id");
            this.title = rs.getString("title");
            this.content = rs.getString("content");
        } catch (SQLException se) {
            DataSource.logError("ERROR: Tutorial resultset", se);
        }
    }

    public Tutorial(String tutorial_id) {
        String query = "SELECT id,title,UNCOMPRESS(content) AS content FROM tutorials WHERE id=?;";
        // Construct query execution parameters
        ArrayList<String> statement_parameters = new ArrayList<String>();
        statement_parameters.add(tutorial_id);

        // Manages opening/closing the connections to the database
        DataSource ds = new DataSource();
        // Open a connection and execute the query
        ds.executeQuery(query, statement_parameters);
        try {
            // If the query was not empty
            if (ds.rs.isBeforeFirst()) {
                ds.rs.next();
                this.id = ds.rs.getString("id");
                this.title = ds.rs.getString("title");
                this.content = ds.rs.getString("content");
            }
        } catch (SQLException se) {
            DataSource.logError("ERROR: Tutorial tutorial_id", se);
        } finally {
            ds.closeQuery();
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

    public String toJSON() {
        return "{\"id\":\"" + id + "\",\"title\":\"" + title + "\",\"content\":\"" + StringEscapeUtils.escapeJava(content) + "\"}";
    }
}
