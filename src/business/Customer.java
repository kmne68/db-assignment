/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

/**
 *
 * @author Keith
 */
public class Customer {
    
    private int customerID;
    private int salesID, areaCode, phoneNumber;
    
    private String customerName, address, city, state, zip, comments;
    private double creditLimit;
    
    // a bean needs an empty constructor
    public Customer() {
        customerID = 0;
        salesID = 0;
        areaCode = 0;
        phoneNumber = 0;
        customerName = "";
        address = "";
        city = "";
        state = "";
        zip = "";
        comments = "";
        creditLimit = 0.0;
        
        
                
    }

    /**
     * @return the customerID
     */
    public int getCustomerID() {
        return customerID;
    }

    /**
     * @param customerID the customerID to set
     */
    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    /**
     * @return the salesID
     */
    public int getSalesID() {
        return salesID;
    }

    /**
     * @param salesID the salesID to set
     */
    public void setSalesID(int salesID) {
        this.salesID = salesID;
    }

    /**
     * @return the areaCode
     */
    public int getAreaCode() {
        return areaCode;
    }

    /**
     * @param areaCode the areaCode to set
     */
    public void setAreaCode(int areaCode) {
        this.areaCode = areaCode;
    }

    /**
     * @return the phoneNumnber
     */
    public int getPhoneNumnber() {
        return phoneNumber;
    }

    /**
     * @param phoneNumnber the phoneNumnber to set
     */
    public void setPhoneNumber(int phoneNumnber) {
        this.phoneNumber = phoneNumnber;
    }

    /**
     * @return the customerNamme
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * @param customerNamme the customerNamme to set
     */
    public void setCustomerName(String customerNamme) {
        this.customerName = customerNamme;
    }

    /**
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @return the city
     */
    public String getCity() {
        return city;
    }

    /**
     * @param city the city to set
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * @return the state
     */
    public String getState() {
        return state;
    }

    /**
     * @param state the state to set
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * @return the zip
     */
    public String getZip() {
        return zip;
    }

    /**
     * @param zip the zip to set
     */
    public void setZip(String zip) {
        this.zip = zip;
    }

    /**
     * @return the comments
     */
    public String getComments() {
        return comments;
    }

    /**
     * @param comments the comments to set
     */
    public void setComments(String comments) {
        this.comments = comments;
    }

    /**
     * @return the creditLimit
     */
    public double getCreditLimit() {
        return creditLimit;
    }

    /**
     * @param creditLimit the creditLimit to set
     */
    public void setCreditLimit(double creditLimit) {
        this.creditLimit = creditLimit;
    }
    
    @Override
    public String toString() {
        return this.customerName + " (" + this.customerID + ")";
    }    
    
}
