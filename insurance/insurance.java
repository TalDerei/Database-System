/**
 * Course: CSE 341
 * Semester and Year: Spring 2021
 * Assignment: Database Systems
 * Author: Derei, Tal
 * User ID: tad222
 */

import java.util.Scanner;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.sql.*;
import java.util.*;
import java.text.*;

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

            /**
             * Interfaces
             */
            // customer_interface(database);   
            // agent_interface(database);        
            adjuster_interface(database);        
            // corperate_interface(database);             
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
    private PreparedStatement checkPolicyDate;
    private PreparedStatement policyPayment;
    private PreparedStatement addDebitCard;
    private PreparedStatement addCreditCard;
    private PreparedStatement getPolicyInformation;
    private PreparedStatement checkClaimID;
    private PreparedStatement checkClaimStatus;
    private PreparedStatement checkPolicyID;
    private PreparedStatement addDependant;
    private PreparedStatement addAdditionalVehicle;
    private PreparedStatement dropAdditionalVehicle;
    
    /**
     * Command-line interface for customers
     */
    public static void customer_interface(Insurance database) {
        /**
         * Prepared Statements for customer interface
         */
        try {
            database.createCustomer = database.connect.prepareStatement("INSERT INTO customer (CUSTOMER_ID, NAME, SOCIAL_SECURITY, EMAIL, ZIP_CODE, CITY, STATE, ADDRESS, PHONE_NUMBER, DATE_OF_BIRTH) VALUES (?,?,?,?,?,?,?,?,?,?)");
            database.checkCustomerID = database.connect.prepareStatement("SELECT customer_id FROM customer WHERE customer_id = ?");
            database.addPolicy = database.connect.prepareStatement("INSERT INTO policy (CUSTOMER_ID, POLICY_ID, TYPE, COST, COVERAGE, DEDUCTIBLE, COINSURANCE, EFFECTIVE_DATE, EXPIRE_DATE, PLAN) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            database.addHomeInsurance = database.connect.prepareStatement("INSERT INTO home_insurance (POLICY_ID, CITY, STATE, ZIP_CODE, ADDRESS, YEAR_BUILT, CONDITION, SQUARE_FOOT, LOT_SIZE, CREDIT_SCORE, MORTGAGE_PAYMENT, MARKET_VALUE, PERSONAL_PROPERTY_REPLACEMENT) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            database.addAutoInsurance = database.connect.prepareStatement("INSERT INTO auto_insurance (POLICY_ID, YEAR, MAKE, MODEL, VIN, LICENSE_PLATE, DRIVER_LICENSE, TOTAL_MILEAGE, ANNUAL_MILES, MARKET_VALUE, AGE, GENDER, CREDIT_SCORE, NUMBER_OF_DEPENDANTS, TRAFFIC_VIOLATIONS, STATE) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            database.addHealthInsurance = database.connect.prepareStatement("INSERT INTO health_insurance (POLICY_ID, PLAN_CATEGORY, OUT_OF_POCKET_MAXIMUM, TOBACCO_USE, AGE, PRE_EXISTING_CONDITIONS, NUMBER_OF_DEPENDANTS, ESTIMATED_COPAY) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
            database.addLifeInsurance = database.connect.prepareStatement("INSERT INTO life_insurance (POLICY_ID, PLAN_CATEGORY, AGE, GENDER, TOBACCO_USE, OCCUPATION, MEDICAL_STATUS, FAMILY_MEDICAL_HISTORY, BENEFICIARY_NAME, BENEFICIARY_SOCIAL_SECURITY) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            database.dropPolicy = database.connect.prepareStatement("DELETE FROM policy WHERE POLICY_ID = ?");
            database.addClaim = database.connect.prepareStatement("INSERT INTO claim (CLAIM_ID, CUSTOMER_ID, CLAIM_TYPE, ACCIDENT, ITEMS_DAMAGED, DESCRIPTION, OUTCOME, ADJUSTER_NOTES, AMOUNT) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
            database.dropClaim = database.connect.prepareStatement("DELETE FROM claim WHERE claim_id = ?");
            database.checkPolicyCost = database.connect.prepareStatement("SELECT cost FROM policy WHERE policy_id = ?");
            database.checkPolicyDate = database.connect.prepareStatement("SELECT effective_date, expire_date FROM policy WHERE policy_id = ?");
            database.policyPayment = database.connect.prepareStatement("INSERT INTO policy_payment (PAYMENT_ID, POLICY_ID, RECIPIENT_NAME, RECIPIENT_ADDRESS, BANK, PAYMENT_AMOUNT, PAYMENT_DATE, STATUS) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
            database.addDebitCard = database.connect.prepareStatement("INSERT INTO debit (PAYMENT_ID, TYPE, CARD_NUMBER, CVV, POLICY_ID, EXPIARY_DATE) VALUES (?, ?, ?, ?, ?, ?)");
            database.addCreditCard = database.connect.prepareStatement("INSERT INTO credit (PAYMENT_ID, TYPE, CARD_NUMBER, CVV, POLICY_ID, EXPIARY_DATE) VALUES (?, ?, ?, ?, ?, ?)");
            database.getPolicyInformation = database.connect.prepareStatement("SELECT * FROM policy WHERE policy_id = ?");
            database.checkClaimID = database.connect.prepareStatement("SELECT claim_id FROM claim WHERE claim_id = ?");
            database.checkClaimStatus = database.connect.prepareStatement("SELECT decision FROM claim WHERE claim_id = ?");
            database.checkPolicyID = database.connect.prepareStatement("SELECT policy_id FROM policy WHERE policy_id = ?");
            database.addDependant = database.connect.prepareStatement("INSERT INTO dependant (DEPENDANT_ID, POLICY_ID, NAME, SOCIAL_SECURITY, DATE_OF_BIRTH) VALUES (?, ?, ?, ?, ?)");
            database.addAdditionalVehicle = database.connect.prepareStatement("INSERT INTO vehicle (POLICY_ID, VEHICLE_ID, EXTRA_VEHICLE, VEHICLE_TYPE, YEAR, MAKE, MODEL, VIN, LICENSE_PLATE, TOTAL_MILEAGE, ANNUAL_MILES, MARKET_VALUE) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            database.dropAdditionalVehicle = database.connect.prepareStatement("DELETE FROM vehicle WHERE vehicle_id = ?");
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
                                        String policy_status = user_string("policy_status");
                                        int success1 = database.insertGenericPolicy(customer_id, policy_id, type, cost, coverage, deductible, coinsurance, effective_date, expire_date, plan, policy_status);
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
                                    String policy_status = user_string("policy_status");
                                    int success3 = database.insertGenericPolicy(customer_id, policy_id, type, cost, coverage, deductible, coinsurance, effective_date, expire_date, plan, policy_status);
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
                                    String policy_status = user_string("policy_status");
                                    int success5 = database.insertGenericPolicy(customer_id, policy_id, type, cost, coverage, deductible, coinsurance, effective_date, expire_date, plan, policy_status);
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
                                    String policy_status = user_string("policy_status");
                                    int success7 = database.insertGenericPolicy(customer_id, policy_id, type, cost, coverage, deductible, coinsurance, effective_date, expire_date, plan, policy_status);
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
                    int policy_id = user_integer("policy_id");
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
                    String decision = user_string("decision");
                    String adjuster_notes = user_string("adjuster_notes");
                    int amount = user_integer("amount");
                    String claim_status = user_string("claim_status");
                    success = database.insertClaim(claim_id, customer_id, claim_type, accident, items_damaged, description, decision, adjuster_notes, amount, claim_status);
                    System.out.print("success value is: " + success);
                    break;
                }
                else if (menue_selection == 5) {
                    int claim_id = user_integer("claim_id");
                    success = database.deleteClaim(claim_id);
                    System.out.print("success value is: " + success);
                    break;
                }
                else if (menue_selection == 6) {
                    System.out.println("Enter policy_id to make a payment: \n");
                    int policy_id = user_integer("policy_id");
                    int cost = database.getPolicyCost(policy_id);
                    System.out.println("success value is: " + cost);
                    Date[] dates = new Date[2];
                    Date payment_date_check = currentDate();
                    System.out.println(payment_date_check);
                    System.out.println(dates[0]);
                    dates = database.getPolicyDate(policy_id);
                    LocalDate localdate = LocalDate.now();
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(dates[0]);
                    String status = checkPaymentStatus(calendar, localdate);
                    System.out.println("Status is: " + status);
                    if (cost != 0) {
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
                                    System.out.print("success value is: " + success1);
                                    payment_id = user_integer("payment_id");
                                    String recipient_name = user_string("recipient_name");
                                    String recipient_address = user_string("recipient_address");
                                    String bank = user_string("bank");
                                    boolean payment_amount_status = true;
                                    int payment_amount;
                                    while (payment_amount_status) {
                                        payment_amount = user_integer("payment_amount");
                                        if (payment_amount == cost) {
                                            payment_amount_status = false;
                                            System.out.print("You entered the full amount!");
                                        }
                                        else {
                                            payment_amount_status = true;
                                            System.out.print("You entered a partial amount! Please enter the full amount!");
                                        }
                                    }
                                    if (payment_date_check.after(dates[0])) {
                                        System.out.print("payment date is after effective date!\n");
                                    }
                                    // String status = user_string("status");
                                    int success2 = database.makePolicyPayment(payment_id, policy_id, recipient_name, recipient_address, bank, cost, status);
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
                                    System.out.print("success value is: " + success1);
                                    payment_id = user_integer("payment_id");
                                    String recipient_name = user_string("recipient_name");
                                    String recipient_address = user_string("recipient_address");
                                    String bank = user_string("bank");
                                    int payment_amount = user_integer("payment_amount");
                                    // String status = user_string("status");
                                    int success2 = database.makePolicyPayment(payment_id, policy_id, recipient_name, recipient_address, bank, payment_amount, status);
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
                else if (menue_selection == 7) {
                    System.out.println("Enter existing policy ID: \n");
                    int policy_id = user_integer("policy_id");
                    success = database.getPolicyInfo(policy_id);
                    System.out.print("success value is: " + success);
                    break;
                }
                else if (menue_selection == 8) {
                    int claim_id = user_integer("claim_id");
                    success = database.getClaimID(claim_id);
                    System.out.print("success value is: " + success);
                    if (claim_id == success) {
                        database.getClaimStatus(claim_id);
                    }
                    break;
                }
                else if (menue_selection == 9) {
                    int policy_id = user_integer("policy_id");
                    success = database.getPolicyID(policy_id);
                    System.out.print("success value is: " + success);
                    if (policy_id == success) {
                        int dependant_id = user_integer("dependant_id");
                        String name = user_string("name");
                        int social_security = user_integer("social_security");
                        date_of_birth = user_string("date_of_birth");
                        database.insertDependant(dependant_id, policy_id, name, social_security, date_of_birth);
                    }
                    break;
                }
                else if (menue_selection == 10) {
                    System.out.print("[1] Add Additional Vehicle to Auto Insurance Policy");
                    System.out.print("[2] Delete Vehicle From Policy");
                    if (menue_selection == 1) {
                        int policy_id = user_integer("policy_id");
                        success = database.getPolicyID(policy_id);
                        System.out.print("success value is: " + success);
                        if (policy_id == success) {
                            int vehicle_id = user_integer("vehicle_id");
                            String name = user_string("name");
                            int social_security = user_integer("social_security");
                            date_of_birth = user_string("date_of_birth");
                            database.insertAdditionalVehicle(vehicle_id, policy_id, name, social_security, date_of_birth);
                        }
                        break;
                    }
                    if (menue_selection == 2) {
                        int vehicle_id = user_integer("vehicle_id");
                        success = database.deleteAdditionalVehicle(vehicle_id);
                        System.out.print("success value is: " + success);
                        break;
                    }
                    else {
                        System.out.print("Enter the correct value!");
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
     * Prepared Statments associated with agent interface
     */
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

    /**
     * Command-line interface for agents
     */
    public static void agent_interface(Insurance database) {
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
     * Prepared Statments associated with adjuster interface
     */
    private PreparedStatement checkadjuster;
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

    /**
     * Command-line interface for adjusters
     */
    public static void adjuster_interface(Insurance database) {
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
            database.claimOutsources = database.connect.prepareStatement("INSERT INTO outsources (CLAIM_ID, NAME) VALUES (?, ?)");
            database.claimCompany = database.connect.prepareStatement("INSERT INTO outsources (NAME, TYPE, PHONE_NUMBER) VALUES (?, ?, ?)");
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
        System.out.println("[9] Remediation firms or body shops to claims\n");
        System.out.println("[10] Outsource a claim to an external company \n");
        System.out.println("[11] Add an item to an existing claim request \n");
        System.out.println("[12] Make a claim payment to a particular customer \n");


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
                    String name = user_string("name");
                    String type = user_string("type");
                    int phone_number = user_integer("phone_number");
                    success = database.getAdjusterID(claim_id);
                    System.out.print("success value is: " + success);
                    if (claim_id == success) { 
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
     * Command-line interfaces for corperate management
     */
    public static void corperate_interface(Insurance database) {
        System.out.println("[1] Generate report on revenue\n");
        System.out.println("[2] Generate report on claims paid\n");
        System.out.println("[3] Generate report on profits based on policy type\n");
        System.out.println("[4] Generate report on profitability of a customer\n");
    }

/**
 * FUNCTIONS ASSOCIATED WITH CUSTOMER_INTERFACE
 */
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
    public int deletePolicy(int policy_id) {
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
     * Function for inserting new customer claim into the database
     */
    public int insertClaim(int claim_id, int customer_id, String claim_type, String accident, String items_damaged, String description, String outcome, String adjuster_notes, int amount, String claim_status) {
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
            addClaim.setString(10, claim_status);
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
    public int deleteClaim(int claim_id) {
        int success = 0;
        try {
            dropClaim.setInt(1, claim_id);
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
    public int insertGenericPolicy(int customer_id, int policy_id, String type, int cost, int coverage, int deductible, int coinsurance, String effective_date, String expire_date, String plan, String policy_status) {
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
            addPolicy.setString(11, policy_status);
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
     * getPolicyCost checks policy cost associated with a customer 
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
     * getPolicyDate checks date associated with customer policy to determine policy payment is overdue or on time
     */
    public Date[] getPolicyDate(int policy_id) {
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
            System.out.println("Customer_id invalid! No customer exists!!");
        }
        return dates;
    }

    /**
     * Customer makes policy payment
     */
    public int makePolicyPayment(int payment_id, int policy_id, String recipient_name, String recipient_address, String bank, int payment_amount, String status) {
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
     * getClaimStatus checks customer's claim status
     */
    public String getClaimStatus(int claim_id) {
        String decision = "";
        try {
            checkClaimStatus.setInt(1, claim_id);
            ResultSet resultset = checkClaimStatus.executeQuery();
            while (resultset.next()) {
                decision = resultset.getString("decision");
                System.out.println("claim decision is: " + decision);
            }
                resultset.close();
        }
        catch (SQLException exception) {
            System.out.println("claim invalid! No claim exists!!");
        }
        return decision;
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
     * Function for inserting new dependant associated with customer policy
     */
    public int insertDependant(int dependant_id, int policy_id, String name, int social_security, String date_of_birth) {
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
            System.out.println("invalid input!");
        }
        return success;
    }

    /**
     * Function for inserting additional vehicle associated with auto insurance policy into the database
     */
    public int insertAdditionalVehicle(int vehicle_id, int policy_id, String name, int social_security, String date_of_birth) {
        int success = 0;
        try {
            addAdditionalVehicle.setInt(1, vehicle_id);
            addAdditionalVehicle.setInt(2, policy_id);
            addAdditionalVehicle.setString(3, name);
            addAdditionalVehicle.setInt(4, social_security);
            addAdditionalVehicle.setDate(5, Date.valueOf(date_of_birth));
            success = addAdditionalVehicle.executeUpdate();
        }
        catch (SQLException exception) {
            System.out.println("invalid input!");
        }
        return success;
    }

    /**
     * Function for dropping additional vehicle associated with auto insurance policy from the database
     */
    public int deleteAdditionalVehicle(int vehicle_id) {
        int success = 0;
        try {
            dropPolicy.setInt(1, vehicle_id);
            success = dropAdditionalVehicle.executeUpdate();
        }
        catch (SQLException exception) {
            System.out.println("invalid input!");
        }
        return success;
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
            claimOutsources.setInt(1, claim_id);
            claimOutsources.setString(2, name);
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
    public int adjusterCompany(String name, String type, int phone_number) {
        int success = 0;
        try {
            claimCompany.setString(1, name);
            claimCompany.setString(2, type);
            claimCompany.setInt(3, phone_number);
            success = claimCompany.executeUpdate();
        }
        catch (SQLException exception) {
            System.out.println("invalid input!");
        }
        return success;
    }


}

    // 1. finish adjuster interface
    // 2. finish corperate interface
    // 3. Split up interfaces into seperate files and incorperate the Application file
    // Start tackling issues below
 

// customer policy: 
    // for entering cusomer policy, can have fixed customer policy rates for simplified db
    // IDs generated server side
// customer can change the payments to anything they want when adding a policy and payment********
// way to differentiate in functions whether user entered invalid input or something already exist in database
// https://blog.developer.atlassian.com/10-design-principles-for-delightful-clis/
// agents can alter customer's policy's
// function to create random 5 digit int
// concept of existing or new user in customer interface?
// home insuranc etype, health insurance type, auto insurance type
// need to change constrain on health/home/auto/life insurance policies from on delete set null to on delete cascade if a policy (associated with customer ID is deleted)
// add type (i.e. dental, emergency) to the policies
// add type to vehicles
// can make multiple payments to same policy -- for now ignore
// double check ER diagram matches sql schmea
// customer wants to check their policy information -- policy_id or customer_id to check status of all their policies
// customer wants to check the status of a claim -- policy_id or customer_id to check status of all their policies
// customer wants to add dependants to a policy -- policy_id
// customer wants to add/drop vehicle to auto_insurance
// add plan category to insurances
// Getting policy information, claim informaition using customer_id, policy_id, or claim_id instead of just one?
// customer_ID does not exist vs a transaction failed!
// check if customer already exists in database
// go back through and start deciding what values need to be generated server side (i.e. status in polcy_payment or IDs)
// add input checking wrapper to allow user to enter [1], [2], [3] where appropriate
// change policy costs to double instead of int to capture decimal points
// do policy_id, agent_id, adjuster_id, and customer_id checking on each query to make sure the IDs exist (allowing us to differentiate between id not existing in database and database query error)
// For example, if customer logs in,4 your interface should allow policy numbers to be displayed rather that expecting users to remember such information.

// Clean Up:    
    // start readme with assumptions and clarifications made
    // link github in the README
    // change description of menue items
    // make input statements more detailed (user_string and user_int taking two inputs)
    // comments with paremenets
    // fix try and catch blocks
    // clean up resource leaks later
    // catch sql ORA exceptions in code
    // catch hanging statemeinputnts
    // potential UI for CLI?
    // REGENERATE DATA GENERATION TO MATCH UPDATED SCHEMA AND ER DIAGRAMS! CHECK CONSISTENCY OF ER + SCHEMA
    // list of entity and relationship sets in README

// Assumptions:
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
    // customer has one dedicated agent, and multiple agnets can comunicate with multiple adjusters and vice versa
    // The price of a policy is quoted per year, but customers pay monthly
    // Payments: my policy has ‘expire_date’ and ‘effective_date’ fields that quotes  a policy for a year. Policy_payments has ‘date’ and ‘status’ fields. When user makes their monthly payment, the date will automatically be inserted when they made their payment. Then you treat effective_date as a starting point, and just increment that by one month every time user makes a payment. Then you can identify whether it’s on time or overdue based on whether payment_date > (expire_date - effective_date) / 12 
    // for simplification, assume that everyone's insyrance is due first of the month? Some DB strain implications, implement staggered approach down the line
    // Another assumption, I don't allow for partial payments instead a policy must be payed in full for the following billing month, otherwise it's flagged as overdue. There would be some consequences such as late fees, which I don't currently handle. 
    // Your first monthly payment is due on the same day of the month as your original policy start date. For example, if your car insurance policy started on April 12th, your monthly payments are due on the 12th of each month.
    // Don't currently support policy renewals, isntead you'll need to make a new policy
    // cascading delete
    // disconnect from database tab
    // limiting amount of inner join operations which are costly

// Limitations:
    // can only add dependants at the beggining in policy creation
    // for simplification you can only associate one home with a home insruance polciy
    // None of the major insurance companies will offer you a policy on a month-to-month basis, but you might have the option to cancel your policy before the six month term is finished and get a refund on the remainder of the term
    // // Rather than keeping a status monicer, if customer doesn't pay, we asssume policy is cancled and remove it from database if customer doesn't renue. In a future iterations, we might consider keeping the policy in the database (rather than delte on cascade) and keep status of the policy on hand