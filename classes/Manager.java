package classes;

/**
 * Manager creates Manager objects that are subclasses of Accounts.
 * 
 * Adam Willis, November 2023
 */

public class Manager extends Account {

    //Constructor
    public Manager (int userID, String email, String password, Boolean isCustomer, 
                        Boolean isStaff, Boolean isManager, AccountHolder holder) {

        super(userID, email, password, isCustomer, isStaff, isManager, holder);
    }

}