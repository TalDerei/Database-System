package views.customerViews;

import java.sql.*;
import manager.IOManager;
import objects.CustomerObjects.*;
import java.util.Scanner;

public class AddPolicy {
     /**
     * add policy
     * @param connection
     */
    public static void add_policy(Connection connection) {
        /** 
         * Policy and Claim objects 
        */
        Policy policy = new Policy();
        Customer customer = new Customer();

        Boolean valid_start_date = true;
        Boolean valid_end_date = true;
        String effective_date = "";
        String expire_date = "";
        String date_of_birth = "";
        System.out.print("Please Enter an Existing 6-digit Customer ID to Create a New Policy: ");
        Scanner input = new Scanner(System.in);
        policy.setCustomerId(Input.user_integer());
        if (customer.getCustomerID(customer.getId()) == customer.getId()) {
            while (true) {
                System.out.println("Select an Insurance Policy From The List Below: \n");
                System.out.println("--------------------------------------------------------------");
                System.out.println("[1] Home Insurance\n");
                System.out.println("[2] Auto Insurance\n");
                System.out.println("[3] Health Insurance\n");
                System.out.println("[4] Life Insurance\n");
                System.out.println("--------------------------------------------------------------");
                boolean conditional = input.hasNextInt();
                if (conditional) {
                    int menue_selection = input.nextInt();
                    if (menue_selection == 1) {
                        System.out.print("Home Insurance\n");
                            policy.setPolicyId(IOManager.idNumber(999999));
                            System.out.println("Enter Policy Type: [1] Single, [2] Family");
                            policy.setType(IOManager.policyType(1)); 
                            System.out.println("Enter Annual Policy Cost (As Quoted by the Insurance Agent). This Amount Will Be Reviewed and Verified Upon Final Policy Approval.");
                            policy.setCost(IOManager.intInputDouble(1000, 5000));
                            System.out.println("Enter Coverage (Amount Covered by the Policy): ");
                            policy.setCoverage(IOManager.intInputDouble(0, 999999.99));
                            System.out.println("Enter Deductible (Amount You Pay Before Your Insurance Plan Starts to Cover Costs): ");
                            policy.setDeductible(IOManager.intInputDouble(0, 9999.99));
                            System.out.println("Enter Coinsurance (Percentage of Costs of a Covered Health Care Service You Pay): ");
                            policy.setCoinsurance(IOManager.intInputRange(0, 99));
                            System.out.println("Enter Policy Effective Date: ");
                            while (valid_start_date) {
                                effective_date = Input.user_string();
                                valid_start_date = Input.validateDate(effective_date);
                            } 
                            System.out.println("Enter Policy Expiration Date: ");
                            while (valid_end_date) {
                                expire_date = Input.user_string();
                                valid_end_date = Input.validateDate(expire_date);
                            } 
                            policy.setPlan(IOManager.policyPlan(menue_selection));
                            policy.setPolicyStatus("Pending");
                            System.out.print("\n");

                            System.out.println("Home Insurance");
                            System.out.print("Enter City: ");
                            policy.setCity(IOManager.stringInputWithoutNumbers(20));
                            System.out.print("Enter State: ");
                            policy.setState(IOManager.stringInputWithoutNumbers(20));
                            System.out.print("Enter Zip Code: ");
                            policy.setZipCode(IOManager.intInput(00000, 99999, 5));
                            System.out.print("Enter Address: ");
                            policy.setAddress(IOManager.stringInput(50));
                            System.out.print("Enter Year Built: ");
                            policy.setYearBuilt(IOManager.intInput(1800, 2021, 4));
                            System.out.print("Enter Condition: ");
                            System.out.print("[1] Bad, [2] Poor, [3] Fair, [4] Good, [5] Excellent : ");
                            policy.setCondition(IOManager.homeCondition(1));
                            System.out.print("Enter Square Footage: ");
                            policy.setSquareFootage(IOManager.intInputDouble(0.00, 9999.99));
                            System.out.print("Enter Lot Size (In Acres): ");
                            policy.setLotSize(IOManager.intInputDouble(0.00, 1000.00));
                            System.out.print("Enter Credit Score: ");
                            policy.setCreditScore(IOManager.intInput(100, 850, 3));
                            System.out.print("Enter Mortgage Payment: ");
                            policy.setMortagePayment(IOManager.intInputDouble(0.00, 9999.99));
                            System.out.print("Enter Market Value: ");
                            policy.setMarketValue(IOManager.intInputDouble(10000.00, 1000000.00));
                            System.out.println("Would you like to include additional personal property replacement protections. Enter [Y]es or [N]o: ");
                            policy.setPersonalPropertyReplacement(IOManager.yesOrNo());
                            System.out.println("Enter plan category: [1] Home, [2] Apartment, [3] Condo, [4] Townhouse, [5] Flat, [6] Mobile Home");
                            policy.setPlanCategory(IOManager.planCategoryHomeInsurance(1));
                            try{
                                if (policy.insertGenericPolicy() && policy.insertHomeInsurance()) {
                                    connection.commit();
                                    System.out.println("TRANSACTION SUCCEEDED!\n");
                                }
                            }
                            catch (SQLException exception) {
                                System.out.println("TRANSACTION FAILED! ROLLED BACK!\n");
                            }
                            System.out.println("DON'T FORGET TO WRITE DOWN YOUR UNIQUE POLICY ID SO YOU DON'T FORGET IT!");
                            System.out.println("YOU'LL NEED THIS TO MAKE FUTURE INQUIRIES ASSOCIATED WITH OUTSTANDING POLICY/CLAIMS!");
                            System.out.println("YOUR UNIQUE POLICY ID IS: " + policy.getPolicyId());
                    }
                    else if (menue_selection == 2) {
                        policy.setPolicyId(IOManager.idNumber(999999));
                        System.out.println("Enter Policy Type: [1] Single, [2] Family");
                        policy.setType(IOManager.policyType(1));
                        System.out.println("Enter Annual Policy Cost (As Quoted by the Insurance Agent). This Amount Will Be Reviewed and Verified Upon Final Policy Approval.");
                        policy.setCost(IOManager.intInputDouble(1000, 5000));
                        System.out.println("Enter Coverage (Amount Covered by the Policy): ");
                        policy.setCoverage(IOManager.intInputDouble(0, 999999.99));
                        System.out.println("Enter Deductible (Amount You Pay Before Your Insurance Plan Starts to Cover Costs): ");
                        policy.setDeductible(IOManager.intInputDouble(0, 9999.99));
                        System.out.println("Enter Coinsurance (Percentage of Costs of a Covered Health Care Service You Pay): ");
                        policy.setCoinsurance(IOManager.intInputRange(0, 99));
                        System.out.println("Enter Policy Effective Date: ");
                        while (valid_start_date) {
                            effective_date = Input.user_string();
                            valid_start_date = Input.validateDate(effective_date);
                        } 
                        System.out.println("Enter Policy Expiration Date: ");
                        while (valid_end_date) {
                            expire_date = Input.user_string();
                            valid_end_date = Input.validateDate(expire_date);
                        } 
                        policy.setPlan(IOManager.policyPlan(menue_selection));
                        policy.setPolicyStatus("Pending");
                        System.out.print("\n");

                        System.out.print("Enter Year Built: ");
                        policy.setYearBuilt(IOManager.intInput(1800, 2021, 4));
                        System.out.print("Enter Make: ");
                        policy.setMake(IOManager.stringInput(10));
                        System.out.print("Enter Model: ");
                        policy.setModel(IOManager.stringInput(10));
                        System.out.print("Enter Vin Number: ");
                        policy.setVin(IOManager.validVin(17));
                        System.out.print("Enter License Plate: ");
                        policy.setLicensePlate(IOManager.stringInput(7));
                        System.out.print("Enter Driver License: ");
                        policy.setDriverLicense(IOManager.stringInput(20));
                        System.out.print("Enter Total Mileage: ");
                        policy.setTotalMileage(IOManager.intInputRange(0, 999999));
                        System.out.print("Enter Total Annual Mileage: ");
                        policy.setAnnualMiles(IOManager.intInputRange(0, 99999));
                        System.out.print("Enter Market Value: ");
                        policy.setMarketValue(IOManager.intInputRange(1000, 10000000));
                        System.out.print("Enter Date of Birth: ");
                        boolean valid_auto_date = true;
                        while (valid_auto_date) {
                            date_of_birth = Input.user_string();
                            valid_auto_date = Input.validateDate(date_of_birth);
                        } 
                        System.out.print("Enter Gender (Enter [M]ale or [F]emale): ");
                        policy.setGender(IOManager.gender());
                        System.out.print("Enter Credit Score: ");
                        policy.setCreditScore(IOManager.intInput(100, 850, 3));
                        System.out.print("Do You Have Past Traffic Violations? Enter [Y]es or [N]o: ");
                        policy.setTrafficViolatations(IOManager.yesOrNo());
                        System.out.print("Enter Number of Dependants: ");
                        policy.SetDependants(IOManager.intInput(0, 9, 1));
                        System.out.print("Enter US State: ");
                        policy.setState(IOManager.stringInputWithoutNumbers(20)); 
                        System.out.print("Enter Policy Category [1] Collision, [2] Motorist, [3] Liability, [4] Comprehensive, [5] Personal Injury: ");
                        policy.setPlanCategory(IOManager.planCategoryAutoInsurance(1));
                        try{
                            if (policy.insertGenericPolicy() && policy.insertAutoInsurance()) {
                                connection.commit();
                                System.out.println("TRANSACTION SUCCEEDED!\n");
                            }
                        }
                        catch (SQLException exception) {
                            System.out.println("TRANSACTION FAILED! ROLLED BACK!\n");
                        }
                        System.out.println("DON'T FORGET TO WRITE DOWN YOUR UNIQUE POLICY ID SO YOU DON'T FORGET IT!");
                        System.out.println("YOU'LL NEED THIS TO MAKE FUTURE INQUIRIES ASSOCIATED WITH OUTSTANDING POLICY/CLAIMS!");
                        System.out.println("YOUR UNIQUE POLICY ID IS: " + policy.getPolicyId() + "\n");
                    }
                    else if (menue_selection == 3) {
                        policy.setPolicyId(IOManager.idNumber(999999));
                        System.out.println("Enter Policy Type: [1] Single, [2] Family");
                        policy.setType(IOManager.policyType(1));
                        System.out.println("Enter Annual Policy Cost (As Quoted by the Insurance Agent). This Amount Will Be Reviewed and Verified Upon Final Policy Approval.");
                        policy.setCost(IOManager.intInputDouble(1000, 5000));
                        System.out.println("Enter Coverage (Amount Covered by the Policy): ");
                        policy.setCoverage(IOManager.intInputDouble(0, 999999.99));
                        System.out.println("Enter Deductible (Amount You Pay Before Your Insurance Plan Starts to Cover Costs): ");
                        policy.setDeductible(IOManager.intInputDouble(0, 9999.99));
                        System.out.println("Enter Coinsurance (Percentage of Costs of a Covered Health Care Service You Pay): ");
                        policy.setCoinsurance(IOManager.intInputRange(0, 99));
                        System.out.println("Enter Policy Effective Date: ");
                        while (valid_start_date) {
                            effective_date = Input.user_string();
                            valid_start_date = Input.validateDate(effective_date);
                        } 
                        System.out.println("Enter Policy Expiration Date: ");
                        while (valid_end_date) {
                            expire_date = Input.user_string();
                            valid_end_date = Input.validateDate(expire_date);
                        } 
                        policy.setPlan(IOManager.policyPlan(menue_selection));
                        policy.setPolicyStatus("Pending");
                        System.out.print("\n");
                        
                        System.out.print("Enter Policy Category [1] Collision, [2] Motorist, [3] Liability, [4] Comprehensive, [5] Personal Injury: ");
                        policy.setPlanCategory(IOManager.planCategoryHealthInsurance(1));
                        System.out.print("Enter Out of Pocket Maximum: ");
                        policy.setOutOfPocketMax(IOManager.intInputDouble(0.00, 9999.99));
                        System.out.print("Enter Tobacco Use ([Y]es or [N]o): ");
                        policy.setTobaccoUse(IOManager.yesOrNo());
                        System.out.print("Enter Date of Birth: ");
                        String age = "";
                        boolean valid_health_date = true;
                        while (valid_health_date) {
                            age = input.next();
                            valid_health_date = Input.validateDate(age);
                        } 
                        System.out.println("Enter Pre-Existing Conditions ([Y]es or [N]o): ");
                        policy.setPreExistingConditions(IOManager.yesOrNo());
                        System.out.println("Enter Number of Dependants: ");
                        policy.SetDependants(IOManager.intInput(0, 9, 1));
                        System.out.println("Enter Estimated Copay: ");
                        policy.setEstimatedCopay(IOManager.intInputDouble(0.00, 9999.99));
                        try{
                            if (policy.insertGenericPolicy() && policy.insertHealthInsurance()) {
                                connection.commit();
                                System.out.println("TRANSACTION SUCCEEDED!\n");
                            }
                        }
                        catch (SQLException exception) {
                            System.out.println("TRANSACTION FAILED! ROLLED BACK!\n");
                        }
                        System.out.println("DON'T FORGET TO WRITE DOWN YOUR UNIQUE POLICY ID SO YOU DON'T FORGET IT!");
                        System.out.println("YOU'LL NEED THIS TO MAKE FUTURE INQUIRIES ASSOCIATED WITH OUTSTANDING POLICY/CLAIMS!");
                        System.out.println("YOUR UNIQUE POLICY ID IS: " + policy.getPolicyId() + "\n");
                        System.out.println("\n");
                        System.out.println("AND DON'T FORGET TO ADD DEPENDANTS TO YOUR POLICY IF YOU HAVE DEPENDANTS!\n");
                    }
                    else if (menue_selection == 4) {
                        policy.setPolicyId(IOManager.idNumber(999999));
                        System.out.println("Enter Policy Type: [1] Single, [2] Family");
                        policy.setType(IOManager.policyType(1));
                        System.out.println("Enter Annual Policy Cost (As Quoted by the Insurance Agent). This Amount Will Be Reviewed and Verified Upon Final Policy Approval.");
                        policy.setCost(IOManager.intInputDouble(1000, 5000));
                        System.out.println("Enter Coverage (Amount Covered by the Policy): ");
                        policy.setCoverage(IOManager.intInputDouble(0, 999999.99));
                        System.out.println("Enter Deductible (Amount You Pay Before Your Insurance Plan Starts to Cover Costs): ");
                        policy.setDeductible(IOManager.intInputDouble(0, 9999.99));
                        System.out.println("Enter Coinsurance (Percentage of Costs of a Covered Health Care Service You Pay): ");
                        policy.setCoinsurance(IOManager.intInputRange(0, 99));
                        System.out.println("Enter Policy Effective Date: ");
                        while (valid_start_date) {
                            effective_date = Input.user_string();
                            valid_start_date = Input.validateDate(effective_date);
                        } 
                        System.out.println("Enter Policy Expiration Date: ");
                        while (valid_end_date) {
                            expire_date = Input.user_string();
                            valid_end_date = Input.validateDate(expire_date);
                        } 
                        policy.setPlan(IOManager.policyPlan(menue_selection));
                        policy.setPolicyStatus("Pending");
                        System.out.print("\n");
        

                        System.out.print("Enter Policy Category: [1] Whole Life, [2] Universal Life, [3] Term Life");
                        policy.setPlanCategory(IOManager.planCategoryLifeInsurance(1));
                        System.out.print("Enter Date of Birth: ");
                        String age = "";
                        boolean valid_life_date = true;
                        while (valid_life_date) {
                            age = input.next();
                            valid_life_date = Input.validateDate(age);
                        } 
                        System.out.print("Enter Gender ([M]ale or [F]emale): ");
                        policy.setGender(IOManager.gender());
                        System.out.print("Enter Tabacco Use ([Y]es or [N]o): ");
                        policy.setTobaccoUse(IOManager.yesOrNo());
                        System.out.print("Enter Occupation: ");
                        policy.setOccupation(IOManager.stringInputWithoutNumbers(30));
                        System.out.print("Enter Medical Status ([1] Health, [2] Not Healthy): ");
                        policy.setMedicalStatus(IOManager.medicalStatus(1));
                        System.out.print("Briefly Describe Past Medical Family History: ");
                        policy.setFamilyMedicalHistory(IOManager.stringInputWithoutNumbers(255));
                        System.out.print("Enter Primary Beneficiary Name: ");
                        policy.setBeneficiaryName(IOManager.stringInputWithoutNumbers(30));
                        System.out.print("Enter Primary Benefiary Social Security: ");
                        policy.setBeneficiarySocial(IOManager.intInput(100000000, 999999999, 9));                        
                        try{
                            if (policy.insertGenericPolicy() && policy.insertLifeInsurance()) {
                                connection.commit();
                                System.out.println("TRANSACTION SUCCEEDED!\n");
                            }
                        }
                        catch (SQLException exception) {
                            System.out.println("TRANSACTION FAILED! ROLLED BACK!\n");
                        }
                        System.out.println("DON'T FORGET TO WRITE DOWN YOUR UNIQUE POLICY ID SO YOU DON'T FORGET IT!");
                        System.out.println("YOU'LL NEED THIS TO MAKE FUTURE INQUIRIES ASSOCIATED WITH OUTSTANDING POLICY/CLAIMS!");
                        System.out.println("YOUR UNIQUE POLICY ID IS: " + policy.getPolicyId() + "\n");
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
            System.out.println("Customer With ID " + policy.getCustomerId() + " Does NOT Exist! Try Again!");
            System.out.print("Please Enter an Existing 6-digit Customer ID To Create a New Policy: ");
        }
    }
}
