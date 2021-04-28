/**
 * Course: CSE 341
 * Semester and Year: Spring 2021
 * Assignment: Database Systems
 * Author: Derei, Tal
 * User ID: tad222
 */

import java.util.Scanner;
import java.util.ArrayList;
import java.sql.Date;
import java.sql.Date;
import java.util.*;
import java.text.*;
import java.sql.*;

/**
 * Insurance database for executing SQL queries using JBDC
 */
public class Insurance {
      /**
     * Establish a connection to the Oracle database
     */
    public static Insurance connect_database(String user, String pass) {
        Insurance database = new Insurance();
        try (Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@edgar1.cse.lehigh.edu:1521:cse241",user,pass);
        ) {
            System.out.println("Connection to the oracle database succeeded!\n");
            connection.setAutoCommit(false);
            database.connect = connection;
            customer_interface(database, connection);        
        } catch (SQLException exception) {
            System.out.println("connection to the oracle database failed! Try again!");
            return null;
        }
         return database;
    }

    /**
     * Prepared Statments associated with customer interface
     */
    private Connection connect;
    private PreparedStatement createCustomer;
    private PreparedStatement checkCustomerID;
    private PreparedStatement addPolicy;
    private PreparedStatement addHomeInsurance;
    private PreparedStatement addAutoInsurance;
    private PreparedStatement addHealthInsurance;
    private PreparedStatement addLifeInsurance;
    private PreparedStatement dropPolicy;
    private PreparedStatement addClaim;
    private PreparedStatement dropClaim;
    private PreparedStatement checkPolicyCost;
    private PreparedStatement policyPayment;
    private PreparedStatement addDebitCard;
    private PreparedStatement addCreditCard;
    private PreparedStatement getPolicyInformation;
    private PreparedStatement checkClaimStatus;
    private PreparedStatement addDependant;
    private PreparedStatement addVehicle;
    private PreparedStatement dropVehicle;

