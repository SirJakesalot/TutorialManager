package tutorialdb_model;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

import javax.annotation.Resource;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 * DataModel mediates all access to the database. Safely executing queries and
 * returning their coresponding model objects.
 * @author Jake Armentrout
 */
public class DataModel {

    /* JDBC driver name and database url */
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL      = "jdbc:mysql:///tutorialdb";

    /* tutorial database admin credentials */
    static final String USER = "tutorialdb_admin";
    static final String PASS = "administrator";

    /* used to open and hold a database connection */
    public Connection conn        = null;
    public PreparedStatement stmt = null;
    public ResultSet rs           = null;

    /**
     * Constructs a DataModel object. Establishes and holds a connection to the
     * local tutorial database.
     */
    public DataModel() throws Exception {
        this.conn = getConnection();
    }

    /**
     * Establishes a local connection to the tutorial database. Return null if
     * fail to open connection.
     * @return Connection Open connection to the local tutorial database.
     */
    public Connection getConnection() throws Exception {
        Class.forName(this.JDBC_DRIVER).newInstance();
        return DriverManager.getConnection(this.DB_URL, this.USER, this.PASS);
    }

    /**
     * Closes any open database connections.
     */
    public void closeConnection() {
        try {
            if (this.rs != null)   { this.rs.close(); }
            if (this.stmt != null) { this.stmt.close(); }
            if (this.conn != null) { this.conn.close(); }
        } catch (Exception e) {
            ;
        }
    }

    /**
     * Closes the ResultSet and PreparedStatment but leaves the database
     * connection open to be used for another query/update. This reduces the
     * overhead of creating multiple connections.
     */
    public void closeStatement() throws SQLException {
        if (this.rs != null)   { this.rs.close(); }
        if (this.stmt != null) { this.stmt.close(); }
    }

    /**
     * Execute a database query statement and set ResultSet to the output.
     * @param query Database query string to be executed with ?'s for parameters
     * @param statement_parameters Parameters to be used with the query string
     */
    public void executeQuery(String query, List<String> statement_parameters) throws SQLException {
        /* log the query */
        Logger.log(Logger.Status.INFO, query);
        /* create prepared statement and set parameters */
        this.stmt = this.conn.prepareStatement(query);
        if (statement_parameters != null) {
            for (int i = 0; i < statement_parameters.size(); ++i) {
                Logger.log(Logger.Status.INFO, statement_parameters.get(i));
                this.stmt.setString(i + 1, statement_parameters.get(i));
            }
        }
        /* perform the query */
        this.rs = this.stmt.executeQuery();
    }

    /**
     * Execute a database update statement and return the number of rows
     * affected.
     * @param update Database update string to be executed with ?'s for parameters
     * @param statement_parameters Parameters to be used with the update string
     * @return int Number of rows affected by the update
     */
    public int executeUpdate(String update, List<String> statement_parameters) throws SQLException {
        /* log the update */
        Logger.log(Logger.Status.INFO, update);
        /* create prepared statement and set parameters */
        this.stmt = this.conn.prepareStatement(update);
        if (statement_parameters != null) {
            for (int i = 0; i < statement_parameters.size(); ++i) {
                Logger.log(Logger.Status.INFO, statement_parameters.get(i));
                this.stmt.setString(i + 1, statement_parameters.get(i));
            }
        }
        /* perform the update and return the number of rows affected */
        return this.stmt.executeUpdate();
    }

    /**
     * Execute an aggregate query statement and return the numerical result.
     * @param query Database query string to be executed with ?'s for parameters
     * @param statement_parameters Parameters to be used with the query string
     * @return int Numerical result from the aggregate function
     */
    public int getAggregateQuery(String query, List<String> statement_parameters) throws SQLException {
        int count = 0;
        executeQuery(query, statement_parameters);
        /* record count for non-empty ResultSet */
        if (this.rs.isBeforeFirst()) {
            this.rs.next();
            count = this.rs.getInt(1);
        }
        return count;
    }

    /**
     * Execute a query that returns an List of Category objects.
     * @param query Database query string to be executed with ?'s for parameters
     * @param statement_parameters Parameters to be used with the query string
     * @return List<Category> List of Category objects for query
     */
    public List<Category> getCategoriesForQuery(String query, List<String> statement_parameters) throws SQLException {
        List<Category> categories = new ArrayList<Category>();
        executeQuery(query, statement_parameters);
        /* add categories for non-empty ResultSet */
        if (this.rs.isBeforeFirst()) {
            while (this.rs.next()) {
                categories.add(new Category(this.rs));
            }
        }
        return categories;
    }

    /**
     * Execute a query that returns an List of Tutorial objects.
     * @param query Database query string to be executed with ?'s for parameters
     * @param statement_parameters Parameters to be used with the query string
     * @return List<Tutorial> List of Tutorial objects for query
     */
    public List<Tutorial> getTutorialsForQuery(String query, List<String> statement_parameters) throws SQLException {
        List<Tutorial> tutorials = new ArrayList<Tutorial>();
        executeQuery(query, statement_parameters);
        /* add tutorials for non-empty ResultSet */
        if (this.rs.isBeforeFirst()) {
            /* add all tutorials from the query */
            while (this.rs.next()) {
                tutorials.add(new Tutorial(this.rs));
            }
        }
        return tutorials;
    }
}
