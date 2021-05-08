/**
 * Course: CSE 341
 * Semester and Year: Spring 2021
 * Assignment: Database Systems
 * Author: Derei, Tal
 * User ID: tad222
 */

package insurance;

import java.util.Scanner;
import java.sql.Date;
import java.util.Calendar;
import java.sql.*;
import java.util.*;
import java.text.*;
import java.util.InputMismatchException;
import insurance.IOManager;

public class Adjuster_Interface {
     /**
     * Prepared Statments associated with adjuster interface
     */
    public Connection connect;
    private PreparedStatement pendingClaims;
    private PreparedStatement checkAdjusterCustomers;
    private PreparedStatement changeClaimType;
    private PreparedStatement changeClaimAccident;
    private PreparedStatement changeClaimItemsDamaged;
    private PreparedStatement changeClaimDescription;
    private PreparedStatement changeClaimDecision;
    private PreparedStatement changeClaimAdjusterNotes;
    private PreparedStatement changeClaimAmount;
    private PreparedStatement changeClaimStatus;
    private PreparedStatement getAllCustomerClaim;
    private PreparedStatement getAllAdjusterCustomers;
    private PreparedStatement adjusterAgent;
    private PreparedStatement checkAdjusterID;
    private PreparedStatement getClaimInformation;
    private PreparedStatement adjusterManagesClaim;
    private PreparedStatement claimOutsources;
    private PreparedStatement claimCompany;
    private PreparedStatement claimItem;
    public PreparedStatement checkClaimID;
    public PreparedStatement checkCustomerID;
    public PreparedStatement checkadjuster;
    public PreparedStatement ClaimPolicy;
    public PreparedStatement policyOfClaim;
    public PreparedStatement getClaimItems;
    public PreparedStatement addCheck;
    public PreparedStatement addACHTransfer;
    public PreparedStatement claimPayments;
    public PreparedStatement checkClaimCost;

    /**
     * Establish a connection to the Oracle database
     */
    public static Adjuster_Interface connect_database() {
        String username = "";
        String password = "";
        try {
            Scanner input = new Scanner(System.in);
            System.out.print("enter Oracle user id: ");   
            username = input.nextLine();
            System.out.print("enter Oracle password for " + username + ": ");
            password = input.nextLine();    
        }
        catch (InputMismatchException inputMismatchException) {
            System.out.println("Wrong credentials! Try Again!");
            return null;
        }
        Adjuster_Interface database = new Adjuster_Interface();
        try (Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@edgar1.cse.lehigh.edu:1521:cse241",username,password);
        ) {
            System.out.println("Connection to the oracle database succeeded!\n");
            database.connect = connection;      
            Adjuster(database); 
        } catch (SQLException exception) {
            System.out.println("connection to the oracle database failed! Try again!");
            return connect_database();
        }
        return database;
    }

