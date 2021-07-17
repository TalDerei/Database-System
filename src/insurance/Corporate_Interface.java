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
        String url = "jdbc:postgresql://ec2-52-1-20-236.compute-1.amazonaws.com:5432/dfocu1cnfk70bl";
        String username = "ezdvczoxegzrgf";
        String password = "e620a6a42fb3d6b0dd4d628325d441386eb62ccd53eee2439aa7b86c9caa91ed";
        // try {
        //     Scanner input = new Scanner(System.in);
        //     System.out.print("enter postgres username: ");   
        //     username = input.nextLine();
        //     System.out.print("enter postgres password for " + username + ": ");
        //     password = input.nextLine();    
        // }
        // catch (InputMismatchException inputMismatchException) {
        //     System.out.println("Wrong credentials! Try Again!");
        //     return null;
        // }
        Corporate_Interface database = new Corporate_Interface();

        try (Connection connection = DriverManager.getConnection(url, username, password)
        ) {
            System.out.println("Connected to the PostgreSQL server successfully.");
            connection.setAutoCommit(false);
            database.connect = connection;   
            Corperate(database);         
        } catch (SQLException exception) {
            System.out.println("\nConnection to the postgres database failed! Try again!\n");
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
        System.out.println("[9] Exit!");
        System.out.println("--------------------------------------------------------------");
        System.out.println("\n");
        System.out.println("***************************************************************************************************");
        System.out.println("THE CORPERATE INTERFACE IS DOWN FOR MAINTENENCE AS THE DEPARTMENT IS UNDERGOING A RESTRUCTUERING!");
        System.out.println("***************************************************************************************************");
    }
}