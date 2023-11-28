package views;

import java.awt.*;

import javax.swing.*;

import models.business.Account;

class NavigationBarPanel extends JPanel {
    private MainFrame parentFrame;
    private DashboardPanel parentPanel;
    private Account account;
    
    public NavigationBarPanel(MainFrame frame, DashboardPanel panel, Account account) {        
        parentFrame = frame;
        parentPanel = panel;
        this.account = account;
        setLayout(new GridLayout());        

        setBackground(CustomStyleConstants.SECTION_COLOUR);

        JButton browserProductsButton = new JButton("Browser Products");
        browserProductsButton.setBackground(CustomStyleConstants.TOGGLED_NAV_BUTTON_COLOUR);
        browserProductsButton.addActionListener(e -> {
            parentPanel.switchToProductsView();
        });
        
        JButton orderHistorySettingsButton = new JButton("Order History");
        JButton accountSettingsButton = new JButton("Account Settings");
        



        

        add(browserProductsButton);
        add(orderHistorySettingsButton);    
        add(accountSettingsButton);
    }
}