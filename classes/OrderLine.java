package classes;

/**
 * OrderLine creates OrderLine objects that describe one order line.
 * Order lines make up an order.
 * 
 * Adam Willis, November 2023
 */

public class OrderLine {

    protected int lineNum;
    protected Product product;
    protected int quantity;

    //Constructor
    public OrderLine (int lineNum, Product product, int quantity) {

        this.lineNum = lineNum;
        this.product = product;
        this.quantity = quantity;
    }

    //Setters
    public void setQuantity(int newQuantity) {
        this.quantity = newQuantity;
    }

    //Getters
    public int getLineNum() {
        return lineNum;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getLinePrice() {
        return getProduct().getPrice() * quantity;
    }

    public String toString() {
        String str = "";

        str += "{Line Number: " + getLineNum();
        str += ", Product: " + getProduct().toString();
        str += ", Quantity: " + getQuantity();
        str += "}";

        return str;
    }

}