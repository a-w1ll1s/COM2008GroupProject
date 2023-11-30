package views.customer;

import java.awt.*;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import models.business.Order;
import views.MainFrame;

public class CustomerProductViewPanel extends JPanel {
    private CustomerView customerView;
    private ProductViewPanel productViewPanel;
    private Order order;
    
    public CustomerProductViewPanel(CustomerView customerView) {        
        this.customerView = customerView;
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

        GridBagConstraints optionButtonConstraints = new GridBagConstraints();
        optionButtonConstraints.insets = new Insets(5, 5, 5, 5);

        JButton confirmOrderButton = new JButton("Confirm order");
        confirmOrderButton.addActionListener(e -> {
            customerView.switchToConfirmOrderView(order);
        });

        optionsPanel.add(confirmOrderButton, optionButtonConstraints);

        JButton addToOrderButton = new JButton("Add to order");
        addToOrderButton.addActionListener(e -> {
            if (productViewPanel.getSelectedProductID() == -1) {
                JOptionPane.showMessageDialog(customerView.getFrame(), "Please select a product!");
                return;
            }
            System.out.println(productViewPanel.getSelectedProductID());            
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
}