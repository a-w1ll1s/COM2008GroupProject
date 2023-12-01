package views.customer;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import helpers.ViewHelpers;
import models.business.Account;
import models.business.Controller;
import models.business.Locomotive;
import models.business.Order;
import models.business.OrderLine;
import models.business.Product;
import models.business.RollingStock;
import models.business.Track;
import models.database.DatabaseConnection;
import models.database.DatabaseMethods;
import views.Staff.OrderTableModel;
import views.CustomStyleConstants;
import views.MainFrame;

public class OrderHistoryPanel extends JPanel {
    private CustomerView customerView;
    private ArrayList<Order> orderHistory = new ArrayList<>();
    private Account account;
    private JPanel contentsPanel;
    
    public OrderHistoryPanel(CustomerView customerView, Account account) {  
        this.customerView = customerView;      
        this.account = account;

        updateOrderHistory();

        add(new JScrollPane(getOrderTable()));
    }

    private void updateOrderHistory() {
        // Try get all the products of the given type from the database
        DatabaseConnection databaseConnection = new DatabaseConnection();
        try {
            databaseConnection.openConnection();
            orderHistory = DatabaseMethods.getPastCustomerOrders(databaseConnection.getConnection(),
                account.getUserID()
                );

            for (Order order : orderHistory) {
                ArrayList<OrderLine> orderLines = DatabaseMethods.getOrderLinesForOrder(databaseConnection.getConnection(), order.getOrderID());
                for (OrderLine line : orderLines) {
                    order.addOrderLine(line);
                }
            }
        } 
        catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error getting order history " + ex.getMessage());
            return;
        } 
        finally {
            databaseConnection.closeConnection();
        }

    }

    private JTable getOrderTable() {

        DefaultTableModel model = new DefaultTableModel();

        for (Order order : orderHistory) {
            model.addRow(new Object[]{order.toString()}); 
            for (OrderLine line : order.getOrderLines()) {
                model.addRow(new Object[]{line.toString()});
            }
        }

        JTable orders = new JTable(model);

        return orders;
    }

}