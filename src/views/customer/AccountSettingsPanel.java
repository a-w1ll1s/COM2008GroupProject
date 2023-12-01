package views.customer;

import models.business.*;
import models.database.DatabaseConnection;
import models.database.DatabaseMethods;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.SQLException;

import views.MainFrame;

public class AccountSettingsPanel extends JPanel {
    private MainFrame parentFrame;
    
    private Account account; 
    private JTextField emailField, forenameField, surnameField;
    private JTextField houseNumField, roadNameField, cityNameField, postcodeField;
    private JButton updateButton;

    public AccountSettingsPanel(MainFrame frame, Account account) {
        this.parentFrame = frame;
        this.account = account;
        setLayout(new GridBagLayout());
        createComponents();
    }

    

    
   


	private void createComponents() {
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(10, 10, 10, 10);
        c.anchor = GridBagConstraints.WEST;
    
        // Email field
        c.gridx = 0; c.gridy = 0;
        add(new JLabel("Email:"), c);
        emailField = new JTextField(account.getEmail(), 20);
        c.gridx = 1;
        add(emailField, c);
    
        // Name fields
        c.gridy++;
        c.gridx = 0;
        add(new JLabel("Forename:"), c);
        forenameField = new JTextField(account.getHolder().getForename(), 20);
        c.gridx = 1;
        add(forenameField, c);
    
        c.gridy++;
        c.gridx = 0;
        add(new JLabel("Surname:"), c);
        surnameField = new JTextField(account.getHolder().getSurname(), 20);
        c.gridx = 1;
        add(surnameField, c);
    
        // Address fields
        c.gridy++;
        c.gridx = 0;
        add(new JLabel("House Number:"), c);
        houseNumField = new JTextField(account.getHolder().getAddress().getHouseNum(), 20);
        c.gridx = 1;
        add(houseNumField, c);

        c.gridy++;
        c.gridx = 0;
        add(new JLabel("Road Name:"), c);
        roadNameField = new JTextField(account.getHolder().getAddress().getRoadName(), 20);
        c.gridx = 1;
        add(roadNameField, c);

        c.gridy++;
        c.gridx = 0;
        add(new JLabel("City Name:"), c);
        cityNameField = new JTextField(account.getHolder().getAddress().getCityName(), 20);
        c.gridx = 1;
        add(cityNameField, c);

        c.gridy++;
        c.gridx = 0;
        add(new JLabel("Postcode:"), c);
        postcodeField = new JTextField(account.getHolder().getAddress().getPostcode(), 20);
        c.gridx = 1;
        add(postcodeField, c);
        
    
        // Update button
        c.gridy++;
        c.gridx = 0;
        c.gridwidth = 2;
        updateButton = new JButton("Update Details");
        updateButton.addActionListener(e -> updateAccountDetails());
        add(updateButton, c);
    }
    

     

    private void updateAccountDetails() {
        // Update account details based on field input
        account.setEmail(emailField.getText());
        account.getHolder().setForename(forenameField.getText());
        account.getHolder().setSurname(surnameField.getText());
    
        // Update address details
        HolderAddress address = account.getHolder().getAddress();
        address.setHouseNum(houseNumField.getText());
        address.setRoadName(roadNameField.getText());
        address.setCityName(cityNameField.getText());
        address.setPostcode(postcodeField.getText());
    
        

        // Database connection
        // Database connection and methods
        DatabaseConnection databaseConnection = new DatabaseConnection();

        try {
            // Open database connection
            databaseConnection.openConnection();
            Connection connection = databaseConnection.getConnection();

            // Start transaction
            connection.setAutoCommit(false);

            DatabaseMethods databaseMethods = new DatabaseMethods();

            // Assuming 'account' is an instance of a class that has a 'getHolder()' method returning an AccountHolder object
            AccountHolder holder = account.getHolder();
            HolderAddress newAddress = new HolderAddress(
                houseNumField.getText(),
                roadNameField.getText(),
                cityNameField.getText(),
                postcodeField.getText()
            );
            
            // Get old address details for comparison
            String oldHouseNum = holder.getAddress().getHouseNum();
            String oldPostcode = holder.getAddress().getPostcode();
            
            // Call update methods
            databaseMethods.updateAccountHolderDetails(connection, holder, newAddress);
            databaseMethods.updateAddressDetails(connection, newAddress, oldHouseNum, oldPostcode);

            

            // Commit transaction
            connection.commit();

            // Notify user of successful update
            JOptionPane.showMessageDialog(this, "Account details updated successfully!");

        } catch (SQLException ex) {
            // Rollback transaction in case of error
            if (databaseConnection != null) {
                try {
                    databaseConnection.getConnection().rollback();
                } catch (SQLException rollbackEx) {
                    JOptionPane.showMessageDialog(this, "Error during rollback: " + rollbackEx.getMessage());
                }
            }

            // Notify user of error
            JOptionPane.showMessageDialog(this, "Error updating account details: " + ex.getMessage());
        } finally {
            // Reset default auto-commit behavior and close connection
            if (databaseConnection != null) {
                try {
                    databaseConnection.getConnection().setAutoCommit(true);
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(this, "Error resetting auto-commit: " + e.getMessage());
                }
                databaseConnection.closeConnection();
            }
        }
    }
}
