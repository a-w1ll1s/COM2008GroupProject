package models.database;
import models.business.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * DatabaseMethods handles the interactions with the database.
 * 
 * Adam Willis, November 2023
 */

public class DatabaseMethods {

    public ArrayList<Product> getProducts(Connection connection) throws SQLException {

        try {

            ArrayList<Product> products = new ArrayList<>();

            String selectStatement = "SELECT * FROM Product";
            PreparedStatement preparedStatement = connection.prepareStatement(selectStatement);
            ResultSet results = preparedStatement.executeQuery();

            while (results.next()) {
                
                Product current = new Product( 
                    results.getInt("productID"),
                    results.getString("productCode"), 
                    results.getString("manufacturer"),
                    results.getString("name"),
                    results.getInt("price"),
                    results.getString("gauge")
                    );

                products.add(current);
            }

            return products;

        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }
    
}
