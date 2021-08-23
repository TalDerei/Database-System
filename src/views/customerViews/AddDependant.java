package views.customerViews;

import java.sql.*;
import manager.IOManager;
import objects.CustomerObjects.*;

public class AddDependant {
    /**
     * Add dependant to existing policy
     * @param connection
     */
    public static void add_dependant(Connection connection) {
        /**
         * Policy object
         */
        Policy policy = new Policy();

        String date_of_birth = "";
        System.out.println("Enter Existing Policy ID to Add a Dependant to the Policy: ");
        int policy_id = Input.user_integer();
        if (policy.getPolicyID(policy_id) == policy_id) {
            int dependant_id = IOManager.idNumber(999999);
            System.out.print("Enter Dependant Name: ");
            String name = IOManager.stringInput(30);
            System.out.print("Enter Dependant Social Security: ");
            int social_security = IOManager.intInput(100000000, 999999999, 9);
            System.out.print("Enter Dependant Date of Birth: ");
            boolean dependant_valid_date = true;
            while (dependant_valid_date) {
                date_of_birth = Input.user_string();
                dependant_valid_date = Input.validateDate(date_of_birth);
            } 
            System.out.print("\n");
            if (policy.insertDependant(dependant_id, policy_id, name, social_security, date_of_birth)) {
                try{
                    connection.commit();
                    System.out.println("TRANSACTION SUCCEEDED!\n");
                }
                catch (SQLException exception) {
                    System.out.println("TRANSACTION FAILED! ROLLED BACK!\n");
                }
            }
        }
        else {
            System.out.println("Could Not Find Policy ID! Try again!\n");
        }
    }
}
