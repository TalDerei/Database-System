package views.customerViews;

import java.sql.*;
import manager.IOManager;
import objects.CustomerObjects.*;

public class CreateProfile {
    /**
     * Create user profile
     * @param connection
     */
    public static void create_profile(Connection connection) {
        /**
         * Policy object
         */
        Customer customer = new Customer();

        boolean valid_start_date = true;
        customer.setId(IOManager.idNumber(999999));
        System.out.print("Enter Customer Name: ");
        customer.setName(IOManager.stringInputWithoutNumbers(30));
        System.out.print("Enter Social Security: ");
        customer.setSocialSecurity(IOManager.intInput(100000000, 999999999, 9));
        System.out.print("Enter Birth Date ([yy]yy-mm-dd): ");
        while (valid_start_date) {
            customer.setDateOfBirth(Input.user_string());
            valid_start_date = Input.validateDate(customer.getDateOfBirth());
        } 
        System.out.print("Enter Email Address: ");
        customer.setEmail(IOManager.validEmail());
        System.out.print("Enter Zip Code: ");
        customer.setZipCode(IOManager.intInput(00000, 99999, 5));
        System.out.print("Enter City: ");
        customer.setCity(IOManager.stringInputWithoutNumbers(20));
        System.out.print("Enter State: ");
        customer.setState( IOManager.stringInputWithoutNumbers(20));
        System.out.print("Enter Address: ");
        customer.setAddress(IOManager.stringInput(50));
        System.out.print("Enter Phone Number: ");
        customer.setPhoneNumber(IOManager.validPhoneNumber(10));
        if (customer.insertCustomer()) {
            try{
                connection.commit();
                System.out.println("TRANSACTION SUCCEEDED!\n");
            }
            catch (SQLException exception) {
                System.out.println("TRANSACTION FAILED! ROLLED BACK!\n");
            }
        }
        System.out.println("DON'T FORGET TO WRITE DOWN YOUR UNIQUE CUSTOMER ID SO YOU DON'T FORGET IT!");
        System.out.println("YOU'LL NEED THIS TO MAKE FUTURE INQUIRIES ASSOCIATED WITH OUTSTANDING POLICY/CLAIMS!");
        System.out.println("YOUR UNIQUE CUSTOMER ID IS: " + customer.getId() + "\n");
    }
}