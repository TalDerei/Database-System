package views.customerViews;

import java.sql.*;

import manager.IOManager;
import objects.CustomerObjects.*;

public class MakePolicyPayment {
    
    /**
     * [5] Make a Policy Payment
     */
    public static void make_policy_payment(Connection connection) {
        System.out.print("Enter Existing Policy ID to Make a Payment: ");
        int policy_id = Input.user_integer();
        success_value = Policy.getPolicyID(policy_id);
        if (success_value == policy_id) {
            int cost = customer_view.getPolicyCost(policy_id);
            Date[] dates = new Date[2];
            Date payment_date_check = customer_view.currentDate();
            System.out.println(payment_date_check);
            System.out.println(dates[0]);
            dates = customer_view.getPolicyDate(policy_id);
            LocalDate localdate = LocalDate.now();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(dates[0]);
            String status = customer_view.checkPaymentStatus(calendar, localdate);
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
                            int success1 = customer_view.payDebitCard(payment_id, type, card_number, cvv, policy_id, expiary_date);
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
                            int success2 = customer_view.makePolicyPayment(payment_id, policy_id, recipient_name, recipient_address, bank, cost, status);
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
                            int success1 = customer_view.payCreditCard(payment_id, type, card_number, cvv, policy_id, expiary_date);
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
                            int success2 = customer_view.makePolicyPayment(payment_id, policy_id, recipient_name, recipient_address, bank, cost, status);
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
}
