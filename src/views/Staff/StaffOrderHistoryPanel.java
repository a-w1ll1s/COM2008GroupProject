package views.Staff;

import models.business.Order;
import models.business.OrderLine;
import models.database.DatabaseConnection;
import models.database.DatabaseMethods;
import views.MainFrame;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;

public class StaffOrderHistoryPanel extends JPanel {
    private MainFrame parentFrame;
    private JTable orderTable;
    private ArrayList<Order> orders;

    public StaffOrderHistoryPanel(MainFrame parentFrame) {
        this.parentFrame = parentFrame;

        getOrders();

        orderTable = getOrderTable();

        setLayout(new BorderLayout());
        add(new JScrollPane(orderTable), BorderLayout.CENTER);
        setVisible(true);
    }

    private void getOrders() {
        DatabaseConnection databaseConnection = new DatabaseConnection();
        try {
            databaseConnection.openConnection();
            this.orders = DatabaseMethods.getOrderHistory(databaseConnection.getConnection());

            for (Order order : orders) {
                ArrayList<OrderLine> orderLines = DatabaseMethods.getOrderLinesForOrder(databaseConnection.getConnection(), order.getOrderID());
                for (OrderLine line : orderLines) {
                    order.addOrderLine(line);
                }
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(parentFrame, "Error getting order history: " + ex.getMessage());
        } finally {
            databaseConnection.closeConnection();
        }
    }

    private JTable getOrderTable() {

        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Order ID");
        model.addColumn("Date");
        model.addColumn("Products");
        model.addColumn("Price");

        for (Order order : orders) {
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