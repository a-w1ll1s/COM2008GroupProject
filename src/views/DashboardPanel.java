package views;

import java.awt.*;

import javax.swing.*;

import models.business.Account;
import views.Manager.ManagerView;
import views.Staff.StaffView;
import views.customer.ProductViewPanel;
import views.customer.AccountSettingsPanel;
import views.customer.OrderHistoryPanel;

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

    private void switchToPanelView(JPanel panel) { 
        containerPanel.removeAll();
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.weightx = 1.0; // Cell takes up all extra horizontal space
        constraints.weighty = 1.0; // Cell takes up all extra vertical space
        constraints.fill = GridBagConstraints.BOTH;
        containerPanel.add(panel, constraints);

        redraw();
    }

    public void switchToProductsView() {
        switchToPanelView(new ProductViewPanel(parentFrame, false));
    }

    public void switchToOrderHistoryView() {
        switchToPanelView(new OrderHistoryPanel(parentFrame, account));
    }

    public void switchToAccountSettingsView() {
        switchToPanelView(new AccountSettingsPanel(parentFrame, account));
    }


    public void switchToStaffView() {
        switchToPanelView(new StaffView(parentFrame));
    }

    public void switchToManagerView() {
        switchToPanelView(new ManagerView(parentFrame));
    }

    private void redraw() {        
        containerPanel.revalidate();
        containerPanel.repaint();
        containerPanel.setVisible(true);        
    }
}