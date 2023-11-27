package views.customer;

import java.awt.*;
import java.sql.SQLException;

import javax.swing.*;

import models.business.Account;
import models.database.DatabaseConnection;
import models.database.DatabaseMethods;

// Parent panel for the dashboard for customers, staff and managers
class ExpandableCategoryPanel extends JPanel {
    private Account account;
    private JPanel containerPanel;

    public ExpandableCategoryPanel(Account account) {        
        this.account = account;
        setLayout(new BorderLayout());

        // Try get all the products of the given type from the database
        DatabaseConnection databaseConnection = new DatabaseConnection();
        try {
            databaseConnection.openConnection();
        } 
        catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error getting account details: " + ex.getMessage());
            return;
        } 
        finally {
            databaseConnection.closeConnection();
        }




    }
}