package views.customer;

import java.awt.*;
import java.util.ArrayList;

import javax.swing.*;

import models.business.Order;
import models.business.Product;
import views.CustomStyleConstants;

public class ProductViewPanel extends JPanel {
    private JPanel mainPanel, optionsPanel;
    private ExpandableCategoryPanel selectedCategory;
    private ArrayList<JButton> categoryButtons = new ArrayList<>();
    private JSpinner quantitySpinner;

    public ProductViewPanel() {        
        setLayout(new GridBagLayout());

        mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());

        // Category Buttons Panel
        JPanel categoryButtonsPanel = new JPanel();
        categoryButtonsPanel.setLayout(new GridBagLayout());

        GridBagConstraints buttonConstraints = new GridBagConstraints();
        buttonConstraints.fill = GridBagConstraints.BOTH;
        buttonConstraints.weightx = 1;
        buttonConstraints.weighty = 1;

        // Create a button for each category
        for (int i = 0; i < ProductCategories.categories.length; ++i) {
            JButton button = new JButton(ProductCategories.categories[i]);
            button.addActionListener(e -> {
                JButton b = (JButton)e.getSource();
                selectCategory(b.getText());
                showSelectedButton(b);
            });

            buttonConstraints.gridy = i;
            categoryButtons.add(button);
            categoryButtonsPanel.add(button, buttonConstraints);
        }

        GridBagConstraints buttonsPanelConstraints = new GridBagConstraints();
        buttonsPanelConstraints.fill = GridBagConstraints.VERTICAL;
        buttonsPanelConstraints.weighty = 1;
        
        mainPanel.add(categoryButtonsPanel, buttonsPanelConstraints);

        GridBagConstraints mainPanelConstraints = new GridBagConstraints();
        mainPanelConstraints.fill = GridBagConstraints.BOTH;
        mainPanelConstraints.weightx = 1;
        mainPanelConstraints.weighty = 1;

        
        
        add(mainPanel, mainPanelConstraints);

        // Selected category products panel
        showSelectedButton(categoryButtons.get(0));
        selectCategory(ProductCategories.categories[0]);
    }

    private void showSelectedButton(JButton button) {
        // Function to reset all category button colours and select the given one
        Color defaultColour = new JButton().getBackground();
        for (JButton b : categoryButtons) {
            if (b.getText().equals(button.getText())) {
                b.setBackground(CustomStyleConstants.TOGGLED_NAV_BUTTON_COLOUR);
            }
            else {
                b.setBackground(defaultColour);
            }
        }
    }

    public int getSelectedProductID() {
        return selectedCategory.getSelectedProductID();
    }
    public Product getSelectedProduct() {
        return selectedCategory.getSelectedProduct();
    }

    public void onSelectedProductChanged() {
        // TODO: Set the quantity to the current quantity of the product in the order
        //quantitySpinner.setValue();
    }


    private void selectCategory(String category) {
        // Check we're not already on the category
        if (selectedCategory != null && category.equals(selectedCategory.getCategory())) 
            return;

        // Remove throws error if the category wasn't actually selected.
        try {
            mainPanel.remove(selectedCategory);
        }
        catch (Exception e) {}

        selectedCategory = new ExpandableCategoryPanel(this, null, category);
        selectedCategory.setBackground(Color.YELLOW);

        GridBagConstraints expandableCategoryPanelConstraints = new GridBagConstraints();
        expandableCategoryPanelConstraints.fill = GridBagConstraints.BOTH;
        expandableCategoryPanelConstraints.weightx = 1;
        expandableCategoryPanelConstraints.weighty = 1;

        mainPanel.add(selectedCategory, expandableCategoryPanelConstraints);


        revalidate();
        repaint();
        setVisible(true);        
    }
}