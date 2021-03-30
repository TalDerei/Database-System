/*
 * Course: CSE341
 * Semester and Year: Spring 2021
 * Assignment: JBDC with Oracle Database
 * Author: Derei, Tal
 * User ID: tad222
 */

import java.util.*;
import java.sql.*;
import java.text.*;

public class insurance {
    public static void main(String[] args) {
        // input variables
        String user;
        String pass;
        String start_date = "";
        String finish_date = "";
        boolean connection_value = true;

        while (connection_value) {
            // boolean checkers for valid dates
            boolean valid_start_date = true;
            boolean valid_end_date = true;

            // Scanner class used to get user input
            Scanner input = new Scanner(System.in);
            
            // prompt username and passwords 
            System.out.print("enter Oracle user id: ");   
            user = input.nextLine();
            System.out.print("enter Oracle password for " + user + ": ");
            pass = input.nextLine();

            // prompt start and finish dates
            System.out.println("Input starting and ending dates for stock price data: ");
            System.out.print("Enter start date in Oracle standard format (dd-MON-yy): ");
            while (valid_start_date) {
                start_date = input.next();
                valid_start_date = validateDate(start_date);
            }
            System.out.print("Enter end date in Oracle standard format (dd-MON-yy): ");
            while (valid_end_date) {
                finish_date = input.next();   
                valid_end_date = validateDate(finish_date); 
            }

            // execute connect_database method
            connection_value = connect_database(user, pass, start_date, finish_date);
        }
    }

    // establish connection to Oracle database and execute SQL queries using JDBC
    public static Boolean connect_database(String user, String pass, String start_date, String finish_date) {
        try (Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@edgar1.cse.lehigh.edu:1521:cse241",user,pass);
            Statement statement = connection.createStatement();
        ) {
            System.out.println("connection to oracle database SUCCEEDED!\n");
            String query = "SELECT count(day) as days, AVG(shares_traded) as shares, AVG(num_trades) as trades, AVG(dollar_volume) as volume FROM stocks WHERE day between '" + start_date + "' and '" + finish_date + "'";
            ResultSet result = statement.executeQuery(query);
            if (!result.next()) {
                System.out.print("Empty result!");
            }
            else {
                String days = result.getString("days");
                String shares = result.getString("shares");
                String trades = result.getString("trades");
                String volume = result.getString("volume");

                System.out.println("Between " + start_date + " and " + finish_date + " there were:");
                System.out.println(days + " trading days");
                System.out.println(shares + " shares traded on average per trading day.");
                System.out.println(trades + " trades on average per trading day.");
                System.out.println(volume + " dollar volume on average per trading day.");
            }

            // close all connections
            result.close();
            statement.close();
            connection.close();
        } catch (Exception exception) {
            // exception.printStackTrace();
            System.out.println("connection to oracle database FAILED! Try again!");
            return true;
        }
        return false;
    }

    // validateDate check's the validity of the user's start and finish dates
    public static Boolean validateDate(String user_date) {
        DateFormat format = new SimpleDateFormat("dd-MMM-yy");
        try {
            format.parse(user_date);
        } catch (ParseException e) {
            System.out.println("date invalid. Please try again!");
            return true;
        }
        return false;
    }
}