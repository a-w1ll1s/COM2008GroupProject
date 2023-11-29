package views.customer;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.*;

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
import views.MainFrame;

public class OrderHistoryPanel extends JPanel {
    private MainFrame parentFrame;
    private ArrayList<Order> orderHistory = new ArrayList<>();
    private Account account;
    
    public OrderHistoryPanel(MainFrame frame, Account account) {        
        parentFrame = frame;
        this.account = account;
        setLayout(new GridBagLayout());

        updateOrderHistory();

        GridBagConstraints orderPanelConstraints = new GridBagConstraints();
        orderPanelConstraints.fill = GridBagConstraints.HORIZONTAL;
        orderPanelConstraints.weightx = 1;

        for (Order order : orderHistory) {
            OrderPanel orderPanel = new OrderPanel(order);
            add(orderPanel, orderPanelConstraints);
            orderPanelConstraints.gridy++;
        }

        //JTable table = new JTable(data, columnNames);
        
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
}