/**
 * Course: CSE 341
 * Semester and Year: Spring 2021
 * Assignment: Database Systems
 * Author: Derei, Tal
 * User ID: tad222
 */

import java.util.Scanner;
import java.util.ArrayList;
import java.util.*;
import java.text.*;
import java.sql.*;
import java.sql.Date;

/**
 * Insurance database for executing SQL queries using JBDC
 */
public class Insurance {
    /**
     * Prepared Statments associated with customer interface
     */
    private Connection connect;
    private PreparedStatement createCustomer;
    private PreparedStatement addPolicy;
    private PreparedStatement dropPolicy;
    private PreparedStatement addClaim;
    private PreparedStatement addVehicle;
    private PreparedStatement makePayment;
    private PreparedStatement checkCustomerID;

    /**
     * Establish a connection to the Oracle database
     */
    public static Insurance connect_database(String user, String pass) {
        Insurance database = new Insurance();
        try (Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@edgar1.cse.lehigh.edu:1521:cse241",user,pass);
        ) {
            System.out.println("Connection to the oracle database succeeded!\n");
            database.connect = connection;
            customer_interface(database);        
        } catch (SQLException exception) {
            exception.printStackTrace();
            System.out.println("connection to the oracle database failed! Try again!");
            return null;
        }
         return database;
    }

