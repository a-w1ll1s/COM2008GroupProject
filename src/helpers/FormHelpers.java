package helpers;

import java.awt.GridBagConstraints;

import javax.swing.JLabel;

public class FormHelpers {
    public static GridBagConstraints getGridBagConstraints(int x, int y, Boolean fillHorizontal) {
        /* Helper function to create a GridBagConstraints object with the given
           row and column. Removes a lot of duplicated code and enables forms to
           be of a similar format throughout the program. */
           
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = x;
        constraints.gridy = y;
        if (fillHorizontal)
            constraints.fill = GridBagConstraints.HORIZONTAL;
        return constraints;
    }

    public static GridBagConstraints getGridBagConstraints(int x, int y) {
        // Allows the fill parameter to not be given and instead the default fill is used
        return FormHelpers.getGridBagConstraints(x, y, true);
    }

    public static Boolean setErrorIfEmpty(String value, JLabel errorLabel) {
        // Helper method for setting an error label's text if the string is empty
        // Simplifies form validation.
        if (value.isBlank()) {
            errorLabel.setText("Cannot be empty");
            return true;
        }
        return false;
    }
}
