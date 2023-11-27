package views;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.SQLException;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import helpers.FormHelpers;
import models.business.Account;
import models.business.Customer;
import models.database.DatabaseConnection;
import models.database.DatabaseMethods;

class LoginPanel extends JPanel {
    private MainFrame parentFrame;
    private JButton loginButton;
    final JTextField emailTextField, passwordTextField;
    private JLabel emailErrorLabel, passwordErrorLabel;
    
    public LoginPanel(MainFrame frame) {        
        parentFrame = frame;
        setLayout(new GridBagLayout());
        
        Font titleFont = new Font("", Font.BOLD, 24);
        
        // Login Panel 
        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(new GridBagLayout());
        loginPanel.setBackground(Color.LIGHT_GRAY);
        loginPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        GridBagConstraints loginPanelConstraints = FormHelpers.getGridBagConstraints(0, 0);
        
        // Title Panel
        JPanel titlePanel = new JPanel(new FlowLayout());
        titlePanel.setOpaque(false);

        JLabel welcomeLabel = new JLabel("Login");
        welcomeLabel.setFont(titleFont);
        welcomeLabel.setAlignmentX(LEFT_ALIGNMENT);

        titlePanel.add(welcomeLabel);

        loginPanel.add(titlePanel);

        // Entry Panel
        JPanel entryPanel = new JPanel(new GridBagLayout());
        entryPanel.setOpaque(false);

        GridBagConstraints entryPanelConstraints = FormHelpers.getGridBagConstraints(0, 1);
        entryPanelConstraints.insets = new Insets(10, 10, 10, 10);
        
        // Email entry
        JLabel emailLabel = new JLabel("Email");
        GridBagConstraints emailLabelConstraints = FormHelpers.getGridBagConstraints(0, 0);
        
        entryPanel.add(emailLabel, emailLabelConstraints);

        emailErrorLabel = new JLabel("", SwingConstants.RIGHT);
        GridBagConstraints emailErrorLabelConstraints = FormHelpers.getGridBagConstraints(1, 0);
        emailErrorLabel.setForeground(Color.RED);

        entryPanel.add(emailErrorLabel, emailErrorLabelConstraints);

        emailTextField = new JTextField(30);
        GridBagConstraints emailTextFieldConstraints = FormHelpers.getGridBagConstraints(0, 1);
        emailTextFieldConstraints.insets = new Insets(0, 0, 5, 0);
        emailTextFieldConstraints.gridwidth = 2;
        entryPanel.add(emailTextField, emailTextFieldConstraints);

        // Password entry
        JLabel passwordLabel = new JLabel("Password");
        GridBagConstraints passwordLabelConstraints = FormHelpers.getGridBagConstraints(0, 2);
        entryPanel.add(passwordLabel, passwordLabelConstraints);

        passwordErrorLabel = new JLabel("", SwingConstants.RIGHT);
        passwordErrorLabel.setForeground(Color.RED);

        entryPanel.add(passwordErrorLabel, FormHelpers.getGridBagConstraints(1, 2));

        passwordTextField = new JPasswordField();
        GridBagConstraints passwordTextFieldConstraints = FormHelpers.getGridBagConstraints(0, 3);
        passwordTextFieldConstraints.insets = new Insets(0, 0, 0, 0);
        passwordTextFieldConstraints.gridwidth = 2;
        entryPanel.add(passwordTextField, passwordTextFieldConstraints);
        
        // Submit Panel
        JPanel submitPanel = new JPanel(new BorderLayout());
        submitPanel.setOpaque(false);
        
        loginButton = new JButton("Login");
        loginButton.addActionListener(e -> tryLogin(e));
        
        submitPanel.add(loginButton, BorderLayout.NORTH);

        loginPanel.add(titlePanel);
        loginPanel.add(submitPanel, FormHelpers.getGridBagConstraints(0, 2, false));
        loginPanel.add(entryPanel, entryPanelConstraints);
        

        // Register Panel
        JPanel registerPanel = new JPanel();
        registerPanel.setOpaque(false);
        registerPanel.setLayout(new GridBagLayout());
        GridBagConstraints registerPanelConstraints = FormHelpers.getGridBagConstraints(0, 1);
        registerPanelConstraints.insets = new Insets(10, 0, 0, 10);

        JLabel registerLabel = new JLabel("Don't have an account?");
        GridBagConstraints registerLabelConstraints = new GridBagConstraints();
        registerLabelConstraints.insets = new Insets(0, 0, 0, 10);

        JButton registerButton = new JButton("Register");
        registerButton.addActionListener(e -> parentFrame.showPage(new RegisterPanel(parentFrame)));
        
        registerPanel.add(registerLabel, registerLabelConstraints);
        registerPanel.add(registerButton);
        
        // Add the panels to the parent panel
        add(loginPanel, loginPanelConstraints);
        add(registerPanel, registerPanelConstraints);
    }
    private void tryLogin(ActionEvent e) {
        // Clear previous errors
        emailErrorLabel.setText("");
        passwordErrorLabel.setText("");

        String email = emailTextField.getText();
        String password = passwordTextField.getText();

        // Verify data has been provided
        Boolean missingData = FormHelpers.setErrorIfEmpty(email, emailErrorLabel);
        missingData |= FormHelpers.setErrorIfEmpty(password, passwordErrorLabel);
        
        if (missingData)
            return;

        // Try retrieve a matching account from the database
        DatabaseConnection databaseConnection = new DatabaseConnection();
        Account account = null;
        try {
            databaseConnection.openConnection();
            account = DatabaseMethods.getAccountDetails(databaseConnection.getConnection(), email);
        } 
        catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error getting account details: " + ex.getMessage());
            return;
        } 
        finally {
            databaseConnection.closeConnection();
        }

        // Check if we found an account
        if (account == null) {
            emailErrorLabel.setText("Email not registered");
            return;
        }

        if (!account.getPassword().equals(password)) {
            passwordErrorLabel.setText("Wrong password");
            return;
        } 

        // Successful login so show customer dashboard
        parentFrame.showPage(new CustomerPanel(parentFrame));
    }
}