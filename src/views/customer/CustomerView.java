package views.customer;

import models.business.Account;
import models.business.Customer;
import models.business.Order;
import views.MainFrame;

import javax.swing.*;
import java.awt.*;

public class CustomerView extends JPanel {
    private MainFrame parentFrame;
    private CustomerProductViewPanel productViewPanel;
    private ConfirmOrderPanel confirmOrderPanel;
    private Account account;

    public CustomerView(MainFrame parentFrame) {
        this.parentFrame = parentFrame;

        initializeComponents();
        layoutComponents();
    }

    private void initializeComponents() {
    }

    private void layoutComponents() {
        setLayout(new BorderLayout());

        productViewPanel = new CustomerProductViewPanel(this);
        add(productViewPanel);
    }

    public void switchToProductsView() {
        switchToPanelView(productViewPanel);
    }

    public void switchToConfirmOrderView(Order order) {
        ConfirmOrderPanel confirmOrderPanel = new ConfirmOrderPanel(parentFrame, account, order);
        switchToPanelView(confirmOrderPanel);
    }

    public MainFrame getFrame() {
        return parentFrame;
    }

    private void switchToPanelView(JPanel panel) { 
        removeAll();
        add(panel);

        revalidate();
        repaint();
        setVisible(true);
    }
}
