package views.customer;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.*;

import models.business.Controller;
import models.business.Locomotive;
import models.business.Product;
import models.business.RollingStock;
import models.business.Track;
import views.CustomStyleConstants;

class ProductPanel extends JPanel {
    private int productID;
    private Boolean isInBasket;
    private int orderQuantity;
    private Font boldFont = new Font("", Font.BOLD, 14);
    private ExpandableCategoryPanel parentPanel;

    public int getProductID() {
        return productID;
    }

    private void setup(ExpandableCategoryPanel parentPanel, Product product) {
        this.parentPanel = parentPanel; 
        productID = product.getProductID();

        setLayout(new GridLayout(0, 2)); // TODO: Change to 3 and add buttons to the right?
        setBorder(BorderFactory.createLoweredSoftBevelBorder());

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                parentPanel.showSelectedProduct(productID);
            }
        });

        // Standard labels for the product parts
        JLabel nameLabel = new JLabel(product.getName());
        nameLabel.setFont(boldFont);
        add(nameLabel);

        JLabel priceLabel = new JLabel("£" + Integer.toString(product.getPrice()));
        add(priceLabel);

        JLabel manufacturerLabel = new JLabel("Manufactured by " + product.getManufacturer());
        add(manufacturerLabel);

        JLabel gaugeLabel = new JLabel("Gauge: " + product.getGauge());
        add(gaugeLabel);
    }

    
    public ProductPanel(ExpandableCategoryPanel parentPanel, Controller controller) {
        setup(parentPanel, controller);
        add(new JLabel());

        String signalType = controller.isDigital() ? "Digital" : "Analogue";
        JLabel typeLabel = new JLabel("Type: " + signalType);

        add(typeLabel);
    }
    public ProductPanel(ExpandableCategoryPanel parentPanel, Locomotive locomotive) {        
        setup(parentPanel, locomotive);
        add(new JLabel());

        JLabel gaugeLabel = new JLabel("Code DCC: " + locomotive.getCodeDCC());
        add(gaugeLabel);
        add(new JLabel());

        JLabel eraLabel = new JLabel("Era: " + locomotive.getEra());
        add(eraLabel);
        
    }
    public ProductPanel(ExpandableCategoryPanel parentPanel, RollingStock rollingStock) {        
        setup(parentPanel, rollingStock);
        add(new JLabel());

        String rollingStockType = rollingStock.isCarriage() ? "Carriage" : "Wagon";
        JLabel typeLabel = new JLabel("Type: " + rollingStockType);

        add(typeLabel);
        add(new JLabel());
        
        JLabel eraLabel = new JLabel("Era: " + rollingStock.getEra());
        add(eraLabel);
    }
    public ProductPanel(ExpandableCategoryPanel parentPanel, Track track) {        
        setup(parentPanel, track);
    }
    public ProductPanel(ExpandableCategoryPanel parentPanel, Product product) {
        setup(parentPanel, product);
    }

    public ProductPanel(ExpandableCategoryPanel parentPanel, ArrayList<Product> pack) {
        // From a pack type product where the pack product is the first in the list
        setup(parentPanel, pack.get(0));

        // Add a seperation line
        add(new JLabel());
        add(new JLabel());

        ArrayList<Product> uniqueProducts = new ArrayList<>();
        Map<Integer, Integer> itemCountMap = new HashMap<>();

        // Count the number of occurences of each item in the pack
        for (int i = 1; i < pack.size(); ++i) {
            Product product = pack.get(i);
            
            Integer productID = product.getProductID();
            if (itemCountMap.containsKey(productID)) {
                itemCountMap.put(productID, itemCountMap.get(productID) + 1);
            }
            else {
                itemCountMap.put(productID, 1);
                uniqueProducts.add(product);
            }
        }
        
        // Display the pack contents
        JLabel contentsLabel = new JLabel("Contains:");
        contentsLabel.setFont(boldFont);
        add(contentsLabel);
        add(new JLabel());

        for (Product product : uniqueProducts) {
            int count = itemCountMap.get(product.getProductID());
            JLabel nameLabel = new JLabel(product.getName() + String.format(" x%d", count));
            add(nameLabel);

            JLabel manufacturerLabel = new JLabel("Manufactured by " + product.getManufacturer());
            add(manufacturerLabel);
        }
    }
}