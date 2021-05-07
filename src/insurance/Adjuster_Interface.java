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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.sql.*;
import java.util.*;
import java.text.*;

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
            connection.setAutoCommit(false);
            database.connect = connection;          
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
          * Prepared Statements for adjuster interface
        */
        try {
            database.checkadjuster = database.connect.prepareStatement("SELECT adjuster_id FROM adjuster WHERE adjuster_id = ?");
            database.checkAdjusterCustomers = database.connect.prepareStatement("SELECT customer_id FROM adjuster NATURAL JOIN communicates NATURAL JOIN customer_agent WHERE adjuster_id = ?");
            database.pendingClaims = database.connect.prepareStatement("SELECT customer_id FROM customer NATURAL JOIN claim NATURAL JOIN claim_payment WHERE status = 'pending'");
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
            database.getAllCustomerClaim = database.connect.prepareStatement("SELECT claim_id FROM claim WHERE customer_id = ?");
            database.getClaimInformation = database.connect.prepareStatement("SELECT * FROM claim WHERE claim_id = ?");
            database.getAllAdjusterCustomers = database.connect.prepareStatement("SELECT customer_id FROM claim WHERE claim_id = ?");
            database.adjusterAgent = database.connect.prepareStatement("SELECT agent_id FROM communicates WHERE adjuster_id = ?");
            database.adjusterManagesClaim = database.connect.prepareStatement("SELECT claim_id FROM manages WHERE employee_id = ?");
            database.claimOutsources = database.connect.prepareStatement("INSERT INTO outsources (NAME, CLAIM_ID) VALUES (?, ?)");
            database.claimCompany = database.connect.prepareStatement("INSERT INTO company (NAME, TYPE, PHONE_NUMBER) VALUES (?, ?, ?)");
            database.claimItem = database.connect.prepareStatement("INSERT INTO item (ITEM_ID, CLAIM_ID, POLICY_ID, ITEM_TYPE, ITEM_VALUE, DESCRIPTION) VALUES (?, ?, ?, ?, ?, ?)");
            database.ClaimPolicy = database.connect.prepareStatement("SELECT policy_id from item (ITEM_ID, CLAIM_ID, POLICY_ID, ITEM_TYPE, ITEM_VALUE, DESCRIPTION) VALUES (?, ?, ?, ?, ?, ?)");
        }
        catch (SQLException exception) {
            System.out.println("Error with Prepared Statements!");
        }

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
        System.out.println("[12] Remove an item from an existing claim request\n");
        System.out.println("[12] Make a claim payment to a particular customer\n");

        //add new customer claim
        //add item to claim
        //analyze claim payments

        

        Scanner input = new Scanner(System.in);
        int menue_selection;
        int success; 
        while (true) {
            boolean conditional = input.hasNextInt();
            if (conditional) {
                menue_selection = input.nextInt();
                if (menue_selection == 1) {
                    int adjuster_id = user_integer("adjuster_id");
                    success = database.getAdjusterID(adjuster_id);
                    System.out.println("success value is: " + success);
                    if (adjuster_id == success) {
                        database.getAdjusterCustomers(adjuster_id);
                        System.out.print("success value is: " + success);
                    }
                    break;
                }
                else if (menue_selection == 2) {
                    database.getPendingClaim();
                    break;
                }
                else if (menue_selection == 3) {
                    int index;
                    int intPlaceHolder = 0;
                    String stringPlaceHolder = "";
                    int claim_id = user_integer("claim_id");
                    success = database.getClaimID(claim_id);
                    System.out.print("success value is: " + success);
                    if (claim_id == success) {
                        System.out.println("");
                        System.out.println("[1] Change type associated with claim claim");
                        System.out.println("[2] Change accident associated with customer claim");
                        System.out.println("[3] Change items damaged associated with customer claim");
                        System.out.println("[4] Change description associated with customer claim");
                        System.out.println("[5] Change decision associated with customer claim");
                        System.out.println("[6] Change adjuster notes associated with customer claim");
                        System.out.println("[7] Change amount associated with customer claim");
                        System.out.println("[8] Change status associated with customer claim");
                        menue_selection = input.nextInt();
                        if (menue_selection == 1) {
                            index = 1;
                            String type = user_string("type");
                            System.out.println("success value is: " + type);
                            int success1 = database.updateCustomerClaim(claim_id, intPlaceHolder, type, index);
                            System.out.println("success value is: " + success1);
                            break;
                        }
                        else if (menue_selection == 2) {
                            index = 2;
                            int accident = user_integer("accident");
                            System.out.println("success value is: " + accident);
                            int success1 = database.updateCustomerClaim(claim_id, accident, stringPlaceHolder, index);
                            System.out.println("success value is: " + success1);
                            break;
                        }
                        else if (menue_selection == 3) {
                            index = 3;
                            int items_damaged = user_integer("items_damaged");
                            System.out.println("success value is: " + items_damaged);
                            int success1 = database.updateCustomerClaim(claim_id, items_damaged, stringPlaceHolder, index);
                            System.out.println("success value is: " + success1);
                            break;
                        }
                        else if (menue_selection == 4) {
                            index = 4;
                            int description = user_integer("description");
                            System.out.println("success value is: " + description);
                            int success1 = database.updateCustomerClaim(claim_id, description, stringPlaceHolder, index);
                            System.out.println("success value is: " + success1);
                            break;
                        }
                        else if (menue_selection == 5) {
                            index = 5;
                            int decision = user_integer("decision");
                            System.out.println("success value is: " + decision);
                            int success1 = database.updateCustomerClaim(claim_id, decision, stringPlaceHolder, index);
                            System.out.println("success value is: " + success1);
                            break;
                        }
                        else if (menue_selection == 6) {
                            index = 6;
                            String adjuster_notes = user_string("adjuster_notes");
                            System.out.println("success value is: " + adjuster_notes);
                            int success1 = database.updateCustomerClaim(claim_id, intPlaceHolder, adjuster_notes, index);
                            System.out.println("success value is: " + success1);
                            break;
                        }
                        else if (menue_selection == 7) {
                            index = 7;
                            String amount = user_string("amount");
                            System.out.println("success value is: " + amount);
                            int success1 = database.updateCustomerClaim(claim_id, intPlaceHolder, amount, index);
                            System.out.println("success value is: " + success1);
                            break;
                        }
                        else if (menue_selection == 8) {
                            index = 8;
                            String status = user_string("status");
                            System.out.println("success value is: " + status);
                            int success1 = database.updateCustomerClaim(claim_id, intPlaceHolder, status, index);
                            System.out.println("success value is: " + success1);
                            break;
                        }
                    }
                    break;
                }
                else if (menue_selection == 4) {
                    int customer_id = user_integer("customer_id");
                    success = database.getCustomerID(customer_id);
                    System.out.print("success value is: " + success);
                    if (customer_id == success) {
                        database.getAllCustomerClaimList(customer_id);
                    }
                    break;
                }
                else if (menue_selection == 5) {
                    System.out.println("Enter existing claim ID: \n");
                    int claim_id = user_integer("claim_id");
                    success = database.getClaimID(claim_id);
                    System.out.print("success value is: " + success);
                    if (claim_id == success) {
                        success = database.getClaimInfo(claim_id);
                        System.out.print("success value is: " + success);
                    }
                    break;
                }
                else if (menue_selection == 6) {
                    System.out.println("Enter existing claim ID: \n");
                    int claim_id = user_integer("claim_id");
                    success = database.getClaimID(claim_id);
                    System.out.print("success value is: " + success);
                    if (claim_id == success) { 
                        success = database.getAllCustomersClaim(claim_id);
                        System.out.print("success value is: " + success);
                    }
                    break;
                }
                else if (menue_selection == 7) {
                    System.out.println("Enter existing adjuster ID: \n");
                    int adjuster_id = user_integer("adjuster_id");
                    success = database.getAdjusterID(adjuster_id);
                    System.out.print("success value is: " + success);
                    if (adjuster_id == success) { 
                        success = database.getAgentAdjuster(adjuster_id);
                        System.out.print("success value is: " + success);
                    }
                    break;
                }
                else if (menue_selection == 8) {
                    System.out.println("Enter existing adjuster ID: \n");
                    int adjuster_id = user_integer("adjuster_id");
                    success = database.getAdjusterID(adjuster_id);
                    System.out.print("success value is: " + success);
                    if (adjuster_id == success) { 
                        success = database.getAdjusterManages(adjuster_id);
                        System.out.print("success value is: " + success);
                    }
                    break;
                }
                else if (menue_selection == 9) {
                    System.out.println("Enter existing claim ID: \n");
                    int claim_id = user_integer("claim_id");
                    success = database.getClaimID(claim_id);
                    System.out.print("success value is: " + success);
                    if (claim_id == success) { 
                        String name = user_string("name");
                        String type = user_string("type");
                        String phone_number = user_string("phone_number");
                        int success1 = database.adjusterOutsourcing(claim_id, name);
                        System.out.print("success value is: " + success1);
                        int success2 = database.adjusterCompany(name, type, phone_number);
                        System.out.print("success value is: " + success2);
                        try{
                            if (success1 + success2 == 2) {
                                database.connect.commit();
                                System.out.println("transaction SUCCEEDED!\n");
                            }
                        }
                        catch (SQLException exception) {
                            System.out.println("transaction FAILED! ROLLED BACK!\n");
                        }
                        break;
                    }
                }
                else if (menue_selection == 10) {
                    System.out.println("Enter existing claim ID: \n");
                    int claim_id = user_integer("claim_id");
                    success = database.getClaimID(claim_id);
                    System.out.print("success value is: " + success);
                    if (claim_id == success) {
                        int item_id = user_integer("item_id");
                        int policy_id = user_integer("policy_id");
                        String item_type = user_string("item_type");
                        int item_value = user_integer("item_value");
                        String description = user_string("description");
                        database.insertClaimItem(item_id, claim_id, policy_id, item_type, item_value, description);
                    }
                    break;
                }
            }
            else {
                System.out.println("invalid input!");
                input.next();
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
            System.out.println("agent ID invalid! No adjuster exists!");
        }
        return id;
    }

    /**
     * Function for getting customers associated with a specific adjuster
     */
    public int getAdjusterCustomers(int adjuster_id) {
        int id = 0;
        try {
            checkAdjusterCustomers.setInt(1, adjuster_id);
            ResultSet resultset = checkAdjusterCustomers.executeQuery();
            while (resultset.next()) {
                id = resultset.getInt("customer_id");
                System.out.println("customer_id is: " + id);
            }
            id = resultset.getInt("customer_id");
                resultset.close();
        }
        catch (SQLException exception) {
            System.out.println("Customer_id invalid! No customer exists!!");
        }
        return id;
    }

    /**
     * Function for updating customer claim 
     */
    public int updateCustomerClaim(int claim_id, int typeInt, String typeString, int index) {
        int success = 0;
        if (index == 1) {
            try {
                changeClaimType.setString(1, typeString);
                changeClaimType.setInt(2, claim_id);
                success = changeClaimType.executeUpdate();
            }
            catch (SQLException exception) {
                System.out.println("invalid input!");
            }
        }
        else if (index == 2) {
            try {
                changeClaimAccident.setString(1, typeString);
                changeClaimAccident.setInt(2, claim_id);
                success = changeClaimAccident.executeUpdate();
            }
            catch (SQLException exception) {
                System.out.println("invalid input!");
            }
        }
        else if (index == 3) {
            try {
                changeClaimItemsDamaged.setString(1, typeString);
                changeClaimItemsDamaged.setInt(2, claim_id);
                success = changeClaimItemsDamaged.executeUpdate();
            }
            catch (SQLException exception) {
                System.out.println("invalid input!");
            }
        }
        else if (index == 4) {
            try {
                changeClaimDescription.setString(1, typeString);
                changeClaimDescription.setInt(2, claim_id);
                success = changeClaimDescription.executeUpdate();
            }
            catch (SQLException exception) {
                System.out.println("invalid input!");
            }
        }
        else if (index == 5) {
            try {
                changeClaimDecision.setString(1, typeString);
                changeClaimDecision.setInt(2, claim_id);
                success = changeClaimDecision.executeUpdate();
            }
            catch (SQLException exception) {
                System.out.println("invalid input!");
            }
        }
        else if (index == 6) {
            try {
                changeClaimAdjusterNotes.setString(1, typeString);
                changeClaimAdjusterNotes.setInt(2, claim_id);
                success = changeClaimAdjusterNotes.executeUpdate();
            }
            catch (SQLException exception) {
                System.out.println("invalid input!");
            }
        }
        else if (index == 7) {
            try {
                changeClaimAmount.setInt(1, typeInt);
                changeClaimAmount.setInt(2, claim_id);
                success = changeClaimAmount.executeUpdate();
            }
            catch (SQLException exception) {
                System.out.println("invalid input!");
            }
        }
        else if (index == 8) {
            try {
                changeClaimStatus.setString(1, typeString);
                changeClaimStatus.setInt(2, claim_id);
                success = changeClaimStatus.executeUpdate();
            }
            catch (SQLException exception) {
                System.out.println("invalid input!");
            }
        }
        return success;
    }

     /**
     * Function for getting all claims associated with a specific customer
     */
    public int getAllCustomerClaimList(int customer_id) {
        int id = 0;
        try {
            getAllCustomerClaim.setInt(1, customer_id);
            ResultSet resultset = getAllCustomerClaim.executeQuery();
            while (resultset.next()) {
                id = resultset.getInt("claim_id");
                System.out.println("claim_id is: " + id);
            }
                resultset.close();
        }
        catch (SQLException exception) {
            System.out.println("Customer_id invalid! No customer exists!!");
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
                int customer_id = resultset.getInt("customer_id");
                String claim_type = resultset.getString("claim_type");
                String accident = resultset.getString("accident");
                String items_damaged = resultset.getString("items_damaged");
                String description = resultset.getString("description");
                String descision = resultset.getString("decision");
                String adjuster_notes = resultset.getString("adjuster_notes");
                int amount = resultset.getInt("amount");
                String status = resultset.getString("claim_status");
                System.out.println("policy customer_id is: " + customer_id);
                System.out.println("policy policy_id is: " + claim_id);
                System.out.println("policy type is: " + claim_type);
                System.out.println("policy cost is: " + accident);
                System.out.println("policy cost is: " + items_damaged);
                System.out.println("policy coverage is: " + description);
                System.out.println("policy deductible is: " + descision);
                System.out.println("policy coinsurance is: " + adjuster_notes);
                System.out.println("policy effective_date is: " + amount);
                System.out.println("policy expire_date is: " + status);
                success = 1;
            }
                resultset.close();
        }
        catch (SQLException exception) {
            System.out.println("claim_id invalid! No claim exists!");
        }
        return success;
    }

     /**
     * Function for getting all customers associated with a specific claim
     */
    public int getAllCustomersClaim(int claim_id) {
        int id = 0;
        try {
            getAllAdjusterCustomers.setInt(1, claim_id);
            ResultSet resultset = getAllAdjusterCustomers.executeQuery();
            while (resultset.next()) {
                id = resultset.getInt("customer_id");
                System.out.println("customer_id is: " + id);
            }
                resultset.close();
        }
        catch (SQLException exception) {
            System.out.println("Customer_id invalid! No customer exists!!");
        }
        return id;
    }

    /**
     * Function for getting all adjusters associated with a specific agent
     */
    public int getAgentAdjuster(int adjuster_id) {
        int id = 0;
        try {
            adjusterAgent.setInt(1, adjuster_id);
            ResultSet resultset = adjusterAgent.executeQuery();
            while (resultset.next()) {
                id = resultset.getInt("agent_id");
                System.out.println("agent_id is: " + id);
            }
                resultset.close();
        }
        catch (SQLException exception) {
            System.out.println("Customer_id invalid! No customer exists!!");
        }
        return id;
    }

    /**
     * Function for getting all adjusters associated with a specific agent
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
            System.out.println("Customer_id invalid! No customer exists!!");
        }
        return id;
    }

    /**
     * Function for inserting new oursourcing company associated with claim into database
     */
    public int adjusterOutsourcing(int claim_id, String name) {
        int success = 0;
        try {
            claimOutsources.setString(1, name);
            claimOutsources.setInt(2, claim_id);
            success = claimOutsources.executeUpdate();
        }
        catch (SQLException exception) {
            System.out.println("invalid input!");
        }
        return success;
    }

    /**
     * Function for inserting new information about a company associated with a claim
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
            System.out.println("invalid input!");
        }
        return success;
    }

    /**
     * Function for inserting item associated with claim
     */
    public int insertClaimItem(int item_id, int claim_id, int policy_id, String item_type, int item_value, String description) {
        int success = 0;
        try {
            claimItem.setInt(1, item_id);
            claimItem.setInt(2, claim_id);
            claimItem.setInt(3, policy_id);
            claimItem.setString(4, item_type);
            claimItem.setInt(5, item_value);
            claimItem.setString(6, description);
            success = claimItem.executeUpdate();
        }
        catch (SQLException exception) {
            System.out.println("invalid input!");
        }
        return success;
    }
    /**
     * Input checking constraints on user integer
     */
    public static int user_integer(String message) {
        System.out.println("enter: " + message);
        Scanner input = new Scanner(System.in);
            boolean conditional = true;
            while (conditional) {
                boolean condition = input.hasNextInt();
                System.out.println("condition is: " + condition);
                if (condition) {
                    int user_input = input.nextInt();
                    System.out.println("entered int!");
                    conditional = false; 
                    return user_input; 
                }
                else {
                    System.out.println("entered string!");
                    conditional = true;
                    input.next();
                }
            }
        return 0;
    }
    
     /**
     * Input checking constraints on user string 
     */
    public static String user_string(String message) {
        System.out.println("enter: " + message);
        Scanner input = new Scanner(System.in);
            boolean conditional = true;
            while (conditional) {
                boolean condition = input.hasNextInt();
                System.out.println("condition is: " + conditional);
                if (!condition) {
                    String user_input = input.nextLine();
                    System.out.println("entered string!");
                    conditional = false; 
                    return user_input; 
                }
                else {
                    System.out.println("entered integer!");
                    conditional = true;
                    input.next();
                }
            }
        return "0";
    }

    /**
     * getPendingClaims gets customers with pending claims
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
            System.out.println("Customer_id invalid! No customer exists!!");
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
                System.out.println("claim is: " + id);
            }
                resultset.close();
        }
        catch (SQLException exception) {
            System.out.println("claim ID invalid! No claim exists!!");
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
                System.out.println("customer_id is: " + id);
            }
            try {
                resultset.close();
            }
            catch (SQLException exception) {
                System.out.println("Cannot close resultset!");
            }
        }
        catch (SQLException exception) {
            System.out.println("Customer_id invalid! No customer exists!!");
        }
        return id;
    }
}

