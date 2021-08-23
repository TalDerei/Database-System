package objects.CustomerObjects;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.Date;
import manager.DBManager;

public class Customer {
    /**
     * Prepared Statments 
     */
    public static PreparedStatement addCustomer;
    public static PreparedStatement checkCustomerID;

    /**
     * Customer profile parameters
     */
    private int customer_id;
    private String name;
    private int social_security;
    private String date_of_birth;
    private String email;
    private int zip_code;
    private String city;
    private String state;
    private String address;
    private String phone_number; 

    /**
     * Constructor for creating new Profile objects
     */
    public Customer() {
    }

    /**
     * Constructor to create Customer objects
     * @param customer_id
     * @param name
     * @param social_security
     * @param date_of_birth
     * @param email
     * @param zip_code
     * @param city
     * @param state
     * @param address
     * @param phone_number
     */
    public Customer(int customer_id, String name, int social_security, String date_of_birth, String email, int zip_code, String city, String state, String address, String phone_number) {
        this.customer_id = customer_id;
        this.name = name;
        this.social_security = social_security;
        this.date_of_birth = date_of_birth;
        this.email = email;
        this.zip_code = zip_code;
        this.city = city;
        this.state = state;
        this.address = address;
        this.phone_number = phone_number;
    }

    /**
     * Insert new customer profile 
     * @return Whether inserting customer was successful
     */
    public Boolean insertCustomer() {
        Connection conn = DBManager.getConnection();

        try {
            Customer.addCustomer = conn.prepareStatement("INSERT INTO customer (CUSTOMER_ID, NAME, SOCIAL_SECURITY, EMAIL, ZIP_CODE, CITY, STATE, ADDRESS, DATE_OF_BIRTH, PHONE_NUMBER) VALUES (?,?,?,?,?,?,?,?,?,?)");
        }
        catch (SQLException exception) {
            System.out.println("Error with Prepared Statements!");
        }

        try {
            addCustomer.setInt(1, customer_id);
            addCustomer.setString(2, name);
            addCustomer.setInt(3, social_security);
            addCustomer.setString(4, email);
            addCustomer.setInt(5, zip_code);
            addCustomer.setString(6, city);
            addCustomer.setString(7, state);
            addCustomer.setString(8, address);
            addCustomer.setDate(9, Date.valueOf(date_of_birth));
            addCustomer.setString(10, phone_number);
            addCustomer.executeUpdate();
            return true;
        }
        catch (SQLException exception) {
            System.out.println("Failed to Add Customer to Database! Something Went Wrong, Try Again!");
        }
        return false;
    }

    /**
     * Check is customer ID already exists in the database when user attempts to add policy
     * @param customer_id
     * @return Whether retrieving customer ID was successful
     */
    public int getCustomerID(int customer_id) {
        Connection conn = DBManager.getConnection();
        try {
            Customer.checkCustomerID = conn.prepareStatement("SELECT customer_id FROM customer WHERE customer_id = ?");
        }
        catch (SQLException exception) {
            System.out.println("Error with Prepared Statements!");
        }

        int id = 0;
        try {
            checkCustomerID.setInt(1, customer_id);
            ResultSet resultset = checkCustomerID.executeQuery();
            while (resultset.next()) {
                id = resultset.getInt("customer_id");
                System.out.println("customer_id is: " + id);
            }
        }
        catch (SQLException exception) {
            System.out.println("Customer ID invalid! No customer exists!!");
        }
        return id;
    }


    /**
     *  Getters and Setter functions
     */
    public void setId (int customer_id) {
        this.customer_id = customer_id;
    }

    public void setName (String name) {
        this.name = name;
    }

    public void setSocialSecurity (int social_security) {
        this.social_security = social_security;
    }

    public void setDateOfBirth (String date_of_birth) {
        this.date_of_birth = date_of_birth;
    }

    public void setEmail (String email) {
        this.email = email;
    }

    public void setZipCode (int zip_code) {
        this.zip_code = zip_code;
    }

    public void setCity (String city) {
        this.city = city;
    }

    public void setState (String state) {
        this.state = state;
    }

    public void setAddress (String address) {
        this.address = address;
    }

    public void setPhoneNumber (String phone_number) {
        this.phone_number = phone_number;
    }

    public int getId () {
        return customer_id;
    }

    public String getName () {
        return name;
    }

    public int getSocialSecurity () {
        return social_security;
    }

    public String getDateOfBirth () {
        return date_of_birth;
    }

    public String getEmail () {
        return email;
    }

    public int getZipCode () {
        return zip_code;
    }

    public String getCity () {
        return city;
    }

    public String getState () {
        return state;
    }

    public String getAddress () {
        return address;
    }

    public String getPhoneNumber () {
        return phone_number;
    }
}
