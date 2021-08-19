/**
 * Course: CSE 341
 * Semester and Year: Spring 2021
 * Assignment: Database Systems
 * Author: Derei, Tal
 * User ID: tad222
 */

import java.sql.Connection;
import java.util.Scanner;
import interfaces.Customer_Interface;
import interfaces.Adjuster_Interface;
import interfaces.Agent_Interface;
import interfaces.Corporate_Interface;
import manager.DBManager;

/**
 * User-interface for insurance database
 */
public class Main {
    public static void main(String[] args) {
        /**
         * Connect to PostgreSQL database
         */
        Connection connection = DBManager.connect();

        System.out.print("\nChoose From the List of Interfaces Below: \n");
        System.out.println("**************************************************************");
        System.out.println("[1] Customer Interface\n");
        System.out.println("[2] Agent Interface\n");
        System.out.println("[3] Adjuster Interface\n");
        System.out.println("[4] Corporate Interface");
        System.out.println("**************************************************************");
        int interfaces;
        while (true) {
            Scanner input = new Scanner(System.in);
            boolean condition = input.hasNextInt();
            if (condition) {
                interfaces = input.nextInt();
                if (interfaces == 1) {
                    Customer_Interface.Customer(connection);
                    break;
                }
                else if (interfaces == 2) {
                    Agent_Interface.Agent(connection);
                    break;
                }
                else if (interfaces == 3) {
                    Adjuster_Interface.Adjuster(connection);
                    break;
                }
                else if (interfaces == 4) {
                    Corporate_Interface.Corperate();
                    break;
                }
            }
        }
        /**
         * Disconnect from PostgreSQL database
         */
        DBManager.disconnect();
    }
}

// create getters and setters and simplify CLI
// create seperate classes and objects 
// implement arraylists for retrieving data from database
// create interface boilerplate
// tests (don't change database)
// change to boolean
// make objects folder explicit 
// function return types
// clean open resources like 'input'