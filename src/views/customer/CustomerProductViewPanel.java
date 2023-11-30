package views.customer;

import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import models.business.Account;
import models.business.Order;
import models.business.OrderLine;
import models.database.DatabaseConnection;
import models.database.DatabaseMethods;
import views.MainFrame;

public class CustomerProductViewPanel extends JPanel {
    private CustomerView customerView;
    private ProductViewPanel productViewPanel;
    private Order order;
    private Account account;
    
    public CustomerProductViewPanel(CustomerView customerView, Account account) {        
        this.customerView = customerView;
        this.account = account;
        setLayout(new GridBagLayout());

        GridBagConstraints productViewPanelConstraints = new GridBagConstraints();
        productViewPanelConstraints.fill = GridBagConstraints.BOTH;
        productViewPanelConstraints.weightx = 1;
        productViewPanelConstraints.weighty = 1;
        productViewPanel = new ProductViewPanel();

        // Options panel
        JPanel optionsPanel = new JPanel();
        optionsPanel.setLayout(new GridBagLayout());
        GridBagConstraints optionsPanelConstraints = new GridBagConstraints();
        optionsPanelConstraints.fill = GridBagConstraints.BOTH;
        optionsPanelConstraints.weightx = 1;
        optionsPanelConstraints.gridy = 1;
        
        // Try find a previous order
        DatabaseConnection databaseConnection = new DatabaseConnection();
        try {
            databaseConnection.openConnection();
            ArrayList<Order> pendingOrders = DatabaseMethods.getPendingCustomerOrders(
                databaseConnection.getConnection(), 
                account.getUserID());

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


        GridBagConstraints optionButtonConstraints = new GridBagConstraints();
        optionButtonConstraints.insets = new Insets(5, 5, 5, 5);

        JButton confirmOrderButton = new JButton("Confirm order");
        confirmOrderButton.addActionListener(e -> {
            if (order == null) {
                JOptionPane.showMessageDialog(customerView.getFrame(), "Add a product first!");
            }
            else {
                if (order.getOrderLines().size() == 0){ 
                    JOptionPane.showMessageDialog(customerView.getFrame(), "Add a product first!");
                }
                else {
                    customerView.switchToConfirmOrderView(order);
                }
            }
        });

        optionsPanel.add(confirmOrderButton, optionButtonConstraints);

        JButton addToOrderButton = new JButton("Add to order");
        addToOrderButton.addActionListener(e -> {
            addSelectedProductToOrder(); 
        });

        JButton removeFromOrderButton = new JButton("Remove from order");
        removeFromOrderButton.addActionListener(e -> {
            if (productViewPanel.getSelectedProductID() == -1) {
                JOptionPane.showMessageDialog(customerView.getFrame(), "Please select a product!");
                return;
            }
        });

        JLabel quantityLabel = new JLabel("Order Quantity: ");
        JSpinner quantitySpinner = new JSpinner(new SpinnerNumberModel(0, 0, 100, 1));

        quantitySpinner.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                JSpinner s = (JSpinner) e.getSource();
                System.out.println(s.getValue().toString());
            }
        });

        optionsPanel.add(addToOrderButton, optionButtonConstraints);
        optionsPanel.add(removeFromOrderButton, optionButtonConstraints);
        optionsPanel.add(quantityLabel, optionButtonConstraints);
        optionsPanel.add(quantitySpinner);

        add(productViewPanel, productViewPanelConstraints);
        add(optionsPanel, optionsPanelConstraints);
    }

    private void addSelectedProductToOrder() {
        if (productViewPanel.getSelectedProductID() == -1) {
            JOptionPane.showMessageDialog(customerView.getFrame(), "Please select a product!");
            return;
        }

        DatabaseConnection databaseConnection = new DatabaseConnection();
        if (order == null) {
            try {
                databaseConnection.openConnection();
                order = DatabaseMethods.createNewOrGetPendingOrder(databaseConnection.getConnection(), 
                    account.getUserID());
            } 
            catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Error creating/getting order: " + ex.getMessage());
                databaseConnection.closeConnection();
                return;
            } 
        }

        try {
            databaseConnection.openConnection();

            OrderLine orderLine = DatabaseMethods.createOrIncrementOrderLine(databaseConnection.getConnection(),
                order.getOrderID(),
                productViewPanel.getSelectedProduct()
            );

            Boolean orderLineExists = false;
            for (OrderLine line : order.getOrderLines()) {
                if (line.getLineNum() == orderLine.getLineNum()) {
                    orderLineExists = true;
                    line.setQuantity(orderLine.getQuantity());
                }
            }
            if (!orderLineExists) {
                order.addOrderLine(orderLine);
            }
            
        } 
        catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error creating order line: " + ex.getMessage());
            return;
        } 
        finally {
            databaseConnection.closeConnection();
        }
    }
}