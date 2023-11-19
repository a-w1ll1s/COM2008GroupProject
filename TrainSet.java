
/**
 * TrainSet creates TrainSet objects that are subclasses of Products.
 * 
 * Adam Willis, November 2023
 */

public class TrainSet extends Product{

    //Constructor
    public TrainSet (int productID, String productCode, String manufacturer, 
                            String name, int price, String gauge) {
        
        super(productID,productCode,manufacturer,name,price,gauge);
    }

}