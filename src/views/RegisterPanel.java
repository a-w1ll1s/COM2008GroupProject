package views;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.SQLException;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import helpers.ViewHelpers;
import models.business.Account;
import models.database.DatabaseConnection;
import models.database.DatabaseMethods;

class RegisterPanel extends JPanel {
    private MainFrame parentFrame;
    final JTextField emailTextField, passwordTextField, forenameTextField, surnameTextField, houseNumberTextField,
                     postcodeTextField, roadNameTextField, cityNameTextField;
    private JLabel emailErrorLabel, passwordErrorLabel, forenameErrorLabel, surnameErrorLabel, houseNumberErrorLabel,
                   postcodeErrorLabel, roadNameErrorLabel, cityNameErrorLabel;
    final int TEXT_FIELD_COLUMNS = 30;

    // TODO: Set font size based off of window size
    
    public RegisterPanel(MainFrame frame) {        
        parentFrame = frame;
        setLayout(new GridBagLayout());
        
        Font titleFont = new Font("", Font.BOLD, 24);
        
        // Register Panel 
        JPanel registerPanel = new JPanel();
        registerPanel.setLayout(new GridBagLayout());
        registerPanel.setBackground(CustomStyleConstants.SECTION_COLOUR);
        registerPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        
        GridBagConstraints registerPanelConstraints = ViewHelpers.getGridBagConstraints(0, 0);
    
        
        // Title Panel
        JPanel titlePanel = new JPanel(new FlowLayout());
        titlePanel.setOpaque(false);

        JLabel welcomeLabel = new JLabel("Register");
        welcomeLabel.setFont(titleFont);
        welcomeLabel.setAlignmentX(LEFT_ALIGNMENT);

        titlePanel.add(welcomeLabel);
        registerPanel.add(titlePanel);

        // Entry Panel
        JPanel entryPanel = new JPanel(new GridBagLayout());
        entryPanel.setOpaque(false);

        GridBagConstraints entryPanelConstraints = new GridBagConstraints();
        entryPanelConstraints.gridy = 1;
        entryPanelConstraints.insets = new Insets(10, 10, 10, 10);
        entryPanelConstraints.fill = GridBagConstraints.HORIZONTAL;

        // Email entry
        JLabel emailLabel = new JLabel("Email");
        GridBagConstraints emailLabelConstraints = ViewHelpers.getGridBagConstraints(0, 0);
        
        entryPanel.add(emailLabel, emailLabelConstraints);

        emailErrorLabel = new JLabel("", SwingConstants.RIGHT);
        emailErrorLabel.setForeground(Color.RED);
        GridBagConstraints emailErrorLabelConstraints = ViewHelpers.getGridBagConstraints(1, 0);
        
        entryPanel.add(emailErrorLabel, emailErrorLabelConstraints);
    
        emailTextField = new JTextField(TEXT_FIELD_COLUMNS);
        GridBagConstraints emailTextFieldConstraints = ViewHelpers.getGridBagConstraints(0, 1);
        emailTextFieldConstraints.insets = new Insets(0, 0, 5, 0);
        emailTextFieldConstraints.gridwidth = 2;
        entryPanel.add(emailTextField, emailTextFieldConstraints);

        // Password entry
        JLabel passwordLabel = new JLabel("Password");
        GridBagConstraints passwordLabelConstraints = ViewHelpers.getGridBagConstraints(0, 2);
        entryPanel.add(passwordLabel, passwordLabelConstraints);

        passwordErrorLabel = new JLabel("", SwingConstants.RIGHT);
        GridBagConstraints passwordErrorLabelConstraints = ViewHelpers.getGridBagConstraints(1, 2);
        passwordErrorLabel.setForeground(Color.RED);

        entryPanel.add(passwordErrorLabel, passwordErrorLabelConstraints);

        passwordTextField = new JPasswordField();
        GridBagConstraints passwordTextFieldConstraints = ViewHelpers.getGridBagConstraints(0, 3);
        passwordTextFieldConstraints.insets = new Insets(0, 0, 5, 0);
        passwordTextFieldConstraints.gridwidth = 2;
        entryPanel.add(passwordTextField, passwordTextFieldConstraints);


        // Side by side entries
        Insets leftSideTextFieldInsets = new Insets(0, 0, 0, 10);
        Insets labelInsets = new Insets(5, 0, 0, 0);

        // Forename entry
        JLabel forenameLabel = new JLabel("Forename");
        GridBagConstraints forenameLabelConstraints = ViewHelpers.getGridBagConstraints(0, 4);
        forenameLabelConstraints.insets = labelInsets;
        entryPanel.add(forenameLabel, forenameLabelConstraints);

        forenameTextField = new JTextField(TEXT_FIELD_COLUMNS / 2);
        GridBagConstraints forenameTextFieldConstraints = ViewHelpers.getGridBagConstraints(0, 5);
        forenameTextFieldConstraints.insets = leftSideTextFieldInsets;
        entryPanel.add(forenameTextField, forenameTextFieldConstraints);

        forenameErrorLabel = new JLabel("");
        forenameErrorLabel.setForeground(Color.RED);

        entryPanel.add(forenameErrorLabel, ViewHelpers.getGridBagConstraints(0, 6));
    
        // Surname entry
        JLabel surnameLabel = new JLabel("Surname");
        GridBagConstraints surnameLabelConstraints = ViewHelpers.getGridBagConstraints(1, 4);
        surnameLabelConstraints.insets = labelInsets;
        entryPanel.add(surnameLabel, surnameLabelConstraints);

        surnameTextField = new JTextField(TEXT_FIELD_COLUMNS / 2);
        GridBagConstraints surnameTextFieldConstraints = ViewHelpers.getGridBagConstraints(1, 5);
        entryPanel.add(surnameTextField, surnameTextFieldConstraints);

        surnameErrorLabel = new JLabel("");
        surnameErrorLabel.setForeground(Color.RED);

        entryPanel.add(surnameErrorLabel, ViewHelpers.getGridBagConstraints(1, 6));

        // House number/name entry
        JLabel houseNumberLabel = new JLabel("House Number/Name");
        GridBagConstraints houseNumberLabelConstraints = ViewHelpers.getGridBagConstraints(0, 8);
        houseNumberLabelConstraints.insets = labelInsets;
        
        entryPanel.add(houseNumberLabel, houseNumberLabelConstraints);
    
        houseNumberTextField = new JTextField(10);
        houseNumberTextField.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) { 
                if (houseNumberTextField.getText().length() >= 10 )
                    e.consume(); 
            }
        });

        GridBagConstraints houseNumberTextFieldConstraints = ViewHelpers.getGridBagConstraints(0, 9);
        houseNumberTextFieldConstraints.insets = leftSideTextFieldInsets;
        entryPanel.add(houseNumberTextField, houseNumberTextFieldConstraints);
        

        // Postcode entry
        JLabel postcodeLabel = new JLabel("Postcode");
        GridBagConstraints postcodeLabelConstraints = ViewHelpers.getGridBagConstraints(1, 8);
        postcodeLabelConstraints.insets = labelInsets;
        entryPanel.add(postcodeLabel, postcodeLabelConstraints);
    
        postcodeTextField = new JTextField(10);
        postcodeTextField.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) { 
                if (postcodeTextField.getText().length() >= 10 )
                    e.consume(); 
            }
        });
        GridBagConstraints postcodeTextFieldConstraints = ViewHelpers.getGridBagConstraints(1, 9);
        entryPanel.add(postcodeTextField, postcodeTextFieldConstraints);

        // Housenumber/postcode error labels
        houseNumberErrorLabel = new JLabel("");
        houseNumberErrorLabel.setForeground(Color.RED);
        GridBagConstraints houseNumberErrorLabelConstraints = ViewHelpers.getGridBagConstraints(0, 10);
        
        entryPanel.add(houseNumberErrorLabel, houseNumberErrorLabelConstraints);

        postcodeErrorLabel = new JLabel("");
        postcodeErrorLabel.setForeground(Color.RED);
        GridBagConstraints postcodeErrorLabelConstraints = ViewHelpers.getGridBagConstraints(1, 10);
        
        entryPanel.add(postcodeErrorLabel, postcodeErrorLabelConstraints);

        // City name entry
        JLabel cityNameLabel = new JLabel("City Name");
        GridBagConstraints cityNameLabelConstraints = ViewHelpers.getGridBagConstraints(0, 11);
        cityNameLabelConstraints.insets = labelInsets;
        entryPanel.add(cityNameLabel, cityNameLabelConstraints);
    
        cityNameTextField = new JTextField(12);
        GridBagConstraints cityNameTextFieldConstraints = ViewHelpers.getGridBagConstraints(0, 12);
        cityNameTextFieldConstraints.insets = leftSideTextFieldInsets;
        entryPanel.add(cityNameTextField, cityNameTextFieldConstraints);

        // Road name entry
        JLabel roadNameLabel = new JLabel("Road Name");
        GridBagConstraints roadNameLabelConstraints = ViewHelpers.getGridBagConstraints(1, 11);
        roadNameLabelConstraints.insets = labelInsets;
        entryPanel.add(roadNameLabel, roadNameLabelConstraints);
    
        roadNameTextField = new JTextField(12);
        GridBagConstraints roadNameTextFieldConstraints = ViewHelpers.getGridBagConstraints(1, 12);
        entryPanel.add(roadNameTextField, roadNameTextFieldConstraints);

        // City name/Road name error labels
        cityNameErrorLabel = new JLabel("");
        cityNameErrorLabel.setForeground(Color.RED);
        GridBagConstraints cityNameErrorLabelConstraints = ViewHelpers.getGridBagConstraints(0, 13);
        
        entryPanel.add(cityNameErrorLabel, cityNameErrorLabelConstraints);

        roadNameErrorLabel = new JLabel("");
        roadNameErrorLabel.setForeground(Color.RED);
        GridBagConstraints roadNameErrorLabelConstraints = ViewHelpers.getGridBagConstraints(1, 13);
        
        entryPanel.add(roadNameErrorLabel, roadNameErrorLabelConstraints);

        // Submit Panel
        JPanel submitPanel = new JPanel(new BorderLayout());
        submitPanel.setOpaque(false);
        GridBagConstraints submitPanelConstraints = ViewHelpers.getGridBagConstraints(0, 2, false);

        JButton registerButton = new JButton("Register");
        registerButton.addActionListener(e -> TryRegister(e));
        submitPanel.add(registerButton, BorderLayout.NORTH);

        registerPanel.add(titlePanel);
        registerPanel.add(entryPanel, entryPanelConstraints);
        registerPanel.add(submitPanel, submitPanelConstraints);

        // Login Panel
        JPanel loginPanel = new JPanel();
        loginPanel.setOpaque(false);
        loginPanel.setLayout(new GridBagLayout());
        GridBagConstraints loginPanelConstraints = ViewHelpers.getGridBagConstraints(0, 1);
        loginPanelConstraints.insets = new Insets(10, 0, 0, 10);

        JLabel loginLabel = new JLabel("Already have an account?");
        GridBagConstraints loginLabelConstraints = new GridBagConstraints();
        loginLabelConstraints.insets = new Insets(0, 0, 0, 10);

        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(e -> parentFrame.showPage(new LoginPanel(parentFrame)));
        
        loginPanel.add(loginLabel, loginLabelConstraints);
        loginPanel.add(loginButton);

        // Add the panels to the parent panel
        add(registerPanel, registerPanelConstraints);
        add(loginPanel, loginPanelConstraints);
    }
    private void TryRegister(ActionEvent e) {
        // Clear previous errors
        emailErrorLabel.setText("");
        passwordErrorLabel.setText("");
        forenameErrorLabel.setText("");
        surnameErrorLabel.setText("");
        houseNumberErrorLabel.setText("");
        postcodeErrorLabel.setText("");
        roadNameErrorLabel.setText("");
        cityNameErrorLabel.setText("");
        
        // Get the given values
        String email = emailTextField.getText();
        String password = passwordTextField.getText();
        String forename = forenameTextField.getText();
        String surname = surnameTextField.getText();
        String houseNumber = houseNumberTextField.getText();
        String postcode = postcodeTextField.getText();
        String cityName = cityNameTextField.getText();
        String roadName = roadNameTextField.getText();
        
        // Verify all data has been provided
        Boolean missingData = ViewHelpers.setErrorIfEmpty(email, emailErrorLabel);
        missingData |= ViewHelpers.setErrorIfEmpty(password, passwordErrorLabel);
        missingData |= ViewHelpers.setErrorIfEmpty(forename, forenameErrorLabel);
        missingData |= ViewHelpers.setErrorIfEmpty(surname, surnameErrorLabel);
        missingData |= ViewHelpers.setErrorIfEmpty(houseNumber, houseNumberErrorLabel);
        missingData |= ViewHelpers.setErrorIfEmpty(postcode, postcodeErrorLabel);
        missingData |= ViewHelpers.setErrorIfEmpty(roadName, roadNameErrorLabel);
        missingData |= ViewHelpers.setErrorIfEmpty(cityName, cityNameErrorLabel);

        if (missingData)
            return;
        
        // Check email format
        if (!(email.contains("@") && email.contains((".")))) {
            emailErrorLabel.setText("Invalid email");
    }
        
        DatabaseConnection databaseConnection = new DatabaseConnection();
        Account account = null;
        
        try {
            databaseConnection.openConnection();
            DatabaseMethods.registerUser(databaseConnection.getConnection(), 
                email,
                password,
                forename,
                surname,
                houseNumber,
                roadName,
                cityName,
                postcode);

            account = DatabaseMethods.getAccountDetails(databaseConnection.getConnection(), email);
        } 
        catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error registering user: " + ex.getMessage());
            return;
        } 
        finally {
            databaseConnection.closeConnection();
        }

        if (account == null)
            return;

        // Successful register so automatically login.

        parentFrame.showPage(new DashboardPanel(parentFrame, account));
        
    }
}