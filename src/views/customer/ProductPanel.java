package views.customer;

import java.awt.*;
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

class ProductPanel extends JPanel {
    private Font boldFont = new Font("", Font.BOLD, 14);

    private void setup(Product product) {
        setLayout(new GridLayout(0, 2)); // TODO: Change to 3 and add buttons to the right?
        setBorder(BorderFactory.createLoweredSoftBevelBorder());

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

    
    public ProductPanel(Controller controller) {
        setup(controller);
        add(new JLabel());

        String signalType = controller.isDigital() ? "Digital" : "Analogue";
        JLabel typeLabel = new JLabel("Type: " + signalType);

        add(typeLabel);
    }
    public ProductPanel(Locomotive locomotive) {        
        setup(locomotive);
        add(new JLabel());

        JLabel gaugeLabel = new JLabel("Code DCC: " + locomotive.getCodeDCC());
        add(gaugeLabel);
        add(new JLabel());

        JLabel eraLabel = new JLabel("Era: " + locomotive.getEra());
        add(eraLabel);
        
    }
    public ProductPanel(RollingStock rollingStock) {        
        setup(rollingStock);
        add(new JLabel());

        String rollingStockType = rollingStock.isCarriage() ? "Carriage" : "Wagon";
        JLabel typeLabel = new JLabel("Type: " + rollingStockType);

        add(typeLabel);
        add(new JLabel());
        
        JLabel eraLabel = new JLabel("Era: " + rollingStock.getEra());
        add(eraLabel);
    }
    public ProductPanel(Track track) {        
        setup(track);
    }
    public ProductPanel(Product product) {
        setup(product);
    }

    public ProductPanel(ArrayList<Product> pack) {
        // From a pack type product where the pack product is the first in the list
        setup(pack.get(0));

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