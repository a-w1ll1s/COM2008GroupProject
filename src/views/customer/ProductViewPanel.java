package views.customer;

import java.awt.*;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import models.business.Account;
import views.CustomStyleConstants;
import views.MainFrame;

public class ProductViewPanel extends JPanel {
    private MainFrame parentFrame;
    private JPanel mainPanel, optionsPanel;
    private ExpandableCategoryPanel selectedCategory;
    private ArrayList<JButton> categoryButtons = new ArrayList<>();
    private JSpinner quantitySpinner;

    public ProductViewPanel(MainFrame frame, Boolean isStaffView) {        
        parentFrame = frame;
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
        buttonsPanelConstraints.fill = GridBagConstraints.BOTH;
        buttonsPanelConstraints.weightx = 0.15;
        buttonsPanelConstraints.weighty = 1;
        
        mainPanel.add(categoryButtonsPanel, buttonsPanelConstraints);

        GridBagConstraints mainPanelConstraints = new GridBagConstraints();
        mainPanelConstraints.fill = GridBagConstraints.BOTH;
        mainPanelConstraints.weightx = 1;
        mainPanelConstraints.weighty = 1;

        // Options panel
        optionsPanel = new JPanel();
        optionsPanel.setLayout(new GridBagLayout());
        GridBagConstraints optionsPanelConstraints = new GridBagConstraints();
        optionsPanelConstraints.fill = GridBagConstraints.BOTH;
        optionsPanelConstraints.weightx = 1;
        optionsPanelConstraints.gridy = 1;

        if (isStaffView) {
            addStaffOptions();
        }
        else {
            addCustomerOptions();
        }
        
        add(mainPanel, mainPanelConstraints);
        add(optionsPanel, optionsPanelConstraints);

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

    private void addStaffOptions() {
        GridBagConstraints optionButtonConstraints = new GridBagConstraints();
        optionButtonConstraints.insets = new Insets(5, 5, 5, 5);

        JButton addProductButton = new JButton("Add product");
        JButton deleteProductButton = new JButton("Delete product");

        optionsPanel.add(addProductButton, optionButtonConstraints);
        optionsPanel.add(deleteProductButton, optionButtonConstraints);
        //optionsPanel.add(quantityLabel, optionButtonConstraints);
        //optionsPanel.add(quantitySpinner);

    }

    private void addCustomerOptions() {
        GridBagConstraints optionButtonConstraints = new GridBagConstraints();
        optionButtonConstraints.insets = new Insets(5, 5, 5, 5);

        JButton confirmOrderButton = new JButton("Confirm order");

        JButton addToOrderButton = new JButton("Add to order");
        addToOrderButton.addActionListener(e -> {
            if (selectedCategory.getSelectedProductID() == -1) {
                JOptionPane.showMessageDialog(parentFrame, "Please select a product!");
                return;
            }
            System.out.println(selectedCategory.getSelectedProductID());            
        });

        JButton removeFromOrderButton = new JButton("Remove from order");

        JLabel quantityLabel = new JLabel("Order Quantity: ");
        quantitySpinner = new JSpinner(new SpinnerNumberModel(0, 0, 100, 1));

        quantitySpinner.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                JSpinner s = (JSpinner) e.getSource();
                System.out.println(s.getValue().toString());
            }
        });

        optionsPanel.add(addToOrderButton, optionButtonConstraints);
        optionsPanel.add(removeFromOrderButton, optionButtonConstraints);
        optionsPanel.add(quantityLabel, optionButtonConstraints);
        optionsPanel.add(quantitySpinner);
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