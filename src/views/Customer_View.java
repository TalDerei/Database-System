package views;

import java.sql.*;
import java.text.*;
import java.util.Scanner;
import java.time.LocalDate;
import java.util.Calendar;

public class Customer_View {
    /**
     * Prepared Statments associated with customer interface
     */
    public static PreparedStatement createCustomer;
    public static PreparedStatement checkCustomerID;
    public static PreparedStatement addPolicy; 
    public static PreparedStatement addHomeInsurance;
    public static PreparedStatement addAutoInsurance;
    public static PreparedStatement addHealthInsurance;
    public static PreparedStatement addLifeInsurance;
    public static PreparedStatement dropPolicy;
    public static PreparedStatement addClaim;
    public static PreparedStatement dropClaim;
    public static PreparedStatement checkPolicyCost;
    public static PreparedStatement checkPolicyDate;
    public static PreparedStatement policyPayment;
    public static PreparedStatement addDebitCard;
    public static PreparedStatement addCreditCard;
    public static PreparedStatement getPolicyInformation;
    public static PreparedStatement checkClaimID;
    public static PreparedStatement checkClaimStatus;
    public static PreparedStatement checkPolicyID;
    public static PreparedStatement addDependant;
    public static PreparedStatement addAdditionalVehicle;
    public static PreparedStatement dropAdditionalVehicle;

