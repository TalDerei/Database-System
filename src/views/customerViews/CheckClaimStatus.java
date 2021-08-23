package views.customerViews;

import java.sql.*;
import objects.CustomerObjects.*;

public class CheckClaimStatus {
     /**
     * Check claim status associated with a claim
     */
    public static void check_claim_status(Connection connection) {
        /**
         * Claim object
         */
        Claim claim = new Claim();

        System.out.print("Enter Existing Claim ID to Check the Status of the Claim: ");
        claim.setClaimId(Input.user_integer());
        if (claim.getClaimID(claim.getClaimId()) == claim.getClaimId()) {
            claim.getClaimStatus(claim.getClaimId());
            System.out.print("\n"); 
        }
        else {
            System.out.println("Could Not Find Claim ID! Try again!\n");
        }
    }
}
