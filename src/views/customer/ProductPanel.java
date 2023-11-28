package views.customer;

import java.awt.*;
import java.lang.reflect.Field;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.text.StyleConstants;

import models.business.Controller;
import models.business.Locomotive;
import models.business.Product;
import models.business.RollingStock;
import models.business.Track;
import views.CustomStyleConstants;

class ProductPanel extends JPanel {
    private Font boldFont = new Font("", Font.BOLD, 14);

    private void setup(Product product) {
        setLayout(new GridLayout(0, 2));
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
}