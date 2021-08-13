/**
 * Course: CSE 341
 * Semester and Year: Spring 2021
 * Assignment: agent Systems
 * Author: Derei, Tal
 * User ID: tad222
 */

package insurance;

import java.util.Scanner;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Calendar;
import java.sql.*;
import java.util.*;
import java.text.*;

    /**
     * Agent Interface
     */
    public class Agent_Interface { 
    /**
     * Prepared Statments associated with agent interface
     */
    public Connection connect;
    private PreparedStatement checkAgentID;
    private PreparedStatement checkAgentCustomers;
    private PreparedStatement overdueBills;
    private PreparedStatement pendingClaims;
    private PreparedStatement estimatedRevenue;
    private PreparedStatement changePolicyType;
    private PreparedStatement changePolicyCost;
    private PreparedStatement changePolicyCoverage;
    private PreparedStatement changePolicyDeductible;
    private PreparedStatement changePolicyCoinsurance;
    private PreparedStatement changePolicyEffectiveDate;
    private PreparedStatement changePolicyExpireDate;
    private PreparedStatement changePolicyPlan;
    private PreparedStatement changePolicyStatus;
    private PreparedStatement getAllCustomerPolicy;
    private PreparedStatement getAllCustomers;
    private PreparedStatement agentAdjuster;
    public PreparedStatement checkPolicyID;
    public PreparedStatement checkCustomerID;
    public PreparedStatement getPolicyInformation;

    /**
     * Command-line interface for agents
     * @param database Connection object
     */
    public static void Agent(Connection database) {
        Agent_Interface agent = new Agent_Interface();
       /**
         * Cached Prepared Statements for agent interface
         */
        try {
            agent.checkAgentID = database.prepareStatement("SELECT agent_id FROM agent WHERE agent_id = ?");
            agent.checkAgentCustomers = database.prepareStatement("SELECT customer_id FROM customer_agent WHERE agent_id = ?");
            agent.overdueBills = database.prepareStatement("SELECT customer_id FROM customer NATURAL JOIN policy NATURAL JOIN policy_payment WHERE status = 'LATE'");
            agent.pendingClaims = database.prepareStatement("SELECT customer_id FROM customer NATURAL JOIN claim NATURAL JOIN claim_payment NATURAL JOIN policy WHERE claim_status = 'Pending'");
            agent.estimatedRevenue = database.prepareStatement("SELECT cost FROM customer_agent NATURAL JOIN policy WHERE agent_id = ?");
            agent.checkPolicyID = database.prepareStatement("SELECT policy_id FROM policy WHERE policy_id = ?");
            agent.changePolicyType = database.prepareStatement("UPDATE policy SET type = ? WHERE policy_id = ?");
            agent.changePolicyCost = database.prepareStatement("UPDATE policy SET cost = ? WHERE policy_id = ?");
            agent.changePolicyCoverage = database.prepareStatement("UPDATE policy SET coverage = ? WHERE policy_id = ?");
            agent.changePolicyDeductible = database.prepareStatement("UPDATE policy SET deductible = ? WHERE policy_id = ?");
            agent.changePolicyCoinsurance = database.prepareStatement("UPDATE policy SET coinsurance = ? WHERE policy_id = ?");
            agent.changePolicyEffectiveDate = database.prepareStatement("UPDATE policy SET effective_date = ? WHERE policy_id = ?");
            agent.changePolicyExpireDate = database.prepareStatement("UPDATE policy SET expire_date = ? WHERE policy_id = ?");
            agent.changePolicyPlan = database.prepareStatement("UPDATE policy SET plan = ? WHERE policy_id = ?");
            agent.changePolicyStatus = database.prepareStatement("UPDATE policy SET policy_status = ? WHERE policy_id = ?");
            agent.checkCustomerID = database.prepareStatement("SELECT customer_id FROM customer WHERE customer_id = ?");
            agent.getAllCustomerPolicy = database.prepareStatement("SELECT policy_id FROM policy WHERE customer_id = ?");
            agent.getPolicyInformation = database.prepareStatement("SELECT * FROM policy WHERE policy_id = ?");
            agent.getAllCustomers = database.prepareStatement("SELECT customer_id FROM policy WHERE policy_id = ?");
            agent.agentAdjuster = database.prepareStatement("SELECT adjuster_id FROM communicates WHERE agent_id = ?");
        }
        catch (SQLException exception) {
            System.out.println("Error with Prepared Statements!");
        }
       
        /**
         * Command-line interface for agent interface
         */
        Scanner input = new Scanner(System.in);
        int menue_selection;
        int success; 
        boolean valid_start_date = true;
        boolean valid_end_date = true;
        while (true) {
            System.out.println("--------------------------------------------------------------");
            System.out.println("[1] Get All Customers Associated With a Particular Agent\n");
            System.out.println("[2] Identify Customers With Overdue Bills\n");
            System.out.println("[3] Customers With Pending Claims That Have Not Been Serviced Recently\n");
            System.out.println("[4] Compute the Estimated Revenue Generated by an Agent\n");
            System.out.println("[5] Update a Customer's Policy\n");
            System.out.println("[6] Get All Polcies Associated With a Particular Customer\n"); 
            System.out.println("[7] Get Policy Information Associated With a Customer's Policy\n"); 
            System.out.println("[8] Get All Customers (and Dependants) Associated With a Particular Policy\n"); 
            System.out.println("[9] Get All Adjusters an Agent Communicates With\n");
            System.out.println("[10] Disconnect!");
            System.out.println("--------------------------------------------------------------");
            System.out.print("\nSelect From the List of Options Above: ");
            boolean conditional = input.hasNextInt();
            if (conditional) {
                menue_selection = input.nextInt();
                if (menue_selection == 1) {
                    System.out.print("\nEnter 6-Digit Agent ID to Retrieve All Customers Managed By That Agent: ");
                    int agent_id = user_integer();
                    success = agent.getAgentID(agent_id);
                    if (agent_id == success) {
                        agent.getAgentCustomers(agent_id);
                    }
                    else {
                        System.out.println("Agent With ID " + agent_id + " Does NOT Exist!\n");
                    }
                }
                else if (menue_selection == 2) {
                    System.out.println("Customers with Overdue Bills: ");
                    agent.getOverdueBill();
                }
                else if (menue_selection == 3) {
                    System.out.println("Customers with Pending Claims: ");
                    agent.getPendingClaim();
                }
                else if (menue_selection == 4) {
                    System.out.print("Revenue by the Agent: ");
                    int agent_id = user_integer();
                    success = agent.getAgentID(agent_id);
                    if (agent_id == success) {
                        int estimated_revenue = agent.getEstimatedRevenue(agent_id);
                        System.out.println("Estimated revenue for agent " + agent_id + " is " + estimated_revenue + "\n");
                    }
                    else {
                        System.out.println("Agent With ID " + agent_id + " Does NOT Exist!");
                    }
                }
                else if (menue_selection == 5) {
                    int index;
                    int intPlaceHolder = 0;
                    String stringPlaceHolder = "";
                    System.out.print("Please Enter an Existing 6-digit Policy ID To Update the Policy: ");
                    int policy_id = user_integer();
                    success = agent.getPolicyID(policy_id);
                    if (policy_id == success) {
                        System.out.println("\n--------------------------------------------------------------");
                        System.out.println("[1] Change Type Associated With Customer Policy");
                        System.out.println("[2] Change Cost Associated With Customer Policy");
                        System.out.println("[3] Change Coverage Associated With Customer Policy");
                        System.out.println("[4] Change Deductible Associated With Customer Policy");
                        System.out.println("[5] Change Coinsurance Associated With Customer Policy");
                        System.out.println("[6] Change Effective Date Associated With Customer Policy");
                        System.out.println("[7] Change Expiration Date Associated With Customer Policy");
                        System.out.println("[8] Change Policy Status Associated With Customer Policy");
                        System.out.println("--------------------------------------------------------------\n");
                        System.out.print("Please Choose What Part of the Policy You Would Like to Update: ");
                        menue_selection = input.nextInt();
                        if (menue_selection == 1) {
                            index = 1;
                            System.out.print("Update Type: [1] Single, [2] Family ");
                            String type = IOManager.policyType(1);
                            agent.updateCustomerPolicy(policy_id, intPlaceHolder, type, index);                            
                        }
                        else if (menue_selection == 2) {
                            index = 2;
                            System.out.print("Update Cost: ");
                            double cost = IOManager.intInputDouble(0.00, 999999.99);
                            agent.updateCustomerPolicy(policy_id, cost, stringPlaceHolder, index);
                        }
                        else if (menue_selection == 3) {
                            index = 3;
                            System.out.print("Update Coverage: ");
                            double coverage = IOManager.intInputDouble(0, 999999.99);
                            agent.updateCustomerPolicy(policy_id, coverage, stringPlaceHolder, index);
                        }
                        else if (menue_selection == 4) {
                            index = 4;
                            System.out.print("Update Deductible: ");
                            double deductible = IOManager.intInputDouble(0, 9999.99);
                            agent.updateCustomerPolicy(policy_id, deductible, stringPlaceHolder, index);
                        }
                        else if (menue_selection == 5) {
                            index = 5;
                            System.out.print("Update Coinsurance: ");
                            double coinsurance = IOManager.intInputDouble(0, 99);
                            agent.updateCustomerPolicy(policy_id, coinsurance, stringPlaceHolder, index);
                        }
                        else if (menue_selection == 6) {
                            index = 6;
                            System.out.print("Update Effective Date: ");
                            String effective_date = "";
                            while (valid_start_date) {
                                effective_date = input.next();
                                valid_start_date = validateDate(effective_date);
                            } 
                            agent.updateCustomerPolicy(policy_id, intPlaceHolder, effective_date, index);
                        }
                        else if (menue_selection == 7) {
                            index = 7;
                            System.out.print("Update Expiration Date: ");
                            String expire_date = "";
                            while (valid_end_date) {
                                expire_date = input.next();
                                valid_end_date = validateDate(expire_date);
                            } 
                            agent.updateCustomerPolicy(policy_id, intPlaceHolder, expire_date, index);
                        }
                        else if (menue_selection == 8) {
                            index = 8;
                            System.out.println("Update Policy Status: [1] Pending, [2] Active, [3] Inactive");
                            String policy_status = IOManager.policyStatus(1);
                            agent.updateCustomerPolicy(policy_id, intPlaceHolder, policy_status, index);
                        }
                        else {
                            System.out.println("Invalid Policy ID! Try Again!");
                        }
                    }
                    
                }
                else if (menue_selection == 6) {
                    System.out.print("Please Enter an Existing 6-digit Customer ID to Get All Policies Associated With the Customer: ");
                    int customer_id = user_integer();
                    success = agent.getCustomerID(customer_id);
                    if (customer_id == success) {
                        agent.getAllCustomerPolicyList(customer_id);
                    }
                    else {
                        System.out.println("Customer With ID " + customer_id + " Does NOT Exist! Try Again!");
                    }                    
                }
                else if (menue_selection == 7) {
                    System.out.print("Please Enter an Existing 6-digit Policy ID to Get Policy Information Associated With That Specific Policy: ");
                    int policy_id = user_integer();
                    success = agent.getPolicyID(policy_id);
                    if (policy_id == success) {
                        int success1 = agent.getPolicyInfo(policy_id);
                    }
                    else {
                        System.out.println("Policy With ID " + policy_id + " Does NOT Exist!");
                    }                
                }
                else if (menue_selection == 8) {
                    System.out.print("Enter Existing Policy ID To Retrieve Customers: ");
                    int policy_id = user_integer();
                    success = agent.getPolicyID(policy_id);
                    if (policy_id == success) {
                        agent.getAllCustomersList(policy_id);
                    }
                    else {
                        System.out.println("Policy With ID " + policy_id + " Does NOT Exist!");
                    } 
                }
                else if (menue_selection == 9) {
                    System.out.print("Enter Existing Agent ID to Retrieve Adjusters They Communicate With: ");
                    int agent_id = user_integer();
                    success = agent.getAgentID(agent_id);
                    if (agent_id == success) {
                        success = agent.getAdjusterAgent(agent_id);
                    }
                    else {
                        System.out.println("Agent With ID " + agent_id + " Does NOT Exist!");
                    } 
                }
                else if (menue_selection == 10) {
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
 * FUNCTIONS ASSOCIATED WITH AGENT_INTERFACE
 */
    /** 
     * getAgentID checks if agent ID is in the agent 
     */
    public int getAgentID(int agent_id) {
        int id = 0;
        try {
            checkAgentID.setInt(1, agent_id);
            ResultSet resultset = checkAgentID.executeQuery();
            while (resultset.next()) {
                id = resultset.getInt("agent_id");
                System.out.println("Agent ID: " + id);
            }
                resultset.close();
        }
        catch (SQLException exception) {
            System.out.println("agent ID Invalid! No Agent Exists With That ID.");            
        }
        return id;
    }

    /**
     * getAgentCustomers retrieves customers associated with a specific agent
     */
    public int getAgentCustomers(int agent_id) {
        int id = 0;
        try {
            checkAgentCustomers.setInt(1, agent_id);
            ResultSet resultset = checkAgentCustomers.executeQuery();
            while (resultset.next()) {
                id = resultset.getInt("customer_id");
                System.out.println("Customer ID is: " + id + "\n");
            }
                resultset.close();
        }
        catch (SQLException exception) {
            System.out.println("Agent Doesn't Manage Any Customers Yet!");
        }
        return id;
    }

    /**
     * checkPaymentStatus checks the status of a payment
     */
    public static String checkPaymentStatus(Calendar calendar, LocalDate localdate) {
        String status = "";
        System.out.println("policy day is: " + calendar.get(Calendar.DAY_OF_MONTH));
        System.out.println("policy year is: " + calendar.get(Calendar.YEAR));
        System.out.println("current year is: " + localdate.getYear());
        if ((localdate.getMonthValue() < (calendar.get(Calendar.MONTH) + 1))) {
            status = "LATE";
        }
        if ((localdate.getMonthValue() > (calendar.get(Calendar.MONTH) + 1))) {
            status = "ON TIME";
        }
        return status;
    }

    /**
     * getOverdueBill gets customers with overdue bills
     */
    public int getOverdueBill() {
        int customer_id = 0;
        try {
            ResultSet resultset = overdueBills.executeQuery();
            while (resultset.next()) {
                customer_id = resultset.getInt("customer_id");
                System.out.println("Customer ID: " + customer_id);
            }
                resultset.close();
        }
        catch (SQLException exception) {
            System.out.println("Try Again!");
        }
        return customer_id;
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
                System.out.println("Customer ID: " + customer_id + "\n");
            }
                resultset.close();
        }
        catch (SQLException exception) {
            System.out.println("Try Again!");
        }
        return customer_id;
    }

    /**
     * getEstimatedRevenue estimates agent's revenue based on policy costs (cost on a yearly basis)
     */
    public int getEstimatedRevenue(int agent_id) {
        int total_revenue = 0;
        try {
            estimatedRevenue.setInt(1, agent_id);
            ResultSet resultset = estimatedRevenue.executeQuery();
            while (resultset.next()) {
                total_revenue += resultset.getInt("cost");
            }
                resultset.close();
        }
        catch (SQLException exception) {
            System.out.println("Customer_id invalid! No customer exists!!");
        }
        return total_revenue;
    }

    /**
     * updateCustomerPolicy updates a customers policy 
     */
    public int updateCustomerPolicy(int policy_id, double typeInt, String typeString, int index) {
        int success = 0;
        if (index == 1) {
            try {
                changePolicyType.setString(1, typeString);
                changePolicyType.setInt(2, policy_id);
                success = changePolicyType.executeUpdate();
                System.out.println("POLICY UPDATED!");
            }
            catch (SQLException exception) {
                System.out.println("Invalid Input! Try Again!");
            }
        }
        else if (index == 2) {
            try {
                changePolicyCost.setDouble(1, typeInt);
                changePolicyCost.setInt(2, policy_id);
                success = changePolicyCost.executeUpdate();
                System.out.println("POLICY UPDATED!");
            }
            catch (SQLException exception) {
                System.out.println("Invalid Input! Try Again!");
            }
        }
        else if (index == 3) {
            try {
                changePolicyCoverage.setDouble(1, typeInt);
                changePolicyCoverage.setInt(2, policy_id);
                success = changePolicyCoverage.executeUpdate();
                System.out.println("POLICY UPDATED!");
            }
            catch (SQLException exception) {
                System.out.println("Invalid Input! Try Again!");
            }
        }
        else if (index == 4) {
            try {
                changePolicyDeductible.setDouble(1, typeInt);
                changePolicyDeductible.setInt(2, policy_id);
                success = changePolicyDeductible.executeUpdate();
                System.out.println("POLICY UPDATED!");
            }
            catch (SQLException exception) {
                System.out.println("Invalid Input! Try Again!");
            }
        }
        else if (index == 5) {
            try {
                changePolicyCoinsurance.setDouble(1, typeInt);
                changePolicyCoinsurance.setInt(2, policy_id);
                success = changePolicyCoinsurance.executeUpdate();
                System.out.println("POLICY UPDATED!");
            }
            catch (SQLException exception) {
                System.out.println("Invalid Input! Try Again!");
            }
        }
        else if (index == 6) {
            try {
                changePolicyEffectiveDate.setDate(1, Date.valueOf(typeString));
                changePolicyEffectiveDate.setInt(2, policy_id);
                success = changePolicyEffectiveDate.executeUpdate();
                System.out.println("POLICY UPDATED!");
            }
            catch (SQLException exception) {
                System.out.println("Invalid Input! Try Again!");
            }
        }
        else if (index == 7) {
            try {
                changePolicyExpireDate.setDate(1, Date.valueOf(typeString));
                changePolicyExpireDate.setInt(2, policy_id);
                success = changePolicyExpireDate.executeUpdate();
                System.out.println("POLICY UPDATED!");
            }
            catch (SQLException exception) {
                System.out.println("Invalid Input! Try Again!");
            }
        }
        else if (index == 8) {
            try {
                changePolicyStatus.setString(1, typeString);
                changePolicyStatus.setInt(2, policy_id);
                success = changePolicyStatus.executeUpdate();
                System.out.println("POLICY UPDATED!");
            }
            catch (SQLException exception) {
                System.out.println("Invalid Input! Try Again!");
            }
        }
        return success;
    }

    /**
     * getAllCustomerPolicyList gets all polcies associated with a specific customer
     */
    public int getAllCustomerPolicyList(int customer_id) {
        int id = 0;
        try {
            getAllCustomerPolicy.setInt(1, customer_id);
            ResultSet resultset = getAllCustomerPolicy.executeQuery();
            while (resultset.next()) {
                id = resultset.getInt("policy_id");
                System.out.println("Policy ID: " + id);
            }
                resultset.close();
        }
        catch (SQLException exception) {
            System.out.println("Invalid Input!");
        }
        return id;
    }

    /**
     * getAllCustomersList gets all customers associated with a specific policy
     */
    public int getAllCustomersList(int policy_id) {
        int id = 0;
        try {
            getAllCustomers.setInt(1, policy_id);
            ResultSet resultset = getAllCustomers.executeQuery();
            while (resultset.next()) {
                id = resultset.getInt("customer_id");
                System.out.println("Customer ID: " + id);
            }
                resultset.close();
        }
        catch (SQLException exception) {
            System.out.println("Invalid Input!");
        }
        return id;
    }

    /**
     * getAdjusterAgent gets all adjusters associated with a specific agent
     */
    public int getAdjusterAgent(int agent_id) {
        int id = 0;
        try {
            agentAdjuster.setInt(1, agent_id);
            ResultSet resultset = agentAdjuster.executeQuery();
            while (resultset.next()) {
                id = resultset.getInt("adjuster_id");
                System.out.println("Adjuster ID: " + id);
            }
                resultset.close();
        }
        catch (SQLException exception) {
            System.out.println("Invalid Input!");
        }
        return id;
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
                    System.out.println("Invalid Input!");
                    conditional = true;
                    input.next();
                }
            }
        return "0";
    }

    /**
     * getPolicyID checks if policy id is in the agent when user attempts to add a policy
     */
    public int getPolicyID(int policy_id) {
        int id = 0;
        try {
            checkPolicyID.setInt(1, policy_id);
            ResultSet resultset = checkPolicyID.executeQuery();
            while (resultset.next()) {
                id = resultset.getInt("policy_id");
                System.out.println("Policy ID: " + id);
            }
                resultset.close();
        }
        catch (SQLException exception) {
            System.out.println("Invalid Input!");
        }
        return id;
    }

    /**
     * getCustomerID checks if customer id is in the agent when user attempts to add a policy
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
            System.out.println("Invalid Input!");
        }
        return id;
    }

    /**
     * getPolicyInfo returns information about a customer's existing policy 
     */
    public int getPolicyInfo(int policy_id) {
        int success = 0;
        try {
            getPolicyInformation.setInt(1, policy_id);
            ResultSet resultset = getPolicyInformation.executeQuery();
            while (resultset.next()) {
                int customer_id = resultset.getInt("customer_id");
                String type = resultset.getString("type");
                int cost = resultset.getInt("cost");
                int coverage = resultset.getInt("coverage");
                int deductible = resultset.getInt("deductible");
                int coinsurance = resultset.getInt("coinsurance");
                String effective_date = resultset.getString("effective_date");
                String expire_date = resultset.getString("expire_date");
                String plan = resultset.getString("plan");
                System.out.println("Customer ID is: " + customer_id);
                System.out.println("Policy ID is: " + policy_id);
                System.out.println("Type is: " + type);
                System.out.println("Cost is: " + cost);
                System.out.println("Coverage is: " + coverage);
                System.out.println("Deductible is: " + deductible);
                System.out.println("Coinsurance is: " + coinsurance);
                System.out.println("Effective Date is: " + effective_date);
                System.out.println("Expire Date is: " + expire_date);
                System.out.println("Plan is: " + plan);
                success = 1;
            }
                resultset.close();
        }
        catch (SQLException exception) {
            System.out.println("Invalid Input!");
        }
        return success;
    }

     /**
     * validateDate check's the format of date types
     */
    public static Boolean validateDate(String date_of_birth) {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            format.parse(date_of_birth);
        } catch (ParseException exception) {
            System.out.println("Invalid Input!");
            return true;
        }
        return false;
    }
}