    /**
     * Command-line interface for adjusters
     */
    public static void Adjuster(Adjuster_Interface database) {
        /**
          * Cached Prepared Statements for adjuster interface
        */
        try {
            database.checkadjuster = database.connect.prepareStatement("SELECT adjuster_id FROM adjuster WHERE adjuster_id = ?");
            database.checkAdjusterCustomers = database.connect.prepareStatement("SELECT customer_id FROM adjuster NATURAL JOIN communicates NATURAL JOIN customer_agent WHERE adjuster_id = ?");
            database.pendingClaims = database.connect.prepareStatement("SELECT customer_id FROM customer NATURAL JOIN claim NATURAL JOIN claim_payment NATURAL JOIN policy WHERE claim_status = 'Pending'");
            database.checkClaimID = database.connect.prepareStatement("SELECT claim_id FROM claim WHERE claim_id = ?");
            database.checkCustomerID = database.connect.prepareStatement("SELECT customer_id FROM customer WHERE customer_id = ?");
            database.checkAdjusterID = database.connect.prepareStatement("SELECT adjuster_id FROM adjuster WHERE adjuster_id = ?");
            database.changeClaimType = database.connect.prepareStatement("UPDATE claim SET claim_type = ? WHERE claim_id = ?");
            database.changeClaimAccident = database.connect.prepareStatement("UPDATE claim SET accident = ? WHERE claim_id = ?");
            database.changeClaimItemsDamaged = database.connect.prepareStatement("UPDATE claim SET items_damaged = ? WHERE claim_id = ?");
            database.changeClaimDescription = database.connect.prepareStatement("UPDATE claim SET descripion = ? WHERE claim_id = ?");
            database.changeClaimDecision = database.connect.prepareStatement("UPDATE claim SET decision = ? WHERE claim_id = ?");
            database.changeClaimAdjusterNotes = database.connect.prepareStatement("UPDATE claim SET adjuster_notes = ? WHERE claim_id = ?");
            database.changeClaimAmount = database.connect.prepareStatement("UPDATE claim SET amount = ? WHERE claim_id = ?");
            database.changeClaimStatus = database.connect.prepareStatement("UPDATE claim SET claim_status = ? WHERE claim_id = ?");
            database.getAllCustomerClaim = database.connect.prepareStatement("SELECT claim_id FROM customer NATURAL JOIN claim NATURAL JOIN claim_payment NATURAL JOIN policy where customer_id = ?");
            database.getClaimInformation = database.connect.prepareStatement("SELECT * FROM claim WHERE claim_id = ?");
            database.getAllAdjusterCustomers = database.connect.prepareStatement("SELECT customer_id FROM customer NATURAL JOIN claim NATURAL JOIN claim_payment NATURAL JOIN policy where claim_id = ?");
            database.adjusterAgent = database.connect.prepareStatement("SELECT agent_id FROM communicates WHERE adjuster_id = ?");
            database.adjusterManagesClaim = database.connect.prepareStatement("SELECT claim_id FROM manages WHERE employee_id = ?");
            database.claimOutsources = database.connect.prepareStatement("INSERT INTO outsources (NAME, CLAIM_ID) VALUES (?, ?)");
            database.claimCompany = database.connect.prepareStatement("INSERT INTO company (NAME, TYPE, PHONE_NUMBER) VALUES (?, ?, ?)");
            database.claimItem = database.connect.prepareStatement("INSERT INTO item (ITEM_ID, CLAIM_ID, POLICY_ID, ITEM_TYPE, ITEM_VALUE, DESCRIPTION) VALUES (?, ?, ?, ?, ?, ?)");
            database.ClaimPolicy = database.connect.prepareStatement("SELECT policy_id FROM item (ITEM_ID, CLAIM_ID, POLICY_ID, ITEM_TYPE, ITEM_VALUE, DESCRIPTION) VALUES (?, ?, ?, ?, ?, ?)");
            database.policyOfClaim = database.connect.prepareStatement("SELECT policy_id FROM claim where claim_id = ?");
            database.getClaimItems = database.connect.prepareStatement("SELECT item_id FROM item WHERE claim_id = ?");
            database.addCheck = database.connect.prepareStatement("INSERT INTO checks (PAYMENT_ID, CHECK_NUMBER, CHECK_DATE, CLAIM_ID, BANK, ACCOUNT_NUMBER, ROUTING_NUMBER) VALUES (?, ?, ?, ?, ?, ?, ?)");
            database.addACHTransfer = database.connect.prepareStatement("INSERT INTO ach_transfer (PAYMENT_ID, ACCOUNT_NUMBER, ROUTING_NUMBER, CLAIM_ID) VALUES (?, ?, ?, ?)");
            database.claimPayments = database.connect.prepareStatement("INSERT INTO claim_payment (PAYMENT_ID, CLAIM_ID, RECIPIENT_NAME, RECIPIENT_ADDRESS, PAYMENT_AMOUNT, BANK, PAYMENT_DATE, STATUS) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
            database.checkClaimCost = database.connect.prepareStatement("SELECT amount FROM claim WHERE claim_id = ?");
        }
        catch (SQLException exception) {
            System.out.println("Error with Prepared Statements!");
        }

        /**
         * Command-line interface for adjusters
         */
        Scanner input = new Scanner(System.in);
        int menue_selection;
        int success; 
        while (true) {
            System.out.println("--------------------------------------------------------------");
            System.out.println("[1] Get all customers associated with an adjuster\n");
            System.out.println("[2] Identify claims that have not been serviced recently\n");
            System.out.println("[3] Update a customer's claim\n");
            System.out.println("[4] Get all claims associated with a particular customer\n"); 
            System.out.println("[5] Get claim information associated with a customer's claim\n"); 
            System.out.println("[6] Get all customers (and dependants) associated with a particular claim\n"); 
            System.out.println("[7] Get all agents an adjuster communicates with\n");
            System.out.println("[8] Get all claims an adjuster manages\n");
            System.out.println("[9] Assign external remediation firms or body shops to claims\n");
            System.out.println("[10] Add an item to an existing claim request\n");
            System.out.println("[11] Get All Items in a Claim\n");
            System.out.println("[12] Make a Claim Payment to a Particular Customer on a Policy\n");
            System.out.println("[13] Exit!");
            System.out.println("--------------------------------------------------------------");
            System.out.print("\nSelect From the List of Options Above: ");
            boolean conditional = input.hasNextInt();
            if (conditional) {
                menue_selection = input.nextInt();
                if (menue_selection == 1) {
                    System.out.print("\nEnter 6-Digit Adjuster ID to Retrieve All Customers Managed By That Adjuster: ");
                    int adjuster_id = user_integer();
                    success = database.getAdjusterID(adjuster_id);
                    if (adjuster_id == success) {
                        database.getAdjusterCustomers(adjuster_id);
                    }
                }
                else if (menue_selection == 2) {
                    database.getPendingClaim();
                }
                else if (menue_selection == 3) {
                    int index;
                    int intPlaceHolder = 0;
                    String stringPlaceHolder = "";
                    System.out.print("Please Enter an Existing 6-digit Claim ID To Update the Claim: ");
                    int claim_id = user_integer();
                    success = database.getClaimID(claim_id);
                    if (claim_id == success) {
                        System.out.println("");
                        System.out.println("\n--------------------------------------------------------------");
                        System.out.println("[1] Change Accident Associated With Customer Claim");
                        System.out.println("[2] Change Items Damaged Associated With Customer Claim");
                        System.out.println("[3] Change Description Associated With Customer Claim");
                        System.out.println("[4] Change Decision Associated with customer claim");
                        System.out.println("[5] Change Adjuster Notes Associated With Customer Claim");
                        System.out.println("[6] Change Amount Associated With Customer Claim");
                        System.out.println("[7] Change Status Associated With Customer Claim");
                        System.out.println("--------------------------------------------------------------\n");
                        System.out.print("Please Choose What Part of the Claim You Would Like to Update: ");
                        menue_selection = input.nextInt();
                        if (menue_selection == 1) {
                            index = 2;
                            System.out.println("Update Accident: [1] Wind and Hail, [2] Theft, [3] Car Wreck, [4] Health-Related, [5] Personal Injury, [6] ater Damage, [7] Fire Damage ");
                            String accident = IOManager.accident(1);
                            database.updateCustomerClaim(claim_id, intPlaceHolder, accident, index);
                        }
                        else if (menue_selection == 2) {
                            index = 3;
                            System.out.println("Update Items Damaged: ([Y]es or [N]o)");
                            String items_damaged = IOManager.yesOrNo();
                            database.updateCustomerClaim(claim_id, intPlaceHolder, items_damaged, index);
                        }
                        else if (menue_selection == 3) {
                            index = 4;
                            System.out.println("Update Description: ");
                            String description = IOManager.stringInput(255);
                            database.updateCustomerClaim(claim_id, intPlaceHolder, description, index);
                        }
                        else if (menue_selection == 4) {
                            index = 5;
                            System.out.println("Update decision: [1] Pending, [2] Active, [3] Inactive ");
                            String decision = IOManager.policyStatus(1);
                            database.updateCustomerClaim(claim_id, intPlaceHolder, decision, index);
                        }
                        else if (menue_selection == 5) {
                            index = 6;
                            System.out.println("Update Adjuster Notes: ");
                            String adjuster_notes = IOManager.stringInput(255);
                            database.updateCustomerClaim(claim_id, intPlaceHolder, adjuster_notes, index);
                        }
                        else if (menue_selection == 6) {
                            index = 7;
                            System.out.println("Update Amount: ");
                            double amount = IOManager.intInputDouble(0.00, 999999.99);
                            database.updateCustomerClaim(claim_id, amount, stringPlaceHolder, index);
                        }
                        else if (menue_selection == 7) {
                            index = 8;
                            System.out.println("Update Status: [1] Pending, [2] Active, [3] Inactive");
                            String status = IOManager.policyStatus(1);
                            database.updateCustomerClaim(claim_id, intPlaceHolder, status, index);
                        }
                        else {
                            System.out.println("Invalid Claim ID! Try Again!");
                        }
                    }
                    else {
                        System.out.println("Invalid Claim ID!");
                    }
                }
                else if (menue_selection == 4) {
                    System.out.print("Enter Existing Customer ID: ");
                    int customer_id = user_integer();
                    success = database.getCustomerID(customer_id);
                    if (customer_id == success) {
                        database.getAllCustomerClaimList(customer_id);
                    }
                }
                else if (menue_selection == 5) {
                    System.out.print("Enter Existing Claim ID: ");
                    int claim_id = user_integer();
                    success = database.getClaimID(claim_id);
                    if (claim_id == success) {
                        success = database.getClaimInfo(claim_id);
                    }
                }
                else if (menue_selection == 6) {
                    System.out.print("Enter Existing Claim ID: ");
                    int claim_id = user_integer();
                    success = database.getClaimID(claim_id);
                    if (claim_id == success) { 
                        success = database.getAllCustomersClaim(claim_id);
                    }
                }
                else if (menue_selection == 7) {
                    System.out.print("Enter Existing Adjuster ID: ");
                    int adjuster_id = user_integer();
                    success = database.getAdjusterID(adjuster_id);
                    if (adjuster_id == success) { 
                        success = database.getAgentAdjuster(adjuster_id);
                    }
                }
                else if (menue_selection == 8) {
                    System.out.print("Enter Existing Adjuster ID: ");
                    int adjuster_id = user_integer();
                    success = database.getAdjusterID(adjuster_id);
                    if (adjuster_id == success) { 
                        success = database.getAdjusterManages(adjuster_id);
                    }
                }
                else if (menue_selection == 9) {
                    System.out.print("Enter Existing Claim ID: ");
                    int claim_id = user_integer();
                    success = database.getClaimID(claim_id);
                    if (claim_id == success) { 
                        System.out.print("Company Name: ");
                        String name = IOManager.stringInputWithoutNumbers(30);
                        System.out.println("Enter Company Type: [1] Single, [2] Family ");
                        String type = IOManager.policyType(1);
                        System.out.print("Company Contact Information (Phone Number): ");
                        String phone_number = IOManager.validPhoneNumber(10);
                        int success2 = database.adjusterCompany(name, type, phone_number);
                        int success1 = database.adjusterOutsourcing(claim_id, name);
                        try{
                            if (success1 + success2 == 2) {
                                database.connect.commit();
                                System.out.println("TRANSACTION SUCCEEDED!\n");
                            }
                        }
                        catch (SQLException exception) {
                        }
                    }
                }
                else if (menue_selection == 10) {
                    System.out.print("Enter Existing Claim ID: ");
                    int claim_id = user_integer();
                    success = database.getClaimID(claim_id);
                    if (claim_id == success) {
                        int item_id = IOManager.idNumber(999999);
                        int policy_id = database.getPolicyClaim(claim_id);
                        System.out.print("Company Item Type: ");
                        String item_type = IOManager.stringInputWithoutNumbers(20);
                        System.out.print("Company Item Value: ");
                        double item_value = IOManager.intInputDouble(0.00, 999999.99);
                        System.out.print("Company Item Description: ");
                        String description = IOManager.stringInput(255);
                        database.insertClaimItem(item_id, claim_id, policy_id, item_type, item_value, description);
                    }
                }
                else if (menue_selection == 11) {
                    System.out.print("Enter Existing Claim ID: ");
                    int claim_id = user_integer();
                    success = database.getClaimID(claim_id);
                    if (claim_id == success) {
                        database.retrieveClaimItems(claim_id);
                    }
                }
                else if (menue_selection == 12) {
                    System.out.print("Enter Existing Claim ID to Make a Payment: ");
                    int claim_id = user_integer();
                    int success_value = database.getClaimID(claim_id);
                    if (success_value == claim_id) {
                        double payment_amount = database.getClaimCost(claim_id);
                        if (payment_amount != 0) {
                            System.out.println("How Would You Like To Pay? \n");
                            System.out.println("[1] Checks\n");
                            System.out.println("[2] ACH Transfer\n");
                            while (true) {
                                conditional = input.hasNextInt();
                                if (conditional) {
                                    menue_selection = input.nextInt();
                                    if (menue_selection == 1) {
                                        int payment_id = IOManager.idNumber(999999);
                                        System.out.print("Enter Claim Holder's Name: ");
                                        String recipient_name = IOManager.stringInputWithoutNumbers(30);
                                        System.out.print("Enter Policy Holders Address: ");
                                        String recipient_address = IOManager.stringInput(50);
                                        System.out.println("Enter Bank: [1] BofA, [2] Citigroup, [3] Chase, [4] Wells Fargo");
                                        String bank = IOManager.bank(1);
                                        System.out.println("Enter Claim Payment Date");
                                        boolean valid_payment_date = true;
                                        String payment_date = "";
                                        while (valid_payment_date) {
                                            payment_date = input.next();
                                            valid_payment_date = validateDate(payment_date);
                                        } 
                                        System.out.println("Enter Status: [1] Payed, [2] Not Payed");
                                        String status = IOManager.ClaimStatus(1);
                                        int success1 = database.makeClaimPayment(payment_id, claim_id, recipient_name, recipient_address, payment_amount, bank, payment_date, status);
                                        int check_number = IOManager.idNumber(999999);
                                        String check_date = payment_date;
                                        System.out.println("Enter Account Number: ");
                                        long account_number = IOManager.intInputLong(12);
                                        System.out.println("Enter Routing Number: ");
                                        long routing_number = IOManager.intInputLong(9);
                                        int success2 = database.checkPayment(payment_id, check_number, check_date, claim_id, bank, account_number, routing_number);
                                        try{
                                            if (success1 + success2 == 2) {
                                                database.connect.commit();
                                                System.out.println("Have A Nice Day!\n");
                                                System.exit(0);
                                            }
                                        }
                                        catch (SQLException exception) {
                                            System.out.print("TRANSACTION SUCCEEDED!\n");
                                            System.out.println("Have A Nice Day!\n");
                                            System.exit(0);
                                        }
                                    }
                                    else if (menue_selection == 2) {
                                        int payment_id = IOManager.idNumber(999999);
                                        System.out.print("Enter Claim Holder's Name: ");
                                        String recipient_name = IOManager.stringInputWithoutNumbers(30);
                                        System.out.print("Enter Policy Holders Address: ");
                                        String recipient_address = IOManager.stringInput(50);
                                        System.out.println("Enter Bank: [1] BofA, [2] Citigroup, [3] Chase, [4] Wells Fargo");
                                        String bank = IOManager.bank(1);
                                        System.out.println("Enter Claim Payment Date");
                                        boolean valid_payment_date = true;
                                        String payment_date = "";
                                        while (valid_payment_date) {
                                            payment_date = input.next();
                                            valid_payment_date = validateDate(payment_date);
                                        } 
                                        System.out.println("Enter Status: [1] Payed, [2] Not Payed");
                                        String status = IOManager.ClaimStatus(1);
                                        int success1 = database.makeClaimPayment(payment_id, claim_id, recipient_name, recipient_address, payment_amount, bank, payment_date, status);
                                        System.out.println("Enter Account Number: ");
                                        long account_number = IOManager.intInputLong(12);
                                        System.out.println("Enter Routing Number: ");
                                        long routing_number = IOManager.intInputLong(9);
                                        int success2 = database.makeACHTransfer(payment_id, account_number, routing_number, claim_id);
                                        try{
                                            if (success1 + success2 == 2) {
                                                database.connect.commit();
                                                System.out.println("Have A Nice Day!\n");
                                                System.exit(0);
                                            }
                                        }
                                        catch (SQLException exception) {
                                            System.out.print("TRANSACTION SUCCEEDED!\n");
                                            System.out.println("Have A Nice Day!\n");
                                            System.exit(0);
                                        }
                                    }
                                    else {
                                        System.out.println("Invalid input! Try again!");
                                    }
                                }
                            }   
                        }
                        else {
                            System.out.println("Invalid Input! Claim ID Doesn't Seem to Exist!");
                        }
                    }
                    else {
                        System.out.println("Invalid Input! Claim ID Doesn't Seem to Exist!");
                    }
                }
                else if (menue_selection == 13) {
                    System.exit(0);
                }
            }
            else {
                System.out.println("Invalid Input! Try again!\n");
                input.nextLine();
            }
        }
        
    }

/**
 * FUNCTIONS ASSOCIATED WITH ADJUSTER INTERFACE
 */
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
    public static int user_integer() {
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
    public static String user_string() {
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
    public static Boolean validateDate(String date_of_birth) {
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

