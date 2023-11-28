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
import models.database.DatabaseConnection;
import models.database.DatabaseMethods;
import views.Manager.CustomerTableModel;
import views.Manager.StaffTableModel;

// Parent panel for the dashboard for customers, staff and managers
class ExpandableCategoryPanel extends JPanel {
    private Account account;
    private JPanel productContainerPanel;

    public ExpandableCategoryPanel(Account account, String category) {       
        this.account = account;
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
                        productContainerPanel.add(new ProductPanel(controllers.get(i)));
                
                    break;

                case ProductCategories.LOCOMOTIVES:
                    ArrayList<Locomotive> locomotives = DatabaseMethods.getLocomotives(
                        databaseConnection.getConnection());
                        
                    for (int i = 0; i < locomotives.size(); ++i)
                        productContainerPanel.add(new ProductPanel(locomotives.get(i)));

                    break;

                case ProductCategories.ROLLING_STOCK:
                    ArrayList<RollingStock> rollingStock = DatabaseMethods.getRollingStocks(
                        databaseConnection.getConnection());
                        
                    for (int i = 0; i < rollingStock.size(); ++i)
                        productContainerPanel.add(new ProductPanel(rollingStock.get(i)));

                    break;

                case ProductCategories.TRACK:
                    ArrayList<Track> track = DatabaseMethods.getTracks(
                        databaseConnection.getConnection());
                        
                    for (int i = 0; i < track.size(); ++i)
                        productContainerPanel.add(new ProductPanel(track.get(i)));
                    
                    break;
                
                // TODO:
                case ProductCategories.TRACK_PACKS:
                    //DatabaseMethods.getTrackPackParts(connection);
                    break;

                case ProductCategories.TRAIN_PACKS:
                    break;

                case ProductCategories.TRAIN_SETS:
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
}