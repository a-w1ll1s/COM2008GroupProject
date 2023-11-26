import views.*;
import models.database.*;
import models.business.*;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Main is the program entry.
 * 
 * Oliver Pickford, November 2023
 */
public class Main {
    public static void main (String[] args) {
        new MainFrame("MainFrame!");

        DatabaseConnection databaseConnection = new DatabaseConnection();
        DatabaseMethods databaseMethods = new DatabaseMethods();

        try {
            databaseConnection.openConnection();

            ArrayList<Product> products = databaseMethods.getProducts(databaseConnection.getConnection()); 

            for (Product x : products) {
                System.out.println(x.toString());
            }

            ArrayList<Customer> customers = databaseMethods.getCustomerDetails(databaseConnection.getConnection()); 

            for (Customer x : customers) {
                System.out.println(x.toString());
            }

            ArrayList<Staff> staffs = databaseMethods.getStaffDetails(databaseConnection.getConnection()); 

            for (Staff x : staffs) {
                System.out.println(x.toString());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            databaseConnection.closeConnection();
        }
        
    }
}
