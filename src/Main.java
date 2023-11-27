import views.MainFrame;
import views.Manager.ManagerView;
import models.database.*;
import models.business.*;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        DatabaseConnection databaseConnection = new DatabaseConnection();
        DatabaseMethods databaseMethods = new DatabaseMethods();

        ArrayList<Customer> tempCustomers = new ArrayList<>();
        ArrayList<Staff> tempStaffs = new ArrayList<>();

        try {
            databaseConnection.openConnection();

            // Fetch products, customers, and staff from the database
            ArrayList<Product> products = databaseMethods.getProducts(databaseConnection.getConnection());
            for (Product x : products) {
                System.out.println(x.toString());
            }

            tempCustomers = databaseMethods.getCustomerDetails(databaseConnection.getConnection());
            for (Customer x : tempCustomers) {
                System.out.println(x.toString());
            }

            tempStaffs = databaseMethods.getStaffDetails(databaseConnection.getConnection());
            for (Staff x : tempStaffs) {
                System.out.println(x.toString());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            databaseConnection.closeConnection();
        }

        final ArrayList<Customer> customers = tempCustomers;
        final ArrayList<Staff> staffs = tempStaffs;

        // Display the ManagerView with customer and staff data
        //SwingUtilities.invokeLater(() -> new ManagerView(customers, staffs));

        SwingUtilities.invokeLater(() -> new MainFrame("Application"));
    }
}
