package models.business;

import java.util.ArrayList;

/**
 * Order creates Order objects that describe one order.
 * 
 * Adam Willis, November 2023
 */

public class Order {

    protected int orderID;
    protected int userID;
    protected int date;
    protected String status;

    protected ArrayList<OrderLine> orderLines = new ArrayList<>();

    //Constructor
    public Order (int orderID, int userID, int date, String status) {

        this.orderID = orderID;
        this.userID = userID;
        this.date = date;
        this.status = status;
    }

    //Setters
    public void addOrderLine(OrderLine orderLine) {
        orderLines.add(orderLine);
    }

    public void deleteOrderLine(OrderLine orderLine) {
        orderLines.remove(orderLine.getLineNum());
    }

    //Getters
    public int getOrderID() {
        return orderID;
    }

    public int getUserID() {
        return userID;
    }

    public int getDate() {
        return date;
    }

    public String getStatus() {
        return status;
    }

    public int getOrderCost() {
        int totalCost = 0;
        for (OrderLine line : orderLines) {
            int lineCost = line.getProduct().getPrice() * line.getQuantity();
            totalCost += lineCost;
        }
        return totalCost;
    }
    

    public String toString() {
        String str = "";

        str += "{Order ID: " + getOrderID();
        str += ", User ID: " + getUserID();
        str += ", Date: " + getDate();
        str += ", Status: " + getStatus();
        str += "}";

        return str;
    }

    public void setOrderLines(ArrayList<OrderLine> orderLines) {
        this.orderLines = orderLines;
    }

    public ArrayList<OrderLine> getOrderLines() {
        return orderLines;
    }
}