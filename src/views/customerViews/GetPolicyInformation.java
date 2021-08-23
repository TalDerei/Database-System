package views.customerViews;

import java.sql.*;

import manager.IOManager;
import objects.CustomerObjects.*;

public class GetPolicyInformation {
    /**
     * Get All Information on an Existing Policy
     */
    public static void get_policy_information(Connection connection) {
        /**
         * Policy object
         */
        Policy policy = new Policy();
        System.out.print("Enter Existing Policy ID to Retrieve Information About the Policy: \n");
        policy.setPolicyId(Input.user_integer());
        if (policy.getPolicyID(policy.getPolicyId() == policy.getPolicyId()) {
            policy.getPolicyInfo(policy.getPolicyId());
            System.out.print("\n");
        }
        else {
            System.out.println("Could Not Find Policy ID! Try again!\n");
        }
    }
}

