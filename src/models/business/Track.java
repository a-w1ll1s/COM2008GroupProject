package models.business;

/**
 * Track creates Track objects that are subclasses of Products.
 * 
 * Adam Willis, November 2023
 */

public class Track extends Product{

    //Constructor
    public Track (int productID, String productCode, String manufacturer, 
                        String name, int price, String gauge) {
        
        super(productID,productCode,manufacturer,name,price,gauge);
    }
    
}
