package views;

import java.util.Scanner;
import java.sql.Date;
import java.sql.*;
import java.text.*;

public class Adjuster_View {
    /**
     * Prepared Statments associated with adjuster interface
     */
    public static Connection connect;
    private static PreparedStatement pendingClaims;
    private static PreparedStatement checkAdjusterCustomers;
    private static PreparedStatement changeClaimType;
    private static PreparedStatement changeClaimAccident;
    private static PreparedStatement changeClaimItemsDamaged;
    private static PreparedStatement changeClaimDescription;
    private static PreparedStatement changeClaimDecision;
    private static PreparedStatement changeClaimAdjusterNotes;
    private static PreparedStatement changeClaimAmount;
    private static PreparedStatement changeClaimStatus;
    private static PreparedStatement getAllCustomerClaim;
    private static PreparedStatement getAllAdjusterCustomers;
    private static PreparedStatement adjusterAgent;
    private static PreparedStatement checkAdjusterID;
    private static PreparedStatement getClaimInformation;
    private static PreparedStatement adjusterManagesClaim;
    private static PreparedStatement claimOutsources;
    private static PreparedStatement claimCompany;
    private static PreparedStatement claimItem;
    public static PreparedStatement checkClaimID;
    public static PreparedStatement checkCustomerID;
    public static PreparedStatement checkadjuster;
    public static PreparedStatement ClaimPolicy;
    public static PreparedStatement policyOfClaim;
    public static PreparedStatement getClaimItems;
    public static PreparedStatement addCheck;
    public static PreparedStatement addACHTransfer;
    public static PreparedStatement claimPayments;
    public static PreparedStatement checkClaimCost;

