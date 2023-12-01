package views.customer;

import java.awt.BorderLayout;
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

        setLayout(new BorderLayout());

        updateOrderHistory();

        add(new JScrollPane(getOrderTable()), BorderLayout.CENTER);
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
        model.addColumn("Order ID");
        model.addColumn("Date");
        model.addColumn("Products");
        model.addColumn("Price");

        for (Order order : orderHistory) {
            Object[] row = new Object[4];
            row[0] = order.getOrderID();
            row[1] = order.getDate();

            String products = "";
            for (OrderLine line : order.getOrderLines()) {
                products += line.getQuantity()+"x "+line.getProduct().getName() + ", ";
            }
            row[2] = products;
            row[3] = "£" + order.getOrderCost();

            model.addRow(row);
        }

        JTable orders = new JTable(model);
        orders.getColumnModel().getColumn(2).setPreferredWidth(900);

        return orders;
    }

}