package models;

/**
 * Account creates Account objects that describe one account.
 * 
 * Adam Willis, November 2023
 */

public class Account {

    protected int userID;
    protected String email;
    protected String password;
    protected Boolean isCustomer;
    protected Boolean isStaff;
    protected Boolean isManager;
    protected AccountHolder holder;

    //Constructor
    public Account (int userID, String email, String password, Boolean isCustomer,
                      Boolean isStaff, Boolean isManager, AccountHolder holder) {

        this.userID = userID;
        this.email = email;
        this.password = password;
        this.isCustomer = isCustomer;
        this.isStaff = isStaff;
        this.isManager = isManager;
        this.holder = holder;
    }

    //Setters
    public void setEmail(String newEmail) {
        this.email = newEmail;
    }

    //Getters
    public int getUserID() {
        return userID;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Boolean isCustomer() {
        return isCustomer;
    }

    public Boolean isStaff() {
        return isStaff;
    }

    public Boolean isManager() {
        return isManager;
    }

    public AccountHolder getHolder() {
        return holder;
    }

    public String toString() {
        String str = "";

        str += "{ID: " + getUserID();
        str += ", Email: " + getEmail();
        str += ", Customer: " + isCustomer();
        str += ", Staff: £" + isStaff();
        str += ", Manager: " + isManager();
        str += ", Holder: " + getHolder().toString();
        str += "}";

        return str;
    }
    
}