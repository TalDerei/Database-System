package objects.CustomerObjects;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Calendar;
import manager.DBManager;
import java.sql.Connection;

public class Payment {
    /**
     * Prepared Statments 
     */
    public static PreparedStatement policyPayment;
    public static PreparedStatement addDebitCard;
    public static PreparedStatement addCreditCard;

    /**
     * Constructor for creating new Payment objects
     */
    public Payment() {

    }

    public Payment(int payment_id) {

    }

     /**
      * Make a credit card payment to policy
      * @param payment_id
      * @param type
      * @param card_number
      * @param cvv
      * @param policy_id
      * @param expiary_date
      * @return Wether credit card payment is successfull
      */
    public int payCreditCard(int payment_id, String type, long card_number, int cvv, int policy_id, int expiary_date) {
        Connection conn = DBManager.getConnection();

        try {
            Payment.addCreditCard = conn.prepareStatement("INSERT INTO credit (PAYMENT_ID, TYPE, CARD_NUMBER, CVV, POLICY_ID, EXPIARY_DATE) VALUES (?, ?, ?, ?, ?, ?)");
        }
        catch (SQLException exception) {
            System.out.println("Error with Prepared Statements!");
        }
        
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
     * Make a debit card payment to policy
     * @param payment_id
     * @param type
     * @param card_number
     * @param cvv
     * @param policy_id
     * @param expiary_date
     * @return Whether debit card transaction is successful
     */
    public int payDebitCard(int payment_id, String type, long card_number, int cvv, int policy_id, int expiary_date) {
        Connection conn = DBManager.getConnection();

        try {
            Payment.addDebitCard = conn.prepareStatement("INSERT INTO debit (PAYMENT_ID, TYPE, CARD_NUMBER, CVV, POLICY_ID, EXPIARY_DATE) VALUES (?, ?, ?, ?, ?, ?)");
        }
        catch (SQLException exception) {
            System.out.println("Error with Prepared Statements!");
        }

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
     * Allow user to make policy payment
     * @param payment_id
     * @param policy_id
     * @param recipient_name
     * @param recipient_address
     * @param bank
     * @param payment_amount
     * @param status
     * @return Whether payment is successfull
     */
    public int makePolicyPayment(int payment_id, int policy_id, String recipient_name, String recipient_address, String bank, int payment_amount, String status) {  
        Connection conn = DBManager.getConnection();

        try {
            Payment.policyPayment = conn.prepareStatement("INSERT INTO policy_payment (PAYMENT_ID, POLICY_ID, RECIPIENT_NAME, RECIPIENT_ADDRESS, BANK, PAYMENT_AMOUNT, PAYMENT_DATE, STATUS) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
        }
        catch (SQLException exception) {
            System.out.println("Error with Prepared Statements!");
        }
        
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
     * Check the status of a payment
     * @param calendar
     * @param localdate
     * @return Whether payment status is successfull
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

    public static java.sql.Date currentDate() {
        java.util.Date current = new java.util.Date();
        return new java.sql.Date(current.getTime());
    }
}
