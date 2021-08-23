package views.customerViews;

import java.sql.*;
import manager.IOManager;
import objects.CustomerObjects.*;

public class AddClaim {
    /**
     * Add claim to existing policy
     * @param connection
     */
    public static void add_claim(Connection connection) {
        /** 
         * Claim and Policy objects 
        */
        Policy policy = new Policy();
        Claim claim = new Claim();

        System.out.print("Enter an Existing Policy ID to Start a Claim: ");
        policy.setPolicyId(Input.user_integer());
        if (policy.getPolicyID(policy.getPolicyId()) == policy.getPolicyId()) {
            claim.setClaimId(IOManager.idNumber(999999));
            claim.setClaimType(IOManager.policyPlan(1));
            System.out.println("Enter Claim Type: \n");
            System.out.println("Enter Accident [1] Wind and Hail, [2] Theft, [3] Car Wreck, [4] Health-Related, [5] Personal Injury, [6] ater Damage, [7] Fire Damage: ");
            claim.setAccident(IOManager.accident(1));
            System.out.print("Enter Items Damaged ([Y]es or [N]o): ");
            claim.setItemsDamaged(IOManager.yesOrNo());
            System.out.print("Enter Description of the Claim: ");
            claim.setDescription(IOManager.stringInput(255));
            claim.setOutcome("Pending"); 
            claim.setAdjusterNotes("NULL");
            System.out.print("Enter Claim Amount: ");
            claim.setAmount(IOManager.intInputDouble(0.00, 999999.99));
            claim.setClaimStatus("Pending");
            if (claim.insertClaim()) {
                try{
                    connection.commit();
                    System.out.println("TRANSACTION SUCCEEDED!\n");
                }
                catch (SQLException exception) {
                    System.out.println("TRANSACTION FAILED! ROLLED BACK!\n");
                }
            }
            System.out.println("DON'T FORGET TO WRITE DOWN YOUR UNIQUE CLAIM ID SO YOU DON'T FORGET IT!");
            System.out.println("YOU'LL NEED THIS TO MAKE FUTURE INQUIRIES ASSOCIATED WITH OUTSTANDING POLICY/CLAIMS!");
            System.out.println("YOUR UNIQUE CLAIM ID IS: " + claim.getClaimId() + "\n");            
        }
        else {
            System.out.println("Could Not Find Policy ID! Try again!\n");
        }
    }
}
