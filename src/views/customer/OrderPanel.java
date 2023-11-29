package views.customer;

import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import models.business.Order;
import models.business.OrderLine;

class OrderPanel extends JPanel {
    private Order order; 
    private Font boldFont = new Font("", Font.BOLD, 14);

    public OrderPanel(Order order) {
        this.order = order;
        setLayout(new GridLayout());

        JLabel titleLabel = new JLabel("Order ID: " + Integer.toString(order.getOrderID()));
        add(titleLabel);

        JLabel dateLabel = new JLabel("Date: " + Integer.toString(order.getDate()));
        add(dateLabel);

        JLabel costLabel = new JLabel("Total Price; " + Integer.toString(order.getOrderCost()));
        add(costLabel);


        for (OrderLine line : order.getOrderLines()) {
            add(new JLabel(line.toString()));
            add(new JLabel());
        }
    }
}