    public void prepare(Connection conn) {
        /**
         * Cached Prepared Statements for adjuster interface
        */
        try {
            Adjuster_View.checkadjuster = conn.prepareStatement("SELECT adjuster_id FROM adjuster WHERE adjuster_id = ?");
            Adjuster_View.checkAdjusterCustomers = conn.prepareStatement("SELECT customer_id FROM adjuster NATURAL JOIN communicates NATURAL JOIN customer_agent WHERE adjuster_id = ?");
            Adjuster_View.pendingClaims = conn.prepareStatement("SELECT customer_id FROM customer NATURAL JOIN claim NATURAL JOIN claim_payment NATURAL JOIN policy WHERE claim_status = 'Pending'");
            Adjuster_View.checkClaimID = conn.prepareStatement("SELECT claim_id FROM claim WHERE claim_id = ?");
            Adjuster_View.checkCustomerID = conn.prepareStatement("SELECT customer_id FROM customer WHERE customer_id = ?");
            Adjuster_View.checkAdjusterID = conn.prepareStatement("SELECT adjuster_id FROM adjuster WHERE adjuster_id = ?");
            Adjuster_View.changeClaimType = conn.prepareStatement("UPDATE claim SET claim_type = ? WHERE claim_id = ?");
            Adjuster_View.changeClaimAccident = conn.prepareStatement("UPDATE claim SET accident = ? WHERE claim_id = ?");
            Adjuster_View.changeClaimItemsDamaged = conn.prepareStatement("UPDATE claim SET items_damaged = ? WHERE claim_id = ?");
            Adjuster_View.changeClaimDescription = conn.prepareStatement("UPDATE claim SET descripion = ? WHERE claim_id = ?");
            Adjuster_View.changeClaimDecision = conn.prepareStatement("UPDATE claim SET decision = ? WHERE claim_id = ?");
            Adjuster_View.changeClaimAdjusterNotes = conn.prepareStatement("UPDATE claim SET adjuster_notes = ? WHERE claim_id = ?");
            Adjuster_View.changeClaimAmount = conn.prepareStatement("UPDATE claim SET amount = ? WHERE claim_id = ?");
            Adjuster_View.changeClaimStatus = conn.prepareStatement("UPDATE claim SET claim_status = ? WHERE claim_id = ?");
            Adjuster_View.getAllCustomerClaim = conn.prepareStatement("SELECT claim_id FROM customer NATURAL JOIN claim NATURAL JOIN claim_payment NATURAL JOIN policy where customer_id = ?");
            Adjuster_View.getClaimInformation = conn.prepareStatement("SELECT * FROM claim WHERE claim_id = ?");
            Adjuster_View.getAllAdjusterCustomers = conn.prepareStatement("SELECT customer_id FROM customer NATURAL JOIN claim NATURAL JOIN claim_payment NATURAL JOIN policy where claim_id = ?");
            Adjuster_View.adjusterAgent = conn.prepareStatement("SELECT agent_id FROM communicates WHERE adjuster_id = ?");
            Adjuster_View.adjusterManagesClaim = conn.prepareStatement("SELECT claim_id FROM manages WHERE employee_id = ?");
            Adjuster_View.claimOutsources = conn.prepareStatement("INSERT INTO outsources (NAME, CLAIM_ID) VALUES (?, ?)");
            Adjuster_View.claimCompany = conn.prepareStatement("INSERT INTO company (NAME, TYPE, PHONE_NUMBER) VALUES (?, ?, ?)");
            Adjuster_View.claimItem = conn.prepareStatement("INSERT INTO item (ITEM_ID, CLAIM_ID, POLICY_ID, ITEM_TYPE, ITEM_VALUE, DESCRIPTION) VALUES (?, ?, ?, ?, ?, ?)");
            Adjuster_View.ClaimPolicy = conn.prepareStatement("SELECT policy_id FROM item (ITEM_ID, CLAIM_ID, POLICY_ID, ITEM_TYPE, ITEM_VALUE, DESCRIPTION) VALUES (?, ?, ?, ?, ?, ?)");
            Adjuster_View.policyOfClaim = conn.prepareStatement("SELECT policy_id FROM claim where claim_id = ?");
            Adjuster_View.getClaimItems = conn.prepareStatement("SELECT item_id FROM item WHERE claim_id = ?");
            Adjuster_View.addCheck = conn.prepareStatement("INSERT INTO checks (PAYMENT_ID, CHECK_NUMBER, CHECK_DATE, CLAIM_ID, BANK, ACCOUNT_NUMBER, ROUTING_NUMBER) VALUES (?, ?, ?, ?, ?, ?, ?)");
            Adjuster_View.addACHTransfer = conn.prepareStatement("INSERT INTO ach_transfer (PAYMENT_ID, ACCOUNT_NUMBER, ROUTING_NUMBER, CLAIM_ID) VALUES (?, ?, ?, ?)");
            Adjuster_View.claimPayments = conn.prepareStatement("INSERT INTO claim_payment (PAYMENT_ID, CLAIM_ID, RECIPIENT_NAME, RECIPIENT_ADDRESS, PAYMENT_AMOUNT, BANK, PAYMENT_DATE, STATUS) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
            Adjuster_View.checkClaimCost = conn.prepareStatement("SELECT amount FROM claim WHERE claim_id = ?");
        }
        catch (SQLException exception) {
            System.out.println("Error with Prepared Statements!");
        }
    }

     /** 
     * getAdjusterID checks if adjuster id is in the database 
     */
    public int getAdjusterID(int adjuster_id) {
        int id = 0;
        try {
            checkAdjusterID.setInt(1, adjuster_id);
            ResultSet resultset = checkAdjusterID.executeQuery();
            while (resultset.next()) {
                id = resultset.getInt("adjuster_id");
                System.out.println("adjuster ID is: " + id);
            }
                resultset.close();
        }
        catch (SQLException exception) {
            System.out.println("Invalid Input! Try Again!");
        }
        return id;
    }

    /**
     * getAdjusterCustomers gets customers associated with a specific adjuster
     */
    public int getAdjusterCustomers(int adjuster_id) {
        int id = 0;
        try {
            checkAdjusterCustomers.setInt(1, adjuster_id);
            ResultSet resultset = checkAdjusterCustomers.executeQuery();
            while (resultset.next()) {
                id = resultset.getInt("customer_id");
                System.out.println("Customer ID: " + id);
            }
        }
        catch (SQLException exception) {
            System.out.println("Invalid Input! Try again!");
        }
        return id;
    }

    /**
     * updateCustomerClaim updates a customers claim 
     */
    public int updateCustomerClaim(int claim_id, double typeInt, String typeString, int index) {
        int success = 0;
        if (index == 1) {
            try {
                changeClaimType.setString(1, typeString);
                changeClaimType.setInt(2, claim_id);
                success = changeClaimType.executeUpdate();
                System.out.println("CLAIM UPDATED!");
            }
            catch (SQLException exception) {
                System.out.println("Invalid Input! Try again!");
            }
        }
        else if (index == 2) {
            try {
                changeClaimAccident.setString(1, typeString);
                changeClaimAccident.setInt(2, claim_id);
                success = changeClaimAccident.executeUpdate();
                System.out.println("CLAIM UPDATED!");
            }
            catch (SQLException exception) {
                System.out.println("Invalid Input! Try Again!");
            }
        }
        else if (index == 3) {
            try {
                changeClaimItemsDamaged.setString(1, typeString);
                changeClaimItemsDamaged.setInt(2, claim_id);
                success = changeClaimItemsDamaged.executeUpdate();
                System.out.println("CLAIM UPDATED!");
            }
            catch (SQLException exception) {
                System.out.println("Invalid Input! Try Again!");
            }
        }
        else if (index == 4) {
            try {
                changeClaimDescription.setString(1, typeString);
                changeClaimDescription.setInt(2, claim_id);
                success = changeClaimDescription.executeUpdate();
                System.out.println("CLAIM UPDATED!");
            }
            catch (SQLException exception) {
                System.out.println("Invalid Input! Try Again!");
            }
        }
        else if (index == 5) {
            try {
                changeClaimDecision.setString(1, typeString);
                changeClaimDecision.setInt(2, claim_id);
                success = changeClaimDecision.executeUpdate();
                System.out.println("CLAIM UPDATED!");
            }
            catch (SQLException exception) {
                System.out.println("Invalid Input! Try Again!");
            }
        }
        else if (index == 6) {
            try {
                changeClaimAdjusterNotes.setString(1, typeString);
                changeClaimAdjusterNotes.setInt(2, claim_id);
                success = changeClaimAdjusterNotes.executeUpdate();
                System.out.println("CLAIM UPDATED!");
            }
            catch (SQLException exception) {
                System.out.println("Invalid Input! Try Again!");
            }
        }
        else if (index == 7) {
            try {
                changeClaimAmount.setDouble(1, typeInt);
                changeClaimAmount.setInt(2, claim_id);
                success = changeClaimAmount.executeUpdate();
                System.out.println("CLAIM UPDATED!");
            }
            catch (SQLException exception) {
                System.out.println("Invalid Input! Try Again!");
            }
        }
        else if (index == 8) {
            try {
                changeClaimStatus.setString(1, typeString);
                changeClaimStatus.setInt(2, claim_id);
                success = changeClaimStatus.executeUpdate();
                System.out.println("CLAIM UPDATED!");
            }
            catch (SQLException exception) {
                System.out.println("Invalid Input! Try Again!");
            }
        }
        return success;
    }

     /**
     * getAllCustomerClaimList gets all claims associated with a specific customer
     */
    public int getAllCustomerClaimList(int customer_id) {
        int id = 0;
        try {
            getAllCustomerClaim.setInt(1, customer_id);
            ResultSet resultset = getAllCustomerClaim.executeQuery();
            while (resultset.next()) {
                id = resultset.getInt("claim_id");
                System.out.println("Claim ID: " + id);
            }
                resultset.close();
        }
        catch (SQLException exception) {
            System.out.println("Invalid Input! Try again!");
        }
        return id;
    }

    /**
     * getClaimInfo returns information about a customer's existing policy 
     */
    public int getClaimInfo(int claim_id) {
        int success = 0;
        try {
            getClaimInformation.setInt(1, claim_id);
            ResultSet resultset = getClaimInformation.executeQuery();
            while (resultset.next()) {
                String claim_type = resultset.getString("claim_type");
                String accident = resultset.getString("accident");
                String items_damaged = resultset.getString("items_damaged");
                String description = resultset.getString("description");
                String descision = resultset.getString("decision");
                String adjuster_notes = resultset.getString("adjuster_notes");
                int amount = resultset.getInt("amount");
                String status = resultset.getString("claim_status");
                int policy = resultset.getInt("policy_id");
                System.out.println("Type is: " + claim_type);
                System.out.println("Accident is: " + accident);
                System.out.println("Items Damaged is: " + items_damaged);
                System.out.println("Description is: " + description);
                System.out.println("Deductible is: " + descision);
                System.out.println("Coinsurance is: " + adjuster_notes);
                System.out.println("Effective Date is is: " + amount);
                System.out.println("Expiration Date is: " + status);
                System.out.println("Policy is: " + policy);
                success = 1;
            }
                resultset.close();
        }
        catch (SQLException exception) {
            System.out.println("Invalid Input! Try again!");
        }
        return success;
    }

     /**
     * getAllCustomersClaim gets all customers associated with a specific claim
     */
    public int getAllCustomersClaim(int claim_id) {
        int id = 0;
        try {
            getAllAdjusterCustomers.setInt(1, claim_id);
            ResultSet resultset = getAllAdjusterCustomers.executeQuery();
            while (resultset.next()) {
                id = resultset.getInt("customer_id");
                System.out.println("Customer ID: " + id);
            }
                resultset.close();
        }
        catch (SQLException exception) {
            System.out.println("Invalid Input! Try again!");
        }
        return id;
    }

    /**
     * getAgentAdjuster gets all adjusters associated with a specific agent
     */
    public int getAgentAdjuster(int adjuster_id) {
        int id = 0;
        try {
            adjusterAgent.setInt(1, adjuster_id);
            ResultSet resultset = adjusterAgent.executeQuery();
            while (resultset.next()) {
                id = resultset.getInt("agent_id");
                System.out.println("Agent ID: " + id);
            }
                resultset.close();
        }
        catch (SQLException exception) {
            System.out.println("Invalid Input! Try again!");
        }
        return id;
    }

    /**
     * getAdjusterManages gets all adjusters associated with a specific agent
     */
    public int getAdjusterManages(int adjuster_id) {
        int id = 0;
        try {
            adjusterManagesClaim.setInt(1, adjuster_id);
            ResultSet resultset = adjusterManagesClaim.executeQuery();
            while (resultset.next()) {
                id = resultset.getInt("claim_id");
                System.out.println("claim_id is: " + id);
            }
                resultset.close();
        }
        catch (SQLException exception) {
            System.out.println("Invalid Input! Try again!");
        }
        return id;
    }

    /**
     * adjusterOutsourcing inserts new oursourcing company associated with a claim 
     */
    public int adjusterOutsourcing(int claim_id, String name) {
        int success = 0;
        try {
            claimOutsources.setString(1, name);
            claimOutsources.setInt(2, claim_id);
            success = claimOutsources.executeUpdate();
        }
        catch (SQLException exception) {
            System.out.println("Invalid Input!");
        }
        return success;
    }

    /**
     * adjusterCompany inserts new information about a company associated with a claim
     */
    public int adjusterCompany(String name, String type, String phone_number) {
        int success = 0;
        try {
            claimCompany.setString(1, name);
            claimCompany.setString(2, type);
            claimCompany.setString(3, phone_number);
            success = claimCompany.executeUpdate();
        }
        catch (SQLException exception) {
            System.out.println("Invalid Input! Try again!");
        }
        return success;
    }

    /**
     * insertClaimItem adds an item to an existing claim
     */
    public int insertClaimItem(int item_id, int claim_id, int policy_id, String item_type, double item_value, String description) {
        int success = 0;
        try {
            claimItem.setInt(1, item_id);
            claimItem.setInt(2, claim_id);
            claimItem.setInt(3, policy_id);
            claimItem.setString(4, item_type);
            claimItem.setDouble(5, item_value);
            claimItem.setString(6, description);
            success = claimItem.executeUpdate();
        }
        catch (SQLException exception) {
            System.out.println("Invalid Input! Try again!");
        }
        return success;
    }
    
    /**
     * Input checking constraints on integer input
     */
    public int user_integer() {
        Scanner input = new Scanner(System.in);
            boolean conditional = true;
            while (conditional) {
                boolean condition = input.hasNextInt();
                if (condition) {
                    int user_input = input.nextInt();
                    conditional = false; 
                    return user_input; 
                }
                else {
                    System.out.println("Invalid Input!");
                    conditional = true;
                    input.next();
                }
            }
        return 0;
    }
    
     /**
     * Input checking constraints on string input
     */
    public String user_string() {
        Scanner input = new Scanner(System.in);
            boolean conditional = true;
            while (conditional) {
                boolean condition = input.hasNextInt();
                if (!condition) {
                    String user_input = input.nextLine();
                    conditional = false; 
                    return user_input; 
                }
                else {
                    System.out.println("Invalid Input! Try again!");
                    conditional = true;
                    input.next();
                }
            }
        return "0";
    }

    /**
     * getPendingClaim gets customers with pending claims
     */
    public int getPendingClaim() {
        int customer_id = 0;
        try {
            ResultSet resultset = pendingClaims.executeQuery();
            while (resultset.next()) {
                customer_id = resultset.getInt("customer_id");
                System.out.println("customer with pending claim: " + customer_id);
            }
                resultset.close();
        }
        catch (SQLException exception) {
            System.out.println("Invalid Input! Try again!");
        }
        return customer_id;
    }

    /**
     * checkClaimID checks if claim id is in the database when user attempts to add a policy
     */
    public int getClaimID(int claim_id) {
        int id = 0;
        try {
            checkClaimID.setInt(1, claim_id);
            ResultSet resultset = checkClaimID.executeQuery();
            while (resultset.next()) {
                id = resultset.getInt("claim_id");
                System.out.println("Claim ID: " + id);
            }
                resultset.close();
        }
        catch (SQLException exception) {
            System.out.println("Invalid Input! Try again!");
        }
        return id;
    }

    /**
     * getCustomerID checks if customer id is in the database when user attempts to add a policy
     */
    public int getCustomerID(int customer_id) {
        int id = 0;
        try {
            checkCustomerID.setInt(1, customer_id);
            ResultSet resultset = checkCustomerID.executeQuery();
            while (resultset.next()) {
                id = resultset.getInt("customer_id");
                System.out.println("Customer ID: " + id);
            }
            try {
                resultset.close();
            }
            catch (SQLException exception) {
                System.out.println("Cannot close resultset!");
            }
        }
        catch (SQLException exception) {
            System.out.println("Invalid Input! Try again!");
        }
        return id;
    }

    /**
     * getPolicyClaim gets policy associated with a particular claim
     */
    public int getPolicyClaim(int claim_id) {
        int id = 0;
        try {
            policyOfClaim.setInt(1, claim_id);
            ResultSet resultset = policyOfClaim.executeQuery();
            while (resultset.next()) {
                id = resultset.getInt("policy_id");
            }
                resultset.close();
        }
        catch (SQLException exception) {
            System.out.println("Invalid Input! Try again!");
        }
        return id;
    }

    /**
     * retrieveClaimItems gets all items associated with a particular claim
     */
    /**
     * checkClaimID checks if claim id is in the database when user attempts to add a policy
     */
    public int retrieveClaimItems(int claim_id) {
        int id = 0;
        try {
            getClaimItems.setInt(1, claim_id);
            ResultSet resultset = getClaimItems.executeQuery();
            while (resultset.next()) {
                id = resultset.getInt("item_id");
                System.out.println("ITEM ID: " + id);
            }
                resultset.close();
        }
        catch (SQLException exception) {
            System.out.println("Invalid Input! Try again!");
        }
        return id;
    }
    /**
     * getPolicyCost checks policy cost associated with a customer's policy
     */
    public int getClaimCost(int claim_id) {
        int cost = 0;
        try {
            checkClaimCost.setInt(1, claim_id);
            ResultSet resultset = checkClaimCost.executeQuery();
            while (resultset.next()) {
                cost = resultset.getInt("amount");
                System.out.println("Claim Amount is: " + cost);
            }
            try {
                resultset.close();
            }
            catch (SQLException exception) {
                System.out.println("Cannot close resultset!");
            }
        }
        catch (SQLException exception) {
            System.out.println("Failed to Retrieve Policy Cost! Try Again!");
        }
        return cost;
    }
    /**
     * validateDate check's the format of date data types
     */
    public Boolean validateDate(String date_of_birth) {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            format.parse(date_of_birth);
        } catch (ParseException exception) {
            System.out.println("date invalid. Please try again!");
            return true;
        }
        return false;
    }

