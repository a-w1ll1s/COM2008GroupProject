package models.business;

/**
 * BankDetails creates BankDetails objects.
 * 
 * Adam Willis, November 2023
 */

public class BankDetails {

    protected String cardBrand;
    protected int cardNum;
    protected int cardExpiry;
    protected int securityCode;

    //Constructor
    public BankDetails (String cardBrand, int cardNum, int cardExpiry, int securityCode) {

        this.cardBrand = cardBrand;
        this.cardNum = cardNum;
        this.cardExpiry = cardExpiry;
        this.securityCode = securityCode;
    }

    //Setters
    public void setCardBrand(String newCardBrand) {
        this.cardBrand = newCardBrand;
    }

    public void setCardNum(int newCardNum) {
        this.cardNum = newCardNum;
    }

    public void setCardExpiry(int newCardExpiry) {
        this.cardExpiry = newCardExpiry;
    }

    public void setSecurityCode(int newSecurityCode) {
        this.securityCode = newSecurityCode;
    }

    //Getters
    public String getCardBrand() {
        return cardBrand;
    }

    public int getCardNum() {
        return cardNum;
    }
    
    public int getCardExpiry() {
        return cardExpiry;
    }
    
    public int getSecurityCode() {
        return securityCode;
    }

    public String toString() {
        String str = "";

        str += "{Card Brand: " + getCardBrand();
        str += ", Card Number: " + getCardNum();
        str += ", Card Expiry: " + getCardExpiry();
        str += ", Security Code: " + getSecurityCode();
        str += "}";

        return str;
    }
    
}