package models;

/**
 * Locomotive creates Locomotive objects that are subclasses of Products.
 * 
 * Adam Willis, November 2023
 */

public class Locomotive extends Product{

    protected String codeDCC;
    protected String era;

    //Constructor
    public Locomotive (int productID, String productCode, String manufacturer, String name,
                            int price, String gauge, String codeDCC, String era) {
        
        super(productID,productCode,manufacturer,name,price,gauge);
        this.codeDCC = codeDCC;
        this.era = era;
    }

    //Getters
    public String getCodeDCC() {
        return codeDCC;
    }

    public String getEra() {
        return era;
    }

    public String toString() {
        String str = "";
        
        str += super.toString();
        str += ", DCC Code: " + getCodeDCC();
        str += ", Era: " + getEra();
        str += "}";

        return str;
    }  
     
}
