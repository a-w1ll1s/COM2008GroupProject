package classes;

/**
 * Inventory creates Inventory objects.
 * They hold the instances of product objects.
 * 
 * Adam Willis, November 2023
 */

public class Inventory {

    protected Product product;
    protected int stockLevel;

    //Constructor
    public Inventory (Product product, int stockLevel) {

        this.product = product;
        this.stockLevel = stockLevel;
    }

    //Getters
    public Product getProduct() {
        return product;
    }

    public int getStockLevel() {
        return stockLevel;
    }

    public String toString() {
        String str = "";

        str += "{Product: " + getProduct().toString();
        str += ", Stock Level: " + getStockLevel();
        str += "}";

        return str;
    }

}
