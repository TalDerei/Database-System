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
    /**
     * Command-line interface for customers
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
     * Establish a connection to the Oracle database
     */
    public static Agent_Interface connect_database() {
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
        
        Agent_Interface database = new Agent_Interface();
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
     * Command-line interface for agents
     */
    public static void Agent(Agent_Interface database) {
       /**
         * Prepared Statements for agent interface
         */
        try {
            database.checkAgentID = database.connect.prepareStatement("SELECT agent_id FROM agent WHERE agent_id = ?");
            database.checkAgentCustomers = database.connect.prepareStatement("SELECT customer_id FROM customer_agent WHERE agent_id = ?");
            database.overdueBills = database.connect.prepareStatement("SELECT customer_id FROM customer NATURAL JOIN policy NATURAL JOIN policy_payment WHERE status = 'late'");
            database.pendingClaims = database.connect.prepareStatement("SELECT customer_id FROM customer NATURAL JOIN claim NATURAL JOIN claim_payment WHERE status = 'pending'");
            database.estimatedRevenue = database.connect.prepareStatement("SELECT cost FROM customer_agent NATURAL JOIN policy WHERE agent_id = ?");
            database.checkPolicyID = database.connect.prepareStatement("SELECT policy_id FROM policy WHERE policy_id = ?");
            database.changePolicyType = database.connect.prepareStatement("UPDATE policy SET type = ? WHERE policy_id = ?");
            database.changePolicyCost = database.connect.prepareStatement("UPDATE policy SET cost = ? WHERE policy_id = ?");
            database.changePolicyCoverage = database.connect.prepareStatement("UPDATE policy SET coverage = ? WHERE policy_id = ?");
            database.changePolicyDeductible = database.connect.prepareStatement("UPDATE policy SET deductible = ? WHERE policy_id = ?");
            database.changePolicyCoinsurance = database.connect.prepareStatement("UPDATE policy SET coinsurance = ? WHERE policy_id = ?");
            database.changePolicyEffectiveDate = database.connect.prepareStatement("UPDATE policy SET effective_date = ? WHERE policy_id = ?");
            database.changePolicyExpireDate = database.connect.prepareStatement("UPDATE policy SET expire_date = ? WHERE policy_id = ?");
            database.changePolicyPlan = database.connect.prepareStatement("UPDATE policy SET plan = ? WHERE policy_id = ?");
            database.changePolicyStatus = database.connect.prepareStatement("UPDATE policy SET policy_status = ? WHERE policy_id = ?");
            database.checkCustomerID = database.connect.prepareStatement("SELECT customer_id FROM customer WHERE customer_id = ?");
            database.getAllCustomerPolicy = database.connect.prepareStatement("SELECT policy_id FROM customer_policy WHERE customer_id = ?");
            database.getPolicyInformation = database.connect.prepareStatement("SELECT * FROM policy WHERE policy_id = ?");
            database.getAllCustomers = database.connect.prepareStatement("SELECT customer_id FROM policy WHERE policy_id = ?");
            database.agentAdjuster = database.connect.prepareStatement("SELECT adjuster_id FROM communicates WHERE agent_id = ?");
        }
        catch (SQLException exception) {
            System.out.println("Error with Prepared Statements!");
        }
       
        /**
         * Command-line interface for agent interface
         */
        System.out.println("[1] Get all customers associated with a particular agent\n");
        System.out.println("[2] Identify customers with overdue bills\n");
        System.out.println("[3] Customers with pending claims that have not been serviced recently\n");
        System.out.println("[4] Compute estimated revenue generated by an agent\n");
        System.out.println("[5] Update a customer's policy\n");
        System.out.println("[6] Get all polcies associated with a particular customer\n"); 
        System.out.println("[7] Get policy information associated with a customer's policy\n"); 
        System.out.println("[8] Get all customers (and dependants) associated with a particular policy\n"); 
        System.out.println("[9] Get all adjusters an agent communicates with\n");

        // get all customer policy info

        // get all policies that an agent manages

        Scanner input = new Scanner(System.in);
        int menue_selection;
        int success; 
        while (true) {
            boolean conditional = input.hasNextInt();
            if (conditional) {
                menue_selection = input.nextInt();
                if (menue_selection == 1) {
                    int agent_id = user_integer("agent_id");
                    success = database.getAgentID(agent_id);
                    System.out.println("success value is: " + success);
                    if (agent_id == success) {
                        database.getAgentCustomers(agent_id);
                        System.out.print("success value is: " + success);
                    }
                    break;
                }
                else if (menue_selection == 2) {
                    database.getOverdueBill();
                }
                else if (menue_selection == 3) {
                    database.getPendingClaim();
                    break;
                }
                else if (menue_selection == 4) {
                    int agent_id = user_integer("agent_id");
                    success = database.getAgentID(agent_id);
                    System.out.println("success value is: " + success);
                    if (agent_id == success) {
                        int estimated_revenue = database.getEstimatedRevenue(agent_id);
                        System.out.println("Estimated revenue for agent " + agent_id + " is " + estimated_revenue);
                        break;
                    }
                }
                else if (menue_selection == 5) {
                    int index;
                    int intPlaceHolder = 0;
                    String stringPlaceHolder = "";
                    int policy_id = user_integer("policy_id");
                    success = database.getPolicyID(policy_id);
                    System.out.print("success value is: " + success);
                    if (policy_id == success) {
                        System.out.println("");
                        System.out.println("[1] Change type associated with customer policy");
                        System.out.println("[2] Change cost associated with customer policy");
                        System.out.println("[3] Change coverage associated with customer policy");
                        System.out.println("[4] Change deductible associated with customer policy");
                        System.out.println("[5] Change coinsurance associated with customer policy");
                        System.out.println("[6] Change effective_date associated with customer policy");
                        System.out.println("[7] Change expire_date associated with customer policy");
                        System.out.println("[8] Change plan associated with customer policy");
                        System.out.println("[9] Change policy_status associated with customer policy");
                        menue_selection = input.nextInt();
                        if (menue_selection == 1) {
                            index = 1;
                            String type = user_string("type");
                            System.out.println("success value is: " + type);
                            int success1 = database.updateCustomerPolicy(policy_id, intPlaceHolder, type, index);
                            System.out.println("success value is: " + success1);
                            break;
                        }
                        else if (menue_selection == 2) {
                            index = 2;
                            int cost = user_integer("cost");
                            System.out.println("success value is: " + cost);
                            int success1 = database.updateCustomerPolicy(policy_id, cost, stringPlaceHolder, index);
                            System.out.println("success value is: " + success1);
                            break;
                        }
                        else if (menue_selection == 3) {
                            index = 3;
                            int coverage = user_integer("coverage");
                            System.out.println("success value is: " + coverage);
                            int success1 = database.updateCustomerPolicy(policy_id, coverage, stringPlaceHolder, index);
                            System.out.println("success value is: " + success1);
                            break;
                        }
                        else if (menue_selection == 4) {
                            index = 4;
                            int deductible = user_integer("deductible");
                            System.out.println("success value is: " + deductible);
                            int success1 = database.updateCustomerPolicy(policy_id, deductible, stringPlaceHolder, index);
                            System.out.println("success value is: " + success1);
                            break;
                        }
                        else if (menue_selection == 5) {
                            index = 5;
                            int coinsurance = user_integer("coinsurance");
                            System.out.println("success value is: " + coinsurance);
                            int success1 = database.updateCustomerPolicy(policy_id, coinsurance, stringPlaceHolder, index);
                            System.out.println("success value is: " + success1);
                            break;
                        }
                        else if (menue_selection == 6) {
                            index = 6;
                            String effective_date = user_string("effective_date");
                            System.out.println("success value is: " + effective_date);
                            int success1 = database.updateCustomerPolicy(policy_id, intPlaceHolder, effective_date, index);
                            System.out.println("success value is: " + success1);
                            break;
                        }
                        else if (menue_selection == 7) {
                            index = 7;
                            String expire_date = user_string("expire_date");
                            System.out.println("success value is: " + expire_date);
                            int success1 = database.updateCustomerPolicy(policy_id, intPlaceHolder, expire_date, index);
                            System.out.println("success value is: " + success1);
                            break;
                        }
                        else if (menue_selection == 8) {
                            index = 8;
                            String plan = user_string("plan");
                            System.out.println("success value is: " + plan);
                            int success1 = database.updateCustomerPolicy(policy_id, intPlaceHolder, plan, index);
                            System.out.println("success value is: " + success1);
                            break;
                        }
                        else if (menue_selection == 9) {
                            index = 9;
                            String policy_status = user_string("policy_status");
                            System.out.println("success value is: " + policy_status);
                            int success1 = database.updateCustomerPolicy(policy_id, intPlaceHolder, policy_status, index);
                            System.out.println("success value is: " + success1);
                            break;
                        }
                    }
                    break;
                }
                else if (menue_selection == 6) {
                    int customer_id = user_integer("customer_id");
                    success = database.getCustomerID(customer_id);
                    System.out.print("success value is: " + success);
                    if (customer_id == success) {
                        database.getAllCustomerPolicyList(customer_id);
                    }
                    break;
                }
                else if (menue_selection == 7) {
                    System.out.println("Enter existing policy ID: \n");
                    int policy_id = user_integer("policy_id");
                    success = database.getPolicyInfo(policy_id);
                    System.out.print("success value is: " + success);
                    break;
                }
                else if (menue_selection == 8) {
                    System.out.println("Enter existing policy ID: \n");
                    int policy_id = user_integer("policy_id");
                    success = database.getAllCustomersList(policy_id);
                    System.out.print("success value is: " + success);
                    break;
                }
                else if (menue_selection == 9) {
                    System.out.println("Enter existing agent ID: \n");
                    int agent_id = user_integer("agent_id");
                    success = database.getAdjusterAgent(agent_id);
                    System.out.print("success value is: " + success);
                    break;
                }
                else {
                    System.out.println("invalid input!");
                    input.next();
                }
            }
        }
    }
    
    /**
 * FUNCTIONS ASSOCIATED WITH AGENT_INTERFACE
 */

    /** 
     * getAgentID checks if agent id is in the database 
     */
    public int getAgentID(int agent_id) {
        int id = 0;
        try {
            checkAgentID.setInt(1, agent_id);
            ResultSet resultset = checkAgentID.executeQuery();
            while (resultset.next()) {
                id = resultset.getInt("agent_id");
                System.out.println("agent ID is: " + id);
            }
                resultset.close();
        }
        catch (SQLException exception) {
            System.out.println("agent ID invalid! No agent exists!");
        }
        return id;
    }

    /**
     * Function for getting customers associated with a specific agent
     */
    public int getAgentCustomers(int agent_id) {
        int id = 0;
        try {
            checkAgentCustomers.setInt(1, agent_id);
            ResultSet resultset = checkAgentCustomers.executeQuery();
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
                System.out.println("customer with overdue bill: " + customer_id);
            }
                resultset.close();
        }
        catch (SQLException exception) {
            System.out.println("Customer_id invalid! No customer exists!!");
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
     * Function for updating customer policy 
     */
    public int updateCustomerPolicy(int policy_id, int typeInt, String typeString, int index) {
        int success = 0;
        if (index == 1) {
            try {
                changePolicyType.setString(1, typeString);
                changePolicyType.setInt(2, policy_id);
                success = changePolicyType.executeUpdate();
            }
            catch (SQLException exception) {
                System.out.println("invalid input!");
            }
        }
        else if (index == 2) {
            try {
                changePolicyCost.setInt(1, typeInt);
                changePolicyCost.setInt(2, policy_id);
                success = changePolicyCost.executeUpdate();
            }
            catch (SQLException exception) {
                System.out.println("invalid input!");
            }
        }
        else if (index == 3) {
            try {
                changePolicyCoverage.setInt(1, typeInt);
                changePolicyCoverage.setInt(2, policy_id);
                success = changePolicyCoverage.executeUpdate();
            }
            catch (SQLException exception) {
                System.out.println("invalid input!");
            }
        }
        else if (index == 4) {
            try {
                changePolicyDeductible.setInt(1, typeInt);
                changePolicyDeductible.setInt(2, policy_id);
                success = changePolicyDeductible.executeUpdate();
            }
            catch (SQLException exception) {
                System.out.println("invalid input!");
            }
        }
        else if (index == 5) {
            try {
                changePolicyCoinsurance.setInt(1, typeInt);
                changePolicyCoinsurance.setInt(2, policy_id);
                success = changePolicyCoinsurance.executeUpdate();
            }
            catch (SQLException exception) {
                System.out.println("invalid input!");
            }
        }
        else if (index == 6) {
            try {
                changePolicyEffectiveDate.setString(1, typeString);
                changePolicyEffectiveDate.setInt(2, policy_id);
                success = changePolicyEffectiveDate.executeUpdate();
            }
            catch (SQLException exception) {
                System.out.println("invalid input!");
            }
        }
        else if (index == 7) {
            try {
                changePolicyExpireDate.setString(1, typeString);
                changePolicyExpireDate.setInt(2, policy_id);
                success = changePolicyExpireDate.executeUpdate();
            }
            catch (SQLException exception) {
                System.out.println("invalid input!");
            }
        }
        else if (index == 8) {
            try {
                changePolicyPlan.setString(1, typeString);
                changePolicyPlan.setInt(2, policy_id);
                success = changePolicyPlan.executeUpdate();
            }
            catch (SQLException exception) {
                System.out.println("invalid input!");
            }
        }
        else if (index == 9) {
            try {
                changePolicyStatus.setString(1, typeString);
                changePolicyStatus.setInt(2, policy_id);
                success = changePolicyStatus.executeUpdate();
            }
            catch (SQLException exception) {
                System.out.println("invalid input!");
            }
        }
        return success;
    }

    /**
     * Function for getting all polcies associated with a specific customer
     */
    public int getAllCustomerPolicyList(int customer_id) {
        int id = 0;
        try {
            getAllCustomerPolicy.setInt(1, customer_id);
            ResultSet resultset = getAllCustomerPolicy.executeQuery();
            while (resultset.next()) {
                id = resultset.getInt("policy_id");
                System.out.println("policy_id is: " + id);
            }
                resultset.close();
        }
        catch (SQLException exception) {
            System.out.println("Customer_id invalid! No customer exists!!");
        }
        return id;
    }

    /**
     * Function for getting all customers associated with a specific policy
     */
    public int getAllCustomersList(int policy_id) {
        int id = 0;
        try {
            getAllCustomers.setInt(1, policy_id);
            ResultSet resultset = getAllCustomers.executeQuery();
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
    public int getAdjusterAgent(int agent_id) {
        int id = 0;
        try {
            agentAdjuster.setInt(1, agent_id);
            ResultSet resultset = agentAdjuster.executeQuery();
            while (resultset.next()) {
                id = resultset.getInt("adjuster_id");
                System.out.println("adjuster_id is: " + id);
            }
                resultset.close();
        }
        catch (SQLException exception) {
            System.out.println("Customer_id invalid! No customer exists!!");
        }
        return id;
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
     * getPolicyID checks if policy id is in the database when user attempts to add a policy
     */
    public int getPolicyID(int policy_id) {
        int id = 0;
        try {
            checkPolicyID.setInt(1, policy_id);
            ResultSet resultset = checkPolicyID.executeQuery();
            while (resultset.next()) {
                id = resultset.getInt("policy_id");
                System.out.println("policy_id is: " + id);
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
                System.out.println("policy customer_id is: " + customer_id);
                System.out.println("policy policy_id is: " + policy_id);
                System.out.println("policy type is: " + type);
                System.out.println("policy cost is: " + cost);
                System.out.println("policy coverage is: " + coverage);
                System.out.println("policy deductible is: " + deductible);
                System.out.println("policy coinsurance is: " + coinsurance);
                System.out.println("policy effective_date is: " + effective_date);
                System.out.println("policy expire_date is: " + expire_date);
                System.out.println("policy plan is: " + plan);
                success = 1;
            }
                resultset.close();
        }
        catch (SQLException exception) {
            System.out.println("policy_id invalid! No policy exists!");
        }
        return success;
    }
}