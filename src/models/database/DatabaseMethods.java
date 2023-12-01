package models.database;
import models.business.*;

import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * DatabaseMethods handles the interactions with the database.
 * 
 * Adam Willis, November 2023
 */

public final class DatabaseMethods {

    public static Product getProductFromResult(ResultSet results) throws SQLException {
        Product product = new Product( 
            results.getInt("productID"),
            results.getString("productCode"), 
            results.getString("manufacturer"),
            results.getString("name"),
            results.getInt("price"),
            results.getString("gauge")
        );

        return product;    
    }

    public static ArrayList<Product> getProducts(Connection connection) throws SQLException {

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


    public static ArrayList<Order> getOrderHistory(Connection connection) throws SQLException {

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
    public static ArrayList<Customer> getCustomerDetails(Connection connection) throws SQLException {

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

    public static ArrayList<Staff> getStaffDetails(Connection connection) throws SQLException {

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

    public static Account getAccountDetails(Connection connection, String email) throws SQLException {
        // Method for authenticating logins and setting the logged in user.
        // Takes an email and returns the found account details or null if not found.
        // TODO: Make this take a password too?

        try {
            String selectStatement = "SELECT Account.userID, Account.password, Account.isCustomer, Account.isStaff, Account.isManager, AccountHolder.forename, AccountHolder.surname, AccountHolder.holderID, AccountHolder.postcode " + 
            ", HolderAddress.houseNum, HolderAddress.roadName, HolderAddress.cityName, HolderAddress.postcode " +
            "FROM Account " +
            "JOIN AccountHolder ON Account.holderID = AccountHolder.holderID " +
            "JOIN HolderAddress ON AccountHolder.postcode = HolderAddress.postcode AND AccountHolder.houseNum = HolderAddress.houseNum " +
            "WHERE Account.email = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(selectStatement);
            preparedStatement.setString(1, email);

            ResultSet results = preparedStatement.executeQuery();

            // Check for a result
            if (!results.next()) {
                return null;
            }

            int userID = results.getInt("userID");
            String password = results.getString("password");
            Boolean isCustomer = results.getBoolean("isCustomer");
            Boolean isStaff = results.getBoolean("isStaff");
            Boolean isManager = results.getBoolean("isManager");
            String forename = results.getString("forename");
            String surname = results.getString("surname");
            int holderID = results.getInt("holderID");
            String houseNum = results.getString("houseNum");
            String roadName = results.getString("roadName");
            String cityName = results.getString("cityName");
            String postcode = results.getString("postcode");
            

            HolderAddress address = new HolderAddress(houseNum, roadName, cityName, postcode);
            AccountHolder holder = new AccountHolder(holderID, forename, surname, address);
            return new Account(userID, email, password, isCustomer, isStaff, isManager, holder);

        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    
    }

    public static void promoteCustomerToStaff(Connection connection, int customerId) throws SQLException {
        String updateQuery = "UPDATE Account SET isCustomer = TRUE, isStaff = TRUE WHERE userID = ?";
        try (PreparedStatement statement = connection.prepareStatement(updateQuery)) {
            statement.setInt(1, customerId);
            statement.executeUpdate();
        }
    }

    public static void demoteStaffToCustomer(Connection connection, int staffId) throws SQLException {
        String updateQuery = "UPDATE Account SET isStaff = FALSE, isCustomer = TRUE WHERE userID = ?";
        try (PreparedStatement statement = connection.prepareStatement(updateQuery)) {
            statement.setInt(1, staffId);
            statement.executeUpdate();
        }
    }

    public static Product findProduct(Connection connection, int currentID) throws SQLException {

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

    public static ArrayList<ArrayList<Product>> getTrackPacks(Connection connection) throws SQLException {
        String selectStatement = "SELECT * FROM `Track Packs` "
            + "JOIN Product ON Product.productID = `Track Packs`.productID";

        PreparedStatement preparedStatement = connection.prepareStatement(selectStatement);
        ResultSet results = preparedStatement.executeQuery();

        ArrayList<ArrayList<Product>> trackPacks = new ArrayList<>();

        // Get each track pack
        while (results.next()) {
            ArrayList<Product> products = new ArrayList<>();

            TrackPack trackPackProduct = new TrackPack( 
                results.getInt("productID"),
                results.getString("productCode"), 
                results.getString("manufacturer"),
                results.getString("name"),
                results.getInt("price"),
                results.getString("gauge")
            );

            // Add the product as the first product in the track pack
            products.add(trackPackProduct);

            // Get each product in the track pack
            // NOTE: BROKE THIS:
            products.addAll(DatabaseMethods.getTrackPackParts(connection, trackPackProduct));

            trackPacks.add(products);
        }

        return trackPacks;
    }

    public static ArrayList<Product> getTrackPackParts(Connection connection, Product set) throws SQLException {

        try {

            ArrayList<Product> parts = new ArrayList<>();

            int setID = set.getProductID();
            String setCode = set.getProductCode();

            if (setCode.equals("P")) {
                String selectStatement = "SELECT * FROM `HasTrack` "
                    + "JOIN Product ON Product.productID = `HasTrack`.partID "
                    + "WHERE setID = ?";
                    
                PreparedStatement preparedStatement = connection.prepareStatement(selectStatement);
                preparedStatement.setInt(1, setID);
                ResultSet results = preparedStatement.executeQuery();

                while(results.next()) {
                    Product product = getProductFromResult(results);
                    parts.add(product);
                }
            }

            return parts;

        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public static ArrayList<ArrayList<Product>> getTrainSets(Connection connection) throws SQLException {
        String selectStatement = "SELECT * FROM `Train Sets` "
            + "JOIN Product ON Product.productID = `Train Sets`.productID";

        PreparedStatement preparedStatement = connection.prepareStatement(selectStatement);
        ResultSet results = preparedStatement.executeQuery();

        ArrayList<ArrayList<Product>> trainSets = new ArrayList<>();

        // Get each track pack
        while (results.next()) {
            ArrayList<Product> products = new ArrayList<>();

            TrackPack trainSetProduct = new TrackPack( 
                results.getInt("productID"),
                results.getString("productCode"), 
                results.getString("manufacturer"),
                results.getString("name"),
                results.getInt("price"),
                results.getString("gauge")
            );

            // Add the parent product as the first product
            products.add(trainSetProduct);

            // Get each product in the track pack
            products.addAll(DatabaseMethods.getTrainSetPartsAsPacks(connection, trainSetProduct));

            trainSets.add(products);
        }

        return trainSets;
    }

    public static ArrayList<Product> getTrainSetPartsAsPacks(Connection connection, Product set) throws SQLException {
        // Returns all the train set parts, with products like track packs NOT expanded
        try {

            ArrayList<Product> parts = new ArrayList<>();

            int setID = set.getProductID();
            String setCode = set.getProductCode();

            if (setCode.equals("M")) {
                String rollingStockStatement = "SELECT * FROM `HasRollingStock` "
                    + "JOIN Product ON Product.productID = `HasRollingStock`.partID "
                    + "WHERE setID = ?";

                PreparedStatement rollingStockPrepared = connection.prepareStatement(rollingStockStatement);
                rollingStockPrepared.setInt(1, setID);
                ResultSet rollingStockResults = rollingStockPrepared.executeQuery();

                while(rollingStockResults.next()) {                    
                    parts.add(getProductFromResult(rollingStockResults));
                }

                String locomotiveStatement = "SELECT * FROM `HasLocomotive` "
                    + "JOIN Product ON Product.productID = `HasLocomotive`.partID "
                    + "WHERE setID = ?";
                PreparedStatement locomotivePrepared = connection.prepareStatement(locomotiveStatement);
                locomotivePrepared.setInt(1, setID);
                ResultSet locomotiveResults = locomotivePrepared.executeQuery();

                while(locomotiveResults.next()) {
                    parts.add(getProductFromResult(locomotiveResults));
                }

                String trackPackStatement = "SELECT * FROM `HasTrackPack` "
                    + "JOIN Product ON Product.productID = `HasTrackPack`.partID "
                    + "WHERE setID = ?";
                PreparedStatement trackPackPrepared = connection.prepareStatement(trackPackStatement);
                trackPackPrepared.setInt(1, setID);
                ResultSet trackPackResults = trackPackPrepared.executeQuery();

                while(trackPackResults.next()) {
                    Product trackPack = getProductFromResult(trackPackResults);

                    parts.add(trackPack);
                    
                }

                String controllerStatement = "SELECT * FROM `Train Sets` "
                    + "JOIN Product ON Product.productID = `Train Sets`.productID "
                    + "WHERE `Train Sets`.productID = ?";
                PreparedStatement controllerPrepared = connection.prepareStatement(controllerStatement);
                controllerPrepared.setInt(1, setID);
                ResultSet controllerResults = controllerPrepared.executeQuery();

                while(controllerResults.next()) {
                    parts.add(getProductFromResult(controllerResults));
                }
            }

            return parts;

        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public static ArrayList<Product> getTrainSetParts(Connection connection, Product set) throws SQLException {
        // Returns all the train set parts, with products like track packs expanded.
        try {

            ArrayList<Product> parts = new ArrayList<>();

            int setID = set.getProductID();
            String setCode = set.getProductCode();

            if (setCode.equals("M")) {
                String rollingStockStatement = "SELECT * FROM `HasRollingStock` "
                    + "JOIN Product ON Product.productID = `HasRollingStock`.partID "
                    + "WHERE setID = ?";

                PreparedStatement rollingStockPrepared = connection.prepareStatement(rollingStockStatement);
                rollingStockPrepared.setInt(1, setID);
                ResultSet rollingStockResults = rollingStockPrepared.executeQuery();

                while(rollingStockResults.next()) {                    
                    parts.add(getProductFromResult(rollingStockResults));
                }

                String locomotiveStatement = "SELECT * FROM `HasLocomotive` "
                    + "JOIN Product ON Product.productID = `HasLocomotive`.partID "
                    + "WHERE setID = ?";
                PreparedStatement locomotivePrepared = connection.prepareStatement(locomotiveStatement);
                locomotivePrepared.setInt(1, setID);
                ResultSet locomotiveResults = locomotivePrepared.executeQuery();

                while(locomotiveResults.next()) {
                    parts.add(getProductFromResult(locomotiveResults));
                }

                String trackPackStatement = "SELECT * FROM `HasTrackPack` "
                    + "JOIN Product ON Product.productID = `HasTrackPack`.partID "
                    + "WHERE setID = ?";
                PreparedStatement trackPackPrepared = connection.prepareStatement(trackPackStatement);
                trackPackPrepared.setInt(1, setID);
                ResultSet trackPackResults = trackPackPrepared.executeQuery();

                while(trackPackResults.next()) {
                    Product trackPack = getProductFromResult(trackPackResults);

                    parts.addAll(getTrackPackParts(connection, trackPack));
                    
                }

                String controllerStatement = "SELECT * FROM `Train Sets` "
                    + "JOIN Product ON Product.productID = `Train Sets`.productID "
                    + "WHERE `Train Sets`.productID = ?";
                PreparedStatement controllerPrepared = connection.prepareStatement(controllerStatement);
                controllerPrepared.setInt(1, setID);
                ResultSet controllerResults = controllerPrepared.executeQuery();

                while(controllerResults.next()) {
                    parts.add(getProductFromResult(controllerResults));
                }
            }

            return parts;

        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }




    }

    public static ArrayList<Controller> getControllers(Connection connection) throws SQLException { 
        try {
            ArrayList<Controller> controllers = new ArrayList<>();

            String selectStatement = "SELECT * FROM Controller "
                + "JOIN Product ON Product.productID = Controller.productID";

            PreparedStatement preparedStatement = connection.prepareStatement(selectStatement);
            ResultSet results = preparedStatement.executeQuery();

            while (results.next()) {
                
                Controller current = new Controller( 
                    results.getInt("productID"),
                    results.getString("productCode"), 
                    results.getString("manufacturer"),
                    results.getString("name"),
                    results.getInt("price"),
                    results.getString("gauge"),
                    results.getBoolean("digital")
                    );

                controllers.add(current);
            }

            return controllers;

        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }
    public static ArrayList<Locomotive> getLocomotives(Connection connection) throws SQLException { 
        try {
            ArrayList<Locomotive> locomotives = new ArrayList<>();

            String selectStatement = "SELECT * FROM Locomotive "
                + "JOIN Product ON Product.productID = Locomotive.productID";

            PreparedStatement preparedStatement = connection.prepareStatement(selectStatement);
            ResultSet results = preparedStatement.executeQuery();

            while (results.next()) {
                
                Locomotive current = new Locomotive( 
                    results.getInt("productID"),
                    results.getString("productCode"), 
                    results.getString("manufacturer"),
                    results.getString("name"),
                    results.getInt("price"),
                    results.getString("gauge"),
                    results.getString("codeDCC"),
                    results.getString("era")
                    );

                locomotives.add(current);
            }

            return locomotives;

        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }
    public static ArrayList<RollingStock> getRollingStocks(Connection connection) throws SQLException { 
        try {
            ArrayList<RollingStock> rollingStock = new ArrayList<>();

            String selectStatement = "SELECT * FROM `Rolling Stock` "
                + "JOIN Product ON Product.productID = `Rolling Stock`.productID";

            PreparedStatement preparedStatement = connection.prepareStatement(selectStatement);
            ResultSet results = preparedStatement.executeQuery();

            while (results.next()) {
                RollingStock current = new RollingStock( 
                    results.getInt("productID"),
                    results.getString("productCode"), 
                    results.getString("manufacturer"),
                    results.getString("name"),
                    results.getInt("price"),
                    results.getString("gauge"),
                    results.getString("era"),
                    results.getBoolean("isCarriage")
                    );

                rollingStock.add(current);
            }

            return rollingStock;

        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }
    public static ArrayList<Track> getTracks(Connection connection) throws SQLException { 
        try {
            ArrayList<Track> track = new ArrayList<>();

            String selectStatement = "SELECT * FROM Track "
                + "JOIN Product ON Product.productID = Track.productID";

            PreparedStatement preparedStatement = connection.prepareStatement(selectStatement);
            ResultSet results = preparedStatement.executeQuery();

            while (results.next()) {
                
                Track current = new Track( 
                    results.getInt("productID"),
                    results.getString("productCode"), 
                    results.getString("manufacturer"),
                    results.getString("name"),
                    results.getInt("price"),
                    results.getString("gauge")
                    );

                track.add(current);
            }

            return track;

        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public static void fulfillOrder(Connection connection, int orderId) throws SQLException {
        String updateQuery = "UPDATE `Order` SET status = 'Fulfilled' WHERE orderID = ?";
        try (PreparedStatement statement = connection.prepareStatement(updateQuery)) {
            statement.setInt(1, orderId);
            statement.executeUpdate();
        }
    }

    public static void deleteOrderLinesForOrder(Connection connection, int orderId) throws SQLException {
        String deleteQuery = "DELETE FROM `Order Line` WHERE orderID = ?";
        try (PreparedStatement statement = connection.prepareStatement(deleteQuery)) {
            statement.setInt(1, orderId);
            statement.executeUpdate();

        }
    }
    public static void deleteOrderLine(Connection connection, int orderID, int lineID) throws SQLException {
        String deleteQuery = "DELETE FROM `Order Line` WHERE lineID = ? AND orderID = ?";
        try (PreparedStatement statement = connection.prepareStatement(deleteQuery)) {
            statement.setInt(1, lineID);
            statement.setInt(2, orderID);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }
    public static void addProduct(Connection connection, int productID, String productCode, String manufacturer, String name, int price, String gauge) throws SQLException {
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
    


    public static void deleteOrder(Connection connection, int orderId) throws SQLException {
        deleteOrderLinesForOrder(connection, orderId);
        String deleteQuery = "DELETE FROM `Order` WHERE orderID = ?";
        try (PreparedStatement statement = connection.prepareStatement(deleteQuery)) {
            statement.setInt(1, orderId);
            statement.executeUpdate();
        }
    }

    public static ArrayList<Order> getPendingOrders(Connection connection) throws SQLException {
        ArrayList<Order> pendingOrders = new ArrayList<>();

        String orderQuery = "SELECT * FROM `Order` WHERE status = 'Pending'";
        try (PreparedStatement orderStatement = connection.prepareStatement(orderQuery)) {
            ResultSet orderResultSet = orderStatement.executeQuery();

            while (orderResultSet.next()) {
                int orderID = orderResultSet.getInt("orderID");
                int userID = orderResultSet.getInt("userID");
                int date = orderResultSet.getInt("date");  
                String status = orderResultSet.getString("status");

                Order order = new Order(orderID, userID, date, status);
                order.setOrderLines(getOrderLinesForOrder(connection, orderID));
                pendingOrders.add(order);
            }
        }
        return pendingOrders;
    }

    public static ArrayList<OrderLine> getOrderLinesForOrder(Connection connection, int orderID) throws SQLException {
        ArrayList<OrderLine> orderLines = new ArrayList<>();
        String lineQuery = "SELECT * FROM `Order Line` WHERE orderID = ?";
        try (PreparedStatement lineStatement = connection.prepareStatement(lineQuery)) {
            lineStatement.setInt(1, orderID);
            ResultSet lineResultSet = lineStatement.executeQuery();

            while (lineResultSet.next()) {
                int lineNum = lineResultSet.getInt("lineID");
                int productID = lineResultSet.getInt("productID");
                int quantity = lineResultSet.getInt("quantity");

                Product product = findProduct(connection, productID); 
                OrderLine line = new OrderLine(lineNum, product, quantity);
                orderLines.add(line);
            }
        }
        return orderLines;
    }


    public static void editProduct(Connection connection, int productID, String productCode, String manufacturer, String name, int price, String gauge) throws SQLException {
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

    public static void deleteProduct(Connection connection, int productID) throws SQLException {
        String deleteStatement = "DELETE FROM Product WHERE productID = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(deleteStatement)) {
            preparedStatement.setInt(1, productID);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public static void addBankDetails(Connection connection, String accountNumber, String sortCode, String bankName, int holderID) throws SQLException {
        String insertStatement = "INSERT INTO BankDetails (accountNumber, sortCode, bankName, holderID) VALUES (?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertStatement)) {
            preparedStatement.setString(1, accountNumber);
            preparedStatement.setString(2, sortCode);
            preparedStatement.setString(3, bankName);
            preparedStatement.setInt(4, holderID);
    
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public static void editBankDetails(Connection connection, String accountNumber, String newSortCode, String newBankName, int holderID) throws SQLException {
        String updateStatement = "UPDATE BankDetails SET sortCode = ?, bankName = ? WHERE accountNumber = ? AND holderID = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(updateStatement)) {
            preparedStatement.setString(1, newSortCode);
            preparedStatement.setString(2, newBankName);
            preparedStatement.setString(3, accountNumber);
            preparedStatement.setInt(4, holderID);
    
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public static BankDetails getBankDetails(Connection connection, int userID) throws SQLException {
        String selectStatement = "SELECT * FROM BankDetails WHERE userID = ?";
        
        try (PreparedStatement preparedStatement = connection.prepareStatement(selectStatement)) {
            preparedStatement.setInt(1, userID);
            ResultSet results = preparedStatement.executeQuery();

            while (results.next()) {
                String cardBrand = results.getString("cardBrand");
                int cardNum = results.getInt("cardNum");
                int cardExpiry = results.getInt("cardExpiry"); 
                int securityCode = results.getInt("securityCode"); 
                
                BankDetails bankDetails = new BankDetails(cardBrand, cardNum, cardExpiry, securityCode);
                
                return bankDetails;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        
        return null;
    }

    public static void addProductToInventory(Connection connection, int productID, int stockLevel) throws SQLException {
        String insertStatement = "INSERT INTO Inventory (productID, stockLevel) VALUES (?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertStatement)) {
            preparedStatement.setInt(1, productID);
            preparedStatement.setInt(2, stockLevel);
    
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public static ArrayList<Order> getPendingCustomerOrders(Connection connection, int userID) throws SQLException {
        ArrayList<Order> customerOrders = new ArrayList<>();
    
        String selectStatement = "SELECT * FROM `Order` WHERE userID = ? AND status = 'Pending'";
        try (PreparedStatement preparedStatement = connection.prepareStatement(selectStatement)) {
            preparedStatement.setInt(1, userID);
    
            ResultSet results = preparedStatement.executeQuery();
            while (results.next()) {
                Order currentOrder = new Order(
                    results.getInt("orderID"),
                    results.getInt("userID"),
                    results.getInt("date"),
                    results.getString("status")
                );
    
                customerOrders.add(currentOrder);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    
        return customerOrders;
    }

    public static ArrayList<Order> getPastCustomerOrders(Connection connection, int userID) throws SQLException {
        ArrayList<Order> pastOrders = new ArrayList<>();
    
        String selectStatement = "SELECT * FROM `Order` WHERE userID = ? AND status = 'Fulfilled'";
        try (PreparedStatement preparedStatement = connection.prepareStatement(selectStatement)) {
            preparedStatement.setInt(1, userID);
    
            ResultSet results = preparedStatement.executeQuery();
            while (results.next()) {
                Order pastOrder = new Order(
                    results.getInt("orderID"),
                    results.getInt("userID"),
                    results.getInt("date"),
                    results.getString("status")
                );
    
                pastOrders.add(pastOrder);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    
        return pastOrders;
    }

    public static void editCustomerEmail(Connection connection, int userID, String newEmail) throws SQLException {
        String updateEmailQuery = "UPDATE Account SET email = ? WHERE userID = ?";
    
        try (PreparedStatement emailStatement = connection.prepareStatement(updateEmailQuery)) {
            emailStatement.setString(1, newEmail);
            emailStatement.setInt(2, userID);
            emailStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public static void editCustomerAddress(Connection connection, int holderID, HolderAddress newAddress) throws SQLException {
        String updateAddressQuery = "UPDATE HolderAddress SET houseNum = ?, roadName = ?, cityName = ?, postcode = ? WHERE holderID = ?";
    
        try (PreparedStatement addressStatement = connection.prepareStatement(updateAddressQuery)) {
            addressStatement.setString(1, newAddress.getHouseNum());
            addressStatement.setString(2, newAddress.getRoadName());
            addressStatement.setString(3, newAddress.getCityName());
            addressStatement.setString(4, newAddress.getPostcode());
            addressStatement.setInt(5, holderID);
            addressStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }
    

    public static void registerUser(Connection connection, String email, String password, String forename, String surname, String houseNum, String roadName, String cityName, String postcode) throws SQLException {

        connection.setAutoCommit(false);
    
        try {
            String insertAddress = "INSERT INTO HolderAddress (houseNum, roadName, cityName, postcode) VALUES (?, ?, ?, ?)";
            try (PreparedStatement addressStatement = connection.prepareStatement(insertAddress)) {
                addressStatement.setString(1, houseNum);
                addressStatement.setString(2, roadName);
                addressStatement.setString(3, cityName);
                addressStatement.setString(4, postcode);
                addressStatement.executeUpdate();
            }
        
            String insertHolder = "INSERT INTO AccountHolder (forename, surname, houseNum, postcode) VALUES (?, ?, ?, ?)";
            try (PreparedStatement holderStatement = connection.prepareStatement(insertHolder, Statement.RETURN_GENERATED_KEYS)) {
                holderStatement.setString(1, forename);
                holderStatement.setString(2, surname);
                holderStatement.setString(3, houseNum);
                holderStatement.setString(4, postcode);
                holderStatement.executeUpdate();
        
                ResultSet holderKeys = holderStatement.getGeneratedKeys();
                if (holderKeys.next()) {
                    int holderID = holderKeys.getInt(1);
        
                    String insertAccount = "INSERT INTO Account (email, password, holderID, isCustomer, isStaff, isManager) VALUES (?, ?, ?, TRUE, FALSE, FALSE)";
                    try (PreparedStatement accountStatement = connection.prepareStatement(insertAccount)) {
                        accountStatement.setString(1, email);
                        accountStatement.setString(2, password); 
                        accountStatement.setInt(3, holderID);
                        accountStatement.executeUpdate();
                    }
                }
            }
        
            connection.commit();
        
        } catch (SQLException e) {
            connection.rollback();
            e.printStackTrace();
            throw e;
        } finally {
            connection.setAutoCommit(true);
        }
    
    }

    public static Order createNewOrGetPendingOrder(Connection connection, int accountID) throws SQLException {
        ArrayList<Order> pendingOrders = getPendingCustomerOrders(connection, accountID);
    
        if (pendingOrders.size() != 0) {
            return pendingOrders.get(0);
        }

        // Couldn't find a pending order to continue, so create a new order
        int dateAsInt = Integer.parseInt(
            new SimpleDateFormat("ddMMyyyy").format(Calendar.getInstance().getTime()));

        try {
            String insertStatement = "INSERT INTO `Order` (`userID`, `date`, `status`) VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insertStatement, 
                Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setInt(1, accountID);
            preparedStatement.setInt(2, dateAsInt);
            preparedStatement.setString(3, "Pending");

            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            rs.next();

            return new Order(rs.getInt(1), accountID, dateAsInt, "Pending");
           
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }

    }

    public static OrderLine createOrUpdateOrderLine(Connection connection, int orderID, Product product, 
        int newQuantity) 
        throws SQLException {

        ArrayList<OrderLine> orderLines = getOrderLinesForOrder(connection, orderID);
        int lineID = -1;
        for (int i = 0; i < orderLines.size(); ++i) {
            if (orderLines.get(i).getProduct().getProductID() == product.getProductID()) {
                lineID = i;
                break;
            }
        }

        try {
            // If we didn't find an existing order line
            if (lineID == -1) {
                
                lineID = orderLines.size();
                System.out.println("new lineID + " + lineID);

                String insertStatement = "INSERT INTO `Order Line` (lineID, orderID, productID, quantity) "
                + "VALUES (?, ?, ?, 1)";
                    
                PreparedStatement preparedInsertStatement = connection.prepareStatement(insertStatement);
                
                preparedInsertStatement.setInt(1, lineID);
                preparedInsertStatement.setInt(2, orderID);
                preparedInsertStatement.setInt(3, product.getProductID());

                preparedInsertStatement.executeUpdate();

                return new OrderLine(lineID, product, 1);
            }
            else {
                String updateStatement = "UPDATE `Order Line` SET quantity = ? "
                    + "WHERE orderID = ? AND lineID = ? AND productID = ?";
                    
                PreparedStatement preparedUpdateStatement = connection.prepareStatement(updateStatement);

                preparedUpdateStatement.setInt(1, newQuantity);
                preparedUpdateStatement.setInt(2, orderID);
                preparedUpdateStatement.setInt(3, lineID);
                preparedUpdateStatement.setInt(4, product.getProductID());

                preparedUpdateStatement.executeUpdate();

                return new OrderLine(lineID, product, newQuantity);
            }            
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }
    public static Boolean updateOrderLine(Connection connection, int orderID, OrderLine orderLine) 
        throws SQLException {

        try {
            String insertStatement = "UPDATE `Order Line` SET quantity = ? "
                + "WHERE lineID = ? AND orderID = ? AND productID = ?";
                    
            PreparedStatement preparedStatement = connection.prepareStatement(insertStatement, 
                Statement.RETURN_GENERATED_KEYS);

            
            preparedStatement.setInt(1, orderLine.getQuantity());
            preparedStatement.setInt(2, orderLine.getLineNum());
            preparedStatement.setInt(3, orderID);
            preparedStatement.setInt(4, orderLine.getProduct().getProductID());

            preparedStatement.executeUpdate();

            ResultSet rs = preparedStatement.getGeneratedKeys();
            rs.next();

            orderLine.setQuantity(orderID);
            return true;
           
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public HolderAddress getCurrentAddress(Connection connection, int holderID) throws SQLException {
        String query = "SELECT HolderAddress.houseNum, roadName, cityName, HolderAddress.postcode FROM HolderAddress "
                     + "JOIN AccountHolder ON HolderAddress.houseNum = AccountHolder.houseNum AND "
                     + "HolderAddress.postcode = AccountHolder.postcode "
                     + "WHERE AccountHolder.holderID = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, holderID);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new HolderAddress(
                    resultSet.getString("houseNum"),
                    resultSet.getString("roadName"),
                    resultSet.getString("cityName"),
                    resultSet.getString("postcode")
                );
            }
        }
        return null;
    }
    

    public void updateAccountDetails(Connection connection, Account account) throws SQLException {
        String updateQuery = "UPDATE Account SET email = ? WHERE userID = ?";
        try (PreparedStatement statement = connection.prepareStatement(updateQuery)) {
            statement.setString(1, account.getEmail());
            statement.setInt(2, account.getUserID());
            statement.executeUpdate();
        }
    }

    public void updateAccountHolderDetails(Connection connection, AccountHolder holder, HolderAddress newAddress) throws SQLException {
        String updateQuery = "UPDATE AccountHolder SET forename = ?, surname = ?, houseNum = ?, postcode = ? WHERE holderID = ?";
        try (PreparedStatement statement = connection.prepareStatement(updateQuery)) {
            statement.setString(1, holder.getForename());
            statement.setString(2, holder.getSurname());
            statement.setString(3, newAddress.getHouseNum());
            statement.setString(4, newAddress.getPostcode());
            statement.setInt(5, holder.getHolderID());
            statement.executeUpdate();
        }
    }
    
    
    public void updateAddressDetails(Connection connection, HolderAddress newAddress, String oldHouseNum, String oldPostcode) throws SQLException {
        String updateQuery = "UPDATE HolderAddress SET houseNum = ?, roadName = ?, cityName = ?, postcode = ? WHERE houseNum = ? AND postcode = ?";
        try (PreparedStatement statement = connection.prepareStatement(updateQuery)) {
            statement.setString(1, newAddress.getHouseNum());
            statement.setString(2, newAddress.getRoadName());
            statement.setString(3, newAddress.getCityName());
            statement.setString(4, newAddress.getPostcode());
            statement.setString(5, oldHouseNum);
            statement.setString(6, oldPostcode);
            statement.executeUpdate();
        }
    }
    
    

}
