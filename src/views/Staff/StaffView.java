package views.Staff;

import models.business.Customer;
import models.business.Staff;
import models.database.DatabaseConnection;
import models.database.DatabaseMethods;
import views.MainFrame;
import views.customer.ProductViewPanel;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;

public class StaffView extends JPanel {
    private MainFrame parentFrame;
    private JTable customerTable, staffTable;
    private JButton deleteButton, promoteButton;
    private ArrayList<Customer> customers;
    private ArrayList<Staff> staffs;

    public StaffView(MainFrame parentFrame) {
        this.parentFrame = parentFrame;

        initializeComponents();
        layoutComponents();
        
    }

    private void initializeComponents() {
    }

    private void layoutComponents() {
        setLayout(new BorderLayout());

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.add("Pending Orders", new JScrollPane(new PendingOrderQueueView(parentFrame)));
        tabbedPane.add("Edit Products", new ProductViewPanel(parentFrame));
        add(tabbedPane, BorderLayout.CENTER);

    }
}
