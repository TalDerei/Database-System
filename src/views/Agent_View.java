package views;

import java.util.Scanner;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Calendar;
import java.sql.*;
import java.text.*;

public class Agent_View {
    /**
     * Prepared Statments associated with agent interface
     */
    private static PreparedStatement checkAgentID;
    private static PreparedStatement checkAgentCustomers;
    private static PreparedStatement overdueBills;
    private static PreparedStatement pendingClaims;
    private static PreparedStatement estimatedRevenue;
    private static PreparedStatement changePolicyType;
    private static PreparedStatement changePolicyCost;
    private static PreparedStatement changePolicyCoverage;
    private static PreparedStatement changePolicyDeductible;
    private static PreparedStatement changePolicyCoinsurance;
    private static PreparedStatement changePolicyEffectiveDate;
    private static PreparedStatement changePolicyExpireDate;
    private static PreparedStatement changePolicyStatus;
    private static PreparedStatement getAllCustomerPolicy;
    private static PreparedStatement getAllCustomers;
    private static PreparedStatement agentAdjuster;
    public static PreparedStatement checkPolicyID;
    public static PreparedStatement checkCustomerID;
    public static PreparedStatement getPolicyInformation;

    public void prepare(Connection conn) {
        /**
         * Cached Prepared Statements for agent interface
         */
        try {
            Agent_View.checkAgentID = conn.prepareStatement("SELECT agent_id FROM agent WHERE agent_id = ?");
            Agent_View.checkAgentCustomers = conn.prepareStatement("SELECT customer_id FROM customer_agent WHERE agent_id = ?");
            Agent_View.overdueBills = conn.prepareStatement("SELECT customer_id FROM customer NATURAL JOIN policy NATURAL JOIN policy_payment WHERE status = 'LATE'");
            Agent_View.pendingClaims = conn.prepareStatement("SELECT customer_id FROM customer NATURAL JOIN claim NATURAL JOIN claim_payment NATURAL JOIN policy WHERE claim_status = 'Pending'");
            Agent_View.estimatedRevenue = conn.prepareStatement("SELECT cost FROM customer_agent NATURAL JOIN policy WHERE agent_id = ?");
            Agent_View.checkPolicyID = conn.prepareStatement("SELECT policy_id FROM policy WHERE policy_id = ?");
            Agent_View.changePolicyType = conn.prepareStatement("UPDATE policy SET type = ? WHERE policy_id = ?");
            Agent_View.changePolicyCost = conn.prepareStatement("UPDATE policy SET cost = ? WHERE policy_id = ?");
            Agent_View.changePolicyCoverage = conn.prepareStatement("UPDATE policy SET coverage = ? WHERE policy_id = ?");
            Agent_View.changePolicyDeductible = conn.prepareStatement("UPDATE policy SET deductible = ? WHERE policy_id = ?");
            Agent_View.changePolicyCoinsurance = conn.prepareStatement("UPDATE policy SET coinsurance = ? WHERE policy_id = ?");
            Agent_View.changePolicyEffectiveDate = conn.prepareStatement("UPDATE policy SET effective_date = ? WHERE policy_id = ?");
            Agent_View.changePolicyExpireDate = conn.prepareStatement("UPDATE policy SET expire_date = ? WHERE policy_id = ?");
            Agent_View.changePolicyStatus = conn.prepareStatement("UPDATE policy SET policy_status = ? WHERE policy_id = ?");
            Agent_View.checkCustomerID = conn.prepareStatement("SELECT customer_id FROM customer WHERE customer_id = ?");
            Agent_View.getAllCustomerPolicy = conn.prepareStatement("SELECT policy_id FROM policy WHERE customer_id = ?");
            Agent_View.getPolicyInformation = conn.prepareStatement("SELECT * FROM policy WHERE policy_id = ?");
            Agent_View.getAllCustomers = conn.prepareStatement("SELECT customer_id FROM policy WHERE policy_id = ?");
            Agent_View.agentAdjuster = conn.prepareStatement("SELECT adjuster_id FROM communicates WHERE agent_id = ?");
        }
        catch (SQLException exception) {
            System.out.println("Error with Prepared Statements!");
        }
    }

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
    public String checkPaymentStatus(Calendar calendar, LocalDate localdate) {
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
    public Boolean validateDate(String date_of_birth) {
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
