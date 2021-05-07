/**
 * Course: CSE 341
 * Semester and Year: Spring 2021
 * Assignment: Database Systems
 * Author: Derei, Tal
 * User ID: tad222
 */

package insurance;

import java.sql.*;
import java.util.*;
import java.text.*;

public class Corporate_Interface {
    public Connection connect;
     /**
     * Establish a connection to the Oracle database
     */
    public static Corporate_Interface connect_database() {
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
        
        Corporate_Interface database = new Corporate_Interface();
        try (Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@edgar1.cse.lehigh.edu:1521:cse241",username,password);
        ) {
            System.out.println("\nConnection to the oracle database succeeded!\n");
            connection.setAutoCommit(false);
            database.connect = connection;   
            Corperate(database);         
        } catch (SQLException exception) {
            System.out.println("\nConnection to the oracle database failed! Try again!\n");
            return connect_database();
        }
        return database;
    }

     /**
     * Command-line interfaces for corperate management
     */
    public static void Corperate(Corporate_Interface database) {
        System.out.println("--------------------------------------------------------------");
        System.out.println("[1] Generate report on revenue\n");
        System.out.println("[2] Generate report on claims paid\n");
        System.out.println("[3] Generate report on profits based on policy type\n");
        System.out.println("[4] Get salary of an employee (agent or adjuster)\n");
        System.out.println("[5] Give an employee (agent or adjuster) a raise\n");
        System.out.println("[6] Terminate an employee (agent or adjuster) from the company\n");
        System.out.println("[7] Add an agent to the company\n");
        System.out.println("[8] Add an adjuster to the company\n");
        System.out.println("--------------------------------------------------------------");
        System.out.println("\n");
        System.out.println("\n");
        System.out.println("THE CORPERATE INTERFACE IS DOWN FOR MAINTENENCE AS THE DEPARTMENT IS UNDERGOING A RESTRUCTUERING!");
    }
}

 

// // customer policy: 
//     // for entering cusomer policy, can have fixed customer policy rates for simplified db
//     // IDs generated server side
// // customer can change the payments to anything they want when adding a policy and payment********
// // way to differentiate in functions whether user entered invalid input or something already exist in database
// // https://blog.developer.atlassian.com/10-design-principles-for-delightful-clis/
// // agents can alter customer's policy's
// // function to create random 6 digit int
// // concept of existing or new user in customer interface?
// // need to change constrain on health/home/auto/life insurance policies from on delete set null to on delete cascade if a policy (associated with customer ID is deleted)
// // can make multiple payments to same policy -- for now ignore
// // double check ER diagram matches sql schmea
// // customer wants to check their policy information -- policy_id or customer_id to check status of all their policies
// // customer wants to check the status of a claim -- policy_id or customer_id to check status of all their policies
// // customer wants to add dependants to a policy -- policy_id
// // customer wants to add/drop vehicle to auto_insurance
// // Getting policy information, claim informaition using customer_id, policy_id, or claim_id instead of just one?
// // customer_ID does not exist vs a transaction failed!
// // go back through and start deciding what values need to be generated server side (i.e. status in polcy_payment or IDs)
// // add input checking wrapper to allow user to enter [1], [2], [3] where appropriate
// // change policy costs to double instead of int to capture decimal points
// // do policy_id, agent_id, adjuster_id, and customer_id checking on each query to make sure the IDs exist (allowing us to differentiate between id not existing in database and database query error)
// // For example, if customer logs in,4 your interface should allow policy numbers to be displayed rather that expecting users to remember such information.
// // disconnect() to database

// // Clean Up:    
//     // start readme with assumptions and clarifications made
//     // link github in the README
//     // change description of menue items
//     // make input statements more detailed (user_string and user_int taking two inputs)
//     // comments with paremenets
//     // fix try and catch blocks
//     // clean up resource leaks later
//     // catch sql ORA exceptions in code
//     // catch hanging statemeinputnts
//     // potential UI for CLI?
//     // REGENERATE DATA GENERATION TO MATCH UPDATED SCHEMA AND ER DIAGRAMS! CHECK CONSISTENCY OF ER + SCHEMA
//     // list of entity and relationship sets in README

// // Assumptions:
//     // No one can be sure what losses they may suffer – not everyone's risk will be the same. ... Because of this, insurance premiums will vary from person to person because insurers try to make sure that each policyholder pays a premium that reflects their own particular level of risk.
//     // print customer_id, policy_id, claim_id!
//     // In a standard homeowners insurance policy, you have coverage for both Personal Liability and Personal Property.
//     // https://www.youngalfred.com/homeowners-insurance/whats-the-difference-between-personal-liability-and-personal-property-claims
//     // simplification: only one beneficiary
//     // policy cost = 500 - 5000
//     // claim payouts 100 - 100,000
//     // https://www.iii.org/article/what-beneficiary#:~:text=A%20beneficiary%20is%20the%20person,One%20person
//     // https://www.nolo.com/legal-encyclopedia/types-of-traffic-violations.html
//     // https://www.healthcare.gov/how-plans-set-your-premiums/
//     // https://www.youngalfred.com/homeowners-insurance/whats-the-difference-between-personal-liability-and-personal-property-claims
//     // customer has one dedicated agent, and multiple agnets can comunicate with multiple adjusters and vice versa
//     // The price of a policy is quoted per year, but customers pay monthly
//     // Payments: my policy has ‘expire_date’ and ‘effective_date’ fields that quotes  a policy for a year. Policy_payments has ‘date’ and ‘status’ fields. When user makes their monthly payment, the date will automatically be inserted when they made their payment. Then you treat effective_date as a starting point, and just increment that by one month every time user makes a payment. Then you can identify whether it’s on time or overdue based on whether payment_date > (expire_date - effective_date) / 12 
//     // for simplification, assume that everyone's insyrance is due first of the month? Some DB strain implications, implement staggered approach down the line
//     // Another assumption, I don't allow for partial payments instead a policy must be payed in full for the following billing month, otherwise it's flagged as overdue. There would be some consequences such as late fees, which I don't currently handle. 
//     // Your first monthly payment is due on the same day of the month as your original policy start date. For example, if your car insurance policy started on April 12th, your monthly payments are due on the 12th of each month.
//     // Don't currently support policy renewals, isntead you'll need to make a new policy
//     // cascading delete
//     // disconnect from database tab
//     // limiting amount of inner join operations which are costly
//     // couldve reduced number of tables that resulted from relationship sets (tradeoff between size and computation of computing innerjoins)
//     // point of relationship sets was to reduce the size of my natural join operations

// // Limitations:
//     // can only add dependants at the beggining in policy creation
//     // for simplification you can only associate one home with a home insruance polciy
//     // None of the major insurance companies will offer you a policy on a month-to-month basis, but you might have the option to cancel your policy before the six month term is finished and get a refund on the remainder of the term
//     // // Rather than keeping a status monicer, if customer doesn't pay, we asssume policy is cancled and remove it from database if customer doesn't renue. In a future iterations, we might consider keeping the policy in the database (rather than delte on cascade) and keep status of the policy on hand
