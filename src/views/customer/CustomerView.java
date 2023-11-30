package views.customer;

import models.business.Account;
import models.business.Customer;
import models.business.Order;
import models.business.OrderLine;
import models.database.DatabaseConnection;
import models.database.DatabaseMethods;
import views.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerView extends JPanel {
    private MainFrame parentFrame;
    private CustomerProductViewPanel productViewPanel;
    private Customer customer;
    private Order order;

    public CustomerView(MainFrame parentFrame, Customer customer) {
        this.parentFrame = parentFrame;
        this.customer = customer;

        initializeComponents();
        layoutComponents();
    }

    private void initializeComponents() {
        // Try find a previous order
        DatabaseConnection databaseConnection = new DatabaseConnection();
        try {
            databaseConnection.openConnection();
            ArrayList<Order> pendingOrders = DatabaseMethods.getPendingCustomerOrders(
                databaseConnection.getConnection(), 
                customer.getUserID());

            if (pendingOrders.size() != 0) {
                order = pendingOrders.get(0);

                for (OrderLine orderLine : DatabaseMethods.getOrderLinesForOrder(databaseConnection.getConnection(), 
                    order.getOrderID())) {
                    order.addOrderLine(orderLine);
                }
            }                 
        } 
        catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error getting orders: " + ex.getMessage());
            return;
        } 
        finally {
            databaseConnection.closeConnection();
        }
    }

    private void layoutComponents() {
        setLayout(new BorderLayout());

        productViewPanel = new CustomerProductViewPanel(this, customer);
        add(productViewPanel);
    }

    public void switchToProductsView() {
        switchToPanelView(productViewPanel);
    }

    public void switchToConfirmOrderView() {
        ConfirmOrderPanel confirmOrderPanel = new ConfirmOrderPanel(this, parentFrame, customer, order);
        switchToPanelView(confirmOrderPanel);
    }

    public MainFrame getFrame() {
        return parentFrame;
    }

    private void switchToPanelView(JPanel panel) { 
        removeAll();
        add(panel);

        revalidate();
        repaint();
        setVisible(true);
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
