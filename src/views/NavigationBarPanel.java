package views;

import java.awt.*;
import java.util.ArrayList;

import javax.swing.*;

import models.business.Account;

class NavigationBarPanel extends JPanel {
    private MainFrame parentFrame;
    private DashboardPanel parentPanel;
    private Account account;

    private ArrayList<JButton> navigationButtons = new ArrayList<>();
    
    private void showSelectedButton(JButton button) {
        // Function to reset all navbar button colours and select the given one
        Color defaultColour = new JButton().getBackground();
        for (JButton b : navigationButtons) {
            if (b.getText().equals(button.getText())) {
                b.setBackground(CustomStyleConstants.TOGGLED_NAV_BUTTON_COLOUR);
            }
            else {
                b.setBackground(defaultColour);
            }
        }
    }

    public NavigationBarPanel(MainFrame frame, DashboardPanel panel, Account account) {        
        parentFrame = frame;
        parentPanel = panel;
        this.account = account;
        setLayout(new GridLayout());        

        setBackground(CustomStyleConstants.SECTION_COLOUR);

        JButton browserProductsButton = new JButton("Browse Products");
        browserProductsButton.setBackground(CustomStyleConstants.TOGGLED_NAV_BUTTON_COLOUR);
        browserProductsButton.addActionListener(e -> {
            parentPanel.switchToCustomerView();
            showSelectedButton(browserProductsButton);
        });

        navigationButtons.add(browserProductsButton);
        
        JButton orderHistorySettingsButton = new JButton("Order History");
        orderHistorySettingsButton.addActionListener(e -> {
            parentPanel.switchToOrderHistoryView();
            showSelectedButton(orderHistorySettingsButton);
        });

        navigationButtons.add(orderHistorySettingsButton);

        JButton accountSettingsButton = new JButton("Account Settings");
        accountSettingsButton.addActionListener(e -> {
            parentPanel.switchToAccountSettingsView();
            showSelectedButton(accountSettingsButton);
        });
        navigationButtons.add(accountSettingsButton);
        
        // Manager button
        if (account.isManager()){
            JButton managerButton = new JButton("Manager");
            managerButton.addActionListener(e -> {
                parentPanel.switchToManagerView();
                showSelectedButton(managerButton);
            });

            navigationButtons.add(managerButton);
            add(managerButton);
        }

        // Staff button
        if (account.isStaff()){
            JButton staffButton = new JButton("Staff");
            staffButton.addActionListener(e -> {
                parentPanel.switchToStaffView();
                showSelectedButton(staffButton);
            });

            navigationButtons.add(staffButton);
            add(staffButton);
        }
        
        add(browserProductsButton);
        add(orderHistorySettingsButton);    
        add(accountSettingsButton);
    }
}