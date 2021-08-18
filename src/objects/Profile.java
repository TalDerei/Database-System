package objects;

import java.sql.*;

public class Profile {

    public static PreparedStatement createCustomer;

    /**
     * profile parameters
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
     * Constructor for creating new Profile objects for the database
     */
    public Profile() {
    }

    /**
     * Constructor to create Profile objects from database information
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

    public Profile(int customer_id, String name, int social_security, String date_of_birth, String email, int zip_code, String city, String state, String address, String phone_number) {
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
     * insertCustomer inserts new customer
     */
    public int insertCustomer(Connection conn) {
        //Connection conn = DBManager.getConnection();
        try {
            Profile.createCustomer = conn.prepareStatement("INSERT INTO customer (CUSTOMER_ID, NAME, SOCIAL_SECURITY, EMAIL, ZIP_CODE, CITY, STATE, ADDRESS, DATE_OF_BIRTH, PHONE_NUMBER) VALUES (?,?,?,?,?,?,?,?,?,?)");
        }
        catch (SQLException exception) {
            System.out.println("Error with Prepared Statements!");
        }

        int success = 0;

        try {
            createCustomer.setInt(1, customer_id);
            createCustomer.setString(2, name);
            createCustomer.setInt(3, social_security);
            createCustomer.setString(4, email);
            createCustomer.setInt(5, zip_code);
            createCustomer.setString(6, city);
            createCustomer.setString(7, state);
            createCustomer.setString(8, address);
            createCustomer.setDate(9, Date.valueOf(date_of_birth));
            createCustomer.setString(10, phone_number);
            success = createCustomer.executeUpdate();
        }
        catch (SQLException exception) {
            System.out.println("Failed to Add Customer to Database! Something Went Wrong, Try Again!");
        }
        return success;
    }


    public void setCustomerId (int customer_id) {
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

    public int getCustomerId () {
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
