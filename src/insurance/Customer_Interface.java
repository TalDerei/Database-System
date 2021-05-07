/**
 * Course: CSE 341
 * Semester and Year: Spring 2021
 * Assignment: Database Systems
 * Author: Derei, Tal
 * User ID: tad222
 */

package insurance;

import java.sql.*;
import java.text.*;
import java.util.Scanner;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.InputMismatchException;
import insurance.IOManager;

    /**
     * Customer Interface
     */
    public class Customer_Interface {

    /**
     * Prepared Statments associated with customer interface
     */
    public Connection connect;
    public PreparedStatement createCustomer;
    public PreparedStatement checkCustomerID;
    public PreparedStatement addPolicy;
    public PreparedStatement addHomeInsurance;
    public PreparedStatement addAutoInsurance;
    public PreparedStatement addHealthInsurance;
    public PreparedStatement addLifeInsurance;
    public PreparedStatement dropPolicy;
    public PreparedStatement addClaim;
    public PreparedStatement dropClaim;
    public PreparedStatement checkPolicyCost;
    public PreparedStatement checkPolicyDate;
    public PreparedStatement policyPayment;
    public PreparedStatement addDebitCard;
    public PreparedStatement addCreditCard;
    public PreparedStatement getPolicyInformation;
    public PreparedStatement checkClaimID;
    public PreparedStatement checkClaimStatus;
    public PreparedStatement checkPolicyID;
    public PreparedStatement addDependant;
    public PreparedStatement addAdditionalVehicle;
    public PreparedStatement dropAdditionalVehicle;

    /**
     * Establish a connection to the Oracle database
     */
    public static Customer_Interface connect_database() {
        String username = "tad222";
        String password = "sailcreator1";
        // try {
        //     Scanner input = new Scanner(System.in);
        //     System.out.print("enter Oracle user id: ");   
        //     username = input.nextLine();
        //     System.out.print("enter Oracle password for " + username + ": ");
        //     password = input.nextLine();    
        // }
        // catch (InputMismatchException inputMismatchException) {
        //     System.out.println("Wrong credentials! Try Again!");
        //     return null;
        // }
        
        Customer_Interface database = new Customer_Interface();
        try (Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@edgar1.cse.lehigh.edu:1521:cse241",username,password);
        ) {
            System.out.println("\nConnection to the oracle database succeeded!\n");
            connection.setAutoCommit(false);
            database.connect = connection;   
            Customer(database);         
        } catch (SQLException exception) {
            System.out.println("\nConnection to the oracle database failed! Try again!\n");
            return connect_database();
        }
        return database;
    }

    /**
     * CLI for Customer Interface
     */
    public static void Customer(Customer_Interface database) {
        /**
         * Prepared Statements for customer interface
         */
        try {
            database.createCustomer = database.connect.prepareStatement("INSERT INTO customer (CUSTOMER_ID, NAME, SOCIAL_SECURITY, EMAIL, ZIP_CODE, CITY, STATE, ADDRESS, DATE_OF_BIRTH, PHONE_NUMBER) VALUES (?,?,?,?,?,?,?,?,?,?)");
            database.checkCustomerID = database.connect.prepareStatement("SELECT customer_id FROM customer WHERE customer_id = ?");
            database.addPolicy = database.connect.prepareStatement("INSERT INTO policy (CUSTOMER_ID, POLICY_ID, TYPE, COST, COVERAGE, DEDUCTIBLE, COINSURANCE, EFFECTIVE_DATE, EXPIRE_DATE, PLAN, POLICY_STATUS) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            database.addHomeInsurance = database.connect.prepareStatement("INSERT INTO home_insurance (POLICY_ID, CITY, STATE, ZIP_CODE, ADDRESS, YEAR_BUILT, CONDITION, SQUARE_FOOT, LOT_SIZE, CREDIT_SCORE, MORTGAGE_PAYMENT, MARKET_VALUE, PERSONAL_PROPERTY_REPLACEMENT, PLAN_CATEGORY) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            database.addAutoInsurance = database.connect.prepareStatement("INSERT INTO auto_insurance (POLICY_ID, YEAR, MAKE, MODEL, VIN, LICENSE_PLATE, DRIVER_LICENSE, TOTAL_MILEAGE, ANNUAL_MILES, MARKET_VALUE, AGE, GENDER, CREDIT_SCORE, NUMBER_OF_DEPENDANTS, TRAFFIC_VIOLATIONS, STATE, PLAN_CATEGORY) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            database.addHealthInsurance = database.connect.prepareStatement("INSERT INTO health_insurance (POLICY_ID, PLAN_CATEGORY, OUT_OF_POCKET_MAXIMUM, TOBACCO_USE, AGE, PRE_EXISTING_CONDITIONS, NUMBER_OF_DEPENDANTS, ESTIMATED_COPAY) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
            database.addLifeInsurance = database.connect.prepareStatement("INSERT INTO life_insurance (POLICY_ID, PLAN_CATEGORY, AGE, GENDER, TOBACCO_USE, OCCUPATION, MEDICAL_STATUS, FAMILY_MEDICAL_HISTORY, BENEFICIARY_NAME, BENEFICIARY_SOCIAL_SECURITY) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            database.dropPolicy = database.connect.prepareStatement("DELETE FROM policy WHERE policy_id = ?");
            database.addClaim = database.connect.prepareStatement("INSERT INTO claim (CLAIM_ID, CLAIM_TYPE, ACCIDENT, ITEMS_DAMAGED, DESCRIPTION, DECISION, ADJUSTER_NOTES, AMOUNT, CLAIM_STATUS, POLICY_ID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
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
            database.addAdditionalVehicle = database.connect.prepareStatement("INSERT INTO vehicle (POLICY_ID, VEHICLE_ID, EXTRA_VEHICLE, YEAR, MAKE, MODEL, VIN, LICENSE_PLATE, TOTAL_MILEAGE, ANNUAL_MILES, MARKET_VALUE) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            database.dropAdditionalVehicle = database.connect.prepareStatement("DELETE FROM vehicle WHERE vehicle_id = ?");
        }
        catch (SQLException exception) {
            System.out.println("Error with Prepared Statements!");
        }

        /**
         * Command-line interface for customer interface
         */
        Scanner input = new Scanner(System.in);
        int menue_selection;
        int success_value; 
        boolean valid_start_date = true;
        boolean valid_end_date = true;
        String date_of_birth = "";
        String effective_date = "";
        String expire_date = "";
        while (true) {
            System.out.println("[1] Create a New Customer Profile\n");
            System.out.println("[2] Add a Policy\n");
            System.out.println("[3] Drop a Policy\n");
            System.out.println("[4] Add a Claim to an Existing Policy\n");
            System.out.println("[5] Make a Policy Payment\n");
            System.out.println("[6] Get All Information on an Existing Policy\n");
            System.out.println("[7] Check Claim Status Associated With a Claim\n");
            System.out.println("[8] Add Dependant to Policy\n");
            System.out.println("[9] Add/Drop a Vehicle Associated with a Auto Insurance Policy\n");
            System.out.println("[10] Exit!\n");

            System.out.print("\nSelect From the List of Options Above: ");
            boolean conditional = input.hasNextInt();
            if (conditional) {
                menue_selection = input.nextInt();
                /**
                 * [1] Create a New Customer Profile
                 */
                if (menue_selection == 1) {
                    int customer_id = IOManager.idNumber(999999);
                    System.out.print("Enter Customer Name: ");
                    String name = IOManager.stringInputWithoutNumbers(30);
                    System.out.print("Enter Social Security: ");
                    int social_security = IOManager.intInput(100000000, 999999999, 9);
                    System.out.print("Enter Birth Date ([yy]yy-mm-dd): ");
                    while (valid_start_date) {
                        date_of_birth = input.next();
                        valid_start_date = validateDate(date_of_birth);
                    } 
                    System.out.print("Enter Email Address: ");
                    String email = IOManager.validEmail();
                    System.out.print("Enter Zip Code: ");
                    int zip_code = IOManager.intInput(00000, 99999, 5);
                    System.out.print("Enter City: ");
                    String city = IOManager.stringInputWithoutNumbers(20);
                    System.out.print("Enter State: ");
                    String state = IOManager.stringInputWithoutNumbers(20);
                    System.out.print("Enter Address: ");
                    String address = IOManager.stringInput(50);
                    System.out.print("Enter Phone Number: ");
                    String phone_number = IOManager.validPhoneNumber(10);
                    success_value = database.insertCustomer(customer_id, name, social_security, email, zip_code, city, state, address, phone_number, date_of_birth);
                    if (success_value == 1) {
                        try{
                            database.connect.commit();
                            System.out.println("TRANSACTION SUCCEEDED!\n");
                        }
                        catch (SQLException exception) {
                            System.out.println("TRANSACTION FAILED! ROLLED BACK!\n");
                        }
                    }
                    System.out.println("DON'T FORGET TO WRITE DOWN YOUR UNIQUE CUSTOMER ID SO YOU DON'T FORGET IT!");
                    System.out.println("YOU'LL NEED THIS TO MAKE FUTURE INQUIRIES ASSOCIATED WITH OUTSTANDING POLICY/CLAIMS!");
                    System.out.println("YOUR UNIQUE CUSTOMER ID IS: " + customer_id + "\n");
                }
                /**
                 * [2] Add a Policy
                 */
                else if (menue_selection == 2) {
                    System.out.print("Please Enter an Existing 6-digit Customer ID to Create a New Policy: ");
                    int customer_id = user_integer();
                    success_value = database.getCustomerID(customer_id);
                    if (customer_id == success_value) {
                        while (true) {
                            System.out.println("Select an Insurance Policy From The List Below: \n");
                            System.out.println("[1] Home Insurance\n");
                            System.out.println("[2] Auto Insurance\n");
                            System.out.println("[3] Health Insurance\n");
                            System.out.println("[4] Life Insurance\n");
                            conditional = input.hasNextInt();
                            if (conditional) {
                                menue_selection = input.nextInt();
                                if (menue_selection == 1) {
                                    System.out.print("Home Insurance\n");
                                        int policy_id = IOManager.idNumber(999999);
                                        System.out.println("Enter Policy Type: [1] Single, [2] Family");
                                        String type = IOManager.policyType(1);
                                        System.out.println("Enter Annual Policy Cost (As Quoted by the Insurance Agent). This Amount Will Be Reviewed and Verified Upon Final Policy Approval.");
                                        double cost = IOManager.intInputDouble(1000, 5000);
                                        System.out.println("Enter Coverage (Amount Covered by the Policy): ");
                                        double coverage = IOManager.intInputDouble(0, 999999.99);
                                        System.out.println("Enter Deductible (Amount You Pay Before Your Insurance Plan Starts to Cover Costs): ");
                                        double deductible = IOManager.intInputDouble(0, 9999.99);
                                        System.out.println("Enter Coinsurance (Percentage of Costs of a Covered Health Care Service You Pay): ");
                                        int coinsurance = IOManager.intInputRange(0, 99);
                                        System.out.println("Enter Policy Effective Date: ");
                                        while (valid_start_date) {
                                            effective_date = input.next();
                                            valid_start_date = validateDate(effective_date);
                                        } 
                                        System.out.println("Enter Policy Expiration Date: ");
                                        while (valid_end_date) {
                                            expire_date = input.next();
                                            valid_end_date = validateDate(expire_date);
                                        } 
                                        String plan = IOManager.policyPlan(menue_selection);
                                        String policy_status = "Pending";
                                        int success1 = database.insertGenericPolicy(customer_id, policy_id, type, cost, coverage, deductible, coinsurance, effective_date, expire_date, plan, policy_status);
                                        System.out.print("\n");

                                        System.out.println("Home Insurance");
                                        System.out.print("Enter City: ");
                                        String city = IOManager.stringInputWithoutNumbers(20);
                                        System.out.print("Enter State: ");
                                        String state = IOManager.stringInputWithoutNumbers(20);
                                        System.out.print("Enter Zip Code: ");
                                        int zip_code = IOManager.intInput(00000, 99999, 5);
                                        System.out.print("Enter Address: ");
                                        String address = IOManager.stringInput(50);
                                        System.out.print("Enter Year Built: ");
                                        int year_built = IOManager.intInput(1800, 2021, 4);
                                        System.out.print("Enter Condition: ");
                                        System.out.print("[1] Bad, [2] Poor, [3] Fair, [4] Good, [5] Excellent : ");
                                        String condition = IOManager.homeCondition(1);
                                        System.out.print("Enter Square Footage: ");
                                        double square_foot = IOManager.intInputDouble(0.00, 9999.99);
                                        System.out.print("Enter Lot Size (In Acres): ");
                                        double lot_size = IOManager.intInputDouble(0.00, 1000.00);
                                        System.out.print("Enter Credit Score: ");
                                        int credit_score = IOManager.intInput(100, 850, 3);
                                        System.out.print("Enter Mortgage Payment: ");
                                        double mortgage_payment = IOManager.intInputDouble(0.00, 9999.99);
                                        System.out.print("Enter Market Value: ");
                                        double market_value = IOManager.intInputDouble(10000.00, 1000000.00);
                                        System.out.println("Would you like to include additional personal property replacement protections. Enter [Y]es or [N]o: ");
                                        String personal_property_replacement = IOManager.yesOrNo();
                                        System.out.println("Enter plan category: [1] Home, [2] Apartment, [3] Condo, [4] Townhouse, [5] Flat, [6] Mobile Home");
                                        String plan_category = IOManager.planCategoryHomeInsurance(1);
                                        int success2 = database.insertHomeInsurance(policy_id, city, state, zip_code, address, year_built, condition, square_foot, lot_size, credit_score, mortgage_payment, market_value, personal_property_replacement, plan_category);
                                        try{
                                            if (success1 + success2 == 2) {
                                                database.connect.commit();
                                                System.out.println("TRANSACTION SUCCEEDED!\n");
                                            }
                                        }
                                        catch (SQLException exception) {
                                            System.out.println("TRANSACTION FAILED! ROLLED BACK!\n");
                                        }
                                        System.out.println("DON'T FORGET TO WRITE DOWN YOUR UNIQUE POLICY ID SO YOU DON'T FORGET IT!");
                                        System.out.println("YOU'LL NEED THIS TO MAKE FUTURE INQUIRIES ASSOCIATED WITH OUTSTANDING POLICY/CLAIMS!");
                                        System.out.println("YOUR UNIQUE POLICY ID IS: " + policy_id + "\n");
                                }
                                else if (menue_selection == 2) {
                                    int policy_id = IOManager.idNumber(999999);
                                    System.out.println("Enter Policy Type: [1] Single, [2] Family");
                                    String type = IOManager.policyType(1);
                                    System.out.println("Enter Annual Policy Cost (As Quoted by the Insurance Agent). This Amount Will Be Reviewed and Verified Upon Final Policy Approval.");
                                    double cost = IOManager.intInputDouble(1000, 5000);
                                    System.out.println("Enter Coverage (Amount Covered by the Policy): ");
                                    double coverage = IOManager.intInputDouble(0, 999999.99);
                                    System.out.println("Enter Deductible (Amount You Pay Before Your Insurance Plan Starts to Cover Costs): ");
                                    double deductible = IOManager.intInputDouble(0, 9999.99);
                                    System.out.println("Enter Coinsurance (Percentage of Costs of a Covered Health Care Service You Pay): ");
                                    int coinsurance = IOManager.intInputRange(0, 99);
                                    System.out.println("Enter Policy Effective Date: ");
                                    while (valid_start_date) {
                                        effective_date = input.next();
                                        valid_start_date = validateDate(effective_date);
                                    } 
                                    System.out.println("Enter Policy Expiration Date: ");
                                    while (valid_end_date) {
                                        expire_date = input.next();
                                        valid_end_date = validateDate(expire_date);
                                    } 
                                    String plan = IOManager.policyPlan(menue_selection);
                                    String policy_status = "Pending";
                                    int success3 = database.insertGenericPolicy(customer_id, policy_id, type, cost, coverage, deductible, coinsurance, effective_date, expire_date, plan, policy_status);
                                    System.out.print("\n");

                                    System.out.print("Enter Year Built: ");
                                    int year = IOManager.intInput(1800, 2021, 4);
                                    System.out.print("Enter Make: ");
                                    String make = IOManager.stringInput(10);
                                    System.out.print("Enter Model: ");
                                    String model = IOManager.stringInput(10);
                                    System.out.print("Enter Vin Number: ");
                                    String vin = IOManager.validVin(17);
                                    System.out.print("Enter License Plate: ");
                                    String license_plate = IOManager.stringInput(7);
                                    System.out.print("Enter Driver License: ");
                                    String driver_license = IOManager.stringInput(20);
                                    System.out.print("Enter Total Mileage: ");
                                    int total_mileage = IOManager.intInputRange(0, 999999);
                                    System.out.print("Enter Total Annual Mileage: ");
                                    int annual_miles = IOManager.intInputRange(0, 99999);
                                    System.out.print("Enter Market Value: ");
                                    int market_value = IOManager.intInputRange(1000, 10000000);
                                    System.out.print("Enter Date of Birth: ");
                                    boolean valid_auto_date = true;
                                    while (valid_auto_date) {
                                        date_of_birth = input.next();
                                        valid_auto_date = validateDate(date_of_birth);
                                    } 
                                    System.out.print("Enter Gender (Enter [M]ale or [F]emale): ");
                                    String gender = IOManager.gender();
                                    System.out.print("Enter Credit Score: ");
                                    int credit_score = IOManager.intInput(100, 850, 3);
                                    System.out.print("Do You Have Past Traffic Violations? Enter [Y]es or [N]o: ");
                                    String traffic_violations = IOManager.yesOrNo();
                                    System.out.print("Enter Number of Dependants: ");
                                    int number_of_dependants = IOManager.intInput(0, 9, 1);
                                    System.out.print("Enter US State: ");
                                    String state = IOManager.stringInputWithoutNumbers(20); 
                                    System.out.print("Enter Policy Category [1] Collision, [2] Motorist, [3] Liability, [4] Comprehensive, [5] Personal Injury: ");
                                    String plan_category = IOManager.planCategoryAutoInsurance(1);
                                    int success4 = database.insertAutoInsurance(policy_id, year, make, model, vin, license_plate, driver_license, total_mileage, annual_miles, market_value, date_of_birth, gender, credit_score, traffic_violations, number_of_dependants, state, plan_category);
                                    try{
                                        if (success3 + success4 == 2) {
                                            database.connect.commit();
                                            System.out.println("TRANSACTION SUCCEEDED!\n");
                                        }
                                    }
                                    catch (SQLException exception) {
                                        System.out.println("TRANSACTION FAILED! ROLLED BACK!\n");
                                    }
                                    System.out.println("DON'T FORGET TO WRITE DOWN YOUR UNIQUE POLICY ID SO YOU DON'T FORGET IT!");
                                    System.out.println("YOU'LL NEED THIS TO MAKE FUTURE INQUIRIES ASSOCIATED WITH OUTSTANDING POLICY/CLAIMS!");
                                    System.out.println("YOUR UNIQUE POLICY ID IS: " + policy_id + "\n");
                                }
                                else if (menue_selection == 3) {
                                    int policy_id = IOManager.idNumber(999999);
                                    System.out.println("Enter Policy Type: [1] Single, [2] Family");
                                    String type = IOManager.policyType(1);
                                    System.out.println("Enter Annual Policy Cost (As Quoted by the Insurance Agent). This Amount Will Be Reviewed and Verified Upon Final Policy Approval.");
                                    double cost = IOManager.intInputDouble(1000, 5000);
                                    System.out.println("Enter Coverage (Amount Covered by the Policy): ");
                                    double coverage = IOManager.intInputDouble(0, 999999.99);
                                    System.out.println("Enter Deductible (Amount You Pay Before Your Insurance Plan Starts to Cover Costs): ");
                                    double deductible = IOManager.intInputDouble(0, 9999.99);
                                    System.out.println("Enter Coinsurance (Percentage of Costs of a Covered Health Care Service You Pay): ");
                                    int coinsurance = IOManager.intInputRange(0, 99);
                                    System.out.println("Enter Policy Effective Date: ");
                                    while (valid_start_date) {
                                        effective_date = input.next();
                                        valid_start_date = validateDate(effective_date);
                                    } 
                                    System.out.print("Enter Policy Expiration Date: ");
                                    while (valid_end_date) {
                                        expire_date = input.next();
                                        valid_end_date = validateDate(expire_date);
                                    } 
                                    String plan = IOManager.policyPlan(menue_selection);
                                    String policy_status = "Pending";
                                    int success5 = database.insertGenericPolicy(customer_id, policy_id, type, cost, coverage, deductible, coinsurance, effective_date, expire_date, plan, policy_status);
                                    System.out.print("\n");
                                    
                                    System.out.print("Enter Policy Category [1] Collision, [2] Motorist, [3] Liability, [4] Comprehensive, [5] Personal Injury: ");
                                    String plan_category = IOManager.planCategoryHealthInsurance(1);
                                    System.out.print("Enter Out of Pocket Maximum: ");
                                    double out_of_pocket_maximum = IOManager.intInputDouble(0.00, 9999.99);
                                    System.out.print("Enter Tobacco Use ([Y]es or [N]o): ");
                                    String tobacco_use = IOManager.yesOrNo();
                                    System.out.print("Enter Date of Birth: ");
                                    String age = "";
                                    boolean valid_health_date = true;
                                    while (valid_health_date) {
                                        age = input.next();
                                        valid_health_date = validateDate(age);
                                    } 
                                    System.out.println("Enter Pre-Existing Conditions ([Y]es or [N]o): ");
                                    String pre_existing_conditions = IOManager.yesOrNo();
                                    System.out.println("Enter Number of Dependants: ");
                                    int number_of_dependants = IOManager.intInput(0, 9, 1);
                                    System.out.println("Enter Estimated Copay: ");
                                    double estimated_copay = IOManager.intInputDouble(0.00, 9999.99);
                                    int success6 = database.insertHealthInsurance(policy_id, plan_category, out_of_pocket_maximum, tobacco_use, age, pre_existing_conditions, number_of_dependants, estimated_copay);
                                    try{
                                        if (success5 + success6 == 2) {
                                            database.connect.commit();
                                            System.out.println("TRANSACTION SUCCEEDED!\n");
                                        }
                                    }
                                    catch (SQLException exception) {
                                        System.out.println("TRANSACTION FAILED! ROLLED BACK!\n");
                                    }
                                    System.out.println("DON'T FORGET TO WRITE DOWN YOUR UNIQUE POLICY ID SO YOU DON'T FORGET IT!");
                                    System.out.println("YOU'LL NEED THIS TO MAKE FUTURE INQUIRIES ASSOCIATED WITH OUTSTANDING POLICY/CLAIMS!");
                                    System.out.println("YOUR UNIQUE POLICY ID IS: " + policy_id + "\n");
                                }
                                else if (menue_selection == 4) {
                                    int policy_id = IOManager.idNumber(999999);
                                    System.out.println("Enter Policy Type: [1] Single, [2] Family");
                                    String type = IOManager.policyType(1);
                                    System.out.println("Enter Annual Policy Cost (As Quoted by the Insurance Agent). This Amount Will Be Reviewed and Verified Upon Final Policy Approval.");
                                    double cost = IOManager.intInputDouble(1000, 5000);
                                    System.out.println("Enter Coverage (Amount Covered by the Policy): ");
                                    double coverage = IOManager.intInputDouble(0, 999999.99);
                                    System.out.println("Enter Deductible (Amount You Pay Before Your Insurance Plan Starts to Cover Costs): ");
                                    double deductible = IOManager.intInputDouble(0, 9999.99);
                                    System.out.println("Enter Coinsurance (Percentage of Costs of a Covered Health Care Service You Pay): ");
                                    int coinsurance = IOManager.intInputRange(0, 99);
                                    System.out.println("Enter Policy Effective Date: ");
                                    while (valid_start_date) {
                                        effective_date = input.next();
                                        valid_start_date = validateDate(effective_date);
                                    } 
                                    System.out.println("Enter Policy Expiration Date: ");
                                    while (valid_end_date) {
                                        expire_date = input.next();
                                        valid_end_date = validateDate(expire_date);
                                    } 
                                    String plan = IOManager.policyPlan(menue_selection);
                                    String policy_status = "Pending";
                                    int success7 = database.insertGenericPolicy(customer_id, policy_id, type, cost, coverage, deductible, coinsurance, effective_date, expire_date, plan, policy_status);
                                    System.out.print("\n");

                                    System.out.print("Enter Policy Category: [1] Whole Life, [2] Universal Life, [3] Term Life");
                                    String plan_category = IOManager.planCategoryLifeInsurance(1);
                                    System.out.print("Enter Date of Birth: ");
                                    String age = "";
                                    boolean valid_life_date = true;
                                    while (valid_life_date) {
                                        age = input.next();
                                        valid_life_date = validateDate(age);
                                    } 
                                    System.out.print("Enter Gender ([M]ale or [F]emale): ");
                                    String gender = IOManager.gender();
                                    System.out.print("Enter Tabacco Use ([Y]es or [N]o): ");
                                    String tobacco_use = IOManager.yesOrNo();
                                    System.out.print("Enter Occupation: ");
                                    String occupation = IOManager.stringInputWithoutNumbers(30);
                                    System.out.print("Enter Medical Statu ([1] Health, [2] Not Healthy): ");
                                    String medical_status = IOManager.medicalStatus(1);
                                    System.out.print("Briefly Describe Past Medical History Family Medical History: ");
                                    String family_medical_history = IOManager.stringInputWithoutNumbers(255);
                                    System.out.print("Enter Primary Beneficiary Name: ");
                                    String beneficiary_name = IOManager.stringInputWithoutNumbers(30);
                                    System.out.print("Enter Primary Benefiary Social Security: ");
                                    int beneficiary_social_security = IOManager.intInput(100000000, 999999999, 9);
                                    int success8 = database.insertLifeInsurance(policy_id, plan_category, age, gender, tobacco_use, occupation, medical_status, family_medical_history, beneficiary_name, beneficiary_social_security);
                                    
                                    try{
                                        if (success7 + success8 == 2) {
                                            database.connect.commit();
                                            System.out.println("TRANSACTION SUCCEEDED!\n");
                                        }
                                    }
                                    catch (SQLException exception) {
                                        System.out.println("TRANSACTION FAILED! ROLLED BACK!\n");
                                    }
                                    System.out.println("DON'T FORGET TO WRITE DOWN YOUR UNIQUE POLICY ID SO YOU DON'T FORGET IT!");
                                    System.out.println("YOU'LL NEED THIS TO MAKE FUTURE INQUIRIES ASSOCIATED WITH OUTSTANDING POLICY/CLAIMS!");
                                    System.out.println("YOUR UNIQUE POLICY ID IS: " + policy_id + "\n");
                                }
                                else {
                                    System.out.println("Invalid Input! Try Again!");
                                    input.next();
                                }   
                            }
                        }      
                    }
                    else {
                        System.out.println("Customer With ID " + customer_id + " Does NOT Exist! Try Again!");
                        System.out.print("Please Enter an Existing 6-digit Customer ID To Create a New Policy: ");
                    }
                }
                /**
                 * [3] Drop a Policy
                 */
                else if (menue_selection == 3) {
                    System.out.print("Enter an existing policy ID to drop the policy: ");
                    int policy_id = user_integer();
                    success_value = database.getPolicyID(policy_id);
                    if (policy_id == success_value) {
                        success_value = database.deletePolicy(policy_id);
                        System.out.println("Successfully Deleted Policy!\n");
                    }
                    else {
                        System.out.println("Could Not Find Policy ID! Try again!\n");
                    }
                }
                /**
                 * [4] Add a Claim to an Existing Policy
                 */
                else if (menue_selection == 4) {
                    System.out.print("Enter an Existing Policy ID to Start a Claim: ");
                    int policy_id = user_integer();
                    success_value = database.getPolicyID(policy_id);
                    if (policy_id == success_value) {
                        int claim_id = IOManager.idNumber(999999);
                        String claim_type = IOManager.policyPlan(1);
                        System.out.println("Enter Claim Type: " + claim_type);
                        System.out.println("Enter Accident [1] Wind and Hail, [2] Theft, [3] Car Wreck, [4] Health-Related, [5] Personal Injury, [6] ater Damage, [7] Fire Damage: ");
                        String accident = IOManager.accident(1);
                        System.out.print("Enter Items Damaged ([Y]es or [N]o): ");
                        String items_damaged = IOManager.yesOrNo();
                        System.out.print("Enter Description of the Claim: ");
                        String description = IOManager.stringInput(255);
                        String decision = "Pending";
                        String adjuster_notes = "NULL";
                        System.out.print("Enter Claim Amount: ");
                        double amount = IOManager.intInputDouble(0.00, 999999.99);
                        String claim_status = "Pending";
                        success_value = database.insertClaim(claim_id, claim_type, accident, items_damaged, description, decision, adjuster_notes, amount, claim_status, policy_id);            
                        if (success_value == 1) {
                            try{
                                database.connect.commit();
                                System.out.println("TRANSACTION SUCCEEDED!\n");
                            }
                            catch (SQLException exception) {
                                System.out.println("TRANSACTION FAILED! ROLLED BACK!\n");
                            }
                        }
                        System.out.println("DON'T FORGET TO WRITE DOWN YOUR UNIQUE CLAIM ID SO YOU DON'T FORGET IT!");
                        System.out.println("YOU'LL NEED THIS TO MAKE FUTURE INQUIRIES ASSOCIATED WITH OUTSTANDING POLICY/CLAIMS!");
                        System.out.println("YOUR UNIQUE CLAIM ID IS: " + claim_id + "\n");            
                    }
                    else {
                        System.out.println("Could Not Find Policy ID! Try again!\n");
                    }
                }
                /**
                 * [5] Make a Policy Payment
                 */
                else if (menue_selection == 5) {
                    System.out.print("Enter Existing Policy ID to Make a Payment: ");
                    int policy_id = user_integer();
                    success_value = database.getPolicyID(policy_id);
                    if (success_value == policy_id) {
                        int cost = database.getPolicyCost(policy_id);
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
                            System.out.println("How Would You Like To Pay? \n");
                            System.out.println("[1] Debit\n");
                            System.out.println("[2] Credit\n");
                            while (true) {
                                conditional = input.hasNextInt();
                                if (conditional) {
                                    menue_selection = input.nextInt();
                                    if (menue_selection == 1) {
                                        int payment_id = IOManager.idNumber(999999);
                                        System.out.print("Enter Card Type: [1] Visa, [2] Mastercard, [3] Amex, [4] Discover, [5] American Express ");
                                        String type = IOManager.cardType(1);
                                        System.out.print("Enter Card Number: ");
                                        long card_number = IOManager.intInputLong(16);
                                        System.out.print("Enter Expiration Year (YYYY): ");
                                        int expiary_date = IOManager.intInput(2000, 2030, 4);
                                        System.out.print("Enter CVV Number: ");
                                        int cvv = IOManager.intInput(000, 999, 3);
                                        int success1 = database.payDebitCard(payment_id, type, card_number, cvv, policy_id, expiary_date);
                                        System.out.print("success value is: " + success1);
                                        System.out.print("Enter Policy Holder's Name: ");
                                        String recipient_name = IOManager.stringInputWithoutNumbers(30);
                                        System.out.print("Enter Policy Holders Address: ");
                                        String recipient_address = IOManager.stringInput(50);
                                        System.out.println("Enter Bank: [1] BofA, [2] Citigroup, [3] Chase, [4] Wells Fargo");
                                        String bank = IOManager.bank(1);
                                        System.out.print("Enter Amount You'd Like To Pay: ");
                                        boolean payment_amount_status = true;
                                        double payment_amount;
                                        while (payment_amount_status) {
                                            payment_amount = IOManager.intInputDouble(0.00, 9999.99);
                                            if (payment_amount == cost) {
                                                payment_amount_status = false;
                                                System.out.print("You Entered the Full Amount! Policy Payed!\n");
                                            }
                                            else {
                                                payment_amount_status = true;
                                                System.out.print("You Entered a Partial Amount! Please Enter the Full Payment Amount!\n");
                                                System.out.println("Please Enter the Full Amount: " + "$" + cost);
                                            }
                                        }
                                        if (payment_date_check.after(dates[0])) {
                                            System.out.print("Payment Date is After Effective Date! Payment is Marked as Late!\n");
                                        }
                                        int success2 = database.makePolicyPayment(payment_id, policy_id, recipient_name, recipient_address, bank, cost, status);
                                        try{
                                            if (success1 + success2 == 2) {
                                                database.connect.commit();
                                                System.out.print("TRANSACTION SUCCEEDED!\n");
                                                System.out.println("Have A Nice Day!\n");
                                                System.exit(0);
                                            }
                                        }
                                        catch (SQLException exception) {
                                            System.out.println("TRANSACTION FAILED! ROLLED BACK!\n");
                                            System.out.println("Have A Nice Day!\n");
                                            System.exit(0);
                                        }
                                    }
                                    else if (menue_selection == 2) {
                                        int payment_id = IOManager.idNumber(999999);
                                        System.out.print("Enter Card Type: [1] Visa, [2] Mastercard, [3] Amex, [4] Discover, [5] American Express ");
                                        String type = IOManager.cardType(1);
                                        System.out.print("Enter Card Number: ");
                                        long card_number = IOManager.intInputLong(16);
                                        System.out.print("Enter Expiration Year (YYYY): ");
                                        int expiary_date = IOManager.intInput(2000, 2030, 4);
                                        System.out.print("Enter CVV Number: ");
                                        int cvv = IOManager.intInput(000, 999, 3);
                                        int success1 = database.payCreditCard(payment_id, type, card_number, cvv, policy_id, expiary_date);
                                        System.out.print("Enter Policy Holder's Name: ");
                                        String recipient_name = IOManager.stringInputWithoutNumbers(30);
                                        System.out.print("Enter Policy Holders Address: ");
                                        String recipient_address = IOManager.stringInput(50);
                                        System.out.println("Enter Bank: [1] BofA, [2] Citigroup, [3] Chase, [4] Wells Fargo");
                                        String bank = IOManager.bank(1);
                                        System.out.print("Enter Amount You'd Like To Pay: ");
                                        boolean payment_amount_status = true;
                                        double payment_amount;
                                        while (payment_amount_status) {
                                            payment_amount = IOManager.intInputDouble(0.00, 9999.99);
                                            if (payment_amount == cost) {
                                                payment_amount_status = false;
                                                System.out.print("You Entered the Full Amount! Policy Payed!\n");
                                            }
                                            else {
                                                payment_amount_status = true;
                                                System.out.print("You Entered a Partial Amount! Please Enter the Full Payment Amount!\n");
                                                System.out.println("Please Enter the Full Amount: " + "$" + cost);
                                            }
                                        }
                                        if (payment_date_check.after(dates[0])) {
                                            System.out.print("Payment Date is After Effective Date! Payment is Marked as Late!\n");
                                        }
                                        // String status = user_string("status");
                                        int success2 = database.makePolicyPayment(payment_id, policy_id, recipient_name, recipient_address, bank, cost, status);
                                        try{
                                            if (success1 + success2 == 2) {
                                                database.connect.commit();
                                                System.out.println("TRANSACTION SUCCEEDED!\n");
                                                System.exit(0);
                                            }
                                        }
                                        catch (SQLException exception) {
                                            System.out.println("TRANSACTION FAILED! ROLLED BACK!\n");
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
                            System.out.println("Invalid Input! Policy ID Doesn't Seem to Exist!");
                        }
                    }
                    else {
                        System.out.println("Invalid Input! Policy ID Doesn't Seem to Exist!");
                    }
                }
                /**
                 * [6] Get All Information on an Existing Policy
                 */
                else if (menue_selection == 6) {
                    System.out.print("Enter Existing Policy ID to Retrieve Information About the Policy: \n");
                    int policy_id = user_integer();
                    success_value = database.getPolicyID(policy_id);
                    if (policy_id == success_value) {
                        success_value = database.getPolicyInfo(policy_id);
                        System.out.print("\n");
                    }
                    else {
                        System.out.println("Could Not Find Policy ID! Try again!\n");
                    }
                }
                /**
                 * [7] Check Claim Status Associated With a Claim
                 */
                else if (menue_selection == 7) {
                    System.out.print("Enter Existing Claim ID to Check the Status of the Claim: ");
                    int claim_id = user_integer();
                    success_value = database.getClaimID(claim_id);
                    if (claim_id == success_value) {
                        database.getClaimStatus(claim_id);
                        System.out.print("\n");
                        
                    }
                    else {
                        System.out.println("Could Not Find Claim ID! Try again!\n");
                    }
                }
                /**
                 * [8] Add Dependant to Policy
                 */
                else if (menue_selection == 8) {
                    System.out.println("Enter Existing Policy ID to Add a Dependant to the Policy: ");
                    int policy_id = user_integer();
                    success_value = database.getPolicyID(policy_id);
                    if (policy_id == success_value) {
                        int dependant_id = IOManager.idNumber(999999);
                        System.out.print("Enter Dependant Name: ");
                        String name = IOManager.stringInput(30);
                        System.out.print("Enter Dependant Social Security: ");
                        int social_security = IOManager.intInput(100000000, 999999999, 9);
                        System.out.print("Enter Dependant Date of Birth: ");
                        boolean dependant_valid_date = true;
                        while (dependant_valid_date) {
                            date_of_birth = input.next();
                            dependant_valid_date = validateDate(date_of_birth);
                        } 
                        int dep_value = database.insertDependant(dependant_id, policy_id, name, social_security, date_of_birth);
                        System.out.print("\n");
                        if (dep_value == 1) {
                            try{
                                database.connect.commit();
                                System.out.println("TRANSACTION SUCCEEDED!\n");
                            }
                            catch (SQLException exception) {
                                System.out.println("TRANSACTION FAILED! ROLLED BACK!\n");
                            }
                        }
                    }
                    else {
                        System.out.println("Could Not Find Policy ID! Try again!\n");
                    }
                }
                /**
                 * [9] Add/Drop a Vehicle Associated with a Auto Insurance Policy
                 */
                else if (menue_selection == 9) {
                    System.out.println("[1] Add Additional Vehicle to Auto Insurance Policy");
                    System.out.println("[2] Delete Vehicle From Policy");
                    menue_selection = user_integer();
                    if (menue_selection == 1) {
                        System.out.print("Enter Existing Auto Insurance Policy: ");
                        int policy_id = user_integer();
                        success_value = database.getPolicyID(policy_id);
                        if (policy_id == success_value) {
                            int vehicle_id = IOManager.idNumber(999999);
                            String extra_vehicle = "Yes";
                            System.out.print("Enter Year Built: ");
                            int year = IOManager.intInput(1800, 2021, 4);
                            System.out.print("Enter Make: ");
                            String make = IOManager.stringInput(10);
                            System.out.print("Enter Model: ");
                            String model = IOManager.stringInput(10);
                            System.out.print("Enter 17-Digit Vin Number: ");
                            String vin = IOManager.validVin(17);
                            System.out.print("Enter License Plate: ");
                            String license_plate = IOManager.stringInput(7);
                            System.out.print("Enter Total Mileage: ");
                            int total_mileage = IOManager.intInputRange(0, 999999);
                            System.out.print("Enter Total Annual Mileage: ");
                            int annual_miles = IOManager.intInputRange(0, 99999);
                            System.out.print("Enter Market Value: ");           
                            int market_value = IOManager.intInputRange(1000, 10000000);
                            int success_values = database.insertAdditionalVehicle(policy_id, vehicle_id, extra_vehicle, year, make, model, vin, license_plate, total_mileage, annual_miles, market_value);
                            if (success_values == 1) {
                                try{
                                    database.connect.commit();
                                    System.out.println("TRANSACTION SUCCEEDED!\n");
                                }
                                catch (SQLException exception) {
                                    System.out.println("TRANSACTION FAILED! ROLLED BACK!\n");
                                }
                            }
                        }
                    }
                    else if (menue_selection == 2) {
                        System.out.print("[2] Enter Exising Auto Insuance Policy: ");
                        int vehicle_id = user_integer();
                        success_value = database.deleteAdditionalVehicle(vehicle_id);
                        if (success_value == 1) {
                            try{
                                database.connect.commit();
                                System.out.println("TRANSACTION SUCCEEDED!\n");
                            }
                            catch (SQLException exception) {
                                System.out.println("TRANSACTION FAILED! ROLLED BACK!\n");
                            }
                        }
                    }
                    
                    // else {
                    //     System.out.print("Enter the Correct Value From the List Above!");
                    // }
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
 * FUNCTIONS ASSOCIATED WITH CUSTOMER_INTERFACE
*/
    /**
     * insertCustomer inserts new customer
     */
    public int insertCustomer(int customer_id, String name, int social_security, String email, int zip_code, String city, String state, String address, String phone_number, String date_of_birth) {
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
     * insertClaim inserts claim associated with a policy
     */
    public int insertClaim(int claim_id, String claim_type, String accident, String items_damaged, String description, String outcome, String adjuster_notes, double amount, String claim_status, int policy_id) {
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
            System.out.println("Customer ID invalid! No customer exists!!");
        }
        return id;
    }

    /**
     * insertGenericPolicy inserts a top-level policy in the policy table
     */
    public int insertGenericPolicy(int customer_id, int policy_id, String type, double cost, double coverage, double deductible, int coinsurance, String effective_date, String expire_date, String plan, String policy_status) {
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
    public int insertHomeInsurance(int policy_id, String city, String state, int zip_code, String address, int year_built, String condition, double square_foot, double lot_size, int credit_score, double mortgage_payment, double market_value, String personal_property_replacement, String plan_category) {
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
    public int getCustomerId(int customer_id) {
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
            System.out.println("Failed to Retrieve Policy Cost! Try Again!");
        }
        return cost;
    }

    /**
     * getPolicyDate checks date associated with customer policy to determine policy payment is on time or overdue
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
            System.out.println("Failed to Retrive Policy Date! Try Again!");
        }
        return dates;
    }

    /**
     * makePolicyPayment allows user to make policy payment
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
            System.out.println("Invalid Input!");
        }
        return success;
    }

    /**
     * payDebitCard makes a debit payment to policy
     */
    public int payDebitCard(int payment_id, String type, long card_number, int cvv, int policy_id, int expiary_date) {
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
    public int payCreditCard(int payment_id, String type, long card_number, int cvv, int policy_id, int expiary_date) {
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
            System.out.println("Policy ID Invalid! No Policy Exists!");
        }
        return success;
    }

    /**
     * checkClaimID checks if claim ID exists in the database 
     */
    public int getClaimID(int claim_id) {
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
    public String getClaimStatus(int claim_id) {
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
     * getPolicyID checks if policy ID exists in the databases
     */
    public int getPolicyID(int policy_id) {
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
            System.out.println("Failed to Insert Dependant! Try Again!");
        }
        return success;
    }

    /**
     * insertAdditionalVehicle inserts additional vehicles into existing auto insurance policy 
     */
    public int insertAdditionalVehicle(int policy_id, int vehicle_id, String extra_vehicle, int year, String make, String model, String vin, String license_plate, int total_mileage, int annual_miles, int market_value) {
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
    public int deleteAdditionalVehicle(int vehicle_id) {
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