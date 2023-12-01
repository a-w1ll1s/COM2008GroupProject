package views.customer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.text.View;

import helpers.ViewHelpers;
import models.business.Account;
import models.business.AccountHolder;
import models.business.BankDetails;
import models.business.Customer;
import models.business.HolderAddress;
import models.business.Order;
import models.business.OrderLine;
import models.database.DatabaseConnection;
import models.database.DatabaseMethods;

class OrderPanel extends JPanel {
    private Customer customer; 
    private Font boldFont = new Font("", Font.BOLD, 14);
    private JTextField bankBrandTextField, bankExpiryTextField, bankCardNumberTextField, bankSecurityCodeTextField;
    private final int ENTRY_COLUMNS = 15;
    private JPanel contentsPanel, displayPanel;
    private CustomerView customerView;

    public OrderPanel(CustomerView customerView, Customer customer) {
        this.customerView = customerView;
        this.customer = customer;
        setLayout(new GridBagLayout());

        // Try get existing bank details
        updateBankDetails();

        displayPanel = new JPanel();
        displayPanel.setLayout(new GridBagLayout());
        displayOrderDetails();

        contentsPanel = new JPanel();
        contentsPanel.setLayout(new GridBagLayout());
        displayOrderContents();
    }
    private void updateBankDetails() { 
        DatabaseConnection databaseConnection = new DatabaseConnection();
        try {
            databaseConnection.openConnection();
            BankDetails bankDetails = DatabaseMethods.getBankDetails(databaseConnection.getConnection(), 
                customer.getUserID());

            if (bankDetails != null) {
                customer.addBankDetails(bankDetails);
            }
        } 
        catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error getting bank details " + ex.getMessage());
            return;
        } 
        finally {
            databaseConnection.closeConnection();
        }
    }

    public void displayOrderDetails() {
        Order order = customerView.getOrder();

        if (order.getOrderLines().size() == 0) {
            customerView.switchToProductsView();
        }

        displayPanel.removeAll();

        int row = 0;

        // Account info
        AccountHolder holder = customer.getHolder();
        HolderAddress address = holder.getAddress();

        // Order info
        JLabel titleLabel = new JLabel("Confirm your order");
        titleLabel.setFont(boldFont);
        displayPanel.add(titleLabel, ViewHelpers.getGridBagConstraints(0, row));
        row++;
        
        displayPanel.add(new JLabel("Order ID: " + Integer.toString(order.getOrderID())), 
            ViewHelpers.getGridBagConstraints(0, row));
        row++;

        displayPanel.add(new JLabel("Name: " + holder.getName()), ViewHelpers.getGridBagConstraints(0, row));
        row++;
        
        String dateString = Integer.toString(order.getDate());
        String formattedDate = String.format("%s/%s/%s", 
            dateString.substring(0, 2),
            dateString.substring(2, 4),
            dateString.substring(4));

        displayPanel.add(new JLabel("Date: " + formattedDate), ViewHelpers.getGridBagConstraints(0, row));
        row++;

        displayPanel.add(new JLabel("Total Price: £" + Integer.toString(order.getOrderCost())),
            ViewHelpers.getGridBagConstraints(0, row));
        row++;

        JLabel deliveryLabel = new JLabel("Delivery Details");
        deliveryLabel.setFont(boldFont);
        GridBagConstraints deliveryLabelConstraints = ViewHelpers.getGridBagConstraints(0, row);
        deliveryLabelConstraints.insets = new Insets(5, 0, 0, 0);
        displayPanel.add(deliveryLabel, deliveryLabelConstraints);
        row++;

        displayPanel.add(new JLabel("Road Name: " + address.getRoadName()), ViewHelpers.getGridBagConstraints(0, row));
        row++;
        
        displayPanel.add(new JLabel("Postcode: " + address.getPostcode()), ViewHelpers.getGridBagConstraints(0, row));
        row++;

        displayPanel.add(new JLabel("City: " + address.getCityName()), ViewHelpers.getGridBagConstraints(0, row));
        row++;

        // Bank details

        
        // Create form for bank details
        JLabel detailsLabel = new JLabel("Bank Details");
        detailsLabel.setFont(boldFont);
        GridBagConstraints detailsLabelConstraints = ViewHelpers.getGridBagConstraints(0, row);
        detailsLabelConstraints.insets = new Insets(5, 0, 0, 0);
        displayPanel.add(detailsLabel, detailsLabelConstraints);
        row++;

        displayPanel.add(new JLabel("Brand: "), ViewHelpers.getGridBagConstraints(0, row));

        bankBrandTextField = new JTextField(ENTRY_COLUMNS);
        displayPanel.add(bankBrandTextField, ViewHelpers.getGridBagConstraints(1, row, false));
        row++;

        displayPanel.add(new JLabel("Expiry: "), ViewHelpers.getGridBagConstraints(0, row));
        bankExpiryTextField = new JTextField(ENTRY_COLUMNS);
        displayPanel.add(bankExpiryTextField, ViewHelpers.getGridBagConstraints(1, row, false));
        row++;

        displayPanel.add(new JLabel("Card Number: "), ViewHelpers.getGridBagConstraints(0, row));
        bankCardNumberTextField = new JTextField(ENTRY_COLUMNS);
        displayPanel.add(bankCardNumberTextField, ViewHelpers.getGridBagConstraints(1, row, false));
        row++;

        displayPanel.add(new JLabel("Security Code: "), ViewHelpers.getGridBagConstraints(0, row));
        bankSecurityCodeTextField = new JTextField(ENTRY_COLUMNS);
        displayPanel.add(bankSecurityCodeTextField, ViewHelpers.getGridBagConstraints(1, row, false));
        row++;

        BankDetails bankDetails = customer.getBankDetails();

        if (bankDetails != null) {
            bankBrandTextField.setText(bankDetails.getCardBrand());
            bankExpiryTextField.setText(Integer.toString(bankDetails.getCardExpiry()));
            bankCardNumberTextField.setText(Integer.toString(bankDetails.getCardNum()));
            bankSecurityCodeTextField.setText(Integer.toString(bankDetails.getSecurityCode()));
        }

        JButton purchaseButton = new JButton("Purchase");
        purchaseButton.addActionListener(e -> {
            tryPurchase();
        });

        purchaseButton.setFont(boldFont);
        GridBagConstraints purchaseButtonConstraints = ViewHelpers.getGridBagConstraints(0, row);
        purchaseButtonConstraints.insets = new Insets(5, 0, 0, 0);
        displayPanel.add(purchaseButton, purchaseButtonConstraints);


        displayPanel.revalidate();
        displayPanel.repaint();
        displayPanel.setVisible(true);

        GridBagConstraints displayPanelConstraints = new GridBagConstraints();
        displayPanelConstraints.gridx = 0;
        displayPanelConstraints.gridy = 0;
        displayPanelConstraints.weightx = 1;
        displayPanelConstraints.weighty = 1;
        displayPanelConstraints.fill = GridBagConstraints.BOTH;

        

        add(displayPanel, displayPanelConstraints);
    }

    public void displayOrderContents() {
        if (customerView.getOrder().getOrderLines().size() == 0) {
            customerView.switchToProductsView();
        }

        contentsPanel.removeAll();

        int i = 0;

        for (OrderLine line : customerView.getOrder().getOrderLines()) {
            if (line == null)
                continue;

            GridBagConstraints constraints = new GridBagConstraints();
            constraints.fill = GridBagConstraints.HORIZONTAL;
            constraints.weightx = 1;
            constraints.gridy = i;
            constraints.anchor = GridBagConstraints.NORTH;
            
            contentsPanel.add(new OrderLinePanel(customerView, this, line), constraints);
            i++;
        }

        contentsPanel.revalidate();
        contentsPanel.repaint();
        contentsPanel.setVisible(true);

        GridBagConstraints scrollPaneConstraints = new GridBagConstraints();
        scrollPaneConstraints.anchor = GridBagConstraints.NORTHWEST;
        scrollPaneConstraints.gridx = 1;
        scrollPaneConstraints.gridy = 0;
        scrollPaneConstraints.weightx = 1;
        scrollPaneConstraints.weighty = 1;
        scrollPaneConstraints.fill = GridBagConstraints.BOTH;
        
        JScrollPane scrollPane = new JScrollPane(contentsPanel);
        scrollPane.setBorder(new EmptyBorder(new Insets(10, 10, 10, 10)));
        add(scrollPane, scrollPaneConstraints);
    }

    public void removeFromOrder(OrderLine orderLine) {
        Order order = customerView.getOrder();

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
        
        removeAll();
        displayOrderDetails();
        displayOrderContents();
        revalidate();
        repaint();
        setVisible(true);
    }

    private void tryPurchase() {
        if (bankBrandTextField.getText().isBlank()) {
            JOptionPane.showMessageDialog(this, "You must set a bank brand!");
            return;
        }

        if (bankCardNumberTextField.getText().isBlank()) {
            JOptionPane.showMessageDialog(this, "You must set a bank card number!");
            return;
        }

        if (bankExpiryTextField.getText().isBlank()) {
            JOptionPane.showMessageDialog(this, "You must set a bank expiry date!");
            return;
        }

        if (bankSecurityCodeTextField.getText().isBlank()) {
            JOptionPane.showMessageDialog(this, "You must set a bank security code!");
            return;
        }
    }
}