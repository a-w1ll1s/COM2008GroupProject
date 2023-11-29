package views.customer;

import java.awt.*;

import javax.swing.*;
import views.MainFrame;

public class ConfirmOrderPanel extends JPanel {
    private MainFrame parentFrame;

    public ConfirmOrderPanel(MainFrame frame) {        
        parentFrame = frame;
        setLayout(new GridBagLayout());



    }
}