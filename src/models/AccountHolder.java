package models;

/**
 * AccountHolder creates AccountHolder objects that describe the holder of an account.
 * 
 * Adam Willis, November 2023
 */

public class AccountHolder {

    protected int holderID;
    protected String forename;
    protected String surname;
    protected HolderAddress address;

    //Constructor
    public AccountHolder (int holderID, String forename, String surname, HolderAddress address) {

        this.holderID = holderID;
        this.forename = forename;
        this.surname = surname;
        this.address = address;
    }

    //Setters
    public void setForename(String newForename) {
        this.forename = newForename;
    }

    public void setSurname(String newSurname) {
        this.surname = newSurname;
    }

    //Getters
    public int getHolderID() {
        return holderID;
    }

    public String getForename() {
        return forename;
    }
    
    public String getSurname() {
        return surname;
    }

    public String getName() {
        return forename + " " + surname;
    }

    public HolderAddress getAddress() {
        return address;
    }

    public String toString() {
        String str = "";

        str += "{ID: " + getHolderID();
        str += ", Name: " + getName();
        str += ", Address; " + getAddress().toString();
        str += "}";

        return str;
    }

}