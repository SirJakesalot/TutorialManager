package tutorialdb_model;

import java.sql.*;
import java.io.*;
import java.util.*;
import java.net.*;

public class DataSource {
    // JDBC driver name and database url
    private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql:///";

    private static final String DB = "tutorialdb";
    private static final String USR = "root";
    private static final String PASS = "root";

    public Connection conn = null;
    public PreparedStatement stmt = null;
    public ResultSet rs = null;

    // There should be a logger object to handle these! 
    public static void logError(String title, Exception e) {
        System.out.println(title);
        e.printStackTrace();
    }
   
    // Creates a mysql connection 
    public static Connection getConnection() {
        try {
            Class.forName(JDBC_DRIVER).newInstance();
            return DriverManager.getConnection(DB_URL + DB, USR, PASS); 
        } catch (Exception e) {
            logError("ERROR: DataSource getConnection", e);
            return null;
        }
    }

    public void executeQuery(String query, ArrayList<String> statement_parameters) {
        try {
            conn = getConnection();

            // Debugging purposes
            System.out.println("Query: " + query);
            
            // Declare our statement
            stmt = conn.prepareStatement(query);
            for (int i = 0; i < statement_parameters.size(); ++i) {
                System.out.println("param: " + statement_parameters.get(i));
                stmt.setString(i + 1, statement_parameters.get(i));
            }

            // Perform the query
            rs = stmt.executeQuery();

        } catch (SQLException se) {
            logError("ERROR: DataSource executeQuery", se);
        }
    }

    public int executeUpdate(String update, ArrayList<String> statement_parameters) {
        try {
            conn = getConnection();

            // Debugging purposes
            System.out.println("Update: " + update);
            
            // Declare our statement
            stmt = conn.prepareStatement(update);
            for (int i = 0; i < statement_parameters.size(); ++i) {
                System.out.println("param: " + statement_parameters.get(i));
                stmt.setString(i + 1, statement_parameters.get(i));
            }

            // Perform the update and return the number of rows affected
            return stmt.executeUpdate();

        } catch (SQLException se) {
            logError("ERROR: DataSource executeUpdate", se);
        }
        return 0;
    }

    public void closeQuery() {
        try {
            if (rs != null) { rs.close(); }
            if (stmt != null) { stmt.close(); }
            if (conn != null) { conn.close(); }
        } catch (SQLException se) {
            logError("ERROR: DataSource closeQuery", se);
        }
    }

    // Return the number of database entries for the given query
    public static int getQueryCount(String query, ArrayList<String> statement_parameters) {
        int count = 0;
        // Manages opening/closing the connections to the database
        DataSource ds = new DataSource();
        // Open a connection and execute the query
        ds.executeQuery(query, statement_parameters);
        try {
            // If the query was not empty
            if (ds.rs.isBeforeFirst()) {
                ds.rs.next();
                count = ds.rs.getInt(1);
            }
        } catch (SQLException se) {
            DataSource.logError("ERROR: DataSource getQueryCount", se);
        } finally {
            ds.closeQuery();
        }
        return count;
    }

    // Return the genres for the given query
    public static ArrayList<Tutorial> getTutorialsForQuery(String query, ArrayList<String> statement_parameters) {
        ArrayList<Tutorial> tutorials = new ArrayList<Tutorial>();
        // Manages opening/closing the connections to the database
        DataSource ds = new DataSource();
        // Open a connection and execute the query
        ds.executeQuery(query, statement_parameters);
        try {
            // If the query was not empty
            if (ds.rs.isBeforeFirst()) {
                while (ds.rs.next()) {
                    tutorials.add(new Tutorial(ds.rs));
                }
            } else {
                return null;
            }
        } catch (SQLException se) {
            DataSource.logError("ERROR: Tutorial getTutorialsForQuery", se);
        } finally {
            ds.closeQuery();
        }
        return tutorials;
    }
}