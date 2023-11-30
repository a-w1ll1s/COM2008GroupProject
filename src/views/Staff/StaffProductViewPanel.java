package views.Staff;

import java.awt.*;

import javax.swing.*;

import views.MainFrame;
import views.customer.ProductViewPanel;

public class StaffProductViewPanel extends JPanel {
    private MainFrame parentFrame;
    private ProductViewPanel productViewPanel;
    
    public StaffProductViewPanel(MainFrame frame) {        
        parentFrame = frame;
        setLayout(new GridBagLayout());

        GridBagConstraints productViewPanelConstraints = new GridBagConstraints();
        productViewPanelConstraints.fill = GridBagConstraints.BOTH;
        productViewPanelConstraints.weightx = 1;
        productViewPanelConstraints.weighty = 1;
        productViewPanel = new ProductViewPanel(frame);


        // Options panel
        JPanel optionsPanel = new JPanel();
        optionsPanel.setLayout(new GridBagLayout());
        GridBagConstraints optionsPanelConstraints = new GridBagConstraints();
        optionsPanelConstraints.fill = GridBagConstraints.BOTH;
        optionsPanelConstraints.weightx = 1;
        optionsPanelConstraints.gridy = 1;

        GridBagConstraints optionButtonConstraints = new GridBagConstraints();
        optionButtonConstraints.insets = new Insets(5, 5, 5, 5);

        JButton addProductButton = new JButton("Add product");
        JButton deleteProductButton = new JButton("Delete product");

        optionsPanel.add(addProductButton, optionButtonConstraints);
        optionsPanel.add(deleteProductButton, optionButtonConstraints);
        //optionsPanel.add(quantityLabel, optionButtonConstraints);
        //optionsPanel.add(quantitySpinner);

        add(productViewPanel, productViewPanelConstraints);
        add(optionsPanel, optionsPanelConstraints);
    }
}