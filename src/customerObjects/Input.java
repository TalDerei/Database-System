package customerObjects;

import java.util.Scanner;
import java.text.*;

public class Input {
    /**
     * input checking for user-defined integers
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
     * user_string provides input checking for strings
     */
    public String user_string() {
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
     * validateDate check's the format of date data types
     */
    public Boolean validateDate(String date_of_birth) {
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
