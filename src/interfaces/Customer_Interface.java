/**
 * Course: CSE 341
 * Semester and Year: Spring 2021
 * Assignment: Database Systems
 * Author: Derei, Tal
 * User ID: tad222
 */

package interfaces;

import java.sql.*;
import java.util.Scanner;
import java.time.LocalDate;
import java.util.Calendar;
import manager.IOManager;
import views.Customer_View;

/**
 * Customer Interface
 */
public class Customer_Interface {
    /**
     * Command-line interface for customers
     * @param connection Connection object
     */
    public static void Customer(Connection connection) {
        Customer_View.prepareStatements(connection);

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
            System.out.println("--------------------------------------------------------------");
            System.out.println("[1] Create a New Customer Profile\n");
            System.out.println("[2] Add a Policy\n");
            System.out.println("[3] Drop a Policy\n");
            System.out.println("[4] Add a Claim to an Existing Policy\n");
            System.out.println("[5] Make a Policy Payment\n");
            System.out.println("[6] Get All Information on an Existing Policy\n");
            System.out.println("[7] Check Claim Status Associated With a Claim\n");
            System.out.println("[8] Add Dependant to Policy\n");
            System.out.println("[9] Add/Drop a Vehicle Associated with a Auto Insurance Policy\n");
            System.out.println("[10] Disconnect!\n");
            System.out.println("--------------------------------------------------------------");

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
                        valid_start_date = Customer_View.validateDate(date_of_birth);
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
                    success_value = Customer_View.insertCustomer(customer_id, name, social_security, email, zip_code, city, state, address, phone_number, date_of_birth);
                    if (success_value == 1) {
                        try{
                            connection.commit();
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
                    int customer_id = Customer_View.user_integer();
                    success_value = Customer_View.getCustomerID(customer_id);
                    if (customer_id == success_value) {
                        while (true) {
                            System.out.println("Select an Insurance Policy From The List Below: \n");
                            System.out.println("--------------------------------------------------------------");
                            System.out.println("[1] Home Insurance\n");
                            System.out.println("[2] Auto Insurance\n");
                            System.out.println("[3] Health Insurance\n");
                            System.out.println("[4] Life Insurance\n");
                            System.out.println("--------------------------------------------------------------");
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
                                            valid_start_date = Customer_View.validateDate(effective_date);
                                        } 
                                        System.out.println("Enter Policy Expiration Date: ");
                                        while (valid_end_date) {
                                            expire_date = input.next();
                                            valid_end_date = Customer_View.validateDate(expire_date);
                                        } 
                                        String plan = IOManager.policyPlan(menue_selection);
                                        String policy_status = "Pending";
                                        int success1 = Customer_View.insertGenericPolicy(customer_id, policy_id, type, cost, coverage, deductible, coinsurance, effective_date, expire_date, plan, policy_status);
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
                                        int success2 = Customer_View.insertHomeInsurance(policy_id, city, state, zip_code, address, year_built, condition, square_foot, lot_size, credit_score, mortgage_payment, market_value, personal_property_replacement, plan_category);
                                        try{
                                            if (success1 + success2 == 2) {
                                                connection.commit();
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
                                        valid_start_date = Customer_View.validateDate(effective_date);
                                    } 
                                    System.out.println("Enter Policy Expiration Date: ");
                                    while (valid_end_date) {
                                        expire_date = input.next();
                                        valid_end_date = Customer_View.validateDate(expire_date);
                                    } 
                                    String plan = IOManager.policyPlan(menue_selection);
                                    String policy_status = "Pending";
                                    int success3 = Customer_View.insertGenericPolicy(customer_id, policy_id, type, cost, coverage, deductible, coinsurance, effective_date, expire_date, plan, policy_status);
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
                                        valid_auto_date = Customer_View.validateDate(date_of_birth);
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
                                    int success4 = Customer_View.insertAutoInsurance(policy_id, year, make, model, vin, license_plate, driver_license, total_mileage, annual_miles, market_value, date_of_birth, gender, credit_score, traffic_violations, number_of_dependants, state, plan_category);
                                    try{
                                        if (success3 + success4 == 2) {
                                            connection.commit();
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
                                        valid_start_date = Customer_View.validateDate(effective_date);
                                    } 
                                    System.out.print("Enter Policy Expiration Date: ");
                                    while (valid_end_date) {
                                        expire_date = input.next();
                                        valid_end_date = Customer_View.validateDate(expire_date);
                                    } 
                                    String plan = IOManager.policyPlan(menue_selection);
                                    String policy_status = "Pending";
                                    int success5 = Customer_View.insertGenericPolicy(customer_id, policy_id, type, cost, coverage, deductible, coinsurance, effective_date, expire_date, plan, policy_status);
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
                                        valid_health_date = Customer_View.validateDate(age);
                                    } 
                                    System.out.println("Enter Pre-Existing Conditions ([Y]es or [N]o): ");
                                    String pre_existing_conditions = IOManager.yesOrNo();
                                    System.out.println("Enter Number of Dependants: ");
                                    int number_of_dependants = IOManager.intInput(0, 9, 1);
                                    System.out.println("Enter Estimated Copay: ");
                                    double estimated_copay = IOManager.intInputDouble(0.00, 9999.99);
                                    int success6 = Customer_View.insertHealthInsurance(policy_id, plan_category, out_of_pocket_maximum, tobacco_use, age, pre_existing_conditions, number_of_dependants, estimated_copay);
                                    try{
                                        if (success5 + success6 == 2) {
                                            connection.commit();
                                            System.out.println("TRANSACTION SUCCEEDED!\n");
                                        }
                                    }
                                    catch (SQLException exception) {
                                        System.out.println("TRANSACTION FAILED! ROLLED BACK!\n");
                                    }
                                    System.out.println("DON'T FORGET TO WRITE DOWN YOUR UNIQUE POLICY ID SO YOU DON'T FORGET IT!");
                                    System.out.println("YOU'LL NEED THIS TO MAKE FUTURE INQUIRIES ASSOCIATED WITH OUTSTANDING POLICY/CLAIMS!");
                                    System.out.println("YOUR UNIQUE POLICY ID IS: " + policy_id + "\n");
                                    System.out.println("\n");
                                    System.out.println("AND DON'T FORGET TO ADD DEPENDANTS TO YOUR POLICY IF YOU HAVE DEPENDANTS!\n");
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
                                        valid_start_date = Customer_View.validateDate(effective_date);
                                    } 
                                    System.out.println("Enter Policy Expiration Date: ");
                                    while (valid_end_date) {
                                        expire_date = input.next();
                                        valid_end_date = Customer_View.validateDate(expire_date);
                                    } 
                                    String plan = IOManager.policyPlan(menue_selection);
                                    String policy_status = "Pending";
                                    int success7 = Customer_View.insertGenericPolicy(customer_id, policy_id, type, cost, coverage, deductible, coinsurance, effective_date, expire_date, plan, policy_status);
                                    System.out.print("\n");

                                    System.out.print("Enter Policy Category: [1] Whole Life, [2] Universal Life, [3] Term Life");
                                    String plan_category = IOManager.planCategoryLifeInsurance(1);
                                    System.out.print("Enter Date of Birth: ");
                                    String age = "";
                                    boolean valid_life_date = true;
                                    while (valid_life_date) {
                                        age = input.next();
                                        valid_life_date = Customer_View.validateDate(age);
                                    } 
                                    System.out.print("Enter Gender ([M]ale or [F]emale): ");
                                    String gender = IOManager.gender();
                                    System.out.print("Enter Tabacco Use ([Y]es or [N]o): ");
                                    String tobacco_use = IOManager.yesOrNo();
                                    System.out.print("Enter Occupation: ");
                                    String occupation = IOManager.stringInputWithoutNumbers(30);
                                    System.out.print("Enter Medical Status ([1] Health, [2] Not Healthy): ");
                                    String medical_status = IOManager.medicalStatus(1);
                                    System.out.print("Briefly Describe Past Medical Family History: ");
                                    String family_medical_history = IOManager.stringInputWithoutNumbers(255);
                                    System.out.print("Enter Primary Beneficiary Name: ");
                                    String beneficiary_name = IOManager.stringInputWithoutNumbers(30);
                                    System.out.print("Enter Primary Benefiary Social Security: ");
                                    int beneficiary_social_security = IOManager.intInput(100000000, 999999999, 9);
                                    int success8 = Customer_View.insertLifeInsurance(policy_id, plan_category, age, gender, tobacco_use, occupation, medical_status, family_medical_history, beneficiary_name, beneficiary_social_security);
                                    
                                    try{
                                        if (success7 + success8 == 2) {
                                            connection.commit();
                                            System.out.println("TRANSACTION SUCCEEDED!\n");
                                        }
                                    }
                                    catch (SQLException exception) {
                                        System.out.println("TRANSACTION FAILED! ROLLED BACK!\n");
                                    }
                                    System.out.println("DON'T FORGET TO WRITE DOWN YOUR UNIQUE POLICY ID SO YOU DON'T FORGET IT!");
                                    System.out.println("YOU'LL NEED THIS TO MAKE FUTURE INQUIRIES ASSOCIATED WITH OUTSTANDING POLICY/CLAIMS!");
                                    System.out.println("YOUR UNIQUE POLICY ID IS: " + policy_id + "\n");
                                    System.out.println("\n");
                                    System.out.println("AND DON'T FORGET TO ADD DEPENDANTS TO YOUR POLICY IF YOU HAVE DEPENDANTS!\n");
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
                    int policy_id = Customer_View.user_integer();
                    success_value = Customer_View.getPolicyID(policy_id);
                    if (policy_id == success_value) {
                        success_value = Customer_View.deletePolicy(policy_id);
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
                    int policy_id = Customer_View.user_integer();
                    success_value = Customer_View.getPolicyID(policy_id);
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
                        success_value = Customer_View.insertClaim(claim_id, claim_type, accident, items_damaged, description, decision, adjuster_notes, amount, claim_status, policy_id);            
                        if (success_value == 1) {
                            try{
                                connection.commit();
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
                    int policy_id = Customer_View.user_integer();
                    success_value = Customer_View.getPolicyID(policy_id);
                    if (success_value == policy_id) {
                        int cost = Customer_View.getPolicyCost(policy_id);
                        Date[] dates = new Date[2];
                        Date payment_date_check = Customer_View.currentDate();
                        System.out.println(payment_date_check);
                        System.out.println(dates[0]);
                        dates = Customer_View.getPolicyDate(policy_id);
                        LocalDate localdate = LocalDate.now();
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(dates[0]);
                        String status = Customer_View.checkPaymentStatus(calendar, localdate);
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
                                        int success1 = Customer_View.payDebitCard(payment_id, type, card_number, cvv, policy_id, expiary_date);
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
                                        int success2 = Customer_View.makePolicyPayment(payment_id, policy_id, recipient_name, recipient_address, bank, cost, status);
                                        try{
                                            if (success1 + success2 == 2) {
                                                connection.commit();
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
                                        int success1 = Customer_View.payCreditCard(payment_id, type, card_number, cvv, policy_id, expiary_date);
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
                                        int success2 = Customer_View.makePolicyPayment(payment_id, policy_id, recipient_name, recipient_address, bank, cost, status);
                                        try{
                                            if (success1 + success2 == 2) {
                                                connection.commit();
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
                    int policy_id = Customer_View.user_integer();
                    success_value = Customer_View.getPolicyID(policy_id);
                    if (policy_id == success_value) {
                        success_value = Customer_View.getPolicyInfo(policy_id);
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
                    int claim_id = Customer_View.user_integer();
                    success_value = Customer_View.getClaimID(claim_id);
                    if (claim_id == success_value) {
                        Customer_View.getClaimStatus(claim_id);
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
                    int policy_id = Customer_View.user_integer();
                    success_value = Customer_View.getPolicyID(policy_id);
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
                            dependant_valid_date = Customer_View.validateDate(date_of_birth);
                        } 
                        int dep_value = Customer_View.insertDependant(dependant_id, policy_id, name, social_security, date_of_birth);
                        System.out.print("\n");
                        if (dep_value == 1) {
                            try{
                                connection.commit();
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
                    menue_selection = Customer_View.user_integer();
                    if (menue_selection == 1) {
                        System.out.print("Enter Existing Auto Insurance Policy: ");
                        int policy_id = Customer_View.user_integer();
                        success_value = Customer_View.getPolicyID(policy_id);
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
                            int success_values = Customer_View.insertAdditionalVehicle(policy_id, vehicle_id, extra_vehicle, year, make, model, vin, license_plate, total_mileage, annual_miles, market_value);
                            if (success_values == 1) {
                                try{
                                    connection.commit();
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
                        int vehicle_id = Customer_View.user_integer();
                        success_value = Customer_View.deleteAdditionalVehicle(vehicle_id);
                        if (success_value == 1) {
                            try{
                                connection.commit();
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
}