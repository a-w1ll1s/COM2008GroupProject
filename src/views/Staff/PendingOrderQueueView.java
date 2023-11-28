package views.Staff;

import models.business.Order;
import models.database.DatabaseConnection;
import models.database.DatabaseMethods;
import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;

public class PendingOrderQueueView {
    private JFrame frame;
    private JTable orderTable;
    private JButton fulfillButton, deleteButton;
    private ArrayList<Order> orders;
    private DatabaseMethods databaseMethods;

    public PendingOrderQueueView(ArrayList<Order> orders) {
        this.orders = orders;
        this.databaseMethods = new DatabaseMethods();
        initializeComponents();
        layoutComponents();
        attachEventHandlers();
    }

    private void initializeComponents() {
        frame = new JFrame("Pending Order Queue");
        orderTable = new JTable(new OrderTableModel(orders));
        
        fulfillButton = new JButton("Fulfill Order");
        deleteButton = new JButton("Delete Order");
    }

    private void layoutComponents() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setSize(800, 600);

        frame.add(new JScrollPane(orderTable), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(fulfillButton);
        buttonPanel.add(deleteButton);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    private void attachEventHandlers() {
        fulfillButton.addActionListener(e -> {
            int selectedRow = orderTable.getSelectedRow();
            if (selectedRow != -1) {
                Order order = orders.get(selectedRow);
                DatabaseConnection databaseConnection = new DatabaseConnection();
                try {
                    databaseConnection.openConnection();
                    databaseMethods.fulfillOrder(databaseConnection.getConnection(), order.getOrderID());
                    refreshOrderTable();
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(frame, "Error fulfilling order: " + ex.getMessage());
                } finally {
                    databaseConnection.closeConnection();
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Please select an order to fulfill.");
            }
        });

        deleteButton.addActionListener(e -> {
            int selectedRow = orderTable.getSelectedRow();
            if (selectedRow != -1) {
                Order order = orders.get(selectedRow);
                DatabaseConnection databaseConnection = new DatabaseConnection();
                try {
                    databaseConnection.openConnection();
                    databaseMethods.deleteOrder(databaseConnection.getConnection(), order.getOrderID());
                    refreshOrderTable();
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(frame, "Error deleting order: " + ex.getMessage());
                } finally {
                    databaseConnection.closeConnection();
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Please select an order to delete.");
            }
        });
    }

    private void refreshOrderTable() {
        DatabaseConnection databaseConnection = new DatabaseConnection();
        try {
            databaseConnection.openConnection();
            ArrayList<Order> updatedOrders = databaseMethods.getPendingOrders(databaseConnection.getConnection());
            orderTable.setModel(new OrderTableModel(updatedOrders));
            this.orders = updatedOrders;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(frame, "Error refreshing orders: " + ex.getMessage());
        } finally {
            databaseConnection.closeConnection();
        }
    
        
    }

}

   
