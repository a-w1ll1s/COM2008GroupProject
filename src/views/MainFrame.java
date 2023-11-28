package views;

import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.*;

import models.business.Account;
import models.business.Customer;
import models.business.Staff;
import models.database.DatabaseConnection;
import models.database.DatabaseMethods;

public class MainFrame extends JFrame {
    // Needed for serialisation
    private static final long serialVersionUID = 1L;
    private Container contentPane;

    public MainFrame(String title) throws HeadlessException {
        super(title);

        // Default frame setup 
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        setSize(screenSize.width / 2, screenSize.height / 2);
        setLocation(screenSize.width / 4, screenSize.height / 4);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        
        contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());
        //showPage(new LoginPanel(this));


        // TEMP: Testing dashboard
        DatabaseConnection databaseConnection = new DatabaseConnection();
        Account account = null;
        try {
            databaseConnection.openConnection();
            account = DatabaseMethods.getAccountDetails(databaseConnection.getConnection(), "testcustomer@test.com");
        } 
        catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error getting account details: " + ex.getMessage());
            return;
        } 
        finally {
            databaseConnection.closeConnection();
        }

        showPage(new DashboardPanel(this, account));
    }

    public void showPage(JPanel panel) {
        // Show the given page panel
        contentPane.removeAll();
        contentPane.add(panel, BorderLayout.CENTER);

        // Update the frame display
        revalidate();
        repaint();
        setVisible(true);
    }
}