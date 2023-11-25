package models.business;

/**
 * RollingStock creates RollingStock objects that are subclasses of Products.
 * 
 * Adam Willis, November 2023
 */

 public class RollingStock extends Product{

    protected String era;
    protected Boolean isCarriage;

    //Constructor
    public RollingStock (int productID, String productCode, String manufacturer, String name,
                            int price, String gauge, String era, Boolean isCarriage) {
        
        super(productID,productCode,manufacturer,name,price,gauge);
        this.era = era;
        this.isCarriage = isCarriage;
    }

    //Getters
    public String getEra() {
        return era;
    }

    public Boolean isCarriage() {
        return isCarriage;
    }

    public String toString() {
        String str = "";
        
        str += super.toString();
        str += ", Era: " + getEra();
        str += ", Carriage: " + isCarriage();
        str += "}";

        return str;
    }  
     
}
