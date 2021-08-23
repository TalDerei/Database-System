package interfaces;

import java.util.Scanner;
import java.sql.*;
import views.Adjuster_View;
import manager.IOManager;

/**
 * Adjuster Interface
 */
public class Adjuster_Interface {
    /**
     * Command-line interface for adjusters
     * @param conn Connection object
     */
    public static void Adjuster(Connection conn) {
        Adjuster_View adjuster_view = new Adjuster_View();
        adjuster_view.prepare(conn);

        /**
         * Command-line interface for adjusters
         */
        Scanner input = new Scanner(System.in);
        int menue_selection;
        int success; 
        while (true) {
            System.out.println("--------------------------------------------------------------");
            System.out.println("[1] Get all customers associated with an adjuster\n");
            System.out.println("[2] Identify claims that have not been serviced recently\n");
            System.out.println("[3] Update a customer's claim\n");
            System.out.println("[4] Get all claims associated with a particular customer\n"); 
            System.out.println("[5] Get claim information associated with a customer's claim\n"); 
            System.out.println("[6] Get all customers (and dependants) associated with a particular claim\n"); 
            System.out.println("[7] Get all agents an adjuster communicates with\n");
            System.out.println("[8] Get all claims an adjuster manages\n");
            System.out.println("[9] Assign external remediation firms or body shops to claims\n");
            System.out.println("[10] Add an item to an existing claim request\n");
            System.out.println("[11] Get All Items in a Claim\n");
            System.out.println("[12] Make a Claim Payment to a Particular Customer on a Policy\n");
            System.out.println("[13] Disconnect!");
            System.out.println("--------------------------------------------------------------");
            System.out.print("\nSelect From the List of Options Above: ");
            boolean conditional = input.hasNextInt();
            if (conditional) {
                menue_selection = input.nextInt();
                if (menue_selection == 1) {
                    System.out.print("\nEnter 6-Digit Adjuster ID to Retrieve All Customers Managed By That Adjuster: ");
                    int adjuster_id = adjuster_view.user_integer();
                    success = adjuster_view.getAdjusterID(adjuster_id);
                    if (adjuster_id == success) {
                        adjuster_view.getAdjusterCustomers(adjuster_id);
                    }
                }
                else if (menue_selection == 2) {
                    adjuster_view.getPendingClaim();
                }
                else if (menue_selection == 3) {
                    int index;
                    int intPlaceHolder = 0;
                    String stringPlaceHolder = "";
                    System.out.print("Please Enter an Existing 6-digit Claim ID To Update the Claim: ");
                    int claim_id = adjuster_view.user_integer();
                    success = adjuster_view.getClaimID(claim_id);
                    if (claim_id == success) {
                        System.out.println("");
                        System.out.println("\n--------------------------------------------------------------");
                        System.out.println("[1] Change Accident Associated With Customer Claim");
                        System.out.println("[2] Change Items Damaged Associated With Customer Claim");
                        System.out.println("[3] Change Description Associated With Customer Claim");
                        System.out.println("[4] Change Decision Associated with customer claim");
                        System.out.println("[5] Change Adjuster Notes Associated With Customer Claim");
                        System.out.println("[6] Change Amount Associated With Customer Claim");
                        System.out.println("[7] Change Status Associated With Customer Claim");
                        System.out.println("--------------------------------------------------------------\n");
                        System.out.print("Please Choose What Part of the Claim You Would Like to Update: ");
                        menue_selection = input.nextInt();
                        if (menue_selection == 1) {
                            index = 2;
                            System.out.println("Update Accident: [1] Wind and Hail, [2] Theft, [3] Car Wreck, [4] Health-Related, [5] Personal Injury, [6] ater Damage, [7] Fire Damage ");
                            String accident = IOManager.accident(1);
                            adjuster_view.updateCustomerClaim(claim_id, intPlaceHolder, accident, index);
                        }
                        else if (menue_selection == 2) {
                            index = 3;
                            System.out.println("Update Items Damaged: ([Y]es or [N]o)");
                            String items_damaged = IOManager.yesOrNo();
                            adjuster_view.updateCustomerClaim(claim_id, intPlaceHolder, items_damaged, index);
                        }
                        else if (menue_selection == 3) {
                            index = 4;
                            System.out.println("Update Description: ");
                            String description = IOManager.stringInput(255);
                            adjuster_view.updateCustomerClaim(claim_id, intPlaceHolder, description, index);
                        }
                        else if (menue_selection == 4) {
                            index = 5;
                            System.out.println("Update decision: [1] Pending, [2] Active, [3] Inactive ");
                            String decision = IOManager.policyStatus(1);
                            adjuster_view.updateCustomerClaim(claim_id, intPlaceHolder, decision, index);
                        }
                        else if (menue_selection == 5) {
                            index = 6;
                            System.out.println("Update Adjuster Notes: ");
                            String adjuster_notes = IOManager.stringInput(255);
                            adjuster_view.updateCustomerClaim(claim_id, intPlaceHolder, adjuster_notes, index);
                        }
                        else if (menue_selection == 6) {
                            index = 7;
                            System.out.println("Update Amount: ");
                            double amount = IOManager.intInputDouble(0.00, 999999.99);
                            adjuster_view.updateCustomerClaim(claim_id, amount, stringPlaceHolder, index);
                        }
                        else if (menue_selection == 7) {
                            index = 8;
                            System.out.println("Update Status: [1] Pending, [2] Active, [3] Inactive");
                            String status = IOManager.policyStatus(1);
                            adjuster_view.updateCustomerClaim(claim_id, intPlaceHolder, status, index);
                        }
                        else {
                            System.out.println("Invalid Claim ID! Try Again!");
                        }
                    }
                    else {
                        System.out.println("Invalid Claim ID!");
                    }
                }
                else if (menue_selection == 4) {
                    System.out.print("Enter Existing Customer ID: ");
                    int customer_id = adjuster_view.user_integer();
                    success = adjuster_view.getCustomerID(customer_id);
                    if (customer_id == success) {
                        adjuster_view.getAllCustomerClaimList(customer_id);
                    }
                }
                else if (menue_selection == 5) {
                    System.out.print("Enter Existing Claim ID: ");
                    int claim_id = adjuster_view.user_integer();
                    success = adjuster_view.getClaimID(claim_id);
                    if (claim_id == success) {
                        success = adjuster_view.getClaimInfo(claim_id);
                    }
                }
                else if (menue_selection == 6) {
                    System.out.print("Enter Existing Claim ID: ");
                    int claim_id = adjuster_view.user_integer();
                    success = adjuster_view.getClaimID(claim_id);
                    if (claim_id == success) { 
                        success = adjuster_view.getAllCustomersClaim(claim_id);
                    }
                }
                else if (menue_selection == 7) {
                    System.out.print("Enter Existing Adjuster ID: ");
                    int adjuster_id = adjuster_view.user_integer();
                    success = adjuster_view.getAdjusterID(adjuster_id);
                    if (adjuster_id == success) { 
                        success = adjuster_view.getAgentAdjuster(adjuster_id);
                    }
                }
                else if (menue_selection == 8) {
                    System.out.print("Enter Existing Adjuster ID: ");
                    int adjuster_id = adjuster_view.user_integer();
                    success = adjuster_view.getAdjusterID(adjuster_id);
                    if (adjuster_id == success) { 
                        success = adjuster_view.getAdjusterManages(adjuster_id);
                    }
                }
                else if (menue_selection == 9) {
                    System.out.print("Enter Existing Claim ID: ");
                    int claim_id = adjuster_view.user_integer();
                    success = adjuster_view.getClaimID(claim_id);
                    if (claim_id == success) { 
                        System.out.print("Company Name: ");
                        String name = IOManager.stringInputWithoutNumbers(30);
                        System.out.println("Enter Company Type: [1] Single, [2] Family ");
                        String type = IOManager.policyType(1);
                        System.out.print("Company Contact Information (Phone Number): ");
                        String phone_number = IOManager.validPhoneNumber(10);
                        int success2 = adjuster_view.adjusterCompany(name, type, phone_number);
                        int success1 = adjuster_view.adjusterOutsourcing(claim_id, name);
                        try{
                            if (success1 + success2 == 2) {
                                conn.commit();
                                System.out.println("TRANSACTION SUCCEEDED!\n");
                            }
                        }
                        catch (SQLException exception) {
                        }
                    }
                }
                else if (menue_selection == 10) {
                    System.out.print("Enter Existing Claim ID: ");
                    int claim_id = adjuster_view.user_integer();
                    success = adjuster_view.getClaimID(claim_id);
                    if (claim_id == success) {
                        int item_id = IOManager.idNumber(999999);
                        int policy_id = adjuster_view.getPolicyClaim(claim_id);
                        System.out.print("Company Item Type: ");
                        String item_type = IOManager.stringInputWithoutNumbers(20);
                        System.out.print("Company Item Value: ");
                        double item_value = IOManager.intInputDouble(0.00, 999999.99);
                        System.out.print("Company Item Description: ");
                        String description = IOManager.stringInput(255);
                        adjuster_view.insertClaimItem(item_id, claim_id, policy_id, item_type, item_value, description);
                    }
                }
                else if (menue_selection == 11) {
                    System.out.print("Enter Existing Claim ID: ");
                    int claim_id = adjuster_view.user_integer();
                    success = adjuster_view.getClaimID(claim_id);
                    if (claim_id == success) {
                        adjuster_view.retrieveClaimItems(claim_id);
                    }
                }
                else if (menue_selection == 12) {
                    System.out.print("Enter Existing Claim ID to Make a Payment: ");
                    int claim_id = adjuster_view.user_integer();
                    int success_value = adjuster_view.getClaimID(claim_id);
                    if (success_value == claim_id) {
                        double payment_amount = adjuster_view.getClaimCost(claim_id);
                        if (payment_amount != 0) {
                            System.out.println("How Would You Like To Pay? \n");
                            System.out.println("[1] Checks\n");
                            System.out.println("[2] ACH Transfer\n");
                            while (true) {
                                conditional = input.hasNextInt();
                                if (conditional) {
                                    menue_selection = input.nextInt();
                                    if (menue_selection == 1) {
                                        int payment_id = IOManager.idNumber(999999);
                                        System.out.print("Enter Claim Holder's Name: ");
                                        String recipient_name = IOManager.stringInputWithoutNumbers(30);
                                        System.out.print("Enter Policy Holders Address: ");
                                        String recipient_address = IOManager.stringInput(50);
                                        System.out.println("Enter Bank: [1] BofA, [2] Citigroup, [3] Chase, [4] Wells Fargo");
                                        String bank = IOManager.bank(1);
                                        System.out.println("Enter Claim Payment Date");
                                        boolean valid_payment_date = true;
                                        String payment_date = "";
                                        while (valid_payment_date) {
                                            payment_date = input.next();
                                            valid_payment_date = adjuster_view.validateDate(payment_date);
                                        } 
                                        System.out.println("Enter Status: [1] Payed, [2] Not Payed");
                                        String status = IOManager.ClaimStatus(1);
                                        int success1 = adjuster_view.makeClaimPayment(payment_id, claim_id, recipient_name, recipient_address, payment_amount, bank, payment_date, status);
                                        int check_number = IOManager.idNumber(999999);
                                        String check_date = payment_date;
                                        System.out.println("Enter Account Number: ");
                                        long account_number = IOManager.intInputLong(12);
                                        System.out.println("Enter Routing Number: ");
                                        long routing_number = IOManager.intInputLong(9);
                                        int success2 = adjuster_view.checkPayment(payment_id, check_number, check_date, claim_id, bank, account_number, routing_number);
                                        try{
                                            if (success1 + success2 == 2) {
                                                conn.commit();
                                                System.out.println("Have A Nice Day!\n");
                                                System.exit(0);
                                            }
                                        }
                                        catch (SQLException exception) {
                                            System.out.print("TRANSACTION SUCCEEDED!\n");
                                            System.out.println("Have A Nice Day!\n");
                                            System.exit(0);
                                        }
                                    }
                                    else if (menue_selection == 2) {
                                        int payment_id = IOManager.idNumber(999999);
                                        System.out.print("Enter Claim Holder's Name: ");
                                        String recipient_name = IOManager.stringInputWithoutNumbers(30);
                                        System.out.print("Enter Policy Holders Address: ");
                                        String recipient_address = IOManager.stringInput(50);
                                        System.out.println("Enter Bank: [1] BofA, [2] Citigroup, [3] Chase, [4] Wells Fargo");
                                        String bank = IOManager.bank(1);
                                        System.out.println("Enter Claim Payment Date");
                                        boolean valid_payment_date = true;
                                        String payment_date = "";
                                        while (valid_payment_date) {
                                            payment_date = input.next();
                                            valid_payment_date = adjuster_view.validateDate(payment_date);
                                        } 
                                        System.out.println("Enter Status: [1] Payed, [2] Not Payed");
                                        String status = IOManager.ClaimStatus(1);
                                        int success1 = adjuster_view.makeClaimPayment(payment_id, claim_id, recipient_name, recipient_address, payment_amount, bank, payment_date, status);
                                        System.out.println("Enter Account Number: ");
                                        long account_number = IOManager.intInputLong(12);
                                        System.out.println("Enter Routing Number: ");
                                        long routing_number = IOManager.intInputLong(9);
                                        int success2 = adjuster_view.makeACHTransfer(payment_id, account_number, routing_number, claim_id);
                                        try{
                                            if (success1 + success2 == 2) {
                                                conn.commit();
                                                System.out.println("Have A Nice Day!\n");
                                                System.exit(0);
                                            }
                                        }
                                        catch (SQLException exception) {
                                            System.out.print("TRANSACTION SUCCEEDED!\n");
                                            System.out.println("Have A Nice Day!\n");
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
                            System.out.println("Invalid Input! Claim ID Doesn't Seem to Exist!");
                        }
                    }
                    else {
                        System.out.println("Invalid Input! Claim ID Doesn't Seem to Exist!");
                    }
                }
                else if (menue_selection == 13) {
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

