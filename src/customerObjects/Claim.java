package customerObjects;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import manager.DBManager;

import java.sql.Connection;

public class Claim {
    public static PreparedStatement addClaim;
    public static PreparedStatement dropClaim;
    public static PreparedStatement checkClaimID;
    public static PreparedStatement checkClaimStatus;

    public Claim() {

    }

    public Claim(int claim_id) {

    }

    /**
     * insertClaim inserts claim associated with a policy
     */
    public int insertClaim(int claim_id, String claim_type, String accident, String items_damaged, String description, String outcome, String adjuster_notes, double amount, String claim_status, int policy_id) {
        Connection conn = DBManager.getConnection();

        try {
            Claim.addClaim = conn.prepareStatement("INSERT INTO claim (CLAIM_ID, CLAIM_TYPE, ACCIDENT, ITEMS_DAMAGED, DESCRIPTION, DECISION, ADJUSTER_NOTES, AMOUNT, CLAIM_STATUS, POLICY_ID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
        }
        catch (SQLException exception) {
            System.out.println("Error with Prepared Statements!");
        }

        int success = 0;
        try {
            addClaim.setInt(1, claim_id);
            addClaim.setString(2, claim_type);
            addClaim.setString(3, accident);
            addClaim.setString(4, items_damaged);
            addClaim.setString(5, description);
            addClaim.setString(6, outcome);
            addClaim.setString(7, adjuster_notes);
            addClaim.setDouble(8, amount);
            addClaim.setString(9, claim_status);
            addClaim.setInt(10, policy_id);
            success = addClaim.executeUpdate();
            System.out.println("\nSuccessfully Added Claim to Database!\n");
        }
        catch (SQLException exception) {
            System.out.println("Failed to Add Claim to Database! Something Went Wrong, Try Again!");
        }
        return success;
    }

        /**
     * getClaimStatus checks customer's claim status
     */
    public String getClaimStatus(int claim_id) {
        Connection conn = DBManager.getConnection();

        try {
            Claim.checkClaimStatus = conn.prepareStatement("SELECT decision FROM claim WHERE claim_id = ?");
        }
        catch (SQLException exception) {
            System.out.println("Error with Prepared Statements!");
        }

        String decision = "";
        try {
            checkClaimStatus.setInt(1, claim_id);
            ResultSet resultset = checkClaimStatus.executeQuery();
            while (resultset.next()) {
                decision = resultset.getString("decision");
                System.out.println("Claim Decision Is: " + decision);
            }
                resultset.close();
        }
        catch (SQLException exception) {
            System.out.println("Claim ID Invalid! No Claim Exists!");
        }
        return decision;
    }

        /**
     * checkClaimID checks if claim ID exists in the database 
     */
    public int getClaimID(int claim_id) {
        Connection conn = DBManager.getConnection();

        try {
            Claim.checkClaimID = conn.prepareStatement("SELECT claim_id FROM claim WHERE claim_id = ?");
        }
        catch (SQLException exception) {
            System.out.println("Error with Prepared Statements!");
        }

        int id = 0;
        try {
            checkClaimID.setInt(1, claim_id);
            ResultSet resultset = checkClaimID.executeQuery();
            while (resultset.next()) {
                id = resultset.getInt("claim_id");
            }
                resultset.close();
        }
        catch (SQLException exception) {
            System.out.println("Claim ID Invalid! No Claim Exists!");
        }
        return id;
    }
    
    

}
