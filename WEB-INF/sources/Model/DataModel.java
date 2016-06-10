package tutorialdb_model;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.io.IOException;
import java.util.ArrayList;

import javax.annotation.Resource;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class DataModel {

    // DataModel variables
    public Connection conn = null;
    public PreparedStatement stmt = null;
    public ResultSet rs = null;

    // Handles server logs
    public static void log(String title, String message) {
        System.out.println(title + ": " + message);
    }
    // Handles server exception logs
    public static void log(String title, String message, Exception e) {
        System.out.println(title + ": " + message);
        e.printStackTrace();
    }

    // Creates a connection on initialization
    public DataModel() {
        this.conn = getConnection();
    }

    // Creates a LOCAL MySQL connection 
    public Connection getConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            return DriverManager.getConnection("jdbc:mysql:///tutorialdb", "root", "root"); 

        } catch (Exception e) {
            log("ERROR", "DataModel getConnection", e);
            return null;
        }
    }

    // Close all DataModel variables
    public void closeConnection() {
        try {
            if (rs != null) { rs.close(); }
            if (stmt != null) { stmt.close(); }
            if (conn != null) { conn.close(); }
        } catch (SQLException se) {
            log("ERROR", "DataModel closeConnection", se);
        }
    }

    // Close the resultset and statment, leave the connection open for another query. Reduces overhead for creating another connection
    public void closeStatement() {
        try {
            if (rs != null) { rs.close(); }
            if (stmt != null) { stmt.close(); }
        } catch (SQLException se) {
            log("ERROR", "DataModel closeStatement", se);
        }
    }

    public void executeQuery(String query, ArrayList<String> statement_parameters) {
        try {
            // Log the query
            log("Query", query);
            
            // Create prepared statement
            stmt = conn.prepareStatement(query);
            if (statement_parameters != null) {
                for (int i = 0; i < statement_parameters.size(); ++i) {
                    log("param", statement_parameters.get(i));
                    stmt.setString(i + 1, statement_parameters.get(i));
                }
            }

            // Perform the query
            rs = stmt.executeQuery();

        } catch (SQLException se) {
            log("ERROR", "DataModel executeQuery", se);
        }
    }

    public int executeUpdate(String update, ArrayList<String> statement_parameters) {
        try {
            // Log the update
            log("Update", update);
            
            // Create statement
            stmt = conn.prepareStatement(update);
            if (statement_parameters != null) {
                for (int i = 0; i < statement_parameters.size(); ++i) {
                    log("param", statement_parameters.get(i));
                    stmt.setString(i + 1, statement_parameters.get(i));
                }
            }

            // Perform the update and return the number of rows affected
            return stmt.executeUpdate();

        } catch (SQLException se) {
            log("ERROR", "DataModel executeUpdate", se);
        }
        return 0;
    }

    // Return the number of database entries for the given query
    public int getQueryCount(String query, ArrayList<String> statement_parameters) {
        int count = 0;
        // Open a connection and execute the query
        executeQuery(query, statement_parameters);
        try {
            // If the query was not empty
            if (rs.isBeforeFirst()) {
                rs.next();
                count = rs.getInt(1);
            }
        } catch (SQLException se) {
            log("ERROR", "DataModel getQueryCount", se);
        }
        return count;
    }

    // Return Tutorial objects for the given query
    public ArrayList<Tutorial> getTutorialsForQuery(String query, ArrayList<String> statement_parameters) {
        ArrayList<Tutorial> tutorials = new ArrayList<Tutorial>();
        // Open a connection and execute the query
        executeQuery(query, statement_parameters);
        try {
            // If the query was not empty
            if (rs.isBeforeFirst()) {
                while (rs.next()) {
                    tutorials.add(new Tutorial(rs));
                }
            } else {
                return null;
            }
        } catch (SQLException se) {
            log("ERROR", "Tutorial getTutorialsForQuery", se);
        }
        return tutorials;
    }
}
