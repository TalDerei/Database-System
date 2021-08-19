package customerObjects;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;

import manager.DBManager;

import java.sql.Connection;

public class Policy {
    
    public static PreparedStatement getPolicyInformation;
    private static PreparedStatement addPolicy; 
    private static PreparedStatement dropPolicy;
    private static PreparedStatement checkPolicyID;
    private static PreparedStatement dropAdditionalVehicle;
    private static PreparedStatement addHomeInsurance;
    public static PreparedStatement addAutoInsurance;
    public static PreparedStatement addHealthInsurance;
    public static PreparedStatement addLifeInsurance;
    public static PreparedStatement checkPolicyCost;
    public static PreparedStatement addAdditionalVehicle;
    public static PreparedStatement checkPolicyDate;
    public static PreparedStatement addDependant;

    /**
     * Policy parameters 
     */
    private int policy_id;


    /**
     * Constructor for dropping existing policy
     */
    public Policy() {

    }

    public Policy(int policy_id) {
        this.policy_id = policy_id;
    }

        /**
     * getPolicyInfo returns all information about a customer's existing policy 
     */
    public int getPolicyInfo(int policy_id) {
        Connection conn = DBManager.getConnection();

        try {
            Policy.getPolicyInformation = conn.prepareStatement("SELECT * FROM policy WHERE policy_id = ?");
        }
        catch (SQLException exception) {
            System.out.println("Error with Prepared Statements!");
        }
        
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
            System.out.println("Policy ID Invalid! No Policy Exists!");
        }
        return success;
    }

    /**
     * Check for existing customer policy ID
     */
    public static int getPolicyID(int policy_id) {
        Connection conn = DBManager.getConnection();

        try {
            Policy.checkPolicyID = conn.prepareStatement("SELECT policy_id FROM policy WHERE policy_id = ?");
        }
        catch (SQLException exception) {
            System.out.println("Error with Prepared Statements!");
        }

        int id = 0;
        try {
            checkPolicyID.setInt(1, policy_id);
            ResultSet resultset = checkPolicyID.executeQuery();
            while (resultset.next()) {
                id = resultset.getInt("policy_id");
            }
                resultset.close();
        }
        catch (SQLException exception) {
            System.out.println("Policy ID Invalid! No Policy Exists!");
        }
        return id;
    }

    /**
     * Drop policy object from the database
     * @param policy_id 
     * @return Boolean whether drop was successful
     */
    public static Boolean dropPolicy(int policy_id) {
        Connection conn = DBManager.getConnection();

        try {
            Policy.dropPolicy = conn.prepareStatement("DELETE FROM policy WHERE policy_id = ?");
        }
        catch (SQLException exception) {
            System.out.println("Error with Prepared Statements!");
        }

        try {
            dropPolicy.setInt(1, policy_id);
            dropPolicy.executeUpdate();
            return true;
        }
        catch (SQLException exception) {
            System.out.println("invalid input!");
        }
        return false;
    }

    /**
     * Insert poolicy object
     */
    public int insertGenericPolicy(int customer_id, int policy_id, String type, double cost, double coverage, double deductible, int coinsurance, String effective_date, String expire_date, String plan, String policy_status) {
        Connection conn = DBManager.getConnection();

        try {
            Policy.addPolicy = conn.prepareStatement("INSERT INTO policy (CUSTOMER_ID, POLICY_ID, TYPE, COST, COVERAGE, DEDUCTIBLE, COINSURANCE, EFFECTIVE_DATE, EXPIRE_DATE, PLAN, POLICY_STATUS) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
        }
        catch (SQLException exception) {
            System.out.println("Error with Prepared Statements!");
        }

        int success = 0;
        try {
            addPolicy.setInt(1, customer_id);
            addPolicy.setInt(2, policy_id);
            addPolicy.setString(3, type);
            addPolicy.setDouble(4, cost);
            addPolicy.setDouble(5, coverage);
            addPolicy.setDouble(6, deductible);
            addPolicy.setInt(7, coinsurance);
            addPolicy.setDate(8, Date.valueOf(effective_date));
            addPolicy.setDate(9, Date.valueOf(expire_date));
            addPolicy.setString(10, plan);
            addPolicy.setString(11, policy_status);
            success = addPolicy.executeUpdate();
        }
        catch (SQLException exception) {
            System.out.println("invalid input!");
        }
        return success;
    }

    /**
     * deleteAdditionalVehicle deletes additional vehicles from existing auto insurance policy 
     */
    public int deleteAdditionalVehicle(int vehicle_id) {
        Connection conn = DBManager.getConnection();

        try {
            Policy.dropAdditionalVehicle = conn.prepareStatement("DELETE FROM vehicle WHERE vehicle_id = ?");
        }
        catch (SQLException exception) {
            System.out.println("Error with Prepared Statements!");
        }

        int success = 0;
        try {
            dropPolicy.setInt(1, vehicle_id);
            success = dropAdditionalVehicle.executeUpdate();
            System.out.println("\nSuccessfully Deleted Additional Vehicle From Auto Insurance Policy!\n");
        }
        catch (SQLException exception) {
            System.out.println("Failed to Delete Additional Vehicle From Auto Insurance! Try Again!");
        }
        return success;
    }

    /**
     * Insert home insurance policy object
     */
    public int insertHomeInsurance(int policy_id, String city, String state, int zip_code, String address, int year_built, String condition, double square_foot, double lot_size, int credit_score, double mortgage_payment, double market_value, String personal_property_replacement, String plan_category) {
        Connection conn = DBManager.getConnection();

        try {
            Policy.addHomeInsurance = conn.prepareStatement("INSERT INTO home_insurance (POLICY_ID, CITY, STATE, ZIP_CODE, ADDRESS, YEAR_BUILT, CONDITION, SQUARE_FOOT, LOT_SIZE, CREDIT_SCORE, MORTGAGE_PAYMENT, MARKET_VALUE, PERSONAL_PROPERTY_REPLACEMENT, PLAN_CATEGORY) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
        }
        catch (SQLException exception) {
            System.out.println("Error with Prepared Statements!");
        }

        int success = 0;
        try {
            addHomeInsurance.setInt(1, policy_id);
            addHomeInsurance.setString(2, city);
            addHomeInsurance.setString(3, state);
            addHomeInsurance.setInt(4, zip_code);
            addHomeInsurance.setString(5, address);
            addHomeInsurance.setInt(6, year_built);
            addHomeInsurance.setString(7, condition);
            addHomeInsurance.setDouble(8, square_foot);
            addHomeInsurance.setDouble(9, lot_size);
            addHomeInsurance.setInt(10, credit_score);
            addHomeInsurance.setDouble(11, mortgage_payment);
            addHomeInsurance.setDouble(12, market_value);
            addHomeInsurance.setString(13, personal_property_replacement);
            addHomeInsurance.setString(14, plan_category);
            success = addHomeInsurance.executeUpdate();
        }
        catch (SQLException exception) {
            System.out.println("Failed to Add Home Insurance to Database! Something Went Wrong, Try Again!");
        }
        return success;
    }

    /**
     * insertAutoInsurance creates auto insurance policy
     */
    public int insertAutoInsurance(int policy_id, int year, String make, String model, String vin, String license_plate, String driver_license, int total_mileage, int annual_miles, int market_value, String age, String gender, int credit_score, String traffic_violations, int number_of_dependants, String state, String plan_category) {
        Connection conn = DBManager.getConnection();

        try {
            Policy.addAutoInsurance = conn.prepareStatement("INSERT INTO auto_insurance (POLICY_ID, YEAR, MAKE, MODEL, VIN, LICENSE_PLATE, DRIVER_LICENSE, TOTAL_MILEAGE, ANNUAL_MILES, MARKET_VALUE, AGE, GENDER, CREDIT_SCORE, NUMBER_OF_DEPENDANTS, TRAFFIC_VIOLATIONS, STATE, PLAN_CATEGORY) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
        }
        catch (SQLException exception) {
            System.out.println("Error with Prepared Statements!");
        }

        int success = 0;
        try {
            addAutoInsurance.setInt(1, policy_id);
            addAutoInsurance.setInt(2, year);
            addAutoInsurance.setString(3, make);
            addAutoInsurance.setString(4, model);
            addAutoInsurance.setString(5, vin);
            addAutoInsurance.setString(6, license_plate);
            addAutoInsurance.setString(7, driver_license);
            addAutoInsurance.setInt(8, total_mileage);
            addAutoInsurance.setInt(9, annual_miles);
            addAutoInsurance.setInt(10, market_value);
            addAutoInsurance.setDate(11, Date.valueOf(age));
            addAutoInsurance.setString(12, gender);
            addAutoInsurance.setInt(13, credit_score);
            addAutoInsurance.setInt(14, number_of_dependants);
            addAutoInsurance.setString(15, traffic_violations);
            addAutoInsurance.setString(16, state);
            addAutoInsurance.setString(17, plan_category);
            success = addAutoInsurance.executeUpdate();
        }
        catch (SQLException exception) {
            System.out.println("Failed to Add Auto Insurance to Database! Something Went Wrong, Try Again!");
        }
        return success;
    }

     /**
     * insertHealthInsurance creates health insurance policy
     */
    public int insertHealthInsurance(int policy_id, String plan_category, double out_of_pocket_maximum, String tobacco_use, String age, String pre_existing_conditions, int number_of_dependants, double estimated_copay) {
        Connection conn = DBManager.getConnection();

        try {
            Policy.addHealthInsurance = conn.prepareStatement("INSERT INTO health_insurance (POLICY_ID, PLAN_CATEGORY, OUT_OF_POCKET_MAXIMUM, TOBACCO_USE, AGE, PRE_EXISTING_CONDITIONS, NUMBER_OF_DEPENDANTS, ESTIMATED_COPAY) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
        }
        catch (SQLException exception) {
            System.out.println("Error with Prepared Statements!");
        }
        
        int success = 0;
        try {
            addHealthInsurance.setInt(1, policy_id);
            addHealthInsurance.setString(2, plan_category);
            addHealthInsurance.setDouble(3, out_of_pocket_maximum);
            addHealthInsurance.setString(4, tobacco_use);
            addHealthInsurance.setDate(5, Date.valueOf(age));
            addHealthInsurance.setString(6, pre_existing_conditions);
            addHealthInsurance.setInt(7, number_of_dependants);
            addHealthInsurance.setDouble(8, estimated_copay);
            success = addHealthInsurance.executeUpdate();
        }
        catch (SQLException exception) {
            System.out.println("Failed to Add Health Insurance to Database! Something Went Wrong, Try Again!");
        }
        return success;
    }

      /**
     * insertLifeInsurance creates life insurance policy
     */
    public int insertLifeInsurance(int policy_id, String plan_category, String age, String gender, String tobacco_use, String occupation, String medical_status, String family_medical_history, String beneficiary_name, int beneficiary_social_security) {
        Connection conn = DBManager.getConnection();

        try {
            Policy.addLifeInsurance = conn.prepareStatement("INSERT INTO life_insurance (POLICY_ID, PLAN_CATEGORY, AGE, GENDER, TOBACCO_USE, OCCUPATION, MEDICAL_STATUS, FAMILY_MEDICAL_HISTORY, BENEFICIARY_NAME, BENEFICIARY_SOCIAL_SECURITY) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
        }
        catch (SQLException exception) {
            System.out.println("Error with Prepared Statements!");
        }

        int success = 0;
        try {
            addLifeInsurance.setInt(1, policy_id);
            addLifeInsurance.setString(2, plan_category);
            addLifeInsurance.setDate(3, Date.valueOf(age));
            addLifeInsurance.setString(4, gender);
            addLifeInsurance.setString(5, tobacco_use);
            addLifeInsurance.setString(6, occupation);
            addLifeInsurance.setString(7, medical_status);
            addLifeInsurance.setString(8, family_medical_history);
            addLifeInsurance.setString(9, beneficiary_name);
            addLifeInsurance.setInt(10, beneficiary_social_security);
            success = addLifeInsurance.executeUpdate();
        }
        catch (SQLException exception) {
            System.out.println("Failed to Add Life Insurance to Database! Something Went Wrong, Try Again!");
        }
        return success;
    }

     /**
     * getPolicyCost checks policy cost associated with a customer's policy
     */
    public int getPolicyCost(int policy_id) {
        Connection conn = DBManager.getConnection();

        try {
            Policy.checkPolicyCost = conn.prepareStatement("SELECT cost FROM policy WHERE policy_id = ?");
        }
        catch (SQLException exception) {
            System.out.println("Error with Prepared Statements!");
        }

        int cost = 0;
        try {
            checkPolicyCost.setInt(1, policy_id);
            ResultSet resultset = checkPolicyCost.executeQuery();
            while (resultset.next()) {
                cost = resultset.getInt("cost");
                System.out.println("policy cost is: " + cost);
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
     * currentDate retrieves the current date based on internal system clock 
     */
    public java.sql.Date currentDate() {
        java.util.Date current = new java.util.Date();
        return new java.sql.Date(current.getTime());
    }

    /**
     * insertAdditionalVehicle inserts additional vehicles into existing auto insurance policy 
     */
    public int insertAdditionalVehicle(int policy_id, int vehicle_id, String extra_vehicle, int year, String make, String model, String vin, String license_plate, int total_mileage, int annual_miles, int market_value) {
        Connection conn = DBManager.getConnection();

        try {
            Policy.addAdditionalVehicle = conn.prepareStatement("INSERT INTO vehicle (POLICY_ID, VEHICLE_ID, EXTRA_VEHICLE, YEAR, MAKE, MODEL, VIN, LICENSE_PLATE, TOTAL_MILEAGE, ANNUAL_MILES, MARKET_VALUE) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
        }
        catch (SQLException exception) {
            System.out.println("Error with Prepared Statements!");
        }
        
        int success = 0;
        try {
            addAdditionalVehicle.setInt(1, policy_id);
            addAdditionalVehicle.setInt(2, vehicle_id);
            addAdditionalVehicle.setString(3, extra_vehicle);
            addAdditionalVehicle.setInt(4, year);
            addAdditionalVehicle.setString(5, make);
            addAdditionalVehicle.setString(6, model);
            addAdditionalVehicle.setString(7, vin);
            addAdditionalVehicle.setString(8, license_plate);
            addAdditionalVehicle.setInt(9, total_mileage);
            addAdditionalVehicle.setInt(10, annual_miles);
            addAdditionalVehicle.setInt(11, market_value);
            success = addAdditionalVehicle.executeUpdate();
            System.out.println("\nSuccessfully Added Additional Vehicle to Auto Insurance Policy!\n");
        }
        catch (SQLException exception) {
            System.out.println("Failed to Insert Additional Vehicle to Auto Insurance! Try Again!");
        }
        return success;
    }

    /**
     * getPolicyDate checks date associated with customer policy to determine policy payment is on time or overdue
     */
    public Date[] getPolicyDate(int policy_id) {
        Connection conn = DBManager.getConnection();

        try {
            Policy.checkPolicyDate = conn.prepareStatement("SELECT effective_date, expire_date FROM policy WHERE policy_id = ?");
        }
        catch (SQLException exception) {
            System.out.println("Error with Prepared Statements!");
        }
                
        Date[] dates = new Date[2];
        Date effective_date;
        Date expire_date;
        try {
            checkPolicyDate.setInt(1, policy_id);
            ResultSet resultset = checkPolicyDate.executeQuery();
            while (resultset.next()) {
                effective_date = resultset.getDate("effective_date");
                System.out.println("effective_date is: " + effective_date);
                expire_date = resultset.getDate("expire_date");
                System.out.println("expire_date is: " + expire_date);
                dates[0] = effective_date;
                dates[1] = expire_date;
            }
            try {
                resultset.close();
            }
            catch (SQLException exception) {
                System.out.println("Cannot close resultset!");
            }
        }
        catch (SQLException exception) {
            System.out.println("Failed to Retrive Policy Date! Try Again!");
        }
        return dates;
    }

    /**
     * insertDependant inserts new dependant associated with a customer policy
     */
    public int insertDependant(int dependant_id, int policy_id, String name, int social_security, String date_of_birth) {
        Connection conn = DBManager.getConnection();

        try {
            Policy.addDependant = conn.prepareStatement("INSERT INTO dependant (DEPENDANT_ID, POLICY_ID, NAME, SOCIAL_SECURITY, DATE_OF_BIRTH) VALUES (?, ?, ?, ?, ?)");
        }
        catch (SQLException exception) {
            System.out.println("Error with Prepared Statements!");
        }
        
        int success = 0;
        try {
            addDependant.setInt(1, dependant_id);
            addDependant.setInt(2, policy_id);
            addDependant.setString(3, name);
            addDependant.setInt(4, social_security);
            addDependant.setDate(5, Date.valueOf(date_of_birth));
            success = addDependant.executeUpdate();
        }
        catch (SQLException exception) {
            System.out.println("Failed to Insert Dependant! Try Again!");
        }
        return success;
    }



}
