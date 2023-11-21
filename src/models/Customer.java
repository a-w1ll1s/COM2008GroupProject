package models;

/**
 * Customer creates Customer objects that are subclasses of Accounts.
 * 
 * Adam Willis, November 2023
 */

public class Customer extends Account {

    protected BankDetails bankDetails;
    protected Boolean hasBankDetails;

    //Constructor
    public Customer (int userID, String email, String password, Boolean isCustomer, 
                        Boolean isStaff, Boolean isManager, AccountHolder holder) {

        super(userID, email, password, isCustomer, isStaff, isManager, holder);
        this.hasBankDetails = false;
    }

    public Customer (int userID, String email, String password, Boolean isCustomer, Boolean isStaff, 
                        Boolean isManager, AccountHolder holder, BankDetails bankDetails) {

        super(userID, email, password, isCustomer, isStaff, isManager, holder);
        this.bankDetails = bankDetails;
        this.hasBankDetails = true;
    }

    //Setters
    public void addBankDetails(BankDetails newBankDetails) {
        this.bankDetails = newBankDetails;
        this.hasBankDetails = true;
    }

    public void removeBankDetails() {
        this.bankDetails = null;
        this.hasBankDetails = false;
    }

    //Getters
    public BankDetails getBankDetails() {
        return bankDetails;
    }

    public Boolean hasBankDetails() {
        return hasBankDetails;
    }

    public String toString() {
        String str = "";

        str += super.toString();
        str += ", Has Bank Details: " + hasBankDetails();
        str += "}";

        return str;
    }
    
}