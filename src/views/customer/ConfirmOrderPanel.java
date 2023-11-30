package views.customer;

import java.awt.*;

import javax.swing.*;

import models.business.Account;
import models.business.Order;
import views.MainFrame;

public class ConfirmOrderPanel extends JPanel {
    private MainFrame parentFrame;
    private Account account;
    private Order order;

    public ConfirmOrderPanel(MainFrame frame, Account account, Order order) {        
        parentFrame = frame;
        this.account = account;
        this.order = order;
        setLayout(new GridBagLayout());

        layoutComponents();
    }

    private void layoutComponents() {
        OrderPanel orderPanel = new OrderPanel(order);
        add(orderPanel);


    }
}