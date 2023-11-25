package models.business;

/**
 * TrackPack creates TrackPack objects that are subclasses of Products.
 * 
 * Adam Willis, November 2023
 */

public class TrackPack extends Product{

    //Constructor
    public TrackPack (int productID, String productCode, String manufacturer, 
                            String name, int price, String gauge) {
        
        super(productID,productCode,manufacturer,name,price,gauge);
    }

}
