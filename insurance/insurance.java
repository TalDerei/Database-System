/**
 * Course: CSE 341
 * Semester and Year: Spring 2021
 * Assignment: Database System
 * Author: Derei, Tal
 * User ID: tad222
 */

import java.util.Scanner;
import java.sql.Connection;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.sql.SQLException;

/**
 * Insuance 
 */
public class insurance {
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
    public static Boolean validateDate(String start_date) {
        DateFormat format = new SimpleDateFormat("dd-MMM-yy");
        try {
            format.parse(start_date);
        } catch (ParseException e) {
            System.out.println("date invalid. Please try again!");
            return true;
        }
        return false;
    }
}