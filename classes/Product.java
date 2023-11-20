package classes;

/**
 * Product creates Product objects that are a part of the Inventory.
 * They describe the general product, not a single instance.
 * 
 * Adam Willis, November 2023
 */

public class Product {

    protected int productID;
    protected String productCode;
    protected String manufacturer;
    protected String name;
    protected int price;
    protected String gauge;

    //Constructor
    public Product (int productID, String productCode, String manufacturer,
                        String name, int price, String gauge) {

        this.productID = productID;
        this.productCode = productCode;
        this.manufacturer = manufacturer;
        this.name = name;
        this.price = price;
        this.gauge = gauge;
    }

    //Getters
    public int getProductID() {
        return productID;
    }

    public String getProductCode() {
        return productCode;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public String getGauge() {
        return gauge;
    }

    public String toString() {
        String str = "";

        str += "{ID: " + getProductID();
        str += ", Code: " + getProductCode();
        str += ", Manufacturer: " + getManufacturer();
        str += ", Name: " + getName();
        str += ", Price: £" + getPrice();
        str += ", Gauge: " + getGauge();
        str += "}";

        return str;
    }
    
}