    /**
     * Command-line interface for customers
     */
    public static void customer_interface(Insurance database) {
        /**
         * Prepared Statements for customer interface
         */
        try {
            database.createCustomer = database.connect.prepareStatement("INSERT INTO customer (CUSTOMER_ID, NAME, SOCIAL_SECURITY, EMAIL, ZIP_CODE, CITY, STATE, ADDRESS, DATE_OF_BIRTH, PHONE_NUMBER) VALUES (?,?,?,?,?,?,?,?,?,?)");
            database.addPolicy = database.connect.prepareStatement("INSERT INTO policy NATURAL JOIN home_insurance (POLICY_ID, CUSTOMER_ID, POLICY_TYPE, COVERAGE, POLICY_COST, CITY, STATE, ZIP_CODE, ADDRESS, YEAR_BUILT, CONDITION, SQUARE_FOOT, LOT_SIZE, CREDIT_SCORE, MORTGAGE_PAYMENT, MARKET_VALUE, PERSONAL_PROPERTY_REPLACEMENT) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) WHERE CUSTOMER_ID = ?");
            database.dropPolicy = database.connect.prepareStatement("DELETE FROM policy WHERE POLICY_ID = ?");
            database.addClaim = database.connect.prepareStatement("INSERT INTO claim (CLAIM_ID, CUSTOMER_ID, DESCRIPTION, CLAIM_TYPE, CLAIM_PAYMENT) VALUES (?, ?, ?, ?, ?)");
            database.addVehicle = database.connect.prepareStatement("INSERT INTO auto_insurance (POLICY_ID, YEAR, MAKE, MODEL, VIN_NUMBER, MARKET_VALUE, AGE, GENDER, GEOGRAPHIC_LOCATION, CREDIT_HISTORY, DRIVING_RECORD, ANNUAL_MILES) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            database.checkCustomerID = database.connect.prepareStatement("SELECT customer_id FROM customer WHERE customer_id = ?");
            // database.makePayment = database.connect.prepareStatement("INSERT INTO auto_insurance (POLICY_ID, YEAR, MAKE, MODEL, VIN_NUMBER, MARKET_VALUE, AGE, GENDER, GEOGRAPHIC_LOCATION, CREDIT_HISTORY, DRIVING_RECORD, ANNUAL_MILES) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
        }
        catch (SQLException exception) {
            System.out.println("Error with Prepared Statements!");
            exception.printStackTrace();
        }
        /**
         * Command-line interface for customer interface
         */
        System.out.println("[1] Create a new customer\n");
        System.out.println("[2] Add a policy\n");
        System.out.println("[3] Drop a policy\n");
        System.out.println("[4] Make a claim\n");
        System.out.println("[5] Add/remove/replace a vehicle\n");

        //             query for policy type 
        //                 natural join policy with policy type
        //         else 
        //             prompt user to renter customer_id or create new customer
                

        Scanner input = new Scanner(System.in);
        int menue_selection;
        int success; 
        String date_of_birth = "";
        boolean valid_start_date = true;
        while (true) {
            boolean condition = input.hasNextInt();
            if (condition) {
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
                    success = database.insertCustomer(customer_id, name, social_security, email, zip_code, city, state, address, date_of_birth, phone_number);
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
                            condition = input.hasNextInt();
                            if (condition) {
                                menue_selection = input.nextInt();
                                if (menue_selection == 1) {
                                    System.out.print("Policy 1\n");
                                    break;
                                }
                                else if (menue_selection == 2) {
                                    System.out.print("Policy 3\n");
                                    break;
                                }
                                else if (menue_selection == 3) {
                                    System.out.print("Policy 2\n");
                                    break;
                                }
                                else if (menue_selection == 4) {
                                    System.out.print("Policy 4\n");
                                    break;
                                }
                                else {
                                    System.out.println("invalid input! Try again!");
                                    input.next();
                                }
                            }
                        }
                    }

                    // String policy_id = user_string("policy_id");
                    // String customer_id = user_string("customer_id");
                    // String policy_type = user_string("policy_type");
                    // String coverage = user_string("coverage");
                    // String policy_cost = user_string("policy_cost");
                    // success = database.insertPolicy(policy_id, customer_id, policy_type, coverage, policy_cost);
                    // System.out.print("success value is: " + success);
                    // break;
                }
                else if (menue_selection == 3) {
                    String policy_id = user_string("policy_id");
                    success = database.deletePolicy(policy_id);
                    System.out.print("success value is: " + success);
                    break;
                }
                else if (menue_selection == 4) {
                    String claim_id = user_string("claim_id");
                    String customer_id = user_string("customer_id");
                    String description = user_string("description");
                    String claim_type = user_string("claim_type");
                    String claim_payment = user_string("claim_payment");
                    success = database.insertClaim(claim_id, customer_id, description, claim_type, claim_payment);
                    System.out.print("success value is: " + success);
                    break;
                }
                else if (menue_selection == 5) {
                    String policy_id = user_string("policy_id");
                    String year = user_string("year");
                    String make = user_string("make");
                    String model = user_string("model");
                    String vin_number = user_string("vin_number");
                    String market_value = user_string("market_value");
                    String gender = user_string("gender");
                    String geographic_location = user_string("geographic_location");
                    String credit_history = user_string("credit_history");
                    String driving_record = user_string("driving_record");
                    String annual_miles = user_string("annual_miles");
                    success = database.insertAutoInsurance(policy_id, year, make, model, vin_number, market_value, date_of_birth, gender, geographic_location, credit_history, driving_record, annual_miles);
                    System.out.print("success value is: " + success);
                    break;
                }
            }
            else {
                System.out.println("invalid input! Try again!");
                input.next();
            }
    }
}

    // INSERT INTO TAD222.AUTO_INSURANCE(POLICY_ID, YEAR, MAKE, MODEL, VIN_NUMBER, MARKET_VALUE, AGE, GENDER, GEOGRAPHIC_LOCATION, CREDIT_HISTORY, DRIVING_RECORD, ANNUAL_MILES) VALUES

    // create table auto_insurance(
    // policy_id varchar(5),
    // year numeric (4,0),
    // make varchar(10),
    // model varchar(10),
    // vin_number varchar(17),
    // market_value numeric (10,2),
    // age varchar (2),
    // gender varchar (4),
    // geographic_location varchar(20),
    // credit_history numeric (3,0),
    // driving_record varchar(10),
    // annual_miles numeric (6,0),
    // primary key (policy_id),
    // foreign key (policy_id) references policy(policy_id) on delete set null);


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
    public int insertCustomer(int customer_id, String name, int social_security, String email, int zip_code, String city, String state, String address, String date_of_birth, long phone_number) {
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
            createCustomer.setLong(10, phone_number);
            success = createCustomer.executeUpdate();
        }
        catch (SQLException exception) {
            System.out.println("invalid input!");
            exception.printStackTrace();
        }
        return success;
    }

    /**
     * Function for inserting new customer policy into the database
     */
    public int insertPolicy(String policy_id, String customer_id, String policy_type, String coverage, String policy_cost) {
        int success = 0;
        try {
            addPolicy.setString(1, policy_id);
            addPolicy.setString(2, customer_id);
            addPolicy.setString(3, policy_type);
            addPolicy.setString(4, coverage);
            addPolicy.setString(5, policy_cost);
            success = addPolicy.executeUpdate();
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
    public int insertClaim(String claim_id, String customer_id, String description, String claim_type, String claim_payment) {
        int success = 0;
        try {
            addClaim.setString(1, claim_id);
            addClaim.setString(2, customer_id);
            addClaim.setString(3, description);
            addClaim.setString(4, claim_type);
            addClaim.setString(5, claim_payment);
            success = addClaim.executeUpdate();
        }
        catch (SQLException exception) {
            System.out.println("invalid input!");
        }
        return success;
    }

    /**
     * Function for inserting new customer vehicle (auto insurance) into the database
     */
    public int insertAutoInsurance(String policy_id, String year, String make, String model, String vin_number, String market_value, String date_of_birth, String gender, String geographic_location, String credit_history, String driving_record, String annual_miles) {
        int success = 0;
        try {
            addVehicle.setString(1, policy_id);
            addVehicle.setString(2, year);
            addVehicle.setString(3, make);
            addVehicle.setString(4, model);
            addVehicle.setString(5, vin_number);
            addVehicle.setString(6, market_value);
            addVehicle.setString(7, date_of_birth);
            addVehicle.setString(8, gender);
            addVehicle.setString(9, geographic_location);
            addVehicle.setString(10, credit_history);
            addVehicle.setString(11, driving_record);
            addVehicle.setString(12, annual_miles);
            success = addVehicle.executeUpdate();
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
        input.close();
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
        input.close();
        return "0";
    }

    /**
     * validateDate check's the validity of the user's start and finish dates
     */
    public static Boolean validateDate(String date_of_birth) {
    DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    try {
        format.parse(date_of_birth);
    } catch (ParseException e) {
        System.out.println("date invalid. Please try again!");
        return true;
    }
    return false;
}

    public static String policy_cost(String message) {
        return "";
    }

    /**
     * getCustomerID checks if customer id is in the database when user attempts to add a policy
     */
    public int getCustomerID(int customer_id) {
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
// need to tackling adding existing family members/friends to an insurance policy
// concept of existing or new user in customer interface?
// insurance can be:  life insurance: term life, whole life and universal life.
// start readme with assumptions and clarifications made
// add foreign keys: policy_payment(payment_amount) references cost in policy + claim_payment(payment_amount) references cost in claim
// add dependants table

// No one can be sure what losses they may suffer – not everyone's risk will be the same. ... Because of this, insurance premiums will vary from person to person because insurers try to make sure that each policyholder pays a premium that reflects their own particular level of risk.
// print customer_id, policy_id, claim_id!
// In a standard homeowners insurance policy, you have coverage for both Personal Liability and Personal Property.
// https://www.youngalfred.com/homeowners-insurance/whats-the-difference-between-personal-liability-and-personal-property-claims
// simplification: only one beneficiary
// policy cost = 500 - 5000
// claim payouts 100 - 100,000


// Limitations
    // can only add dependants at the beggining in policy creation