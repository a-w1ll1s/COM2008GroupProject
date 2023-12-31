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
import views.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;

public class ManagerView extends JPanel {
    private MainFrame parentFrame;
    private JTable customerTable, staffTable;
    private JButton deleteButton, promoteButton;
    private ArrayList<Customer> customers;
    private ArrayList<Staff> staffs;

    public ManagerView(MainFrame parentFrame) {
        this.parentFrame = parentFrame;

        
        initializeComponents();
        refreshCustomerTable();
        refreshStaffTable();
        layoutComponents();
        attachEventHandlers();
        
    }

    private void initializeComponents() {
        customerTable = new JTable(new CustomerTableModel(customers));
        staffTable = new JTable(new StaffTableModel(staffs));
        
        deleteButton = new JButton("Demote Staff to Customer");
        promoteButton = new JButton("Promote Customer to Staff");
    }

    private void layoutComponents() {
        setLayout(new BorderLayout());

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.add("Customers", new JScrollPane(customerTable));
        tabbedPane.add("Staff", new JScrollPane(staffTable));
        add(tabbedPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(promoteButton);
        buttonPanel.add(deleteButton);
        add(buttonPanel, BorderLayout.SOUTH);
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
                    JOptionPane.showMessageDialog(parentFrame, "Error promoting customer: " + ex.getMessage());
                } finally {
                    databaseConnection.closeConnection();
                }
            } else {
                JOptionPane.showMessageDialog(parentFrame, "Please select a customer to promote.");
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
                    JOptionPane.showMessageDialog(parentFrame, "Error demoting staff: " + ex.getMessage());
                } finally {
                    databaseConnection.closeConnection();
                }
            } else {
                JOptionPane.showMessageDialog(parentFrame, "Please select a staff member to demote.");
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
            JOptionPane.showMessageDialog(parentFrame, "Error fetching customer data: " + ex.getMessage());
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
            JOptionPane.showMessageDialog(parentFrame, "Error fetching staff data: " + ex.getMessage());
        } finally {
            databaseConnection.closeConnection();
        }
    }
    
    // Main method for testing
    //public static void main(String[] args) {
        // For testing, you can create a new ManagerView with empty lists
        //SwingUtilities.invokeLater(() -> new ManagerView(new ArrayList<>(), new ArrayList<>()));
    //}
}