    /**
     * makeClaimPayment allows user to make claim payment
     */
    public int makeClaimPayment(int payment_id, int claim_id, String recipient_name, String recipient_address, double payment_amount, String bank, String payment_date, String status) {
        int success = 0;
        try {
            claimPayments.setInt(1, payment_id);
            claimPayments.setInt(2, claim_id);
            claimPayments.setString(3, recipient_name);
            claimPayments.setString(4, recipient_address);
            claimPayments.setDouble(5, payment_amount);
            claimPayments.setString(6, bank);
            claimPayments.setDate(7, Date.valueOf(payment_date));
            claimPayments.setString(8, status);
            success = claimPayments.executeUpdate();
        }
        catch (SQLException exception) {
            System.out.println("Invalid Input! Try Again!");
        }
        return success;
    }

    /**
     * checkPayment allows adjuster to make claim payment in the form of a check
     */
    public int checkPayment(int payment_id, int check_number, String check_date, int claim_id, String bank, long account_number, long routing_number) {
        int success = 0;
        try {
            addCheck.setInt(1, payment_id);
            addCheck.setInt(2, check_number);
            addCheck.setDate(3, Date.valueOf(check_date));
            addCheck.setInt(4, claim_id);
            addCheck.setString(5, bank);
            addCheck.setLong(6, account_number);
            addCheck.setLong(7, routing_number);
            success = addCheck.executeUpdate();
        }
        catch (SQLException exception) {
            System.out.println("Invalid Input! Try Again!");
        }
        return success;
    }

    /**
     * makeACHTransfer allows adjuster to make claim payment in the form of ACH transfer
     */
    public int makeACHTransfer(int payment_id, long account_number, long routing_number, int claim_id) {
        int success = 0;
        try {
            addACHTransfer.setInt(1, payment_id);
            addACHTransfer.setLong(2, account_number);
            addACHTransfer.setLong(3, routing_number);
            addACHTransfer.setInt(4, claim_id);
            success = addACHTransfer.executeUpdate();
        }
        catch (SQLException exception) {
            System.out.println("Invalid Input! Try Again!");
        }
        return success;
    }
}
