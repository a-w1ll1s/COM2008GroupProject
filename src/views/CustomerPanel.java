package views;

import java.awt.*;

import javax.swing.*;

class CustomerPanel extends JPanel {
    private MainFrame parentFrame;
    
    public CustomerPanel(MainFrame frame) {        
        parentFrame = frame;
        setLayout(new GridBagLayout());
    }
}