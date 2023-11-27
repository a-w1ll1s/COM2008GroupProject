package models.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * DatabaseConnection handles the connection to the database.
 * 
 * Adam Willis, November 2023
 */

public class DatabaseConnection {

    private static final String URL = "jdbc:mysql://stusql.dcs.shef.ac.uk:3306/team028";
    private static final String USER = "team028";
    private static final String PASSWORD = "tee1Od9Ek";

    private Connection connection = null;

    public void openConnection() throws SQLException {
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public Connection getConnection() {
        return this.connection;
    }
    
}
