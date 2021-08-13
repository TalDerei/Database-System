/**
 * Course: CSE 341
 * Semester and Year: Spring 2021
 * Assignment: Database Systems
 * Author: Derei, Tal
 * User ID: tad222
 */

import java.sql.Connection;
import java.util.Scanner;
import insurance.Customer_Interface;
import insurance.Adjuster_Interface;
import insurance.Agent_Interface;
import insurance.Corporate_Interface;
import insurance.DBManager;

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

