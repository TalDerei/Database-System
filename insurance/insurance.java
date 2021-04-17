/**
 * Course: CSE 341
 * Semester and Year: Spring 2021
 * Assignment: Database Systems
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
    public Boolean connect_database(String user, String pass) {
        try (Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@edgar1.cse.lehigh.edu:1521:cse241",user,pass);
            Statement statement = connection.createStatement();
        ) {
            System.out.println("connection to oracle database SUCCEEDED!\n");
        } catch (Exception exception) {
            exception.printStackTrace();
            System.out.println("connection to oracle database FAILED! Try again!");
            return true;
        }
        return false;
    }
}