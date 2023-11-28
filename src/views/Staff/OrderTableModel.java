package views.Staff;

import models.business.Order;
import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

public class OrderTableModel extends AbstractTableModel {
    private final ArrayList<Order> orders;
    private final String[] columnNames = {"Order ID", "User ID", "Date", "Status", "Total Cost"};

    public OrderTableModel(ArrayList<Order> orders) {
        this.orders = orders;
    }

    @Override
    public int getRowCount() {
        return orders.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Order order = orders.get(rowIndex);
        switch (columnIndex) {
            case 0: return order.getOrderID();
            case 1: return order.getUserID();
            case 2: return order.getDate(); 
            case 3: return order.getStatus();
            case 4: return order.getOrderCost();
            default: return null;
        }
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }
}
