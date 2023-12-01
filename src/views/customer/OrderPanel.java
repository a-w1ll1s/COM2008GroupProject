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
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
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
import views.CustomStyleConstants;

class OrderPanel extends JPanel {
    private Order order;
    private Customer customer; 
    private Font boldFont = new Font("", Font.BOLD, 14);
    private JTextField bankBrandTextField, bankExpiryTextField, bankCardNumberTextField, bankSecurityCodeTextField;
    private final int ENTRY_COLUMNS = 15;
    private JPanel contentsPanel;
    private CustomerView customerView;

    public OrderPanel(CustomerView customerView, Order order, Customer customer) {
        this.customerView = customerView;
        this.order = order;
        this.customer = customer;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Color.GREEN);

        int row = 0;

        // Account info
        AccountHolder holder = customer.getHolder();
        HolderAddress address = holder.getAddress();

        JPanel displayPanel = new JPanel();
        displayPanel.setLayout(new GridBagLayout());
        
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
        
        displayPanel.add(new JLabel("Date: " + Integer.toString(order.getDate())), ViewHelpers.getGridBagConstraints(0, row));
        row++;

        displayPanel.add(new JLabel("Total Price: " + Integer.toString(order.getOrderCost())),
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

        // Try get existing bank details
        updateBankDetails();

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

        JLabel contentsLabel = new JLabel("Contents");
        contentsLabel.setFont(boldFont);
        GridBagConstraints contentsLabelConstraints = ViewHelpers.getGridBagConstraints(0, row);
        contentsLabelConstraints.insets = new Insets(5, 0, 0, 0);
        displayPanel.add(contentsLabel, contentsLabelConstraints);

        contentsPanel = new JPanel();
        contentsPanel.setLayout(new GridBagLayout());
        
        row++;
        displayOrderContents();
        

        GridBagConstraints contentsPanelConstraints = ViewHelpers.getGridBagConstraints(0, row);
        contentsPanelConstraints.fill = GridBagConstraints.BOTH;
        contentsPanelConstraints.weightx = 1;
        contentsPanelConstraints.weighty = 1;
        contentsPanelConstraints.gridwidth = 2;

        add(displayPanel);
        add(new JScrollPane(contentsPanel));
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

    private void displayOrderContents() {
        contentsPanel.removeAll();

        int i = 0;

        for (OrderLine line : order.getOrderLines()) {
            if (line == null)
                continue;

            contentsPanel.add(new OrderLinePanel(customerView, this, line), ViewHelpers.getGridBagConstraints(0, i));
            i++;
        }

        contentsPanel.revalidate();
        contentsPanel.repaint();
        contentsPanel.setVisible(true);
    }

    public void removeFromOrder(OrderLine orderLine) {
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
        System.out.println(order.getOrderLines().size() - newLines.size());
        order.setOrderLines(newLines);
        customerView.setOrder(order);
        displayOrderContents();
    }
}