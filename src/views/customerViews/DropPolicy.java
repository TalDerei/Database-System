package views.customerViews;

import java.sql.*;
import objects.CustomerObjects.*;

public class DropPolicy {
    /**
     * Drop policy
     */
    public static void drop_policy(Connection connection) {
        /** 
         * Policy object
         */
        Policy policy = new Policy();

        System.out.print("Enter an existing policy ID to drop the policy: ");
        policy.setPolicyId(Input.user_integer());
        if (policy.getPolicyID(policy.getPolicyId()) == policy.getPolicyId()) {
            System.out.println("Successfully Deleted Policy!\n");
        }
        else {
            System.out.println("Could Not Find Policy ID! Try again!\n");
        }
    }
}
