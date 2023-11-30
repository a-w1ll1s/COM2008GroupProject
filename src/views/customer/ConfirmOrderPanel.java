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

    public ConfirmOrderPanel(MainFrame frame, Customer customer, Order order) {        
        parentFrame = frame;
        this.customer = customer;
        this.order = order;
        setLayout(new GridBagLayout());
        setBackground(Color.BLUE);

        layoutComponents();
    }

    private void layoutComponents() {
        OrderPanel orderPanel = new OrderPanel(order, customer);

        GridBagConstraints orderPanelConstraints = new GridBagConstraints();
        orderPanelConstraints.fill = GridBagConstraints.BOTH;
        orderPanelConstraints.weightx = 1;
        orderPanelConstraints.weighty = 1;
        add(orderPanel, orderPanelConstraints);
    }
}