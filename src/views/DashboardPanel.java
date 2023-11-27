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
        add(navigationBarPanel, BorderLayout.PAGE_START);

        containerPanel = new JPanel();
        add(containerPanel);
    }

    public void switchToProductsView() {
        containerPanel.removeAll();
        containerPanel.add(new ProductViewPanel(parentFrame));

        redraw();
    }

    private void redraw() {        
        containerPanel.revalidate();
        containerPanel.repaint();
        containerPanel.setVisible(true);        
    }
}