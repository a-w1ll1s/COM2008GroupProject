import views.Manager.ManagerView;
import views.Staff.PendingOrderQueueView;
import models.database.*;
import models.business.*;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        DatabaseConnection databaseConnection = new DatabaseConnection();
        DatabaseMethods databaseMethods = new DatabaseMethods();

        ArrayList<Customer> customers = new ArrayList<>();
        ArrayList<Staff> staffs = new ArrayList<>();
        ArrayList<Order> pendingOrders = new ArrayList<>();

        try {
            databaseConnection.openConnection();

            // Fetch data from the database
            customers.addAll(databaseMethods.getCustomerDetails(databaseConnection.getConnection()));
            staffs.addAll(databaseMethods.getStaffDetails(databaseConnection.getConnection()));
            pendingOrders.addAll(databaseMethods.getPendingOrders(databaseConnection.getConnection()));
            System.out.println("Fetched orders: " + pendingOrders);
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            databaseConnection.closeConnection();
        }

        // Display both ManagerView and PendingOrderQueueView
        SwingUtilities.invokeLater(() -> {
            new ManagerView(customers, staffs);
            new PendingOrderQueueView(pendingOrders);
        });
    }
}
