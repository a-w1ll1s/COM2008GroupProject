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
    private Customer customer;

    public CustomerView(MainFrame parentFrame, Customer customer) {
        this.parentFrame = parentFrame;
        this.customer = customer;

        initializeComponents();
        layoutComponents();
    }

    private void initializeComponents() {
    }

    private void layoutComponents() {
        setLayout(new BorderLayout());

        productViewPanel = new CustomerProductViewPanel(this, customer);
        add(productViewPanel);
    }

    public void switchToProductsView() {
        switchToPanelView(productViewPanel);
    }

    public void switchToConfirmOrderView(Order order) {
        ConfirmOrderPanel confirmOrderPanel = new ConfirmOrderPanel(parentFrame, customer, order);
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
