package objects.CustomerObjects;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import manager.DBManager;
import java.sql.Connection;

public class Claim {
    /**
     * Prepared Statments 
     */
    public static PreparedStatement addClaim;
    public static PreparedStatement dropClaim;
    public static PreparedStatement checkClaimID;
    public static PreparedStatement checkClaimStatus;

    /**
     * Claim parameters
     */
    private int claim_id;
    private String claim_type;
    private String accident;
    private String items_damaged;
    private String description;
    private String outcome;
    private String adjuster_notes;
    private Double amount;
    private String claim_status;
    private int policy_id;

    /**
     * Constructor for creating new Claim objects
     */
    public Claim() {
        
    }

    /**
     * Constructor to create Claim objects
     * @param claim_id
     * @param claim_type
     * @param accident
     * @param items_damaged
     * @param description
     * @param outcome
     * @param adjuster_notes
     * @param amount
     * @param claim_status
     * @param policy_id
     */
    public Claim(int claim_id, String claim_type, String accident, String items_damaged, String description, String outcome, String adjuster_notes, double amount, String claim_status, int policy_id) {
        this.claim_id = claim_id;
        this.claim_type = claim_type;
        this.accident = accident;
        this.items_damaged = items_damaged;
        this.description = description;
        this.outcome = outcome;
        this.adjuster_notes = adjuster_notes;
        this.amount = amount;
        this.claim_status = claim_status;
        this.policy_id = policy_id;

    }

    /**
     * Issue new claim associated with a policy
     * @return Whether inserting claim was successful
     */
    public Boolean insertClaim() {
        Connection conn = DBManager.getConnection();
        
        try {
            Claim.addClaim = conn.prepareStatement("INSERT INTO claim (CLAIM_ID, CLAIM_TYPE, ACCIDENT, ITEMS_DAMAGED, DESCRIPTION, DECISION, ADJUSTER_NOTES, AMOUNT, CLAIM_STATUS, POLICY_ID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
        }
        catch (SQLException exception) {
            System.out.println("Error with Prepared Statements!");
        }

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
            addClaim.executeUpdate();
            System.out.println("\nSuccessfully Added Claim to Database!\n");
            return true;
        }
        catch (SQLException exception) {
            System.out.println("Failed to Add Claim to Database! Something Went Wrong, Try Again!");
        }
        return false;
    }

    /**
     * Check customer's claim status
     * @param claim_id
     * @return Claim decision
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
     * Check if claim ID exists in the database 
     * @param claim_id
     * @return Claim ID
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

    /**
     * Getters and Setter functions
     */
    public void setClaimId(int claim_id) {
        this.claim_id = claim_id;
    }

    public void setClaimType(String claim_type) {
        this.claim_type = claim_type;
    }

    public void setAccident(String accident) {
        this.accident = accident;
    }

    public void setItemsDamaged(String items_damaged) {
        this.items_damaged = items_damaged;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setOutcome(String outcome) {
        this.outcome = outcome;
    }

    public void setAdjusterNotes(String adjuster_notes) {
        this.adjuster_notes = adjuster_notes;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
    
    public void setClaimStatus(String claim_status) {
        this.claim_status = claim_status;
    }

    public void setPolicyId(int policy_id) {
        this.policy_id = policy_id;
    }

    public int getClaimId() {
        return claim_id;
    }

    public String getClaimType() {
        return claim_type;
    }

    public String getAccident() {
        return accident;
    }

    public String getItemsDamaged() {
        return items_damaged;
    }

    public String getDescription() {
        return description;
    }

    public String getOutcome() {
        return outcome;
    }

    public String getAdjusterNotes() {
        return adjuster_notes;
    }

    public Double getAmount() {
        return amount;
    }
    
    public String getClaimStatus() {
        return claim_status;
    }

    public int getPolicyId() {
        return policy_id;
    }
}
