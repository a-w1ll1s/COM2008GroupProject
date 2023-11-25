package models.business;

/**
 * Staff creates Staff objects that are subclasses of Accounts.
 * 
 * Adam Willis, November 2023
 */

public class Staff extends Account {

    //Constructor
    public Staff (int userID, String email, String password, Boolean isCustomer, 
                        Boolean isStaff, Boolean isManager, AccountHolder holder) {

        super(userID, email, password, isCustomer, isStaff, isManager, holder);
    }

}