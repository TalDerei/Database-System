/**
 * Course: CSE 341
 * Semester and Year: Spring 2021
 * Assignment: Database Systems
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
        boolean connection_value = true;

        while (connection_value) {
            // Scanner class used to get user input
            Scanner input = new Scanner(System.in);
            
            // prompt username and passwords 
            System.out.print("enter Oracle user id: ");   
            user = input.nextLine();
            System.out.print("enter Oracle password for " + user + ": ");
            pass = input.nextLine();

            // execute connect_database method
            connection_value = new insurance().connect_database(user, pass);
        }
    }
}
