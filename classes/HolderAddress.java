package classes;

/**
 * HolderAddress creates HolderAddress objects that describe the address of a holder.
 * 
 * Adam Willis, November 2023
 */

public class HolderAddress {

    protected String houseNum;
    protected String roadName;
    protected String cityName;
    protected String postcode;

    //Constructor
    public HolderAddress (String houseNum, String roadName, String cityName, String postcode) {

        this.houseNum = houseNum;
        this.roadName = roadName;
        this.cityName = cityName;
        this.postcode = postcode;
    }

    //Setters
    public void setHouseNum(String newHouseNum) {
        this.houseNum = newHouseNum;
    }

    public void setRoadName(String newRoadName) {
        this.houseNum = newRoadName;
    }

    public void setCityName(String newCityName) {
        this.houseNum = newCityName;
    }

    public void setPostcode(String newPostcode) {
        this.houseNum = newPostcode;
    }

    //Getters
    public String getHouseNum() {
        return houseNum;
    }

    public String getRoadName() {
        return roadName;
    }
    
    public String getCityName() {
        return cityName;
    }

    public String getPostcode() {
        return postcode;
    }

    public String getAddress() {
        String str = "";

        str += "{";
        str += houseNum + " ";
        str += roadName + ", ";
        str += cityName + ", ";
        str += postcode + "}";
        
        return str;
    }

    public String toString() {
        return getAddress();
    }

}