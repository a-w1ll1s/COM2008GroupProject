package views.Manager;
/**
 * Staff table model puts the Staff details into a table that can be displayed
 * 
 * Liam Thomas, November 2023
 */
import models.business.Staff;
import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

public class StaffTableModel extends AbstractTableModel {
    private final ArrayList<Staff> staffs;
    private final String[] columnNames = {"UserID", "Email", "First Name", "Last Name"};

    public StaffTableModel(ArrayList<Staff> staffs) {
        this.staffs = staffs;
    }

    @Override
    public int getRowCount() {
        return staffs.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Staff staff = staffs.get(rowIndex);
        switch (columnIndex) {
            case 0: return staff.getUserID();
            case 1: return staff.getEmail();
            case 2: return staff.getHolder().getForename(); 
            case 3: return staff.getHolder().getSurname();  
            default: return null;
        }
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }
}
