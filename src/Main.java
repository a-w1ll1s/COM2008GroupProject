import views.MainFrame;
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

        /* 
        ArrayList<Customer> customers = new ArrayList<>();
        ArrayList<Staff> staffs = new ArrayList<>();
        ArrayList<Order> pendingOrders = new ArrayList<>();

        try {
            databaseConnection.openConnection();

            // Fetch data from the database
            customers.addAll(DatabaseMethods.getCustomerDetails(databaseConnection.getConnection()));
            staffs.addAll(DatabaseMethods.getStaffDetails(databaseConnection.getConnection()));
            
            System.out.println("Fetched orders: " + pendingOrders);
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            databaseConnection.closeConnection();
        }
        */

        SwingUtilities.invokeLater(() -> new MainFrame("Application"));
    }
}
