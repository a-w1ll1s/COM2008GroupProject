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


    public ArrayList<Order> getOrderHistory(Connection connection) throws SQLException {

        try {

            ArrayList<Order> orders = new ArrayList<>();

            String selectStatement = "SELECT * FROM `Order`";
            PreparedStatement preparedStatement = connection.prepareStatement(selectStatement);
            ResultSet results = preparedStatement.executeQuery();

            while (results.next()) {
                
                Order current = new Order( 
                    results.getInt("orderID"),
                    results.getInt("userID"), 
                    results.getInt("date"),
                    results.getString("status")
                    );

                orders.add(current);
            }

            return orders;

        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }


    }
    public ArrayList<Customer> getCustomerDetails(Connection connection) throws SQLException {

        try {

            ArrayList<Customer> customers = new ArrayList<>();

            String selectStatement = "SELECT Account.userID, Account.email, Account.password, AccountHolder.forename, AccountHolder.surname, AccountHolder.holderID, AccountHolder.postcode " + 
            ", HolderAddress.houseNum, HolderAddress.roadName, HolderAddress.cityName, HolderAddress.postcode " +
            "FROM Account " +
            "JOIN AccountHolder ON Account.holderID = AccountHolder.holderID " +
            "JOIN HolderAddress ON AccountHolder.postcode = HolderAddress.postcode AND AccountHolder.houseNum = HolderAddress.houseNum " +
            "WHERE Account.isCustomer = TRUE";
            PreparedStatement preparedStatement = connection.prepareStatement(selectStatement);
            ResultSet results = preparedStatement.executeQuery();

            while (results.next()) {
                int userID = results.getInt("userID");
                String email = results.getString("email");
                String password = results.getString("password");
                String forename = results.getString("forename");
                String surname = results.getString("surname");
                int holderID = results.getInt("holderID");
                String houseNum = results.getString("houseNum");
                String roadName = results.getString("roadName");
                String cityName = results.getString("cityName");
                String postcode = results.getString("postcode");
                

                HolderAddress address = new HolderAddress(houseNum, roadName, cityName, postcode);
                AccountHolder holder = new AccountHolder(holderID, forename, surname, address);
                Customer customer = new Customer(userID, email, password, true, false, false, holder);

                customers.add(customer);
            }

            return customers;

        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    
}

    public ArrayList<Staff> getStaffDetails(Connection connection) throws SQLException {

        try {

            ArrayList<Staff> staffs = new ArrayList<>();

            String selectStatement = "SELECT Account.userID, Account.email, Account.password, AccountHolder.forename, AccountHolder.surname, AccountHolder.holderID, AccountHolder.postcode " + 
            ", HolderAddress.houseNum, HolderAddress.roadName, HolderAddress.cityName, HolderAddress.postcode " +
            "FROM Account " +
            "JOIN AccountHolder ON Account.holderID = AccountHolder.holderID " +
            "JOIN HolderAddress ON AccountHolder.postcode = HolderAddress.postcode AND AccountHolder.houseNum = HolderAddress.houseNum " +
            "WHERE Account.isStaff = TRUE";
            PreparedStatement preparedStatement = connection.prepareStatement(selectStatement);
            ResultSet results = preparedStatement.executeQuery();

            while (results.next()) {
                int userID = results.getInt("userID");
                String email = results.getString("email");
                String password = results.getString("password");
                String forename = results.getString("forename");
                String surname = results.getString("surname");
                int holderID = results.getInt("holderID");
                String houseNum = results.getString("houseNum");
                String roadName = results.getString("roadName");
                String cityName = results.getString("cityName");
                String postcode = results.getString("postcode");
                

                HolderAddress address = new HolderAddress(houseNum, roadName, cityName, postcode);
                AccountHolder holder = new AccountHolder(holderID, forename, surname, address);
                Staff staff = new Staff(userID, email, password, true, true, false, holder);

                staffs.add(staff);
            }

            return staffs;

        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    
}

    public void promoteCustomerToStaff(Connection connection, int customerId) throws SQLException {
        String updateQuery = "UPDATE Account SET isCustomer = TRUE, isStaff = TRUE WHERE userID = ?";
        try (PreparedStatement statement = connection.prepareStatement(updateQuery)) {
            statement.setInt(1, customerId);
            statement.executeUpdate();
        }
    }

    public void demoteStaffToCustomer(Connection connection, int staffId) throws SQLException {
        String updateQuery = "UPDATE Account SET isStaff = FALSE, isCustomer = TRUE WHERE userID = ?";
        try (PreparedStatement statement = connection.prepareStatement(updateQuery)) {
            statement.setInt(1, staffId);
            statement.executeUpdate();
        }
    }

    public Product findProduct(Connection connection, int currentID) throws SQLException {

        try {
            String selectStatement = "SELECT * FROM `Product` WHERE productID = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(selectStatement);
            preparedStatement.setInt(1, currentID);
            ResultSet result = preparedStatement.executeQuery();
            

            while(result.next()) {
                Product product = new Product( 
                result.getInt("productID"),
                result.getString("productCode"), 
                result.getString("manufacturer"),
                result.getString("name"),
                result.getInt("price"),
                result.getString("gauge")
                );

                return product;
            }

            return null;

        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public ArrayList<Product> getTrackPackParts(Connection connection, Product set) throws SQLException {

        try {

            ArrayList<Product> parts = new ArrayList<>();

            int setID = set.getProductID();
            String setCode = set.getProductCode();

            if (setCode.equals("P")) {
                String selectStatement = "SELECT * FROM `HasTrack` WHERE setID = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(selectStatement);
                preparedStatement.setInt(1, setID);
                ResultSet results = preparedStatement.executeQuery();

                while(results.next()) {
                    parts.add(findProduct(connection, results.getInt("partID")));
                }
            }

            return parts;

        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public ArrayList<Product> getTrainSetParts(Connection connection, Product set) throws SQLException {

        try {

            ArrayList<Product> parts = new ArrayList<>();

            int setID = set.getProductID();
            String setCode = set.getProductCode();

            if (setCode.equals("M")) {
                String rollingStockStatement = "SELECT * FROM `HasRollingStock` WHERE setID = ?";
                PreparedStatement rollingStockPrepared = connection.prepareStatement(rollingStockStatement);
                rollingStockPrepared.setInt(1, setID);
                ResultSet rollingStockResults = rollingStockPrepared.executeQuery();

                while(rollingStockResults.next()) {
                    parts.add(findProduct(connection, rollingStockResults.getInt("partID")));
                }

                String locomotiveStatement = "SELECT * FROM `HasLocomotive` WHERE setID = ?";
                PreparedStatement locomotivePrepared = connection.prepareStatement(locomotiveStatement);
                locomotivePrepared.setInt(1, setID);
                ResultSet locomotiveResults = locomotivePrepared.executeQuery();

                while(locomotiveResults.next()) {
                    parts.add(findProduct(connection, locomotiveResults.getInt("partID")));
                }

                String trackPackStatement = "SELECT * FROM `HasTrackPack` WHERE setID = ?";
                PreparedStatement trackPackPrepared = connection.prepareStatement(trackPackStatement);
                trackPackPrepared.setInt(1, setID);
                ResultSet trackPackResults = trackPackPrepared.executeQuery();

                while(trackPackResults.next()) {
                    Product trackPack = findProduct(connection, trackPackResults.getInt("partID"));
                    parts.addAll(getTrackPackParts(connection, trackPack));
                }

                String controllerStatement = "SELECT * FROM `Train Sets` WHERE productID = ?";
                PreparedStatement controllerPrepared = connection.prepareStatement(controllerStatement);
                controllerPrepared.setInt(1, setID);
                ResultSet controllerResults = controllerPrepared.executeQuery();

                while(controllerResults.next()) {
                    parts.add(findProduct(connection, controllerResults.getInt("controllerID")));
                }
            }

            return parts;

        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public void addProduct(Connection connection, int productID, String productCode, String manufacturer, String name, int price, String gauge) throws SQLException {
        String insertStatement = "INSERT INTO Product (productID, productCode, manufacturer, name, price, gauge) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertStatement)) {
            preparedStatement.setInt(1, productID);
            preparedStatement.setString(2, productCode);
            preparedStatement.setString(3, manufacturer);
            preparedStatement.setString(4, name);
            preparedStatement.setInt(5, price);
            preparedStatement.setString(6, gauge);
    
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }
    

    public void editProduct(Connection connection, int productID, String productCode, String manufacturer, String name, int price, String gauge) throws SQLException {
        String updateStatement = "UPDATE Product SET productCode = ?, manufacturer = ?, name = ?, price = ?, gauge = ? WHERE productID = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(updateStatement)) {
            preparedStatement.setString(1, productCode);
            preparedStatement.setString(2, manufacturer);
            preparedStatement.setString(3, name);
            preparedStatement.setInt(4, price);
            preparedStatement.setString(5, gauge);
            preparedStatement.setInt(6, productID);
    
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public void deleteProduct(Connection connection, int productID) throws SQLException {
        String deleteStatement = "DELETE FROM Product WHERE productID = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(deleteStatement)) {
            preparedStatement.setInt(1, productID);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }
    
    
}