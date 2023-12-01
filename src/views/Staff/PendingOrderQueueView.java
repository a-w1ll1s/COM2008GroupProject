package views.Staff;

import models.business.Order;
import models.database.DatabaseConnection;
import models.database.DatabaseMethods;
import views.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;

public class PendingOrderQueueView extends JPanel {
    private MainFrame parentFrame;
    private JTable orderTable;
    private JButton fulfillButton, deleteButton;
    private ArrayList<Order> orders;

    public PendingOrderQueueView(MainFrame parentFrame) {
        this.parentFrame = parentFrame;

        updatePendingOrders();
        initializeComponents();
        layoutComponents();
        attachEventHandlers();
    }

    private void updatePendingOrders() {
        DatabaseConnection databaseConnection = new DatabaseConnection();
        try {
            databaseConnection.openConnection();
            this.orders = DatabaseMethods.getConfirmedOrders(databaseConnection.getConnection());
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(parentFrame, "Error fulfilling order: " + ex.getMessage());
        } finally {
            databaseConnection.closeConnection();
        }
    }

    private void initializeComponents() {
        orderTable = new JTable(new OrderTableModel(orders));
        
        fulfillButton = new JButton("Fulfill Order");
        deleteButton = new JButton("Delete Order");
    }

    private void layoutComponents() {
        setLayout(new BorderLayout());

        add(new JScrollPane(orderTable), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(fulfillButton);
        buttonPanel.add(deleteButton);
        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void attachEventHandlers() {
        fulfillButton.addActionListener(e -> {
            int selectedRow = orderTable.getSelectedRow();
            if (selectedRow != -1) {
                Order order = orders.get(selectedRow);
                DatabaseConnection databaseConnection = new DatabaseConnection();
                try {
                    databaseConnection.openConnection();
                    DatabaseMethods.fulfillOrder(databaseConnection.getConnection(), order.getOrderID());
                    refreshOrderTable();
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(parentFrame, "Error fulfilling order: " + ex.getMessage());
                } finally {
                    databaseConnection.closeConnection();
                }
            } else {
                JOptionPane.showMessageDialog(parentFrame, "Please select an order to fulfill.");
            }
        });

        deleteButton.addActionListener(e -> {
            int selectedRow = orderTable.getSelectedRow();
            if (selectedRow != -1) {
                Order order = orders.get(selectedRow);
                DatabaseConnection databaseConnection = new DatabaseConnection();
                try {
                    databaseConnection.openConnection();
                    DatabaseMethods.deleteOrder(databaseConnection.getConnection(), order.getOrderID());
                    refreshOrderTable();
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(parentFrame, "Error deleting order: " + ex.getMessage());
                } finally {
                    databaseConnection.closeConnection();
                }
            } else {
                JOptionPane.showMessageDialog(parentFrame, "Please select an order to delete.");
            }
        });
    }

    private void refreshOrderTable() {
        DatabaseConnection databaseConnection = new DatabaseConnection();
        try {
            databaseConnection.openConnection();
            ArrayList<Order> updatedOrders = DatabaseMethods.getConfirmedOrders(databaseConnection.getConnection());
            orderTable.setModel(new OrderTableModel(updatedOrders));
            this.orders = updatedOrders;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(parentFrame, "Error refreshing orders: " + ex.getMessage());
        } finally {
            databaseConnection.closeConnection();
        }
    }

}

   
