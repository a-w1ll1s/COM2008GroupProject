package views.customer;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import helpers.ViewHelpers;
import models.business.Customer;
import models.business.Order;
import models.business.OrderLine;
import models.database.DatabaseConnection;
import models.database.DatabaseMethods;
import views.CustomStyleConstants;

public class OrderLinePanel extends JPanel {
    private CustomerView customerView; 
    private OrderPanel orderPanel;
    private OrderLine orderLine;

    public OrderLinePanel(CustomerView customerView, OrderPanel orderPanel, OrderLine orderLine) {
        this.customerView = customerView;
        this.orderPanel = orderPanel;
        this.orderLine = orderLine;

        //setLayout(new GridBagLayout());
        setLayout(new GridLayout(6, 2));
        setBackground(CustomStyleConstants.SECTION_COLOUR);
        setBorder(BorderFactory.createLoweredSoftBevelBorder());

        add(new JLabel("Product: "));
        add(new JLabel(orderLine.getProduct().getName()));

        add(new JLabel("Manufacturer: "));
        add(new JLabel(orderLine.getProduct().getManufacturer()));

        add(new JLabel("Gauge: "));
        add(new JLabel(orderLine.getProduct().getGauge()));

        add(new JLabel("Price: "));
        add(new JLabel("£" + Integer.toString(orderLine.getLinePrice())));

        add(new JLabel("Quantity: "));

        JSpinner quantitySpinner = new JSpinner(new SpinnerNumberModel(this.orderLine.getQuantity(), 
            0, 100, 1));

        quantitySpinner.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int newQuantity = (int)((JSpinner)e.getSource()).getValue();

                DatabaseConnection databaseConnection = new DatabaseConnection();
                try {
                    databaseConnection.openConnection();

                    Order order = customerView.getOrder();

                    if (newQuantity == 0) {
                        orderPanel.removeFromOrder(orderLine);
                        return;
                    }

                    OrderLine newOrderLine = DatabaseMethods.createOrUpdateOrderLine(databaseConnection.getConnection(),
                        order.getOrderID(),
                        orderLine.getProduct(),
                        newQuantity
                    );

                    order.addOrUpdateOrderLine(newOrderLine);
                    customerView.setOrder(order);
                } 
                catch (SQLException ex) {
                    return;
                } 
                finally {
                    databaseConnection.closeConnection();
                }
            }
        });

        add(quantitySpinner);

        // Option buttons
        JButton removeButton = new JButton("Remove");
        removeButton.addActionListener(e -> {
            orderPanel.removeFromOrder(orderLine);
        });

        add(new JLabel());
        add(removeButton);

        /* 
        add(new JLabel("Product: " + orderLine.getProduct().getName()), ViewHelpers.getGridBagConstraints(0, 0));
        add(new JLabel("Manufacturer: " + orderLine.getProduct().getManufacturer()), ViewHelpers.getGridBagConstraints(0, 1));
        add(new JLabel("Gauge: " + orderLine.getProduct().getGauge()), ViewHelpers.getGridBagConstraints(0, 2));
        add(new JLabel("Quantity: "), ViewHelpers.getGridBagConstraints(0, 3));

        JSpinner quantitySpinner = new JSpinner(new SpinnerNumberModel(this.orderLine.getQuantity(), 
            0, 100, 1));

        quantitySpinner.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int newQuantity = (int)((JSpinner)e.getSource()).getValue();

                DatabaseConnection databaseConnection = new DatabaseConnection();
                try {
                    databaseConnection.openConnection();

                    Order order = customerView.getOrder();

                    if (newQuantity == 0) {
                        orderPanel.removeFromOrder(orderLine);
                        return;
                    }

                    OrderLine newOrderLine = DatabaseMethods.createOrUpdateOrderLine(databaseConnection.getConnection(),
                        order.getOrderID(),
                        orderLine.getProduct(),
                        newQuantity
                    );

                    order.addOrUpdateOrderLine(newOrderLine);
                    customerView.setOrder(order);
                } 
                catch (SQLException ex) {
                    return;
                } 
                finally {
                    databaseConnection.closeConnection();
                }
            }
        });

        add(quantitySpinner, ViewHelpers.getGridBagConstraints(1, 3, false));

        // Option buttons
        JButton removeButton = new JButton("Remove");
        removeButton.addActionListener(e -> {
            orderPanel.removeFromOrder(orderLine);
        });

        GridBagConstraints removeButtonConstraints = ViewHelpers.getGridBagConstraints(1, 1, false);
        removeButtonConstraints.insets = new Insets(5, 5, 5, 5);
        add(removeButton, removeButtonConstraints);
        */
    }
}