    /**
     * Cached Prepared Statements for agent interfaces
     */
    public static void prepareStatements(Connection conn) {
        try {
            Customer_View.createCustomer = conn.prepareStatement("INSERT INTO customer (CUSTOMER_ID, NAME, SOCIAL_SECURITY, EMAIL, ZIP_CODE, CITY, STATE, ADDRESS, DATE_OF_BIRTH, PHONE_NUMBER) VALUES (?,?,?,?,?,?,?,?,?,?)");
            Customer_View.dropPolicy = conn.prepareStatement("DELETE FROM policy WHERE policy_id = ?");
            Customer_View.addClaim = conn.prepareStatement("INSERT INTO claim (CLAIM_ID, CLAIM_TYPE, ACCIDENT, ITEMS_DAMAGED, DESCRIPTION, DECISION, ADJUSTER_NOTES, AMOUNT, CLAIM_STATUS, POLICY_ID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            Customer_View.checkCustomerID = conn.prepareStatement("SELECT customer_id FROM customer WHERE customer_id = ?");
            Customer_View.addPolicy = conn.prepareStatement("INSERT INTO policy (CUSTOMER_ID, POLICY_ID, TYPE, COST, COVERAGE, DEDUCTIBLE, COINSURANCE, EFFECTIVE_DATE, EXPIRE_DATE, PLAN, POLICY_STATUS) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            Customer_View.addHomeInsurance = conn.prepareStatement("INSERT INTO home_insurance (POLICY_ID, CITY, STATE, ZIP_CODE, ADDRESS, YEAR_BUILT, CONDITION, SQUARE_FOOT, LOT_SIZE, CREDIT_SCORE, MORTGAGE_PAYMENT, MARKET_VALUE, PERSONAL_PROPERTY_REPLACEMENT, PLAN_CATEGORY) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            Customer_View.addAutoInsurance = conn.prepareStatement("INSERT INTO auto_insurance (POLICY_ID, YEAR, MAKE, MODEL, VIN, LICENSE_PLATE, DRIVER_LICENSE, TOTAL_MILEAGE, ANNUAL_MILES, MARKET_VALUE, AGE, GENDER, CREDIT_SCORE, NUMBER_OF_DEPENDANTS, TRAFFIC_VIOLATIONS, STATE, PLAN_CATEGORY) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            Customer_View.addHealthInsurance = conn.prepareStatement("INSERT INTO health_insurance (POLICY_ID, PLAN_CATEGORY, OUT_OF_POCKET_MAXIMUM, TOBACCO_USE, AGE, PRE_EXISTING_CONDITIONS, NUMBER_OF_DEPENDANTS, ESTIMATED_COPAY) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
            Customer_View.checkPolicyCost = conn.prepareStatement("SELECT cost FROM policy WHERE policy_id = ?");
            Customer_View.policyPayment = conn.prepareStatement("INSERT INTO policy_payment (PAYMENT_ID, POLICY_ID, RECIPIENT_NAME, RECIPIENT_ADDRESS, BANK, PAYMENT_AMOUNT, PAYMENT_DATE, STATUS) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
            Customer_View.addCreditCard = conn.prepareStatement("INSERT INTO credit (PAYMENT_ID, TYPE, CARD_NUMBER, CVV, POLICY_ID, EXPIARY_DATE) VALUES (?, ?, ?, ?, ?, ?)");
            Customer_View.addDebitCard = conn.prepareStatement("INSERT INTO debit (PAYMENT_ID, TYPE, CARD_NUMBER, CVV, POLICY_ID, EXPIARY_DATE) VALUES (?, ?, ?, ?, ?, ?)");
            Customer_View.checkClaimStatus = conn.prepareStatement("SELECT decision FROM claim WHERE claim_id = ?");
            Customer_View.addDependant = conn.prepareStatement("INSERT INTO dependant (DEPENDANT_ID, POLICY_ID, NAME, SOCIAL_SECURITY, DATE_OF_BIRTH) VALUES (?, ?, ?, ?, ?)");
            Customer_View.checkClaimID = conn.prepareStatement("SELECT claim_id FROM claim WHERE claim_id = ?");
            Customer_View.addAdditionalVehicle = conn.prepareStatement("INSERT INTO vehicle (POLICY_ID, VEHICLE_ID, EXTRA_VEHICLE, YEAR, MAKE, MODEL, VIN, LICENSE_PLATE, TOTAL_MILEAGE, ANNUAL_MILES, MARKET_VALUE) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            Customer_View.dropAdditionalVehicle = conn.prepareStatement("DELETE FROM vehicle WHERE vehicle_id = ?");
            Customer_View.addLifeInsurance = conn.prepareStatement("INSERT INTO life_insurance (POLICY_ID, PLAN_CATEGORY, AGE, GENDER, TOBACCO_USE, OCCUPATION, MEDICAL_STATUS, FAMILY_MEDICAL_HISTORY, BENEFICIARY_NAME, BENEFICIARY_SOCIAL_SECURITY) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            Customer_View.checkPolicyDate = conn.prepareStatement("SELECT effective_date, expire_date FROM policy WHERE policy_id = ?");
            Customer_View.getPolicyInformation = conn.prepareStatement("SELECT * FROM policy WHERE policy_id = ?");
            Customer_View.checkPolicyID = conn.prepareStatement("SELECT policy_id FROM policy WHERE policy_id = ?");
        }
        catch (SQLException exception) {
            System.out.println("Error with Prepared Statements!");
        }
    }

    /**
     * insertCustomer inserts new customer
     */
    public static int insertCustomer(int customer_id, String name, int social_security, String email, int zip_code, String city, String state, String address, String phone_number, String date_of_birth) {
        int success = 0;
        try {
            createCustomer.setInt(1, customer_id);
            createCustomer.setString(2, name);
            createCustomer.setInt(3, social_security);
            createCustomer.setString(4, email);
            createCustomer.setInt(5, zip_code);
            createCustomer.setString(6, city);
            createCustomer.setString(7, state);
            createCustomer.setString(8, address);
            createCustomer.setDate(9, Date.valueOf(date_of_birth));
            createCustomer.setString(10, phone_number);
            success = createCustomer.executeUpdate();
            System.out.println("\nSuccessfully Added Customer to Database!\n");
        }
        catch (SQLException exception) {
            System.out.println("Failed to Add Customer to Database! Something Went Wrong, Try Again!");
        }
        return success;
    }

    /**
     * deletePolicy deletes existing policy
     */
    public static int deletePolicy(int policy_id) {
        int success = 0;
        try {
            dropPolicy.setInt(1, policy_id);
            success = dropPolicy.executeUpdate();
        }
        catch (SQLException exception) {
            System.out.println("invalid input!");
        }
        return success;
    }

    /**
     * insertClaim inserts claim associated with a policy
     */
    public static int insertClaim(int claim_id, String claim_type, String accident, String items_damaged, String description, String outcome, String adjuster_notes, double amount, String claim_status, int policy_id) {
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
     * user_integer provides input checking for integers
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
                    System.out.println("Entered string! Please enter an integer and try again!");
                    conditional = true;
                    input.next();
                }
            }
        return 0;
    }
    
     /**
     * user_string provides input checking for strings
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
                    System.out.println("Entered integer! Please enter a string and try again!");
                    conditional = true;
                    input.next();
                }
            }
        return "0";
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
     * currentDate retrieves the current date based on internal system clock 
     */
    public static java.sql.Date currentDate() {
        java.util.Date current = new java.util.Date();
        return new java.sql.Date(current.getTime());
    }

    /**
     * getCustomerID checks if customer ID is in the database when user attempts to add a policy
     */
    public static int getCustomerID(int customer_id) {
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
            System.out.println("Customer ID invalid! No customer exists!!");
        }
        return id;
    }

    /**
     * insertGenericPolicy inserts a top-level policy in the policy table
     */
    public static int insertGenericPolicy(int customer_id, int policy_id, String type, double cost, double coverage, double deductible, int coinsurance, String effective_date, String expire_date, String plan, String policy_status) {
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
     * insertHomeInsurance creates home insurance policy
     */
    public static int insertHomeInsurance(int policy_id, String city, String state, int zip_code, String address, int year_built, String condition, double square_foot, double lot_size, int credit_score, double mortgage_payment, double market_value, String personal_property_replacement, String plan_category) {
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
    public static int insertAutoInsurance(int policy_id, int year, String make, String model, String vin, String license_plate, String driver_license, int total_mileage, int annual_miles, int market_value, String age, String gender, int credit_score, String traffic_violations, int number_of_dependants, String state, String plan_category) {
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
    public static int insertHealthInsurance(int policy_id, String plan_category, double out_of_pocket_maximum, String tobacco_use, String age, String pre_existing_conditions, int number_of_dependants, double estimated_copay) {
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
    public static int insertLifeInsurance(int policy_id, String plan_category, String age, String gender, String tobacco_use, String occupation, String medical_status, String family_medical_history, String beneficiary_name, int beneficiary_social_security) {
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
     * getCustomerId checks if customer ID exists in the database
     */
    public static int getCustomerId(int customer_id) {
        int success = 0;
        try {
            checkCustomerID.setInt(1, customer_id);
            ResultSet resultset = checkCustomerID.executeQuery();
            while (resultset.next()) {
                success = resultset.getInt("customer_id");
            }
            try {
                resultset.close();
            }
            catch (SQLException exception) {
                System.out.println("Cannot Close Resultset!");
            }
        }
        catch (SQLException exception) {
            System.out.println("Customer ID Unvalid! No customer Exists with ID: " + customer_id);
        }
        return success;
    }

    /**
     * getPolicyCost checks policy cost associated with a customer's policy
     */
    public static int getPolicyCost(int policy_id) {
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
     * getPolicyDate checks date associated with customer policy to determine policy payment is on time or overdue
     */
    public static Date[] getPolicyDate(int policy_id) {
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
     * makePolicyPayment allows user to make policy payment
     */
    public static int makePolicyPayment(int payment_id, int policy_id, String recipient_name, String recipient_address, String bank, int payment_amount, String status) {
        int success = 0;
        try {
            policyPayment.setInt(1, payment_id);
            policyPayment.setInt(2, policy_id);
            policyPayment.setString(3, recipient_name);
            policyPayment.setString(4, recipient_address);
            policyPayment.setString(5, bank);
            policyPayment.setInt(6, payment_amount);
            policyPayment.setDate(7, currentDate());
            policyPayment.setString(8, status);
            success = policyPayment.executeUpdate();
        }
        catch (SQLException exception) {
            System.out.println("Invalid Input!");
        }
        return success;
    }

    /**
     * payDebitCard makes a debit payment to policy
     */
    public static int payDebitCard(int payment_id, String type, long card_number, int cvv, int policy_id, int expiary_date) {
        int success = 0;
        try {
            addDebitCard.setInt(1, payment_id);
            addDebitCard.setString(2, type);
            addDebitCard.setLong(3, card_number);
            addDebitCard.setInt(4, cvv);
            addDebitCard.setInt(5, policy_id);
            addDebitCard.setInt(6, expiary_date);
            success = addDebitCard.executeUpdate();
        }
        catch (SQLException exception) {
            System.out.println("Something Went Wrong Trying to Make a Policy Payment with Debit!");
        }
        return success;
    }

     /**
     * payCreditCard makes a credit payment to policy
     */
    public static int payCreditCard(int payment_id, String type, long card_number, int cvv, int policy_id, int expiary_date) {
        int success = 0;
        try {
            addCreditCard.setInt(1, payment_id);
            addCreditCard.setString(2, type);
            addCreditCard.setLong(3, card_number);
            addCreditCard.setInt(4, cvv);
            addCreditCard.setInt(5, policy_id);
            addCreditCard.setInt(6, expiary_date);
            success = addCreditCard.executeUpdate();
        }
        catch (SQLException exception) {
            System.out.println("Something Went Wrong Trying to Make a Policy Payment with Credit!");
        }
        return success;
    }

    /**
     * getPolicyInfo returns all information about a customer's existing policy 
     */
    public static int getPolicyInfo(int policy_id) {
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
     * checkClaimID checks if claim ID exists in the database 
     */
    public static int getClaimID(int claim_id) {
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
     * getClaimStatus checks customer's claim status
     */
    public static String getClaimStatus(int claim_id) {
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
     * getPolicyID checks if policy ID exists in the database
     */
    public static int getPolicyID(int policy_id) {
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
     * insertDependant inserts new dependant associated with a customer policy
     */
    public static int insertDependant(int dependant_id, int policy_id, String name, int social_security, String date_of_birth) {
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

    /**
     * insertAdditionalVehicle inserts additional vehicles into existing auto insurance policy 
     */
    public static int insertAdditionalVehicle(int policy_id, int vehicle_id, String extra_vehicle, int year, String make, String model, String vin, String license_plate, int total_mileage, int annual_miles, int market_value) {
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
     * deleteAdditionalVehicle deletes additional vehicles from existing auto insurance policy 
     */
    public static int deleteAdditionalVehicle(int vehicle_id) {
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
}
