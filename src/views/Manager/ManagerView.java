package views.Manager;
/**
 * Manager view allows the user to promote customers to staff and vice versa
 * 
 * Liam Thomas, November 2023
 */
import models.business.Customer;
import models.business.Staff;
import models.database.DatabaseConnection;
import models.database.DatabaseMethods;
import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;

public class ManagerView {

    private JFrame frame;
    private JTable customerTable, staffTable;
    private JButton deleteButton, promoteButton;
    private ArrayList<Customer> customers;
    private ArrayList<Staff> staffs;

    public ManagerView(ArrayList<Customer> customers, ArrayList<Staff> staffs) {
        this.customers = customers;
        this.staffs = staffs;
        initializeComponents();
        layoutComponents();
        attachEventHandlers();
    }

    private void initializeComponents() {
        frame = new JFrame("Manager Panel");
        customerTable = new JTable(new CustomerTableModel(customers));
        staffTable = new JTable(new StaffTableModel(staffs));
        
        deleteButton = new JButton("Demote Staff to Customer");
        promoteButton = new JButton("Promote Customer to Staff");
    }

    private void layoutComponents() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setSize(800, 600);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.add("Customers", new JScrollPane(customerTable));
        tabbedPane.add("Staff", new JScrollPane(staffTable));
        frame.add(tabbedPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(promoteButton);
        buttonPanel.add(deleteButton);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    private void attachEventHandlers() {
        promoteButton.addActionListener(e -> {
            int selectedRow = customerTable.getSelectedRow();
            if (selectedRow != -1) {
                Customer customer = customers.get(selectedRow);
                 DatabaseConnection databaseConnection = new DatabaseConnection();
                try {
                    databaseConnection.openConnection();
                    DatabaseMethods.promoteCustomerToStaff(databaseConnection.getConnection(), customer.getUserID());
                    refreshCustomerTable();
                    refreshStaffTable();
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(frame, "Error promoting customer: " + ex.getMessage());
                } finally {
                    databaseConnection.closeConnection();
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Please select a customer to promote.");
            }
        });
    
        deleteButton.addActionListener(e -> {
            int selectedRow = staffTable.getSelectedRow();
            if (selectedRow != -1) {
                Staff staff = staffs.get(selectedRow);
                DatabaseConnection databaseConnection = new DatabaseConnection();
                try {
                    databaseConnection.openConnection();
                    DatabaseMethods.demoteStaffToCustomer(databaseConnection.getConnection(), staff.getUserID());
                    refreshStaffTable();
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(frame, "Error demoting staff: " + ex.getMessage());
                } finally {
                    databaseConnection.closeConnection();
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Please select a staff member to demote.");
            }
        });
    }
    


    
    private void refreshCustomerTable() {
        DatabaseConnection databaseConnection = new DatabaseConnection();
        try {
            databaseConnection.openConnection();
            ArrayList<Customer> updatedCustomers = DatabaseMethods.getCustomerDetails(databaseConnection.getConnection());
            customerTable.setModel(new CustomerTableModel(updatedCustomers));
            this.customers = updatedCustomers; // Update the local customers list
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(frame, "Error fetching customer data: " + ex.getMessage());
        } finally {
            databaseConnection.closeConnection();
        }
    }
    
    private void refreshStaffTable() {
        DatabaseConnection databaseConnection = new DatabaseConnection();
        try {
            databaseConnection.openConnection();
            ArrayList<Staff> updatedStaffs = DatabaseMethods.getStaffDetails(databaseConnection.getConnection());
            staffTable.setModel(new StaffTableModel(updatedStaffs));
            this.staffs = updatedStaffs; // Update the local staffs list
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(frame, "Error fetching staff data: " + ex.getMessage());
        } finally {
            databaseConnection.closeConnection();
        }
    }
    
   
    
    // Main method for testing
    public static void main(String[] args) {
        // For testing, you can create a new ManagerView with empty lists
        SwingUtilities.invokeLater(() -> new ManagerView(new ArrayList<>(), new ArrayList<>()));
    }
}
