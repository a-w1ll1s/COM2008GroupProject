package views.customer;

import java.awt.*;
import java.sql.SQLException;

import javax.swing.*;

import helpers.FormHelpers;
import models.database.DatabaseConnection;
import views.MainFrame;

public class ProductViewPanel extends JPanel {
    private MainFrame parentFrame;
    
    public ProductViewPanel(MainFrame frame) {        
        parentFrame = frame;
        setLayout(new GridBagLayout());

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




        SelectCategoryButton trainSetCategoryButton = new ExpandableCategoryPanel(null);


        ExpandableCategoryPanel trainSetCategoryPanel = new ExpandableCategoryPanel(parentFrame, "Train Sets");
        add(trainSetCategoryPanel);
        
        

        ExpandableCategoryPanel trackPackCategoryPanel = new ExpandableCategoryPanel(parentFrame, "Track Packs");
        add(trackPackCategoryPanel, FormHelpers.getGridBagConstraints(0, 1));

        ExpandableCategoryPanel trackCategoryPanel = new ExpandableCategoryPanel(parentFrame, "Track");
        add(trackCategoryPanel, FormHelpers.getGridBagConstraints(0, 2));
    }

    private void selectCategory(String category) {

    }
}