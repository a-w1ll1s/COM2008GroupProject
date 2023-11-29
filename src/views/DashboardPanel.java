package views;

import java.awt.*;

import javax.swing.*;

import models.business.Account;

import views.customer.ProductViewPanel;

// Parent panel for the dashboard for customers, staff and managers
class DashboardPanel extends JPanel {
    private MainFrame parentFrame;
    private Account account;
    private JPanel containerPanel;

    public DashboardPanel(MainFrame frame, Account account) {        
        parentFrame = frame;
        this.account = account;
        setLayout(new BorderLayout());

        // Navigation bar
        NavigationBarPanel navigationBarPanel = new NavigationBarPanel(parentFrame, this, account);
        GridBagConstraints navigationBarConstraints = new GridBagConstraints();
        navigationBarConstraints.fill = GridBagConstraints.HORIZONTAL;
        add(navigationBarPanel, BorderLayout.NORTH);

        // Main part of the panel
        containerPanel = new JPanel();
        containerPanel.setLayout(new GridBagLayout());
        containerPanel.setBackground(Color.RED);

        GridBagConstraints containerPanelConstraints = new GridBagConstraints();
        containerPanelConstraints.fill = GridBagConstraints.BOTH;
        containerPanelConstraints.gridy = 1;
        add(containerPanel);
        
        // TEMP: Testing
        switchToProductsView();
        
    }

    public void switchToProductsView() {
        containerPanel.removeAll();
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.weightx = 1.0; // Cell takes up all extra horizontal space
        constraints.weighty = 1.0; // Cell takes up all extra vertical space
        constraints.fill = GridBagConstraints.BOTH;
        containerPanel.add(new ProductViewPanel(parentFrame), constraints);

        redraw();
    }

    public void switchToStaffView() {
        // TEMP: For now just go to customer view, need to show the staff options on the items
        switchToProductsView();
    }

    private void redraw() {        
        containerPanel.revalidate();
        containerPanel.repaint();
        containerPanel.setVisible(true);        
    }
}