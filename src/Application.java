/**
 * Course: CSE 341
 * Semester and Year: Spring 2021
 * Assignment: Database Systems
 * Author: Derei, Tal
 * User ID: tad222
 */

import java.io.*;
import java.util.Scanner;
import insurance.Customer_Interface;
import insurance.Adjuster_Interface;
import insurance.Agent_Interface;
import insurance.Corporate_Interface;

/**
 * User-interface for insurance database
 */
public class Application {
    public static void main(String[] args) {
        /**
         * Connect to customer, agent, adjuster, and corperate interfaces
         */
        System.out.println("**************************************************************");
        System.out.println("[1] Customer Interface\n");
        System.out.println("[2] Agent Interface\n");
        System.out.println("[3] Adjuster Interface\n");
        System.out.println("[4] Corporate Management Interface");
        System.out.println("**************************************************************");
        int interfaces;
        while (true) {
            System.out.print("\nChoose From the List of Interfaces Above: ");
            Scanner input = new Scanner(System.in);
            boolean condition = input.hasNextInt();
            if (condition) {
                interfaces = input.nextInt();
                if (interfaces == 1) {
                    Customer_Interface customer_connection = Customer_Interface.connect_database();
                    break;
                }
                else if (interfaces == 2) {
                    Agent_Interface agent_connection = Agent_Interface.connect_database();
                    break;
                }
                else if (interfaces == 3) {
                    Adjuster_Interface adjuster_connection = Adjuster_Interface.connect_database();
                    break;
                }
                else if (interfaces == 4) {
                    Corporate_Interface corporate_interface = Corporate_Interface.connect_database();
                    break;
                }
            }
        }
    }
}

