/**
 * Course: CSE 341
 * Semester and Year: Spring 2021
 * Assignment: Database System
 * Author: Derei, Tal
 * User ID: tad222
 */

import java.io.*;
import java.util.*;
import java.sql.*;
import java.text.*;;

/**
 * User-interface for insurance database
 */
public class app {
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
                valid_start_date = new insurance().validateDate(start_date);
            }
            System.out.print("Enter end date in Oracle standard format (dd-MON-yy): ");
            while (valid_end_date) {
                finish_date = input.next();   
                valid_end_date = new insurance().validateDate(finish_date); 
            }

            // execute connect_database method
            connection_value = new insurance().connect_database(user, pass, start_date, finish_date);
        }
    }
}
