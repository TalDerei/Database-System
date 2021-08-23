package objects.CustomerObjects;

import java.util.Scanner;
import java.text.*;

public class Input {
    /**
     * Input checking for user-defined integers
     * @return User's inputs
     */
    public static int user_integer() {
        Scanner input = new Scanner(System.in);
        boolean conditional = true;
        while (conditional) {
            boolean condition = input.hasNextInt();
            if (condition) {
                int user_input = input.nextInt();
                conditional = false; 
                return user_input; 
            }
            else {
                System.out.println("Enter a valid integer!");
                conditional = true;
                input.next();
            }
        }
        return 0;
    }

    /**
     * Input checking for user-defined strings
     * @return User's input
     */
    public static String user_string() {
        Scanner input = new Scanner(System.in);
        boolean conditional = true;
        while (conditional) {
            boolean condition = input.hasNextInt();
            if (!condition) {
                String user_input = input.nextLine();
                conditional = false; 
                return user_input; 
            }
            else {
                System.out.println("Entered integer! Please enter a string and try again!");
                conditional = true;
                input.next();
            }
        }
        return "0";
    }

    /**
     * Check the validity of the format of 'date' data types
     * @param date_of_birth
     * @return Whether date is valid or not
     */
    public static Boolean validateDate(String date_of_birth) {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            format.parse(date_of_birth);
        } catch (ParseException exception) {
            System.out.println("date invalid. Please try again!");
            return true;
        }
        return false;
    }
}
