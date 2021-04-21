/**
 * Course: CSE 341
 * Semester and Year: Spring 2021
 * Assignment: Database Systems
 * Author: Derei, Tal
 * User ID: tad222
 */

import java.util.Scanner;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.ResultSet;

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
            // exception.printStackTrace();
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
         * Command-line interface for customer interface
         */
        System.out.println("[1] Create a new customer in the database\n");
        System.out.println("[2] Add a policy\n");
        System.out.println("[3] Drop a policy\n");
        System.out.println("[4] Make a claim\n");
        System.out.println("[5] Add/remove/replace a vehicle\n");

        Scanner input = new Scanner(System.in);
        int menue_selection;
        int success; 
        while (true) {
            boolean condition = input.hasNextInt();
            if (condition) {
                menue_selection = input.nextInt();
                if (menue_selection == 1) {
                    String customer_id = user_string("customer_id");
                    String name = user_string("name");
                    String address = user_string("address");
                    String age = user_string("age");
                    String phone_number = user_string("phone_number");
                    success = database.insertCustomer(customer_id, name, address, age, phone_number);
                    System.out.print("success value is: " + success);
                    break;
                }
                else if (menue_selection == 2) {
                    String policy_id = user_string("policy_id");
                    String customer_id = user_string("customer_id");
                    String policy_type = user_string("policy_type");
                    String coverage = user_string("coverage");
                    String policy_cost = user_string("policy_cost");
                    success = database.insertPolicy(policy_id, customer_id, policy_type, coverage, policy_cost);
                    System.out.print("success value is: " + success);
                    break;
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
                    String age = user_string("age");
                    String gender = user_string("gender");
                    String geographic_location = user_string("geographic_location");
                    String credit_history = user_string("credit_history");
                    String driving_record = user_string("driving_record");
                    String annual_miles = user_string("annual_miles");
                    success = database.insertAutoInsurance(policy_id, year, make, model, vin_number, market_value, age, gender, geographic_location, credit_history, driving_record, annual_miles);
                    System.out.print("success value is: " + success);
                    break;
                }
                
                
            }
            else {
                System.out.println("invalid input! Try again!");
                input.next();
            }


        /**
         * Prepared Statements for customer interface
         */
        try {
            database.createCustomer = database.connect.prepareStatement("INSERT INTO customer (CUSTOMER_ID, NAME, ADDRESS, AGE, PHONE_NUMBER) VALUES (?,?,?,?,?)");
            database.addPolicy = database.connect.prepareStatement("INSERT INTO policy (POLICY_ID, CUSTOMER_ID, POLICY_TYPE, COVERAGE, POLICY_COST) VALUES (?, ?, ?, ?, ?) WHERE CUSTOMER_ID = ?");
            database.dropPolicy = database.connect.prepareStatement("DELETE FROM policy WHERE POLICY_ID = ?");
            database.addClaim = database.connect.prepareStatement("INSERT INTO claim (CLAIM_ID, CUSTOMER_ID, DESCRIPTION, CLAIM_TYPE, CLAIM_PAYMENT) VALUES (?, ?, ?, ?, ?)");
            database.addVehicle = database.connect.prepareStatement("INSERT INTO auto_insurance (POLICY_ID, YEAR, MAKE, MODEL, VIN_NUMBER, MARKET_VALUE, AGE, GENDER, GEOGRAPHIC_LOCATION, CREDIT_HISTORY, DRIVING_RECORD, ANNUAL_MILES) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            // database.makePayment = database.connect.prepareStatement("INSERT INTO auto_insurance (POLICY_ID, YEAR, MAKE, MODEL, VIN_NUMBER, MARKET_VALUE, AGE, GENDER, GEOGRAPHIC_LOCATION, CREDIT_HISTORY, DRIVING_RECORD, ANNUAL_MILES) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
        }
        catch (SQLException exception) {
            System.out.println("Error with Prepared Statements!");
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
    public int insertCustomer(String customer_id, String name, String address, String age, String phone_number) {
        int success = 0;
        try {
            createCustomer.setString(1, customer_id);
            createCustomer.setString(2, name);
            createCustomer.setString(3, address);
            createCustomer.setString(4, age);
            createCustomer.setString(5, phone_number);
            success = createCustomer.executeUpdate();
        }
        catch (SQLException exception) {
            System.out.println("invalid input!");
        }
        return success;
    }

    /**
     * Function for inserting new customer policy into the database
     */
    public int insertPolicy(String policy_id, String customer_id, String policy_type, String coverage, String policy_cost) {
        int success = 0;
        try {
            createCustomer.setString(1, policy_id);
            createCustomer.setString(2, customer_id);
            createCustomer.setString(3, policy_type);
            createCustomer.setString(4, coverage);
            createCustomer.setString(5, policy_cost);
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
            createCustomer.setString(1, policy_id);
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
            createCustomer.setString(1, claim_id);
            createCustomer.setString(2, customer_id);
            createCustomer.setString(3, description);
            createCustomer.setString(4, claim_type);
            createCustomer.setString(5, claim_payment);
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
    public int insertAutoInsurance(String policy_id, String year, String make, String model, String vin_number, String market_value, String age, String gender, String geographic_location, String credit_history, String driving_record, String annual_miles) {
        int success = 0;
        try {
            createCustomer.setString(1, policy_id);
            createCustomer.setString(2, year);
            createCustomer.setString(3, make);
            createCustomer.setString(4, model);
            createCustomer.setString(5, vin_number);
            createCustomer.setString(6, market_value);
            createCustomer.setString(7, age);
            createCustomer.setString(8, gender);
            createCustomer.setString(9, geographic_location);
            createCustomer.setString(10, credit_history);
            createCustomer.setString(11, driving_record);
            createCustomer.setString(12, annual_miles);
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
}

// there's some discrepencies between ER diagram and schema in terms of the fields
// refactor database schema in oracle and repopulate fields with sample data
// make enter statements more detailed
// catch exceptions on bad sql statements
// way to differentiate in functions whether user entered invalid input or something already exist in database
// executeUpdate() vs executeQuery() vs execute() commands
// potential UI for CLI?
// What about cascading changes to database, like a customer name changes?