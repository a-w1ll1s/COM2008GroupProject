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

public class CustomerProductViewPanel extends JPanel {
    private CustomerView customerView;
    private ProductViewPanel productViewPanel;
    private Account account;
    private JSpinner quantitySpinner;
    private JPanel optionsPanel;
    
    public CustomerProductViewPanel(CustomerView customerView, Account account) {        
        this.customerView = customerView;
        this.account = account;
        setLayout(new GridBagLayout());

        GridBagConstraints productViewPanelConstraints = new GridBagConstraints();
        productViewPanelConstraints.fill = GridBagConstraints.BOTH;
        productViewPanelConstraints.weightx = 1;
        productViewPanelConstraints.weighty = 1;
        productViewPanel = new ProductViewPanel(this);

        add(productViewPanel, productViewPanelConstraints);
        setupOptionsPanel();
    }

    private void setupOptionsPanel() {
        // Options panel
        if (optionsPanel != null)
            remove(optionsPanel);

        optionsPanel = new JPanel();
        optionsPanel.setLayout(new GridBagLayout());
        GridBagConstraints optionsPanelConstraints = new GridBagConstraints();
        optionsPanelConstraints.fill = GridBagConstraints.BOTH;
        optionsPanelConstraints.weightx = 1;
        optionsPanelConstraints.gridy = 1;

        GridBagConstraints optionButtonConstraints = new GridBagConstraints();
        optionButtonConstraints.insets = new Insets(5, 5, 5, 5);

        JButton confirmOrderButton = new JButton("Confirm order");
        confirmOrderButton.addActionListener(e -> {
            Order order = customerView.getOrder();
            if (order == null) {
                JOptionPane.showMessageDialog(customerView.getFrame(), "Add a product first!");
            }
            else {
                if (order.getOrderLines().size() == 0){ 
                    JOptionPane.showMessageDialog(customerView.getFrame(), "Add a product first!");
                }
                else {
                    customerView.switchToConfirmOrderView();
                }
            }
        });

        optionsPanel.add(confirmOrderButton, optionButtonConstraints);

        JButton addToOrderButton = new JButton("Add to order");
        addToOrderButton.addActionListener(e -> {
            addSelectedProductToOrder(); 
            quantitySpinner.setValue(
                customerView.getOrder().getOrderLine(productViewPanel.getSelectedProductID()).getQuantity());
            quantitySpinner.revalidate();
            quantitySpinner.repaint();
            quantitySpinner.setVisible(true);

        });

        JButton removeFromOrderButton = new JButton("Remove from order");
        removeFromOrderButton.addActionListener(e -> {
            if (productViewPanel.getSelectedProductID() == -1) {
                JOptionPane.showMessageDialog(customerView.getFrame(), "Please select a product!");
                return;
            }

            Order order = customerView.getOrder();
            OrderLine orderLine = order.getOrderLine(productViewPanel.getSelectedProductID());

            DatabaseConnection databaseConnection = new DatabaseConnection();
            try {
                databaseConnection.openConnection();
                DatabaseMethods.deleteOrderLine(databaseConnection.getConnection(), 
                    order.getOrderID(),
                    orderLine.getLineNum());
            } 
            catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Error deleting order line " + ex.getMessage());
                return;
            } 
            finally {
                databaseConnection.closeConnection();
            }

            ArrayList<OrderLine> newLines = new ArrayList<>();
            for (OrderLine line : order.getOrderLines()) {
                if (line.getLineNum() != orderLine.getLineNum()) {
                    newLines.add(line);
                }
            }
            order.setOrderLines(newLines);
            customerView.setOrder(order);

            // Must redraw spinner so it does not fire the value changed
            setupOptionsPanel();
        });

        JLabel quantityLabel = new JLabel("Order Quantity: ");
        quantitySpinner = new JSpinner(new SpinnerNumberModel(0, 0, 100, 1));

        quantitySpinner.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int newQuantity = (int)((JSpinner)e.getSource()).getValue();
                
                DatabaseConnection databaseConnection = new DatabaseConnection();
                try {
                    databaseConnection.openConnection();

                    Order order = customerView.getOrder();
                    OrderLine orderLine = DatabaseMethods.createOrUpdateOrderLine(databaseConnection.getConnection(),
                        order.getOrderID(),
                        productViewPanel.getSelectedProduct(),
                        newQuantity
                    );

                    order.addOrUpdateOrderLine(orderLine);
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

        optionsPanel.add(addToOrderButton, optionButtonConstraints);
        optionsPanel.add(removeFromOrderButton, optionButtonConstraints);
        optionsPanel.add(quantityLabel, optionButtonConstraints);
        optionsPanel.add(quantitySpinner);

        add(optionsPanel, optionsPanelConstraints);

        optionsPanel.revalidate();
        optionsPanel.repaint();
        optionsPanel.setVisible(true);

        
    }
    
    private void addSelectedProductToOrder() {
        if (productViewPanel.getSelectedProductID() == -1) {
            JOptionPane.showMessageDialog(customerView.getFrame(), "Please select a product!");
            return;
        }

        DatabaseConnection databaseConnection = new DatabaseConnection();
        Order order = customerView.getOrder();
        
        if (order == null) {
            try {
                databaseConnection.openConnection();
                order = DatabaseMethods.createNewOrGetPendingOrder(databaseConnection.getConnection(), 
                    account.getUserID());

                customerView.setOrder(order);
            } 
            catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Error creating/getting order: " + ex.getMessage());
                databaseConnection.closeConnection();
                return;
            } 
        }

        try {
            databaseConnection.openConnection();

            order = customerView.getOrder();
            OrderLine before = order.getOrderLine(productViewPanel.getSelectedProductID()); 

            int newQuantity = 1;
            if (before != null) {
                newQuantity = before.getQuantity() + 1;
            }

            OrderLine orderLine = DatabaseMethods.createOrUpdateOrderLine(databaseConnection.getConnection(),
                order.getOrderID(),
                productViewPanel.getSelectedProduct(),
                newQuantity
            );

            System.out.println(orderLine);

            order.addOrUpdateOrderLine(orderLine);
            customerView.setOrder(order);
            System.out.println(customerView.getOrder().getOrderLines() + " 2");
        } 
        catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error creating order line: " + ex.getMessage());
            return;
        } 
        finally {
            databaseConnection.closeConnection();
        }
    }

    public void onSelectedProductChanged() {
        setupOptionsPanel();
        if (quantitySpinner == null)
            return;

        OrderLine line = customerView.getOrder().getOrderLine(productViewPanel.getSelectedProductID());
        if (line == null) {
            quantitySpinner.setValue(0);
        }
        else {
            quantitySpinner.setValue(line.getQuantity());
        }
        quantitySpinner.revalidate();
        quantitySpinner.repaint();
        quantitySpinner.setVisible(true);
    }
}