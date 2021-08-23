package manager;

import java.sql.*;
import java.util.*;

public class DBManager {
    private static Connection conn;
   
    /**
     * Establish a connection to the PostgreSQL Database
     * @return Connection object
     */
    public static Connection connect() {
        /* User-defined variables and database parameters */
        Connection conn;
        final String url = "jdbc:postgresql://ec2-52-1-20-236.compute-1.amazonaws.com:5432/dfocu1cnfk70bl";
        Scanner input = new Scanner(System.in);
        String username = "ezdvczoxegzrgf";
        String password = "e620a6a42fb3d6b0dd4d628325d441386eb62ccd53eee2439aa7b86c9caa91ed";

        /* try connecting to database with user-credentials */
        try {
            conn = DriverManager.getConnection(url, username, password);
            conn.setAutoCommit(false);
            System.out.println("Connected to the PostgreSQL database server successfully!");
        } catch (SQLException exception) {
            System.out.println("\nConnection to the postgres database server failed! Please try again!\n");
            return connect();
        }
        return conn;
    }

    /**
     * Disconnect from database
     */
    public static void disconnect() {
        if (conn != null) {
            try {
                conn.close();
            }
            catch (SQLException exception) {}
        }
    }

    public static Connection getConnection() {
        if (conn == null) {
            conn = DBManager.connect();
        }
        return conn;
    }
}


