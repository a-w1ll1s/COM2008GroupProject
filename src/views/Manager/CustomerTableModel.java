package views.Manager;
/**
 * Cutomer table model puts the customer details into a table that can be displayed
 * 
 * Liam Thomas, November 2023
 */
import models.business.Customer;
import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

public class CustomerTableModel extends AbstractTableModel {
    private final ArrayList<Customer> customers;
    private final String[] columnNames = {"UserID", "Email", "First Name", "Last Name"};

    public CustomerTableModel(ArrayList<Customer> customers) {
        this.customers = customers;
    }

    @Override
    public int getRowCount() {
        return customers.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Customer customer = customers.get(rowIndex);
        switch (columnIndex) {
            case 0: return customer.getUserID();
            case 1: return customer.getEmail();
            case 2: return customer.getHolder().getForename();
            case 3: return customer.getHolder().getSurname();  
            default: return null;
        }
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }
}
