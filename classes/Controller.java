package classes;

/**
 * Controller creates Controller objects that are subclasses of Products.
 * 
 * Adam Willis, November 2023
 */

public class Controller extends Product{

    protected Boolean digital;

    //Constructor
    public Controller (int productID, String productCode, String manufacturer, 
                        String name, int price, String gauge, Boolean digital) {
        
        super(productID,productCode,manufacturer,name,price,gauge);
        this.digital = digital;
    }

    //Getters
    public Boolean isDigital() {
        return digital;
    }

    public String toString() {
        String str = "";
        
        str += super.toString();
        str += ", Digital: " + isDigital();
        str += "}";

        return str;
    }    
    
}
