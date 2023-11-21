package gui;

import java.awt.*;
import javax.swing.*;

public class MainFrame extends JFrame {
    // Needed for serialisation
    private static final long serialVersionUID = 1L;

    public MainFrame(String title) throws HeadlessException {
        super(title);
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        setSize(screenSize.width/2, screenSize.height/2);
        setLocation(screenSize.width/4, screenSize.height/4);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);

        Container contentPane = getContentPane();
        contentPane.setLayout(new GridLayout());
        JLabel label = new JLabel("Next friday :()");
		label.setFont(new Font("Tahoma", Font.PLAIN, 32));
        contentPane.add(label);
    }
}