    /**
     * Command-line interface for customers
     */
    public static void customer_interface(Insurance database, Connection connection) {
        /**
         * Prepared Statements for customer interface
         */
        try {
            database.createCustomer = database.connect.prepareStatement("INSERT INTO customer (CUSTOMER_ID, NAME, SOCIAL_SECURITY, EMAIL, ZIP_CODE, CITY, STATE, ADDRESS, PHONE_NUMBER, DATE_OF_BIRTH) VALUES (?,?,?,?,?,?,?,?,?,?)");
            database.checkCustomerID = database.connect.prepareStatement("SELECT customer_id FROM customer WHERE customer_id = ?");
            database.addPolicy = database.connect.prepareStatement("INSERT INTO policy (CUSTOMER_ID, POLICY_ID, TYPE, COST, COVERAGE, DEDUCTIBLE, COINSURANCE, EFFECTIVE_DATE, EXPIRE_DATE, PLAN) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            database.addHomeInsurance = database.connect.prepareStatement("INSERT INTO home_insurance (POLICY_ID, CITY, STATE, ZIP_CODE, ADDRESS, YEAR_BUILT, CONDITION, SQUARE_FOOT, LOT_SIZE, CREDIT_SCORE, MORTGAGE_PAYMENT, MARKET_VALUE, PERSONAL_PROPERTY_REPLACEMENT) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            database.addAutoInsurance= database.connect.prepareStatement("INSERT INTO auto_insurance (POLICY_ID, YEAR, MAKE, MODEL, VIN, LICENSE_PLATE, DRIVER_LICENSE, TOTAL_MILEAGE, ANNUAL_MILES, MARKET_VALUE, AGE, GENDER, CREDIT_SCORE, NUMBER_OF_DEPENDANTS, TRAFFIC_VIOLATIONS, STATE) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            database.addHealthInsurance = database.connect.prepareStatement("INSERT INTO health_insurance (POLICY_ID, PLAN_CATEGORY, OUT_OF_POCKET_MAXIMUM, TOBACCO_USE, AGE, PRE_EXISTING_CONDITIONS, NUMBER_OF_DEPENDANTS, ESTIMATED_COPAY) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
            database.addLifeInsurance = database.connect.prepareStatement("INSERT INTO life_insurance (POLICY_ID, PLAN_CATEGORY, AGE, GENDER, TOBACCO_USE, OCCUPATION, MEDICAL_STATUS, FAMILY_MEDICAL_HISTORY, BENEFICIARY_NAME, BENEFICIARY_SOCIAL_SECURITY) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            database.dropPolicy = database.connect.prepareStatement("DELETE FROM policy WHERE POLICY_ID = ?");
            database.addClaim = database.connect.prepareStatement("INSERT INTO claim (CLAIM_ID, CUSTOMER_ID, CLAIM_TYPE, ACCIDENT, ITEMS_DAMAGED, DESCRIPTION, OUTCOME, ADJUSTER_NOTES, AMOUNT) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
            database.dropClaim = database.connect.prepareStatement("DELETE FROM claim WHERE claim_id = ?");
            database.checkPolicyCost = database.connect.prepareStatement("SELECT cost FROM policy WHERE policy_id = ?");
            database.policyPayment = database.connect.prepareStatement("INSERT INTO policy_payment (PAYMENT_ID, POLICY_ID, RECIPIENT_NAME, RECIPIENT_ADDRESS, BANK, PAYMENT_AMOUNT, PAYMENT_DATE) VALUES (?, ?, ?, ?, ?, ?, ?)");
            database.addDebitCard = database.connect.prepareStatement("INSERT INTO debit (PAYMENT_ID, TYPE, CARD_NUMBER, CVV, POLICY_ID, EXPIARY_DATE) VALUES (?, ?, ?, ?, ?, ?)");
            database.addCreditCard = database.connect.prepareStatement("INSERT INTO credit (PAYMENT_ID, TYPE, CARD_NUMBER, CVV, POLICY_ID, EXPIARY_DATE) VALUES (?, ?, ?, ?, ?, ?)");
            database.getPolicyInformation = database.connect.prepareStatement("SELECT * FROM WHERE policy_id = ?");
            database.checkClaimStatus = database.connect.prepareStatement("SELECT decision FROM claim WHERE claim_id = ? or policy_id = ?");
            database.addDependant = database.connect.prepareStatement("INSERT INTO credit (DEPENDANT_ID, POLICY_ID, NAME, SOCIAL_SECURITY, DATE_OF_BIRTH) VALUES (?, ?, ?, ?, ?)");
            database.addVehicle = database.connect.prepareStatement("INSERT INTO vehicle (POLICY_ID, VEHICLE_ID, EXTRA_VEHICLE, VEHICLE_TYPE, YEAR, MAKE, MODEL, VIN, LICENSE_PLATE, TOTAL_MILEAGE, ANNUAL_MILES, MARKET_VALUE) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            database.dropVehicle = database.connect.prepareStatement("DELETE FROM vehicle WHERE vehicle_id = ?");
        }
        catch (SQLException exception) {
            System.out.println("Error with Prepared Statements!");
        }
        /**
         * Command-line interface for customer interface
         */
        System.out.println("[1] Create a New Customer\n");
        System.out.println("[2] Add a Policy\n");
        System.out.println("[3] Drop a Policy\n");
        System.out.println("[4] Add a Claim\n");
        System.out.println("[5] Drop a Claim\n");
        System.out.println("[6] Make a Policy Payment\n");
        System.out.println("[7] Get Information on Existing Policy\n");
        System.out.println("[8] Check Claim Status\n");
        System.out.println("[9] Add Dependant to Policy\n");
        System.out.println("[10] Add/Drop a Vehicle\n");

        Scanner input = new Scanner(System.in);
        int menue_selection;
        int success; 
        String date_of_birth = "";
        boolean valid_start_date = true;
        while (true) {
            boolean conditional = input.hasNextInt();
            if (conditional) {
                menue_selection = input.nextInt();
                if (menue_selection == 1) {
                    int customer_id = user_integer("customer_id");
                    String name = user_string("name");
                    int social_security = user_integer("social_security");
                    System.out.print("Enter start date in Oracle standard format (yyyy-mm-dd): ");
                    while (valid_start_date) {
                        date_of_birth = input.next();
                        valid_start_date = validateDate(date_of_birth);
                    } 
                    String email = user_string("email");
                    int zip_code = user_integer("zip_code");
                    String city = user_string("city");
                    String state = user_string("state");
                    String address = user_string("address");
                    long phone_number = user_integer("phone_number");
                    success = database.insertCustomer(customer_id, name, social_security, email, zip_code, city, state, address, phone_number, date_of_birth);
                    System.out.print("success value is: " + success);
                    break;
                }
                else if (menue_selection == 2) {
                    int customer_id = user_integer("customer_id");
                    success = database.getCustomerID(customer_id);
                    System.out.print("success value is: " + success);
                    System.out.print("\n");
                    if (customer_id == success) {
                        while (true) {
                            System.out.println("Select an insurance policy from the list below: \n");
                            System.out.println("[1] Home Insurance\n");
                            System.out.println("[2] Auto Insurance\n");
                            System.out.println("[3] Health Insurance\n");
                            System.out.println("[4] Life Insurance\n");
                            conditional = input.hasNextInt();
                            if (conditional) {
                                menue_selection = input.nextInt();
                                if (menue_selection == 1) {
                                        System.out.print("Home Insurance\n");
                                        int policy_id = user_integer("policy_id");
                                        String city = user_string("city");
                                        String state = user_string("state");
                                        int zip_code = user_integer("zip_code");
                                        String address = user_string("address");
                                        int year_built = user_integer("year_built");
                                        String condition = user_string("condition");
                                        int square_foot = user_integer("square_foot");
                                        int lot_size = user_integer("lot_size");
                                        int credit_score = user_integer("credit_score");
                                        int mortgage_payment = user_integer("mortgage_payment");
                                        int market_value = user_integer("market_value");
                                        String personal_property_replacement = user_string("personal_property_replacement");
                                        int success2 = database.insertHomeInsurance(policy_id, city, state, zip_code, address, year_built, condition, square_foot, lot_size, credit_score, mortgage_payment, market_value, personal_property_replacement);
                                        System.out.println("home_insurance success value is: " + success2);
                                        System.out.println("customer_id is: " + customer_id);
                                        String type = user_string("type");
                                        int cost = user_integer("cost");
                                        int coverage = user_integer("coverage");
                                        int deductible = user_integer("deductible");
                                        int coinsurance = user_integer("coinsurance");
                                        String effective_date = user_string("effective_date");
                                        String expire_date = user_string("expire_date");
                                        String plan = user_string("plan");
                                        int success1 = database.insertGenericPolicy(customer_id, policy_id, type, cost, coverage, deductible, coinsurance, effective_date, expire_date, plan);
                                        System.out.println("policy success value is: " + success1);
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
                                else if (menue_selection == 2) {
                                    System.out.print("Auto Insurance\n");
                                    int policy_id = user_integer("policy_id");
                                    int year = user_integer("year");
                                    String make = user_string("make");
                                    String model = user_string("model");
                                    String vin = user_string("vin");
                                    String license_plate = user_string("license_plate");
                                    String driver_license = user_string("driver_license");
                                    int total_mileage = user_integer("total_mileage");
                                    int annual_miles = user_integer("annual_miles");
                                    int market_value = user_integer("market_value");
                                    date_of_birth = user_string("date_of_birth");
                                    String gender = user_string("gender");
                                    int credit_score = user_integer("credit_score");
                                    String traffic_violations = user_string("traffic_violations");
                                    int number_of_dependants = user_integer("number_of_dependants");
                                    String state = user_string("state");
                                    int success4 = database.insertAutoInsurance(policy_id, year, make, model, vin, license_plate, driver_license, total_mileage, annual_miles, market_value, date_of_birth, gender, credit_score, traffic_violations, number_of_dependants, state);
                                    System.out.println("home_insurance success value is: " + success4);
                                    System.out.println("customer_id is: " + customer_id);
                                    policy_id = user_integer("policy_id");
                                    String type = user_string("type");
                                    int cost = user_integer("cost");
                                    int coverage = user_integer("coverage");
                                    int deductible = user_integer("deductible");
                                    int coinsurance = user_integer("coinsurance");
                                    String effective_date = user_string("effective_date");
                                    String expire_date = user_string("expire_date");
                                    String plan = user_string("plan");
                                    int success3 = database.insertGenericPolicy(customer_id, policy_id, type, cost, coverage, deductible, coinsurance, effective_date, expire_date, plan);
                                    System.out.println("policy success value is: " + success3);
                                    try{
                                        if (success3 + success4 == 2) {
                                            database.connect.commit();
                                            System.out.println("transaction SUCCEEDED!\n");
                                        }
                                    }
                                    catch (SQLException exception) {
                                        System.out.println("transaction FAILED! ROLLED BACK!\n");
                                    }
                                    break;
                                }
                                else if (menue_selection == 3) {
                                    System.out.print("Health Insurance\n");
                                    int policy_id = user_integer("policy_id");
                                    String plan_category = user_string("plan_category");
                                    int out_of_pocket_maximum = user_integer("out_of_pocket_maximum");
                                    String tobacco_use = user_string("tobacco_use");
                                    String age = user_string("age");
                                    String pre_existing_conditions = user_string("pre_existing_conditions");
                                    int number_of_dependants = user_integer("number_of_dependants");
                                    int estimated_copay = user_integer("estimated_copay");
                                    int success6 = database.insertHealthInsurance(policy_id, plan_category, out_of_pocket_maximum, tobacco_use, age, pre_existing_conditions, number_of_dependants, estimated_copay);
                                    System.out.println("home_insurance success value is: " + success6);
                                    System.out.println("customer_id is: " + customer_id);
                                    policy_id = user_integer("policy_id");
                                    String type = user_string("type");
                                    int cost = user_integer("cost");
                                    int coverage = user_integer("coverage");
                                    int deductible = user_integer("deductible");
                                    int coinsurance = user_integer("coinsurance");
                                    String effective_date = user_string("effective_date");
                                    String expire_date = user_string("expire_date");
                                    String plan = user_string("plan");
                                    int success5 = database.insertGenericPolicy(customer_id, policy_id, type, cost, coverage, deductible, coinsurance, effective_date, expire_date, plan);
                                    System.out.println("policy success value is: " + success5);
                                    try{
                                        if (success5 + success6 == 2) {
                                            database.connect.commit();
                                            System.out.println("transaction SUCCEEDED!\n");
                                        }
                                    }
                                    catch (SQLException exception) {
                                        System.out.println("transaction FAILED! ROLLED BACK!\n");
                                    }
                                    break;
                                }
                                else if (menue_selection == 4) {
                                    System.out.print("Life Insurance\n");
                                    int policy_id = user_integer("policy_id");
                                    String plan_category = user_string("plan_category");
                                    String age = user_string("age");
                                    String gender = user_string("gender");
                                    String tobacco_use = user_string("tobacco_use");
                                    String occupation = user_string("occupation");
                                    String medical_status = user_string("medical_status");
                                    String family_medical_history = user_string("family_medical_history");
                                    String beneficiary_name = user_string("beneficiary_name");
                                    int beneficiary_social_security = user_integer("beneficiary_social_security");
                                    int success8 = database.insertLifeInsurance(policy_id, plan_category, age, gender, tobacco_use, occupation, medical_status, family_medical_history, beneficiary_name, beneficiary_social_security);
                                    System.out.println("home_insurance success value is: " + success8);
                                    System.out.println("customer_id is: " + customer_id);
                                    policy_id = user_integer("policy_id");
                                    String type = user_string("type");
                                    int cost = user_integer("cost");
                                    int coverage = user_integer("coverage");
                                    int deductible = user_integer("deductible");
                                    int coinsurance = user_integer("coinsurance");
                                    String effective_date = user_string("effective_date");
                                    String expire_date = user_string("expire_date");
                                    String plan = user_string("plan");
                                    int success7 = database.insertGenericPolicy(customer_id, policy_id, type, cost, coverage, deductible, coinsurance, effective_date, expire_date, plan);
                                    System.out.println("policy success value is: " + success7);
                                    try{
                                        if (success7 + success8 == 2) {
                                            database.connect.commit();
                                            System.out.println("transaction SUCCEEDED!\n");
                                        }
                                    }
                                    catch (SQLException exception) {
                                        System.out.println("transaction FAILED! ROLLED BACK!\n");
                                    }
                                    break;
                                }
                                else {
                                    System.out.println("invalid input! Try again!");
                                    System.exit(0);
                                    // input.next();
                                }   
                            }
                        }      
                    }
                }
                else if (menue_selection == 3) {
                    String policy_id = user_string("policy_id");
                    success = database.deletePolicy(policy_id);
                    System.out.print("success value is: " + success);
                    break;
                }
                else if (menue_selection == 4) {
                    int claim_id = user_integer("claim_id");
                    int customer_id = user_integer("customer_id");
                    String claim_type = user_string("claim_type");
                    String accident = user_string("accident");
                    String items_damaged = user_string("items_damaged");
                    String description = user_string("description");
                    String outcome = user_string("claim_ioutcomed");
                    String adjuster_notes = user_string("adjuster_notes");
                    int amount = user_integer("amount");
                    success = database.insertClaim(claim_id, customer_id, claim_type, accident, items_damaged, description, outcome, adjuster_notes, amount);
                    System.out.print("success value is: " + success);
                    break;
                }
                else if (menue_selection == 5) {
                    String claim_id = user_string("claim_id");
                    success = database.deleteClaim(claim_id);
                    System.out.print("success value is: " + success);
                    break;
                }
                else if (menue_selection == 6) {
                    System.out.println("Enter policy_id to make a payment: \n");
                    int policy_id = user_integer("policy_id");
                    success = database.getPolicyCost(policy_id);
                    System.out.println("success value is: " + success);
                    if (success != 0) {
                        System.out.println("How would you like to pay? \n");
                        System.out.println("[1] Debit Card\n");
                        System.out.println("[2] Credit Card\n");
                        while (true) {
                            conditional = input.hasNextInt();
                            if (conditional) {
                                menue_selection = input.nextInt();
                                if (menue_selection == 1) {
                                    int payment_id = user_integer("payment_id");
                                    String type = user_string("type");
                                    int card_number = user_integer("card_number");
                                    int expiary_date = user_integer("expiary_date");
                                    int cvv = user_integer("cvv");
                                    int success1 = database.payDebitCard(payment_id, type, card_number, cvv, policy_id, expiary_date);
                                    System.out.print("success value is: " + success);
                                    payment_id = user_integer("payment_id");
                                    String recipient_name = user_string("recipient_name");
                                    String recipient_address = user_string("recipient_address");
                                    String bank = user_string("bank");
                                    int payment_amount = user_integer("payment_amount");
                                    int success2 = database.makePolicyPayment(payment_id, policy_id, recipient_name, recipient_address, bank, payment_amount);
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
                                else if (menue_selection == 2) {
                                    int payment_id = user_integer("payment_id");
                                    String type = user_string("type");
                                    int card_number = user_integer("card_number");
                                    int expiary_date = user_integer("expiary_date");
                                    int cvv = user_integer("cvv");
                                    int success1 = database.payCreditCard(payment_id, type, card_number, cvv, policy_id, expiary_date);
                                    System.out.print("success value is: " + success);
                                    payment_id = user_integer("payment_id");
                                    String recipient_name = user_string("recipient_name");
                                    String recipient_address = user_string("recipient_address");
                                    String bank = user_string("bank");
                                    int payment_amount = user_integer("payment_amount");
                                    int success2 = database.makePolicyPayment(payment_id, policy_id, recipient_name, recipient_address, bank, payment_amount);
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
                                else {
                                    System.out.println("invalid input! Try again!");
                                    System.exit(0);
                                    // input.next();
                                }
                            }
                        }   
                    }
                    else {
                        System.out.println("invalid input! Policy_id doesn't seem to exist!");
                        System.exit(0);
                        // input.next();
                    }
                }
            }
            else {
                System.out.println("invalid input! Try again!");
                System.exit(0);
                // input.next();
            }
        }
    }

    /**
     * Command-line interface for agents
     */
    public void agent_interface(Insurance database) {
        System.out.println("[1] Get all customers associated with a particular agent\n");
        System.out.println("[2] Identify customers with overdue bills\n");
        System.out.println("[3] Customers with pending claims that have not been serviced recently\n");
        System.out.println("[4] Compute revenue generated by the agent\n");

        // try {
        //     PreparedStatement ps = database.connecting.prepareStatement("SELECT * from employee");
        //     ResultSet result = ps.executeQuery();
        //     if (!result.next()) {
        //         System.out.print("Empty result!");
        //     }
        //     else {
        //         String employee_id = result.getString("employee_id");
        //         String name = result.getString("name");

        //         System.out.println(employee_id + " employee_id");
        //         System.out.println(name + " name");
        //     }
        // }
        // catch (SQLException exception) {
        //     System.out.println("Error!");
        // }
    }

    /**
     * Command-line interface for adjusters
     */
    public void adjuster_interface(Insurance database) {
        System.out.println("[1] Identify claims that have not been serviced recently\n");
        System.out.println("[2] Remediation firms or body shops to claims\n");
    }

    /**
     * Command-line interfaces for corperate management
     */
    public void corperate_interface(Insurance database) {
        System.out.println("[1] Generate report on revenue\n");
        System.out.println("[2] Generate report on claims paid\n");
        System.out.println("[3] Generate report on profits based on policy type\n");
        System.out.println("[4] Generate report on profitability of a customer\n");
    }

    /**
     * Function for inserting new customers into the database
     */
    public int insertCustomer(int customer_id, String name, int social_security, String email, int zip_code, String city, String state, String address, long phone_number, String date_of_birth) {
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
            createCustomer.setLong(9, phone_number);
            createCustomer.setDate(10, Date.valueOf(date_of_birth));
            success = createCustomer.executeUpdate();
        }
        catch (SQLException exception) {
            System.out.println("invalid input!");
        }
        return success;
    }

    /**
     * Function for deleting existing customer policy into the database
     */
    public int deletePolicy(String policy_id) {
        int success = 0;
        try {
            dropPolicy.setString(1, policy_id);
            success = dropPolicy.executeUpdate();
        }
        catch (SQLException exception) {
            System.out.println("invalid input!");
        }
        return success;
    }

    /**
     * Function for inserting new customer claim into the database
     */
    public int insertClaim(int claim_id, int customer_id, String claim_type, String accident, String items_damaged, String description, String outcome, String adjuster_notes, int amount) {
        int success = 0;
        try {
            addClaim.setInt(1, claim_id);
            addClaim.setInt(2, customer_id);
            addClaim.setString(3, claim_type);
            addClaim.setString(4, accident);
            addClaim.setString(5, items_damaged);
            addClaim.setString(6, description);
            addClaim.setString(7, outcome);
            addClaim.setString(8, adjuster_notes);
            addClaim.setInt(9, amount);
            success = addClaim.executeUpdate();
        }
        catch (SQLException exception) {
            System.out.println("invalid input!");
        }
        return success;
    }

    /**
     * Function for deleting existing customer claim from the database
     */
    public int deleteClaim(String claim_id) {
        int success = 0;
        try {
            dropClaim.setString(1, claim_id);
            success = dropClaim.executeUpdate();
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
     * validateDate check's the validity of the user's start and finish dates
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
     * Insert generic policy under policy table associated with home/auto/health/life insurance in another table
     * Function for inserting new customer policy into the database
     */
    public int insertGenericPolicy(int customer_id, int policy_id, String type, int cost, int coverage, int deductible, int coinsurance, String effective_date, String expire_date, String plan) {
        int success = 0;
        try {
            addPolicy.setInt(1, customer_id);
            addPolicy.setInt(2, policy_id);
            addPolicy.setString(3, type);
            addPolicy.setInt(4, cost);
            addPolicy.setInt(5, coverage);
            addPolicy.setInt(6, deductible);
            addPolicy.setInt(7, coinsurance);
            addPolicy.setDate(8, Date.valueOf(effective_date));
            addPolicy.setDate(9, Date.valueOf(expire_date));
            addPolicy.setString(10, plan);
            success = addPolicy.executeUpdate();
        }
        catch (SQLException exception) {
            System.out.println("invalid input!");
        }
        return success;
    }
    
    /**
     * Insert home insurance associated with a policy_id
     */
    public int insertHomeInsurance(int policy_id, String city, String state, int zip_code, String address, int year_built, String condition, int square_foot, int lot_size, int credit_score, int mortgage_payment, int market_value, String personal_property_replacement) {
        int success = 0;
        try {
            addHomeInsurance.setInt(1, policy_id);
            addHomeInsurance.setString(2, city);
            addHomeInsurance.setString(3, state);
            addHomeInsurance.setInt(4, zip_code);
            addHomeInsurance.setString(5, address);
            addHomeInsurance.setInt(6, year_built);
            addHomeInsurance.setString(7, condition);
            addHomeInsurance.setInt(8, square_foot);
            addHomeInsurance.setInt(9, lot_size);
            addHomeInsurance.setInt(10, credit_score);
            addHomeInsurance.setInt(11, mortgage_payment);
            addHomeInsurance.setInt(12, market_value);
            addHomeInsurance.setString(13, personal_property_replacement);
            success = addHomeInsurance.executeUpdate();
        }
        catch (SQLException exception) {
            System.out.println("invalid input!");
        }
        return success;
    }

     /**
     * Insert auto insurance associated with a policy_id
     */
    public int insertAutoInsurance(int policy_id, int year, String make, String model, String vin, String license_plate, String driver_license, int total_mileage, int annual_miles, int market_value, String date_of_birth, String gender, int credit_score, String traffic_violations, int number_of_dependants, String state) {
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
            addAutoInsurance.setDate(11, Date.valueOf(date_of_birth));
            addAutoInsurance.setString(12, gender);
            addAutoInsurance.setInt(13, credit_score);
            addAutoInsurance.setString(14, traffic_violations);
            addAutoInsurance.setInt(15, number_of_dependants);
            addAutoInsurance.setString(16, state);
            success = addAutoInsurance.executeUpdate();
        }
        catch (SQLException exception) {
            System.out.println("invalid input!");
        }
        return success;
    }

     /**
     * Insert health insurance associated with a policy_id
     */
    public int insertHealthInsurance(int policy_id, String plan_category, int out_of_pocket_maximum, String tobacco_use, String age, String pre_existing_conditions, int number_of_dependants, int estimated_copay) {
        int success = 0;
        try {
            addHealthInsurance.setInt(1, policy_id);
            addHealthInsurance.setString(2, plan_category);
            addHealthInsurance.setInt(3, out_of_pocket_maximum);
            addHealthInsurance.setString(4, tobacco_use);
            addHealthInsurance.setDate(5, Date.valueOf(age));
            addHealthInsurance.setString(6, pre_existing_conditions);
            addHealthInsurance.setInt(7, number_of_dependants);
            addHealthInsurance.setInt(8, estimated_copay);
            success = addHealthInsurance.executeUpdate();
        }
        catch (SQLException exception) {
            System.out.println("invalid input!");
        }
        return success;
    }

     /**
     * Insert auto insurance associated with a policy_id
     */
    public int insertLifeInsurance(int policy_id, String plan_category, String age, String gender, String tobacco_use, String occupation, String medical_status, String family_medical_history, String beneficiary_name, int beneficiary_social_security) {
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
            System.out.println("invalid input!");
        }
        return success;
    }

    /**
     * getCustomerId checks if customer id is in the database when user attempts to add a policy
     */
    public int getCustomerId(int customer_id) {
        int success = 0;
        try {
            checkCustomerID.setInt(1, customer_id);
            ResultSet resultset = checkCustomerID.executeQuery();
            while (resultset.next()) {
                success = resultset.getInt("customer_id");
                System.out.println("customer_id is: " + success);
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
        return success;
    }

    /**
     * getPolicyCost checks if customer id is in the database when user attempts to add a policy
     */
    public int getPolicyCost(int policy_id) {
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
            System.out.println("Customer_id invalid! No customer exists!!");
        }
        return cost;
    }

    /**
     * Customer makes policy payment
     */
    public int makePolicyPayment(int payment_id, int policy_id, String recipient_name, String recipient_address, String bank, int payment_amount) {
        int success = 0;
        try {
            policyPayment.setInt(1, payment_id);
            policyPayment.setInt(2, policy_id);
            policyPayment.setString(3, recipient_name);
            policyPayment.setString(4, recipient_address);
            policyPayment.setString(5, bank);
            policyPayment.setInt(6, payment_amount);
            policyPayment.setDate(7, currentDate());
            success = policyPayment.executeUpdate();
        }
        catch (SQLException exception) {
            System.out.println("invalid input!");
        }
        return success;
    }

    /**
     * Policy payment with debit card
     */
    public int payDebitCard(int payment_id, String type, int card_number, int cvv, int policy_id, int expiary_date) {
        int success = 0;
        try {
            addDebitCard.setInt(1, payment_id);
            addDebitCard.setString(2, type);
            addDebitCard.setInt(3, card_number);
            addDebitCard.setInt(4, cvv);
            addDebitCard.setInt(5, policy_id);
            addDebitCard.setInt(6, expiary_date);
            success = addDebitCard.executeUpdate();
        }
        catch (SQLException exception) {
            System.out.println("invalid input!");
        }
        return success;
    }

     /**
     * Policy payment with credit card
     */
    public int payCreditCard(int payment_id, String type, int card_number, int cvv, int policy_id, int expiary_date) {
        int success = 0;
        try {
            addCreditCard.setInt(1, payment_id);
            addCreditCard.setString(2, type);
            addCreditCard.setInt(3, card_number);
            addCreditCard.setInt(4, cvv);
            addCreditCard.setInt(5, policy_id);
            addCreditCard.setInt(6, expiary_date);
            success = addCreditCard.executeUpdate();
        }
        catch (SQLException exception) {
            System.out.println("invalid input!");
        }
        return success;
    }
}

// customer policy: 
    // for entering cusomer policy, can have fixed customer policy rates for simplified db
    // IDs generated server side

// better message statements for user input
// customer can change the payments to anything they want when adding a policy and payment********
// make enter statements more detailed
// catch exceptions on bad sql statements
// way to differentiate in functions whether user entered invalid input or something already exist in database
// executeUpdate() vs executeQuery() vs execute() commands
// potential UI for CLI?
// link github in the README
// https://blog.developer.atlassian.com/10-design-principles-for-delightful-clis/
// agents can alter customer's policy's
// function to create random 5 digit int
// concept of existing or new user in customer interface?
// start readme with assumptions and clarifications made
// add / remove vehicle from policy
// home insuranc etype, health insurance type, auto insurance type
// take out foreign key in er diagram
// need to change constrain on health/home/auto/life insurance policies from on delete set null to on delete cascade if a policy (associated with customer ID is deleted)
// clean up resource leaks later
// catch sql ORA exceptions in code
// catch hanging statements
// add type (i.e. dental, emergency) to the policies
// add type to vehicles
// can make multiple payments to same policy -- for now ignore
// REGENATE DATA GENERATION TO MATCH UPDATED SCHEMA AND ER DIAGRAMS!
// double check ER diagram matches sql schmea
// customer wants to check their policy information -- policy_id or customer_id to check status of all their policies
// customer wants to check the status of a claim -- policy_id or customer_id to check status of all their policies
// customer wants to add dependants to a policy -- policy_id
// customer wants to add/drop vehicle to auto_insurance

// No one can be sure what losses they may suffer – not everyone's risk will be the same. ... Because of this, insurance premiums will vary from person to person because insurers try to make sure that each policyholder pays a premium that reflects their own particular level of risk.
// print customer_id, policy_id, claim_id!
// In a standard homeowners insurance policy, you have coverage for both Personal Liability and Personal Property.
// https://www.youngalfred.com/homeowners-insurance/whats-the-difference-between-personal-liability-and-personal-property-claims
// simplification: only one beneficiary
// policy cost = 500 - 5000
// claim payouts 100 - 100,000
// https://www.iii.org/article/what-beneficiary#:~:text=A%20beneficiary%20is%20the%20person,One%20person
// https://www.nolo.com/legal-encyclopedia/types-of-traffic-violations.html
// https://www.healthcare.gov/how-plans-set-your-premiums/
// https://www.youngalfred.com/homeowners-insurance/whats-the-difference-between-personal-liability-and-personal-property-claims

// Limitations
    // can only add dependants at the beggining in policy creation
    // for simplification you can only associate one home with a home insruance polciy