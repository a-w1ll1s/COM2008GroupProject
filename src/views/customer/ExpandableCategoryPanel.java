package views.customer;

import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import models.business.Account;
import models.business.Controller;
import models.business.Locomotive;
import models.business.Product;
import models.business.RollingStock;
import models.business.Track;
import models.business.TrackPack;
import models.database.DatabaseConnection;
import models.database.DatabaseMethods;
import views.CustomStyleConstants;
import views.Manager.CustomerTableModel;
import views.Manager.StaffTableModel;

// Parent panel for the dashboard for customers, staff and managers
class ExpandableCategoryPanel extends JPanel {
    private ProductViewPanel parentPanel;
    private Account account;
    private JPanel productContainerPanel;
    private String category;
    private int selectedProductID = -1;
    
    public ExpandableCategoryPanel(ProductViewPanel parentPanel, Account account, String category) {       
        this.parentPanel = parentPanel;
        this.account = account;
        this.category = category;

        setLayout(new GridBagLayout());

        productContainerPanel = new JPanel();
        productContainerPanel.setLayout(new GridLayout(0, 1));

        // Try get all the products of the given type from the database
        DatabaseConnection databaseConnection = new DatabaseConnection();
        try {
            databaseConnection.openConnection();

            switch (category) {
                case ProductCategories.CONTROLLERS:
                    ArrayList<Controller> controllers = DatabaseMethods.getControllers(
                        databaseConnection.getConnection());

                    for (int i = 0; i < controllers.size(); ++i)
                        productContainerPanel.add(new ProductPanel(this, controllers.get(i)));
                
                    break;

                case ProductCategories.LOCOMOTIVES:
                    ArrayList<Locomotive> locomotives = DatabaseMethods.getLocomotives(
                        databaseConnection.getConnection());
                        
                    for (int i = 0; i < locomotives.size(); ++i)
                        productContainerPanel.add(new ProductPanel(this, locomotives.get(i)));

                    break;

                case ProductCategories.ROLLING_STOCK:
                    ArrayList<RollingStock> rollingStock = DatabaseMethods.getRollingStocks(
                        databaseConnection.getConnection());
                        
                    for (int i = 0; i < rollingStock.size(); ++i)
                        productContainerPanel.add(new ProductPanel(this, rollingStock.get(i)));

                    break;

                case ProductCategories.TRACK:
                    ArrayList<Track> track = DatabaseMethods.getTracks(
                        databaseConnection.getConnection());
                        
                    for (int i = 0; i < track.size(); ++i)
                        productContainerPanel.add(new ProductPanel(this, track.get(i)));
                    
                    break;
                
                case ProductCategories.TRACK_PACKS:
                    ArrayList<ArrayList<Product>> trackPacks = DatabaseMethods.getTrackPacks(
                        databaseConnection.getConnection());

                    for (int i = 0; i < trackPacks.size(); i++) {
                        ArrayList<Product> pack = trackPacks.get(i);
                        productContainerPanel.add(new ProductPanel(this, pack));
                    }
                    
                    break;

                case ProductCategories.TRAIN_SETS:
                    ArrayList<ArrayList<Product>> trainSets = DatabaseMethods.getTrainSets(
                        databaseConnection.getConnection());

                    for (int i = 0; i < trainSets.size(); i++) {
                        ArrayList<Product> trainSet = trainSets.get(i);
                        productContainerPanel.add(new ProductPanel(this, trainSet));
                    }
                    break;                    
            }
        } 
        catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, String.format("Error getting products from %s: ", category) 
                + ex.getMessage());

            return;
        } 
        finally {
            databaseConnection.closeConnection();
        }

        GridBagConstraints productContainerConstraints = new GridBagConstraints();
        productContainerConstraints.fill = GridBagConstraints.BOTH;
        productContainerConstraints.weightx = 1;
        productContainerConstraints.weighty = 1;
        
        JScrollPane scrollPane = new JScrollPane(productContainerPanel);
        add(scrollPane, productContainerConstraints);
    }

    public String getCategory() {
        return category;
    }

    public int getSelectedProductID() {
        return selectedProductID;
    }

    public void showSelectedProduct(int productID) {
        if (productID == selectedProductID)
            return;

        Color defaultColour = new JPanel().getBackground();
        for (Component comp : productContainerPanel.getComponents()) {
            if (comp instanceof ProductPanel) {
                ProductPanel panel = (ProductPanel)comp;
                if (panel.getProductID() == productID) {
                    panel.setBackground(CustomStyleConstants.SELECTED_PRODUCT_COLOUR);
                    selectedProductID = productID;

                    // Get the parent to process anything that needs to be shown when we select a product
                    parentPanel.onSelectedProductChanged();
                }
                else {
                    panel.setBackground(defaultColour);
                }
            }
        }
    }
}