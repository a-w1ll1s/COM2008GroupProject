package views.customer;

import java.awt.*;

import javax.swing.*;

import models.business.Account;
import models.business.Customer;
import models.business.Order;
import views.MainFrame;

public class ConfirmOrderPanel extends JPanel {
    private MainFrame parentFrame;
    private Customer customer;
    private Order order;
    private CustomerView customerView;

    public ConfirmOrderPanel(CustomerView customerView, MainFrame frame, Customer customer, Order order) {     
        this.customerView = customerView;   
        parentFrame = frame;
        this.customer = customer;
        this.order = order;
        setLayout(new GridBagLayout());

        layoutComponents();
    }

    private void layoutComponents() {
        OrderPanel orderPanel = new OrderPanel(customerView, customer);

        GridBagConstraints orderPanelConstraints = new GridBagConstraints();
        orderPanelConstraints.fill = GridBagConstraints.BOTH;
        orderPanelConstraints.weightx = 1;
        orderPanelConstraints.weighty = 1;
        add(orderPanel, orderPanelConstraints);
    }
}