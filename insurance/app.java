/**
 * Course: CSE 341
 * Semester and Year: Spring 2021
 * Assignment: Database Systems
 * Author: Derei, Tal
 * User ID: tad222
 */

import java.io.*;
import java.util.Scanner;

/**
 * User-interface for insurance database
 */
public class app {
    public static void main(String[] args) {
        /**
         * User database login credentials -- username and password
         */
        String user;
        String pass;
        // boolean connection_status = true;

        /**
         * Connect to the database
         */
        Scanner input = new Scanner(System.in);
        // while (connection_status) {
            System.out.print("enter Oracle user id: ");   
            user = input.nextLine();
            System.out.print("enter Oracle password for " + user + ": ");
            pass = input.nextLine();

            Insurance database = Insurance.connect_database(user, pass);
        // }

        /**
         * Connect to customer, agent, adjuster, or corperate interfaces
         */
        System.out.println("Enter [1] customer\n");
        System.out.println("Enter [2] agent\n");
        System.out.println("Enter [3] adjuster\n");
        System.out.println("Enter [4] corporate management\n");
        int interfaces;
        while (true) {
            boolean condition = input.hasNextInt();
            if (condition) {
                interfaces = input.nextInt();
                if (interfaces == 1) {
                    new Insurance().customer(database);
                    break;
                }
                else if (interfaces == 2) {
                    new Insurance().agent(database);
                    break;
                }
                else if (interfaces == 3) {
                    new Insurance().adjuster(database);
                    break;
                }
                else if (interfaces == 4) {
                   new Insurance().corperate(database);
                   break;
                }
            }
            else {
                System.out.println("invalid input! Try again!");
                input.next();
            }
        }
    }
}


