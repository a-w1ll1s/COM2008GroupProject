package views.customer;

import java.awt.*;

import javax.swing.*;

import views.CustomStyleConstants;
import views.MainFrame;

public class ProductViewPanel extends JPanel {
    private MainFrame parentFrame;
    private ExpandableCategoryPanel selectedCategory;

    public ProductViewPanel(MainFrame frame) {        
        parentFrame = frame;
        setLayout(new GridBagLayout());
        
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
            });

            buttonConstraints.gridy = i;
            categoryButtonsPanel.add(button, buttonConstraints);
        }

        GridBagConstraints buttonsPanelConstraints = new GridBagConstraints();
        buttonsPanelConstraints.fill = GridBagConstraints.BOTH;
        buttonsPanelConstraints.weightx = 0.15;
        buttonsPanelConstraints.weighty = 1;
        
        add(categoryButtonsPanel, buttonsPanelConstraints);
        

        // Selected category products panel
        selectCategory(ProductCategories.categories[0]);
    }

    private void selectCategory(String category) {
        // Check we're not already on the category
        if (selectedCategory != null && category.equals(selectedCategory.getCategory())) 
            return;

        // Remove throws error if the category wasn't actually selected.
        try {
            remove(selectedCategory);
        }
        catch (Exception e) {
            
        }

        selectedCategory = new ExpandableCategoryPanel(null, category);
        selectedCategory.setBackground(Color.YELLOW);

        GridBagConstraints expandableCategoryPanelConstraints = new GridBagConstraints();
        expandableCategoryPanelConstraints.fill = GridBagConstraints.BOTH;
        expandableCategoryPanelConstraints.weightx = 1;
        expandableCategoryPanelConstraints.weighty = 1;

        add(selectedCategory, expandableCategoryPanelConstraints);


        revalidate();
        repaint();
        setVisible(true);        
    }
}